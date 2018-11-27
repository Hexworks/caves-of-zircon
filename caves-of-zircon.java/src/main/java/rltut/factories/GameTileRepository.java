package rltut.factories;

import org.hexworks.zircon.api.StyleSets;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.graphics.Symbols;
import rltut.blocks.GameTile;

import static org.hexworks.zircon.api.color.ANSITileColor.*;

public class GameTileRepository {

    private GameTileRepository() {
    }

    public static GameTile FLOOR = new GameTile(
            Symbols.INTERPUNCT,
            StyleSets.empty().withForegroundColor(YELLOW),
            "A dirt and rock cave floor.");

    public static GameTile WALL = new GameTile(
            '#',
            StyleSets.empty().withForegroundColor(TileColors.fromString("#999999")),
            "A dirt and rock cave wall.");

    public static GameTile PLAYER = new GameTile(
            '@',
            StyleSets.empty().withForegroundColor(TileColors.fromString("#ffffff")),
            "");

    public static GameTile FUNGUS = new GameTile(
            'f',
            StyleSets.empty().withForegroundColor(TileColors.fromString("#4B9051")),
            "");

    public static GameTile STAIRS_DOWN = new GameTile(
            '>',
            StyleSets.empty().withForegroundColor(WHITE),
            "A stone staircase that goes down.");

    public static GameTile STAIRS_UP = new GameTile(
            '<',
            StyleSets.empty().withForegroundColor(WHITE),
            "A stone staircase that goes up.");

    public static GameTile UNKNOWN = new GameTile(
            ' ',
            StyleSets.empty().withForegroundColor(WHITE),
            "(unknown)");

    public static GameTile EMPTY = new GameTile(
            ' ', StyleSets.empty(),
            "Nothingness.");
}
