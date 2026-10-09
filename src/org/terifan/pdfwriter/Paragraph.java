package org.terifan.pdfwriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.terifan.pdfwriter.Utilities.renderRectangle;


public class Paragraph implements Content, Cloneable
{
	private ArrayList<Span> mSpans;
	private Span mSplitSpan;
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
		mVerticalAlignment = VerticalAlignment.BASELINE;
	}


	public Paragraph(Style aStyle, String... aText)
	{
		this();
		add(aStyle, aText);
	}


	public Paragraph(Style aStyle, Collection<String> aText)
	{
		this();
		add(aStyle, aText.toArray(String[]::new));
	}


	public Paragraph(Span... aSpans)
	{
		this();
		add(aSpans);
	}


	public Paragraph(Collection<Span> aSpans)
	{
		this();
		add(aSpans.toArray(Span[]::new));
	}


	public Paragraph add(Span... aSpan)
	{
		mSpans.addAll(Arrays.asList(aSpan));
		return this;
	}


	public Paragraph add(Style aStyle, String... aText)
	{
		for (String s : aText)
		{
			if (s != null)
			{
				mSpans.add(new Span(aStyle, s));
			}
		}
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


	public Insets getBorderThickness(Insets aInsets)
	{
		if (aInsets == null)
		{
			aInsets = new Insets();
		}
		return aInsets.set(mBorderThickness);
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

		renderRectangle(null, aPage, fillOutput, lineOutput, aBoundsLeft, aBoundsTop, aBoundsRight, Math.max(aBoundsTop - mHeight, aBoundsBottom), mFillColor, mBorderThickness, mBorderPattern, mBorderColor);

		textOutput.println("q");

		while (!mLayout.isEmpty())
		{
			Row row = mLayout.get(0);

			if (nextOffsetY - row.height < aBoundsBottom)
			{
				break;
			}

			mLayout.remove(0);

			Chunk firstChunk = row.isEmpty() ? null : row.get(0);
			Chunk lastChunk = row.isEmpty() ? null : row.get(row.size() - 1);
			if (mAlignment != null && !row.isEmpty())
			{
				switch (mAlignment)
				{
					case LEFT:
					{
						double adjust = aBoundsLeft - firstChunk.x0 + mMargins.left();
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
						double adjust = aBoundsLeft + aBoundsRight - firstChunk.x0 - lastChunk.xt;
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
						double adjust = aBoundsRight - lastChunk.xt - mMargins.right();
						for (Chunk chunk : row)
						{
							chunk.x0 += adjust;
							chunk.x1 += adjust;
							chunk.xt += adjust;
						}
						break;
					}
					case JUSTIFY:
					{
						break;
					}
					case SPLIT:
					{
						Span leftSpan = mSplitSpan;
						Chunk lastRightChunk = null;
						for (Chunk chunk : row)
						{
							if (chunk.span != leftSpan)
							{
								lastRightChunk = chunk;
							}
						}
						if (lastRightChunk != null)
						{
							double adjust = aBoundsRight - lastRightChunk.xt - mMargins.right();
							for (Chunk chunk : row)
							{
								if (chunk.span != leftSpan)
								{
									chunk.x0 += adjust;
									chunk.x1 += adjust;
									chunk.xt += adjust;
								}
							}
						}
						break;
					}
				}
			}

			int justifiedSpaces = mAlignment == Alignment.JUSTIFY && row.softWrapped ? countJustifiableSpaces(row) : 0;
			double justifiedSpace = justifiedSpaces == 0 ? 0 : Math.max(0, (aBoundsRight - mMargins.right() - lastChunk.xt) / justifiedSpaces);
			int justifiedSpacesUsed = 0;

			double topMargin = 0;
			double maxAscent = -100000;

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();
				Insets margins = style.getMargins(null);
				Insets thickness = style.getBorderThickness(null);
				topMargin = Math.max(topMargin, thickness.top() + margins.top());
				maxAscent = Math.max(maxAscent, style.getAscent());
			}

			double top = nextOffsetY;
			double bot = 100000;
			double gap = 0;

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();
				chunk.y0 = nextOffsetY - maxAscent - style.getAdjust();
				chunk.y1 = nextOffsetY - maxAscent - style.getAdjust() - row.height;
				chunk.yt = chunk.y0 - topMargin - style.getAscent();

				gap = Math.max(gap, style.getLineGap());
			}

			for (Chunk chunk : row)
			{
				bot = Math.min(bot, nextOffsetY + chunk.y1 - chunk.y0);
			}

			if (!mLayout.isEmpty())
			{
				bot += gap;
			}

			Color lastColor = null;
			Dictionary lastTextExtGState = null;

			for (Chunk chunk : row)
			{
				Style style = chunk.span.getStyle();
				aPage.registerFont(style);

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

				Insets borderThickness = style.getBorderThickness(null);
				int chunkJustifiedSpaces = Math.min(countSpaces(chunk), justifiedSpaces - justifiedSpacesUsed);
				double chunkJustifyOffset = justifiedSpace * justifiedSpacesUsed;

				double rectX0 = chunk.x0 + chunkJustifyOffset;
				double rectX1 = (chunk == lastChunk ? chunk.xt : chunk.x1) + chunkJustifyOffset + justifiedSpace * chunkJustifiedSpaces;

				renderRectangle(style.getExtGState(), aPage, fillOutput, lineOutput, rectX0, Math.ceil(top), rectX1, (int)bot, style.getFillColor(), borderThickness, style.getBorderPattern(), style.getBorderColor());

				if (style.getHighlightColor() != null)
				{
					fillOutput.println("%s rg", style.getHighlightColor());
					fillOutput.println("%f %f m", rectX0, chunk.yt + style.getAscent());
					fillOutput.println("%f %f l", rectX1, chunk.yt + style.getAscent());
					fillOutput.println("%f %f l", rectX1, chunk.yt + style.getDescent());
					fillOutput.println("%f %f l", rectX0, chunk.yt + style.getDescent());
					fillOutput.println("f");
				}

				String text = chunk.span.getText();

				double offsetX = chunk.x0 + borderThickness.left() + chunkJustifyOffset;
				double advanceX = offsetX;
				double advanceY = chunk.yt;

				for (int i = 0, prev = -1, curr; i < chunk.length; i++)
				{
					char ch = text.charAt(chunk.offset + i);
					curr = Character.isWhitespace(ch) ? 0 : 1;

					if (curr != prev)
					{
						if (i > 0)
						{
							textOutput.println("ET");
						}

						advanceX = offsetX;
						advanceY = chunk.yt;

						Color color = style.getTextColor();
						if (color != null && !color.equals(lastColor))
						{
							textOutput.println("%s RG %s rg", color, color);
							lastColor = color;
						}
						if (style.getExtGState() != lastTextExtGState)
						{
							if (style.getExtGState() != null)
							{
								textOutput.println("%s gs", aPage.registerExtGState(style.getExtGState()));
							}
							lastTextExtGState = style.getExtGState();
						}

						textOutput.println("BT");
						textOutput.println("%s %f Tf", style.getIdentity(), style.getSize());
						textOutput.println("1 0 0 1 0 0 Tm");
					}

					textOutput.println("%f %f Td <%s> Tj ", advanceX, advanceY, "%04X".formatted(style.getGlyphIndex(ch)));

					double characterAdvance = style.getAdvance(ch);
					if (ch == ' ' && justifiedSpacesUsed < justifiedSpaces)
					{
						characterAdvance += justifiedSpace;
						justifiedSpacesUsed++;
					}
					advanceX = characterAdvance;
					advanceY = 0;
					offsetX += characterAdvance;
					prev = curr;
				}

				textOutput.println("ET");
			}

			mHeight -= row.height;
			nextOffsetY -= row.height;
		}

		textOutput.println("Q");
		textOutput.println("EMC");

		aOutput.append(fillOutput);
		aOutput.append(lineOutput);
		aOutput.append(textOutput);

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

		mSplitSpan = null;
		for (Span span : spans)
		{
			if (span.getText() != null && !span.getText().isEmpty())
			{
				mSplitSpan = span;
				break;
			}
		}

		double screenX = aBoundsLeft + mMargins.left();
		boolean endedWithLineBreak = false;
		boolean skipLeadingLineFeed = false;
		boolean skipLeadingSpaces = false;

		for (int spanIndex = 0; spanIndex < spans.size(); spanIndex++)
		{
			Span span = spans.get(spanIndex);
			Style style = span.getStyle();

			if (span.getText() == null)
			{
				continue;
			}

			int firstCharacterOffset = 0;
			if (skipLeadingLineFeed)
			{
				if (span.getText().isEmpty())
				{
					continue;
				}
				if (span.getText().charAt(0) == '\n')
				{
					firstCharacterOffset = 1;
				}
				skipLeadingLineFeed = false;
			}

			for (int charOffset = firstCharacterOffset; charOffset < span.getText().length();)
			{
				char currentChar = span.getText().charAt(charOffset);
				if (skipLeadingSpaces && currentRow.isEmpty() && currentChar == ' ')
				{
					charOffset++;
					continue;
				}
				skipLeadingSpaces = false;

				if (currentChar == '\n' || currentChar == '\r')
				{
					Insets styleMargins = style.getMargins(null);
					Insets borderThickness = style.getBorderThickness(null);
					double emptyLineHeight = styleMargins.vertical() + style.getLineHeight() + borderThickness.vertical();
					currentRow.minimumHeight = Math.max(currentRow.minimumHeight, emptyLineHeight);
					currentRow.minimumGap = Math.max(currentRow.minimumGap, style.getLineGap());
					currentRow = new Row();
					currentRow.minimumHeight = emptyLineHeight;
					currentRow.minimumGap = style.getLineGap();
					rows.add(currentRow);
					screenX = aBoundsLeft + mMargins.left();
					boolean followedByLineFeed = currentChar == '\r' && charOffset + 1 < span.getText().length() && span.getText().charAt(charOffset + 1) == '\n';
					if (currentChar == '\r' && charOffset + 1 == span.getText().length())
					{
						skipLeadingLineFeed = startsWithLineFeed(spans, spanIndex + 1);
					}
					charOffset += followedByLineFeed ? 2 : 1;
					endedWithLineBreak = true;
					continue;
				}

				AtomicBoolean oBreakLine = new AtomicBoolean(false);
				Insets borderThickness = style.getBorderThickness(null);

				double x0 = screenX + borderThickness.left();
				double x1 = aBoundsRight - mMargins.right() - borderThickness.right();
				int charLen = findSpanCutoff(span, charOffset, x0, oBreakLine, x1, currentRow.isEmpty());

				if (charLen == 0)
				{
					if (!oBreakLine.get())
					{
						break;
					}
					currentRow.softWrapped = true;
					currentRow = new Row();
					rows.add(currentRow);
					screenX = aBoundsLeft + mMargins.left();
					skipLeadingSpaces = true;
					continue;
				}

				if (charLen > 0)
				{
					Chunk chunk = new Chunk(screenX, span, charOffset, charLen, span.getVerticalAlignment() != null ? span.getVerticalAlignment() : mVerticalAlignment);
					currentRow.add(chunk);

					screenX += borderThickness.horizontal();

					double bestX = screenX;
					for (int i = 0; i < charLen; i++, charOffset++)
					{
						char c = span.getText().charAt(charOffset);
						if (c < ' ')
						{
							c = ' ';
						}
						screenX += style.getAdvance(c);
						if (c != ' ')
						{
							bestX = screenX;
						}
					}

					chunk.x1 = screenX;
					chunk.xt = bestX;
					endedWithLineBreak = false;
				}

				if (oBreakLine.get())
				{
					currentRow.softWrapped = true;
					screenX = aBoundsLeft + mMargins.left();
					currentRow = new Row();
					rows.add(currentRow);
					skipLeadingSpaces = true;
				}
			}
		}

		if (rows.get(rows.size() - 1).isEmpty() && !endedWithLineBreak)
		{
			rows.remove(rows.size() - 1);
		}

		mLayout = rows;
		mWidth = 0;
		mHeight = mMargins.top() + mMargins.bottom();

		for (int i = 0; i < mLayout.size(); i++)
		{
			Row row = mLayout.get(i);

			double rowHeight = row.minimumHeight;
			double width = 0;
			double gap = row.minimumGap;

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

				Insets margins = style.getMargins(null);
				Insets borderThickness = style.getBorderThickness(null);

				rowHeight = Math.max(rowHeight, margins.vertical() + style.getLineHeight() + gap + borderThickness.vertical());

				width += chunk.x1 - chunk.x0;
			}
			if (row.isEmpty() && i < mLayout.size() - 1)
			{
				rowHeight += gap;
			}

			row.height = rowHeight;
			mHeight += rowHeight;
			mWidth = Math.max(mWidth, width);
		}

		mWidth += mMargins.left() + mMargins.right();
	}


	private static boolean startsWithLineFeed(ArrayList<Span> aSpans, int aStartIndex)
	{
		for (int i = aStartIndex; i < aSpans.size(); i++)
		{
			String text = aSpans.get(i).getText();
			if (text != null && !text.isEmpty())
			{
				return text.charAt(0) == '\n';
			}
		}
		return false;
	}


	private static int findSpanCutoff(Span aSpan, int aTextOffset, double aOffsetX, AtomicBoolean oBreakLine, double aLimitX, boolean aFirstWord)
	{
		int len = -1;

		for (int i = 1, limit = aSpan.getText().length() - aTextOffset; i <= limit; i++)
		{
			char character = aSpan.getText().charAt(aTextOffset + i - 1);
			if (character == '\n' || character == '\r')
			{
				return i - 1;
			}

			if (aOffsetX + aSpan.getStyle().measureText(aSpan.getText(), aTextOffset, i) > aLimitX)
			{
				oBreakLine.set(true);
				if (len != -1)
				{
					return len;
				}
				return aFirstWord ? Math.max(1, i - 1) : 0;
			}

			if (character == ' ')
			{
				len = i;
			}

			if (i == limit)
			{
				return limit;
			}
		}

		return len;
	}


	private static int countJustifiableSpaces(Row aRow)
	{
		int count = 0;
		boolean hasTextAfter = false;

		for (int i = aRow.size() - 1; i >= 0; i--)
		{
			Chunk chunk = aRow.get(i);
			String text = chunk.span.getText();
			for (int j = chunk.offset + chunk.length - 1; j >= chunk.offset; j--)
			{
				char c = text.charAt(j);
				if (c == ' ')
				{
					if (hasTextAfter)
					{
						count++;
					}
				}
				else
				{
					hasTextAfter = true;
				}
			}
		}

		return count;
	}


	private static int countSpaces(Chunk aChunk)
	{
		int count = 0;
		String text = aChunk.span.getText();
		for (int i = aChunk.offset; i < aChunk.offset + aChunk.length; i++)
		{
			if (text.charAt(i) == ' ')
			{
				count++;
			}
		}
		return count;
	}


	static class Row extends ArrayList<Chunk>
	{
		double height;
		double minimumHeight;
		double minimumGap;
		boolean softWrapped;
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


		@Override
		public String toString()
		{
			return "Chunk{" + "x0=" + x0 + ", x1=" + x1 + ", y0=" + y0 + ", y1=" + y1 + ", xt=" + xt + ", yt=" + yt + ", offset=" + offset + ", length=" + length + ", verticalAlignment=" + verticalAlignment + '}';
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
