package rltut.ai;


public class ZombieAi extends CreatureAi {
    private Creature player;

    public ZombieAi(Creature creature, Creature player) {
        super(creature);
        this.player = player;
    }

    public void onUpdate() {
        if (Math.random() < 0.2)
            return;

        if (creature.canSee(player.position)) {
            hunt(player);
        } else {
            wander();
        }
    }
}
