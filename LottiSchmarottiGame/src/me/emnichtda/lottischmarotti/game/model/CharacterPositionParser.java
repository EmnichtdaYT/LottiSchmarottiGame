package me.emnichtda.lottischmarotti.game.model;

import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.view.GameCharacter;

public class CharacterPositionParser {

	private Main main;

	public CharacterPositionParser(Main main) {
		this.main = main;
	}
	
	public void parse(String input) {		
		
		String inputUpper = input.toUpperCase();
		
		if (inputUpper.startsWith("POST 24")) {
			String[] fields = inputUpper.substring(8).split("; ");
			for (String field : fields) {
				if (!field.contains("NULL")) {
					System.out.println(field);
					int fieldId;
					int charId;
					int owner;
					String[] splitted = field.split(":");
					if(splitted.length!=2) main.showError("Error!", "Got invalid character information from server. (Has no ':')");
					try {
						fieldId = Integer.parseInt(splitted[0]);
						charId = Integer.parseInt(splitted[1].substring(1, 2));
						splitted = field.split(",");
						System.out.println(splitted[1].substring(1, 2));
						owner = Integer.parseInt(splitted[1].substring(1, 2));
					} catch (NumberFormatException e) {
						main.showError("Error!", "Got invalid character information from server.");
						break;
					}
					for (GameCharacter c : main.getGameScreen().getCharacters()) {
						if (c.getCharId() == charId && c.getPlayerNumber() == owner) {
							c.setStanding(fieldId);
							break;
						}
					}
				}
			}
		}
	}
}
