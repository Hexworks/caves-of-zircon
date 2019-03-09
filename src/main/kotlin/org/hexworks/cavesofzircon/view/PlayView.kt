package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.GameConfig
import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.events.GameLogEvent
import org.hexworks.cavesofzircon.events.PlayerDied
import org.hexworks.cavesofzircon.events.PlayerGainedLevel
import org.hexworks.cavesofzircon.events.PlayerWonTheGame
import org.hexworks.cavesofzircon.view.dialog.openLevelUpDialog
import org.hexworks.cavesofzircon.view.fragment.CavesOfZirconHeader
import org.hexworks.cavesofzircon.view.fragment.PlayerStats
import org.hexworks.cavesofzircon.world.Game
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.component.ComponentAlignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.component.ComponentAlignment.TOP_RIGHT
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.onKeyboardEvent
import org.hexworks.zircon.api.game.ProjectionMode.TOP_DOWN
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.Zircon

class PlayView(private val game: Game) : BaseView() {

    private val logger = LoggerFactory.getLogger(javaClass)

    override val theme = GameConfig.THEME

    override fun onDock() {
        val player = game.player

        screen.onKeyboardEvent(KeyboardEventType.KEY_PRESSED) { event, _ ->
            logger.info("Key released, updating world: $event")
            game.world.update(screen, event, game)
            Processed
        }
        val sidebar = Components.panel()
                .withSize(GameConfig.SIDEBAR_WIDTH, GameConfig.WINDOW_HEIGHT)
                .build()
        val header = CavesOfZirconHeader()
        sidebar.addFragment(header)
        sidebar.addFragment(PlayerStats(player).apply {
            root.moveDownBy(header.root.height)
        })

        val logArea = Components.logArea()
                .withSize(GameConfig.WINDOW_WIDTH - GameConfig.SIDEBAR_WIDTH, GameConfig.LOG_AREA_HEIGHT)
                .withAlignmentWithin(screen, BOTTOM_RIGHT)
                .wrapWithBox()
                .withTitle("Log")
                .build()

        val gameComponent = GameComponents.newGameComponentBuilder<Tile, GameBlock>()
                .withGameArea(game.world)
                .withVisibleSize(game.visibleSize)
                .withProjectionMode(TOP_DOWN)
                .withAlignmentWithin(screen, TOP_RIGHT)
                .build()

        Zircon.eventBus.subscribe<PlayerGainedLevel> { (player) ->
            openLevelUpDialog(screen, player)
        }
        Zircon.eventBus.subscribe<GameLogEvent> { (text) ->
            logArea.addParagraph(text, withNewLine = false, withTypingEffectSpeedInMs = 20)
        }
        Zircon.eventBus.subscribe<PlayerDied> {
            replaceWith(LoseView(it.cause))
            close()
        }
        Zircon.eventBus.subscribe<PlayerWonTheGame> {
            replaceWith(WinView(it.zircons))
            close()
        }

        screen.addComponent(sidebar)
        screen.addComponent(logArea)
        screen.addComponent(gameComponent)
    }
}
