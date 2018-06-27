package spieler.dale;

import java.util.ArrayList;
import java.util.Collections;

import spieler.*;

public class Spieler implements OthelloSpieler {
	String name;
	static int groesse = 8;
	int Rekursionstiefe = 4;
	private Farbe[][] globalesBrett = new Farbe[groesse][groesse];
	private int[][] Values = { {30,-4,12,10,10,12,-4,30},
							   {-4,-8,-4, 2, 2,-4,-8,-4},
							   {12,-4, 2, 2, 2, 2,-4,12},
							   {10, 2, 2,-4,-4, 2, 2,10},
							   {10, 2, 2,-4,-4, 2, 2,10},
							   {12,-4, 2, 2, 2, 2,-4,12},
							   {-4,-8,-4, 2, 2,-4,-8,-4},
							   {30,-4,12,10,10,12,-4,30} 
							 };
	Farbe ich;
	Farbe gegner;
	
	public Spieler(int Halbschritte) {
		this.Rekursionstiefe = Halbschritte;
		this.name = "Groﬂenbier(" + Halbschritte + ")";
	};
	
	public Spieler() {
		this.Rekursionstiefe = 6;
		this.name = "Groﬂenbier(" + 6 + ")";
	};

	private void neuesBrett() {
		for (int zeile = 0; zeile < groesse; zeile++) {
			for (int spalte = 0; spalte < groesse; spalte++)
				globalesBrett[zeile][spalte] = Farbe.LEER;
		}
		globalesBrett[3][3] = Farbe.WEISS;
		globalesBrett[3][4] = Farbe.SCHWARZ;
		globalesBrett[4][3] = Farbe.SCHWARZ;
		globalesBrett[4][4] = Farbe.WEISS;
	}

	public Farbe[][] aktualisiereBrett(Farbe[][] brett, int z, int s, Farbe ich, Farbe gegner) {
		if (z == -1 || s == -1) {
			return brett;
		}
		if (brett[z][s] == Farbe.LEER) {
			// rechts
			for (int i = 1; s + i <= 7;) {
				if (brett[z][s + i] == gegner) {
					i++;
				} else if (brett[z][s + i] == Farbe.LEER) {
					break;
				} else if (brett[z][s + i] == ich && i == 1) {
					break;
				} else if (brett[z][s + i] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z][s + j] = ich;
					}
					break;
				}
			}

			// rechts unten
			for (int i = 1; s + i <= 7 && z + i <= 7;) {
				if (brett[z + i][s + i] == gegner) {
					i++;
				} else if (brett[z + i][s + i] == Farbe.LEER) {
					break;
				} else if (brett[z + i][s + i] == ich && i == 1) {
					break;
				} else if (brett[z + i][s + i] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z + j][s + j] = ich;
					}
					break;
				}
			}

			// unten
			for (int i = 1; z + i <= 7;) {
				if (brett[z + i][s] == gegner) {
					i++;
				} else if (brett[z + i][s] == Farbe.LEER) {
					break;
				} else if (brett[z + i][s] == ich && i == 1) {
					break;
				} else if (brett[z + i][s] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z + j][s] = ich;
					}
					break;
				}
			}

			// links unten
			for (int i = 1; z + i <= 7 && s - i >= 0;) {
				if (brett[z + i][s - i] == gegner) {
					i++;
				} else if (brett[z + i][s - i] == Farbe.LEER) {
					break;
				} else if (brett[z + i][s - i] == ich && i == 1) {
					break;
				} else if (brett[z + i][s - i] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z + j][s - j] = ich;
					}
					break;
				}
			}

			// links
			for (int i = 1; s - i >= 0;) {
				if (brett[z][s - i] == gegner) {
					i++;
				} else if (brett[z][s - i] == Farbe.LEER) {
					break;
				} else if (brett[z][s - i] == ich && i == 1) {
					break;
				} else if (brett[z][s - i] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z][s - j] = ich;
					}
					break;
				}
			}

			// links oben
			for (int i = 1; z - i >= 0 && s - i >= 0;) {
				if (brett[z - i][s - i] == gegner) {
					i++;
				} else if (brett[z - i][s - i] == Farbe.LEER) {
					break;
				} else if (brett[z - i][s - i] == ich && i == 1) {
					break;
				} else if (brett[z - i][s - i] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z - j][s - j] = ich;
					}
					break;
				}
			}

			// oben
			for (int i = 1; z - i >= 0;) {
				if (brett[z - i][s] == gegner) {
					i++;
				} else if (brett[z - i][s] == Farbe.LEER) {
					break;
				} else if (brett[z - i][s] == ich && i == 1) {
					break;
				} else if (brett[z - i][s] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z - j][s] = ich;
					}
					break;
				}
			}

			// rechts oben
			for (int i = 1; s + i <= 7 && z - i >= 0;) {
				if (brett[z - i][s + i] == gegner) {
					i++;
				} else if (brett[z - i][s + i] == Farbe.LEER) {
					break;
				} else if (brett[z - i][s + i] == ich && i == 1) {
					break;
				} else if (brett[z - i][s + i] == ich && i > 1) {
					for (int j = 0; j <= i; j++) {
						brett[z - j][s + j] = ich;
					}
					break;
				}
			}

		}
		return brett;
	}

	public boolean legalerZug(Farbe[][] brett, int z, int s, Farbe ich, Farbe gegner) {
		if (brett[z][s] == Farbe.LEER) {
			// rechts
			for (int i = 1; s + i <= 7;) {
				if (brett[z][s + i] == gegner) {
					i++;
				} else if (brett[z][s + i] == Farbe.LEER) {
					break;
				} else if (brett[z][s + i] == ich && i == 1) {
					break;
				} else if (brett[z][s + i] == ich && i > 1) {
					return true;
				}
			}

			// rechts unten
			for (int i = 1; s + i <= 7 && z + i <= 7;) {
				if (brett[z + i][s + i] == gegner) {
					i++;
				} else if (brett[z + i][s + i] == Farbe.LEER) {
					break;
				} else if (brett[z + i][s + i] == ich && i == 1) {
					break;
				} else if (brett[z + i][s + i] == ich && i > 1) {
					return true;
				}
			}

			// unten
			for (int i = 1; z + i <= 7;) {
				if (brett[z + i][s] == gegner) {
					i++;
				} else if (brett[z + i][s] == Farbe.LEER) {
					break;
				} else if (brett[z + i][s] == ich && i == 1) {
					break;
				} else if (brett[z + i][s] == ich && i > 1) {
					return true;
				}
			}

			// links unten
			for (int i = 1; z + i <= 7 && s - i >= 0;) {
				if (brett[z + i][s - i] == gegner) {
					i++;
				} else if (brett[z + i][s - i] == Farbe.LEER) {
					break;
				} else if (brett[z + i][s - i] == ich && i == 1) {
					break;
				} else if (brett[z + i][s - i] == ich && i > 1) {
					return true;
				}
			}

			// links
			for (int i = 1; s - i >= 0;) {
				if (brett[z][s - i] == gegner) {
					i++;
				} else if (brett[z][s - i] == Farbe.LEER) {
					break;
				} else if (brett[z][s - i] == ich && i == 1) {
					break;
				} else if (brett[z][s - i] == ich && i > 1) {
					return true;
				}
			}

			// links oben
			for (int i = 1; z - i >= 0 && s - i >= 0;) {
				if (brett[z - i][s - i] == gegner) {
					i++;
				} else if (brett[z - i][s - i] == Farbe.LEER) {
					break;
				} else if (brett[z - i][s - i] == ich && i == 1) {
					break;
				} else if (brett[z - i][s - i] == ich && i > 1) {
					return true;
				}
			}

			// oben
			for (int i = 1; z - i >= 0;) {
				if (brett[z - i][s] == gegner) {
					i++;
				} else if (brett[z - i][s] == Farbe.LEER) {
					break;
				} else if (brett[z - i][s] == ich && i == 1) {
					break;
				} else if (brett[z - i][s] == ich && i > 1) {
					return true;
				}
			}

			// rechts oben
			for (int i = 1; s + i <= 7 && z - i >= 0;) {
				if (brett[z - i][s + i] == gegner) {
					i++;
				} else if (brett[z - i][s + i] == Farbe.LEER) {
					break;
				} else if (brett[z - i][s + i] == ich && i == 1) {
					break;
				} else if (brett[z - i][s + i] == ich && i > 1) {
					return true;
				}
			}

		}
		return false;
	}

	public int berechneN‰chsterZug(Farbe[][] brett, Farbe ich, Farbe gegner, int Tiefe) {
		int value = -1000;
		if((Tiefe & 1) == 1) {
			value *= -1;
		}
		ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (legalerZug(brett, i, j, ich, gegner)) {
					ZugListe.add(new Zug(i, j));
				}
			}
		}
//		Collections.shuffle(ZugListe);

		if (Tiefe == Rekursionstiefe) {
			if (ZugListe.isEmpty()) {
				return getBrettValue(brett, ich, gegner);
			} else {
				for(Zug z : ZugListe) {
					Farbe[][] brettCopy = new Farbe[groesse][groesse];
					for (int i = 0; i < groesse; i++) {
						for (int j = 0; j < groesse; j++) {
							brettCopy[i][j] = brett[i][j];
						}
					}
					int temp = getBrettValue(aktualisiereBrett(brettCopy, z.getZeile(), z.getSpalte(), ich, gegner), ich, gegner);
					if((Tiefe & 1) == 0) {
						if( temp > value) {
							value = temp; 
						}
					}else {
						if( temp < value) {
							value = temp; 
						}
					}
				}
				return value;
			}
		} else {
			if (ZugListe.isEmpty()) {
				Farbe[][] brettCopy = new Farbe[groesse][groesse];
				for (int i = 0; i < groesse; i++) {
					for (int j = 0; j < groesse; j++) {
						brettCopy[i][j] = brett[i][j];
					}
				}
				int temp = berechneN‰chsterZug(brettCopy, gegner, ich,(Tiefe + 1));
				
				if((Tiefe & 1) == 0) {
					if( temp > value) {
						value = temp; 
					}
				}else {
					if( temp < value) {
						value = temp; 
					}
				}
			} else {
				for (Zug y : ZugListe) {
					
					Farbe[][] brettCopy = new Farbe[groesse][groesse];
					for (int i = 0; i < groesse; i++) {
						for (int j = 0; j < groesse; j++) {
							brettCopy[i][j] = brett[i][j];
						}
					}
					int temp = berechneN‰chsterZug(aktualisiereBrett(brettCopy, y.getZeile(), y.getSpalte(), ich, gegner), gegner, ich,
							(Tiefe + 1));
					if((Tiefe & 1) == 0) {
						if( temp > value) {
							value = temp; 
						}
					}else {
						if( temp < value) {
							value = temp; 
						}
					}					
				}
			}
			return value;
		}

	}
	
	public int getBrettValue(Farbe [][] brett, Farbe ich, Farbe gegner) {
		int value = 0;
		for(int i = 0; i < groesse; i++) {
			{
				for(int j = 0; j < groesse; j++) {
					if(brett[i][j] == ich) {
						value += Values[i][j];
					}
					if(brett[i][j] == gegner) {
						value -= Values[i][j];
					}
				}
			}
			
		}
		return value;
	}

	@Override
	public Zug berechneZug(Zug vorherigerZug, long zeitWeiss, long zeitSchwarz) throws ZugException {
		
		if (vorherigerZug != null && vorherigerZug.getPassen() != true)
			globalesBrett = aktualisiereBrett(globalesBrett, vorherigerZug.getZeile(), vorherigerZug.getSpalte(),
					gegner, ich);
		
		 ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		 for(int i = 0; i <= 7; i++) {
		 	for(int j = 0; j <= 7; j++) {
		 		if(legalerZug(globalesBrett, i, j, ich, gegner)) {
		 			ZugListe.add(new Zug(i,j));
		 		}
		 	}
		 }
//		 Collections.shuffle(ZugListe);
		 
		 if(ZugListe.isEmpty()) {
			 Zug Pass = new Zug(-1,-1);
			 Pass.setPassen();
			 return Pass;
		 }
		 
		 if(ZugListe.size() == 1) {
				globalesBrett = aktualisiereBrett(globalesBrett, ZugListe.get(0).getZeile(), ZugListe.get(0).getSpalte(), ich, gegner);
			 return ZugListe.get(0);
		 }
		 
		Farbe[][] brettCopy = new Farbe[groesse][groesse];
		for (int i = 0; i < groesse; i++) {
			for (int j = 0; j < groesse; j++) {
				brettCopy[i][j] = globalesBrett[i][j];
			}
		}
		Zug Zug = new Zug(5,5);
		int value = -1000;
		for(Zug z : ZugListe) {
			int temp = berechneN‰chsterZug(aktualisiereBrett(brettCopy, z.getZeile(), z.getSpalte(),
					ich , gegner), gegner, ich, 0);
			if(temp > value) {
				Zug = z;
				value = temp;
			}
		}
		globalesBrett = aktualisiereBrett(globalesBrett, Zug.getZeile(), Zug.getSpalte(), ich, gegner);
		return Zug;

	}
	
	
	@Override
	public void neuesSpiel(Farbe meineFarbe, int bedenkzeitInSekunden) {
		ich = meineFarbe;
		gegner = Farbe.WEISS;
		if (ich == Farbe.WEISS)
			gegner = Farbe.SCHWARZ;
		neuesBrett();
	}

	@Override
	public String meinName() {
		return name;
	}
}
