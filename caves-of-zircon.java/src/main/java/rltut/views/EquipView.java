package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class EquipView extends InventoryBasedView {

    EquipView(Creature player) {
        super(player);
    }

    protected String getVerb() {
        return "wear or wield";
    }

    protected boolean isAcceptable(Item item) {
        return item.attackValue() > 0 || item.defenseValue() > 0;
    }

    protected View use(Item item) {
        player.equip(item);
        return null;
    }
}
