package rltut.views;

import org.hexworks.zircon.api.input.Input;

public interface View {

//    Screen getScreen();

    void dock();

    View respondToUserInput(Input input);
}
