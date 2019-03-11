package pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;


public class TextArea
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Style mStyle;
	private ArrayList<Paragraph> mParagraphs;


	public TextArea(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, Style aStyle, Paragraph... aParagraph)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
		mStyle = aStyle;
		mParagraphs = new ArrayList<>(Arrays.asList(aParagraph));
	}


	String produce(Page aPage) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		content.println("1 0 0 RG");
		content.println(mBoundsLeft + " " + mBoundsTop + " m");
		content.println(mBoundsRight + " " + mBoundsTop + " l");
		content.println(mBoundsRight + " " + mBoundsBottom + " l");
		content.println(mBoundsLeft + " " + mBoundsBottom + " l");
		content.println("s");

		double boundsTop = mBoundsTop - mStyle.getDescent() - mStyle.getLineHeight();

		for (Paragraph paragraph : mParagraphs)
		{
			ArrayList<Symbol> text = new ArrayList<>();

			for (int i = 0; i < paragraph.getText().length(); i++)
			{
				text.add(new Symbol(mStyle, paragraph.getText().charAt(i)));
			}

			double targetY = boundsTop;

			for (int offset = 0; offset < text.size();)
			{
				double targetX = mBoundsLeft;
				double currentX;

				for (;;)
				{
					AtomicBoolean breakLine = new AtomicBoolean(false);
					int nextSegmentLength = layoutLine(text, offset, targetX, breakLine, mBoundsRight);
					if (nextSegmentLength == 0)
					{
						break;
					}

					Style style = text.get(offset).getStyle();
					aPage.registerFont(style);

					content.println("BT");
					content.println("%s %f Tf", style.getIdentity(), style.getSize());
					currentX = 0;

					double currentY = 0;

					if (targetY < mBoundsBottom)
					{
						offset = text.size();
						break;
					}

					for (int i = 0; i < nextSegmentLength; i++, offset++)
					{
						Symbol symbol = text.get(offset);

						content.println("%f %f Td <%04X> Tj", targetX - currentX, targetY - currentY, style.lookup(symbol));

						currentX = targetX;
						currentY = targetY;

						targetX += style.getAdvance(symbol);
					}

					targetY -= style.getLineHeight();

					if (breakLine.get())
					{
						break;
					}
				}

				content.println("ET");
			}

			boundsTop = targetY;
		}

		return baos.toString();
	}


	private static int layoutLine(ArrayList<Symbol> aText, int aTextOffset, double aTargetX, AtomicBoolean aBreakLine, double aMaxLineLength)
	{
		int len = -1;

		for (int i = 1, limit = aText.size() - aTextOffset; i <= limit; i++)
		{
			if (i == limit)
			{
				len = limit;
				aBreakLine.set(true);
				break;
			}

			Symbol symbol = aText.get(aTextOffset + i);

			double x = aTargetX + symbol.getStyle().measureText(aText, aTextOffset, i);

			if (x > aMaxLineLength)
			{
				aBreakLine.set(true);
				break;
			}
			if (symbol.isBreakChar())
			{
				len = i + 1;
			}
		}

//		System.out.println(aText.subList(aTextOffset, aTextOffset + len));
		return len;
	}
}
