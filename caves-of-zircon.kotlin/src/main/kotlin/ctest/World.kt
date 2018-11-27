package ctest

import ctest.entities.Entity
import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.zircon.api.data.impl.Position3D

class World {

    private val entities = mutableMapOf<Position3D, MutableList<Entity>>()
    private val entityPositionLookup = mutableMapOf<Identifier, Position3D>()

    fun moveEntity(entity: Entity, position3D: Position3D) {
        entityPositionLookup[entity.id]?.let { pos ->
            entities[pos]?.remove(entity)
        }
        addEntity(entity, position3D)
    }

    fun addEntity(entity: Entity, position3D: Position3D) {
        entities.getOrPut(position3D) { mutableListOf() }.add(entity)
    }

    fun fetchEntities(position3D: Position3D): List<Entity> {
        return entities.getOrElse(position3D) { listOf() }
    }
}
