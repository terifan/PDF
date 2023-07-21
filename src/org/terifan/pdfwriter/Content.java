package org.terifan.pdfwriter;

import java.io.IOException;


public interface Content
{
	void reuseContent();


	void layout(double aX0, double aX1);


	double getHeight();


	double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aY0, double aX0, double aY1, double aX1) throws IOException;


	boolean isConsumed();


	double getWidth();
}
