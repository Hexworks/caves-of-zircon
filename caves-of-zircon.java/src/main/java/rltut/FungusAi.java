package rltut;

public class FungusAi extends CreatureAi {
	private StuffFactory factory;
	private int spreadcount;
	
	public FungusAi(Creature creature, StuffFactory factory) {
		super(creature);
		this.factory = factory;
	}

	public void onUpdate(){
		if (spreadcount < 5 && Math.random() < 0.01)
			spread();
	}
	
	private void spread(){
		int x = creature.x + (int)(Math.random() * 11) - 5;
		int y = creature.y + (int)(Math.random() * 11) - 5;
		
		if (!creature.canEnter(x, y, creature.z))
			return;
		
		creature.doAction("spawn a child");
		
		Creature child = factory.newFungus(creature.z);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
	}
}
