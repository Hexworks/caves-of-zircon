package org.hexworks.cavesofzircon.builders

import org.hexworks.cavesofzircon.blocks.GameBlock

object GameBlockFactory {

    fun floor() = GameBlock.create()

    fun wall() = GameBlock.createWith(EntityFactory.newWall())
}
