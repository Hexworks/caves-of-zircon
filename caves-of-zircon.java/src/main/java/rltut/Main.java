package rltut;

import org.hexworks.zircon.api.AppConfigs;
import org.hexworks.zircon.api.CP437TilesetResources;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.SwingApplications;
import org.hexworks.zircon.api.TrueTypeFontResources;
import org.hexworks.zircon.api.grid.TileGrid;
import rltut.views.StartView;
import rltut.views.View;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        int tileSize = 16;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int windowWidth = (int) (screenSize.width / tileSize * 0.6);
        int windowHeight = (int) (screenSize.height / tileSize * 0.6);

        TileGrid tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .enableBetaFeatures()
                .withSize(Sizes.create(windowWidth, windowHeight))
                .withDefaultTileset(CP437TilesetResources.taffer20x20())
                .build());


        new StartView(tileGrid).dock();
    }
}
