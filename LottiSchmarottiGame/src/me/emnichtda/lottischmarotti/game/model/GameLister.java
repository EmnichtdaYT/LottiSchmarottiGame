package me.emnichtda.lottischmarotti.game.model;

import me.emnichtda.lottischmarotti.game.main.Main;

public class GameLister {

	private Main main;
	private String ip;
	private int port;
	private String name;
	
	public GameLister(Main main, String ip, int port, String name) {
		this.main = main;
		this.ip = ip;
		this.port = port;
		this.name = name;
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
