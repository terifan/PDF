package org.terifan.pdfwriter;


public class Span
{
	private Style mStyle;
	private String mText;
	private VerticalAlignment mVerticalAlignment;


	public Span(Style aStyle, String aText)
	{
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
		this.mVerticalAlignment = aVerticalAlignment;
		return this;
	}
}
