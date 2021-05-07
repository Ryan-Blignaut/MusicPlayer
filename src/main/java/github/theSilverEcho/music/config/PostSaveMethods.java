package github.theSilverEcho.music.config;


import github.theSilverEcho.music.jni.Blur;

public enum PostSaveMethods
{
	UPDATE_BLUR(Blur::refresh);
	Runnable method;

	PostSaveMethods(Runnable method)
	{
		this.method = method;
	}


	public void run()
	{
		this.method.run();
	}
}
