package me.emnichtda.lottischmarotti.game.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.view.GameListScreen;

public class GameLister {

	private Main main;
	private String ip;
	private int port;
	private String name;
	private GameListScreen gameListScreen;

	public GameLister(Main main, GameListScreen gameListScreen, String ip, int port, String name) {
		this.main = main;
		this.ip = ip;
		this.port = port;
		this.name = name;
		this.gameListScreen = gameListScreen;
	}

	public void list() throws UnknownHostException, IOException, NotALottiSchmarottiGameException, LottiSchmarottiError {

		Socket s = new Socket(ip, port);

		DataOutputStream out = null;
		DataInputStream in = null;

		out = new DataOutputStream(s.getOutputStream());
		in = new DataInputStream(s.getInputStream());

		String initial = in.readUTF();
		if (!initial.toUpperCase().startsWith("GET 1")) {
			s.close();
			throw new NotALottiSchmarottiGameException(
					"Responded with a non LottiSchmarotti typical answer - should be 'GET 1'");
		}
		out.writeUTF("POST 1 CLIENT " + name);

		String clients = in.readUTF();
		String splitted[] = clients.split(":");
		if (splitted.length != 3) {
			s.close();
			throw new NotALottiSchmarottiGameException("Responded with a non LottiSchmarotti typical answer");
		}
		double playerCount = 0;
		for (char c : splitted[1].toCharArray()) {
			if (c == '\'') {
				playerCount += 0.5;
			}
		}

		out.writeUTF("POST 21 Got all informations");

		s.close();

		new Game(main, gameListScreen, ip, port, name, (int) playerCount).show();

	}

	public Main getMain() {
		return main;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public GameListScreen getGameListScreen() {
		return gameListScreen;
	}

}
