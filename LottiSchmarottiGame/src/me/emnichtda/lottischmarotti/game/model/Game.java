package me.emnichtda.lottischmarotti.game.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javafx.concurrent.Task;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.view.GameListScreen;

public class Game implements Showable {

	public static final int MAX_PLAYERS = 3;

	private int playersConnected;

	private Main main;
	private GameListScreen gameListScreen;

	private String ip;
	private int port;
	private String name;

	private DataOutputStream out;
	private DataInputStream in;

	private Socket socket;

	private int diceDecision = -1;

	private int playerNumber;
	
	private ArrayList<GameField> fields = new ArrayList<>();

	private boolean characterDescision;
	
	public void setCharacterDescision(boolean charDesc) {
		characterDescision = charDesc;
	}
	
	public Game(Main main, GameListScreen gameListScreen, String ip, int port, String name, int playersConnected) {
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.playersConnected = playersConnected;
		this.main = main;
		this.gameListScreen = gameListScreen;
		
		initFields();
		
	}
	
	//Wayyyy to long method xD
	private void initFields() {
		fields.add(new GameField(main.getGameScreen(), 705, 87));
		fields.add(new GameField(main.getGameScreen(), 697, 176));
		fields.add(new GameField(main.getGameScreen(), 714, 276));
		fields.add(new GameField(main.getGameScreen(), 634, 335));
		fields.add(new GameField(main.getGameScreen(), 620, 448)); //5
		fields.add(new GameField(main.getGameScreen(), 557, 534));
		fields.add(new GameField(main.getGameScreen(), 479, 598));
		fields.add(new GameField(main.getGameScreen(), 366, 628));
		fields.add(new GameField(main.getGameScreen(), 266, 590));
		fields.add(new GameField(main.getGameScreen(), 182, 528)); //10
		fields.add(new GameField(main.getGameScreen(), 122, 436));
		fields.add(new GameField(main.getGameScreen(), 115, 324));
		fields.add(new GameField(main.getGameScreen(), 159, 228));
		fields.add(new GameField(main.getGameScreen(), 228, 145));
		fields.add(new GameField(main.getGameScreen(), 325, 98));  //15
		fields.add(new GameField(main.getGameScreen(), 437, 111));
		fields.add(new GameField(main.getGameScreen(), 520, 163));
		fields.add(new GameField(main.getGameScreen(), 476, 250));
		fields.add(new GameField(main.getGameScreen(), 386, 216));
		fields.add(new GameField(main.getGameScreen(), 295, 228)); //20
		fields.add(new GameField(main.getGameScreen(), 245, 306));
		fields.add(new GameField(main.getGameScreen(), 218, 397));
		fields.add(new GameField(main.getGameScreen(), 290, 469));
		fields.add(new GameField(main.getGameScreen(), 367, 511));
		fields.add(new GameField(main.getGameScreen(), 456, 479)); //25
		fields.add(new GameField(main.getGameScreen(), 520, 394));
		fields.add(new GameField(main.getGameScreen(), 380, 352));
		
	}

	public void show() {
		gameListScreen.addGame(this);
	}

	public int getPlayersConnected() {
		return playersConnected;
	}

	public int getPort() {
		return port;
	}

	public Main getMain() {
		return main;
	}

	public void start() throws IOException, NotALottiSchmarottiGameException, LottiSchmarottiError {

		socket = new Socket(ip, port);

		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());

		String initial = in.readUTF();

		if (!initial.toUpperCase().startsWith("GET 1")) {
			socket.close();
			throw new NotALottiSchmarottiGameException(
					"Responded with a non LottiSchmarotti typical answer - should be 'GET 1'");
		}

		out.writeUTF("POST 1 PLAYER " + name);

		String clients = in.readUTF();

		if (clients.startsWith("POST 40")) {
			throw new LottiSchmarottiError(clients);
		}

		String splitted[] = clients.split(":");
		if (splitted.length != 3) {
			socket.close();
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
		
		playerNumber = players.indexOf(name) + 1;

		main.setPlayersConnected(players);

		GameInput task = new GameInput();

		GameParser parser = new GameParser(main, this);
		CharacterPositionParser positionParser = new CharacterPositionParser(main);
		
		task.messageProperty().addListener((idfk, np, input) -> {
			System.out.println(input);
			positionParser.parse(input);
			try {
				parser.parse(input);
			} catch (NotALottiSchmarottiGameException e) {
				main.showError("Error", e.getLocalizedMessage());
			}
		});

		new Thread(task).start();
		
		

	}

	class GameInput extends Task<Game> {

		@Override
		protected Game call() throws Exception {
			while (true) {
				try {
					String line = in.readUTF();
					updateMessage(line);
				} catch (IOException e) {
					main.showError("Error", e.getLocalizedMessage());
				}
			}
		}
	}

	public DataOutputStream getOut() {
		return out;
	}

	public DataInputStream getIn() {
		return in;
	}

	public void close() {
		if (socket != null)
			try {
				if (out != null)
					out.writeUTF("POST 21 Disconnecting.");
				socket.close();
			} catch (IOException e) {
			} // Dann eben nicht lmao
	}

	public int getDiceDecision() {
		return diceDecision;
	}

	public void requestContinueDecision() {
		diceDecision = -1;
		main.getGameScreen().showRollButton();
		main.getGameScreen().showContinueButton();
		main.getGameScreen().getContinueButton().setOnAction((e) -> {
			diceDecision = 0;
		});
		main.getGameScreen().getRollButton().setOnAction((e) -> {
			diceDecision = 1;
		});
	}

	public void requestCharacterDecision() {
		characterDescision = true;
		main.getGameScreen().hideRollButton();
		main.getGameScreen().hideContinueButton();
		diceDecision = -1;
		
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public ArrayList<GameField> getFields() {
		return fields;
	}

	public void selectedCharacter(int charId) {
		if(!characterDescision) return;
		try {
			System.out.println("SENDING POST 2" + charId);
			out.writeUTF("POST 2 " + charId);
		} catch (IOException e) {
			main.showError("Error", e.getLocalizedMessage());
		}
		characterDescision = false;
	}

}
