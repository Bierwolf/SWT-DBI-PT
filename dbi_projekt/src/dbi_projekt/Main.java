package dbi_projekt;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		long start, ende, startwrite, endwrite;
		int n = 1; //Anzahl d. Durchgänge
		Boolean remote = false; //true falls remote DB-Connection
		
		Create_DB table = new Create_DB();
		
		table.deleteDatabase(remote);
		table.createDatabase(remote);
		table.deleteTables(remote);
		table.createTables(remote);
		
		System.out.println("Creating accounts.txt..");
		startwrite = System.currentTimeMillis();
		
		try {
			table.writeSQLFile(n);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		endwrite = System.currentTimeMillis();
		System.out.println(("Dauer: " +(endwrite - startwrite)) + " ms");
		
		System.out.println ("Starting to measure time..");
		start = System.currentTimeMillis();
		
		table.fill(n, remote);
		
		ende = System.currentTimeMillis();
		System.out.println ("Anzahl Durchgänge: " + n);
		System.out.println(("Dauer: " +(ende - start)) + " ms");
	}

}
