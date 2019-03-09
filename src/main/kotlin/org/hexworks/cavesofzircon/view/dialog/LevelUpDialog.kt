package org.hexworks.cavesofzircon.view.dialog

import org.hexworks.amethyst.api.entity.EntityType
import org.hexworks.cavesofzircon.attributes.CombatStats
import org.hexworks.cavesofzircon.attributes.VisionAttributes
import org.hexworks.cavesofzircon.extensions.GameEntity
import org.hexworks.cavesofzircon.extensions.findAttribute
import org.hexworks.cavesofzircon.extensions.logGameEvent
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.extensions.onComponentEvent
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

class LevelUpDialog(screen: Screen, player: GameEntity<EntityType>) : Dialog(screen, false) {

    override val container = Components.panel()
            .withTitle("Ding!")
            .withSize(30, 15)
            .withBoxType(BoxType.TOP_BOTTOM_DOUBLE)
            .wrapWithBox()
            .build().also { modalPanel ->
                modalPanel.addComponent(Components.textBox()
                        .withPosition(1, 1)
                        .withContentWidth(27)
                        .addHeader("Congratulations, you levelled up!")
                        .addParagraph("Pick an improvement from the options below:"))

                val stats = player.findAttribute<CombatStats>()
                val vision = player.findAttribute<VisionAttributes>()

                modalPanel.addComponent(Components.button()
                        .withText("Max HP")
                        .withPosition(1, 7)
                        .build().apply {
                            onComponentEvent(ComponentEventType.ACTIVATED) {
                                stats.maxHpProperty.value += 10
                                logGameEvent("You look healthier.")
                                root.close(EmptyModalResult)
                                Processed
                            }
                        })


                modalPanel.addComponent(Components.button()
                        .withText("Attack")
                        .withPosition(1, 8)
                        .build().apply {
                            onComponentEvent(ComponentEventType.ACTIVATED) {
                                stats.attackValueProperty.value += 2
                                logGameEvent("You look stronger.")
                                root.close(EmptyModalResult)
                                Processed
                            }
                        })

                modalPanel.addComponent(Components.button()
                        .withText("Defense")
                        .withPosition(1, 9)
                        .build().apply {
                            onComponentEvent(ComponentEventType.ACTIVATED) {
                                stats.defenseValueProperty.value += 2
                                logGameEvent("You look tougher.")
                                root.close(EmptyModalResult)
                                Processed
                            }
                        })

                modalPanel.addComponent(Components.button()
                        .withText("Vision")
                        .withPosition(1, 10)
                        .build().apply {
                            onComponentEvent(ComponentEventType.ACTIVATED) {
                                vision.visionRadius++
                                logGameEvent("You look more aware.")
                                root.close(EmptyModalResult)
                                Processed
                            }
                        })
            }
}
