package rltut.world;

import org.hexworks.zircon.api.data.impl.Position3D;

import java.util.Arrays;
import java.util.List;

public class PositionUtils {

    public static List<Position3D> fetchSameLevelNeighborsOf(Position3D pos) {
        return Arrays.asList(
                pos.withRelativeX(-1).withRelativeY(1),
                pos.withRelativeY(1),
                pos.withRelativeX(1).withRelativeY(1),
                pos.withRelativeX(-1),
                pos.withRelativeX(1),
                pos.withRelativeX(-1).withRelativeY(-1),
                pos.withRelativeY(-1),
                pos.withRelativeX(1).withRelativeY(-1));
    }

}
