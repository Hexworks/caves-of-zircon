package rltut.blocks;

import org.hexworks.zircon.api.data.Tile;
import org.hexworks.zircon.api.data.base.BaseCharacterTile;
import org.hexworks.zircon.api.graphics.StyleSet;
import org.jetbrains.annotations.NotNull;

import static rltut.factories.GameTileRepository.FLOOR;
import static rltut.factories.GameTileRepository.WALL;

public class GameTile extends BaseCharacterTile {

    private char character;
    private StyleSet styleSet;
    private String description;

    public GameTile(char character, StyleSet styleSet, String description) {
        this.character = character;
        this.styleSet = styleSet;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isGround() {
        return this == FLOOR;
    }

    public boolean isDiggable() {
        return this == WALL;
    }

    @Override
    public char getCharacter() {
        return character;
    }

    @NotNull
    @Override
    public StyleSet getStyleSet() {
        return styleSet;
    }

    @NotNull
    @Override
    public Tile createCopy() {
        return new GameTile(character, styleSet, description);
    }

    @NotNull
    @Override
    public String generateCacheKey() {
        return String.format("GameTile(c=%s,s=%s)", character, styleSet);
    }
}
