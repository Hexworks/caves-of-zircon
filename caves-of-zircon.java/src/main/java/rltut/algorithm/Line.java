package rltut.algorithm;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.data.impl.Position3D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// TODO: remove, Zircon has this
public class Line implements Iterable<Position3D> {
    private List<Position3D> Position3Ds;

    public List<Position3D> getPosition3Ds() {
        return Position3Ds;
    }

    public Line(int x0, int y0, int x1, int y1) {
        Position3Ds = new ArrayList<>();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            Position3Ds.add(Positions.create3DPosition(x0, y0, 0));

            if (x0 == x1 && y0 == y1)
                break;

            int e2 = err * 2;
            if (e2 > -dx) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    @Override
    public Iterator<Position3D> iterator() {
        return Position3Ds.iterator();
    }
}
