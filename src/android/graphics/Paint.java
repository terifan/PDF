package android.graphics;


public class Paint
{
	private int mColor;


	public void setColor(int aColor)
	{
		mColor = aColor;
	}


	public int getColor()
	{
		return mColor;
	}


	public void setStyle(Style aStyle)
	{
	}


	public enum Style
	{
		STROKE
	}
}
