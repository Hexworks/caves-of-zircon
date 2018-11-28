package org.hexworks.cavesofzircon.factory

import org.hexworks.cavesofzircon.blocks.GameBlock

object GameBlockFactory {

    fun floor() = GameBlock.create()

    fun wall() = GameBlock.createWith(EntityFactory.newWall())
}
