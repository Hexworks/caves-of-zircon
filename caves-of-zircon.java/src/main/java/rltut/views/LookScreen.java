package rltut.views;

import org.hexworks.zircon.api.Positions;
import rltut.ai.Creature;
import rltut.entities.Item;
import rltut.world.Tile;

public class LookScreen extends TargetBasedScreen {

    LookScreen(Creature player, String caption, int sx, int sy) {
        super(player, caption, sx, sy);
    }

    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
        Creature creature = player.creature(Positions.create3DPosition(x, y, player.position.getZ()));
        if (creature != null) {
            caption = creature.glyph() + " " + creature.name() + creature.details();
            return;
        }

        Item item = player.item(Positions.create3DPosition(x, y, player.position.getZ()));
        if (item != null) {
            caption = item.glyph() + " " + player.nameOf(item) + item.details();
            return;
        }

        Tile tile = player.tile(Positions.create3DPosition(x, y, player.position.getZ()));
        caption = tile.glyph() + " " + tile.details();
    }
}
