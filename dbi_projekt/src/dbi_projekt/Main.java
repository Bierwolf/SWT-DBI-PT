package dbi_projekt;

import java.io.*;

public class Main {

	public static void main(String[] args) {
		
		long start, ende, startwrite, endwrite;
		int n = 20; //Anzahl d. Durchgänge

		Boolean remote = false; //true falls remote DB-Connection
		String [] filepaths = null;
		Create_DB table = new Create_DB();
		
		table.deleteDatabase(remote);
		table.createDatabase(remote);
		table.deleteTables(remote);
		table.createTables(remote);
		
		System.out.println("Creating accounts.txt..");
		startwrite = System.currentTimeMillis();
		
		try {
			filepaths = table.writeSQLFile(n);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		endwrite = System.currentTimeMillis();
		System.out.println(("Dauer Filecreate: " +(endwrite - startwrite)) + " ms");
		
		System.out.println ("Starting to measure time..");
		start = System.currentTimeMillis();
		/*System.out.println(filepaths[0]);
		System.exit(0);*/

		table.execute(filepaths, remote);
		//table.fill(n, remote);
		
		ende = System.currentTimeMillis();
		System.out.println ("Anzahl Durchgänge: " + n);
		System.out.println(("Dauer Insert: " +(ende - start)) + " ms");
		System.out.println("Gesamtdauer: " +((ende - start) + (endwrite - startwrite)) + " ms");
		/*start = System.currentTimeMillis();
		table.updateEngine(remote);
		ende = System.currentTimeMillis();
		System.out.println(("Dauer Update: " +(ende - start)) + " ms");*/
		table.deletefiles(filepaths);
	}

}
