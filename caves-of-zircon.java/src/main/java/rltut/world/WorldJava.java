package rltut.world;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.ai.Creature;
import rltut.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class WorldJava {
    private Tile[][][] tiles;
    private Item[][][] items;

    private int width;

    public int width() {
        return width;
    }

    private int height;

    public int height() {
        return height;
    }

    private int depth;

    public int depth() {
        return depth;
    }

    private List<Creature> creatures;

    WorldJava(Tile[][][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.depth = tiles[0][0].length;
        this.creatures = new ArrayList<>();
        this.items = new Item[width][height][depth];
    }

    public Creature creature(Position3D pos) {
        for (Creature c : creatures) {
            if (c.position.equals(pos))
                return c;
        }
        return null;
    }

    public Tile tile(Position3D pos) {
        if (pos.getX() < 0 || pos.getX() >= width || pos.getY() < 0 || pos.getY() >= height || pos.getZ() < 0 || pos.getZ() >= depth)
            return Tile.BOUNDS;
        else
            return tiles[pos.getX()][pos.getY()][pos.getZ()];
    }

    public char glyph(Position3D pos) {
        Creature creature = creature(pos);
        if (creature != null)
            return creature.glyph();

        if (item(pos) != null)
            return item(pos).glyph();

        return tile(pos).glyph();
    }

    public TileColor color(Position3D pos) {
        Creature creature = creature(pos);
        if (creature != null)
            return creature.color();

        if (item(pos) != null)
            return item(pos).color();

        return tile(pos).color();
    }

    public void dig(Position3D pos) {
        if (tile(pos).isDiggable())
            tiles[pos.getX()][pos.getY()][pos.getZ()] = Tile.FLOOR;
    }

    public void addAtEmptyLocation(Creature creature, int z) {
        Position3D pos;

        do {
            pos = Positions.create3DPosition(
                    (int) (Math.random() * width),
                    (int) (Math.random() * height),
                    z);
        }
        while (!tile(pos).isGround() || creature(pos) != null);

        creature.position = pos;
        creatures.add(creature);
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }

    public void remove(Item item) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    if (items[x][y][z] == item) {
                        items[x][y][z] = null;
                        return;
                    }
                }
            }
        }
    }

    public Item item(Position3D pos) {
        return items[pos.getX()][pos.getY()][pos.getZ()];
    }

    public void addAtEmptyLocation(Item item, int depth) {
        Position3D pos;
        do {
            pos = Positions.create3DPosition(
                    (int) (Math.random() * width),
                    (int) (Math.random() * height),
                    depth);
        }
        while (!tile(pos).isGround() || item(pos) != null);
        items[pos.getX()][pos.getY()][depth] = item;
    }

    public void remove(Position3D pos) {
        items[pos.getX()][pos.getY()][pos.getZ()] = null;
    }

    public boolean addAtEmptySpace(Item item, Position3D pos) {
        if (item == null)
            return true;

        List<Position3D> Position3Ds = new ArrayList<>();
        List<Position3D> checked = new ArrayList<>();

        Position3Ds.add(pos);

        while (!Position3Ds.isEmpty()) {
            Position3D p = Position3Ds.remove(0);
            checked.add(p);

            if (!tile(p).isGround())
                continue;

            if (items[p.getX()][p.getY()][p.getZ()] == null) {
                items[p.getX()][p.getY()][p.getZ()] = item;
                Creature c = this.creature(p);
                if (c != null)
                    c.notify("A %s lands between your feet.", c.nameOf(item));
                return true;
            } else {
                List<Position3D> neighbors = PositionUtils.fetchSameLevelNeighborsOf(p);
                neighbors.removeAll(checked);
                Position3Ds.addAll(neighbors);
            }
        }
        return false;
    }

    public void add(Creature pet) {
        creatures.add(pet);
    }
}
