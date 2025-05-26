package org.terifan.pdfwriter;



public class Span implements Cloneable
{
	private Style mStyle;
	private String mText;
	private VerticalAlignment mVerticalAlignment;


	private Span()
	{
		mVerticalAlignment = VerticalAlignment.BASELINE;
	}


	public Span(Style aStyle, String aText)
	{
		this();
		mStyle = aStyle;
		mText = aText;
	}


	public String getText()
	{
		return mText;
	}


	public Style getStyle()
	{
		return mStyle;
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
