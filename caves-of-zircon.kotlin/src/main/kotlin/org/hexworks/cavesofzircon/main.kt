package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.view.StartView
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.Size
import java.awt.Toolkit

fun main(args: Array<String>) {

    val tileset = CP437TilesetResources.rogueYun16x16()
    val tileSize = tileset.width
    val screenSize = Toolkit.getDefaultToolkit().screenSize

    val windowWidth = screenSize.width.div(tileSize).times(0.8).toInt()
    val windowHeight = screenSize.height.div(tileSize).times(0.8).toInt()

    val grid = SwingApplications.startTileGrid(AppConfigs.newConfig()
            .enableBetaFeatures()
            .withSize(Size.create(windowWidth, windowHeight))
            .withDefaultTileset(tileset)
            .build())


    StartView(grid).dock()

}
