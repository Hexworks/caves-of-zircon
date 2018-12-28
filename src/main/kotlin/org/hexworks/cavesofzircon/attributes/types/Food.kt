package org.hexworks.cavesofzircon.attributes.types

import org.hexworks.cavesofzircon.attributes.NutritionalValue
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.attribute

interface Food : Item

val GameEntity<Food>.nutritionalValue: NutritionalValue
    get() = attribute()
