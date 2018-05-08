package Modulo;

public class Modulo 
{

	public static int addiere(int a, int b, int pi)
	{
		if (pi-a > b)
			return a+b;
		int x = pi-a; 
		int y = b-x;
		while (y > pi)
		{
			y = y-pi;
		}
		return y;
	}
	
	public static int multipliziere(int a, int b, int pi)
	{
		if (b == 0 || a == 0)
			return 0;
		int ret = 1;
		while (b > 0)
		{
			if ((b&1) == 1)
				ret = ret*a;
			b = b>>1;
			a = addiere(a, a, pi);
			while (ret > pi)
				ret = ret-pi;
		}
		return ret;
	}

	public static int potenziere(int x, int k, int pi)
	{
		if (k == 0)
			return 1;
		if (k == 1)
			return x;
		while (k > 1)
		{
			k = k>>1;
			x = multipliziere(x, x, pi);
		}
		return x;
	}
	
	public static void main(String[] args) 
	{
		int n = 7;
		//a+b und a*b
		int a = 3;
		int b = 5;
		//x^k 
		int x = 11;
		int k = 2;
		
		int plus = addiere(a, b, n);
		System.out.println("Rest Plus: " + plus);
		int mal = multipliziere(a, b, n);
		System.out.println("Rest Mal: " + mal);
		int potenz = potenziere(x, k, n);
		System.out.println("Rest Potenz: " + potenz);
		
//****************************************************************************************************************************************************************		
		
//		int res = 1;
//		while(k > 1)
//		{
//			if((k & 1) == 1) 
//			{
//			res = res*x;
//			}
//			x = x*x;
//			k = k>>1;
//			}
//			if(k > 0)
//			{
//			res = res*x;
//			}
//		//(a+b) modulo n oder (a*b) modulo n oder x^k modulo n
//		// z = a+b oder z = a*b oder z = res
//		int z = a+b;
//		// i ist Zaehlvariable
//		int i = 1;
//		if (n == 0)
//			return;
//		while(z > n)
//		{
//			n = n<<1;
//			i = i<<1;
//		}	
//		do
//		{
//			if (z >= n)
//			{
//				z = z-n;
//			}	
//			n = n>>>1;
//			i = i>>>1;
//		}
//		while(i!=0);
//		System.out.println(z);
	}

}
