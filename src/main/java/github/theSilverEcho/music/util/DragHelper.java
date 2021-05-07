package github.theSilverEcho.music.util;

import github.theSilverEcho.music.config.ClientSettings;
import github.theSilverEcho.music.player.MainMusicUi;
import javafx.scene.effect.MotionBlur;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DragHelper
{
	private static final VecHelper offset = new VecHelper();

	public static void makeStageDraggable(Pane pane)
	{
		Stage stage = MainMusicUi.stage;

		stage.setY(ClientSettings.POSITION.y);
		stage.setX(ClientSettings.POSITION.x);


		pane.setOnMousePressed(event ->
		{
			offset.x = event.getSceneX();
			offset.y = event.getSceneY();
		});

		pane.setOnMouseDragged(event ->
		{
			double width = Screen.getPrimary().getVisualBounds().getMaxX();
			double height = Screen.getPrimary().getVisualBounds().getMaxY();
			if (event.getScreenX() - offset.x >= width - pane.getWidth())
				stage.setX(width - pane.getWidth());
			else if (event.getScreenX() - offset.x < 0)
				stage.setX(0);
			else
				stage.setX(ClientSettings.POSITION.x = event.getScreenX() - offset.x);

			if (event.getScreenY() - offset.y >= height - pane.getHeight())
				stage.setY(height - pane.getHeight());
			else if (event.getScreenY() - offset.y < 0)
				stage.setY(0);
			else
				stage.setY(ClientSettings.POSITION.y = event.getScreenY() - offset.y);


			stage.setOpacity(0.7);
			pane.setEffect(new MotionBlur());

		});

		pane.setOnMouseDragReleased(event ->
		{
			stage.setOpacity(1);
			pane.setEffect(null);
		});
		pane.setOnMouseReleased(event ->
		{
			stage.setOpacity(1);
			pane.setEffect(null);
		});

	}
}
