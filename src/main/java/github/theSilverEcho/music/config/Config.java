package github.theSilverEcho.music.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import github.theSilverEcho.music.config.selector.ConfigOption;
import github.theSilverEcho.music.config.selector.PostSaveProcess;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Config
{

	protected final static Config CLIENT_INSTANCE = new Config(new File("src/client.json"));
	protected final static Config SERVER_INSTANCE = new Config(new File("src/server.json"));

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final List<Object> configObjects = new ArrayList<>();
	private final File file;
	private JsonObject config = new JsonObject();
	private Object object;

	private Config(File configFile)
	{
		this.file = configFile;
		if (!configFile.exists())
		{
			config = new JsonObject();
			saveConfig();
		} else
			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
			{
				config = new JsonParser().parse(bufferedReader.lines().collect(Collectors.joining())).getAsJsonObject();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

	}

	private void saveConfig()
	{
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
		{
			file.createNewFile();
			bufferedWriter.write(gson.toJson(config));
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void save()
	{
		configObjects.forEach(this::loadFieldToJson);
		saveConfig();

	}

	public void register(Object object)
	{
		if (Arrays.stream(object.getClass().getDeclaredFields()).noneMatch(f -> f.isAnnotationPresent(ConfigOption.class)))
			return;
		this.object = object;
		loadFieldToClass();
		configObjects.add(object);
	}

	private void loadFieldToClass()
	{
		Class<?> clazz = this.object.getClass();
		if (!config.has(clazz.getSimpleName()))
			config.add(clazz.getSimpleName(), new JsonObject());
		applyStream(this::loadFieldToClass);
	}

	private void loadFieldToJson(Object o)
	{
		applyStream(this::loadFieldToJson);
	}

	private void loadFieldToClass(Field field)
	{
		try
		{
			field.setAccessible(true);
			Class<?> clazz = object.getClass();
			if (config.get(clazz.getSimpleName()).getAsJsonObject().has(field.getName()))
				field.set(field, gson.fromJson(config.getAsJsonObject(clazz.getSimpleName()).get(field.getName()), field.getType()));
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}

	private void loadFieldToJson(Field field)
	{
		try
		{
			field.setAccessible(true);
			final Class<?> clazz = this.object.getClass();
			JsonObject classObject = config.get(clazz.getSimpleName()).getAsJsonObject();
			classObject.add(field.getName(), gson.toJsonTree(field.get(clazz), field.getType()));
			if (field.isAnnotationPresent(PostSaveProcess.class))
			{
				final PostSaveProcess annotation = field.getAnnotation(PostSaveProcess.class);
				annotation.method().run();
			}

		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}


	private void applyStream(Consumer<Field> consumer)
	{
		final Class<?> clazz = this.object.getClass();
		Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.isAnnotationPresent(ConfigOption.class))
				.filter(field -> config.has(clazz.getSimpleName()))
				.forEach(consumer);
	}

	public static List<Field> getAllFields()
	{
		final Class<?> clazz = CLIENT_INSTANCE.object.getClass();
		return Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
	}

}
