package me.emnichtda.lottischmarotti.game.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.Game;
import me.emnichtda.lottischmarotti.game.model.Showable;

public class GameScreen extends Pane implements Showable {
	private Game game;
	private Main main;
	private ArrayList<GameCharacter> characters = new ArrayList<>();

	private ImageView spielfeld;
	private ListView<String> lv_players = new ListView<String>();
	private Button btn_roll;
	private Button btn_continue;

	public GameScreen(Main main, Game game) {
		this.game = game;
		this.main = main;
		init();
	}

	public void init() {
		try {
			spielfeld = new ImageView(new Image(new FileInputStream("board.png")));
		} catch (FileNotFoundException e) {
		} // This exception shouldn't happen. I already did a if the title image is
			// missing
		getChildren().add(spielfeld);

		lv_players.setLayoutX(850);
		lv_players.maxHeightProperty().set(75);
		getChildren().add(lv_players);
		
		for(int i = 1; i < 4; i++) {
			for(int j = 0; j < 3; j++) characters.add(new GameCharacter(main, i, j));
		}
		System.out.println("doing");
		getChildren().addAll(characters);
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

	public void showRollButton() {
		if (btn_roll == null) {
			btn_roll = new Button("Roll another time");
			btn_roll.setLayoutX(890);
			btn_roll.setLayoutY(500);
			getChildren().add(btn_roll);
		}
	}

	public Button getRollButton() {
		return btn_roll;
	}

	public void showContinueButton() {
		if (btn_continue == null) {
			btn_continue = new Button("Continue");
			btn_continue.setLayoutX(1000);
			btn_continue.setLayoutY(500);
			getChildren().add(btn_continue);
		}
	}

	public Button getContinueButton() {
		return btn_continue;
	}

	public void hideRollButton() {
		getChildren().remove(btn_roll);
		btn_roll = null;		
	}

	public void hideContinueButton() {
		getChildren().remove(btn_continue);
		btn_continue = null;
	}

	public void showCharacterDecision() {
			//TODO implement
	}
}
