package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.cavesofzircon.factory.CreatureFactory
import org.hexworks.cavesofzircon.world.WorldBuilder
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType.*

class PlayView(private val tileGrid: TileGrid) : View {

    private val screenSize = tileGrid.size
    private val screen = Screens.createScreenFor(tileGrid)
    private val world = WorldBuilder(Sizes.create(150, 100))
            .makeCaves()
            .build()
    private val creatureFactory = CreatureFactory(world)
    private val gameComponent: GameComponent<GameTile, GameBlock>
    private val player = creatureFactory.newPlayer()

    init {
        gameComponent = GameComponents.newGameComponentBuilder<GameTile, GameBlock>()
                .withGameArea(world)
                .withVisibleSize(Sizes.from2DTo3D(screenSize, 1))
                .withProjectionMode(ProjectionMode.TOP_DOWN)
                .build()
        screen.addComponent(gameComponent)

        screen.display()

        screen.applyColorTheme(ColorThemes.capturedByPirates())
    }

    override fun display() {
        screen.display()
    }

    override fun respondToUserInput(input: Input): View {
        val screenPos = player.position - gameComponent.visibleOffset().to2DPosition()
        return when (input.inputType()) {
            Enter -> WinView(tileGrid)
            Escape -> LoseView(tileGrid)
            ArrowUp -> {
                if (screenPos.y < screenSize.height / 2) {
                    gameComponent.scrollOneBackward()
                }
                player.moveBy(Positions.create(0, -1))
                this
            }
            ArrowDown -> {
                if (screenPos.y > screenSize.height / 2) {
                    gameComponent.scrollOneForward()
                }
                player.moveBy(Positions.create(0, 1))
                this
            }
            ArrowLeft -> {
                if (screenPos.x > screenSize.width / 2) {
                    gameComponent.scrollOneLeft()
                }
                player.moveBy(Positions.create(-1, 0))
                this
            }
            ArrowRight -> {
                if (screenPos.x < screenSize.width / 2) {
                    gameComponent.scrollOneRight()
                }
                player.moveBy(Positions.create(1, 0))
                this
            }
            else -> this
        }
    }
}
