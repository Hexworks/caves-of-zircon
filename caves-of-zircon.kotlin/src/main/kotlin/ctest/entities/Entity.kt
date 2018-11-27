package ctest.entities

import ctest.components.Component
import ctest.Context
import ctest.commands.Command
import ctest.properties.Property
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
    val properties: List<Property>
    val components: List<Component>

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
