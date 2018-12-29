package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.attributes.types.ExperienceGainer
import org.hexworks.cavesofzircon.attributes.types.combatStats
import org.hexworks.cavesofzircon.attributes.types.equipment
import org.hexworks.cavesofzircon.commands.Attack
import org.hexworks.cavesofzircon.commands.Destroy
import org.hexworks.cavesofzircon.commands.GainXpFrom
import org.hexworks.cavesofzircon.extensions.GameCommand
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.extensions.whenTypeIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.fold

object Attackable : BaseSystem<GameContext>() {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<Attack> { (context, attacker, target) ->
        val attackerEquipment = attacker.equipment
        val attackerStats = attacker.combatStats

        val targetEquipment = target.equipment
        val targetStats = target.combatStats

        val attackValue = attackerStats.attackValue + attackerEquipment.fold(
                whenEmpty = { 0 },
                whenPresent = { it.attackValue })

        val defenseValue = targetStats.defenseValue - targetEquipment.fold(
                whenEmpty = { 0 },
                whenPresent = { it.defenseValue })

        val damage = Math.max(0, attackValue - defenseValue)
        val finalDamage = (Math.random() * damage).toInt() + 1
        targetStats.hpProperty.value -= finalDamage
        if (finalDamage == 0) {
            val armor = targetEquipment.fold(whenPresent = { it.armorName }, whenEmpty = { "<unknown>" })
            logGameEvent("The $attacker hits the $target but the $armor blocks the attack!")
        } else {
            logGameEvent("The $attacker hits the $target for $finalDamage!")
        }
        targetStats.whenShouldBeDestroyed {
            logGameEvent("The $target dies.")
            target.executeCommand(Destroy(context, target))
            attacker.whenTypeIs<ExperienceGainer> { gainer ->
                attacker.executeCommand(GainXpFrom(
                        context = context,
                        source = target,
                        gainer = gainer))
            }
        }
    }
}