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


	public ImageArea(double aBoundsLeft, double aBoundsTop, double aBoundsRight, double aBoundsBottom, Image aImage)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
		mImage = aImage;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		if (mImage.isReady())
		{
			aPage.registerImage(mImage);

			Margins margins = mImage.getMargins();

			Output content = new Output(baos);
			content.print("q ");
			content.print(mBoundsRight - mBoundsLeft - margins.left() - margins.right());
			content.print(" 0 ");
			content.print(" 0 ");
			content.print(mBoundsTop - mBoundsBottom - margins.top() - margins.bottom());
			content.print(" ");
			content.print(mBoundsLeft + margins.left());
			content.print(" ");
			content.print(mBoundsBottom + margins.bottom());
			content.print(" cm " + mImage.getIdentity());
			content.println(" Do Q");
		}

		return baos.toString();
	}
}
