package spieler.dale;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import spieler.*;

public class Zufallsspieler implements OthelloSpieler
{
	public Zufallsspieler() {};
	
	static int groesse = 8;
	private Farbe [][] brett = new Farbe [groesse][groesse];
//	private int[][] Values = { 	{30,-4,12,10,10,12,-4,30},
//								{-4,-8,-4, 2, 2,-4,-8,-4},
//								{12,-4, 2, 2, 2, 2,-4,12},
//								{10, 2, 2,-4,-4, 2, 2,10},
//								{10, 2, 2,-4,-4, 2, 2,10},
//								{12,-4, 2, 2, 2, 2,-4,12},
//								{-4,-8,-4, 2, 2,-4,-8,-4},
//								{30,-4,12,10,10,12,-4,30}			
//								};
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
			aktualisiereBrett(vorherigerZug.getZeile(), vorherigerZug.getSpalte(), gegner, ich);
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(legalerZug(i,j)) {
					ZugListe.add(new Zug(i,j));					
					
				}
			}
			
		}
		if(ZugListe.isEmpty()) {
			Zug Pass = new Zug(0,0);
			Pass.setPassen();
			return Pass;
		}
		Zug Zug = ZugListe.get(ThreadLocalRandom.current().nextInt(0, ZugListe.size()));
		aktualisiereBrett(Zug.getZeile(),Zug.getSpalte(), ich, gegner);
		return Zug;
		
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

	 public void aktualisiereBrett(int z, int s, Farbe ich, Farbe gegner) {
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
	 }
	 
	 public boolean legalerZug(int z, int s) {
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
	 return "Zufall";
	 }
}
