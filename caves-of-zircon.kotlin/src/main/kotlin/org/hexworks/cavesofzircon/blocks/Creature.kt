package org.hexworks.cavesofzircon.blocks

import org.hexworks.cavesofzircon.ai.CreatureAI
import org.hexworks.cavesofzircon.ai.NoOpAi
import org.hexworks.cavesofzircon.world.World
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.kotlin.map


class Creature(private val world: World,
               initialPosition: Position,
               val tile: GameTile,
               var ai: CreatureAI = NoOpAi()) {

    var position: Position = initialPosition
        private set


    fun dig(position: Position) {
        world.dig(position)
    }

    fun moveBy(position: Position) {
        val oldBlock = world.fetchBlockAt(this.position).get()
        val newPos = this.position + position
        println("============================================")
        println("old pos: ${this.position}, new pos: $newPos")
        world.fetchBlockAt(newPos).map { newBlock ->
            println("block found at $newPos, trying to enter...")
            if (ai.onEnter(newPos, newBlock)) {
                println("Can enter! Moving to new position...")
                oldBlock.clearTile()
                newBlock.setTile(tile)
                this.position = newPos
            }
        }
    }

}
