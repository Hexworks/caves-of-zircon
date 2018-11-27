package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class DropView extends InventoryBasedView {

    DropView(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "drop";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return true;
    }

    @Override
    protected View use(Item item) {
        player.drop(item);
        return null;
    }
}
