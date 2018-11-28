package org.hexworks.cavesofzircon.ai

import org.hexworks.cavesofzircon.blocks.Creature
import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.factory.EntityFactory
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position


class FungusAI(private val fungus: Creature,
               private val entityFactory: EntityFactory) : CreatureAI {

    private var spreadcount: Int = 0

    override fun onEnter(position: Position, block: GameBlock): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdate() {
        if (spreadcount < 5 && Math.random() < 0.02) {
            spread()
        }
    }

    private fun spread() {
        val x = fungus.position.x + (Math.random() * 11).toInt() - 5
        val y = fungus.position.y + (Math.random() * 11).toInt() - 5
        val newPos = Positions.create(x, y)

        if (fungus.canEnter(newPos)) {
            val child = entityFactory.newFungus()
//            child.moveTo(newPos)
            spreadcount++
        }
    }
}
