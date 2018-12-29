package org.hexworks.cavesofzircon.extensions

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.VisionAttributes
import org.hexworks.cavesofzircon.attributes.types.Item
import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.world.World
import org.hexworks.cobalt.datatypes.extensions.fold
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.shape.EllipseFactory
import org.hexworks.zircon.api.shape.LineFactory

fun World.addAtEmptyPosition(entity: GameEntity<EntityType>,
                             offset: Position3D = Positions.default3DPosition(),
                             size: Size3D = actualSize()): Boolean {
    return findEmptyLocationWithin(offset, size).fold(
            whenEmpty = {
                false
            },
            whenPresent = { location ->
                addEntity(entity, location)
                true
            })

}

fun World.whenHasBlockAt(pos: Position3D, fn: (GameBlock) -> Unit) {
    fetchBlockAt(pos).map(fn)
}

fun World.isVisionBlockedAt(pos: Position3D): Boolean {
    return fetchBlockAt(pos).fold(whenEmpty = { false }, whenPresent = {
        it.entities.any(GameEntity<EntityType>::blocksVision)
    })
}

fun World.isVisionNotBlockedAt(pos: Position3D) = isVisionBlockedAt(pos).not()

fun World.findVisiblePositionsFor(entity: GameEntity<EntityType>): Iterable<Position> {
    val result = mutableSetOf<Position>()
    val centerPos = entity.position.to2DPosition()
    entity.whenHasAttribute<VisionAttributes> { (radius) ->
        EllipseFactory.buildEllipse(centerPos, centerPos.withRelativeX(radius).withRelativeY(radius))
                .positions().forEach { ringPos ->
                    val iter = LineFactory.buildLine(centerPos, ringPos).iterator()
                    do {
                        val next = iter.next()
                        result.add(next)
                    } while (iter.hasNext() && isVisionNotBlockedAt(Position3D.from2DPosition(next, entity.position.z)))
                }
    }
    return result
}

fun World.whenCanSee(looker: GameEntity<EntityType>, target: GameEntity<EntityType>, fn: (path: List<Position>) -> Unit) {
    looker.whenHasAttribute<VisionAttributes> { (visionRange) ->
        if (looker.hasPosition() && target.hasPosition()) {
            val lookerPos = looker.position
            val targetPos = target.position
            val z = lookerPos.z
            if (lookerPos.isNotOnSameLevelAs(targetPos)) {
                return@whenHasAttribute
            }
            if (lookerPos.to2DPosition().distanceTo(targetPos.to2DPosition()) >= visionRange) {
                return@whenHasAttribute
            }
            val path = LineFactory.buildLine(lookerPos.to2DPosition(), targetPos.to2DPosition())
            if (path.none { isVisionBlockedAt(Positions.from2DTo3D(it, z)) }) {
                fn(path.positions().toList().drop(1))
            }
        }
    }
}

fun World.fetchEntitiesAt(pos: Position3D): List<GameEntity<EntityType>> {
    return fetchBlockAt(pos).fold(whenEmpty = { listOf() }, whenPresent = {
        it.entities.toList()
    })
}

fun World.findItemsAt(pos: Position3D): List<GameEntity<Item>> {
    return fetchEntitiesAt(pos).filterType()
}
