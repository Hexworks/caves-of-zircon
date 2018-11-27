package rltut.views;

import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import org.hexworks.zircon.api.input.InputType;

public class WinScreen implements View {

    TileGrid tileGrid;

    public WinScreen(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
    }

    @Override
    public void dock() {
        // TODO: dialogs
//        tileGrid.write("You won.", 1, 1);
//        tileGrid.writeCenter("-- press [enter] to restart --", 22);
    }

    @Override
    public View respondToUserInput(Input input) {
        return input.inputTypeIs(InputType.Enter) ? new PlayView(tileGrid) : this;
    }
}
