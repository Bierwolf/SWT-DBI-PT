package spieler.dale;

import spieler.*;

public class Spieler implements OthelloSpieler
{
//	Brett brett;
	Farbe ich;
	Farbe gegner;
	
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
		 
//		 brett = new Brett();
//		 brett.initialisiereBrett();
	 }
	 
	 @Override
	 public String meinName()
	 {
	 return "dale";
	 }
}
