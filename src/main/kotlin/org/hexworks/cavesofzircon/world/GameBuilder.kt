package org.hexworks.cavesofzircon.world

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.Player
import org.hexworks.cavesofzircon.builders.EntityFactory
import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.builders.GameConfig.ARMOR_PER_LEVEL
import org.hexworks.cavesofzircon.builders.GameConfig.BATS_PER_LEVEL
import org.hexworks.cavesofzircon.builders.GameConfig.FUNGI_PER_LEVEL
import org.hexworks.cavesofzircon.builders.GameConfig.ROCKS_PER_LEVEL
import org.hexworks.cavesofzircon.builders.GameConfig.WEAPONS_PER_LEVEL
import org.hexworks.cavesofzircon.builders.GameConfig.ZOMBIES_PER_LEVEL
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.addAtEmptyPosition
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.data.impl.Size3D

class GameBuilder(val worldSize: Size3D) {

    private val worldBuilder = WorldBuilder(worldSize).makeCaves()
    private val visibleSize = Sizes.create3DSize(GameConfig.VISIBLE_WORLD_WIDTH, GameConfig.VISIBLE_WORLD_HEIGHT, 1)
    val world = worldBuilder.build(visibleSize = visibleSize, worldSize = worldSize)

    fun buildGame(): Game {

        prepareWorld()

        val player = addPlayer()
        addBats()
        addFungi()
        addZombies()
        addRocks()
        addWeapons()
        addArmor()
        val game = Game(world = world, player = player)
        world.addWorldEntity(EntityFactory.newDepthEffect(game))
        world.addWorldEntity(EntityFactory.newFogOfWar(game))
        return game
    }

    private fun prepareWorld() = also {
        world.scrollUpBy(world.actualSize().zLength)
    }

    private fun addPlayer(): GameEntity<Player> {
        val player = EntityFactory.newPlayer()
        world.addAtEmptyPosition(player,
                offset = Positions.default3DPosition().withZ(GameConfig.DUNGEON_LEVELS - 1),
                size = world.visibleSize().copy(zLength = 0))
        return player
    }

    private fun addFungi() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(FUNGI_PER_LEVEL) {
                addToWorld(level, EntityFactory.newFungus())
            }
        }
    }

    private fun addBats() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(BATS_PER_LEVEL) {
                addToWorld(level, EntityFactory.newBat())
            }
        }
    }

    private fun addZombies() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(ZOMBIES_PER_LEVEL) {
                addToWorld(level, EntityFactory.newZombie())
            }
        }
    }

    private fun addRocks() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(ROCKS_PER_LEVEL) {
                addToWorld(level, EntityFactory.newZircon())
            }
        }
    }

    private fun addWeapons() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(WEAPONS_PER_LEVEL) {
                addToWorld(level, EntityFactory.newRandomWeapon())
            }
        }
    }

    private fun addArmor() = also {
        repeat(world.actualSize().zLength) { level ->
            repeat(ARMOR_PER_LEVEL) {
                addToWorld(level, EntityFactory.newRandomArmor())
            }
        }
    }

    private fun addToWorld(level: Int, entity: GameEntity<EntityType>) {
        world.addAtEmptyPosition(entity,
                offset = Positions.default3DPosition().withZ(level),
                size = world.actualSize().copy(zLength = 0))
    }
}
