package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.ItemHolder
import org.hexworks.cavesofzircon.commands.*
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.view.dialog.openEquipmentDialog
import org.hexworks.cavesofzircon.view.dialog.openHelpDialog
import org.hexworks.cavesofzircon.view.dialog.openInventoryDialog
import org.hexworks.cavesofzircon.view.dialog.openLootDialog
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.kotlin.whenKeyStroke

/**
 * This [System] is responsible for figuring out what to do based on
 * the input which was received from the player.
 */
object PlayerInputHandler : BaseBehavior<GameContext>() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val (world, screen, input, player) = context
        logger.debug("Updating player position from new input...")
        val currentPos = player.position
        input.whenKeyStroke { ks ->
            val (newPos, direction) = when (ks.getCharacter()) {
                'w' -> currentPos.withRelativeY(-1) to CameraMoveDirection.UP
                'a' -> currentPos.withRelativeX(-1) to CameraMoveDirection.LEFT
                's' -> currentPos.withRelativeY(1) to CameraMoveDirection.DOWN
                'd' -> currentPos.withRelativeX(1) to CameraMoveDirection.RIGHT
                else -> {
                    currentPos to null
                }
            }
            direction?.let {
                logger.debug("New position ($newPos) is calculated for player. Trying to move.")
                player.executeCommand(MoveTo(context, player, newPos))
                player.executeCommand(MoveCamera(
                        context = context,
                        source = player,
                        oldPosition = currentPos,
                        newPosition = newPos,
                        cameraMoveDirection = it))
            }
            input.whenCharacterIs('f') {
                player.executeCommand(MoveDown(context, player, newPos))
            }
            input.whenCharacterIs('r') {
                player.executeCommand(MoveUp(context, player, newPos))
            }
            input.whenCharacterIs('i') {
                openInventoryDialog(context)
            }
            input.whenCharacterIs('e') {
                openEquipmentDialog(context)
            }
            input.whenCharacterIs('p') {
                player.executeCommand(PickItemUp(context, player, currentPos))
            }
            input.whenCharacterIs('l') {
                world.fetchBlockAt(currentPos).map { block ->
                    block.entities.filterNot { it.isPlayer }
                            .firstOrNull { entity ->
                                entity.hasInventory()
                            }?.let { lootable ->
                                @Suppress("UNCHECKED_CAST")
                                openLootDialog(context, player, lootable as GameEntity<ItemHolder>)
                            }
                }
            }
            input.whenCharacterIs('h') {
                openHelpDialog(screen)
            }
        }
        return true
    }
}
