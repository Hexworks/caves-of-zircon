package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.amethyst.api.base.BaseEntityType
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.ZirconCounter
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute

object Player : BaseEntityType(
        name = "player"), ItemHolder, ExperienceGainer

val GameEntity<Player>.zirconCounter: ZirconCounter
    get() = attribute()

object StairsDown : BaseEntityType(
        name = "stairs down")

object StairsUp : BaseEntityType(
        name = "stairs up")

object Exit : BaseEntityType(
        name = "exit")

object Wall : BaseEntityType()

object Bat : BaseEntityType(
        name = "bat")

object Fungus : BaseEntityType(
        name = "fungus")

object Zombie : BaseEntityType(
        name = "zombie")

data class Corpse(private val of: EntityType) : BaseEntityType(
        name = "corpse of ${of.name}")

// items

object Mushroom : BaseEntityType(
        name = "Mushroom",
        description = "Some weird looking mushrooms. Maybe it is edible?"), Food

object BatMeat : BaseEntityType(
        name = "Bat meat",
        description = "Stringy bat meat. It is edible, but not tasty."), Food

object Zircon : BaseEntityType(
        name = "Zircon",
        description = "A small piece of Zircon. Its value is unfathomable."), Item

// weapons

object Dagger : BaseEntityType(
        name = "Rusty Dagger",
        description = "A small, rusty dagger made of some metal alloy."), Weapon

object Sword : BaseEntityType(
        name = "Iron Sword",
        description = "A shiny sword made of iron. It is a two-hand weapon"), Weapon

object Staff : BaseEntityType(
        name = "Wooden Staff",
        description = "A wooden staff made of birch. It has seen some use"), Weapon

// armor

object LightArmor : BaseEntityType(
        name = "Leather Tunic",
        description = "A tunic made of rugged leather. It is very comfortable."), Armor

object MediumArmor : BaseEntityType(
        name = "Chainmail",
        description = "A sturdy chainmail armor made of interlocking iron chains."), Armor

object HeavyArmor : BaseEntityType(
        name = "Platemail",
        description = "A heavy and shiny platemail armor made of bronze."), Armor

// built-in items

object Fangs : BaseEntityType(
        name = "fangs",
        description = "Bloody fangs"), Weapon

object Skin : BaseEntityType(
        name = "skin",
        description = "Skin."), Armor

object Club : BaseEntityType(
        name = "Club",
        description = "A wooden club. It doesn't give you an edge over your opponent (haha)."), Weapon

object Jacket : BaseEntityType(
        name = "Leather jacket",
        description = "Dirty and rugged jacket made of leather."), Armor

object NoItem : BaseEntityType(
        name = "Select an item",
        description = "Select an item"), Item


object FogOfWarType : BaseEntityType()

object DepthEffectType : BaseEntityType()
