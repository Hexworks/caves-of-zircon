package org.hexworks.cavesofzircon.blocks

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.extensions.fetchTile
import org.hexworks.cavesofzircon.extensions.occupiesBlock
import org.hexworks.cavesofzircon.builders.GameTileRepository
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock private constructor(private var entityTile: GameTile = GameTileRepository.floor,
                                    private val entities: MutableList<Entity> = mutableListOf())
    : BlockBase<GameTile>() {

    override val layers
        get() = mutableListOf(entityTile)

    fun isOccupied() = entities.any { it.occupiesBlock() }

    fun isEmptyFloor() = entities.isEmpty()

    fun fetchOccupier() = entities.firstOrNull { it.occupiesBlock() }
            ?: throw NoSuchElementException("This block is not occupied.")

    fun getEntities() = entities.toList()

    fun addEntity(entity: Entity) {
        entities.add(entity)
        entityTile = entity.fetchTile()
    }

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
        entityTile = entities.lastOrNull()?.fetchTile() ?: GameTileRepository.floor
    }

    override fun fetchSide(side: BlockSide): GameTile {
        return GameTileRepository.empty
    }

    companion object {

        fun create(): GameBlock = GameBlock()

        fun createWith(entity: Entity) = GameBlock().also {
            it.addEntity(entity)
        }
    }
}
