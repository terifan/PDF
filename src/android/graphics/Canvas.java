package android.graphics;

import java.awt.Graphics2D;


public class Canvas
{
	private Bitmap mBitmap;
	private Graphics2D mGraphics;


	public Canvas(Bitmap aBitmap)
	{
		mBitmap = aBitmap;
	}


	public void drawRect(int aFromX, int aFromY, int aWidth, int aHeight, Paint aPaint)
	{
		if (mGraphics == null)
		{
			mGraphics = mBitmap.getImage().createGraphics();
		}
		mGraphics.setColor(new java.awt.Color(aPaint.getColor()));
		mGraphics.fillRect(aFromX, aFromY, aWidth, aHeight);
	}


	/**
	 * Fake method to make code compatible with Android code.
	 *
	 * @return
	 *   always false
	 */
	public boolean isOpaque()
	{
		if (mGraphics != null)
		{
			mGraphics.dispose();
			mGraphics = null;
		}
		return false;
	}
}
