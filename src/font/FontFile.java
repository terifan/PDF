package font;


public interface FontFile
{
	double getLineHeight();


	double getLineGap();


	double getAscent();


	double getDescent();


	double getMinLeftSideBearing();


	double getMinRightSideBearing();


	int findGlyphIndex(int aCharacter);


	double getGlyphWidth(int aSymbol);
}
