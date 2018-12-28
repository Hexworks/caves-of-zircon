package org.hexworks.cavesofzircon.extensions

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.impl.Position3D
import kotlin.math.abs

fun Position3D.sameLevelNeighbors(): List<Position3D> {
    return (-1..1).flatMap { x ->
        (-1..1).map { y ->
            withRelativeX(x).withRelativeY(y)
        }
    }.minus(this).shuffled()
}

fun Position3D.isOnSameLevelAs(other: Position3D) = z == other.z

fun Position3D.isNotOnSameLevelAs(other: Position3D) = isOnSameLevelAs(other).not()

fun Position.distanceTo(other: Position): Int {
    return abs(x - other.x) + abs(y - other.y)
}
