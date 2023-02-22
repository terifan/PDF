package org.terifan.pdfwriter;

import java.io.IOException;


public interface Value
{
	void writeTo(Output aOutput) throws IOException;
}
