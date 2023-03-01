package org.terifan.font;


public interface FontFile
{
	String getName();


	double getLineHeight();


	double getLineGap();


	double getAscent();


	double getDescent();


	double getMinLeftSideBearing();


	double getMinRightSideBearing();


	int findGlyphIndex(int aCharacter);


	double getGlyphWidth(int aSymbol);


	double getGlyphAdvanceWidth(int aSymbol);


	double getGlyphLeftSideBearing(int aSymbol);


	double[] getFontBBox();


	int getUnitsPerEm();
}
