package me.emnichtda.lottischmarotti.game.view;

import me.emnichtda.lottischmarotti.game.model.Showable;

public class TextInputDialog extends javafx.scene.control.TextInputDialog implements Showable{

	public TextInputDialog(String title, String textHint) {
		this.setTitle(title);
		this.setHeaderText(title);
		this.setContentText(textHint);
	}

}
