package dbi_projekt;

import java.io.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Create_DB table = new Create_DB();
		
		table.deleteDatabase();
		table.createDatabase();
		table.deleteTables();
		table.createTables();
		
		long start, ende, startwrite, endwrite;
		int n = 1; //Anzahl d. Durchgänge
		System.out.println ("Starting to measure time..");
		start = System.currentTimeMillis();
		
		table.fill(n);

		
		ende = System.currentTimeMillis();
		System.out.println ("Anzahl Durchgänge: " + n);
		System.out.println(("Dauer: " +(ende - start)) + "ms");
	}

}
