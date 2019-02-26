package pdf.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import pdf.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import pdf.Font;
import pdf.Paragraph;


public class TextArea
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Font mFont;
	private ArrayList<Paragraph> mText;


	public TextArea(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, Font aFont, Paragraph... aText)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
		mFont = aFont;
		mText = new ArrayList<>(Arrays.asList(aText));
	}


	public String produce() throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output content = new Output(baos);

		content.println("1 0 0 RG");
		content.println(mBoundsLeft + " " + mBoundsTop + " m");
		content.println(mBoundsRight + " " + mBoundsTop + " l");
		content.println(mBoundsRight + " " + mBoundsBottom + " l");
		content.println(mBoundsLeft + " " + mBoundsBottom + " l");
		content.println("s");

		double ascent = new Symbol(mFont, mText.get(0).getText().charAt(0)).getFont().getAscent();
		System.out.println(ascent);

		double boundsTop = mBoundsTop - ascent;

		for (Paragraph paragraph : mText)
		{
			ArrayList<Symbol> text = new ArrayList<>();

			for (int i = 0; i < paragraph.getText().length(); i++)
			{
				try
				{
					text.add(new Symbol(mFont, paragraph.getText().charAt(i)));
				}
				catch (Exception e)
				{
					e.printStackTrace(System.out);
				}
			}

			double targetY = boundsTop;

			for (int textOffset = 0; textOffset < text.size();)
			{
				double targetX = mBoundsLeft;
				double currentX;

				for (;;)
				{
					AtomicBoolean breakLine = new AtomicBoolean(false);
					int nextSegmentLength = layoutLine(text, textOffset, targetX, breakLine, mBoundsRight);
					if (nextSegmentLength == 0)
					{
						break;
					}

					Font font = text.get(textOffset).getFont();

//					content.println("q");
//					content.println("0 0 595.28 841.89 re");
//					content.println("W* n");
//					content.println("q");
//					content.println("1 0 0 1 0 0 cm");
//					content.println("/G3 gs");

					content.println("BT");
					content.println("/" + font.getFontRef().getIdentity() + " " + font.getSize() + " Tf");
					currentX = 0;

					double currentY = 0;

					if (targetY < mBoundsBottom)
					{
						textOffset = text.size();
						break;
					}

					for (int i = 0; i < nextSegmentLength; i++, textOffset++)
					{
						Symbol symbol = text.get(textOffset);

						targetX += font.getLeftBearing(symbol);

						content.print((float)(targetX - currentX) + " " + (float)(targetY - currentY) + " Td ");
						content.println(String.format("<%04X>", font.getFontRef().lookup(symbol)) + " Tj");

						currentX = targetX;
						currentY = targetY;

						targetX += font.getAdvance(symbol) - font.getLeftBearing(symbol);
					}

					targetY -= font.getLineHeight();

					if (breakLine.get())
					{
						break;
					}
				}

				content.println("ET");
//				content.println("Q");
//				content.println("Q");
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

			double x = aTargetX + symbol.getFont().measureText(aText, aTextOffset, i);

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
