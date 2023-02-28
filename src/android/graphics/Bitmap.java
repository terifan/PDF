package android.graphics;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import javax.imageio.ImageIO;


public class Bitmap
{
	private BufferedImage mImage;


	public Bitmap(BufferedImage aImage)
	{
		mImage = aImage;
	}


	public BufferedImage getImage()
	{
		return mImage;
	}


	public int getWidth()
	{
		return mImage.getWidth();
	}


	public int getHeight()
	{
		return mImage.getHeight();
	}


	public int getPixel(int x, int y)
	{
		return mImage.getRGB(x, y);
	}


	public enum Config
	{
		ARGB_8888
	}


	public enum CompressFormat
	{
		PNG
	}


	public static Bitmap createBitmap(int aWidth, int aHeight, Config aConfig)
	{
		return new Bitmap(new BufferedImage(aWidth, aHeight, BufferedImage.TYPE_INT_ARGB));
	}


	public void compress(CompressFormat aCompressFormat, int aRatio, OutputStream aOutput)
	{
		try
		{
			ImageIO.write(mImage, "png", aOutput);
		}
		catch (Exception e)
		{
			throw new IllegalStateException(e);
		}
	}
}
