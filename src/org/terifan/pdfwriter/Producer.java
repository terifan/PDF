package org.terifan.pdfwriter;

import java.io.IOException;


public interface Producer
{
	String produce(PDFWriter aPDFWriter, Page aPage) throws IOException;
}
