package org.hexworks.cavesofzircon.tiles

import org.hexworks.zircon.api.data.CharacterTile

class GameTile(private val characterTile: CharacterTile) : CharacterTile by characterTile
