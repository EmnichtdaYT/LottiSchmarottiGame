package me.emnichtda.lottischmarotti.game.model;

import javafx.scene.shape.Circle;
import me.emnichtda.lottischmarotti.game.view.GameScreen;

public class GameField extends Circle implements Showable {
	private int x;
	private int y;
	private boolean holeable = false;
	private GameScreen game;

	public GameField(GameScreen game, int x, int y) {
		this.x = x;
		this.y = y;
		this.game = game;
	}

	public GameField(GameScreen game, int x, int y, boolean holeable) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.holeable = holeable;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isHoleable() {
		return holeable;
	}

	public GameScreen getGame() {
		return game;
	}

	@Override
	public void show() {
		if (holeable) {
			this.setRadius(10);
			this.setCenterX(x);
			this.setCenterY(y);
			game.getChildren().add(this);
		}
	}

	public void hide() {
		game.getChildren().remove(this);
	}

}
