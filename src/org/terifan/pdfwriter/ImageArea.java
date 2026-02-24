package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class ImageArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Image mImage;


	public ImageArea(double aLeft, double aTop, double aRight, double aBottom, Image aImage)
	{
		if (aRight < aLeft || aBottom > aTop)
		{
			throw new IllegalArgumentException();
		}

		mBoundsTop = aTop;
		mBoundsLeft = aLeft;
		mBoundsBottom = aBottom;
		mBoundsRight = aRight;
		mImage = aImage;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (mImage.isReady())
		{
			aPage.registerImage(mImage);

			Insets margins = mImage.getMargins();

			Output content = new Output(baos);
			content.println("q");
			content.print(mBoundsRight - mBoundsLeft - margins.left() - margins.right());
			content.print(" 0 ");
			content.print(" 0 ");
			content.print(mBoundsTop - mBoundsBottom - margins.top() - margins.bottom());
			content.print(" ");
			content.print(mBoundsLeft + margins.left());
			content.print(" ");
			content.print(mBoundsBottom + margins.bottom());
			content.println(" cm " + mImage.getIdentity() + " Do ");
			content.println("Q");
		}

		return baos.toString();
	}
}
