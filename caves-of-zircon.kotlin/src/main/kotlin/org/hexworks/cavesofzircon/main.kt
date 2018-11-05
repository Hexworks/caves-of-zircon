package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.view.StartView
import org.hexworks.cavesofzircon.view.View
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.StyleSets
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.kotlin.onInput
import java.awt.Toolkit

fun main(args: Array<String>) {

    val tileSize = 16
    val screenSize = Toolkit.getDefaultToolkit().screenSize

    val windowWidth = screenSize.width.div(tileSize).times(0.6).toInt()
    val windowHeight = screenSize.height.div(tileSize).times(0.6).toInt()

    StyleSets.defaultStyle()
    val grid = SwingApplications.startTileGrid(AppConfigs.newConfig()
            .enableBetaFeatures()
            .withSize(Size.create(windowWidth, windowHeight))
            .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
            .build())


    var currentView: View = StartView(grid)

    grid.onInput { input ->
        try {
            val prevView = currentView
            currentView = currentView.respondToUserInput(input)
            if (prevView !== currentView) {
                currentView.display()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    currentView.display()


}
