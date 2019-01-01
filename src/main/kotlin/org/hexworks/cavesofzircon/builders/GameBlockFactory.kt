package org.hexworks.cavesofzircon.builders

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.zircon.api.data.impl.Position3D

object GameBlockFactory {

    fun floor() = GameBlock.create()

    fun wall(position: Position3D) = GameBlock.createWith(EntityFactory.newWall(position))

    fun stairsDown() = GameBlock.createWith(EntityFactory.newStairsDown())

    fun stairsUp() = GameBlock.createWith(EntityFactory.newStairsUp())

    fun exit() = GameBlock.createWith(EntityFactory.exit())

}
