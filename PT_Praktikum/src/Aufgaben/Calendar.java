package Aufgaben;

import java.util.GregorianCalendar;

public class Calendar 
{
	   // wahr oder falsch, wenn es ein Schaltjahr ist
    public static boolean Schaltjahr(int Jahr) 
    {
        if  ((Jahr % 4 == 0) && (Jahr % 100 != 0))
        	return true;
        else if  (Jahr % 400 == 0) 
        	return true;
        else
            return false;
    }
    
    public static void main(String[] args)
    {
    	GregorianCalendar kalender = new GregorianCalendar();
    	int Tag = kalender.get(kalender.DAY_OF_MONTH);
        int Monat = kalender.get(kalender.MONTH);    
        int Jahr = kalender.get(kalender.YEAR);
        int Starttag = kalender.getMinimalDaysInFirstWeek();
        
        //Monatsnamen
        String[] Monate = 
        	{                         
                "Januar", "Februar", "Maerz", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"
            };
        
        // Tagesanzahl in den Monaten
        int[] Tage = 
        	{
        		31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
        	};

        // Schaltjahrtest Februar = Monat[1]
        if (Monat == 1 && Schaltjahr(Jahr)) 
        	Tage[Monat] = 29;

        // Kalenderausgabe
        System.out.println(Monate[Monat] + " " + Jahr);
        System.out.println("Mo  Di  Mi  Do  Fr  Sa  So");
        for (int i = 0; i < Starttag; i++)
            System.out.print("    ");
        for (int i = 1; i <= Tage[Monat]; i++) 
        {
        	if (i == Tag)
            	System.out.printf("%2d* ", i);
        	else 
        		System.out.printf("%2d  ", i);
            if (((i + Starttag) % 7 == 0) || (i == Tage[Monat])) 
            	System.out.println();
        }
     
    }

}
