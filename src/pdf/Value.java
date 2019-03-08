package pdf;

import java.io.IOException;


public interface Value
{
	void writeTo(Output aOutput) throws IOException;
}
