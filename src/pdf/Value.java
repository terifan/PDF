package pdf;

import java.io.IOException;


@FunctionalInterface
public interface Value
{
	void writeTo(Output aOutput) throws IOException;
}
