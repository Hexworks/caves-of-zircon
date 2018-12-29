package org.hexworks.cavesofzircon.commands

import org.hexworks.cavesofzircon.attributes.types.Combatant
import org.hexworks.cavesofzircon.attributes.types.ExperienceGainer
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext

/**
 * A [GameCommand] representing [gainer] gaining xp from [source].
 */
data class GainXpFrom(override val context: GameContext,
                      override val source: GameEntity<Combatant>,
                      val gainer: GameEntity<ExperienceGainer>) : GameCommand<Combatant> {

    override fun toString() = "$gainer gains xp from $source."
}
