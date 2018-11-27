package org.hexworks.cavesofzircon.view

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_CENTER
import org.hexworks.zircon.api.component.ComponentAlignment.CENTER
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.kotlin.onMouseReleased

class StartView(tileGrid: TileGrid) : BaseView(tileGrid) {

    init {
        val msg = "Welcome to Caves of Zircon."
        val header = Components.textBox()
                .withContentWidth(msg.length)
                .addHeader(msg)
                .addNewLine()
                .withAlignmentWithin(tileGrid, CENTER)
                .build()
        val startButton = Components.button()
                .withAlignmentAround(header, BOTTOM_CENTER)
                .withText("Start!")
                .wrapSides(false)
                .withBoxType(BoxType.DOUBLE)
                .wrapWithBox()
                .build()
        startButton.onMouseReleased {
            PlayView(tileGrid).dock()
        }
        screen.addComponent(header)
        screen.addComponent(startButton)
    }
}
