package github.theSilverEcho.music.util;

import github.theSilverEcho.music.config.selector.PostSaveProcess;

import java.lang.reflect.Field;
import java.util.Optional;

public class ReflectionHelper
{
	public static <T> void setField(Field field, T value)
	{
		try
		{
			field.setAccessible(true);
			field.set(field, value);
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

	public static Object getField(Field field)
	{
		try
		{
			return field.get(field);
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return new Object();
	}

	public static <T> Optional<T> getField(Class<T> type, Field field)
	{
		try
		{
			if (type.isAssignableFrom(field.get(field).getClass()))
				return Optional.of(type.cast(field.get(field)));
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return Optional.empty();
	}
}
