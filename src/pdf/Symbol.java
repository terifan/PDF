package pdf;


public class Symbol
{
	private Style mStyle;
	private char mCharacter;


	public Symbol(Style aStyle, char aCharacter)
	{
		mStyle = aStyle;
		mCharacter = aCharacter;
	}


	public char getCharacter()
	{
		return mCharacter;
	}


	public Style getStyle()
	{
		return mStyle;
	}


	public boolean isBreakChar()
	{
		return Character.isWhitespace(mCharacter);
	}
}
