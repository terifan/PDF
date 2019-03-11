package pdf;

import java.util.ArrayList;


public class Paragraph
{
	private ArrayList<Symbol> mText;


	public Paragraph(Style aStyle, String aText)
	{
		mText = new ArrayList<>();

		for (int i = 0; i < aText.length(); i++)
		{
			mText.add(new Symbol(aStyle, aText.charAt(i)));
		}
	}


	public ArrayList<Symbol> getText()
	{
		return mText;
	}
}
