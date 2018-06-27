package test;

import testsuper.*;

public class test extends testsuper
{
	
	public static void main(String[] args) 
	{
		//Math.pow ist die Potenzfunktion (^) in java
		long l=(long)Math.pow(3, 22);
		int i = (int)l;
		System.out.println(l);
		System.out.println(i);
		//meineMethode(); -> unsichtbar, da die methode in einem anderen package liegt
	}

}
