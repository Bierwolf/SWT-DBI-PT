package dbi_projekt;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Create_DB table = new Create_DB();
		
		table.createDatabase();
		table.createTables();
		
		long start, ende;
		int n = 0;
		start = System.currentTimeMillis();
		
		
		
		ende = System.currentTimeMillis();
		System.out.println (n + ": " + (ende - start) + "ms");
		table.deleteTables();
	}

}
