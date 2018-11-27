package rltut.views;

import org.hexworks.zircon.api.Positions;
import rltut.ai.Creature;
import rltut.entities.Spell;

public class CastSpellScreen extends TargetBasedScreen {
	private Spell spell;
	
	CastSpellScreen(Creature player, String caption, int sx, int sy, Spell spell) {
		super(player, caption, sx, sy);
		this.spell = spell;
	}
	
	public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
		player.castSpell(spell, Positions.create(x, y));
	}
}
