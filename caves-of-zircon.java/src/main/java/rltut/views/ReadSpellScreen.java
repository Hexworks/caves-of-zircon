package rltut.views;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import org.hexworks.zircon.api.input.InputType;
import rltut.ai.Creature;
import rltut.entities.Item;
import rltut.entities.Spell;

import java.util.ArrayList;

public class ReadSpellScreen implements View {

    protected Creature player;
    private String letters;
    private Item item;
    private int sx;
    private int sy;
    TileGrid terminal;

    ReadSpellScreen(Creature player, int sx, int sy, Item item) {
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.item = item;
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    public void dock() {
        ArrayList<String> lines = getList();
        int y = 23 - lines.size();
        int x = 4;
//        if (lines.size() > 0) {
//            terminal.clear(' ', x, y, 20, lines.size());
//        }
//        for (String line : lines) {
//            terminal.write(line, x, y++);
//        }
//        terminal.clear(' ', 0, 23, 80, 1);
//        terminal.write("What would you like to read?", 2, 23);
    }

    private ArrayList<String> getList() {
        ArrayList<String> lines = new ArrayList<String>();

        for (int i = 0; i < item.writtenSpells().size(); i++) {
            Spell spell = item.writtenSpells().get(i);

            String line = letters.charAt(i) + " - " + spell.name() + " (" + spell.manaCost() + " mana)";

            lines.add(line);
        }
        return lines;
    }

    public View respondToUserInput(Input input) {
        // TODO: possible bug?
        char c = input.asKeyStroke().get().getCharacter();

        Item[] items = player.inventory().getItems();

        if (letters.indexOf(c) > -1
                && items.length > letters.indexOf(c)
                && items[letters.indexOf(c)] != null) {
            return use(item.writtenSpells().get(letters.indexOf(c)));
        } else if (input.inputTypeIs(InputType.Escape)) {
            return null;
        } else {
            return this;
        }
    }

    protected View use(Spell spell) {
        if (spell.requiresTarget())
            return new CastSpellScreen(player, "", sx, sy, spell);

        player.castSpell(spell, Positions.create(player.position.getX(), player.position.getY()));
        return null;
    }
}
