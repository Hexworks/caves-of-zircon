package org.hexworks.cavesofzircon.extensions

import org.hexworks.zircon.api.data.impl.Position3D


fun Position3D.sameLevelNeighbors(): List<Position3D> {
    return listOf(
            withRelativeX(-1).withRelativeY(1),
            withRelativeY(1),
            withRelativeX(1).withRelativeY(1),
            withRelativeX(-1),
            withRelativeX(1),
            withRelativeX(-1).withRelativeY(-1),
            withRelativeY(-1),
            withRelativeX(1).withRelativeY(-1)).shuffled()
}
