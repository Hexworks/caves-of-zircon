package rltut.screens;

import rltut.Creature;
import rltut.Item;

public class QuaffScreen extends InventoryBasedScreen {

	public QuaffScreen(Creature player) {
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
	protected Screen use(Item item) {
		player.quaff(item);
		return null;
	}

}
