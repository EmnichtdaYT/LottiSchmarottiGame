package me.emnichtda.lottischmarotti.game.main;
	
import javafx.application.Application;
import javafx.stage.Stage;
import me.emnichtda.lottischmarotti.game.view.MainMenu;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage stage;
	
	private MainMenu mainMenu;
	
	@Override
	public void start(Stage stage) {
			this.stage = stage;
			mainMenu = new MainMenu(this);
			mainMenu.show();
	}
	
	public void setScene(Scene scene) {
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
