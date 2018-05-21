package spieler.dale;

import spieler.*;

public class Spieler implements OthelloSpieler
{
	static int anzahl = 8;
	private Farbe [][] brett = new Farbe [anzahl][anzahl];
	Farbe ich;
	Farbe gegner;
	
	private void neuesBrett()
	  {
	    for (int zeile = 0; zeile < 8; zeile++)
	    {
	      for (int spalte = 0; spalte < 8; spalte++)
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
	 // TODO Eigenen Zug berechnen
	 return null;
	 }
	
	 @Override
	 public void neuesSpiel(Farbe meineFarbe,
	 int bedenkzeitInSekunden)
	 {
	 // TODO Neues Spiel beginnen; beachten Sie die
	 // Anfangsbelegung des Spielbretts!
		 ich = meineFarbe;
		 gegner = Farbe.WEISS;
		 if (ich == Farbe.WEISS)
			 gegner = Farbe.SCHWARZ;
		 neuesBrett();
	 }
	 
	 @Override
	 public String meinName()
	 {
	 return "dale";
	 }
}
