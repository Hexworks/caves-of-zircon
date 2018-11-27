package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class EatView extends InventoryBasedView {

    EatView(Creature player) {
        super(player);
    }

    @Override
    protected String getVerb() {
        return "eat";
    }

    @Override
    protected boolean isAcceptable(Item item) {
        return item.foodValue() != 0;
    }

    @Override
    protected View use(Item item) {
        player.eat(item);
        return null;
    }
}
