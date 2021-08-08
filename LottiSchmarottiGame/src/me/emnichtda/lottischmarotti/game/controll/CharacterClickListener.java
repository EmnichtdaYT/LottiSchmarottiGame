package me.emnichtda.lottischmarotti.game.controll;

import javafx.event.Event;
import javafx.event.EventHandler;
import me.emnichtda.lottischmarotti.game.model.Game;

public class CharacterClickListener implements EventHandler<Event>{
	
	private int charId;
	private Game game;
	private int ownerNumber;
	
	public CharacterClickListener(Game game, int charId, int ownerId) {
		this.game = game;
		this.charId = charId;
		this.ownerNumber = ownerId;
	}

	@Override
	public void handle(Event e){
		if(ownerNumber == game.getPlayerNumber())
		game.selectedCharacter(charId);
	}
	
	
}
