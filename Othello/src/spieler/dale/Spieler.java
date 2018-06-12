package spieler.dale;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import spieler.*;

public class Spieler implements OthelloSpieler
{
	public Spieler() {};
	
	static int groesse = 8;
	int Rekursionstiefe = 3;
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
//		ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		if(vorherigerZug != null && vorherigerZug.getPassen() != true)
			brett = aktualisiereBrett(brett, vorherigerZug.getZeile(), vorherigerZug.getSpalte(), gegner, ich);
//		for(int i = 0; i <= 7; i++) {
//			for(int j = 0; j <= 7; j++) {
//				if(legalerZug(brett, i, j, ich, gegner)) {
//					ZugListe.add(new Zug(i,j));					
//					
//				}
//			}
//			
//		}
//		if(ZugListe.isEmpty()) {
//			Zug Pass = new Zug(-1,-1);
//			Pass.setPassen();
//			return Pass;
//		}
//		//Zug Zug = ZugListe.get(ThreadLocalRandom.current().nextInt(0, ZugListe.size()));
//		Zug Zug = new Zug(-1,-1);
//		for(Zug z : ZugListe) {
//			if(Zug.getZeile() == -1)
//			{
//				Zug = z;
//			}else if(getValue(z) > getValue(Zug)) {
//				Zug = z;
//			}
//			
//		}
//		ArrayList<Zug> list = new ArrayList<Zug>() {{
//		    for(int i = 0; i <= Rekursionstiefe; i++) {
//		    	add(new Zug(-5,-5));
//		    }
//		}};
//		ArrayList<Zug> currentlist = new ArrayList<Zug>() {{
//		    for(int i = 0; i <= Rekursionstiefe; i++) {
//		    	add(new Zug(-5,-5));
//		    }
//		}};
		ArrayList<Zug> besterPfad = berechneNächsterZug(brett, ich, gegner, 0, new ArrayList<Zug>(), new ArrayList<Zug>());
		Zug Zug = besterPfad.get(0);
		brett = aktualisiereBrett(brett, Zug.getZeile(),Zug.getSpalte(), ich, gegner);
		//return besterPfad.get(besterPfad.size()-1);
		return Zug;
		
	 }
	
	public ArrayList<Zug> berechneNächsterZug(Farbe[][] brett, Farbe ich, Farbe gegner, int Tiefe, ArrayList<Zug> AktuellerPfad, ArrayList<Zug> BesterPfad) {
		ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		for(int i = 0; i <= 7; i++) {
			for(int j = 0; j <= 7; j++) {
				if(legalerZug(brett, i, j, ich, gegner)) {
					ZugListe.add(new Zug(i,j));					
				}
			}			
		}
				
		if(Tiefe == Rekursionstiefe) {
			if(ZugListe.isEmpty()) {
				Zug Pass = new Zug(-1,-1);
				Pass.setPassen();
				if(AktuellerPfad.size()-1 < Tiefe) {
					AktuellerPfad.add(Pass);
				}else {
					AktuellerPfad.set(Tiefe, Pass);
				}
			}else {
				Zug Zug = new Zug(-5,-5);
				for(Zug z : ZugListe) {
					if(Zug.getZeile() == -5)
					{
						Zug = z;
					}else if(getValue(z) > getValue(Zug)) {
						Zug = z;
					}
				}
				if(AktuellerPfad.size()-1 < Tiefe) {
					AktuellerPfad.add(Zug);
				}else {
					AktuellerPfad.set(Tiefe, Zug);					
				}
			}
			
			if(getZugListeValue(AktuellerPfad) > getZugListeValue(BesterPfad)) {
				BesterPfad.clear();
				BesterPfad.addAll(AktuellerPfad);
			}
			
			
			return BesterPfad;
		}else {
			if(ZugListe.isEmpty()) {
				Zug Pass = new Zug(-1,-1);
				Pass.setPassen();
				if(AktuellerPfad.size()-1 < Tiefe) {
					AktuellerPfad.add(Pass);
				}else {
					AktuellerPfad.set(Tiefe, Pass);					
				}
				BesterPfad = berechneNächsterZug(brett, gegner, ich, (Tiefe+1), AktuellerPfad, BesterPfad);
			}else {
				for(Zug y : ZugListe) {
					if(AktuellerPfad.size()-1 < Tiefe) {
						AktuellerPfad.add(y);
					}else {
						AktuellerPfad.set(Tiefe, y);					
					}
					BesterPfad = berechneNächsterZug(aktualisiereBrett(brett, y.getZeile(), y.getSpalte(), ich, gegner), gegner, ich, (Tiefe+1), AktuellerPfad, BesterPfad);		
				}
			}
			return BesterPfad;
		}
		
	}
	
	public int getZugListeValue(ArrayList<Zug> ZugListe) {
//		if(ZugListe.get(0).getZeile() == -5) {
//			return -1000;
//		}
		if(ZugListe.isEmpty()) {
			return -1000;
		}
		int flipper = 1;
		int Value = 0;
		for(Zug z : ZugListe) {
			Value += (getValue(z) * flipper);
			flipper *= -1;
		}		
		return Value;
	}
	
	public int getValue(Zug z) {
		if(z.getPassen() == true) {
			return PassValue;
		}else if(z.getSpalte() == -5) {
			return -1000;
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
		if(z == -1 || s == -1) {
			return brett;
		}
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
