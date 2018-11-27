package rltut.ai;

import org.hexworks.zircon.api.Positions;
import rltut.factories.StuffFactory;

public class FungusAi extends CreatureAi {
    private StuffFactory factory;
    private int spreadcount;

    public FungusAi(Creature creature, StuffFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public void onUpdate() {
        if (spreadcount < 5 && Math.random() < 0.01)
            spread();
    }

    private void spread() {
        int x = creature.position.getX() + (int) (Math.random() * 11) - 5;
        int y = creature.position.getY() + (int) (Math.random() * 11) - 5;

        if (!creature.canEnter(Positions.create3DPosition(x, y, creature.position.getZ())))
            return;

        creature.doAction("spawn a child");

        Creature child = factory.newFungus(creature.position.getZ());
        child.position = Positions.create3DPosition(x, y, creature.position.getZ());
        spreadcount++;
    }
}
