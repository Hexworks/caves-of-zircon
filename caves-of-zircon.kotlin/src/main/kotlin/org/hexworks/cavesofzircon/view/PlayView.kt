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
    private val world = WorldBuilder(screenSize + screenSize)
            .makeCaves()
            .build(Sizes.from2DTo3D(screenSize, 1))
    private val creatureFactory = CreatureFactory(world)
    private val gameComponent: GameComponent<GameTile, GameBlock>
    private var player = creatureFactory.newPlayer()

    init {
        gameComponent = GameComponents.newGameComponentBuilder<GameTile, GameBlock>()
                .withGameArea(world)
                .withVisibleSize(Sizes.from2DTo3D(screenSize, 1))
                .withProjectionMode(ProjectionMode.TOP_DOWN)
                .build()
        screen.addComponent(gameComponent)
        screen.applyColorTheme(ColorThemes.capturedByPirates())
        for (i in 0..7) {
            creatureFactory.newFungus()
        }
    }

    override fun display() {
        screen.display()
    }

    override fun respondToUserInput(input: Input): View {
        val screenPos = player.position - world.visibleOffset().to2DPosition()
        val result = when (input.inputType()) {
            Enter -> WinView(tileGrid)
            Escape -> LoseView(tileGrid)
            ArrowUp -> {
                if (player.moveBy(Positions.create(0, -1)) && screenPos.y < screenSize.height / 2) {
                    world.scrollOneBackward()
                }
                this
            }
            ArrowDown -> {
                if (player.moveBy(Positions.create(0, 1)) && screenPos.y > screenSize.height / 2) {
                    world.scrollOneForward()
                }
                this
            }
            ArrowLeft -> {
                if (player.moveBy(Positions.create(-1, 0)) && screenPos.x < screenSize.width / 2) {
                    world.scrollOneLeft()
                }
                this
            }
            ArrowRight -> {
                if (player.moveBy(Positions.create(1, 0)) && screenPos.x > screenSize.width / 2) {
                    world.scrollOneRight()
                }
                this
            }
            else -> this
        }
        if (input.isKeyStroke()) {
            world.update()
        }
        return result
    }
}
