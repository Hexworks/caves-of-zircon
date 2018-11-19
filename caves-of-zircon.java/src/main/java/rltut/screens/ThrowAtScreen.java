package rltut.screens;

import rltut.Creature;
import rltut.Item;
import rltut.Line;
import rltut.Point;

public class ThrowAtScreen extends TargetBasedScreen {
	private Item item;
	
	public ThrowAtScreen(Creature player, int sx, int sy, Item item) {
		super(player, "Throw " + player.nameOf(item) + " at?", sx, sy);
		this.item = item;
	}

	public boolean isAcceptable(int x, int y) {
		if (!player.canSee(x, y, player.z))
			return false;
		
		for (Point p : new Line(player.x, player.y, x, y)){
			if (!player.realTile(p.x, p.y, player.z).isGround())
				return false;
		}
		
		return true;
	}

	public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
		player.throwItem(item, x, y, player.z);
	}
}
