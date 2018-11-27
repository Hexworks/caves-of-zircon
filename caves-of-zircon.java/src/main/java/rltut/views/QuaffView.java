package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class QuaffView extends InventoryBasedView {

    QuaffView(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "quaff";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.quaffEffect() != null;
    }

    @Override
    protected View use(Item item) {
        player.quaff(item);
        return null;
    }

}
