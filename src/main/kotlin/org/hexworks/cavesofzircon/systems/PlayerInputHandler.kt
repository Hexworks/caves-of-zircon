package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.base.BaseBehavior
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.ItemHolder
import org.hexworks.cavesofzircon.commands.*
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.hasInventory
import org.hexworks.cavesofzircon.extensions.isPlayer
import org.hexworks.cavesofzircon.extensions.position
import org.hexworks.cavesofzircon.view.dialog.openEquipmentDialog
import org.hexworks.cavesofzircon.view.dialog.openHelpDialog
import org.hexworks.cavesofzircon.view.dialog.openInventoryDialog
import org.hexworks.cavesofzircon.view.dialog.openLootDialog
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.uievent.KeyCode.*
import org.hexworks.zircon.api.uievent.KeyboardEvent

/**
 * This [System] is responsible for figuring out what to do based on
 * the input which was received from the player.
 */
object PlayerInputHandler : BaseBehavior<GameContext>() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val (world, screen, uiEvent, player) = context
        val currentPos = player.position
        if (uiEvent is KeyboardEvent) {
            logger.info("Receiving player input: $uiEvent")
            val (newPos, direction) = when (uiEvent.code) {
                KEY_W -> currentPos.withRelativeY(-1) to CameraMoveDirection.UP
                KEY_A -> currentPos.withRelativeX(-1) to CameraMoveDirection.LEFT
                KEY_S -> currentPos.withRelativeY(1) to CameraMoveDirection.DOWN
                KEY_D -> currentPos.withRelativeX(1) to CameraMoveDirection.RIGHT
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
            when (uiEvent.code) {
                KEY_F -> player.executeCommand(MoveDown(context, player, newPos))
                KEY_R -> player.executeCommand(MoveUp(context, player, newPos))
                KEY_I -> openInventoryDialog(context)
                KEY_E -> openEquipmentDialog(context)
                KEY_P -> player.executeCommand(PickItemUp(context, player, currentPos))
                KEY_L -> world.fetchBlockAt(currentPos).map { block ->
                    block.entities.filterNot { it.isPlayer }
                            .firstOrNull { entity ->
                                entity.hasInventory()
                            }?.let { lootable ->
                                @Suppress("UNCHECKED_CAST")
                                openLootDialog(context, player, lootable as GameEntity<ItemHolder>)
                            }
                }
                KEY_H -> openHelpDialog(screen)
                else -> {
                    //
                }
            }
        }
        return true
    }
}
