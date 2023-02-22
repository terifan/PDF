package org.terifan.pdfwriter;

import java.util.ArrayList;
import java.util.Arrays;


public class Paragraph
{
	private ArrayList<Span> mSpans;


	public Paragraph(Span... aSpans)
	{
		mSpans = new ArrayList<>(Arrays.asList(aSpans));
	}


	public Paragraph(Style aStyle, String aText)
	{
		mSpans = new ArrayList<>();
		mSpans.add(new Span(aStyle, aText));
	}


	public ArrayList<Span> getSpans()
	{
		return mSpans;
	}
}
