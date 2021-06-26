package me.emnichtda.lottischmarotti.game.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.Game;
import me.emnichtda.lottischmarotti.game.model.Showable;

public class GameScreen extends Pane implements Showable{
	private Game game;
	private Main main;
	
	private ImageView spielfeld;
	private ListView<String> lv_players = new ListView<String>();
	
	public GameScreen(Main main, Game game) {
		this.game = game;
		this.main = main;
		init();
	}
	
	public void init() {
		try {
			spielfeld = new ImageView(new Image(new FileInputStream("board.png")));
		} catch (FileNotFoundException e) {	} //This exception shouldn't happen. I already did a if the title image is missing
		getChildren().add(spielfeld);
		
		lv_players.setLayoutX(850);
		lv_players.maxHeightProperty().set(75);
		getChildren().add(lv_players);
	}
	
	public Game getGame() {
		return game;
	}
	public Main getMain() {
		return main;
	}
	@Override
	public void show() {
		Scene scene = new Scene(this, 1100, 750);
		main.setScene(scene);	
	}

	public void updatePlayers() { 
		lv_players.setItems(FXCollections.observableArrayList(main.getPlayersConnected()));
		
	}
}
