package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cavesofzircon.events.EntityAddedToWorld
import org.hexworks.cavesofzircon.events.EntityRemovedFromWorld
import org.hexworks.cavesofzircon.world.Context
import org.hexworks.cavesofzircon.world.World
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.Zircon
import java.util.concurrent.ConcurrentHashMap

class Game(private val screen: Screen,
           private val world: World) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val entities = ConcurrentHashMap<Identifier, Entity>()

    init {
        Zircon.eventBus.subscribe<EntityAddedToWorld> { (entity) ->
            entities[entity.id] = entity
        }
        Zircon.eventBus.subscribe<EntityRemovedFromWorld> { (entity) ->
            entities.remove(entity.id)
        }
    }

    fun addInput(input: Input) {
        entities.forEach { (_, entity) ->
            try {
                entity.update(Context(
                        world = world,
                        screen = screen,
                        input = input,
                        entity = entity,
                        entityPosition = world.findPositionOfEntity(entity).get()))
            } catch (e: Exception) {
                logger.error("Failed to update entity", e)
            }
        }
    }
}
