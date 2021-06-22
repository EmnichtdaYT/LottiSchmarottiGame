package me.emnichtda.lottischmarotti.game.main;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.emnichtda.lottischmarotti.game.view.GameListScreen;
import me.emnichtda.lottischmarotti.game.view.MainMenu;
import me.emnichtda.lottischmarotti.game.view.TextInputDialog;


public class Main extends Application {
	
	private Stage stage;
	
	private MainMenu mainMenu;
	
	@Override
	public void start(Stage stage) {
			this.stage = stage;
			stage.setResizable(false);
			stage.setTitle("Lotti Schmarotti");
			try {
				stage.getIcons().add(new Image(new FileInputStream("Icon.png")));
			} catch (FileNotFoundException e) {		}
			mainMenu = new MainMenu(this);
			mainMenu.show();
	}
	
	public Optional<String> showIpBox() {
		return new TextInputDialog("Enter ip and port", "IP:PORT:").showAndWait();
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
		new GameListScreen(this, ip, port, name).show();
	}
}
