package org.hexworks.cavesofzircon.systems

import org.hexworks.amethyst.api.EntityType
import org.hexworks.amethyst.api.base.BaseSystem
import org.hexworks.cavesofzircon.attributes.Hunger
import org.hexworks.cavesofzircon.attributes.types.nutritionalValue
import org.hexworks.cavesofzircon.commands.Destroy
import org.hexworks.cavesofzircon.commands.Eat
import org.hexworks.cavesofzircon.commands.Exert
import org.hexworks.cavesofzircon.extensions.*
import org.hexworks.cavesofzircon.world.GameContext

object DigestiveSystem : BaseSystem<GameContext>(Hunger::class) {

    override fun executeCommand(command: GameCommand<out EntityType>) = command.whenCommandIs<Eat> { (_, entity, food) ->
        entity.attribute<Hunger>().currentValue += food.nutritionalValue.value
    } or command.whenCommandIs<Exert> { (context, entity, force) ->
        val hunger = entity.attribute<Hunger>()
        hunger.currentValue -= force
        checkStarvation(context, entity, hunger)
    }

    override fun update(entity: GameEntity<out EntityType>, context: GameContext): Boolean {
        val hunger = entity.attribute<Hunger>()
        hunger.currentValue -= 2
        checkStarvation(context, entity, hunger)
        return true
    }

    private fun checkStarvation(context: GameContext, entity: GameEntity<EntityType>, hunger: Hunger) {
        if (hunger.currentValue <= 0) {
            logGameEvent("$entity dies of hunger.")
            entity.executeCommand(Destroy(
                    context = context,
                    source = entity))
        }
    }
}
