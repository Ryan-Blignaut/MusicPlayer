package github.theSilverEcho.music.jni;

import github.theSilverEcho.music.config.ClientSettings;
import javafx.stage.Stage;

public enum Blur
{

	NONE(0), TRANSPARENT(3), ACRYLIC(4);
	private final int value;
	private static Blur cachedState;
	private static Stage cachedStage;

	Blur(int value)
	{
		this.value = value;
	}

	public static void applyBlurToStage(Stage stage, Blur blurType)
	{
		cachedStage = stage;
		if (ClientSettings.BLUR_ENABLED)
			blurEffect(blurType);
		else if (cachedState != NONE)
			blurEffect(NONE);
		cachedState = blurType;
	}

	public static void refresh()
	{
		applyBlurToStage(cachedStage, cachedState);
	}

	public static void apply(Blur blurType)
	{
		applyBlurToStage(cachedStage, blurType);
	}

	private static void blurEffect(Blur blurType)
	{
		final String cachedTitle = cachedStage.getTitle();
		cachedStage.setTitle("JFX_" + System.currentTimeMillis());
		MusicPluginJNI.applyBlur(cachedStage.getTitle(), blurType.value);
		cachedStage.setTitle(cachedTitle);
	}


	static
	{
		System.loadLibrary("libs/MusicPlayerJNI");
	}

}
