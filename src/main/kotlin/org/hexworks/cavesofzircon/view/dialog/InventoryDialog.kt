package org.hexworks.cavesofzircon.view.dialog

import org.hexworks.cavesofzircon.attributes.Inventory
import org.hexworks.cavesofzircon.attributes.types.Food
import org.hexworks.cavesofzircon.commands.DropItem
import org.hexworks.cavesofzircon.commands.Eat
import org.hexworks.cavesofzircon.extensions.findAttribute
import org.hexworks.cavesofzircon.extensions.position
import org.hexworks.cavesofzircon.extensions.whenTypeIs
import org.hexworks.cavesofzircon.view.fragment.ItemInfoFragment
import org.hexworks.cavesofzircon.view.fragment.ItemListFragment
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.onComponentEvent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed

class InventoryDialog(context: GameContext) : Dialog(context.screen) {

    override val container = Components.panel()
            .withTitle("Inventory")
            .withSize(42, 20)
            .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox()
            .build().also { inventoryPanel ->
                val player = context.player
                val playerPosition = player.position
                val inventory = player.findAttribute<Inventory>()
                val itemListFragment = ItemListFragment(inventory, 20)
                inventoryPanel.addFragment(itemListFragment)
                inventoryPanel.addFragment(ItemInfoFragment(
                        selectedItem = itemListFragment.selectedProperty,
                        size = Sizes.create(19, 17)).apply {
                    root.moveRightBy(20)
                })

                val drop = Components.button()
                        .withText("Drop")
                        .withAlignmentWithin(inventoryPanel, ComponentAlignment.BOTTOM_LEFT)
                        .build().apply {
                            onComponentEvent(ComponentEventType.ACTIVATED) {
                                itemListFragment.fetchSelectedItem().map { selectedItem ->
                                    itemListFragment.removeSelectedItem()
                                    player.executeCommand(DropItem(
                                            context = context,
                                            source = player,
                                            item = selectedItem,
                                            position = playerPosition))
                                }
                                Processed
                            }
                        }

                val eat = Components.button()
                        .withText("Eat")
                        .withPosition(Positions.topRightOf(drop).withRelativeX(1))
                        .build().apply {
                            onComponentEvent(ComponentEventType.ACTIVATED) {
                                itemListFragment.fetchSelectedItem().map { selectedItem ->
                                    selectedItem.whenTypeIs<Food> { food ->
                                        itemListFragment.removeSelectedItem()
                                        inventory.removeItem(food)
                                        player.executeCommand(Eat(
                                                context = context,
                                                source = player,
                                                target = food))
                                    }
                                }
                                Processed
                            }
                        }

                inventoryPanel.addComponent(drop)
                inventoryPanel.addComponent(eat)
            }
}
