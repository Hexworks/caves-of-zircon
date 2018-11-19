package rltut;


public class GoblinAi extends CreatureAi {
	private Creature player;
	
	public GoblinAi(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}

	public void onUpdate(){
		if (canUseBetterEquipment())
			useBetterEquipment();
		else if (canRangedWeaponAttack(player))
			creature.rangedWeaponAttack(player);
		else if (canThrowAt(player))
			creature.throwItem(getWeaponToThrow(), player.x, player.y, player.z);
		else if (creature.canSee(player.x, player.y, player.z))
			hunt(player);
		else if (canPickup())
			creature.pickup();
		else
			wander();
	}
}
