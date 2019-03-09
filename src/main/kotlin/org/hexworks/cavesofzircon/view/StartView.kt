package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.GameConfig
import org.hexworks.cavesofzircon.GameConfig.WORLD_SIZE
import org.hexworks.cavesofzircon.world.GameBuilder
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.extensions.onComponentEvent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed

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
        startButton.onComponentEvent(ComponentEventType.ACTIVATED) {
            val game = GameBuilder(
                    worldSize = WORLD_SIZE).buildGame()
            replaceWith(PlayView(game))
            close()
            Processed
        }
        screen.addComponent(header)
        screen.addComponent(startButton)
    }
}
