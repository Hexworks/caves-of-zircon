package org.hexworks.cavesofzircon.blocks

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.extensions.fetchTile
import org.hexworks.cavesofzircon.repository.GameTileRepository
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock(private var tile: GameTile,
                private var entityTile: GameTile = GameTileRepository.empty,
                private val type: GameBlockType,
                private val entities: MutableList<Entity> = mutableListOf()) : BlockBase<GameTile>() {

    override val layers
        get() = mutableListOf(tile, entityTile)

    override fun toString(): String {
        return "GameBlock(name=${type.name})"
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
        entityTile = entity.fetchTile()
    }

    fun fetchEntities() = entities.toList()

    fun removeEntity(entity: Entity) {
        entities.remove(entity)
        entityTile = entities.lastOrNull()?.fetchTile() ?: GameTileRepository.empty
    }

    // TODO: remove these start
    fun isDiggable() = tile === GameTileRepository.wall

    fun isGround() = tile === GameTileRepository.floor

    fun hasCreature() = tile === GameTileRepository.fungus

    fun setTile(tile: GameTile) {
        this.tile = tile
    }

    fun clearTile() {
    }
    // TODO: remove these end

    override fun fetchSide(side: BlockSide): GameTile {
        return GameTileRepository.empty
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as GameBlock
        if (type != other.type) return false
        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}
