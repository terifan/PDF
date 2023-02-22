package org.terifan.pdfwriter;


public class Span
{
	private Style mStyle;
	private String mText;


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
}
