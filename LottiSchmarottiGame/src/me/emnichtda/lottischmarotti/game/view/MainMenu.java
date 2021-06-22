package me.emnichtda.lottischmarotti.game.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import me.emnichtda.lottischmarotti.game.main.Main;

public class MainMenu extends GridPane{

	private Main main;
	
	public MainMenu(Main main) {
		this.main = main;
		init();
	}
	
	private void init() {
		
		setAlignment(Pos.CENTER);
		
		initLogo();
		initConnectButton();
	}

	private void initConnectButton() {
				
	}

	private void initLogo() {
		try {
			ImageView logo = new ImageView(new Image(new FileInputStream("Title.png")));
		} catch (FileNotFoundException e) {
			Label l = new Label("Images not found. Please fix! Exiting...");
			l.setFont(new Font(20));
			this.add(l, 0, 0);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {	}
			System.exit(0);
			
		}
		
	}
	
	public void show() {
		Scene scene = new Scene(this, 500, 500);
		main.setScene(scene);
	}

	public Main getMain() {
		return main;
	}

}
