package org.hexworks.cavesofzircon.blocks

import org.hexworks.cavesofzircon.ai.CreatureAI
import org.hexworks.cavesofzircon.ai.NoOpAi
import org.hexworks.cavesofzircon.world.World
import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.data.Position


class Creature(private val world: World,
               initialPosition: Position = Position.unknown(),
               val tile: GameTile,
               var ai: CreatureAI = NoOpAi()) {

    var position: Position = initialPosition
        private set


    fun dig(position: Position) {
        world.dig(position)
    }

    fun canEnter(position: Position): Boolean {
        return world.fetchBlockAt(position).fold(whenEmpty = {
            false
        }, whenPresent = {
            it.isGround()
        })
    }

    fun update() {
        ai.onUpdate()
    }

    fun moveTo(position: Position): Boolean {
        this.position = position
        world.fetchBlockAt(position).map {
            it.setTile(tile)
        }
        return true
    }

    fun moveBy(position: Position): Boolean {
        val oldBlock = world.fetchBlockAt(this.position).get()
        val newPos = this.position + position
        var moved = false
        world.fetchBlockAt(newPos).map { newBlock ->
            if (ai.onEnter(newPos, newBlock)) {
                oldBlock.clearTile()
                newBlock.setTile(tile)
                this.position = newPos
                moved = true
            }
        }
        return moved
    }

    fun attack(other: Creature) {
        world.remove(other)
    }

}
