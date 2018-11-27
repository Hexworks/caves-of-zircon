package rltut.views;

import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import org.hexworks.zircon.api.input.InputType;
import rltut.ai.Creature;

public class LoseView implements View {
    private Creature player;
    private TileGrid tileGrid;

    public LoseView(TileGrid tileGrid, Creature player) {
        this.player = player;
        this.tileGrid = tileGrid;
    }

    @Override
    public void dock() {
        // TODO: use dialogs
//        tileGrid.writeCenter("R.I.P.", 3);
//        tileGrid.writeCenter(player.causeOfDeath(), 5);
//        tileGrid.writeCenter("-- press [enter] to restart --", 22);
    }

    @Override
    public View respondToUserInput(Input input) {
        return input.inputTypeIs(InputType.Enter) ? new PlayView(tileGrid) : this;
    }
}
