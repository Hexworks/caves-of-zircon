package rltut.algorithm;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.ai.Creature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static rltut.world.PositionUtils.fetchSameLevelNeighborsOf;

public class PathFinder {
    private ArrayList<Position3D> open;
    private ArrayList<Position3D> closed;
    private HashMap<Position3D, Position3D> parents;
    private HashMap<Position3D, Integer> totalCost;

    public PathFinder() {
        this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.parents = new HashMap<>();
        this.totalCost = new HashMap<>();
    }

    private int heuristicCost(Position3D from, Position3D to) {
        return Math.max(Math.abs(from.getX() - to.getY()), Math.abs(from.getY() - to.getY()));
    }

    private int costToGetTo(Position3D from) {
        return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
    }

    private int totalCost(Position3D from, Position3D to) {
        if (totalCost.containsKey(from))
            return totalCost.get(from);

        int cost = costToGetTo(from) + heuristicCost(from, to);
        totalCost.put(from, cost);
        return cost;
    }

    private void reParent(Position3D child, Position3D parent) {
        parents.put(child, parent);
        totalCost.remove(child);
    }

    public ArrayList<Position3D> findPath(Creature creature, Position3D start, Position3D end, int maxTries) {
        open.clear();
        closed.clear();
        parents.clear();
        totalCost.clear();

        open.add(start);

        for (int tries = 0; tries < maxTries && open.size() > 0; tries++) {
            Position3D closest = getClosestPosition3D(end);

            open.remove(closest);
            closed.add(closest);

            if (closest.equals(end))
                return createPath(start, closest);
            else
                checkNeighbors(creature, end, closest);
        }
        return null;
    }

    private Position3D getClosestPosition3D(Position3D end) {
        Position3D closest = open.get(0);
        for (Position3D other : open) {
            if (totalCost(other, end) < totalCost(closest, end))
                closest = other;
        }
        return closest;
    }

    private void checkNeighbors(Creature creature, Position3D end, Position3D closest) {
        for (Position3D neighbor : fetchSameLevelNeighborsOf(closest)) {
            if (closed.contains(neighbor)
                    || !creature.canEnter(Positions.create3DPosition(neighbor.getX(), neighbor.getY(), creature.position.getZ()))
                    && !neighbor.equals(end))
                continue;

            if (open.contains(neighbor))
                reParentNeighborIfNecessary(closest, neighbor);
            else
                reParentNeighbor(closest, neighbor);
        }
    }

    private void reParentNeighbor(Position3D closest, Position3D neighbor) {
        reParent(neighbor, closest);
        open.add(neighbor);
    }

    private void reParentNeighborIfNecessary(Position3D closest, Position3D neighbor) {
        Position3D originalParent = parents.get(neighbor);
        double currentCost = costToGetTo(neighbor);
        reParent(neighbor, closest);
        double reparentCost = costToGetTo(neighbor);

        if (reparentCost < currentCost)
            open.remove(neighbor);
        else
            reParent(neighbor, originalParent);
    }

    private ArrayList<Position3D> createPath(Position3D start, Position3D end) {
        ArrayList<Position3D> path = new ArrayList<>();

        while (!end.equals(start)) {
            path.add(end);
            end = parents.get(end);
        }

        Collections.reverse(path);
        return path;
    }
}
