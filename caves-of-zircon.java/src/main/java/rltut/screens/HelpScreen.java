package rltut.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("roguelike help", 1);
		terminal.write("Descend the Caves Of Slight Danger, find the lost Teddy Bear, and return to", 1, 3);
		terminal.write("the surface to win. Use what you find to avoid dying.", 1, 4);
		
		int y = 6;
		terminal.write("[g] or [,] to pick up", 2, y++);
		terminal.write("[d] to drop", 2, y++);
		terminal.write("[e] to eat", 2, y++);
		terminal.write("[w] to wear or wield", 2, y++);
		terminal.write("[?] for help", 2, y++);
		terminal.write("[x] to examine your items", 2, y++);
		terminal.write("[;] to look around", 2, y++);
		terminal.write("[t] to throw an item", 2, y++);
		terminal.write("[q] to quaff a potion", 2, y++);
		terminal.write("[r] to read something", 2, y++);
		
		terminal.writeCenter("-- press any key to continue --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}
}
