package me.emnichtda.lottischmarotti.game.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import me.emnichtda.lottischmarotti.game.controll.ConnectHandler;
import me.emnichtda.lottischmarotti.game.main.Main;
import me.emnichtda.lottischmarotti.game.model.Showable;

public class MainMenu extends GridPane implements Showable {

	private Main main;

	public MainMenu(Main main) {
		this.main = main;
		init();
	}

	private void init() {

		setAlignment(Pos.CENTER);

		try {
			initLogo();
		} catch (FileNotFoundException e) {
			Label l = new Label("Images not found. Please fix!");
			l.setFont(new Font(20));
			this.add(l, 0, 0);
			return;
		}
		initConnectButton();
	}

	private void initConnectButton() {
		Button b = new Button("Connect");
		b.setFont(new Font(17));
		b.setOnAction(new ConnectHandler(main));
		setHalignment(b, HPos.CENTER);
		add(b, 0, 1);
	}

	private void initLogo() throws FileNotFoundException {

		ImageView logo = new ImageView(new Image(new FileInputStream("Title.png")));
		
		add(logo, 0, 0);
	}

	public void show() {
		Scene scene = new Scene(this, 500, 300);
		main.setScene(scene);
	}

	public Main getMain() {
		return main;
	}

}
