package org.terifan.pdfwriter;

import java.io.IOException;


class ObjRef implements Value
{
	private final Ref mReference;
	private final Obj mObject;
	private final Offset mFutureOffset;


	public ObjRef(Ref aReference, Obj aObject, Offset aFutureOffset)
	{
		mReference = aReference;
		mObject = aObject;
		mFutureOffset = aFutureOffset;
	}


	@Override
	public void writeTo(Output aOutput) throws IOException
	{
		mFutureOffset.set(aOutput.size());

		aOutput.println(mReference.getRef() + " 0 obj");
		mObject.write(aOutput);
		aOutput.println("");
		aOutput.println("endobj");
	}


	Ref getReference()
	{
		return mReference;
	}
}
