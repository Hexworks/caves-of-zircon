package rltut.world;

import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;

public enum Tile {
    FLOOR((char) 250, ANSITileColor.YELLOW, "A dirt and rock cave floor."),
    WALL((char) 177, ANSITileColor.YELLOW, "A dirt and rock cave wall."),
    BOUNDS('x', ANSITileColor.GRAY, "Beyond the edge of the world."),
    STAIRS_DOWN('>', ANSITileColor.WHITE, "A stone staircase that goes down."),
    STAIRS_UP('<', ANSITileColor.WHITE, "A stone staircase that goes up."),
    UNKNOWN(' ', ANSITileColor.WHITE, "(unknown)");

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private TileColor color;

    public TileColor color() {
        return color;
    }

    private String description;

    public String details() {
        return description;
    }

    Tile(char glyph, TileColor color, String description) {
        this.glyph = glyph;
        this.color = color;
        this.description = description;
    }

    public boolean isGround() {
        return this != WALL && this != BOUNDS;
    }

    public boolean isDiggable() {
        return this == Tile.WALL;
    }
}
