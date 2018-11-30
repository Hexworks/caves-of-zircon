package org.hexworks.cavesofzircon.builders

import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols

object GameTileRepository {

    fun floor(): GameTile {
        return GameTile(variate(FLOOR_VARIATIONS))
    }

    fun wall() = GameTile(variate(WALL_VARIATIONS))

    val PLAYER = GameTile(Tiles.newBuilder()
            .withCharacter('@')
            .withForegroundColor(GameColors.PLAYER_COLOR)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile())

    val FUNGUS = GameTile(Tiles.newBuilder()
            .withCharacter('f')
            .withForegroundColor(GameColors.FUNGUS_COLOR)
            .withBackgroundColor(TileColor.transparent())
            .buildCharacterTile())

    val STAIRS_UP = GameTile(Tiles.newBuilder()
            .withCharacter('<')
            .withForegroundColor(ANSITileColor.WHITE)
            .buildCharacterTile())

    val STAIRS_DOWN = GameTile(Tiles.newBuilder()
            .withCharacter('>')
            .withForegroundColor(ANSITileColor.WHITE)
            .buildCharacterTile())

    private fun variate(variations: Map<CharacterTile, Double>): CharacterTile {
        require(variations.map { it.value }.reduce(Double::plus) <= 1) {
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
        return result.firstOrNull() ?: throw NoSuchElementException("Failed to pick variation for tile.")
    }


    val EMPTY: GameTile = GameTile(Tiles.empty())

    // FLOOR

    private val CAVE_FLOOR_COLOR = TileColor.create(46, 28, 16)
    private val CAVE_DEBRIS_COLOR = TileColor.create(66, 48, 36)

    private val CAVE_WALL_COLOR = TileColor.create(96, 96, 96)
    private val CAVE_WALL_DEBRIS_COLOR = TileColor.create(65, 65, 65)

    private val FLOOR_VARIATION_0 = Tiles.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(CAVE_WALL_DEBRIS_COLOR)
            .withBackgroundColor(CAVE_FLOOR_COLOR)
            .buildCharacterTile()

    private val FLOOR_VARIATION_1 = Tiles.newBuilder()
            .withCharacter(Symbols.CIRCUMFLEX)
            .withForegroundColor(CAVE_WALL_DEBRIS_COLOR)
            .withBackgroundColor(CAVE_FLOOR_COLOR)
            .buildCharacterTile()

    private val FLOOR_VARIATION_2 = Tiles.newBuilder()
            .withCharacter(Symbols.BLOCK_SPARSE)
            .withForegroundColor(CAVE_WALL_DEBRIS_COLOR)
            .withBackgroundColor(CAVE_FLOOR_COLOR)
            .buildCharacterTile()

    private val FLOOR_VARIATIONS = mapOf(
            FLOOR_VARIATION_0 to .8,
            FLOOR_VARIATION_1 to .15,
            FLOOR_VARIATION_2 to .05)

    private val WALL_VARIATION_0 = Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(CAVE_WALL_DEBRIS_COLOR)
            .withBackgroundColor(CAVE_WALL_COLOR)
            .buildCharacterTile()

    private val WALL_VARIATION_1 = Tiles.newBuilder()
            .withCharacter(Symbols.PHI_UPPERCASE)
            .withForegroundColor(CAVE_WALL_DEBRIS_COLOR)
            .withBackgroundColor(CAVE_WALL_COLOR)
            .buildCharacterTile()

    private val WALL_VARIATION_2 = Tiles.newBuilder()
            .withCharacter(Symbols.THETA)
            .withForegroundColor(CAVE_WALL_DEBRIS_COLOR)
            .withBackgroundColor(CAVE_WALL_COLOR)
            .buildCharacterTile()

    private val WALL_VARIATIONS = mapOf(
            WALL_VARIATION_0 to .7,
            WALL_VARIATION_1 to .2,
            WALL_VARIATION_2 to .1)
}
