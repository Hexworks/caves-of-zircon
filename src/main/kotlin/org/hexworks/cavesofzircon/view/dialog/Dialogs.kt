package org.hexworks.cavesofzircon.view.dialog

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.types.ItemHolder
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.api.screen.Screen

fun openInventoryDialog(context: GameContext) {
    context.screen.openModal(InventoryDialog(context))
}

fun openLootDialog(context: GameContext, looter: GameEntity<ItemHolder>, lootable: GameEntity<ItemHolder>) {
    context.screen.openModal(LootDialog(context, looter, lootable))
}


fun openLevelUpDialog(screen: Screen, player: GameEntity<EntityType>) {
    screen.openModal(LevelUpDialog(screen, player))
}

fun openHelpDialog(screen: Screen) {
    screen.openModal(HelpDialog(screen))
}

fun openEquipmentDialog(context: GameContext) {
    context.screen.openModal(EquipmentDialog(context))
}



















