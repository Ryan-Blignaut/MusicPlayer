package github.theSilverEcho.music.player;

import github.theSilverEcho.music.config.ClientSettings;

import java.util.ArrayList;

public class RunMusic
{
	private static final ArrayList<Runnable> END_TASKS = new ArrayList<>();

	public static void main(String[] args)
	{
		ClientSettings.INSTANCE.create();
		MainMusicUi.main(args);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> END_TASKS.forEach(Runnable::run)));
	}

	static
	{
		addEndTask(ClientSettings.INSTANCE::save);
	}

	public static void addEndTask(Runnable task)
	{
		END_TASKS.add(task);
	}

}
