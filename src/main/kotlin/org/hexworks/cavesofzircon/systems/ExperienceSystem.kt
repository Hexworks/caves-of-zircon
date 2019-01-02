package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.Consumed
import org.hexworks.amethyst.api.Pass
import org.hexworks.amethyst.api.Response
import org.hexworks.amethyst.api.base.BaseFacet
import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.combatStats
import org.hexworks.cavesofzircon.attributes.types.experience
import org.hexworks.cavesofzircon.commands.GainXpFrom
import org.hexworks.cavesofzircon.events.PlayerGainedLevel
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.isPlayer
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.internal.Zircon
import kotlin.math.min


object ExperienceSystem : BaseFacet<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<GainXpFrom> { (_, source, gainer) ->
        val sourceStats = source.combatStats
        val gainerXp = gainer.experience
        val gainerStats = gainer.combatStats
        val amount = (sourceStats.maxHp + sourceStats.attackValue + sourceStats.defenseValue) - gainerXp.currentLevel * 2
        var response: Response = Pass
        if (amount > 0) {
            response = Consumed
            gainerXp.currentXP += amount
            while (gainerXp.currentXP > Math.pow(gainerXp.currentLevel.toDouble(), 1.5) * 20) {
                gainerXp.currentLevel++
                logGameEvent("$gainer advanced to level ${gainerXp.currentLevel}.")
                if (gainer.isPlayer) {
                    Zircon.eventBus.publish(PlayerGainedLevel(gainer))
                    val addedHp = gainerXp.currentLevel * 2
                    gainerStats.hpProperty.value = min(gainerStats.hp + addedHp, gainerStats.maxHp)
                }
            }
        }
        response
    }
}
