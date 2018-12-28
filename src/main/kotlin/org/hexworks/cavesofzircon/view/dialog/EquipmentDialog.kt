package org.hexworks.cavesofzircon.view.dialog

import org.hexworks.cavesofzircon.attributes.types.Armor
import org.hexworks.cavesofzircon.attributes.types.Item
import org.hexworks.cavesofzircon.attributes.types.Weapon
import org.hexworks.cavesofzircon.attributes.types.inventory
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.equipment
import org.hexworks.cavesofzircon.extensions.filterType
import org.hexworks.cavesofzircon.world.GameContext
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.kotlin.onMouseReleased

class EquipmentDialog(context: GameContext) : Dialog(context.screen) {

    override val container = Components.panel()
            .withTitle("Equip items")
            .withSize(42, 20)
            .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox()
            .build().also { equipmentPanel ->

                val player = context.player
                val inventory = player.inventory
                val equipment = player.equipment

                val weaponHeader = Components.header()
                        .withText("Weapons")
                val armorHeader = Components.header()
                        .withText("Armor")

                val weapons = inventory.items.filterType<Weapon>().toMutableList()
                val armors = inventory.items.filterType<Armor>().toMutableList()

                weapons.forEachIndexed { idx, item ->
                    buildButtonFor(idx + 2, item).apply {
                        equipmentPanel.addComponent(this)
                        onMouseReleased {
                            val newWeapon = weapons[idx]
                            val oldWeapon = equipment.equipWeapon(inventory, newWeapon)
                            weapons[idx] = oldWeapon
                            textProperty.value = "${oldWeapon.name} ${Symbols.GUILLEMET_RIGHT}"
                        }
                    }
                }

                armorHeader.withPosition(0, weapons.size + 3)

                armors.forEachIndexed { idx, item ->
                    buildButtonFor(idx + weapons.size + 5, item).apply {
                        equipmentPanel.addComponent(this)
                        onMouseReleased {
                            val newArmor = armors[idx]
                            val oldArmor = equipment.equipArmor(inventory, newArmor)
                            armors[idx] = oldArmor
                            textProperty.value = "${oldArmor.name} ${Symbols.GUILLEMET_RIGHT}"
                        }
                    }
                }

                equipmentPanel.addComponent(weaponHeader)
                equipmentPanel.addComponent(armorHeader)
                equipmentPanel.addComponent(equipment.toComponent(20).apply {
                    moveTo(Positions.create(20, 0))
                })
            }

    private fun buildButtonFor(idx: Int, item: GameEntity<Item>): Button {
        return Components.button()
                .wrapSides(false)
                .withText("${item.name} ${Symbols.GUILLEMET_RIGHT}")
                .withPosition(0, idx)
                .build()
    }
}
