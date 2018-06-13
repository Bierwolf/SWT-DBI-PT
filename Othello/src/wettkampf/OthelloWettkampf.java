package wettkampf;


import java.util.ArrayList;
import java.util.List;

import spieler.OthelloSpieler;

public class OthelloWettkampf {
	public static void main(String args[]) {
		List<OthelloSpieler> spieler = new ArrayList<OthelloSpieler>();
		spieler.add(new spieler.dale.Spieler());
		spieler.add(new spieler.Referenzspieler(2));
		
		
		new rahmen.OthelloArena(150, spieler, true);
	}
}
