package rltut.views;

import org.hexworks.zircon.api.data.impl.Position3D;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import rltut.algorithm.Line;
import rltut.ai.Creature;

import java.awt.event.KeyEvent;

public abstract class TargetBasedScreen implements View {

    private TileGrid terminal;
    protected Creature player;
    String caption;
    private int sx;
    private int sy;
    private int x;
    private int y;

    TargetBasedScreen(Creature player, String caption, int sx, int sy) {
        this.player = player;
        this.caption = caption;
        this.sx = sx;
        this.sy = sy;
    }

    @Override
    public void dock() {
        for (Position3D p : new Line(sx, sy, sx + x, sy + y)) {
            if (p.getX() < 0 || p.getX() >= 80 || p.getY() < 0 || p.getY() >= 24)
                continue;

            // todo
//			terminal.write('*', p.x, p.y, ANSITileColor.BRIGHT_MAGENTA);
        }

//		terminal.clear(' ', 0, 23, 80, 1);
//		terminal.write(caption, 0, 23);
    }

    @Override
    public View respondToUserInput(Input input) {
        int px = x;
        int py = y;

        switch (input.asKeyStroke().get().getCharacter()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                x--;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                x++;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_J:
                y--;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_K:
                y++;
                break;
            case KeyEvent.VK_Y:
                x--;
                y--;
                break;
            case KeyEvent.VK_U:
                x++;
                y--;
                break;
            case KeyEvent.VK_B:
                x--;
                y++;
                break;
            case KeyEvent.VK_N:
                x++;
                y++;
                break;
            case KeyEvent.VK_ENTER:
                selectWorldCoordinate(player.position.getX() + x, player.position.getY() + y, sx + x, sy + y);
                return null;
            case KeyEvent.VK_ESCAPE:
                return null;
        }

        if (!isAcceptable(player.position.getX() + x, player.position.getY() + y)) {
            x = px;
            y = py;
        }

        enterWorldCoordinate(player.position.getX() + x, player.position.getY() + y, sx + x, sy + y);

        return this;
    }

    public boolean isAcceptable(int x, int y) {
        return true;
    }

    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {

    }

    public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
    }
}
