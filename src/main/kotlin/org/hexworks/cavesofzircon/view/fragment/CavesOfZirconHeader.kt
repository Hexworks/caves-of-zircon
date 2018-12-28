package org.hexworks.cavesofzircon.view.fragment

import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.graphics.BoxType

class CavesOfZirconHeader : Fragment {
    override val root = Components.textBox()
            .withContentWidth(GameConfig.SIDEBAR_WIDTH - 2)
            .addHeader("Caves of Zircon")
            .addNewLine()
            .addParagraph("Press 'h' for help.")
            .withBoxType(BoxType.SINGLE)
            .wrapWithBox()
            .build()
}
