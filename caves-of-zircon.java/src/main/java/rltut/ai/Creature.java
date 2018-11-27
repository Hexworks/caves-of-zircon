package rltut.ai;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.Position;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.entities.Effect;
import rltut.entities.Inventory;
import rltut.entities.Item;
import rltut.algorithm.Line;
import rltut.entities.Spell;
import rltut.world.Tile;
import rltut.world.WorldJava;

import java.util.ArrayList;
import java.util.List;


public class Creature {
    private WorldJava world;

    public Position3D position;

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private TileColor color;

    public TileColor color() {
        return color;
    }

    private CreatureAi ai;

    void setCreatureAi(CreatureAi ai) {
        this.ai = ai;
    }

    private int maxHp;

    public int maxHp() {
        return maxHp;
    }

    public void modifyMaxHp(int amount) {
        maxHp += amount;
    }

    private int hp;

    public int hp() {
        return hp;
    }

    private int attackValue;

    public void modifyAttackValue(int value) {
        attackValue += value;
    }

    private int attackValue() {
        return attackValue
                + (weapon == null ? 0 : weapon.attackValue())
                + (armor == null ? 0 : armor.attackValue());
    }

    private int defenseValue;

    public void modifyDefenseValue(int value) {
        defenseValue += value;
    }

    private int defenseValue() {
        return defenseValue
                + (weapon == null ? 0 : weapon.defenseValue())
                + (armor == null ? 0 : armor.defenseValue());
    }

    private int visionRadius;

    public void modifyVisionRadius(int value) {
        visionRadius += value;
    }

    public int visionRadius() {
        return visionRadius;
    }

    private String name;

    public String name() {
        return name;
    }

    private Inventory inventory;

    public Inventory inventory() {
        return inventory;
    }

    private int maxFood;

    public int maxFood() {
        return maxFood;
    }

    private int food;

    public int food() {
        return food;
    }

    private Item weapon;

    public Item weapon() {
        return weapon;
    }

    private Item armor;

    public Item armor() {
        return armor;
    }

    private int xp;

    public int xp() {
        return xp;
    }

    public void modifyXp(int amount) {
        xp += amount;

        notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);

        while (xp > (int) (Math.pow(level, 1.75) * 25)) {
            level++;
            doAction("advance to level %d", level);
            ai.onGainLevel();
            modifyHp(level * 2, "Died from having a negative level?");
        }
    }

    private int level;

    public int level() {
        return level;
    }

    private int regenHpCooldown;
    private int regenHpPer1000;

    public void modifyRegenHpPer1000(int amount) {
        regenHpPer1000 += amount;
    }

    private List<Effect> effects;

    public List<Effect> effects() {
        return effects;
    }

    private int maxMana;

    public int maxMana() {
        return maxMana;
    }

    public void modifyMaxMana(int amount) {
        maxMana += amount;
    }

    private int mana;

    public int mana() {
        return mana;
    }

    public void modifyMana(int amount) {
        mana = Math.max(0, Math.min(mana + amount, maxMana));
    }

    private int regenManaCooldown;
    private int regenManaPer1000;

    public void modifyRegenManaPer1000(int amount) {
        regenManaPer1000 += amount;
    }

    private String causeOfDeath;

    public String causeOfDeath() {
        return causeOfDeath;
    }

    public Creature(WorldJava world, char glyph, TileColor color, String name, int maxHp, int attack, int defense) {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = 9;
        this.name = name;
        this.inventory = new Inventory(20);
        this.maxFood = 1000;
        this.food = maxFood / 3 * 2;
        this.level = 1;
        this.regenHpPer1000 = 10;
        this.effects = new ArrayList<>();
        this.maxMana = 5;
        this.mana = maxMana;
        this.regenManaPer1000 = 20;
    }

    public void moveBy(Position3D pos) {
        if (pos.equals(Positions.default3DPosition()))
            return;

        Tile tile = world.tile(position.plus(pos));

        if (pos.getZ() == -1) {
            if (tile == Tile.STAIRS_DOWN) {
                doAction("walk up the stairs to level %d", position.getZ() + pos.getZ() + 1);
            } else {
                doAction("try to go up but are stopped by the cave ceiling");
                return;
            }
        } else if (pos.getZ() == 1) {
            if (tile == Tile.STAIRS_UP) {
                doAction("walk down the stairs to level %d", position.getZ() + pos.getZ() + 1);
            } else {
                doAction("try to go down but are stopped by the cave floor");
                return;
            }
        }

        Creature other = world.creature(position.plus(pos));

        modifyFood(-1);

        if (other == null)
            ai.onEnter(position.plus(pos), tile);
        else
            meleeAttack(other);
    }

    private void meleeAttack(Creature other) {
        commonAttack(other, attackValue(), "attack the %s for %d damage", other.name);
    }

    private void throwAttack(Item item, Creature other) {
        commonAttack(other, attackValue / 2 + item.thrownAttackValue(), "throw a %s at the %s for %d damage", nameOf(item), other.name);
        other.addEffect(item.quaffEffect());
    }

    public void rangedWeaponAttack(Creature other) {
        commonAttack(other, attackValue / 2 + weapon.rangedAttackValue(), "fire a %s at the %s for %d damage", nameOf(weapon), other.name);
    }

    private void commonAttack(Creature other, int attack, String action, Object... params) {
        modifyFood(-2);

        int amount = Math.max(0, attack - other.defenseValue());

        amount = (int) (Math.random() * amount) + 1;

        Object[] params2 = new Object[params.length + 1];
        System.arraycopy(params, 0, params2, 0, params.length);
        params2[params2.length - 1] = amount;

        doAction(action, params2);

        other.modifyHp(-amount, "Killed by a " + name);

        if (other.hp < 1)
            gainXp(other);
    }

    private void gainXp(Creature other) {
        int amount = other.maxHp
                + other.attackValue()
                + other.defenseValue()
                - level;

        if (amount > 0)
            modifyXp(amount);
    }

    public void modifyHp(int amount, String causeOfDeath) {
        hp += amount;
        this.causeOfDeath = causeOfDeath;

        if (hp > maxHp) {
            hp = maxHp;
        } else if (hp < 1) {
            doAction("die");
            leaveCorpse();
            world.remove(this);
        }
    }

    private void leaveCorpse() {
        Item corpse = new Item('%', color, name + " corpse", null);
        corpse.modifyFoodValue(maxHp * 5);
        world.addAtEmptySpace(corpse, position);
        for (Item item : inventory.getItems()) {
            if (item != null)
                drop(item);
        }
    }

    void dig(Position3D pos) {
        modifyFood(-10);
        world.dig(pos);
        doAction("dig");
    }

    public void update() {
        modifyFood(-1);
        regenerateHealth();
        regenerateMana();
        updateEffects();
        ai.onUpdate();
    }

    private void updateEffects() {
        List<Effect> done = new ArrayList<>();

        for (Effect effect : effects) {
            effect.update(this);
            if (effect.isDone()) {
                effect.end(this);
                done.add(effect);
            }
        }

        effects.removeAll(done);
    }

    private void regenerateHealth() {
        regenHpCooldown -= regenHpPer1000;
        if (regenHpCooldown < 0) {
            if (hp < maxHp) {
                modifyHp(1, "Died from regenerating health?");
                modifyFood(-1);
            }
            regenHpCooldown += 1000;
        }
    }

    private void regenerateMana() {
        regenManaCooldown -= regenManaPer1000;
        if (regenManaCooldown < 0) {
            if (mana < maxMana) {
                modifyMana(1);
                modifyFood(-1);
            }
            regenManaCooldown += 1000;
        }
    }

    public boolean canEnter(Position3D pos) {
        return world.tile(pos).isGround() && world.creature(pos) == null;
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public void doAction(String message, Object... params) {
        for (Creature other : getCreaturesWhoSeeMe()) {
            if (other == this) {
                other.notify("You " + message + ".", params);
            } else {
                other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
            }
        }
    }

    public void doAction(Item item, String message, Object... params) {
        if (hp < 1)
            return;

        for (Creature other : getCreaturesWhoSeeMe()) {
            if (other == this) {
                other.notify("You " + message + ".", params);
            } else {
                other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
            }
            other.learnName(item);
        }
    }

    private List<Creature> getCreaturesWhoSeeMe() {
        List<Creature> others = new ArrayList<>();
        int r = 9;
        for (int ox = -r; ox < r + 1; ox++) {
            for (int oy = -r; oy < r + 1; oy++) {
                if (ox * ox + oy * oy > r * r)
                    continue;

                Creature other = world.creature(position.withRelativeX(ox)
                        .withRelativeY(oy));

                if (other == null)
                    continue;

                others.add(other);
            }
        }
        return others;
    }

    private String makeSecondPerson(String text) {
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }

    public boolean canSee(Position3D position) {
        return (detectCreatures > 0 && world.creature(position) != null
                || ai.canSee(position));
    }

    public Tile realTile(Position3D pos) {
        return world.tile(pos);
    }

    public Tile tile(Position3D pos) {
        if (canSee(pos))
            return world.tile(pos);
        else
            return ai.rememberedTile(pos);
    }

    public Creature creature(Position3D pos) {
        if (canSee(pos))
            return world.creature(pos);
        else
            return null;
    }

    public void pickup() {
        Item item = world.item(position);

        if (inventory.isFull() || item == null) {
            doAction("grab at the ground");
        } else {
            doAction("pickup a %s", nameOf(item));
            world.remove(position);
            inventory.add(item);
        }
    }

    public void drop(Item item) {
        if (world.addAtEmptySpace(item, position)) {
            doAction("drop a " + nameOf(item));
            inventory.remove(item);
            unequip(item);
        } else {
            notify("There's nowhere to drop the %s.", nameOf(item));
        }
    }

    private void modifyFood(int amount) {
        food += amount;

        if (food > maxFood) {
            maxFood = (maxFood + food) / 2;
            food = maxFood;
            notify("You can't belive your stomach can hold that much!");
            modifyHp(-1, "Killed by overeating.");
        } else if (food < 1 && isPlayer()) {
            modifyHp(-1000, "Starved to death.");
        }
    }

    public boolean isPlayer() {
        return glyph == '@';
    }

    public void eat(Item item) {
        doAction("eat a " + nameOf(item));
        consume(item);
    }

    public void quaff(Item item) {
        doAction("quaff a " + nameOf(item));
        consume(item);
    }

    private void consume(Item item) {
        if (item.foodValue() < 0)
            notify("Gross!");

        addEffect(item.quaffEffect());

        modifyFood(item.foodValue());
        getRidOf(item);
    }

    private void addEffect(Effect effect) {
        if (effect == null)
            return;

        effect.start(this);
        effects.add(effect);
    }

    private void getRidOf(Item item) {
        inventory.remove(item);
        unequip(item);
    }

    private void putAt(Item item, Position3D pos) {
        inventory.remove(item);
        unequip(item);
        world.addAtEmptySpace(item, pos);
    }

    private void unequip(Item item) {
        if (item == null)
            return;

        if (item == armor) {
            if (hp > 0)
                doAction("remove a " + nameOf(item));
            armor = null;
        } else if (item == weapon) {
            if (hp > 0)
                doAction("put away a " + nameOf(item));
            weapon = null;
        }
    }

    public void equip(Item item) {
        if (!inventory.contains(item)) {
            if (inventory.isFull()) {
                notify("Can't equip %s since you're holding too much stuff.", nameOf(item));
                return;
            } else {
                world.remove(item);
                inventory.add(item);
            }
        }

        if (item.attackValue() == 0 && item.rangedAttackValue() == 0 && item.defenseValue() == 0)
            return;

        if (item.attackValue() + item.rangedAttackValue() >= item.defenseValue()) {
            unequip(weapon);
            doAction("wield a " + nameOf(item));
            weapon = item;
        } else {
            unequip(armor);
            doAction("put on a " + nameOf(item));
            armor = item;
        }
    }

    public Item item(Position3D pos) {
        if (canSee(position))
            return world.item(pos);
        else
            return null;
    }

    public String details() {
        return String.format("  level:%d  attack:%d  defense:%d  hp:%d", level, attackValue(), defenseValue(), hp);
    }

    public void throwItem(Item item, Position3D pos) {
        Position3D end = position.withZ(0);

        for (Position3D p : new Line(position.getX(), position.getY(), pos.getX(), pos.getY())) {
            if (!realTile(p.withZ(position.getZ())).isGround())
                break;
            end = p;
        }

        int newX = end.getX();
        int newY = end.getY();

        Position3D newPos = Positions.create3DPosition(newX, newY, pos.getZ());

        Creature c = creature(newPos);


        if (c != null)
            throwAttack(item, c);
        else
            doAction("throw a %s", nameOf(item));

        if (item.quaffEffect() != null && c != null)
            getRidOf(item);
        else
            putAt(item, newPos);
    }

    public void summon(Creature other) {
        world.add(other);
    }

    private int detectCreatures;

    public void modifyDetectCreatures(int amount) {
        detectCreatures += amount;
    }

    public void castSpell(Spell spell, Position pos) {
        Creature other = creature(Positions.from2DTo3D(pos, position.getZ()));

        if (spell.manaCost() > mana) {
            doAction("Position3D and mumble but nothing happens");
            return;
        } else if (other == null) {
            doAction("Position3D and mumble at nothing");
            return;
        }

        other.addEffect(spell.effect());
        modifyMana(-spell.manaCost());
    }

    public String nameOf(Item item) {
        return ai.getName(item);
    }

    private void learnName(Item item) {
        notify("The " + item.appearance() + " is a " + item.name() + "!");
        ai.setName(item, item.name());
    }
}
