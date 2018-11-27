package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.whenInputTypeIs

class PlayerMoveHandler : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun update(context: Context) {
        val (world, _, input, player) = context
        val playerPos = world.findPositionOfEntity(player).get()
        input.whenInputTypeIs(InputType.ArrowUp) {
            logger.info("Moving player up from $playerPos.")
            if (world.moveEntity(player, playerPos.withRelativeY(-1))) {
                world.scrollOneBackward()
            }
        }
        input.whenInputTypeIs(InputType.ArrowDown) {
            logger.info("Moving player down from $playerPos.")
            if (world.moveEntity(player, playerPos.withRelativeY(1))) {
                world.scrollOneForward()
            }
        }
        input.whenInputTypeIs(InputType.ArrowLeft) {
            logger.info("Moving player left from $playerPos.")
            if (world.moveEntity(player, playerPos.withRelativeX(-1))) {
                world.scrollOneLeft()
            }
        }
        input.whenInputTypeIs(InputType.ArrowRight) {
            logger.info("Moving player right from $playerPos.")
            if (world.moveEntity(player, playerPos.withRelativeX(1))) {
                world.scrollOneRight()
            }
        }
    }
}
