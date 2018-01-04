package personal;

import java.time.LocalDate;

import verwaltung.Haarfarbig;

public abstract class Mitarbeiter implements Comparable<Mitarbeiter>, Haarfarbig
{
	private
		String Name;
		int Gehalt;
		LocalDate Erstellungsdatum;
		int Personal_ID;
		static int id_counter=1;
		int haarfarbe = 0;
		
	public Mitarbeiter(String Name, int Gehalt)
	{
		this.Name = Name;
		this.Gehalt = Gehalt;
		this.Erstellungsdatum = LocalDate.now();
		this.Personal_ID = id_counter;
		id_counter++;
	}
	
	public void raise(int percentage) {
		Gehalt *= (1+percentage/100);
	}
	
	public int getLohn()
	{
		return Gehalt;
	}

	public boolean equals(Mitarbeiter M)
	{
		if(this.getClass() == M.getClass() && this.Name == M.Name && this.Gehalt == M.Gehalt && this.Erstellungsdatum == M.Erstellungsdatum && this.Personal_ID == M.Personal_ID)
			return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		return (String) (Name + ","+ Gehalt + "," + Erstellungsdatum.toString() + "," + Personal_ID);
	}

	@Override
	public int compareTo(Mitarbeiter a)
	{
		return this.Gehalt - a.getGehalt();
	}
	
	public void setName(String name) {
		Name = name;
	}

	public String getName() {
		return Name;
	}

	public void setGehalt(int gehalt) {
		Gehalt = gehalt;
	}

	public int getGehalt() {
		return Gehalt;
	}

	public LocalDate getErstellungsdatum() {
		return Erstellungsdatum;
	}

	public int getPersonal_ID() {
		return Personal_ID;
	}
	
	@Override
	public void setFarbe(int Farbe) {
		haarfarbe = Farbe;
	}
	
	@Override
	public int getFarbe() {
		return haarfarbe;
	}
	
}
