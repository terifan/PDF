package pdf;


public class Ref
{
	protected int mReference;


	public Ref(int aReference)
	{
		mReference = aReference;
	}


	public int getRef()
	{
		return mReference;
	}


	@Override
	public String toString()
	{
		return mReference + " 0 R";
	}
}
