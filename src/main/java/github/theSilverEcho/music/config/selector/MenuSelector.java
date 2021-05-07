package github.theSilverEcho.music.config.selector;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface MenuSelector
{
	String[] options() default {};


}
