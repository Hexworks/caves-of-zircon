package org.hexworks.cavesofzircon.view

import org.hexworks.cavesofzircon.Game
import org.hexworks.cavesofzircon.attributes.CombatStats
import org.hexworks.cavesofzircon.blocks.GameBlock
import org.hexworks.cavesofzircon.blocks.GameTile
import org.hexworks.cavesofzircon.builders.EntityFactory
import org.hexworks.cavesofzircon.events.GameEventHappened
import org.hexworks.cavesofzircon.extensions.addAtEmptyPosition
import org.hexworks.cavesofzircon.extensions.property
import org.hexworks.cavesofzircon.world.WorldBuilder
import org.hexworks.cobalt.databinding.api.extensions.onChange
import org.hexworks.cobalt.events.api.subscribe
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.GameComponents
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.ComponentAlignment.*
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.game.ProjectionMode.TOP_DOWN
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.kotlin.onInputOfType
import org.hexworks.zircon.api.kotlin.onKeyStroke
import org.hexworks.zircon.internal.Zircon

class PlayView(tileGrid: TileGrid) : BaseView(tileGrid) {

    private val screenSize = screen.size
    private val sidebarWidth = screenSize.width / 5
    private val statsPanelHeight = screenSize.height / 6
    private val actionsPanelHeight = screenSize.height - statsPanelHeight
    private val logAreaHeight = screenSize.height / 6
    private val gameAreaWidth = screenSize.width - sidebarWidth
    private val gameAreaHeight = screenSize.height - logAreaHeight
    private val worldSize = Sizes.create3DSize(screenSize.width * 2, screenSize.height * 2, 10)
    private val visibleSize = Sizes.create3DSize(gameAreaWidth, gameAreaHeight, 1)
    private var player = EntityFactory.newPlayer()
    private val world = WorldBuilder(worldSize)
            .makeCaves()
            .build(visibleSize, worldSize)
    private val game = Game(screen, world)
    private val playerStats = player.property<CombatStats>()
    private val playerHp: TextArea

    init {
        screen.onInputOfType(InputType.Enter) {
            WinView(tileGrid).dock()
        }
        screen.onInputOfType(InputType.Escape) {
            LoseView(tileGrid).dock()
        }
        screen.onKeyStroke {
            game.addInput(it)
        }
        prepareWorld()

        val statsPanel = Components.panel()
                .withSize(Sizes.create(sidebarWidth, statsPanelHeight))
                .withTitle("Stats")
                .withAlignmentWithin(screen, TOP_LEFT)
                .wrapWithBox()
                .build()
        playerHp = Components.textArea()
                .withSize(Sizes.create(statsPanel.contentSize.width, 1))
                .build()
        playerHp.disable()
        updateHp()
        playerStats.hp.onChange {
            updateHp()
        }
        statsPanel.addComponent(playerHp)
        val actionsPanel = Components.panel()
                .withSize(Sizes.create(sidebarWidth, actionsPanelHeight))
                .withTitle("Actions")
                .withAlignmentWithin(screen, BOTTOM_LEFT)
                .wrapWithBox()
                .build()
        val logPanel = Components.panel()
                .withSize(Sizes.create(gameAreaWidth, logAreaHeight))
                .withAlignmentWithin(screen, BOTTOM_RIGHT)
                .wrapWithBox()
                .withTitle("Game log")
                .build()
        val logArea = Components.logArea()
                .withSize(logPanel.size - Sizes.create(2, 2))
                .build()
        logPanel.addComponent(logArea)
        val gameComponent = GameComponents.newGameComponentBuilder<GameTile, GameBlock>()
                .withGameArea(world)
                .withVisibleSize(visibleSize)
                .withProjectionMode(TOP_DOWN)
                .withAlignmentWithin(screen, TOP_RIGHT)
                .build()
        Zircon.eventBus.subscribe<GameEventHappened> { (text) ->
            logArea.addParagraph(text, withNewLine = false, withTypingEffectSpeedInMs = 20)
        }

        screen.addComponent(statsPanel)
        screen.addComponent(actionsPanel)
        screen.addComponent(logPanel)
        screen.addComponent(gameComponent)
    }

    private fun updateHp() {
        playerHp.text = "HP: ${playerStats.hp.value}/${playerStats.maxHp}"
    }

    private fun prepareWorld() {
        world.addEntity(player, world.findEmptyLocation().get())
        repeat(10) {
            val fungus = EntityFactory.newFungus()
            world.addAtEmptyPosition(fungus)
        }
    }
}
