package rltut.views;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.ai.Creature;
import rltut.algorithm.Line;

public class FireWeaponScreen extends TargetBasedScreen {

    FireWeaponScreen(Creature player, int sx, int sy) {
        super(player, "Fire " + player.nameOf(player.weapon()) + " at?", sx, sy);
    }

    public boolean isAcceptable(int x, int y) {
        if (!player.canSee(Positions.create3DPosition(x, y, player.position.getZ()))) {
            return false;
        }
        for (Position3D p : new Line(player.position.getX(), player.position.getY(), x, y)) {
            if (!player.realTile(Positions.create3DPosition(p.getX(), p.getY(), player.position.getZ())).isGround())
                return false;
        }
        return true;
    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
        Creature other = player.creature(Positions.create3DPosition(x, y, player.position.getZ()));
        if (other == null) {
            player.notify("There's no one there to fire at.");
        } else {
            player.rangedWeaponAttack(other);
        }
    }
}
