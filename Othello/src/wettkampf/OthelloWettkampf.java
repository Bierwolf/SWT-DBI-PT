package wettkampf;


import java.util.ArrayList;
import java.util.List;

import spieler.OthelloSpieler;

public class OthelloWettkampf {
	public static void main(String args[]) {
		List<OthelloSpieler> spieler = new ArrayList<OthelloSpieler>();
		spieler.add(new spieler.dale.Spieler(5));
		spieler.add(new spieler.dale.Spieler(4));
		spieler.add(new spieler.dale.Spieler(3));
		
		
		new rahmen.OthelloArena(1050, spieler, true);
	}
}
