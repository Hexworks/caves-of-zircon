package rltut.views;

import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.TileColors;
import org.hexworks.zircon.api.Tiles;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import org.hexworks.zircon.api.screen.Screen;
import rltut.ai.Creature;
import rltut.algorithm.FieldOfView;
import rltut.dialogs.HelpDialog;
import rltut.entities.Item;
import rltut.factories.StuffFactory;
import rltut.world.Tile;
import rltut.world.WorldBuilder;
import rltut.world.WorldJava;

import java.util.ArrayList;
import java.util.List;

public class PlayView implements View {
    private WorldJava world = new WorldBuilder(90, 32, 5)
            .makeCaves()
            .build();
    private Creature player;
    private int screenWidth = 80;
    private int screenHeight = 23;
    private List<String> messages = new ArrayList<>();
    private FieldOfView fov = new FieldOfView(world);

    private View subview;
    private TileGrid tileGrid;
    private Screen screen;

    PlayView(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
        this.screen = Screens.createScreenFor(tileGrid);
        StuffFactory factory = new StuffFactory(world);
        createCreatures(factory);
        createItems(factory);
    }

    private void createCreatures(StuffFactory factory) {
        player = factory.newPlayer(messages, fov);
        for (int z = 0; z < world.depth(); z++) {
            for (int i = 0; i < 4; i++) {
                factory.newFungus(z);
            }
            for (int i = 0; i < 10; i++) {
                factory.newBat(z);
            }
            for (int i = 0; i < z * 2 + 1; i++) {
                factory.newZombie(z, player);
                factory.newGoblin(z, player);
            }
        }
    }

    private void createItems(StuffFactory factory) {
        for (int z = 0; z < world.depth(); z++) {
            for (int i = 0; i < world.width() * world.height() / 50; i++) {
                factory.newRock(z);
            }
            factory.newFruit(z);
            factory.newEdibleWeapon(z);
            factory.newBread(z);
            factory.randomArmor(z);
            factory.randomWeapon(z);
            factory.randomWeapon(z);
            for (int i = 0; i < z + 1; i++) {
                factory.randomPotion(z);
                factory.randomSpellBook(z);
            }
        }
        factory.newVictoryItem(world.depth() - 1);
    }

    private int getScrollX() {
        return Math.max(0, Math.min(player.position.getY() - screenWidth / 2, world.width() - screenWidth));
    }

    private int getScrollY() {
        return Math.max(0, Math.min(player.position.getY() - screenHeight / 2, world.height() - screenHeight));
    }

    @Override
    public void dock() {
        screen.display();
        screen.onInput(input -> {
            System.out.println("input: " + input);
            respondToUserInput(input);
            update();
        });
        update();
    }

    private void update() {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(tileGrid, left, top);
        displayMessages(tileGrid, messages);

        String stats = String.format(" %3d/%3d hp   %d/%d mana   %8s", player.hp(), player.maxHp(), player.mana(), player.maxMana(), hunger());
        tileGrid.write(stats, Positions.create(1, 23));
    }

    private String hunger() {
        if (player.food() < player.maxFood() * 0.10)
            return "Starving";
        else if (player.food() < player.maxFood() * 0.25)
            return "Hungry";
        else if (player.food() > player.maxFood() * 0.90)
            return "Stuffed";
        else if (player.food() > player.maxFood() * 0.75)
            return "Full";
        else
            return "";
    }

    private void displayMessages(TileGrid tileGrid, List<String> messages) {
        int top = screenHeight - messages.size();
        for (int i = 0; i < messages.size(); i++) {
            tileGrid.write(messages.get(i), Positions.create(0, top + i));
        }
    }

    private void displayTiles(TileGrid tileGrid, int left, int top) {
        fov.update(player.position, player.visionRadius());
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;
                if (player.canSee(Positions.create3DPosition(wx, wy, player.position.getZ()))) {
                    tileGrid.setTileAt(Positions.create(x, y), Tiles.newBuilder()
                            .withCharacter(world.glyph(Positions.create3DPosition(wx, wy, player.position.getZ())))
                            .withForegroundColor(world.color(Positions.create3DPosition(wx, wy, player.position.getZ())))
                            .buildCharacterTile());
                } else {
                    tileGrid.setTileAt(Positions.create(x, y), Tiles.newBuilder()
                            .withCharacter(fov.tile(Positions.create3DPosition(wx, wy, player.position.getZ())).glyph())
                            .withBackgroundColor(TileColors.create(64, 64, 64))
                            .buildCharacterTile());
                }
            }
        }
    }

    @Override
    public View respondToUserInput(Input input) {
        int level = player.level();
        input.asKeyStroke().map(ks -> {
            switch (ks.inputType()) {
                case ArrowLeft:
                    player.moveBy(Positions.create3DPosition(-1, 0, 0));
                    break;
                case ArrowRight:
                    player.moveBy(Positions.create3DPosition(1, 0, 0));
                    break;
                case ArrowUp:
                    player.moveBy(Positions.create3DPosition(0, -1, 0));
                    break;
                case ArrowDown:
                    player.moveBy(Positions.create3DPosition(0, 1, 0));
                    break;

            }
            switch (ks.getCharacter()) {
                case 'H':
                    player.moveBy(Positions.create3DPosition(-1, 0, 0));
                    break;
                case 'L':
                    player.moveBy(Positions.create3DPosition(1, 0, 0));
                    break;
                case 'K':
                    player.moveBy(Positions.create3DPosition(0, -1, 0));
                    break;
                case 'J':
                    player.moveBy(Positions.create3DPosition(0, 1, 0));
                    break;
                case 'Y':
                    player.moveBy(Positions.create3DPosition(-1, -1, 0));
                    break;
                case 'U':
                    player.moveBy(Positions.create3DPosition(1, -1, 0));
                    break;
                case 'B':
                    player.moveBy(Positions.create3DPosition(-1, 1, 0));
                    break;
                case 'N':
                    player.moveBy(Positions.create3DPosition(1, 1, 0));
                    break;
                case 'D':
                    subview = new DropView(player);
                    break;
                case 'E':
                    subview = new EatView(player);
                    break;
                case 'W':
                    subview = new EquipView(player);
                    break;
                case 'X':
                    subview = new ExamineView(player);
                    break;
                case ';':
                    subview = new LookScreen(player, "Looking",
                            player.position.getX() - getScrollX(),
                            player.position.getY() - getScrollY());
                    break;
                case 'T':
                    subview = new ThrowView(player,
                            player.position.getX() - getScrollX(),
                            player.position.getY() - getScrollY());
                    break;
                case 'F':
                    if (player.weapon() == null || player.weapon().rangedAttackValue() == 0)
                        player.notify("You don't have a ranged weapon equiped.");
                    else
                        subview = new FireWeaponScreen(player,
                                player.position.getX() - getScrollX(),
                                player.position.getY() - getScrollY());
                    break;
                case 'Q':
                    subview = new QuaffView(player);
                    break;
                case 'R':
                    subview = new ReadView(player,
                            player.position.getX() - getScrollX(),
                            player.position.getY() - getScrollY());
                    break;
                case 'g':
                case ',':
                    player.pickup();
                    break;
                case '<':
                    if (userIsTryingToExit())
                        return userExits();
                    else
                        player.moveBy(Positions.create3DPosition(0, 0, -1));
                    break;
                case '>':
                    player.moveBy(Positions.create3DPosition(0, 0, 1));
                    break;
                case '?':
                    subview = new HelpDialog();
                    break;
            }
            return this;
        });
        if (player.level() > level) {
            subview = new LevelUpScreen(player, player.level() - level);
        }
        if (subview == null) {
            world.update();
        }
        if (player.hp() < 1) {
            return new LoseView(tileGrid, player);
        }
        return this;
    }

    private boolean userIsTryingToExit() {
        return player.position.getZ() == 0 && world.tile(player.position) == Tile.STAIRS_UP;
    }

    private View userExits() {
        for (Item item : player.inventory().getItems()) {
            if (item != null && item.name().equals("teddy bear"))
                return new WinScreen(tileGrid);
        }
        player.modifyHp(0, "Died while cowardly fleeing the caves.");
        return new LoseView(tileGrid, player);
    }
}
