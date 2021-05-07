package github.theSilverEcho.music.player;

import github.theSilverEcho.music.jni.Blur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class MainMusicUi extends Application
{
	public static Stage stage;
	private static final FXMLLoader FXML_LOADER = new FXMLLoader();

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		final URL url = this.getClass().getResource("/music/loginScreen.fxml");
		FXML_LOADER.setLocation(url);
		stage = primaryStage;
		final Scene scene = new Scene(new Group());
		scene.setFill(Color.TRANSPARENT);
		scene.setRoot(FXML_LOADER.load());

		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);

		stage.show();
		Blur.applyBlurToStage(stage, Blur.TRANSPARENT);
	}
}
