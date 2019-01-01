package org.hexworks.cavesofzircon.events

import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.data.impl.Position3D

data class EntityDiggedOut(val position: Position3D) : Event
