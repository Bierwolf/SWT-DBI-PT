package Klassenbrowser;

import java.lang.reflect.*;
import java.util.Scanner;

public class Klassenbrowser 
{
        
	public static void main(String[] args) 
	{
		try
		{
			Scanner sc = new Scanner (System.in);
			System.out.println("Name der Klasse (zb. java.lang.String): ");
			String Name = sc.next();
			
			Class<?> klasse = Class.forName(Name);
			System.out.println(klasse);
			
			System.out.println("{");
			System.out.println("   Superklasse:");
			
			System.out.println("   " + klasse.getSuperclass());
			
			System.out.println("");
			System.out.println("   Konstruktoren:");
			
			for(Constructor<?> konstruktoren: klasse.getDeclaredConstructors())
			{
				System.out.println("   " + konstruktoren);
			}
			
			System.out.println("");
			System.out.println("   Methoden:");
			
			for(Method methoden: klasse.getDeclaredMethods())
			{
				System.out.println("   " + methoden);
				System.out.println("   " + methoden.getReturnType());
			}
			
			System.out.println("");
			System.out.println("   Felder:");
			
			for(Field felder: klasse.getDeclaredFields())
			{
				System.out.println("   " + felder);
			}
			
			System.out.println("}");
			sc.close();
		}
		catch(ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

}