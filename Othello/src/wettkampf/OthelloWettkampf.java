package wettkampf;

import java.util.ArrayList;
import java.util.List;

import spieler.OthelloSpieler;

public class OthelloWettkampf {
	public static void main(String args[]) {
		List<OthelloSpieler> spieler = new ArrayList<OthelloSpieler>();
		spieler.add(new spieler.Referenzspieler());
		spieler.add(new spieler.Referenzspieler());
		
		new rahmen.OthelloArena(150, spieler, false);
	}
}
