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
	private ArrayList<Paragraph> mParagraphs;


	public TextArea(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, Paragraph... aParagraph)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
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

		Style style = mParagraphs.get(0).getSpans().get(0).getStyle();

		double boundsTop = mBoundsTop - style.getDescent() - style.getLineHeight();

		for (Paragraph paragraph : mParagraphs)
		{
			ArrayList<Span> spans = paragraph.getSpans();
			double targetY = boundsTop;

			for (int spanIndex = 0; spanIndex < spans.size(); spanIndex++)
			{
				Span span = spans.get(spanIndex);
				String text = span.getText();

				style = span.getStyle();
				aPage.registerFont(style);

				for (int offset = 0; offset < text.length();)
				{
					double targetX = mBoundsLeft;
					double currentX;

					for (;;)
					{
						AtomicBoolean breakLine = new AtomicBoolean(false);
						int nextSegmentLength = layoutLine(span, offset, targetX, breakLine, mBoundsRight);
						if (nextSegmentLength == 0)
						{
							break;
						}

						content.println("BT");
						content.println("%s %f Tf", style.getIdentity(), style.getSize());
						currentX = 0;

						double currentY = 0;

						if (targetY < mBoundsBottom)
						{
							offset = text.length();
							break;
						}

						for (int i = 0; i < nextSegmentLength; i++, offset++)
						{
							char ch = text.charAt(offset);

							content.println("%f %f Td <%04X> Tj", targetX - currentX, targetY - currentY, style.getGlyphIndex(ch));

							currentX = targetX;
							currentY = targetY;

							targetX += style.getAdvance(ch);
						}

						targetY -= style.getLineHeight();

						if (breakLine.get())
						{
							break;
						}
					}

					content.println("ET");
				}
			}

			boundsTop = targetY;
		}

		return baos.toString();
	}


	private static int layoutLine(Span aSpan, int aTextOffset, double aTargetX, AtomicBoolean aBreakLine, double aMaxLineLength)
	{
		int len = -1;

		for (int i = 1, limit = aSpan.getText().length() - aTextOffset; i <= limit; i++)
		{
			if (i == limit)
			{
				len = limit;
				aBreakLine.set(true);
				break;
			}

			char ch = aSpan.getText().charAt(aTextOffset + i);

			double x = aTargetX + aSpan.getStyle().measureText(aSpan.getText(), aTextOffset, i);

			if (x > aMaxLineLength)
			{
				aBreakLine.set(true);
				break;
			}
			if (ch == ' ')
			{
				len = i + 1;
			}
		}

//		System.out.println(aText.subList(aTextOffset, aTextOffset + len));
		return len;
	}
}
