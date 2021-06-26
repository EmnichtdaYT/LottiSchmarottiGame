package me.emnichtda.lottischmarotti.game.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.emnichtda.lottischmarotti.game.model.Game;
import me.emnichtda.lottischmarotti.game.model.LottiSchmarottiError;
import me.emnichtda.lottischmarotti.game.model.NotALottiSchmarottiGameException;
import me.emnichtda.lottischmarotti.game.view.GameListScreen;
import me.emnichtda.lottischmarotti.game.view.GameScreen;
import me.emnichtda.lottischmarotti.game.view.MainMenu;
import me.emnichtda.lottischmarotti.game.view.TextInputDialog;

public class Main extends Application {

	private Stage stage;

	private MainMenu mainMenu;

	private GameListScreen gamesList;

	private GameScreen gameScreen;

	private ArrayList<String> playersConnected;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setResizable(false);
		stage.setTitle("Lotti Schmarotti");
		try {
			stage.getIcons().add(new Image(new FileInputStream("Icon.png")));
		} catch (FileNotFoundException e) {
		}
		mainMenu = new MainMenu(this);
		mainMenu.show();

		stage.setOnCloseRequest((e) -> {
			if (gameScreen != null) {
				gameScreen.getGame().close();
			}
		});

	}

	public Optional<String> showIpBox() {
		TextInputDialog ipInput = new TextInputDialog("Enter ip and port", "IP:PORT:");
		Optional<String> input = ipInput.showAndWait();
		return input;
	}

	public void showError(String heading, String text) {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle("Error!");
		a.setHeaderText(heading);
		a.setContentText(text);
		a.showAndWait();
	}

	public void setScene(Scene scene) {
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Optional<String> showNameBox() {
		return new TextInputDialog("Enter username", "Username:").showAndWait();
	}

	public void showGameListScreen(String ip, int port, String name) {
		gamesList = new GameListScreen(this, ip, port, name);
		gamesList.show();
	}

	public GameListScreen getGamesList() {
		return gamesList;
	}

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void startGame(Game game) {
		gameScreen = new GameScreen(this, game);
		gameScreen.show();
		stage.setY(10);
		stage.setX(10);
		try {
			game.start();
		} catch (IOException | NotALottiSchmarottiGameException | LottiSchmarottiError e) {
			showError("Error", e.getLocalizedMessage());
		}
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setPlayersConnected(ArrayList<String> players) {
		playersConnected = players;
		if (gameScreen != null)
			gameScreen.updatePlayers();
	}

	public ArrayList<String> getPlayersConnected() {
		return playersConnected;
	}
}
