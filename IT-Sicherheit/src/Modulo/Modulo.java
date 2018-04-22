package Modulo;

public class Modulo 
{

	public static void main(String[] args) 
	{
		// a+b und a*b
		int a = 130;
		int b = 25;
		int n = 8;
		//x^k 
		int x = 5;
		int k = 3;
		int res = 1;
		while(k > 1)
		{
			if((k & 1) == 1) 
			{
			res = res*x;
			}
			x = x*x;
			k = k>>1;
			}
			if(k > 0)
			{
			res = res*x;
			}
		//(a+b) modulo n oder (a*b) modulo n oder x^k modulo n
		// z = a+b oder z = a*b oder z = res
		int z = a+b;
		// i ist Zaehlvariable
		int i = 1;
		if (n == 0)
			return;
		while(z > n)
		{
			n = n<<1;
			i = i<<1;
		}	
		do
		{
			if (z >= n)
			{
				z = z-n;
			}	
			n = n>>>1;
			i = i>>>1;
		}
		while(i!=0);
		System.out.println(z);
	}

}
