package pdf.writer;

import pdf.Symbol;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import pdf.Font;


public class TextArea
{
	private double mBoundsTop;
	private double mBoundsLeft;
	private double mBoundsBottom;
	private double mBoundsRight;
	private Font mFont;
	private String mText;


	public TextArea(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight, Font aFont, String aText)
	{
		mBoundsTop = aBoundsTop;
		mBoundsLeft = aBoundsLeft;
		mBoundsBottom = aBoundsBottom;
		mBoundsRight = aBoundsRight;
		mFont = aFont;
		mText = aText;
	}


	public String produce()
	{
		mFont.getFontRef().update(mText);

		String content = "";

		content += "1 0 0 RG\n" + mBoundsLeft + " " + mBoundsTop + " m\n" + mBoundsRight + " " + mBoundsTop + "l\n" + mBoundsRight + " " + mBoundsBottom + "l\n" + mBoundsLeft + " " + mBoundsBottom + "l\n" + "s\n";

		ArrayList<Symbol> text = new ArrayList<>();

		for (int i = 0; i < mText.length(); i++)
		{
			try
			{
				text.add(new Symbol(mFont, mText.charAt(i)));
			}
			catch (Exception e)
			{
				//e.printStackTrace(System.out);
			}
		}

		double targetY = mBoundsTop;

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

				double lineHeight = font.getLineHeight() + font.getLineGap();

				content += "BT\n";
				content += "/" + font.getFontRef().getIdentity() + " " + font.getSize() + " Tf\n";
				currentX = 0;

				double currentY = 0;

				targetY -= lineHeight + font.getDescent();

				if (targetY < mBoundsBottom)
				{
					textOffset = text.size();
					break;
				}

				for (int i = 0; i < nextSegmentLength; i++, textOffset++)
				{
					Symbol symbol = text.get(textOffset);

					targetX += -font.getMinLeftSideBearing();

					content += (float)(targetX - currentX) + " " + (float)(targetY - currentY) + " Td ";
					content += String.format("<%04x>", symbol.getSymbol()) + " Tj\n";

					currentX = targetX;
					currentY = targetY;

					targetX += symbol.getWidth() - font.getMinRightSideBearing();
				}

				if (breakLine.get())
				{
					break;
				}
			}

			content += "ET\n";
		}

		return content;
	}


	private static int layoutLine(ArrayList<Symbol> aText, int aTextOffset, double aTargetX, AtomicBoolean aBreakLine, double aMaxLineLength)
	{
		int len = -1;

		for (int i = 0; i < aText.size() - aTextOffset; i++)
		{
			Symbol symbol = aText.get(aTextOffset + i);

			double x = aTargetX + aText.get(aTextOffset + i).getFont().measureText(aText, aTextOffset, i);

			if (x > aMaxLineLength)
			{
				aBreakLine.set(true);
				break;
			}
			if (i == aText.size() - aTextOffset - 1)
			{
				len = -1;
				aBreakLine.set(true);
				break;
			}
			if (symbol.isBreakChar())
			{
				len = i + 1;
			}
		}

		if (len == -1)
		{
			len = aText.size() - aTextOffset;
		}

//		System.out.println(aText.subList(aTextOffset, aTextOffset + len));
		return len;
	}
}
