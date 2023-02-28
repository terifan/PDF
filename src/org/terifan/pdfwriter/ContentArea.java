package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class ContentArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private ContentStream mContentStream;
	private Anchor mAnchor;
	private Color mBackground;


	public ContentArea(double aLeft, double aTop, double aRight, double aBottom, ContentStream aContentStream)
	{
		if (aRight < aLeft || aBottom > aTop)
		{
			throw new IllegalArgumentException();
		}

		mBoundsTop = aTop;
		mBoundsLeft = aLeft;
		mBoundsBottom = aBottom;
		mBoundsRight = aRight;
		mContentStream = aContentStream;
		mAnchor = Anchor.NORTH_WEST;

//		setBackground(new Color(new Random().nextDouble(),new Random().nextDouble(),new Random().nextDouble()));
	}


	public Color getBackground()
	{
		return mBackground;
	}


	public ContentArea setBackground(Color aBackground)
	{
		this.mBackground = aBackground;
		return this;
	}


	public Anchor getAnchor()
	{
		return mAnchor;
	}


	public ContentArea setAnchor(Anchor aAnchor)
	{
		this.mAnchor = aAnchor;
		return this;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		renderRectangle(content, mBoundsLeft, mBoundsTop, mBoundsRight, mBoundsBottom, null, null, mBackground, null);

		double width = 0;
		double height = 0;
		mContentStream.layout(mBoundsLeft, mBoundsRight);
//		for (Paragraph paragraph : mContents)
//		{
//			paragraph.layout(mBoundsLeft, mBoundsRight);
//			width = Math.max(width, paragraph.getWidth());
//			height += paragraph.getHeight();
//		}
		width = mContentStream.getWidth();
		height = mContentStream.getHeight();

		double boundsTop;
		switch (mAnchor)
		{
			case SOUTH:
			case SOUTH_EAST:
			case SOUTH_WEST:
				boundsTop = mBoundsBottom + height + 1; // todo: +1 is because rounding errors
				break;
			case CENTER:
			case WEST:
			case EAST:
				boundsTop = (mBoundsBottom + mBoundsTop + height) / 2;
				break;
			default:
				boundsTop = mBoundsTop;
				break;
		}

		double boundsLeft;
		switch (mAnchor)
		{
			case NORTH_EAST:
			case EAST:
			case SOUTH_EAST:
				boundsLeft = mBoundsRight - width - 1; // todo: -1 is because rounding errors
				break;
			case CENTER:
			case NORTH:
			case SOUTH:
				boundsLeft = (mBoundsLeft + mBoundsRight - width) / 2;
				break;
			default:
				boundsLeft = mBoundsLeft;
				break;
		}

		boundsTop = mContentStream.produce(aPDFWriter, content, aPage, boundsTop, boundsLeft, mBoundsBottom, mBoundsRight);

		return baos.toString();
	}
}
