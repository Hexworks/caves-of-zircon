package org.hexworks.cavesofzircon.view.dialog

import org.hexworks.cavesofzircon.attributes.types.Corpse
import org.hexworks.cavesofzircon.attributes.types.ItemHolder
import org.hexworks.cavesofzircon.attributes.types.inventory
import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.cavesofzircon.extensions.typeIs
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.kotlin.onMouseReleased

class LootDialog(context: GameContext,
                 looter: GameEntity<ItemHolder>,
                 lootable: GameEntity<ItemHolder>) : Dialog(context.screen) {

    override val container = Components.panel()
            .withTitle("Looting")
            .withSize(42, 15)
            .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox()
            .build().also { modalPanel ->
                checkEmptyCorpse(lootable, context)
                val inventory = looter.inventory
                val lootableInv = lootable.inventory
                var currentSize = 0

                val inventoryPanel = Components.panel()
                        .withSize(Sizes.create(20, 12))
                        .withAlignmentWithin(modalPanel, ComponentAlignment.TOP_LEFT)
                        .withTitle(looter.name)
                        .wrapWithBox()
                        .build()

                inventory.items.forEachIndexed { index, item ->
                    val label = Components.label()
                            .withText(item.name)
                            .withPosition(Positions.create(0, index))
                            .build()
                    inventoryPanel.addComponent(label)
                    currentSize++
                }


                val lootablePanel = Components.panel()
                        .withSize(Sizes.create(20, 12))
                        .withAlignmentWithin(modalPanel, ComponentAlignment.TOP_RIGHT)
                        .withTitle(lootable.name)
                        .wrapWithBox()
                        .build()

                lootableInv.items.forEachIndexed { index, item ->
                    val takeButton = Components.button()
                            .withText("${Symbols.ARROW_LEFT} ${item.name}")
                            .withPosition(Positions.create(0, index))
                            .build()
                    lootablePanel.addComponent(takeButton)
                    takeButton.onMouseReleased {
                        if (inventory.isFull) {
                            logGameEvent("Your inventory is full!")
                        } else {
                            lootablePanel.removeComponent(takeButton)
                            lootableInv.removeItem(item)
                            inventory.addItem(item)
                            val label = Components.label()
                                    .withText(item.name)
                                    .withPosition(Positions.create(0, currentSize))
                                    .build()
                            label.applyColorTheme(GameConfig.THEME)
                            inventoryPanel.addComponent(label)
                            currentSize++
                            checkEmptyCorpse(lootable, context)
                        }
                    }
                }

                modalPanel.addComponent(inventoryPanel)
                modalPanel.addComponent(lootablePanel)
                modalPanel.applyColorTheme(GameConfig.THEME)
            }

    private fun checkEmptyCorpse(lootable: GameEntity<ItemHolder>, context: GameContext) {
        if (lootable.inventory.isEmpty && lootable.typeIs<Corpse>()) {
            context.world.removeEntity(lootable)
        }
    }
}
