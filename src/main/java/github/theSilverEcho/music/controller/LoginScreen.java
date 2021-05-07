package github.theSilverEcho.music.controller;

import github.theSilverEcho.music.player.MainMusicUi;
import github.theSilverEcho.music.util.DragHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class LoginScreen
{


	@FXML
	void closeScreen(ActionEvent event)
	{
		MainMusicUi.stage.close();
	}

	@FXML
	private HBox paneTest;

	@FXML
	void initialize()
	{
		DragHelper.makeStageDraggable(paneTest);
	}

	@FXML
	void openSettings(ActionEvent event)
	{

	}
}
