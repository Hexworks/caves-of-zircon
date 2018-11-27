package rltut.views;

import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import rltut.entities.LevelUpController;
import rltut.ai.Creature;

import java.util.List;

public class LevelUpScreen implements View {
    private LevelUpController controller;
    private Creature player;
    private int picks;
    TileGrid terminal;

    public LevelUpScreen(Creature player, int picks) {
        this.controller = new LevelUpController();
        this.player = player;
        this.picks = picks;
    }

    @Override
    public void dock() {
        List<String> options = controller.getLevelUpOptions();
        // TODO: use dialog instead
        int y = 5;
//        terminal.clear(' ', 5, y, 30, options.size() + 2);
//        terminal.write("   Choose a level up bonus    ", 5, y++);
//        terminal.write("------------------------------", 5, y++);

//        for (int i = 0; i < options.size(); i++) {
//            terminal.write(String.format("[%d] %s", i + 1, options.get(i)), 5, y++);
//        }
    }

    @Override
    public View respondToUserInput(Input input) {
        List<String> options = controller.getLevelUpOptions();
        StringBuilder chars = new StringBuilder();
        for (int i = 0; i < options.size(); i++) {
            chars.append(i + 1);
        }
        // TODO: possible bug?
        int i = chars.toString().indexOf(input.asKeyStroke().get().getCharacter());
        if (i < 0) {
            return this;
        }
        controller.getLevelUpOption(options.get(i)).invoke(player);
        if (--picks < 1) {
            return null;
        } else {
            return this;
        }
    }
}
