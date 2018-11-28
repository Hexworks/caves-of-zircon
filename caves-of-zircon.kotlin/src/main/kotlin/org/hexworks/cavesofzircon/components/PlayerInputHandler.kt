package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.commands.MoveCamera
import org.hexworks.cavesofzircon.commands.TryMovePlayerTo
import org.hexworks.cavesofzircon.extensions.isPlayer
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.input.InputType.*

/**
 * This [Component] is responsible for figuring out what to do based on
 * the input which was received from the player.
 */
object PlayerInputHandler : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun update(context: Context) {
        val (world, _, input, entity) = context
        if (entity.isPlayer()) {
            val currentPos = world.findPositionOfEntity(entity).get()
            val newPos = when (input.inputType()) {
                ArrowUp -> currentPos.withRelativeY(-1)
                ArrowDown -> currentPos.withRelativeY(1)
                ArrowLeft -> currentPos.withRelativeX(-1)
                ArrowRight -> currentPos.withRelativeX(1)
                else -> {
                    currentPos
                }
            }
            if (currentPos != newPos) {
                // and if this is the player we also need to update the camera
                entity.executeCommand(TryMovePlayerTo(context, entity, newPos))
                entity.executeCommand(MoveCamera(
                        context = context,
                        source = entity,
                        oldPosition = currentPos,
                        newPosition = newPos))
            }
        }
    }
}
