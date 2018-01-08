package verwaltung;

import java.util.*;

import personal.*;

public class Personalverwaltung
{	
	static ArrayList<Mitarbeiter> Verwaltung = new ArrayList<Mitarbeiter>();
	
	public static void main (String[] args)
	{		
		Manager Kevin = new Manager("Kevin", 600, 30);
		Manager Nick = new Manager("Nick", 400, 50);
		Manager Bob = new Manager("Bob", 400, 20);
		Verwaltung.add(Bob);
		Verwaltung.add(Kevin);
		Verwaltung.add(Nick);
		Verwaltung.add(Bob);
		Verwaltung.add(Bob);
		Verwaltung.add(Kevin);
		Verwaltung.add(Bob);
		Verwaltung.add(Kevin);
		Verwaltung.add(Nick);
		Verwaltung.add(Bob);
		Verwaltung.add(new Angestellter("Matze", 200));
		Verwaltung.add(Bob);
		Verwaltung.add(Kevin);
		Verwaltung.add(Nick);
		Verwaltung.add(Kevin);
		Verwaltung.add(Bob);
		Verwaltung.add(Nick);
		Verwaltung.add(Kevin);
		print();
		System.out.println("");
		removedoubles();
		print();
		System.out.println("");
		Collections.sort(Verwaltung);
		print();
		System.out.println("");
		System.out.println(maxGehalt().toString());
		System.out.println(minGehalt().toString());
		System.out.println(MinMaxBonus());
		return;
	}
	
	static public void removedoubles()
	{
 		for(int i = 0; i < Verwaltung.toArray().length; i++)
 		{
 			for(int j = i+1; j < Verwaltung.toArray().length; j++)
 			{
 				if(Verwaltung.get(i).equals(Verwaltung.get(j)))
 				{
 					Verwaltung.remove(j);
 					j--; 					
 				}
 			}
 		}
	}

	static public void print() {
		for(Mitarbeiter m : Verwaltung)
		{
			System.out.println(m.toString());
		}
	}
	
	static public Mitarbeiter maxGehalt()
	{
		int index = 0;
		int gehalt = 0;
		for(Mitarbeiter m : Verwaltung)
		{
			if(m.getGehalt() > gehalt)
			{
				gehalt = m.getGehalt();
				index = Verwaltung.indexOf(m);
			}
		}
		
		return Verwaltung.get(index);
	}
	
	static public Mitarbeiter minGehalt()
	{
		int index = 0;
		Integer gehalt = null;
		for(Mitarbeiter m : Verwaltung)
		{
			if(gehalt == null)
			{
				gehalt = m.getGehalt();
				index = Verwaltung.indexOf(m);
				
			}
			if(m.getGehalt() < gehalt)
			{
				gehalt = m.getGehalt();
				index = Verwaltung.indexOf(m);
			}
		}
		
		return Verwaltung.get(index);
	}
	
	static public Pair<Manager> MinMaxBonus()
	{
		int maxindex = 0;
		Integer maxbonus = null;
		for(Mitarbeiter m : Verwaltung)
		{
			if(m instanceof Manager)
			{
				if(maxbonus == null)
				{
					maxbonus = m.getGehalt();
					maxindex = Verwaltung.indexOf(m);
					
				}
				if(((Manager) m).getBonus() < maxbonus)
				{
					maxbonus = m.getGehalt();
					maxindex = Verwaltung.indexOf(m);
				}
			}
		}
		
		int minindex = 0;
		Integer minbonus = null;
		for(Mitarbeiter m : Verwaltung)
		{
			if(m instanceof Manager)
			{
				if(minbonus == null)
				{
					minbonus = m.getGehalt();
					minindex = Verwaltung.indexOf(m);
					
				}
				if(((Manager) m).getBonus() > maxbonus)
				{
					minbonus = m.getGehalt();
					minindex = Verwaltung.indexOf(m);
				}
			}
			
		}
		if(minbonus == null || maxbonus == null) {return new Pair<Manager>();}
		return new Pair<Manager>((Manager) Verwaltung.get(minindex), (Manager) Verwaltung.get(maxindex));
	}
}
