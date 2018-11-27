package rltut.algorithm;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.ai.Creature;

import java.util.List;

public class Path {

    private static PathFinder pf = new PathFinder();

    private List<Position3D> Position3Ds;

    public List<Position3D> Position3Ds() {
        return Position3Ds;
    }

    public Path(Creature creature, int x, int y) {
        Position3Ds = pf.findPath(
                creature,
                creature.position,
                Positions.create3DPosition(x, y, creature.position.getZ()), 300);
    }
}
