package rltut.views;

import rltut.ai.Creature;
import rltut.entities.Item;

public class ExamineView extends InventoryBasedView {

	ExamineView(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "examine";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected View use(Item item) {
		String article = "aeiou".contains(player.nameOf(item).subSequence(0, 1)) ? "an " : "a ";
		player.notify("It's " + article + player.nameOf(item) + "." + item.details());
		return null;
	}
}
