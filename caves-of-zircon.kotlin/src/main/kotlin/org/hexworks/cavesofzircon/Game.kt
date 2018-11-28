package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.factory.EntityFactory
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cavesofzircon.world.WorldBuilder
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode.TOP_DOWN
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.screen.Screen

class Game(val screen: Screen) {

    private val screenSize = screen.size
    private val world = WorldBuilder(screenSize + screenSize)
            .makeCaves()
            .build(Sizes.from2DTo3D(screenSize, 1))
    private val gameComponent: GameComponent<GameTile, GameBlock> = GameComponents.newGameComponentBuilder<GameTile, GameBlock>()
            .withGameArea(world)
            .withVisibleSize(Sizes.from2DTo3D(screenSize, 1))
            .withProjectionMode(TOP_DOWN)
            .build()
    private var player = EntityFactory.newPlayer()

    private val entities = mutableListOf<Entity>()

    init {
        entities.add(player)
        world.addEntity(player, Positions.create3DPosition(1, 1, 0))
        screen.addComponent(gameComponent)
    }

    fun addInput(input: Input) {
        entities.forEach {
            it.update(Context(world, screen, input, it))
        }
    }
}
