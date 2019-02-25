package samples;

import font.truetype.TrueTypeFont;
import util.Streams;


public class ReadFontFile
{
	public static void main(String... args)
	{
		try
		{
			new TrueTypeFont(Streams.readAll("d:\\Desktop\\pdf\\Anonymous_Pro.ttf"));
		}
		catch (Throwable e)
		{
			e.printStackTrace(System.out);
		}
	}
}
