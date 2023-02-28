package samples;

import java.io.IOException;
import java.io.OutputStream;


public interface Template<T extends Document>
{
	void generate(OutputStream aOutput, String aLanguage, T aDocument) throws IOException;
}
