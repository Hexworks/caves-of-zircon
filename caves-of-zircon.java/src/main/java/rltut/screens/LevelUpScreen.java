package rltut.screens;

import java.awt.event.KeyEvent;
import java.util.List;

import rltut.Creature;
import rltut.LevelUpController;
import asciiPanel.AsciiPanel;

public class LevelUpScreen implements Screen {
	private LevelUpController controller;
	private Creature player;
	private int picks;
	
	public LevelUpScreen(Creature player, int picks){
		this.controller = new LevelUpController();
		this.player = player;
		this.picks = picks;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		List<String> options = controller.getLevelUpOptions();
		
		int y = 5;
		terminal.clear(' ', 5, y, 30, options.size() + 2);
		terminal.write("   Choose a level up bonus    ", 5, y++);
		terminal.write("------------------------------", 5, y++);
		
		for (int i = 0; i < options.size(); i++){
			terminal.write(String.format("[%d] %s", i+1, options.get(i)), 5, y++);
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		List<String> options = controller.getLevelUpOptions();
		String chars = "";
		
		for (int i = 0; i < options.size(); i++){
			chars = chars + Integer.toString(i+1);
		}
		
		int i = chars.indexOf(key.getKeyChar());
		
		if (i < 0)
			return this;
		
		controller.getLevelUpOption(options.get(i)).invoke(player);
		
		if (--picks < 1)
			return null;
		else
			return this;
	}
}
