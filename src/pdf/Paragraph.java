package pdf;

import java.util.ArrayList;


public class Paragraph
{
	private ArrayList<Span> mSpans;


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
