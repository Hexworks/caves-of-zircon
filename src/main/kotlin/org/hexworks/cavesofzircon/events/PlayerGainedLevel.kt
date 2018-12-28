package org.hexworks.cavesofzircon.events

import org.hexworks.cavesofzircon.attributes.types.ExperienceGainer
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cobalt.events.api.Event

data class PlayerGainedLevel(val player: GameEntity<ExperienceGainer>) : Event
