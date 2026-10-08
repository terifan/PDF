package org.terifan.pdfwriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static org.terifan.pdfwriter.Anchor.CENTER;
import static org.terifan.pdfwriter.Anchor.NORTH;
import static org.terifan.pdfwriter.Anchor.NORTH_WEST;
import static org.terifan.pdfwriter.Anchor.SOUTH;
import static org.terifan.pdfwriter.Anchor.SOUTH_WEST;
import static org.terifan.pdfwriter.Anchor.WEST;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class TextArea implements Producer
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private ArrayList<Paragraph> mParagraphs;
	private Anchor mAnchor;
	private Color mBackground;


	public TextArea(double aLeft, double aTop, double aRight, double aBottom, Paragraph... aParagraph)
	{
		if (aRight < aLeft || aBottom > aTop)
		{
			throw new IllegalArgumentException("aRight < aLeft || aBottom > aTop");
		}

		mBoundsTop = aTop;
		mBoundsLeft = aLeft;
		mBoundsBottom = aBottom;
		mBoundsRight = aRight;
		mParagraphs = new ArrayList<>(Arrays.asList(aParagraph));
		mAnchor = Anchor.NORTH_WEST;
	}


	public TextArea add(Paragraph aParagraph)
	{
		mParagraphs.add(aParagraph);
		return this;
	}


	public Color getBackground()
	{
		return mBackground;
	}


	public TextArea setBackground(Color aBackground)
	{
		mBackground = aBackground;
		return this;
	}


	public Anchor getAnchor()
	{
		return mAnchor;
	}


	public TextArea setAnchor(Anchor aAnchor)
	{
		mAnchor = aAnchor;
		return this;
	}


	@Override
	public String produce(PDFWriter aPDFWriter, Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		renderRectangle(null,aPage,content, content, mBoundsLeft, mBoundsTop, mBoundsRight, mBoundsBottom, mBackground, null, null);

		double width = 0;
		double height = 0;
		for (Paragraph paragraph : mParagraphs)
		{
			paragraph.layout(mBoundsLeft, mBoundsRight);
			width = Math.max(width, paragraph.getLayoutWidth());
			height += paragraph.getLayoutHeight();
		}

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
			case NORTH:
			case NORTH_EAST:
			case NORTH_WEST:
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
			case WEST:
			case NORTH_WEST:
			case SOUTH_WEST:
			default:
				boundsLeft = mBoundsLeft;
				break;
		}

		if (!mParagraphs.isEmpty())
		{
//			content.println("q");

			for (Paragraph paragraph : mParagraphs)
			{
				boundsTop = paragraph.produce(aPDFWriter, content, aPage, boundsTop, boundsLeft, mBoundsBottom, boundsLeft + width);
			}

//			content.println("Q");
//			content.println("EMC");
		}

		return baos.toString();
	}
}
