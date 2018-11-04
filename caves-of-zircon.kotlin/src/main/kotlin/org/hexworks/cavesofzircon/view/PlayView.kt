package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.tiles.GameBlock
import org.hexworks.cavesofzircon.tiles.GameTile
import org.hexworks.cavesofzircon.world.WorldBuilder
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType.*

class PlayView(private val tileGrid: TileGrid) : View {

    private val screenSize = tileGrid.size
    private val toolbarWidth = screenSize.width.div(5)
    private val screen = Screens.createScreenFor(tileGrid)
    private val world = WorldBuilder(Sizes.create(200, 200))
            .makeCaves()
            .build()

    private val gameComponent: GameComponent<GameTile, GameBlock>

    init {
        val toolbar = Components.panel()
                .withTitle("Toolbar")
                .withSize(screenSize.withWidth(toolbarWidth))
                .withBoxType(BoxType.SINGLE)
                .wrapWithBox()
                .build()
        screen.addComponent(toolbar)
        gameComponent = GameComponents.newGameComponentBuilder<GameTile, GameBlock>()
                .withGameArea(world)
                .withPosition(Positions.defaultPosition().relativeToRightOf(toolbar))
                .withVisibleSize(Sizes.from2DTo3D(screenSize.withRelativeWidth(-toolbarWidth), 1))
                .build()
        screen.addComponent(gameComponent)

        screen.display()

        screen.applyColorTheme(ColorThemes.capturedByPirates())
    }

    override fun display() {
        screen.display()
    }

    override fun respondToUserInput(input: Input): View {
        return when (input.inputType()) {
            Enter -> WinView(tileGrid)
            Escape -> LoseView(tileGrid)
            ArrowUp -> {
                gameComponent.scrollOneBackward()
                this
            }
            ArrowDown -> {
                gameComponent.scrollOneForward()
                this
            }
            ArrowLeft -> {
                gameComponent.scrollOneLeft()
                this
            }
            ArrowRight -> {
                gameComponent.scrollOneRight()
                this
            }
            else -> this
        }
    }
}
