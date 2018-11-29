package org.hexworks.cavesofzircon.events

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cobalt.events.api.Event

data class EntityAddedToWorld(val entity: Entity) : Event
