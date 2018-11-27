package rltut.factories;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.color.ANSITileColor;
import org.hexworks.zircon.api.color.TileColor;
import org.hexworks.zircon.api.data.impl.Position3D;
import rltut.ai.*;
import rltut.algorithm.FieldOfView;
import rltut.entities.Effect;
import rltut.entities.Item;
import rltut.world.WorldJava;

import java.util.*;

public class StuffFactory {
    private WorldJava world;
    private Map<String, TileColor> potionColors;
    private List<String> potionAppearances;

    public StuffFactory(WorldJava world) {
        this.world = world;

        setUpPotionAppearances();
    }

    private void setUpPotionAppearances() {
        potionColors = new HashMap<>();
        potionColors.put("red potion", ANSITileColor.BRIGHT_RED);
        potionColors.put("yellow potion", ANSITileColor.BRIGHT_YELLOW);
        potionColors.put("green potion", ANSITileColor.BRIGHT_GREEN);
        potionColors.put("cyan potion", ANSITileColor.BRIGHT_CYAN);
        potionColors.put("blue potion", ANSITileColor.BRIGHT_BLUE);
        potionColors.put("magenta potion", ANSITileColor.BRIGHT_MAGENTA);
        potionColors.put("dark potion", ANSITileColor.GRAY);
        potionColors.put("grey potion", ANSITileColor.WHITE);
        potionColors.put("light potion", ANSITileColor.BRIGHT_WHITE);

        potionAppearances = new ArrayList<>(potionColors.keySet());
        Collections.shuffle(potionAppearances);
    }

    public Creature newPlayer(List<String> messages, FieldOfView fov) {
        Creature player = new Creature(world, '@', ANSITileColor.BRIGHT_WHITE, "player", 100, 20, 5);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;
    }

    public Creature newFungus(int depth) {
        Creature fungus = new Creature(world, 'f', ANSITileColor.GREEN, "fungus", 10, 0, 0);
        world.addAtEmptyLocation(fungus, depth);
        new FungusAi(fungus, this);
        return fungus;
    }

    public Creature newBat(int depth) {
        Creature bat = new Creature(world, 'b', ANSITileColor.BRIGHT_YELLOW, "bat", 15, 5, 0);
        world.addAtEmptyLocation(bat, depth);
        new BatAi(bat);
        return bat;
    }

    public Creature newZombie(int depth, Creature player) {
        Creature zombie = new Creature(world, 'z', ANSITileColor.WHITE, "zombie", 50, 10, 10);
        world.addAtEmptyLocation(zombie, depth);
        new ZombieAi(zombie, player);
        return zombie;
    }

    public Creature newGoblin(int depth, Creature player) {
        Creature goblin = new Creature(world, 'g', ANSITileColor.BRIGHT_GREEN, "goblin", 66, 15, 5);
        new GoblinAi(goblin, player);
        goblin.equip(randomWeapon(depth));
        goblin.equip(randomArmor(depth));
        world.addAtEmptyLocation(goblin, depth);
        return goblin;
    }

    public Item newRock(int depth) {
        Item rock = new Item(',', ANSITileColor.YELLOW, "rock", null);
        rock.modifyThrownAttackValue(5);
        world.addAtEmptyLocation(rock, depth);
        return rock;
    }

    public Item newVictoryItem(int depth) {
        Item item = new Item('*', ANSITileColor.BRIGHT_WHITE, "teddy bear", null);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newBread(int depth) {
        Item item = new Item('%', ANSITileColor.YELLOW, "bread", null);
        item.modifyFoodValue(400);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newFruit(int depth) {
        Item item = new Item('%', ANSITileColor.BRIGHT_RED, "apple", null);
        item.modifyFoodValue(100);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newDagger(int depth) {
        Item item = new Item(')', ANSITileColor.WHITE, "dagger", null);
        item.modifyAttackValue(5);
        item.modifyThrownAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newSword(int depth) {
        Item item = new Item(')', ANSITileColor.BRIGHT_WHITE, "sword", null);
        item.modifyAttackValue(10);
        item.modifyThrownAttackValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newStaff(int depth) {
        Item item = new Item(')', ANSITileColor.YELLOW, "staff", null);
        item.modifyAttackValue(5);
        item.modifyDefenseValue(3);
        item.modifyThrownAttackValue(3);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newBow(int depth) {
        Item item = new Item(')', ANSITileColor.YELLOW, "bow", null);
        item.modifyAttackValue(1);
        item.modifyRangedAttackValue(5);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newEdibleWeapon(int depth) {
        Item item = new Item(')', ANSITileColor.YELLOW, "baguette", null);
        item.modifyAttackValue(3);
        item.modifyFoodValue(100);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newLightArmor(int depth) {
        Item item = new Item('[', ANSITileColor.GREEN, "tunic", null);
        item.modifyDefenseValue(2);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newMediumArmor(int depth) {
        Item item = new Item('[', ANSITileColor.WHITE, "chainmail", null);
        item.modifyDefenseValue(4);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item newHeavyArmor(int depth) {
        Item item = new Item('[', ANSITileColor.BRIGHT_WHITE, "platemail", null);
        item.modifyDefenseValue(6);
        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item randomWeapon(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newDagger(depth);
            case 1:
                return newSword(depth);
            case 2:
                return newBow(depth);
            default:
                return newStaff(depth);
        }
    }

    public Item randomArmor(int depth) {
        switch ((int) (Math.random() * 3)) {
            case 0:
                return newLightArmor(depth);
            case 1:
                return newMediumArmor(depth);
            default:
                return newHeavyArmor(depth);
        }
    }

    private Item newPotionOfHealth(int depth) {
        String appearance = potionAppearances.get(0);
        final Item item = new Item('!', potionColors.get(appearance), "health potion", appearance);
        item.setQuaffEffect(new Effect(1) {
            public void start(Creature creature) {
                if (creature.hp() == creature.maxHp())
                    return;

                creature.modifyHp(15, "Killed by a health potion?");
                creature.doAction(item, "look healthier");
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newPotionOfMana(int depth) {
        String appearance = potionAppearances.get(1);
        final Item item = new Item('!', potionColors.get(appearance), "mana potion", appearance);
        item.setQuaffEffect(new Effect(1) {
            public void start(Creature creature) {
                if (creature.mana() == creature.maxMana())
                    return;

                creature.modifyMana(10);
                creature.doAction(item, "look restored");
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newPotionOfSlowHealth(int depth) {
        String appearance = potionAppearances.get(2);
        final Item item = new Item('!', potionColors.get(appearance), "slow health potion", appearance);
        item.setQuaffEffect(new Effect(100) {
            public void start(Creature creature) {
                creature.doAction(item, "look a little better");
            }

            public void update(Creature creature) {
                super.update(creature);
                creature.modifyHp(1, "Killed by a slow health potion?");
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newPotionOfPoison(int depth) {
        String appearance = potionAppearances.get(3);
        final Item item = new Item('!', potionColors.get(appearance), "poison potion", appearance);
        item.setQuaffEffect(new Effect(20) {
            public void start(Creature creature) {
                creature.doAction(item, "look sick");
            }

            public void update(Creature creature) {
                super.update(creature);
                creature.modifyHp(-1, "Died of poison.");
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newPotionOfWarrior(int depth) {
        String appearance = potionAppearances.get(4);
        final Item item = new Item('!', potionColors.get(appearance), "warrior's potion", appearance);
        item.setQuaffEffect(new Effect(20) {
            public void start(Creature creature) {
                creature.modifyAttackValue(5);
                creature.modifyDefenseValue(5);
                creature.doAction(item, "look stronger");
            }

            public void end(Creature creature) {
                creature.modifyAttackValue(-5);
                creature.modifyDefenseValue(-5);
                creature.doAction("look less strong");
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newPotionOfArcher(int depth) {
        String appearance = potionAppearances.get(5);
        final Item item = new Item('!', potionColors.get(appearance), "archers potion", appearance);
        item.setQuaffEffect(new Effect(20) {
            public void start(Creature creature) {
                creature.modifyVisionRadius(3);
                creature.doAction(item, "look more alert");
            }

            public void end(Creature creature) {
                creature.modifyVisionRadius(-3);
                creature.doAction("look less alert");
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newPotionOfExperience(int depth) {
        String appearance = potionAppearances.get(6);
        final Item item = new Item('!', potionColors.get(appearance), "experience potion", appearance);
        item.setQuaffEffect(new Effect(20) {
            public void start(Creature creature) {
                creature.doAction(item, "look more experienced");
                creature.modifyXp(creature.level() * 5);
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    public Item randomPotion(int depth) {
        switch ((int) (Math.random() * 9)) {
            case 0:
                return newPotionOfHealth(depth);
            case 1:
                return newPotionOfHealth(depth);
            case 2:
                return newPotionOfMana(depth);
            case 3:
                return newPotionOfMana(depth);
            case 4:
                return newPotionOfSlowHealth(depth);
            case 5:
                return newPotionOfPoison(depth);
            case 6:
                return newPotionOfWarrior(depth);
            case 7:
                return newPotionOfArcher(depth);
            default:
                return newPotionOfExperience(depth);
        }
    }

    public Item newWhiteMagesSpellbook(int depth) {
        Item item = new Item('+', ANSITileColor.BRIGHT_WHITE, "white mage's spellbook", null);
        item.addWrittenSpell("minor heal", 4, new Effect(1) {
            public void start(Creature creature) {
                if (creature.hp() == creature.maxHp())
                    return;

                creature.modifyHp(20, "Killed by a minor heal spell?");
                creature.doAction("look healthier");
            }
        });

        item.addWrittenSpell("major heal", 8, new Effect(1) {
            public void start(Creature creature) {
                if (creature.hp() == creature.maxHp())
                    return;

                creature.modifyHp(50, "Killed by a major heal spell?");
                creature.doAction("look healthier");
            }
        });

        item.addWrittenSpell("slow heal", 12, new Effect(50) {
            public void update(Creature creature) {
                super.update(creature);
                creature.modifyHp(2, "Killed by a slow heal spell?");
            }
        });

        item.addWrittenSpell("inner strength", 16, new Effect(50) {
            public void start(Creature creature) {
                creature.modifyAttackValue(2);
                creature.modifyDefenseValue(2);
                creature.modifyVisionRadius(1);
                creature.modifyRegenHpPer1000(10);
                creature.modifyRegenManaPer1000(-10);
                creature.doAction("seem to glow with inner strength");
            }

            public void update(Creature creature) {
                super.update(creature);
                if (Math.random() < 0.25)
                    creature.modifyHp(1, "Killed by inner strength spell?");
            }

            public void end(Creature creature) {
                creature.modifyAttackValue(-2);
                creature.modifyDefenseValue(-2);
                creature.modifyVisionRadius(-1);
                creature.modifyRegenHpPer1000(-10);
                creature.modifyRegenManaPer1000(10);
            }
        });

        world.addAtEmptyLocation(item, depth);
        return item;
    }

    private Item newBlueMagesSpellbook(int depth) {
        Item item = new Item('+', ANSITileColor.BRIGHT_BLUE, "blue mage's spellbook", null);

        item.addWrittenSpell("blood to mana", 1, new Effect(1) {
            public void start(Creature creature) {
                int amount = Math.min(creature.hp() - 1, creature.maxMana() - creature.mana());
                creature.modifyHp(-amount, "Killed by a blood to mana spell.");
                creature.modifyMana(amount);
            }
        });

        item.addWrittenSpell("blink", 6, new Effect(1) {
            public void start(Creature creature) {
                creature.doAction("fade out");

                int mx = 0;
                int my = 0;

                do {
                    mx = (int) (Math.random() * 11) - 5;
                    my = (int) (Math.random() * 11) - 5;
                }
                while (!creature.canEnter(creature.position.withRelativeX(mx).withRelativeY(my))
                        && creature.canSee(creature.position.withRelativeX(mx).withRelativeY(my)));

                creature.moveBy(Positions.create3DPosition(mx, my, 0));

                creature.doAction("fade in");
            }
        });

        item.addWrittenSpell("summon bats", 11, new Effect(1) {
            public void start(Creature creature) {
                for (int ox = -1; ox < 2; ox++) {
                    for (int oy = -1; oy < 2; oy++) {
                        int nx = creature.position.getX() + ox;
                        int ny = creature.position.getY() + oy;
                        if (ox == 0 && oy == 0
                                || creature.creature(Positions.create3DPosition(nx, ny, creature.position.getZ())) != null)
                            continue;

                        Creature bat = newBat(0);

                        if (!bat.canEnter(Positions.create3DPosition(nx, ny, creature.position.getZ()))) {
                            world.remove(bat);
                            continue;
                        }

                        bat.position = Positions.create3DPosition(nx, ny, creature.position.getZ());

                        creature.summon(bat);
                    }
                }
            }
        });

        item.addWrittenSpell("detect creatures", 16, new Effect(75) {
            public void start(Creature creature) {
                creature.doAction("look far off into the distance");
                creature.modifyDetectCreatures(1);
            }

            public void end(Creature creature) {
                creature.modifyDetectCreatures(-1);
            }
        });
        world.addAtEmptyLocation(item, depth);
        return item;
    }


    public Item randomSpellBook(int depth) {
        if ((int) (Math.random() * 2) == 0) {
            return newWhiteMagesSpellbook(depth);
        }
        return newBlueMagesSpellbook(depth);
    }

}
