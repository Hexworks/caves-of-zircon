package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.events.GameLogEvent
import org.hexworks.cavesofzircon.events.PlayerDied
import org.hexworks.cavesofzircon.events.PlayerGainedLevel
import org.hexworks.cavesofzircon.events.PlayerWonTheGame
import org.hexworks.cavesofzircon.view.dialog.openLevelUpDialog
import org.hexworks.cavesofzircon.view.fragment.CavesOfZirconHeader
import org.hexworks.cavesofzircon.view.fragment.PlayerStats
import org.hexworks.cavesofzircon.world.Game
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_RIGHT
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.game.ProjectionMode.TOP_DOWN
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.kotlin.onKeyStroke
import org.hexworks.zircon.internal.Zircon

class PlayView(tileGrid: TileGrid, private val game: Game) : BaseView(tileGrid) {

    init {
        val player = game.player
        Zircon.eventBus.subscribe<PlayerGainedLevel> { (player) ->
            openLevelUpDialog(screen, player)
        }
        screen.onKeyStroke {
            game.world.update(screen, it, game)
        }
        val sidebar = Components.panel()
                .withSize(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT)
                .build()
        val header = CavesOfZirconHeader()
        sidebar.addFragment(header)
        sidebar.addFragment(PlayerStats(player).apply {
            root.moveDownBy(header.root.height)
        })


        val logPanel = Components.panel()
                .withSize(GameConfig.WINDOW_WIDTH - GameConfig.SIDEBAR_WIDTH, GameConfig.LOG_AREA_HEIGHT)
                .withAlignmentWithin(screen, BOTTOM_RIGHT)
                .wrapWithBox()
                .withTitle("Log")
                .build().also { parent ->
                    val logArea = Components.logArea()
                            .withSize(parent.size - Sizes.create(2, 2))
                            .build()
                    parent.addComponent(logArea)
                    Zircon.eventBus.subscribe<GameLogEvent> { (text) ->
                        logArea.addParagraph(text, withNewLine = false, withTypingEffectSpeedInMs = 20)
                    }
                }
        val gameComponent = GameComponents.newGameComponentBuilder<Tile, GameBlock>()
                .withGameArea(game.world)
                .withVisibleSize(game.visibleSize)
                .withProjectionMode(TOP_DOWN)
                .withAlignmentWithin(screen, TOP_RIGHT)
                .build()
        Zircon.eventBus.subscribe<PlayerDied> {
            LoseView(tileGrid).dock()
        }
        Zircon.eventBus.subscribe<PlayerWonTheGame> {
            WinView(tileGrid, it.zircons).dock()
        }
        screen.addComponent(sidebar)
        screen.addComponent(logPanel)
        screen.addComponent(gameComponent)
    }
}
