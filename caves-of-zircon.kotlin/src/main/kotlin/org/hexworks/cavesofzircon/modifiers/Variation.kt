package org.hexworks.cavesofzircon.modifiers

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier

class Variation(vararg variations: Pair<CharacterTile, Double>) : TileTransformModifier<CharacterTile> {

    private var chosenVariation: CharacterTile

    init {
        require(variations.map { it.second }.reduce(Double::plus) <= 1) {
            "Variation chances need to add up to 1.0"
        }
        var currentChance = 0.0
        val rnd = Math.random()
        val result = mutableListOf<CharacterTile>()
        variations.forEach { (tile, chance) ->
            if (rnd > currentChance && rnd <= currentChance + chance) {
                result.add(tile)
            }
            currentChance += chance
        }
        chosenVariation = result.firstOrNull() ?: throw NoSuchElementException("Failed to pick variation for tile.")
    }

    override fun generateCacheKey() = chosenVariation.generateCacheKey()

    override fun transform(tile: CharacterTile): CharacterTile {
        return chosenVariation
    }

    override fun canTransform(tile: Tile) = tile.asCharacterTile().isPresent
}
