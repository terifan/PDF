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

		double descent = new Symbol(mFont, mText.get(0).getText().charAt(0)).getFont().getDescent();
		double lineHeight = new Symbol(mFont, mText.get(0).getText().charAt(0)).getFont().getLineHeight();

		double boundsTop = mBoundsTop - descent - lineHeight;

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
//					content.println(
//						"q\n" +
////						"0 0 612 791.25 re\n" +
////						"W* n\n" +
////						"q\n" +
////						".75 0 0 .75 0 0 cm\n" +
//						"/G3 gs\n" +
//						"BT\n" +
//						font.getFontRef().getIdentity() + " " + font.getSize() + " Tf" + "\n" +
////						"1 0 0 -1 0 0 Tm\n" +
////						(float)(0*targetX) + " " + (float)(500+0*targetY ) + " Td " + String.format("<%04X>", font.getFontRef().lookup(new Symbol(font, 'X'))) + " Tj\n" +
//						(float)(20+0*targetX) + " " + (float)(500+0*targetY ) + " Td <004D> Tj\n" +
//						"ET\n" +
//						"Q\n" +
//						"Q"
//					);
					content.println("BT");
					content.println("%s %f Tf", font.getFontRef().getIdentity(), font.getSize());
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

						content.println("0.5 w 0 1 0 RG %1$f %2$f m %3$f %2$f l %3$f %4$f l %1$f %4$f l s", targetX, targetY + font.getDescent() + font.getLineHeight(), targetX + font.getAdvance(symbol), targetY + font.getDescent());

						targetX += 0 * font.getLeftBearing(symbol);

						content.println("%f %f Td <%04X> Tj", targetX - currentX, targetY - currentY, font.getFontRef().lookup(symbol));

						currentX = targetX;
						currentY = targetY;

						targetX += font.getAdvance(symbol) - 0 * font.getLeftBearing(symbol);
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
