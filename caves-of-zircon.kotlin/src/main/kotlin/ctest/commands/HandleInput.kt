package ctest.commands

import ctest.Context
import ctest.entities.Entity
import org.hexworks.zircon.api.input.Input

data class HandleInput(override val context: Context,
                       override val source: Entity,
                       val input: Input) : Command
