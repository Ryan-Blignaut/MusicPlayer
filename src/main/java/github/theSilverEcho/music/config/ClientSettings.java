package github.theSilverEcho.music.config;

import github.theSilverEcho.music.config.selector.*;
import github.theSilverEcho.music.util.VecHelper;

import static github.theSilverEcho.music.config.PostSaveMethods.UPDATE_BLUR;

public class ClientSettings
{
	public static final ClientSettings INSTANCE = new ClientSettings();

	@ConfigOption
	@CategorySelector(category = Category.CLIENT)
	@BooleanSelector
	@PostSaveProcess(method = UPDATE_BLUR)
	public static Boolean BLUR_ENABLED = false;

	@ConfigOption
	@CategorySelector(category = Category.CLIENT)
	@MenuSelector(options = {"NONE", "TRANSPARENT", "ACRYLIC"})
	public static String BLUR_TYPE = "TRANSPARENT";

	@ConfigOption
	@CategorySelector(category = Category.AUDIO)
	@SliderSelector(min = 10, max = 90)
	public static Double SLIDER_TEST = 55d;


	@ConfigOption
	@CategorySelector(category = Category.AUDIO)
	@TextSelector
	public static String TEXT_TEST = "Hello World";

	@ConfigOption
	public static VecHelper POSITION = new VecHelper();


	public void create()
	{
		Config.CLIENT_INSTANCE.register(new ClientSettings());
	}

	public void save()
	{
		Config.CLIENT_INSTANCE.save();
	}

}
