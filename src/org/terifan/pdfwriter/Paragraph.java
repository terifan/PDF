package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


public class Paragraph
{
	private ArrayList<Span> mSpans;
	private Alignment mAlignment;
	private double mHeight;
	private Color mStrokeColor;
	private Color mFillColor;
	private ArrayList<ArrayList<Chunk>> mLayout;
	private boolean mReady;


	public Paragraph(Style aStyle, String aText)
	{
		this(new Span(aStyle, aText));
	}


	public Paragraph(Span... aSpans)
	{
		this(Arrays.asList(aSpans));
	}


	public Paragraph(List<Span> aSpans)
	{
		mSpans = new ArrayList<>(aSpans);
		mAlignment = Alignment.LEFT;
	}


	public Color getStrokeColor()
	{
		return mStrokeColor;
	}


	public Paragraph setStrokeColor(Color aStrokeColor)
	{
		this.mStrokeColor = aStrokeColor;
		return this;
	}


	public Color getFillColor()
	{
		return mFillColor;
	}


	public Paragraph setFillColor(Color aFillColor)
	{
		this.mFillColor = aFillColor;
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


	public double getHeight()
	{
		return mHeight;
	}


	public boolean isReady()
	{
		return mReady;
	}


	public double produce(PDFWriter aPDFWriter, Output aContent, Page aPage, double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
		boolean firstRow = true;
		double nextOffsetY = aBoundsTop;

		while (!mLayout.isEmpty())
		{
			ArrayList<Chunk> row = mLayout.get(0);

			double rowHeight = 0;

			for (Chunk chunk : row)
			{
				rowHeight = Math.max(rowHeight, chunk.span.getStyle().getLineHeight());
			}

			if (nextOffsetY - rowHeight < aBoundsBottom)
			{
				break;
			}

			mLayout.remove(0);

			if (firstRow && (mStrokeColor != null || mFillColor != null))
			{
				double y1 = Math.max(aBoundsTop - mHeight, aBoundsBottom);

				if (mStrokeColor != null) aContent.println("%s RG", mStrokeColor);
				if (mFillColor != null) aContent.println("%s rg", mFillColor);
				aContent.println(aBoundsLeft + " " + aBoundsTop + " m");
				aContent.println(aBoundsRight + " " + aBoundsTop + " l");
				aContent.println(aBoundsRight + " " + y1 + " l");
				aContent.println(aBoundsLeft + " " + y1 + " l");
				aContent.println(mFillColor != null && mStrokeColor != null ? "B" : mFillColor != null ? "f" : "s");

				firstRow = false;
			}

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();

				aContent.println("BT");

				chunk.y0 = nextOffsetY;
				chunk.y1 = nextOffsetY - rowHeight;
				chunk.yt = chunk.y1 + style.getLineHeight();

				if (style.getBoxStrokeColor() != null || style.getBoxFillColor() != null)
				{
					double x1 = row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1;

					if (style.getBoxStrokeColor() != null) aContent.println("%s RG", style.getBoxStrokeColor());
					if (style.getBoxFillColor() != null) aContent.println("%s rg", style.getBoxFillColor());
					aContent.println(chunk.x0 + " " + chunk.y0 + " m");
					aContent.println(x1 + " " + chunk.y0 + " l");
					aContent.println(x1 + " " + chunk.y1 + " l");
					aContent.println(chunk.x0 + " " + chunk.y1 + " l");
					aContent.println(style.getBoxFillColor() != null && style.getBoxStrokeColor() != null ? "B" : style.getBoxFillColor() != null ? "f" : "s");
				}

				if (style.getHighlightColor() != null)
				{
					double x1 = row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1;

					aContent.println("%s rg", style.getHighlightColor());
					aContent.println(chunk.x0 + " " + chunk.yt + " m");
					aContent.println(x1 + " " + chunk.yt + " l");
					aContent.println(x1 + " " + chunk.y1 + " l");
					aContent.println(chunk.x0 + " " + chunk.y1 + " l");
					aContent.println("f");
				}

				String text = chunk.span.getText();

				aPage.registerFont(style);

				if (style.getTextColor() != null)
				{
					aContent.println("%s rg", style.getTextColor());
				}

				aContent.println("%s %f Tf", style.getIdentity(), style.getSize());

				double x = chunk.x0;
				double y = chunk.y1 - style.getDescent();

				for (int i = 0; i < chunk.length; i++)
				{
					char ch = text.charAt(chunk.offset + i);

					aContent.println("%f %f Td <%04X> Tj", x, y, style.getGlyphIndex(ch));

					x = style.getAdvance(ch);
					y = 0;
				}

				aContent.println("ET");
			}

			mHeight -= rowHeight;
			nextOffsetY -= rowHeight;
		}

		return nextOffsetY;
	}


	public void layout(double aBoundsTop, double aBoundsLeft, double aBoundsBottom, double aBoundsRight) throws IOException
	{
		mReady = true;

		ArrayList<ArrayList<Chunk>> rows = new ArrayList<>();
		ArrayList<Chunk> currentRow = new ArrayList<>();
		rows.add(currentRow);

		ArrayList<Span> spans = getSpans();

		if (spans.isEmpty())
		{
			mLayout = rows;
			mHeight = 0;
			return;
		}

		double x = aBoundsLeft;

		for (int spanIndex = 0; spanIndex < spans.size(); spanIndex++)
		{
			Span span = spans.get(spanIndex);
			Style style = span.getStyle();

			for (int offset = 0; offset < span.getText().length();)
			{
				AtomicBoolean oBreakLine = new AtomicBoolean(false);

				int chunkLen = findSpanCutoff(span, offset, x, oBreakLine, aBoundsRight, currentRow.isEmpty());
				if (chunkLen == 0)
				{
					break;
				}

				if (chunkLen > 0)
				{
					Chunk chunk = new Chunk(x, span, offset, chunkLen);
					currentRow.add(chunk);

					double bestX = x;
					for (int i = 0; i < chunkLen; i++, offset++)
					{
						char c = span.getText().charAt(offset);
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
					x = aBoundsLeft;
					currentRow = new ArrayList<>();
					rows.add(currentRow);
				}
			}
		}

		mLayout = rows;
		mHeight = 0;

		for (ArrayList<Chunk> row : mLayout)
		{
			double rowHeight = 0;

			for (Chunk chunk : row)
			{
				rowHeight = Math.max(rowHeight, chunk.span.getStyle().getLineHeight());
			}

			mHeight += rowHeight;
		}
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

			if (aSpan.getText().charAt(aTextOffset + i) == '-')
			{
				len = i;
			}
			else if (aSpan.getText().charAt(aTextOffset + i) == ' ' || aSpan.getText().charAt(aTextOffset + i) == '-')
			{
				len = i + 1;
			}
		}

		return len;
	}


	private static class Chunk
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


		public Chunk(double aX0, Span aSpan, int aOffset, int aLength)
		{
			this.x0 = aX0;
			this.span = aSpan;
			this.offset = aOffset;
			this.length = aLength;
		}
	}
}
