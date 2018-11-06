package org.hexworks.cavesofzircon.ai

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.zircon.api.data.Position


interface CreatureAI {

    fun onEnter(position: Position, block: GameBlock): Boolean

    fun onUpdate() {}
}
