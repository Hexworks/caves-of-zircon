package rltut.ai;


public class GoblinAi extends CreatureAi {
    private Creature player;

    public GoblinAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

    public void onUpdate() {
        if (canUseBetterEquipment())
            useBetterEquipment();
        else if (canRangedWeaponAttack(player))
            creature.rangedWeaponAttack(player);
        else if (canThrowAt(player))
            creature.throwItem(getWeaponToThrow(), player.position);
        else if (creature.canSee(player.position))
            hunt(player);
        else if (canPickup())
            creature.pickup();
        else
            wander();
    }
}
