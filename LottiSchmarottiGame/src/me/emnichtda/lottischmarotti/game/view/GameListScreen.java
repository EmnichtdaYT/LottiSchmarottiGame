package me.emnichtda.lottischmarotti.game.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.Showable;

public class GameListScreen extends GridPane implements Showable{

	private Main main;
	private String ip;
	private int port;
	private String name;
	
	public GameListScreen(Main main, String ip, int port, String name) {
		this.main = main;
		this.ip = ip;
		this.port = port;
		this.name = name;
	}
	
	@Override
	public void show() {
		Scene scene = new Scene(this, 500, 600);
		main.setScene(scene);		
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
	

}
