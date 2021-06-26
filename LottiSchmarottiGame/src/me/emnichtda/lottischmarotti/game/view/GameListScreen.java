package me.emnichtda.lottischmarotti.game.view;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.Game;
import me.emnichtda.lottischmarotti.game.model.GameLister;
import me.emnichtda.lottischmarotti.game.model.Showable;

public class GameListScreen extends GridPane implements Showable{

	private Main main;
	private String ip;
	private int port;
	private String name;
	
	private ArrayList<Game> games;
	private ListView<String> lv_games; 
	private Label l_ip;
	private Button b_mainmenu;
	
	public GameListScreen(Main main, String ip, int port, String name) {
		this.main = main;
		this.ip = ip;
		this.port = port;
		this.name = name;
		init();
	}
	
	private void init() {
		
		this.setAlignment(Pos.CENTER);
		
		l_ip = new Label(ip + ":" + port);
		l_ip.setFont(new Font(30));
		GridPane.setHalignment(l_ip, HPos.CENTER);
		this.add(l_ip, 0, 0);
		
		lv_games = new ListView<>();
		lv_games.setPrefWidth(370);
		lv_games.setPrefHeight(450);
		this.add(lv_games, 0, 1);
		
		b_mainmenu = new Button("Back to main menu");
		GridPane.setHalignment(b_mainmenu, HPos.CENTER);
		b_mainmenu.setOnAction((e) -> {
			main.getMainMenu().show();
		});
		this.add(b_mainmenu, 0, 2);
		
		games = new ArrayList<>();
		
		initGameList();
	}
	
	private void initGameList() {
		try {
			new GameLister(main, this, ip, port, name).list();
		} catch (Exception e) {
			l_ip.setTextFill(Color.RED);
			System.out.println(e.getLocalizedMessage());
			return;
		}
		l_ip.setTextFill(Color.GREEN);
	}

	public void addGame(Game g) {
		games.add(g);
		lv_games.getItems().add("PORT: " + g.getPort() + " | Players: " + g.getPlayersConnected() + "/" + Game.MAX_PLAYERS);
		lv_games.getSelectionModel().selectedItemProperty().addListener((e, oldValue, newValue) -> {
			Game game = null;
			for(Game cGame : games) {
				if(("PORT: " + cGame.getPort() + " | Players: " + cGame.getPlayersConnected() + "/" + Game.MAX_PLAYERS).equals(newValue)) {
					game = cGame;
					break;
				}				
			}
			main.startGame(game);
		});
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
