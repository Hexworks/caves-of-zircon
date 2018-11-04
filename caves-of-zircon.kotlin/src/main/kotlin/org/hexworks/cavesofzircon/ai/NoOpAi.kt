package org.hexworks.cavesofzircon.ai

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.zircon.api.data.Position

class NoOpAi : CreatureAI {
    override fun onEnter(position: Position, block: GameBlock) = false
}
