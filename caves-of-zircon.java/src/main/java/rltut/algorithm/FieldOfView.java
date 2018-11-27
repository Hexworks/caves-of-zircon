package rltut.algorithm;

import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.world.Tile;
import rltut.world.WorldJava;

public class FieldOfView {
    private WorldJava world;
    private int depth;

    private boolean[][] visible;

    public boolean isVisible(Position3D pos) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        return z == depth && x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
    }

    private Tile[][][] tiles;

    public Tile tile(Position3D pos) {
        return tiles[pos.getX()][pos.getY()][pos.getZ()];
    }

    public FieldOfView(WorldJava world) {
        this.world = world;
        this.visible = new boolean[world.width()][world.height()];
        this.tiles = new Tile[world.width()][world.height()][world.depth()];

        for (int x = 0; x < world.width(); x++) {
            for (int y = 0; y < world.height(); y++) {
                for (int z = 0; z < world.depth(); z++) {
                    tiles[x][y][z] = Tile.UNKNOWN;
                }
            }
        }
    }

    public void update(Position3D pos, int r) {
        depth = pos.getZ();
        visible = new boolean[world.width()][world.height()];

        for (int x = -r; x < r; x++) {
            for (int y = -r; y < r; y++) {
                if (x * x + y * y > r * r)
                    continue;

                if (pos.getX() + x < 0 || pos.getX() + x >= world.width() || pos.getY() + y < 0 || pos.getY() + y >= world.height())
                    continue;

                for (Position3D p : new Line(pos.getX(), pos.getY(), pos.getX() + x, pos.getY() + y)) {
                    Tile tile = world.tile(p.withZ(pos.getZ()));
                    visible[p.getX()][p.getY()] = true;
                    tiles[p.getX()][p.getY()][pos.getZ()] = tile;

                    if (!tile.isGround())
                        break;
                }
            }
        }
    }
}
