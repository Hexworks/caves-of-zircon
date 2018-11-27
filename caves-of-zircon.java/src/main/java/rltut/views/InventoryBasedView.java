package rltut.views;

import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import org.hexworks.zircon.api.input.InputType;
import rltut.ai.Creature;
import rltut.entities.Item;

import java.util.ArrayList;

public abstract class InventoryBasedView implements View {

    TileGrid terminal;
    protected Creature player;
    private String letters;

    protected abstract String getVerb();

    protected abstract boolean isAcceptable(Item item);

    protected abstract View use(Item item);

    InventoryBasedView(Creature player) {
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
    }

    @Override
    public void dock() {
        // TODO: use dialogs instead
//        ArrayList<String> lines = getList();
//        int y = 23 - lines.size();
//        int x = 4;
//        if (lines.size() > 0) {
//            terminal.clear(' ', x, y, 20, lines.size());
//        }
//        for (String line : lines) {
//            terminal.write(line, x, y++);
//        }
//        terminal.clear(' ', 0, 23, 80, 1);
//        terminal.write("What would you like to " + getVerb() + "?", 2, 23);
    }

    private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();
        Item[] inventory = player.inventory().getItems();
        for (int i = 0; i < inventory.length; i++) {
            Item item = inventory[i];
            if (item == null || !isAcceptable(item))
                continue;
            String line = letters.charAt(i) + " - " + item.glyph() + " " + player.nameOf(item);
            if (item == player.weapon() || item == player.armor())
                line += " (equipped)";
            lines.add(line);
        }
        return lines;
    }

    public View respondToUserInput(Input input) {
        Item[] items = player.inventory().getItems();
        if (input.inputTypeIs(InputType.Enter)) {
            return null;
        } else {
            if (input.isKeyStroke()) {
                char c = input.asKeyStroke().get().getCharacter();
                if (letters.indexOf(c) > -1
                        && items.length > letters.indexOf(c)
                        && items[letters.indexOf(c)] != null
                        && isAcceptable(items[letters.indexOf(c)])) {
                    return use(items[letters.indexOf(c)]);
                }
            }
            return this;
        }
    }
}
