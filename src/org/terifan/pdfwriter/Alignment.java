package org.terifan.pdfwriter;


public enum Alignment
{
	LEFT,
	CENTER,
	RIGHT,
	/**
	 * Add space between words to fill the entire line.
	 */
	JUSTIFY,
	/**
	 * The first span in a paragraph is left aligned while all remaining spans are right aligned. Useful for key/value text.
	 */
	SPLIT
}