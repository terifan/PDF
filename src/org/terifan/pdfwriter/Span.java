package org.terifan.pdfwriter;


public class Span implements Cloneable
{
	private VerticalAlignment mVerticalAlignment;
	private Style mStyle;
	private String mText;


	private Span()
	{
		this(null, null);
	}


	public Span(Style aStyle, String aText)
	{
		mVerticalAlignment = VerticalAlignment.BASELINE;
		mStyle = aStyle;
		mText = aText;
	}


	public String getText()
	{
		return mText;
	}


	public Span setText(String aText)
	{
		mText = aText;
		return this;
	}


	public Style getStyle()
	{
		return mStyle;
	}


	public Span setStyle(Style aStyle)
	{
		mStyle = aStyle;
		return this;
	}


	public VerticalAlignment getVerticalAlignment()
	{
		return mVerticalAlignment;
	}


	public Span setVerticalAlignment(VerticalAlignment aVerticalAlignment)
	{
		mVerticalAlignment = aVerticalAlignment;
		return this;
	}


	@Override
	public String toString()
	{
		return "Span{" + mText + '}';
	}


	@Override
	public Span clone()
	{
		Span span;
		try
		{
			span = (Span)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			span = new Span();
		}
		span.mVerticalAlignment = mVerticalAlignment;
		span.mStyle = mStyle.clone();
		span.mText = mText;
		return span;
	}
}
