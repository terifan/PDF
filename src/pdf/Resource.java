package pdf;

import java.io.IOException;
import java.util.UUID;


public abstract class Resource
{
	private final UUID mUUID;
	private String mIdentity;


	Resource()
	{
		mUUID = UUID.randomUUID();
	}


	UUID getUUID()
	{
		return mUUID;
	}


	void setIdentity(String aIdentity)
	{
		mIdentity = aIdentity;
	}


	public String getIdentity()
	{
		return mIdentity;
	}


	abstract Ref print(PDFWriter aWriter) throws IOException;
}
