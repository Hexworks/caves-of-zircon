package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class ThrowView extends InventoryBasedView {
    private int sx;
    private int sy;

    ThrowView(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    protected String getVerb() {
        return "throw";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected View use(Item item) {
        return new ThrowAtScreen(player, sx, sy, item);
    }
}
