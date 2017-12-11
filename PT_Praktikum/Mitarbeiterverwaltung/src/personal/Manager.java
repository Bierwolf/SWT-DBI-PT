package personal;

public class Manager extends Mitarbeiter
{
	private
		int bonus;
	
	public Manager(String Name, int Gehalt, int bonus)
	{
		super(Name, Gehalt);
		this.bonus = bonus;
	}
	
	public Manager(String Name, int Gehalt)
	{
		super(Name, Gehalt);
		this.bonus = 0;
	}
	
	public boolean equals(Manager M)
	{
		if(super.equals(M))
				if(this.bonus == M.bonus) return true;
		return false;
	}
	
	@Override
	public String toString()
	{
		return (super.toString() + "," + bonus);
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	@Override
	public int getLohn()
	{
		return Gehalt+bonus;
	}
	
	

}
