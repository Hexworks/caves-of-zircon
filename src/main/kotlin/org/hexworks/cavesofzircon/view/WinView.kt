package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.world.GameBuilder
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.api.mvc.base.BaseView

class WinView(private val zircons: Int) : BaseView() {

    override val theme = GameConfig.THEME

    override fun onDock() {
        val msg = "You won!"
        val header = Components.textBox()
                .withContentWidth(GameConfig.VISIBLE_WORLD_WIDTH / 2)
                .addHeader(msg)
                .addNewLine()
                .addParagraph("Congratulations! You have escaped from Caves of Zircon!", withNewLine = false)
                .addParagraph("You've managed to find $zircons Zircons.")
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .build()
        val restartButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_LEFT)
                .withText("Restart")
                .wrapSides(false)
                .wrapWithBox()
                .withBoxType(BoxType.SINGLE)
                .build()
        val exitButton = Components.button()
                .withAlignmentAround(header, ComponentAlignment.BOTTOM_RIGHT)
                .withText("Quit")
                .wrapSides(false)
                .wrapWithBox()
                .withBoxType(BoxType.SINGLE)
                .build()

        restartButton.onMouseReleased {
            replaceWith(PlayView(GameBuilder(
                    worldSize = GameConfig.WORLD_SIZE).buildGame()))
            close()
        }

        exitButton.onMouseReleased {
            System.exit(0)
        }

        screen.addComponent(header)
        screen.addComponent(restartButton)
        screen.addComponent(exitButton)
    }
}
