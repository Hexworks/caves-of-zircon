package rltut.ai;

import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.algorithm.FieldOfView;
import rltut.entities.Item;
import rltut.world.Tile;

import java.util.List;

public class PlayerAi extends CreatureAi {

	private List<String> messages;
	private FieldOfView fov;
	
	public PlayerAi(Creature creature, List<String> messages, FieldOfView fov) {
		super(creature);
		this.messages = messages;
		this.fov = fov;
	}

	public void onEnter(Position3D pos, Tile tile){
		if (tile.isGround()){
			creature.position = pos;
			
			Item item = creature.item(creature.position);
			if (item != null)
				creature.notify("There's a " + creature.nameOf(item) + " here.");
			
		} else if (tile.isDiggable()) {
			creature.dig(pos);
		}
	}
	
	public void onNotify(String message){
		messages.add(message);
	}
	
	public boolean canSee(Position3D pos) {
		return fov.isVisible(pos);
	}
	
	public void onGainLevel(){
	}

	public Tile rememberedTile(Position3D pos) {
		return fov.tile(pos);
	}
}
