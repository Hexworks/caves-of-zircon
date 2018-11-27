package rltut.blocks;

import org.hexworks.zircon.api.data.BlockSide;
import org.hexworks.zircon.api.data.base.BlockBase;
import org.jetbrains.annotations.NotNull;
import rltut.factories.GameTileRepository;

import java.util.Collections;
import java.util.List;

class GameBlock extends BlockBase<GameTile> {

    private GameTile tile;
    private GameBlockType type;
    private GameTile originalTile;

    public GameBlock(GameTile tile, GameBlockType type) {
        this.tile = tile;
        this.type = type;
        originalTile = tile;
    }

    public Boolean isDiggable() {
        return tile == GameTileRepository.WALL;
    }

    public Boolean isGround() {
        return tile == GameTileRepository.FLOOR;
    }

    public Boolean hasCreature() {
        return tile == GameTileRepository.FUNGUS;
    }

    public void setTile(GameTile tile) {
        this.tile = tile;
    }

    public void clearTile() {
        this.tile = originalTile;
    }

    @NotNull
    @Override
    public List<GameTile> getLayers() {
        return Collections.singletonList(tile);
    }

    @NotNull
    @Override
    public GameTile fetchSide(@NotNull BlockSide blockSide) {
        return GameTileRepository.EMPTY;
    }

    @Override
    public String toString() {
        return String.format("GameBlock(type=%s)", type);
    }
}
