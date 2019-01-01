@file:Suppress("MemberVisibilityCanBePrivate")

package org.hexworks.cavesofzircon.builders

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.*
import org.hexworks.cavesofzircon.attributes.flags.BlockOccupier
import org.hexworks.cavesofzircon.attributes.flags.VisionBlocker
import org.hexworks.cavesofzircon.attributes.types.*
import org.hexworks.cavesofzircon.commands.Attack
import org.hexworks.cavesofzircon.commands.Dig
import org.hexworks.cavesofzircon.entities.DepthEffect
import org.hexworks.cavesofzircon.entities.FogOfWar
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.newGameEntityOfType
import org.hexworks.cavesofzircon.systems.*
import org.hexworks.cavesofzircon.world.Game
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.resource.BuiltInGraphicTilesetResource
import kotlin.random.Random


object EntityFactory {

    private val random = Random(654897)

    fun newFogOfWar(game: Game) = FogOfWar(game)

    fun newDepthEffect(game: Game) = DepthEffect(game)

    fun newPlayer() = newGameEntityOfType(Player) {
        attributes(BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.PLAYER),
                Experience(currentLevel = 1,
                        currentXP = 0),
                Hunger(initialValue = 5000,
                        maxValue = 5000),
                Equipment(
                        initialWeapon = newClub(),
                        initialArmor = newJacket()),
                CombatStats.create(
                        maxHp = 100,
                        attackValue = 10,
                        defenseValue = 5),
                EntityActions(Attack::class, Dig::class),
                VisionAttributes(
                        visionRadius = 9),
                Inventory(10),
                ZirconCounter())
        behaviors(PlayerInputHandler, DigestiveSystem)
        facets(CameraMover,
                Movable,
                StairClimber,
                BlockInspector,
                Destroyable,
                ExperienceSystem,
                DigestiveSystem,
                Attackable,
                ZirconGatherer or ItemPicker)
    }


    fun newFungus(): GameEntity<Fungus> {
        val mushrooms = mutableListOf<GameEntity<Mushroom>>()
        repeat(random.nextInt(3)) {
            mushrooms.add(newMushroom())
        }
        return newGameEntityOfType(Fungus) {
            attributes(BlockOccupier,
                    EntityPosition(),
                    EntityTile(GameTileRepository.FUNGUS),
                    FungusSpread(),
                    CombatStats.create(
                            maxHp = 10,
                            attackValue = 0,
                            defenseValue = 0),
                    Inventory(mushrooms.size).apply {
                        mushrooms.forEach {
                            addItem(it)
                        }
                    })
            facets(Attackable, Destroyable)
            behaviors(FungusGrower)
        }

    }

    fun newBat(): GameEntity<Bat> {
        val batMeats = mutableListOf<GameEntity<BatMeat>>()
        repeat(random.nextInt(2)) {
            batMeats.add(newBatMeat())
        }
        return newGameEntityOfType(Bat) {
            attributes(BlockOccupier,
                    EntityPosition(),
                    EntityTile(GameTileRepository.BAT),
                    CombatStats.create(
                            maxHp = 5,
                            attackValue = 2,
                            defenseValue = 1),
                    EntityActions(Attack::class),
                    Inventory(batMeats.size).apply {
                        batMeats.forEach {
                            addItem(it)
                        }
                    })
            facets(Movable, Attackable, Destroyable)
            behaviors(Wanderer)
        }

    }

    fun newZombie() = newGameEntityOfType(Zombie) {
        attributes(BlockOccupier,
                EntityPosition(),
                EntityTile(GameTileRepository.ZOMBIE),
                VisionAttributes(10),
                CombatStats.create(
                        maxHp = 25,
                        attackValue = 8,
                        defenseValue = 4),
                EntityActions(Attack::class),
                Inventory(1))
        facets(Movable, Attackable, Destroyable)
        behaviors(HunterSeeker or Wanderer)
    }

    fun newWall(position: Position3D) = newGameEntityOfType(Wall) {
        attributes(VisionBlocker,
                EntityPosition(position.also {
                    if(it.isUnknown()) {
                        println()
                    }
                }),
                BlockOccupier,
                EntityTile(GameTileRepository.wall()))
        facets(Diggable)
    }

    fun newStairsDown() = newGameEntityOfType(StairsDown) {
        attributes(EntityTile(GameTileRepository.STAIRS_DOWN),
                EntityPosition())
    }

    fun newStairsUp() = newGameEntityOfType(StairsUp) {
        attributes(EntityTile(GameTileRepository.STAIRS_UP),
                EntityPosition())
    }

    fun exit() = newGameEntityOfType(Exit) {
        attributes(EntityTile(GameTileRepository.EXIT),
                EntityPosition())
    }

    fun newCorpseFrom(type: EntityType, items: List<GameEntity<Item>>) = newGameEntityOfType(Corpse(type)) {
        attributes(EntityPosition(),
                EntityTile(GameTileRepository.CORPSE),
                Inventory(items.size).apply {
                    items.forEach {
                        addItem(it)
                    }
                })
    }

    // items

    fun newZircon() = newGameEntityOfType(Zircon) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Zircon")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                EntityTile(GameTileRepository.zircon()))
    }

    fun newMushroom() = newGameEntityOfType(Mushroom) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Violet fungus")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                NutritionalValue(500),
                EntityPosition(),
                EntityTile(GameTileRepository.MUSHROOM))
    }

    fun newBatMeat() = newGameEntityOfType(BatMeat) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Meatball")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                NutritionalValue(750),
                EntityPosition(),
                EntityTile(GameTileRepository.BAT_MEAT))
    }

    fun newDagger() = newGameEntityOfType(Dagger) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Dagger")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.dagger()))
    }

    fun newSword() = newGameEntityOfType(Sword) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Short sword")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 6,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.sword()))
    }

    fun newStaff() = newGameEntityOfType(Staff) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("staff")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        attackValue = 4,
                        defenseValue = 2,
                        combatItemType = "Weapon"),
                EntityTile(GameTileRepository.staff()))
    }

    fun newLightArmor() = newGameEntityOfType(LightArmor) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Leather armor")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 2,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.lightArmor()))
    }

    fun newMediumArmor() = newGameEntityOfType(MediumArmor) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Chain mail")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 3,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.mediumArmor()))
    }

    fun newHeavyArmor() = newGameEntityOfType(HeavyArmor) {
        attributes(ItemIcon(Tiles.newBuilder()
                .withName("Plate mail")
                .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                .buildGraphicTile()),
                EntityPosition(),
                ItemCombatStats(
                        defenseValue = 4,
                        combatItemType = "Armor"),
                EntityTile(GameTileRepository.heavyArmor()))
    }

    fun newRandomWeapon(): GameEntity<Weapon> = when (random.nextInt(3)) {
        0 -> newDagger()
        1 -> newSword()
        else -> newStaff()
    }

    fun newRandomArmor(): GameEntity<Armor> = when (random.nextInt(3)) {
        0 -> newLightArmor()
        1 -> newMediumArmor()
        else -> newHeavyArmor()
    }

    fun newClub() = newGameEntityOfType(Club) {
        attributes(ItemCombatStats(combatItemType = "Weapon"),
                EntityTile(),
                EntityPosition(),
                ItemIcon(Tiles.newBuilder()
                        .withName("Club")
                        .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                        .buildGraphicTile()))
    }

    fun newJacket() = newGameEntityOfType(Jacket) {
        attributes(ItemCombatStats(combatItemType = "Armor"),
                EntityTile(),
                EntityPosition(),
                ItemIcon(Tiles.newBuilder()
                        .withName("Leather jacket")
                        .withTileset(BuiltInGraphicTilesetResource.NETHACK_16X16)
                        .buildGraphicTile()))
    }
}























