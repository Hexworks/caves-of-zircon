package org.hexworks.cavesofzircon.blocks

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.builders.GameTileRepository
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.BlockSide
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BlockBase

class GameBlock private constructor(private var defaultTile: Tile = GameTileRepository.floor(),
                                    private val currentEntities: MutableList<GameEntity<EntityType>> = mutableListOf())
    : BlockBase<Tile>() {

    override val layers
        get() = mutableListOf(defaultTile, currentEntities.map {
            it.tile
        }.let {
            if (it.contains(GameTileRepository.PLAYER)) {
                GameTileRepository.PLAYER
            } else {
                it.firstOrNull()
            }
        } ?: GameTileRepository.EMPTY)

    val isOccupied: Boolean
        get() = currentEntities.any { it.occupiesBlock }

    val isEmptyFloor: Boolean
        get() = currentEntities.isEmpty()

    val hasWall: Boolean
        get() = currentEntities.any { it.isWall }

    val hasStairsUp: Boolean
        get() = currentEntities.any { it.isStairsUp }

    val hasExit: Boolean
        get() = currentEntities.any { it.isExit }

    val hasStairsDown: Boolean
        get() = currentEntities.any { it.isStairsDown }

    val occupier: GameEntity<EntityType>
        get() = currentEntities.firstOrNull { it.occupiesBlock }
                ?: throw NoSuchElementException("This block is not occupied.")

    val entities: Iterable<GameEntity<EntityType>>
        get() = currentEntities.toList()

    fun addEntity(entity: GameEntity<EntityType>) {
        currentEntities.add(entity)
    }

    fun removeEntity(entity: GameEntity<EntityType>) {
        currentEntities.remove(entity)
    }

    override fun fetchSide(side: BlockSide): Tile {
        return GameTileRepository.EMPTY
    }

    override fun createCopy(): Block<Tile> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setSide(side: BlockSide, tile: Tile) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        fun create(): GameBlock = GameBlock()

        fun createWith(entity: GameEntity<EntityType>) = GameBlock().also {
            it.addEntity(entity)
        }
    }
}
