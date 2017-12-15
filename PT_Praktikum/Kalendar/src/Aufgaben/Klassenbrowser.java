package Aufgaben;

import java.lang.reflect.*;

public class Klassenbrowser 
{
	public static void main(String[] args) 
	{
		try
		{
			String Name = "java.lang.Object";
			
			Class klasse = Class.forName(Name);
			System.out.println(klasse);
			
			System.out.println("{");
			System.out.println("   Superklasse:");
			
			System.out.println("   " + klasse.getSuperclass());
			
			System.out.println("");
			System.out.println("   Konstruktoren:");
			
			for(Constructor konstruktoren: klasse.getDeclaredConstructors())
			{
				System.out.println("   " + konstruktoren);
			}
			
			System.out.println("");
			System.out.println("   Methoden:");
			
			for(Method methode: klasse.getDeclaredMethods())
			{
				System.out.println("   " + methode);
			}
			
			System.out.println("");
			System.out.println("   Felder:");
			
			for(Field feld: klasse.getDeclaredFields())
			{
				System.out.println("   " + feld);
			}
			
			System.out.println("}");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
