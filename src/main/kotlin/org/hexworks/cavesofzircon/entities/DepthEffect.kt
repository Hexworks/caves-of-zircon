package org.hexworks.cavesofzircon.entities

import org.hexworks.amethyst.api.base.BaseEntity
import org.hexworks.cavesofzircon.attributes.types.DepthEffectType
import org.hexworks.cavesofzircon.GameConfig
import org.hexworks.cavesofzircon.events.EntityDiggedOut
import org.hexworks.cavesofzircon.extensions.isVisionBlockedAt
import org.hexworks.cavesofzircon.extensions.isVisionNotBlockedAt
import org.hexworks.cavesofzircon.extensions.isWall
import org.hexworks.cavesofzircon.world.Game
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.modifier.BorderPosition.TOP
import org.hexworks.zircon.internal.Zircon
import java.util.concurrent.ConcurrentHashMap

class DepthEffect(private val game: Game) : BaseEntity<DepthEffectType, GameContext>(DepthEffectType) {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val world = game.world
    private val size = game.worldSize
    private val shadowSize = GameConfig.TILESET.height / 8

    private val depthOverlays = ConcurrentHashMap<Int, Layer>().also { overlays ->
        logger.info("Adding depth effect with shadow (size: $shadowSize).")
        repeat(size.zLength) { level ->
            val overlay = Layers.newBuilder()
                    .withSize(size.to2DSize())
                    .build()
            overlays[level] = overlay
            world.pushOverlayAt(overlay, level)
            size.to2DSize().fetchPositions().forEach { pos ->
                val currentBlockPos = Positions.from2DTo3D(pos, level)
                val blockBelowPos = currentBlockPos.withRelativeY(1)
                if (world.isVisionBlockedAt(currentBlockPos) && world.isVisionNotBlockedAt(blockBelowPos)) {
                    overlay.setTileAt(pos.withRelativeY(1), createShadowTile())
                }
            }
        }
    }

    init {
        Zircon.eventBus.subscribe<EntityDiggedOut> { (position) ->
            val layer = depthOverlays[position.z]
            layer?.setTileAt(position.to2DPosition().withRelativeY(1), Tiles.empty())
            game.world.fetchBlockAt(position.withRelativeY(-1)).map {
                if(it.isWall) {
                    layer?.setTileAt(position.to2DPosition(), createShadowTile())
                }
            }
        }
    }

    private fun createShadowTile() = Tiles.empty()
            .withModifiers(
                    Borders.newBuilder()
                            .withBorderColor(TileColors.create(0, 0, 0, 15))
                            .withBorderWidth(shadowSize * 4)
                            .withBorderPositions(TOP)
                            .build(),
                    Borders.newBuilder()
                            .withBorderColor(TileColors.create(0, 0, 0, 15))
                            .withBorderWidth(shadowSize * 3)
                            .withBorderPositions(TOP)
                            .build(),
                    Borders.newBuilder()
                            .withBorderColor(TileColors.create(0, 0, 0, 15))
                            .withBorderWidth(shadowSize * 2)
                            .withBorderPositions(TOP)
                            .build())
}
