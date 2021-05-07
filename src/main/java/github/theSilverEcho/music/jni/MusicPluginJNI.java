package github.theSilverEcho.music.jni;

public class MusicPluginJNI
{
	protected static native void applyBlur(String target, int accentState);

	protected native void keyStroke();
}
