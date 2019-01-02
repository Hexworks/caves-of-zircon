package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.builders.GameConfig.WORLD_SIZE
import org.hexworks.cavesofzircon.world.GameBuilder
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.api.mvc.base.BaseView

class StartView : BaseView() {

    override val theme = GameConfig.THEME

    override fun onDock() {
        val msg = "Welcome to Caves of Zircon."
        val header = Components.textBox()
                .withContentWidth(msg.length)
                .addHeader(msg)
                .addNewLine()
                .withAlignmentWithin(screen, CENTER)
                .build()
        val startButton = Components.button()
                .withAlignmentAround(header, BOTTOM_CENTER)
                .withText("Start!")
                .wrapSides(false)
                .withBoxType(BoxType.SINGLE)
                .wrapWithShadow()
                .wrapWithBox()
                .build()
        startButton.onMouseReleased {
            val game = GameBuilder(
                    worldSize = WORLD_SIZE).buildGame()
            replaceWith(PlayView(game))
            close()
        }
        screen.addComponent(header)
        screen.addComponent(startButton)
    }
}
