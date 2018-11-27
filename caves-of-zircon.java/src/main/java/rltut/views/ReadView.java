package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class ReadView extends InventoryBasedView {

    private int sx;
    private int sy;

    ReadView(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    protected String getVerb() {
        return "read";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return !item.writtenSpells().isEmpty();
    }

    @Override
    protected View use(Item item) {
        return new ReadSpellScreen(player, sx, sy, item);
    }

}
