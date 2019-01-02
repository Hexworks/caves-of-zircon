package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.view.StartView
import org.hexworks.zircon.api.SwingApplications

@Suppress("ConstantConditionIf")
fun main(args: Array<String>) {

    val application = SwingApplications.startApplication(GameConfig.buildAppConfig())

    application.dock(StartView())

}
