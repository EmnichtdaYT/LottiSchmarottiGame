package me.emnichtda.lottischmarotti.game.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import me.emnichtda.lottischmarotti.game.controll.CharacterClickListener;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.Game;

public class GameCharacter extends Button {
	private Image image;
	private int charId;
	private int playerNumber;

	private int standing;
	
	private Main main;
	private Game game;

	private int defaultX = 775;
	private int defaultY = 20;

	public GameCharacter(Main main, Game game, int playerNumber, int charId) {
		
		this.main = main;
		this.game = game;
		
		this.charId = charId;
		this.playerNumber = playerNumber;
		try {
			init();
		} catch (Exception e) {
			main.showError("Error!", "Character image not found! " + e.getLocalizedMessage());
		}
	}

	private void init() throws FileNotFoundException {
		FileInputStream imageStream;
		switch (playerNumber) {
		case 1:
			imageStream = new FileInputStream("yellow.png");
			break;
		case 2:
			imageStream = new FileInputStream("blue.png");
			defaultY = 260;
			break;
		case 3:
			imageStream = new FileInputStream("red.png");
			defaultY = 500;
			break;
		default:
			throw new IllegalArgumentException("Character ID out of range");
		}
		defaultY += charId * 70;
		image = new Image(imageStream);
		this.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		this.setLayoutX(defaultX);
		this.setLayoutY(defaultY);
		this.setMinSize(60, 60);
		this.setMaxSize(60, 60);
		
		this.setOnMouseClicked(new CharacterClickListener(game, charId, playerNumber));
	}

	public int getStanding() {
		return standing;
	}

	public void setStanding(int standing) {
		this.standing = standing;
		this.setLayoutX(game.getFields().get(standing).getX()-this.getBackground().getImages().get(0).getImage().getWidth()/2);
		this.setLayoutY(game.getFields().get(standing).getY()-this.getBackground().getImages().get(0).getImage().getHeight()/2);
	}

	public Game getGame() {
		return game;
	}

	public Main getMain() {
		return main;
	}

	public int getCharId() {
		return charId;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
	
	
}
