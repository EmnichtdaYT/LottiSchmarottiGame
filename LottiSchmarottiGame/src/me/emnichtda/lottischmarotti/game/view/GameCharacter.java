package me.emnichtda.lottischmarotti.game.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.GameField;

public class GameCharacter extends Button {
	private Image image;
	private int charId;
	private int playerNumber;

	private GameField standing;

	private int defaultX = 775;
	private int defaultY = 20;

	public GameCharacter(Main main, int playerNumber, int charId) {
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
	}
}
