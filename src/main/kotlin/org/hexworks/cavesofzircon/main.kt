package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.builders.GameConfig
import org.hexworks.cavesofzircon.view.StartView
import org.hexworks.zircon.api.SwingApplications

@Suppress("ConstantConditionIf")
fun main(args: Array<String>) {

    StartView(SwingApplications.startTileGrid(GameConfig.buildAppConfig())).dock()

}
