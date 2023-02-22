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


	/**
	 * Do not call this directly, use Font.findGlyphIndex() instead!
	 */
	int findGlyphIndexImpl(int aCharacter);


	double getGlyphWidth(int aSymbol);


	double getGlyphAdvanceWidth(int aSymbol);


	double getGlyphLeftSideBearing(int aSymbol);


	double[] getFontBBox();


	int getUnitsPerEm();
}
