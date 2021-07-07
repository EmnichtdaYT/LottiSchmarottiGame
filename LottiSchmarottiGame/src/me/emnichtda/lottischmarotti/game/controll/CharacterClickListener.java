package me.emnichtda.lottischmarotti.game.controll;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.view.GameCharacter;

public class CharacterClickListener implements EventHandler<Event>{
	
	private Main main;
	private GameCharacter character;
	
	public CharacterClickListener(Main main, GameCharacter character) {
		this.main = main;
		this.character = character;
	}

	@Override
	public void handle(Event e){
		//TODO implement
	}
	
	
}
