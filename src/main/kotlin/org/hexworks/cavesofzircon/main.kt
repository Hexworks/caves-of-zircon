package org.hexworks.cavesofzircon

import org.hexworks.cavesofzircon.view.PlayView
import org.hexworks.cavesofzircon.view.StartView
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.kotlin.onFinished
import org.hexworks.zircon.api.kotlin.transform
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame

@Suppress("ConstantConditionIf")
fun main(args: Array<String>) {

    val application = SwingApplications.startApplication(GameConfig.buildAppConfig())

    val screen = Screens.createScreenFor(application.tileGrid)

    val size = Size.create(80, 50)
    val rex = REXPaintResource.loadREXFile(PlayView::class.java.getResourceAsStream("/rex_files/splash.xp"))
    val img = DrawSurfaces.tileGraphicsBuilder().withSize(size).build()

    rex.toLayerList().forEach {
        img.draw(it)
    }

    val builder = Animations.newBuilder()

    (20 downTo 1).forEach { idx ->
        val repeat = if (idx == 1) 40 else 1
        builder.addFrame(
                DefaultAnimationFrame(
                        size = size,
                        layers = listOf(Layers.newBuilder()
                                .withTileGraphics(img.toTileImage()
                                        .transform { tc ->
                                            tc.withBackgroundColor(tc.backgroundColor
                                                    .darkenByPercent(idx.toDouble().div(20)))
                                                    .withForegroundColor(tc.foregroundColor
                                                            .darkenByPercent(idx.toDouble().div(20)))
                                        }.toTileGraphic())
                                .build()),
                        repeatCount = repeat))
    }

    (0..20).forEach { idx ->
        val repeat = if (idx == 20) 20 else 1
        builder.addFrame(
                DefaultAnimationFrame(
                        size = size,
                        layers = listOf(Layers.newBuilder()
                                .withTileGraphics(img.toTileImage().transform { tc ->
                                    tc.withBackgroundColor(tc.backgroundColor
                                            .darkenByPercent(idx.toDouble().div(20)))
                                            .withForegroundColor(tc.foregroundColor
                                                    .darkenByPercent(idx.toDouble().div(20)))
                                }.toTileGraphic())
                                .build()),
                        repeatCount = repeat))
    }

    val anim = builder
            .withLoopCount(1)
            .setPositionForAll(Positions.zero())
            .build()

    screen.display()

    screen.startAnimation(anim).onFinished {
        screen.close()
        application.dock(StartView())
    }


}
