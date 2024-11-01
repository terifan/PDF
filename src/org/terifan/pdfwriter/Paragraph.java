package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class Paragraph implements Content
{
	private ArrayList<Span> mSpans;
	private Alignment mAlignment;
	private VerticalAlignment mVerticalAlignment;
	private double mHeight;
	private Color mBorderColor;
	private Color mFillColor;
	private Margins mMargins;
	private double mWidth;

	private ArrayList<Row> mLayout;


	public Paragraph()
	{
		mSpans = new ArrayList<>();
		mAlignment = Alignment.LEFT;
		mMargins = new Margins();
	}


	public Paragraph(Style aStyle, String... aText)
	{
		this(aStyle, Arrays.asList(aText));
	}


	public Paragraph(Style aStyle, Collection<String> aText)
	{
		this();
		for (String s : aText)
		{
			mSpans.add(new Span(aStyle, s));
		}
	}


	public Paragraph(Span... aSpans)
	{
		this(Arrays.asList(aSpans));
	}


	public Paragraph(Collection<Span> aSpans)
	{
		this();
		mSpans.addAll(aSpans);
	}


	public Paragraph add(Span aSpan)
	{
		mSpans.add(aSpan);
		return this;
	}


	public Color getBorderColor()
	{
		return mBorderColor;
	}


	public Paragraph setBorderColor(Color aBorderColor)
	{
		mBorderColor = aBorderColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Paragraph setFillColor(Color aFillColor)
	{
		mFillColor = aFillColor;
		return this;
	}


	public Margins getMargins()
	{
		return mMargins;
	}


	public Paragraph setMargins(Margins aMargins)
	{
		mMargins = aMargins;
		return this;
	}


	public Paragraph setMarginRight(double aValue)
	{
		mMargins.setRight(aValue);
		return this;
	}


	public Paragraph setMarginLeft(double aValue)
	{
		mMargins.setLeft(aValue);
		return this;
	}


	public Paragraph setMarginTop(double aValue)
	{
		mMargins.setTop(aValue);
		return this;
	}


	public Paragraph setMarginBottom(double aValue)
	{
		mMargins.setBottom(aValue);
		return this;
	}


	public VerticalAlignment getVerticalAlignment()
	{
		return mVerticalAlignment;
	}


	public Paragraph setVerticalAlignment(VerticalAlignment aVerticalAlignment)
	{
		mVerticalAlignment = aVerticalAlignment;
		return this;
	}


	public Alignment getAlignment()
	{
		return mAlignment;
	}


	public Paragraph setAlignment(Alignment aAlignment)
	{
		mAlignment = aAlignment;
		return this;
	}


	public ArrayList<Span> getSpans()
	{
		return mSpans;
	}


	@Override
	public double getWidth()
	{
		return mWidth;
	}


	@Override
	public double getHeight()
	{
		return mHeight;
	}


	@Override
	public boolean isConsumed()
	{
		return mLayout != null && mLayout.isEmpty();
	}


	@Override
	public void reuseContent()
	{
		mLayout = null;
	}


	@Override
	public double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
		if (mLayout == null)
		{
			throw new IllegalStateException("layout not called");
		}

		boolean firstRow = true;
		double nextOffsetY = aBoundsTop - mMargins.top();

		while (!mLayout.isEmpty())
		{
			Row row = mLayout.get(0);

			if (nextOffsetY - row.height < aBoundsBottom)
			{
				break;
			}

			mLayout.remove(0);

			if (firstRow)
			{
				renderRectangle(aOutput, aBoundsLeft, aBoundsTop, aBoundsRight, Math.max(aBoundsTop - mHeight, aBoundsBottom), null, null, mFillColor, mBorderColor);
				firstRow = false;
			}

			if (!row.isEmpty())
			{
				double adjust = aBoundsLeft - row.get(0).x0 + mMargins.left();
				for (Chunk chunk : row)
				{
					chunk.x0 += adjust;
					chunk.x1 += adjust;
					chunk.xt += adjust;
				}
				if (mAlignment != Alignment.LEFT)
				{
					adjust = aBoundsRight - row.get(row.size() - 1).xt;
					if (mAlignment == Alignment.SPLIT)
					{
						adjust -= mMargins.right();
						for (int i = row.size() / 2; i < row.size(); i++)
						{
							Chunk chunk = row.get(i);
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
					}
					else
					{
						if (mAlignment == Alignment.CENTER)
						{
							adjust /= 2;
						}
						else
						{
							adjust -= mMargins.right();
						}
						for (Chunk chunk : row)
						{
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
					}
				}
			}

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();

				aOutput.println("BT");

				chunk.y0 = nextOffsetY;
				chunk.y1 = nextOffsetY - row.height;
				chunk.yt = chunk.y1 + style.getLineHeight() - mMargins.top();

				if (chunk.verticalAlignment != null && chunk.verticalAlignment != VerticalAlignment.BOTTOM)
				{
					double adjust;
					if (chunk.verticalAlignment == VerticalAlignment.CENTER)
					{
						adjust = chunk.y0 - chunk.yt;
						adjust /= 2;
					}
					else
					{
						adjust = chunk.y0 - chunk.yt;
						adjust -= mMargins.top();
					}
					chunk.y0 += adjust;
					chunk.y1 += adjust;
					chunk.yt += adjust;
				}

				renderRectangle(aOutput, chunk.x0, chunk.y0, row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1, chunk.y1, null, null, style.getFillColor(), style.getBorderColor());

				if (style.getHighlightColor() != null)
				{
					double x1 = row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1;

					aOutput.println("%s rg", style.getHighlightColor());
					aOutput.println("%f %f m", chunk.x0, chunk.yt);
					aOutput.println("%f %f l", x1, chunk.yt);
					aOutput.println("%f %f l", x1, chunk.y1);
					aOutput.println("%f %f l", chunk.x0, chunk.y1);
					aOutput.println("f");
				}

				String text = chunk.span.getText();

				aPage.registerFont(style);

				if (style.getTextColor() != null)
				{
					aOutput.println("%s rg", style.getTextColor());
				}

				aOutput.println("%s %f Tf", style.getIdentity(), style.getSize());

				double x = chunk.x0;
				double y = chunk.y1 - style.getDescent();

				for (int i = 0; i < chunk.length; i++)
				{
					char ch = text.charAt(chunk.offset + i);
					if (ch < ' ')
					{
						ch = ' ';
					}

					aOutput.println("%f %f Td <%04X> Tj", x, y, style.getGlyphIndex(ch));

					x = style.getAdvance(ch);
					y = 0;
				}

				aOutput.println("ET");
			}

			mHeight -= row.height;
			nextOffsetY -= row.height;
		}

		return nextOffsetY;
	}


	@Override
	public void layout(double aBoundsLeft, double aBoundsRight)
	{
		ArrayList<Row> rows = new ArrayList<>();
		Row currentRow = new Row();
		rows.add(currentRow);

		ArrayList<Span> spans = new ArrayList<>();

		if (mLayout == null)
		{
			spans.addAll(mSpans);
		}
		else
		{
			for (Row row : mLayout)
			{
				for (Chunk chunk : row)
				{
					spans.add(new Span(chunk.span.getStyle(), chunk.span.getText().substring(chunk.offset, chunk.offset + chunk.length)));
				}
			}
		}

		if (spans.isEmpty())
		{
			mLayout = rows;
			mHeight = 0;
			return;
		}

		double x = aBoundsLeft + mMargins.left();

		for (int spanIndex = 0; spanIndex < spans.size(); spanIndex++)
		{
			Span span = spans.get(spanIndex);
			Style style = span.getStyle();

			if (span.getText() == null)
			{
				continue;
			}

			for (int offset = 0; offset < span.getText().length();)
			{
				AtomicBoolean oBreakLine = new AtomicBoolean(false);

				int chunkLen = findSpanCutoff(span, offset, x, oBreakLine, aBoundsRight - mMargins.right(), currentRow.isEmpty());
				if (chunkLen == 0)
				{
					break;
				}

				if (chunkLen > 0)
				{
					Chunk chunk = new Chunk(x, span, offset, chunkLen, span.getVerticalAlignment() != null ? span.getVerticalAlignment() : mVerticalAlignment);
					currentRow.add(chunk);

					double bestX = x;
					for (int i = 0; i < chunkLen; i++, offset++)
					{
						char c = span.getText().charAt(offset);
						if (c < ' ')
						{
							c = ' ';
						}
						x += style.getAdvance(c);
						if (c != ' ')
						{
							bestX = x;
						}
					}

					chunk.x1 = x;
					chunk.xt = bestX;
				}

				if (oBreakLine.get())
				{
					x = aBoundsLeft + mMargins.left();
					currentRow = new Row();
					rows.add(currentRow);
				}
			}
		}

		if (rows.get(rows.size() - 1).isEmpty())
		{
			rows.remove(rows.size() - 1);
		}

		mLayout = rows;
		mWidth = 0;
		mHeight = mMargins.top() + mMargins.bottom();

		for (Row row : mLayout)
		{
			double rowHeight = 0;
			double width = 0;

			for (Chunk chunk : row)
			{
				rowHeight = Math.max(rowHeight, chunk.span.getStyle().getLineHeight());
				width += chunk.x1 - chunk.x0;
			}

			row.height = rowHeight;
			mHeight += rowHeight;
			mWidth = Math.max(mWidth, width);
		}

		mWidth += mMargins.left() + mMargins.right();
	}


	private static int findSpanCutoff(Span aSpan, int aTextOffset, double aOffsetX, AtomicBoolean aBreakLine, double aLimitX, boolean aFirstWord)
	{
		int len = -1;

		for (int i = 1, limit = aSpan.getText().length() - aTextOffset; i <= limit; i++)
		{
			if (i == limit)
			{
				return len == -1 ? limit : len;
			}

			if (aOffsetX + aSpan.getStyle().measureText(aSpan.getText(), aTextOffset, i) > aLimitX)
			{
				if (len == -1 && aFirstWord)
				{
					len = i - 1;
				}
				aBreakLine.set(true);
				break;
			}

			char c = aSpan.getText().charAt(aTextOffset + i);

			if (c == '\n')
			{
				len = i + 1;
				aBreakLine.set(true);
				break;
			}
			if (c == ' ' || c == '-' || c == ',' || c == '.' || c == ':' || c == ';' || c == '/')
			{
				len = i + 1;
			}
		}

		return len;
	}


	static class Row extends ArrayList<Chunk>
	{
		double height;
	}


	static class Chunk
	{
		double x0;
		double x1;
		double y0;
		double y1;
		double xt;
		double yt;
		Span span;
		int offset;
		int length;
		VerticalAlignment verticalAlignment;


		public Chunk(double aX0, Span aSpan, int aOffset, int aLength, VerticalAlignment aVerticalAlignment)
		{
			x0 = aX0;
			span = aSpan;
			offset = aOffset;
			length = aLength;
			verticalAlignment = aVerticalAlignment;
		}
	}


	@Override
	public String toString()
	{
		return "Paragraph" + mSpans;
	}
}
