package org.hexworks.cavesofzircon.builders

import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.modifier.Glow
import org.hexworks.zircon.api.modifier.Markov
import org.hexworks.zircon.api.util.markovchain.MarkovChain
import org.hexworks.zircon.api.util.markovchain.MarkovChainNode

object GameTileRepository {

    fun floor(): CharacterTile {
        return variate(FLOOR_VARIATIONS)
    }

    fun wall() = variate(WALL_VARIATIONS)

    private val DEFAULT = Tiles.newBuilder()
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    val UNREVEALED = DEFAULT
            .withCharacter(' ')
            .withBackgroundColor(GameColors.UNREVEALED_COLOR)

    val PLAYER = DEFAULT
            .withCharacter('@')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val FUNGUS = DEFAULT
            .withCharacter('f')
            .withForegroundColor(GameColors.FUNGUS_COLOR)

    val BAT = DEFAULT
            .withCharacter('b')
            .withForegroundColor(GameColors.BAT_COLOR)

    val ZOMBIE = DEFAULT
            .withCharacter('z')
            .withForegroundColor(GameColors.ZOMBIE_COLOR)

    val STAIRS_UP = DEFAULT
            .withCharacter('<')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val STAIRS_DOWN = DEFAULT
            .withCharacter('>')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val EXIT = DEFAULT
            .withCharacter('+')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    val CORPSE = DEFAULT
            .withCharacter('%')
            .withForegroundColor(GameColors.ACCENT_COLOR)

    // items

    val MUSHROOM = DEFAULT
            .withCharacter('m')
            .withForegroundColor(GameColors.MUSHROOM_COLOR)

    val BAT_MEAT = DEFAULT
            .withCharacter('m')
            .withForegroundColor(GameColors.BAT_MEAT_COLOR)

    fun zircon() = DEFAULT
            .withCharacter(',')
            .withForegroundColor(GameColors.ROCK_COLOR)
            .withGlowEffect()


    fun dagger() = DEFAULT
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.WHITE)
            .withGlowEffect()

    fun sword() = DEFAULT
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .withGlowEffect()

    fun staff() = DEFAULT
            .withCharacter('(')
            .withForegroundColor(ANSITileColor.YELLOW)
            .withGlowEffect()

    fun lightArmor() = DEFAULT
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.GREEN)
            .withGlowEffect()

    fun mediumArmor() = DEFAULT
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.WHITE)
            .withGlowEffect()

    fun heavyArmor() = DEFAULT
            .withCharacter('[')
            .withForegroundColor(ANSITileColor.BRIGHT_WHITE)
            .withGlowEffect()


    private

    fun variate(variations: Map<CharacterTile, Double>): CharacterTile {
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

    private fun CharacterTile.withGlowEffect(): CharacterTile {
        val tile = this
        val initialNode = MarkovChainNode.create(tile)
        val glow0 = GameColors.ROCK_COLOR.lightenByPercent(.05)
        val glow1 = GameColors.ROCK_COLOR.lightenByPercent(.1)
        val glow2 = GameColors.ROCK_COLOR.lightenByPercent(.15)

        val glowingNode0 = MarkovChainNode.create(tile
                .withForegroundColor(glow0)
                .withModifiers(Glow(7f)))
        val glowingNode1 = MarkovChainNode.create(tile
                .withForegroundColor(glow1)
                .withModifiers(Glow(9f)))
        val glowingNode2 = MarkovChainNode.create(tile
                .withForegroundColor(glow2)
                .withModifiers(Glow(11f)))

        initialNode.addNext(.008, glowingNode0)
        glowingNode0.addNext(.010, glowingNode1)
        glowingNode1.addNext(.014, glowingNode2)
        glowingNode2.addNext(.020, glowingNode1)
        glowingNode1.addNext(.028, glowingNode0)
        glowingNode0.addNext(.038, initialNode)
        return tile.withAddedModifiers(Markov(MarkovChain.create(initialNode)))
    }


    val EMPTY: CharacterTile = Tiles.empty()

    private val FLOOR_VARIATION_0 = Tiles.newBuilder()
            .withCharacter(Symbols.INTERPUNCT)
            .withForegroundColor(GameColors.FLOOR_FOREGROUND)
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND)
            .buildCharacterTile()

    private val FLOOR_VARIATION_1 = Tiles.newBuilder()
            .withCharacter(Symbols.BULLET_SMALL)
            .withForegroundColor(GameColors.FLOOR_FOREGROUND.lightenByPercent(.1))
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND.lightenByPercent(.1))
            .buildCharacterTile()

    private val FLOOR_VARIATION_2 = Tiles.newBuilder()
            .withCharacter(' ')
            .withBackgroundColor(GameColors.FLOOR_BACKGROUND.darkenByPercent(.1))
            .buildCharacterTile()

    private val FLOOR_VARIATIONS = mapOf(
            FLOOR_VARIATION_0 to .4,
            FLOOR_VARIATION_1 to .3,
            FLOOR_VARIATION_2 to .3)

    private val WALL_VARIATION_0 = Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(GameColors.WALL_FOREGROUND)
            .withBackgroundColor(GameColors.WALL_BACKGROUND)
            .buildCharacterTile()

    private val WALL_VARIATION_1 = Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(GameColors.WALL_FOREGROUND_MOSSY)
            .withBackgroundColor(GameColors.WALL_BACKGROUND_MOSSY)
            .buildCharacterTile()

    private val WALL_VARIATION_2 = Tiles.newBuilder()
            .withCharacter('#')
            .withForegroundColor(GameColors.WALL_FOREGROUND.darkenByPercent(.2))
            .withBackgroundColor(GameColors.WALL_BACKGROUND.darkenByPercent(.2))
            .buildCharacterTile()

    private val WALL_VARIATIONS = mapOf(
            WALL_VARIATION_0 to .4,
            WALL_VARIATION_1 to .5,
            WALL_VARIATION_2 to .1)
}
