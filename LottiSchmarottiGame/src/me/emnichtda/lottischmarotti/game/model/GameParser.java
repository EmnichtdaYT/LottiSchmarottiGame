package me.emnichtda.lottischmarotti.game.model;

import java.io.IOException;
import java.util.ArrayList;

import javafx.concurrent.Task;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.view.GameCharacter;

public class GameParser {

	private Main main;
	private Game game;

	public GameParser(Main main, Game game) {
		this.main = main;
		this.game = game;
	}

	public void parse(String input) throws NotALottiSchmarottiGameException {

		String inputUpper = input.toUpperCase();

		if (inputUpper.startsWith("POST 0")) {
			System.out.println("ack " + inputUpper);
			return;
		}

		if (inputUpper.startsWith("POST 22")) {

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
					players.add(name.substring(1, name.length() - 1));
				}
			}

			main.setPlayersConnected(players);

		} else if (inputUpper.startsWith("POST 40")) {
			main.showError("Error", input);
		} else if (inputUpper.startsWith("POST 41")) {
			main.showError("Error", input);
		} else if (inputUpper.startsWith("POST 21")) {
			main.showError("Error", "The server is shutting down... " + input);
			game.close();
		} else if (inputUpper.startsWith("GET 2")) {
			Task<String> t = new DiceDecisionHandler(inputUpper);
			t.messageProperty().addListener((idfk, np, current) -> {
				System.out.println(current);
				if (current.contains("CONTINUE?")) {
					game.requestContinueDecision();
				} else if(current.contains("SELECT DIFFERENT ONE")){
					main.showError("Your character can't go there!", "Your chatacter is unable to go the amount of steps you rolled! Please select a different one!");
					game.requestCharacterDecision();
				}else {
					game.requestCharacterDecision();
				}
			});
			new Thread(t).start();
		} else {
			if(!inputUpper.startsWith("POST 24"))
			main.showError("Error", "Unrecognized input received from server " + input);
		}

	}

	class DiceDecisionHandler extends Task<String> {
		String inputUpper;

		public DiceDecisionHandler(String inputUpper) {
			this.inputUpper = inputUpper;
		}

		@Override
		protected String call() throws Exception {
			updateMessage(inputUpper);
			while (game.getDiceDecision() == -1) {
				try {
					Thread.sleep(900);
				} catch (InterruptedException e) {
				}
				try {
					game.getOut().writeUTF("POST 23 waiting");
				} catch (IOException e) {
					main.showError("Error", "Unable to send timeout request " + e.getLocalizedMessage());
				}
			}
			if (inputUpper.contains("CONTINUE")) {
				if (game.getDiceDecision() == 1) {
					game.getOut().writeUTF("POST 2 c");
				} else if (game.getDiceDecision() == 0) {
					game.getOut().writeUTF("POST 2 n");
				}
			} else {

			}
			return null;
		}
	}
}
