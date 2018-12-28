package org.hexworks.cavesofzircon.extensions

import org.hexworks.zircon.api.color.TileColor

fun TileColor.lightenByPercent(percentage: Double): TileColor {
    require(percentage in 0.0..1.0) {
        "The given percentage ($percentage) is not between the required range (0 - 1)."
    }
    return TileColor.create(
            red = (red * (1f + percentage)).toInt(),
            green = (green * (1f + percentage)).toInt(),
            blue = (blue * (1f + percentage)).toInt(),
            alpha = alpha)
}
