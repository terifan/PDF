package org.terifan.pdfwriter;

import java.io.IOException;


public interface Content
{
	public void reuseContent();


	public void layout(double aX0, double aX1);


	public double getHeight();


	public double produce(PDFWriter aPDFWriter, Output aOutput, Page aPage, double aY0, double aX0, double aY1, double aX1) throws IOException;


	public boolean isConsumed();


	public double getWidth();
}
