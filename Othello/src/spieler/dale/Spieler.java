package spieler.dale;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import spieler.*;

public class Spieler implements OthelloSpieler
{
	public Spieler() {};
	
	static int groesse = 8;
	int Rekursionstiefe = 4;
	private Farbe [][] brett = new Farbe [groesse][groesse];
	private int[][] Values = { 	{30,-4,12,10,10,12,-4,30},
								{-4,-8,-4,2,2,-4,-8,-4},
								{12,-4,2,2,2,2,-4,12},
								{10,2,2,-4,-4,2,2,10},
								{10,2,2,-4,-4,2,2,10},
								{12,-4,2,2,2,2,-4,12},
								{-4,-8,-4,2,2,-4,-8,-4},
								{30,-4,12,10,10,12,-4,30}			
								};
	private int PassValue = -6;
	Farbe ich;
	Farbe gegner;
	
	private void neuesBrett()
	  {
	    for (int zeile = 0; zeile < groesse; zeile++)
	    {
	      for (int spalte = 0; spalte < groesse; spalte++)
	        brett[zeile][spalte] = Farbe.LEER;
	    }
	    brett[3][3] = Farbe.WEISS;
	    brett[3][4] = Farbe.SCHWARZ;
	    brett[4][3] = Farbe.SCHWARZ;
	    brett[4][4] = Farbe.WEISS;
	  }
	
	@Override
	 public Zug berechneZug(Zug vorherigerZug, long zeitWeiss,
	 long zeitSchwarz)
	 throws ZugException
	 {
		ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		if(vorherigerZug != null && vorherigerZug.getPassen() != true)
			brett = aktualisiereBrett(brett, vorherigerZug.getZeile(), vorherigerZug.getSpalte(), gegner, ich);
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(legalerZug(brett, i, j, ich, gegner)) {
					ZugListe.add(new Zug(i,j));					
					
				}
			}
			
		}
		if(ZugListe.isEmpty()) {
			Zug Pass = new Zug(-1,-1);
			Pass.setPassen();
			return Pass;
		}
		//Zug Zug = ZugListe.get(ThreadLocalRandom.current().nextInt(0, ZugListe.size()));
		Zug Zug = new Zug(-1,-1);
		for(Zug z : ZugListe) {
			if(Zug.getZeile() == -1)
			{
				Zug = z;
			}else if(getValue(z) > getValue(Zug)) {
				Zug = z;
			}
			
		}

		//ArrayList<Zug> besterPfad= berechneN�chsterZug(brett, ich, gegner, 0, new ArrayList<Zug>());
		brett = aktualisiereBrett(brett, Zug.getZeile(),Zug.getSpalte(), ich, gegner);
		//return besterPfad.get(besterPfad.size()-1);
		return Zug;
		
	 }
	
	public ArrayList<Zug> berechneN�chsterZug(Farbe[][] brett, Farbe ich, Farbe gegner, int Tiefe, ArrayList<Zug> BesterPfad) {
		ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(legalerZug(brett, i, j, ich, gegner)) {
					ZugListe.add(new Zug(i,j));					
					
				}
			}
			
		}
		
		if(ZugListe.isEmpty()) {
			Zug Pass = new Zug(-1,-1);
			Pass.setPassen();
			BesterPfad.add(Pass);
			return BesterPfad;
		}
				
		if(Tiefe == Rekursionstiefe) {
			// TODO Passen ben�tigt noch dringende einbindung in die vorrausrechnung
			
			Zug Zug = new Zug(-5,-5);
			for(Zug z : ZugListe) {
				if(Zug.getZeile() == -5)
				{
					Zug = z;
				}else if(getValue(z) > getValue(Zug)) {
					Zug = z;
				}
			}
			BesterPfad.add(Zug);
			return BesterPfad;
		}else {
			Zug besterZug = new Zug(-1,-1);
			int Value = -1000;
			for(Zug y : ZugListe) {
				int tempValue=0;
				int flipper = 1;
				if((Tiefe&1) == 1) {
					flipper = -1;
				}
				for(Zug z : berechneN�chsterZug(aktualisiereBrett(brett, y.getZeile(), y.getSpalte(), ich, gegner), gegner, ich, Tiefe++, BesterPfad)) {
					tempValue += getValue(z)*flipper;
					flipper *= -1;
				}
				if(tempValue > Value) {
					besterZug = y;
					Value = tempValue;
				}
			}
			BesterPfad.add(besterZug);
			return BesterPfad;
		}
		
	}
	
	public int getValue(Zug z) {
		if(z.getPassen() == true) {
			return PassValue;
		}else {
			return Values[z.getZeile()][z.getSpalte()];
		}
	}

	
	 @Override
	 public void neuesSpiel(Farbe meineFarbe,
	 int bedenkzeitInSekunden)
	 {
		 ich = meineFarbe;
		 gegner = Farbe.WEISS;
		 if (ich == Farbe.WEISS)
			 gegner = Farbe.SCHWARZ;
		 neuesBrett();
	 }

	 public Farbe[][] aktualisiereBrett(Farbe[][] brett, int z, int s, Farbe ich, Farbe gegner) {
		 if(brett[z][s] == Farbe.LEER) {
			 //rechts
			 for(int i = 1; s+i <= 7;) {
				 if(brett[z][s+i] == gegner) {
					 i++;
				 }else if(brett[z][s+i] == Farbe.LEER) {
					 break;
				 }else if(brett[z][s+i] == ich && i == 1) {
					 break;
				 }else if(brett[z][s+i] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z][s+j] = ich;
					 }
					 break;
				 }
			 }
			 
			 //rechts unten
			 for(int i = 1; s+i <= 7 && z+i <= 7;) {
				 if(brett[z+i][s+i] == gegner) {
					 i++;
				 }else if(brett[z+i][s+i] == Farbe.LEER) {
					 break;
				 }else if(brett[z+i][s+i] == ich && i == 1) {
					 break;
				 }else if(brett[z+i][s+i] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z+j][s+j] = ich;
					 }
					 break;
				 }
			 }
			 
			 //unten
			 for(int i = 1; z+i <= 7;) {
				 if(brett[z+i][s] == gegner) {
					 i++;
				 }else if(brett[z+i][s] == Farbe.LEER) {
					 break;
				 }else if(brett[z+i][s] == ich && i == 1) {
					 break;
				 }else if(brett[z+i][s] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z+j][s] = ich;
					 }
					 break;
				 }
			 }
			 
			 //links unten
			 for(int i = 1; z+i <= 7 && s-i >= 0;) {
				 if(brett[z+i][s-i] == gegner) {
					 i++;
				 }else if(brett[z+i][s-i] == Farbe.LEER) {
					 break;
				 }else if(brett[z+i][s-i] == ich && i == 1) {
					 break;
				 }else if(brett[z+i][s-i] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z+j][s-j] = ich;
					 }
					 break;
				 }
			 }
			 
			 //links
			 for(int i = 1; s-i >= 0;) {
				 if(brett[z][s-i] == gegner) {
					 i++;
				 }else if(brett[z][s-i] == Farbe.LEER) {
					 break;
				 }else if(brett[z][s-i] == ich && i == 1) {
					 break;
				 }else if(brett[z][s-i] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z][s-j] = ich;
					 }
					 break;
				 }
			 }
			 
			 //links oben
			 for(int i = 1; z-i >= 0 && s-i >= 0;) {
				 if(brett[z-i][s-i] == gegner) {
					 i++;
				 }else if(brett[z-i][s-i] == Farbe.LEER) {
					 break;
				 }else if(brett[z-i][s-i] == ich && i == 1) {
					break;
				 }else if(brett[z-i][s-i] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z-j][s-j] = ich;
					 }
					 break;
				 }
			 }
			 
			 //oben
			 for(int i = 1; z-i >= 0;) {
				 if(brett[z-i][s] == gegner) {
					 i++;
				 }else if(brett[z-i][s] == Farbe.LEER) {
					 break;
				 }else if(brett[z-i][s] == ich && i == 1) {
					 break;
				 }else if(brett[z-i][s] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z-j][s] = ich;
					 }
					 break;
				 }
			 }
			 
			 //rechts oben
			 for(int i = 1; s+i <= 7 && z-i >= 0;) {
				 if(brett[z-i][s+i] == gegner) {
					 i++;
				 }else if(brett[z-i][s+i] == Farbe.LEER) {
					 break;
				 }else if(brett[z-i][s+i] == ich && i == 1) {
					 break;
				 }else if(brett[z-i][s+i] == ich && i > 1) {
					 for(int j = 0; j <= i; j++) {
						 brett[z-j][s+j] = ich;
					 }
					 break;
				 }
			 }
			 
		 }
		 return brett;
	 }
	 
	 public boolean legalerZug(Farbe[][] brett, int z, int s, Farbe ich, Farbe gegner) {
		 if(brett[z][s] == Farbe.LEER) {
			 //rechts
			 for(int i = 1; s+i <= 7;) {
				 if(brett[z][s+i] == gegner) {
					 i++;
				 }else if(brett[z][s+i] == Farbe.LEER) {
					 break;
				 }else if(brett[z][s+i] == ich && i == 1) {
					 break;
				 }else if(brett[z][s+i] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //rechts unten
			 for(int i = 1; s+i <= 7 && z+i <= 7;) {
				 if(brett[z+i][s+i] == gegner) {
					 i++;
				 }else if(brett[z+i][s+i] == Farbe.LEER) {
					 break;
				 }else if(brett[z+i][s+i] == ich && i == 1) {
					 break;
				 }else if(brett[z+i][s+i] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //unten
			 for(int i = 1; z+i <= 7;) {
				 if(brett[z+i][s] == gegner) {
					 i++;
				 }else if(brett[z+i][s] == Farbe.LEER) {
					 break;
				 }else if(brett[z+i][s] == ich && i == 1) {
					 break;
				 }else if(brett[z+i][s] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //links unten
			 for(int i = 1; z+i <= 7 && s-i >= 0;) {
				 if(brett[z+i][s-i] == gegner) {
					 i++;
				 }else if(brett[z+i][s-i] == Farbe.LEER) {
					 break;
				 }else if(brett[z+i][s-i] == ich && i == 1) {
					 break;
				 }else if(brett[z+i][s-i] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //links
			 for(int i = 1; s-i >= 0;) {
				 if(brett[z][s-i] == gegner) {
					 i++;
				 }else if(brett[z][s-i] == Farbe.LEER) {
					 break;
				 }else if(brett[z][s-i] == ich && i == 1) {
					 break;
				 }else if(brett[z][s-i] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //links oben
			 for(int i = 1; z-i >= 0 && s-i >= 0;) {
				 if(brett[z-i][s-i] == gegner) {
					 i++;
				 }else if(brett[z-i][s-i] == Farbe.LEER) {
					 break;
				 }else if(brett[z-i][s-i] == ich && i == 1) {
					break;
				 }else if(brett[z-i][s-i] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //oben
			 for(int i = 1; z-i >= 0;) {
				 if(brett[z-i][s] == gegner) {
					 i++;
				 }else if(brett[z-i][s] == Farbe.LEER) {
					 break;
				 }else if(brett[z-i][s] == ich && i == 1) {
					 break;
				 }else if(brett[z-i][s] == ich && i > 1) {
					 return true;
				 }
			 }
			 
			 //rechts oben
			 for(int i = 1; s+i <= 7 && z-i >= 0;) {
				 if(brett[z-i][s+i] == gegner) {
					 i++;
				 }else if(brett[z-i][s+i] == Farbe.LEER) {
					 break;
				 }else if(brett[z-i][s+i] == ich && i == 1) {
					 break;
				 }else if(brett[z-i][s+i] == ich && i > 1) {
					 return true;
				 }
			 }
			 
		 }
		 return false;
	 }
	 
	 @Override
	 public String meinName()
	 {
	 return "dale";
	 }
}
