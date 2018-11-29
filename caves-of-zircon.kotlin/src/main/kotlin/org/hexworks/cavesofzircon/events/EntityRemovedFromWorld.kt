package org.hexworks.cavesofzircon.events

import org.hexworks.cavesofzircon.entities.Entity
import org.hexworks.cobalt.events.api.Event

data class EntityRemovedFromWorld(val entity: Entity) : Event
