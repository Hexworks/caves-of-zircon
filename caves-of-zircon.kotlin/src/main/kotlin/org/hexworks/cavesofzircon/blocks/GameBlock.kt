package org.hexworks.cavesofzircon.blocks

import org.hexworks.cavesofzircon.builders.GameTileRepository
import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.extensions.fetchTile
import org.hexworks.cavesofzircon.extensions.isWall
import org.hexworks.cavesofzircon.extensions.occupiesBlock
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock private constructor(private var defaultTile: GameTile = GameTileRepository.floor(),
                                    private val entities: MutableList<Entity> = mutableListOf())
    : BlockBase<GameTile>() {

    override val layers
        get() = mutableListOf(defaultTile, entities.map { it.fetchTile() }.lastOrNull() ?: GameTileRepository.EMPTY)

    fun isOccupied() = entities.any { it.occupiesBlock() }

    fun isEmptyFloor() = entities.isEmpty()

    fun isWall() = entities.any { it.isWall() }

    fun fetchOccupier() = entities.firstOrNull { it.occupiesBlock() }
            ?: throw NoSuchElementException("This block is not occupied.")

    fun getEntities() = entities.toList()

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
    }

    override fun fetchSide(side: BlockSide): GameTile {
        return GameTileRepository.EMPTY
    }

    companion object {

        fun create(): GameBlock = GameBlock()

        fun createWith(entity: Entity) = GameBlock().also {
            it.addEntity(entity)
        }
    }
}
