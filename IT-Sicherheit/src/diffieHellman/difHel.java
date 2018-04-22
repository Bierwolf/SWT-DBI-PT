package diffieHellman;

public class difHel 
{
	
	public static int createKey(int base, int expo)
	{
		int k = 1;
		while(expo > 1)
		{
			if((expo & 1) == 1) 
			{
			k = k*base;
			}
			base = base*base;
			expo = expo>>1;
			}
			if(expo > 0)
			{
			k = k*base;
			}
		return k;
	}
	
	public static int createSecureKey(int key, int exp)
	{
		int k = 1;
		while(exp > 1)
		{
			if((exp & 1) == 1) 
			{
			k = k*key;
			}
			key = key*key;
			exp = exp>>1;
			}
			if(exp > 0)
			{
			k = k*key;
			}
		return k;
	}

	public static void main(String[] args) 
	{
		//a ist prim aufgrund der Gruppe pi
		int a = 3;
		int x = 1;
		int y = 4;
		int keyA = createKey(a,x);
		int keyB = createKey(a,y);
		System.out.println(keyA);
		System.out.println(keyB);
		int skeyA = createSecureKey(keyA,y);
		int skeyB = createSecureKey(keyB,x);
		if (skeyA == skeyB)
			System.out.println("Keys: " + skeyA + " und " + skeyB);
	}

}
