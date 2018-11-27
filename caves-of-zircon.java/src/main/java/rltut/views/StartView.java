package rltut.views;

import org.hexworks.zircon.api.ColorThemes;
import org.hexworks.zircon.api.Components;
import org.hexworks.zircon.api.Positions;
import org.hexworks.zircon.api.Screens;
import org.hexworks.zircon.api.Sizes;
import org.hexworks.zircon.api.component.Button;
import org.hexworks.zircon.api.component.Panel;
import org.hexworks.zircon.api.grid.TileGrid;
import org.hexworks.zircon.api.input.Input;
import org.hexworks.zircon.api.input.InputType;
import org.hexworks.zircon.api.screen.Screen;

import static org.hexworks.zircon.api.component.ComponentAlignment.CENTER;
import static org.hexworks.zircon.api.component.ComponentAlignment.TOP_CENTER;

public class StartView implements View {

    private TileGrid tileGrid;
    private Screen screen;

    public StartView(TileGrid tileGrid) {
        this.tileGrid = tileGrid;
        this.screen = Screens.createScreenFor(tileGrid);
    }

    @Override
    public void dock() {
        final Panel welcomePanel = Components.panel()
                .withSize(Sizes.create(35, 15))
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withTitle("Welcome!")
                .withAlignmentWithin(screen, CENTER)
                .build();

        welcomePanel.addComponent(Components.textBox()
                .withContentWidth(welcomePanel.getContentSize().getWidth() - 2)
                .addParagraph("Welcome to Caves of Zircon!")
                .addParagraph("Click the button below to start.")
                .withPosition(Positions.offset1x1()));

        final Button startButton = Components.button()
                .withText("Start")
                .withAlignmentWithin(welcomePanel, CENTER)
                .build();

        startButton.onMouseClicked(mouseAction -> new PlayView(tileGrid).dock());

        welcomePanel.addComponent(startButton);

        screen.addComponent(welcomePanel);
        screen.applyColorTheme(ColorThemes.arc());
        screen.display();
    }

    @Override
    public View respondToUserInput(Input input) {
        return input.inputTypeIs(InputType.Enter) ? new PlayView(tileGrid) : this;
    }
}
