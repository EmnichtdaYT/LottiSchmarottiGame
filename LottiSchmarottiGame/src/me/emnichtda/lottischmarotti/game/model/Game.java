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

	private String ip;
	private int port;
	private int playersConnected;
	private Main main;
	private GameListScreen gameListScreen;
	private String name;

	private DataOutputStream out;
	private DataInputStream in;

	private Socket socket;

	public Game(Main main, GameListScreen gameListScreen, String ip, int port, String name, int playersConnected) {
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.playersConnected = playersConnected;
		this.main = main;
		this.gameListScreen = gameListScreen;
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
		
		if(clients.startsWith("POST 40")) {
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
				players.add(name.substring(1, name.length()-1));
			}
		}

		main.setPlayersConnected(players);
		
		GameInput task = new GameInput();

		GameParser parser = new GameParser(main, this);
		
		task.messageProperty().addListener((idfk, np, input) -> {
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
			while(true) {
				try {
					String line = in.readUTF();
					updateMessage(line);
				} catch (IOException e) { main.showError("Error", e.getLocalizedMessage()); }
			}
		}
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

}
