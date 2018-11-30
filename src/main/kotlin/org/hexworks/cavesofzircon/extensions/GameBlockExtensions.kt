package org.hexworks.cavesofzircon.extensions

import org.hexworks.cavesofzircon.blocks.GameBlock

fun GameBlock?.isEmptyFloor() = this?.isEmptyFloor() ?: false

fun GameBlock?.isWall() = this?.isWall() ?: false
