package github.theSilverEcho.music.config.selector;

import github.theSilverEcho.music.config.PostSaveMethods;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface PostSaveProcess
{
	PostSaveMethods method();
}
