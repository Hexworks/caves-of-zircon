package rltut.views;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.ai.Creature;
import rltut.entities.Item;
import rltut.algorithm.Line;

public class ThrowAtScreen extends TargetBasedScreen {
    private Item item;

    ThrowAtScreen(Creature player, int sx, int sy, Item item) {
        super(player, "Throw " + player.nameOf(item) + " at?", sx, sy);
        this.item = item;
    }

    public boolean isAcceptable(int x, int y) {
        if (!player.canSee(Positions.create3DPosition(x, y, player.position.getZ())))
            return false;

        for (Position3D p : new Line(player.position.getX(), player.position.getY(), x, y)) {
            if (!player.realTile(p.withZ(player.position.getZ())).isGround())
                return false;
        }

        return true;
    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
        player.throwItem(item, Positions.create3DPosition(x, y, player.position.getZ()));
    }
}
