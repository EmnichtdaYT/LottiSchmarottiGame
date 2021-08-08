package me.emnichtda.lottischmarotti.game.controll;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import me.emnichtda.lottischmarotti.game.main.Main;

public class ConnectHandler implements EventHandler<ActionEvent> {

	private Main main;
	
	public ConnectHandler(Main main) {
		this.main = main;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		Optional<String> ipPortO = main.showIpBox();
		if(!ipPortO.isPresent()) {
			main.showError("Invalid input", "Please enter ip and port (IP:PORT)");
			return;
		}
		String ipPort = ipPortO.get();
		String[] splitted = ipPort.split(":");
		if(splitted.length!=2) {
			main.showError("Invalid input", "Please enter ip and port (IP:PORT)");
			return;
		}
		if(!splitted[0].contains(".")) {
			main.showError("Invalid input", "Please enter a valid ip");
			return;
		}
		int port;
		try {
			port = Integer.parseInt(splitted[1]);
		}catch(NumberFormatException e) {
			main.showError("Invalid input", "Please enter a valid port");
			return;
		}
		
		Optional<String> nameO = main.showNameBox();
		
		if(!nameO.isPresent() || nameO.get().isEmpty()) {
			main.showError("Invalid input", "Please enter your name");
			return;
		}
		
		String name = nameO.get();
		
		main.showGameListScreen(splitted[0], port, name);
		
		
	}

	public Main getMain() {
		return main;
	}

}
