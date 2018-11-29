package org.hexworks.cavesofzircon.components

import org.hexworks.cavesofzircon.attributes.CombatStats
import org.hexworks.cavesofzircon.commands.Attack
import org.hexworks.cavesofzircon.commands.Command
import org.hexworks.cavesofzircon.events.GameEventHappened
import org.hexworks.cavesofzircon.extensions.whenCommandIs
import org.hexworks.cavesofzircon.extensions.whenHasProperty
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.internal.Zircon

object Attackable : BaseComponent() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun executeCommand(command: Command) = command.whenCommandIs<Attack> { cmd ->
        val (context, attacker, target) = cmd
        target.whenHasProperty<CombatStats> { targetStats ->
            attacker.whenHasProperty<CombatStats> { attackerStats ->
                var damage = Math.max(0, attackerStats.attackValue - targetStats.defenseValue)
                damage = (Math.random() * damage).toInt() + 1
                targetStats.hp.value -= damage
                Zircon.eventBus.publish(GameEventHappened("$attacker hits $target for $damage!"))
                if (targetStats.shouldDie()) {
                    logger.info("$target dies.")
                    context.world.removeEntity(target)
                }
            }
        }
    }
}
