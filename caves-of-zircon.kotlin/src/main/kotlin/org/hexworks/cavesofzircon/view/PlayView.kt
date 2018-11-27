package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.cavesofzircon.factory.CreatureFactory
import org.hexworks.cavesofzircon.world.WorldBuilder
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode.TOP_DOWN
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onKeyCombination
import org.hexworks.zircon.api.kotlin.onKeyStroke


class PlayView(tileGrid: TileGrid) : BaseView(tileGrid) {

    private val screenSize = screen.size
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
                .withProjectionMode(TOP_DOWN)
                .build()
        screen.addComponent(gameComponent)
        for (i in 0..7) {
            creatureFactory.newFungus()
        }
        val screenPos = player.position - world.visibleOffset().to2DPosition()
        screen.onKeyCombination(inputType = InputType.Enter, char = '\n') {
            WinView(tileGrid).dock()
        }
        screen.onKeyCombination(inputType = InputType.Escape) {
            LoseView(tileGrid).dock()
        }
        screen.onKeyCombination(inputType = InputType.ArrowUp) {
            if (player.moveBy(Positions.create(0, -1)) && screenPos.y < screenSize.height / 2) {
                world.scrollOneBackward()
            }
            world.update()
        }
        screen.onKeyCombination(inputType = InputType.ArrowDown) {
            if (player.moveBy(Positions.create(0, 1)) && screenPos.y > screenSize.height / 2) {
                world.scrollOneForward()
            }
            world.update()
        }
        screen.onKeyCombination(inputType = InputType.ArrowLeft) {
            if (player.moveBy(Positions.create(-1, 0)) && screenPos.x < screenSize.width / 2) {
                world.scrollOneLeft()
            }
            world.update()
        }
        screen.onKeyCombination(inputType = InputType.ArrowRight) {
            if (player.moveBy(Positions.create(1, 0)) && screenPos.x > screenSize.width / 2) {
                world.scrollOneRight()
            }
            world.update()
        }
    }
}
