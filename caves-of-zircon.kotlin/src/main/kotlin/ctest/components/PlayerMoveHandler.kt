package ctest.components

import ctest.commands.Command
import ctest.commands.HandleInput
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.whenInputTypeIs

class PlayerMoveHandler : BaseComponent() {

    override fun respondToEvent(command: Command): Boolean {

        return command.whenTypeIs<HandleInput, Boolean>(perform = { handleInput ->
            val (context, source, input) = handleInput
            val (world, screen) = context
            input.whenInputTypeIs(InputType.ArrowLeft) {
                
            }
            true
        }, otherwise = { false })
    }

}
