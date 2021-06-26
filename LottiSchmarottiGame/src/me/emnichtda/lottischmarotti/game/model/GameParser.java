package me.emnichtda.lottischmarotti.game.model;

import java.util.ArrayList;

import me.emnichtda.lottischmarotti.game.main.Main;

public class GameParser {
	
	private Main main;
	private Game game;
	
	public GameParser(Main main, Game game) { 
		this.main = main;
		this.game = game;
	}
	
	public void parse(String input) throws NotALottiSchmarottiGameException {
		
		String inputUpper = input.toUpperCase();
		
		if(inputUpper.startsWith("POST 22")) {

			String splitted[] = input.split(":");
			if (splitted.length != 3) {
				game.close();
				throw new NotALottiSchmarottiGameException("Responded with a non LottiSchmarotti typical answer");
			}

			ArrayList<String> players = new ArrayList<>();

			for (String name : splitted[1].split(" ")) {
				if (name.length() > 0) {
					if (name.equals("Clients"))
						break;
					players.add(name.substring(1, name.length()-1));
				}
			}

			main.setPlayersConnected(players);

		}else if(inputUpper.startsWith("POST 40")) {
			main.showError("Error", input);
		}else if(inputUpper.startsWith("POST 41")) {
			main.showError("Error", input);
		}else if(inputUpper.startsWith("POST 21")) {
			main.showError("Error", "The server is shutting down... " + input);
			game.close();
		}
	}
}
