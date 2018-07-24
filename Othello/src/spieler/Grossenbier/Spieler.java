package spieler.Grossenbier;

import java.util.ArrayList;
import spieler.*;

public class Spieler implements OthelloSpieler {
	/**
	 * @author Daniel Gro�enbach, Lennart Bierwolf
	 */
	String name;
	static int groesse = 8;
	int Rekursionstiefe = 6;
	private Farbe[][] globalesBrett = new Farbe[groesse][groesse];	//das Brett, auf dem gespielt wird
	private int[][] Values = { 	{30,-4,12,10,10,12,-4,30},
								{-4,-8,-4, 2, 2,-4,-8,-4},
								{12,-4, 2, 2, 2, 2,-4,12},
								{10, 2, 2,-4,-4, 2, 2,10},
								{10, 2, 2,-4,-4, 2, 2,10},
								{12,-4, 2, 2, 2, 2,-4,12},
								{-4,-8,-4, 2, 2,-4,-8,-4},
								{30,-4,12,10,10,12,-4,30}
							 };	// Die von uns gew�hlten Werte f�r jedes Feld
	Farbe ich;	//unsere Farbe
	Farbe gegner;	//die Farbe vom Gegner
	int alpha = -5000;
	int beta = 5000;
	
	/**
	 * Konstruktor f�r bestimmte Rechentiefe
	 * @param Halbschritte	Rechentiefe in Halbschritten
	 */
	public Spieler(int Halbschritte) {
		this.Rekursionstiefe = Halbschritte;
		this.name = "Gro�enbier(" + Halbschritte + ")";
	};
	
	/**
	 * Standartkonstruktor mit Rechentiefe 6
	 */
	public Spieler() {
		this.Rekursionstiefe = 6;
		this.name = "Gro�enbier(" + 6 + ")";
	};

	/**
	 * Erstellt ein Brett in der Startposition
	 */
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
	
	/**
	 * Aktualisiert das Spielbrett mit dem angegebenen Zug
	 * @param brett	Zu aktualisierendes Spielbrett
	 * @param z	Zeile des gelegten Zugs
	 * @param s	Spalte des gelegten Zugs
	 * @param ich Spieler, der den Zug gelegt hat
	 * @param gegner Spieler, der nicht gelegt hat
	 * @return ein aktualisiertes Brett
	 */
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

	/**
	 * �berpr�ft das Spielbrett, ob der �bergebene Zug gespielt werden darf
	 * @param brett	Zu �berpr�fendes Spielbrett
	 * @param z	Zeile des Zugs
	 * @param s	Spalte des Zugs
	 * @param ich Spieler, der den Zug legen m�chte
	 * @param gegner Spieler, der nicht legen m�chte
	 * @return	True f�r erlaubt, false sonst
	 */
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

	/**
	 * Berechnet, abh�ngig ob der Spieler Maximierer oder Minimierer ist, welcher Wert weiter nach oben im Baum gegeben wird
	 * @param brett	Das Brett, auf dem gespielt wird
	 * @param ich Spieler am Zug
	 * @param gegner Spielr nicht am Zug
	 * @param Tiefe	Aktuelle Suchtiefe im Baum in Halbschritten
	 * @param alpha	Alpha Wert f�r das alpha-beta-Pruning
	 * @param beta	Beta Wert f�r das alpha-beta-Pruning
	 * @param maximierer Ist der aktuelle Spieler Maximierer(true) oder Minimierer(false)
	 * @return den Wert des Pfades f�r das MinMax Verfahren
	 */
	public int berechneN�chsterZug(Farbe[][] brett, Farbe ich, Farbe gegner, int Tiefe, int alpha, int beta, boolean maximierer) {
		int value = 1000;
		if (maximierer) {	//ist der aktuelle Knote ein Maximierer, wird der Wert mit -1000 initialisiert, sonst mit 1000
			value *= -1;
		}
		if (Tiefe == Rekursionstiefe) {	//bei maximaler Rekursionstiefe, wird direkt der Wert zur�ckgegeben
			return getBrettValue(brett);
		} else {
			ArrayList<Zug> ZugListe = getZugListe(brett, ich, gegner);
			if (ZugListe.isEmpty()) {	//ist keine Zugm�glichkeit vorhanden, muss gepasst werden
				Farbe[][] brettCopy = new Farbe[groesse][groesse];
				for (int i = 0; i < groesse; i++) {
					for (int j = 0; j < groesse; j++) {
						brettCopy[i][j] = brett[i][j];	//Kopieren des Brett zum weiterarbeiten
					}
				}
				int temp = berechneN�chsterZug(brettCopy, gegner, ich, (Tiefe + 1), alpha, beta, !maximierer);	//da gepasst wird, muss das Brett nicht ver�ndert werden, bevor tiefer gerechnet wird

				return temp; //es gibt keine M�glichkeit zu w�hlen, also wird der Wert direkt weiter nach oben gereicht
			} else {
				for (Zug y : ZugListe) {
					Farbe[][] brettCopy = new Farbe[groesse][groesse];
					for (int i = 0; i < groesse; i++) {
						for (int j = 0; j < groesse; j++) {
							brettCopy[i][j] = brett[i][j];	//Kopieren des Brett zum weiterarbeiten
						}
					}
					int temp = berechneN�chsterZug(
							aktualisiereBrett(brettCopy, y.getZeile(), y.getSpalte(), ich, gegner), gegner, ich,
							(Tiefe + 1), alpha, beta, !maximierer);	//f�r jeden m�glichen Zug wird mit dem MinMax einen Wert berechnet
					if (maximierer) {	//Der aktuelle Wert mit dem Besten verglichen und im Falle einer Verbesserung(abh�ngig davon, ob der aktuelle Knoten ein Maximierer oder Minimierer ist) �berschrieben, au�erdem wird der alpha oder beta Wert im Falle einer Verbesserung(ebenfalls abh�ngig vom Maximierer/Minimierer) �berschrieben
						if (temp > value) {
							value = temp;
						}
						if(value > alpha) {
							alpha = value;
						}
						if(alpha >= beta) {	//Abbruchbedingung f�r das Alpha-Beta-Pruning, mit R�ckgabe eines Extremwerts, damit der Teil des Baum vom n�chsten Knoten dadr�ber m�glichst Ignoriert wird
							return 5000;
						}
					} else {	//Der aktuelle Wert mit dem Besten verglichen und im Falle einer Verbesserung (abh�ngig davon, ob der aktuelle Knoten ein Maximierer oder Minimierer ist) �berschrieben, au�erdem wird der alpha oder beta Wert im Falle einer Verbesserung(ebenfalls abh�ngig vom Maximierer/Minimierer) �berschrieben
						if (temp < value) {
							value = temp;
						}
						if(value < beta) {
							beta = value;
						}
						if(alpha >= beta) {	//Abbruchbedingung f�r das Alpha-Beta-Pruning, mit R�ckgabe eiens ExtremWerts, damit der Teil des Baum vom n�chsten Knoten dadr�ber m�glichst Ignoriert wird
							return -5000;
						}
					}
				}
			}
			return value;
		}

	}

	/**
	 * Berechnet den Wert des gegebenen Bretts, abh�ngig von der Werte-Matrix
	 * @param brett Das zu bewertende Brett
	 * @return Der Wert des Brett, wobei jedes belegte Feld nach der Werte-Matrix bewertet wird. Vom Spieler belelegte Felder werden aufaddiert und davon werden die vom Gegner belegten Felder subtrahiert
	 */
	public int getBrettValue(Farbe[][] brett) {
		int value = 0;
		for (int i = 0; i < groesse; i++) {
				for (int j = 0; j < groesse; j++) {
					if (brett[i][j] == ich) {
						value += Values[i][j];	//Werte mit unseren Steinen werden addiert
					}
					if (brett[i][j] == gegner) {
						value -= Values[i][j];	//Werte des Gegners werden hingegen subtrahiert
					}
				}
			}
		return value;
	}

	/**
	 * Berechnet alle m�glichen Z�ge auf dem �bergebenen Brett
	 * @param brett Das zu pr�fende Brett
	 * @param ich der Spieler, der ziehen m�chte
	 * @param gegner der Spieler, der nicht ziehen m�chte
	 * @return	Eine Liste aller m�glicher Z�ge
	 */
	public ArrayList<Zug> getZugListe(Farbe[][] brett, Farbe ich, Farbe gegner) {
		ArrayList<Zug> ZugListe = new ArrayList<Zug>();
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if (legalerZug(brett, i, j, ich, gegner)) {
					ZugListe.add(new Zug(i, j)); 
				}
			}
		}
		return ZugListe;
	}

	@Override
	public Zug berechneZug(Zug vorherigerZug, long zeitWeiss, long zeitSchwarz) throws ZugException {
		
		
		if (vorherigerZug != null && vorherigerZug.getPassen() != true)
			globalesBrett = aktualisiereBrett(globalesBrett, vorherigerZug.getZeile(), vorherigerZug.getSpalte(),
					gegner, ich);	//aktualisiert das Brett, wenn es nicht der erste Zug war und nicht gepasst wurde
		
		alpha = -5000;	//Alpha- und Betawerte werden neu initialisiert
		beta = 5000;
		
		ArrayList<Zug> ZugListe = getZugListe(globalesBrett, ich, gegner);

		if (ZugListe.isEmpty()) {	//sind keine Z�ge m�glich, kann direkt Passen zur�ckgegeben werden
			Zug Pass = new Zug(-1, -1);
			Pass.setPassen();
			return Pass;
		}

		if (ZugListe.size() == 1) {	//gibt es nur eine Zugm�glichkeit, wird sie direkt zur�ckgegeben
			globalesBrett = aktualisiereBrett(globalesBrett, ZugListe.get(0).getZeile(), ZugListe.get(0).getSpalte(),
					ich, gegner);
			return ZugListe.get(0);
		}

		Farbe[][] brettCopy = new Farbe[groesse][groesse];
		for (int i = 0; i < groesse; i++) {
			for (int j = 0; j < groesse; j++) {
				brettCopy[i][j] = globalesBrett[i][j];	//kopieren des Bretts, damit das Ursprungsbrett nicht ver�ndert wird
			}
		}
		Zug Zug = new Zug(5, 5);
		int value = -1000;		
		for(Zug z : ZugListe) {
			int temp = berechneN�chsterZug(aktualisiereBrett(brettCopy, z.getZeile(), z.getSpalte(),
					ich , gegner), gegner, ich, 0, alpha, beta, false);	//f�r jeden m�glichen Zug wird mit dem MinMax einen Wert berechnet
			if(temp > value) {	//ist der neue Wert besser als der Aktuelle wird er �berschrieben
				Zug = z;
				value = temp;
			}
		}
		globalesBrett = aktualisiereBrett(globalesBrett, Zug.getZeile(), Zug.getSpalte(), ich, gegner);	//Das Brett wird mit dem berechneten Zug aktualisiert
		return Zug;

	}

	/**
	 * Initialisierung der Farben, sowie erstellen eines neuen Bretts
	 */
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
