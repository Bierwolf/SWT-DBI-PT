package ABIS;

public class SportgeraetFactory extends AbstractFactory {

	@Override
	public Sportgeraet newSportgeraet(String Name) {
		if (Name.equalsIgnoreCase("Fussball")) {
			return new Fussball();
		}
	}
}
