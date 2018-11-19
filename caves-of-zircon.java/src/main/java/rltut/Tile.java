package rltut;

import org.hexworks.zircon.api.color.TileColor;
import rltut.resources.Colors;

public enum Tile {
    FLOOR((char) 250, Colors.YELLOW, "A dirt and rock cave floor."),
    WALL((char) 177, Colors.YELLOW, "A dirt and rock cave wall."),
    BOUNDS('x', Colors.BRIGHT_BLACK, "Beyond the edge of the world."),
    STAIRS_DOWN('>', Colors.WHITE, "A stone staircase that goes down."),
    STAIRS_UP('<', Colors.WHITE, "A stone staircase that goes up."),
    UNKNOWN(' ', Colors.WHITE, "(unknown)");

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
