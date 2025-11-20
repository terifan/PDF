package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.terifan.pdfwriter.Insets.THIN;
import static org.terifan.pdfwriter.Insets.ZERO;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class Paragraph implements Content, Cloneable
{
	private ArrayList<Span> mSpans;
	private Alignment mAlignment;
	private VerticalAlignment mVerticalAlignment;
	private double mHeight;
	private Insets mMargins;
	private double mWidth;
	private ArrayList<Row> mLayout;

	private Insets mBorderThickness;
	private Color mBorderColor;
	private Color mFillColor;
	private String mBorderPattern;


	public Paragraph()
	{
		mSpans = new ArrayList<>();
		mAlignment = Alignment.LEFT;
		mMargins = new Insets();
		mBorderThickness = ZERO;
		mVerticalAlignment = VerticalAlignment.BASELINE;
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
			if (s != null)
			{
				mSpans.add(new Span(aStyle, s));
			}
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


	public ArrayList<Span> getSpans()
	{
		return mSpans;
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


	public Insets getBorderThickness()
	{
		return mBorderThickness;
	}


	public Paragraph setBorderThickness(Insets aBorderThickness)
	{
		mBorderThickness = aBorderThickness;
		return this;
	}


	public Paragraph setBorder(Color aBorderColor, Insets aBorderThickness)
	{
		mBorderColor = aBorderColor;
		mBorderThickness = aBorderThickness;
		return this;
	}


	public String getBorderPattern()
	{
		return mBorderPattern;
	}


	public Paragraph setBorderPattern(String aBorderPattern)
	{
		mBorderPattern = aBorderPattern;
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


	public Insets getMargins()
	{
		return mMargins;
	}


	public Paragraph setMargins(Insets aMargins)
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


	@Override
	public double getLayoutWidth()
	{
		return mWidth;
	}


	@Override
	public double getLayoutHeight()
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

		Output textOutput = new Output();
		Output fillOutput = new Output();
		Output lineOutput = new Output();

		double nextOffsetY = aBoundsTop - mMargins.top();

		renderRectangle(fillOutput, lineOutput, aBoundsLeft, aBoundsTop, aBoundsRight, Math.max(aBoundsTop - mHeight, aBoundsBottom), mFillColor, mBorderThickness, mBorderPattern, mBorderColor);
//		renderRectangle(fillOutput, lineOutput, aBoundsLeft, aBoundsTop, aBoundsRight, Math.max(aBoundsTop - mHeight, aBoundsBottom), mFillColor, THIN, mBorderPattern, Color.RED);

		while (!mLayout.isEmpty())
		{
			Row row = mLayout.get(0);

			if (nextOffsetY - row.height < aBoundsBottom)
			{
				break;
			}

			mLayout.remove(0);

			if (mAlignment != null && !row.isEmpty())
			{
				switch (mAlignment)
				{
					case LEFT:
					{
						double adjust = aBoundsLeft - row.get(0).x0 + mMargins.left();
						for (Chunk chunk : row)
						{
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
						break;
					}
					case CENTER:
					{
						double adjust = aBoundsRight - row.get(row.size() - 1).xt;
						adjust /= 2;
						for (Chunk chunk : row)
						{
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
						break;
					}
					case RIGHT:
					{
						double adjust = aBoundsRight - row.get(row.size() - 1).xt;
						adjust -= mMargins.right();
						for (Chunk chunk : row)
						{
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
						break;
					}
					case SPLIT:
					{
						double adjust = aBoundsRight - row.get(row.size() - 1).xt;
						adjust -= mMargins.right();
						for (int i = row.size() / 2; i < row.size(); i++)
						{
							Chunk chunk = row.get(i);
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
						break;
					}
				}
			}

			double topMargin = 0;
			double botMargin = 0;
			double maxAscent = -100000;
			double maxDescent = 100000;

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();
				Insets margins = style.getMargins();
				Insets thickness = style.getBorderThickness();
				if (margins == null)
				{
					margins = ZERO;
				}
				if (thickness == null)
				{
					thickness = ZERO;
				}
				topMargin = Math.max(topMargin, thickness.top() + margins.top());
				botMargin = Math.max(botMargin, thickness.bottom() + margins.bottom());
				maxAscent = Math.max(maxAscent, style.getAscent());
				maxDescent = Math.min(maxDescent, style.getDescent());
			}

			double top = -100000;
			double bot = 100000;
			double extra = 0;
			double gap = 0;

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();
				chunk.y0 = nextOffsetY - maxAscent - style.getAdjust();
				chunk.y1 = nextOffsetY - maxAscent - style.getAdjust() - row.height;
				chunk.yt = Math.round(chunk.y0 - topMargin - style.getAscent()); // a round feels wrong but looks better when rendered!

				top = Math.max(top, nextOffsetY);
				bot = Math.min(bot, nextOffsetY + chunk.y1 - chunk.y0);

				extra = Math.max(extra, style.getLineExtra());
				gap = Math.max(gap, style.getLineGap());
			}

			bot -= extra;
			if (!mLayout.isEmpty())
			{
				bot += gap;
			}

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();

				if (chunk.verticalAlignment != null && chunk.verticalAlignment != VerticalAlignment.TOP)
				{
					double adjust;
					if (chunk.verticalAlignment == VerticalAlignment.BASELINE)
					{
						adjust = style.getAscent();
					}
					else if (chunk.verticalAlignment == VerticalAlignment.BOTTOM)
					{
						adjust = style.getLineHeight() - style.getDescent();
					}
					else if (chunk.verticalAlignment == VerticalAlignment.CENTER)
					{
						adjust = chunk.y0 - chunk.yt;
						adjust /= 2;
					}
					else
					{
						adjust = chunk.y0 - chunk.yt;
						adjust -= mMargins.top();
					}
					chunk.yt += adjust;
				}

				chunk.y1 -= extra;
				chunk.yt -= extra;

				renderRectangle(fillOutput, lineOutput, chunk.x0, top, row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1, bot, style.getFillColor(), style.getBorderThickness(), style.getBorderPattern(), style.getBorderColor());
//				renderRectangle(fillOutput, lineOutput, chunk.x0, top, row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1, bot, Color.YELLOW, style.getBorderThickness(), style.getBorderPattern(), style.getBorderColor());

				if (style.getHighlightColor() != null)
				{
					double x1 = row.indexOf(chunk) == row.size() - 1 ? chunk.xt : chunk.x1;

					fillOutput.println("%s rg", style.getHighlightColor());
					fillOutput.println("%f %f m", chunk.x0, chunk.yt + style.getAscent());
					fillOutput.println("%f %f l", x1, chunk.yt + style.getAscent());
					fillOutput.println("%f %f l", x1, chunk.yt + style.getDescent());
					fillOutput.println("%f %f l", chunk.x0, chunk.yt + style.getDescent());
					fillOutput.println("f");
				}

				String text = chunk.span.getText();

				double advanceX = chunk.x0 + style.getBorderThickness().left();
				double advanceY = chunk.yt;
				double offsetX = advanceX;

				aPage.registerFont(style);

				textOutput.println("BT");
				textOutput.println("%s %f Tf", style.getIdentity(), style.getSize());
				textOutput.println("1 0 0 1 0 0 Tm");
				if (style.getTextColor() != null)
				{
					textOutput.println("%s rg", style.getTextColor());
				}

				for (int i = 0; i < chunk.length; i++)
				{
					char ch = text.charAt(chunk.offset + i);

					if (ch == ' ')
					{
						textOutput.println("ET");

						textOutput.println("BT");
						textOutput.println("%s %f Tf", style.getIdentity(), style.getSize());
						textOutput.println("1 0 0 1 0 0 Tm");
						textOutput.println("%f %f Td <%s> Tj", offsetX, chunk.yt, "%04X".formatted(style.getGlyphIndex(ch)));
						textOutput.println("ET");

						textOutput.println("BT");
						textOutput.println("%s %f Tf", style.getIdentity(), style.getSize());
						textOutput.println("1 0 0 1 0 0 Tm");
						if (style.getTextColor() != null)
						{
							textOutput.println("%s rg", style.getTextColor());
						}

						offsetX += style.getAdvance(ch);
						advanceX = offsetX;
						advanceY = chunk.yt;
					}
					else if (ch >= ' ')
					{
						textOutput.println("%f %f Td <%s> Tj ", advanceX, advanceY, "%04X".formatted(style.getGlyphIndex(ch)));
						advanceX = style.getAdvance(ch);
						advanceY = 0;
						offsetX += advanceX;
					}
				}

				textOutput.println("ET");
			}

			mHeight -= row.height;
			nextOffsetY -= row.height;
		}

		aOutput.append(fillOutput);
		aOutput.append(lineOutput);

		if (textOutput.size() > 0)
		{
			aOutput.append(textOutput);
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
				AtomicBoolean oLineEnd = new AtomicBoolean(false);

				int chunkLen = findSpanCutoff(span, offset, x, oBreakLine, oLineEnd, aBoundsRight - mMargins.right(), currentRow.isEmpty());

				if (chunkLen == 0)
				{
					break;
				}

				if (chunkLen > 0)
				{
					Chunk chunk = new Chunk(x, span, offset, chunkLen, span.getVerticalAlignment() != null ? span.getVerticalAlignment() : mVerticalAlignment);
					currentRow.add(chunk);

					x += span.getStyle().getBorderThickness().left();
					x += span.getStyle().getBorderThickness().right();

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
					chunk.lineEnd = oLineEnd.get();
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

		for (int i = 0; i < mLayout.size(); i++)
		{
			Row row = mLayout.get(i);

			double rowHeight = 0;
			double width = 0;
			double gap = 0;

			if (i < mLayout.size() - 1)
			{
				for (Chunk chunk : row)
				{
					Style style = chunk.span.getStyle();
					gap = Math.max(gap, style.getLineGap());
				}
			}

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();

				rowHeight = Math.max(rowHeight, style.getLineExtra() + style.getLineHeight() + gap + style.getBorderThickness().top() + style.getBorderThickness().bottom());

				width += chunk.x1 - chunk.x0;
			}

			row.height = rowHeight;
			mHeight += rowHeight;
			mWidth = Math.max(mWidth, width);
		}

		mWidth += mMargins.left() + mMargins.right();
	}


	private static int findSpanCutoff(Span aSpan, int aTextOffset, double aOffsetX, AtomicBoolean oBreakLine, AtomicBoolean oLineEnd, double aLimitX, boolean aFirstWord)
	{
		if (aSpan.getText().charAt(aTextOffset) == '\n')
		{
			oBreakLine.set(true);
			oLineEnd.set(true);
			return 1;
		}

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
				oBreakLine.set(true);
				break;
			}

			char c = aSpan.getText().charAt(aTextOffset + i);

			if (c == '\n')
			{
				len = i + 1;
				oBreakLine.set(true);
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
		boolean lineEnd;


		public Chunk(double aX0, Span aSpan, int aOffset, int aLength, VerticalAlignment aVerticalAlignment)
		{
			x0 = aX0;
			span = aSpan;
			offset = aOffset;
			length = aLength;
			verticalAlignment = aVerticalAlignment;
		}


		@Override
		public String toString()
		{
			return "Chunk{" + "x0=" + x0 + ", x1=" + x1 + ", y0=" + y0 + ", y1=" + y1 + ", xt=" + xt + ", yt=" + yt + ", offset=" + offset + ", length=" + length + ", verticalAlignment=" + verticalAlignment + ", lineEnd=" + lineEnd + '}';
		}
	}


	@Override
	public String toString()
	{
		return "Paragraph" + mSpans;
	}


	public Paragraph clear()
	{
		mSpans.clear();
		return this;
	}


	@Override
	public Paragraph clone()
	{
		Paragraph para;
		try
		{
			para = (Paragraph)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			para = new Paragraph();
		}
		para.mSpans = (ArrayList<Span>)mSpans.clone();
		para.mAlignment = mAlignment;
		para.mVerticalAlignment = mVerticalAlignment;
		para.mBorderColor = mBorderColor == null ? null : mBorderColor.clone();
		para.mFillColor = mFillColor == null ? null : mFillColor.clone();
		para.mMargins = mMargins.clone();
		para.mWidth = mWidth;
		para.mHeight = mHeight;
		return para;
	}
}
