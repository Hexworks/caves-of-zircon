package rltut.screens;

import rltut.Creature;
import rltut.Item;

public class ReadScreen extends InventoryBasedScreen {

	private int sx;
	private int sy;
	
	public ReadScreen(Creature player, int sx, int sy) {
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
	protected Screen use(Item item) {
		return new ReadSpellScreen(player, sx, sy, item);
	}

}
