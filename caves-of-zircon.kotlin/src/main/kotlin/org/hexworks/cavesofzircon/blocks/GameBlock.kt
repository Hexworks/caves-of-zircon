package org.hexworks.cavesofzircon.blocks

import org.hexworks.cavesofzircon.repository.GameTileRepository
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock(private var tile: GameTile,
                private val type: GameBlockType) : BlockBase<GameTile>() {

    private val originalTile = tile

    override val layers
        get() = mutableListOf(tile)

    override fun toString(): String {
        return "GameBlock(name=${type.name})"
    }

    fun isDiggable() = tile === GameTileRepository.wall

    fun isGround() = tile === GameTileRepository.floor

    fun hasCreature() = tile === GameTileRepository.fungus

    fun setTile(tile: GameTile) {
        this.tile = tile
    }

    fun clearTile() {
        this.tile = originalTile
    }


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
