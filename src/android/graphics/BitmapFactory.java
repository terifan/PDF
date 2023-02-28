package android.graphics;

import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


public class BitmapFactory
{
	public static Bitmap decodeStream(InputStream aInputStream) throws IOException
	{
		return new Bitmap(ImageIO.read(aInputStream));
	}
}
