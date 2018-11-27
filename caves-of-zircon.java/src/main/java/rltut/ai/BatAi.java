package rltut.ai;

public class BatAi extends CreatureAi {

    public BatAi(Creature creature) {
        super(creature);
    }

    public void onUpdate() {
        wander();
        wander();
    }
}
