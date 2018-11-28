package org.hexworks.cavesofzircon.entities

import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.components.Component
import org.hexworks.cavesofzircon.properties.Property
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cobalt.datatypes.Identifier

/**
 * An [Entity] is a game object composed of [Property] and
 * [Component] objects representing a cohesive whole.
 * For example a Goblin entity can be composed of
 * `CombatHandler`, `ArmorUser`, `HunterSeeker` components
 * with a `Creature` property.
 */
interface Entity {

    val id: Identifier

    fun <T : Property> property(klass: Class<T>): T

    fun <T : Component> component(klass: Class<T>): T

    /**
     * Adds the given [Command] to this [Entity] for processing.
     * It will be processed when the [Entity] is updated next.
     */
    fun sendCommand(command: Command)

    /**
     * Makes this [Entity] immediately process this [Command].
     * @return true if the [Command] was processed by a [Component] false if not.
     */
    fun executeCommand(command: Command): Boolean

    /**
     * Updates this [Entity] using the given [Context].
     */
    fun update(context: Context)

}
