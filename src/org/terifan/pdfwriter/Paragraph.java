package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Paragraph
{
	private ArrayList<Span> mSpans;


	public Paragraph(Span... aSpans)
	{
		mSpans = new ArrayList<>(Arrays.asList(aSpans));
	}


	public Paragraph(List<Span> aSpans)
	{
		mSpans = new ArrayList<>(aSpans);
	}


	public Paragraph(Style aStyle, String aText)
	{
		mSpans = new ArrayList<>();
		mSpans.add(new Span(aStyle, aText));
	}


	public ArrayList<Span> getSpans()
	{
		return mSpans;
	}


	public double produce(PDFWriter aPDFWriter, Output content, Page aPage, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
//		content.println("1 0 0 RG");
//		content.println(mBoundsLeft + " " + mBoundsTop + " m");
//		content.println(mBoundsRight + " " + mBoundsTop + " l");
//		content.println(mBoundsRight + " " + mBoundsBottom + " l");
//		content.println(mBoundsLeft + " " + mBoundsBottom + " l");
//		content.println("s");

		double boundsTop = aBoundsTop;

		ArrayList<Span> spans = getSpans();
		Style style = getSpans().get(0).getStyle();
		double targetY = boundsTop - style.getDescent() - style.getLineHeight();

		for (int spanIndex = 0; spanIndex < spans.size(); spanIndex++)
		{
			Span span = spans.get(spanIndex);
			String text = span.getText();

			style = span.getStyle();
			aPage.registerFont(style);

			for (int offset = 0; offset < text.length();)
			{
				double targetX = aBoundsLeft;
				double currentX;
				boolean started = false;

				for (;;)
				{
					AtomicBoolean breakLine = new AtomicBoolean(false);
					int nextSegmentLength = layoutLine(span, offset, targetX, breakLine, aBoundsRight);
					if (nextSegmentLength == 0)
					{
						break;
					}

					if (targetY < aBoundsBottom)
					{
						offset = text.length();
						break;
					}

					started = true;
					content.println("BT");
					content.println("%s %f Tf", style.getIdentity(), style.getSize());

					currentX = 0;

					double currentY = 0;

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

				if (started)
				{
					content.println("ET");
				}
			}
		}

		return targetY + style.getDescent() + style.getLineHeight();
	}


	private static int layoutLine(Span aSpan, int aTextOffset, double aTargetX, AtomicBoolean aBreakLine, double aMaxLineLength)
	{
		int len = -1;

		for (int currentLen = 1, limit = aSpan.getText().length() - aTextOffset; currentLen <= limit; currentLen++)
		{
			if (currentLen == limit)
			{
				len = limit;
				aBreakLine.set(true);
				break;
			}

			double x = aTargetX + aSpan.getStyle().measureText(aSpan.getText(), aTextOffset, currentLen);

			if (x > aMaxLineLength)
			{
				if (len == -1)
				{
					len = currentLen - 1;
				}
				aBreakLine.set(true);
				break;
			}
			if (aSpan.getText().charAt(aTextOffset + currentLen) == ' ')
			{
				len = currentLen + 1;
			}
		}

//		System.out.println(aText.subList(aTextOffset, aTextOffset + len));
		return len;
	}
}
