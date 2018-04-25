package spieler.dale;

import spieler.*;

public class Spieler implements OthelloSpieler
{
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
	 }
	 
	 @Override
	 public String meinName()
	 {
	 return "dale";
	 }
}
