package rltut.ai;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.entities.Item;
import rltut.entities.LevelUpController;
import rltut.algorithm.Line;
import rltut.algorithm.Path;
import rltut.world.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreatureAi {
    protected Creature creature;
    private Map<String, String> itemNames;

    CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
        this.itemNames = new HashMap<>();
    }

    String getName(Item item) {
        String name = itemNames.get(item.name());

        return name == null ? item.appearance() : name;
    }

    void setName(Item item, String name) {
        itemNames.put(item.name(), name);
    }

    public void onEnter(Position3D pos, Tile tile) {
        if (tile.isGround()) {
            creature.position = pos;
        } else {
            creature.doAction("bump into a wall");
        }
    }

    public void onUpdate() {
    }

    public void onNotify(String message) {
    }

    public boolean canSee(Position3D pos) {
        if (creature.position.getZ() != pos.getZ())
            return false;

        if ((creature.position.getX() - pos.getX()) * (creature.position.getX() - pos.getX()) + (creature.position.getY() - pos.getY()) * (creature.position.getY() - pos.getY()) > creature.visionRadius() * creature.visionRadius())
            return false;

        for (Position3D p : new Line(creature.position.getX(), creature.position.getY(), pos.getX(), pos.getY())) {
            if (creature.realTile(p).isGround()
                    || p.getX() == pos.getX() && p.getY() == pos.getY())
                continue;

            return false;
        }

        return true;
    }

    void wander() {
        Position3D newPos = Positions.create3DPosition(
                (int) (Math.random() * 3) - 1,
                (int) (Math.random() * 3) - 1,
                creature.position.getZ());

        Creature other = creature.creature(creature.position.plus(newPos));

        if ((other == null || !other.name().equals(creature.name()))
                && creature.tile(newPos).isGround()) {
            creature.moveBy(newPos.withZ(0));
        }
    }

    public void onGainLevel() {
        new LevelUpController().autoLevelUp(creature);
    }

    public Tile rememberedTile(Position3D pos) {
        return Tile.UNKNOWN;
    }

    boolean canThrowAt(Creature other) {
        return creature.canSee(other.position)
                && getWeaponToThrow() != null;
    }

    Item getWeaponToThrow() {
        Item toThrow = null;

        for (Item item : creature.inventory().getItems()) {
            if (item == null || creature.weapon() == item || creature.armor() == item)
                continue;

            if (toThrow == null || item.thrownAttackValue() > toThrow.attackValue())
                toThrow = item;
        }

        return toThrow;
    }

    boolean canRangedWeaponAttack(Creature other) {
        return creature.weapon() != null
                && creature.weapon().rangedAttackValue() > 0
                && creature.canSee(other.position);
    }

    boolean canPickup() {
        return creature.item(creature.position) != null
                && !creature.inventory().isFull();
    }

    void hunt(Creature target) {
        List<Position3D> Position3Ds = new Path(creature, target.position.getX(), target.position.getY()).Position3Ds();

        int mx = Position3Ds.get(0).getX() - creature.position.getX();
        int my = Position3Ds.get(0).getY() - creature.position.getY();

        creature.moveBy(Positions.create3DPosition(mx, my, 0));
    }

    boolean canUseBetterEquipment() {
        int currentWeaponRating = creature.weapon() == null ? 0 : creature.weapon().attackValue() + creature.weapon().rangedAttackValue();
        int currentArmorRating = creature.armor() == null ? 0 : creature.armor().defenseValue();

        for (Item item : creature.inventory().getItems()) {
            if (item == null)
                continue;

            boolean isArmor = item.attackValue() + item.rangedAttackValue() < item.defenseValue();

            if (item.attackValue() + item.rangedAttackValue() > currentWeaponRating
                    || isArmor && item.defenseValue() > currentArmorRating)
                return true;
        }

        return false;
    }

    void useBetterEquipment() {
        int currentWeaponRating = creature.weapon() == null ? 0 : creature.weapon().attackValue() + creature.weapon().rangedAttackValue();
        int currentArmorRating = creature.armor() == null ? 0 : creature.armor().defenseValue();

        for (Item item : creature.inventory().getItems()) {
            if (item == null)
                continue;

            boolean isArmor = item.attackValue() + item.rangedAttackValue() < item.defenseValue();

            if (item.attackValue() + item.rangedAttackValue() > currentWeaponRating
                    || isArmor && item.defenseValue() > currentArmorRating) {
                creature.equip(item);
            }
        }
    }
}
