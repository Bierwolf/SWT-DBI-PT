package dbi_projekt;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		long start, ende, startwrite, endwrite; //F�r die Zeitmessung
		int n; //Anzahl d. Durchg�nge
		Boolean remote = false; //'true', falls Remote DB-Connection ge�ffnet werden soll (wird manuell ge�ndert)
		String [] filepaths = null;
		Create_DB table = new Create_DB();
		
		table.deleteDatabase(remote);
		table.createDatabase(remote);
//		table.deleteTables(remote);		//Ist unn�tig, steht nur zur Vollst�ndigkeit
		table.createTables(remote);
		
		/* Geht schneller im Vergleich zu statischem n */
		Scanner scanner = new Scanner (System.in);
		System.out.printf ("Anzahl Durchg�nge? ");
		n = scanner.nextInt();
		scanner.close();
		int[] randomarray = table.createRandoms(n);
		System.out.println("Creating files..");
		startwrite = System.currentTimeMillis();
		
		/* Erstellen der Files */
		try {
			filepaths = table.writeSQLFile(n, randomarray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		endwrite = System.currentTimeMillis();
		
		System.out.println ("Starting to measure time..");
		start = System.currentTimeMillis();
//		System.out.println(filepaths[0]);
//		System.exit(0);
		
		/* Files werden an das DBMS �bergeben und dort ausgelesen und inserted */
		table.execute(filepaths, remote);
//		table.fill(n, remote);
		
		ende = System.currentTimeMillis();
		
		System.out.println ("\nAnzahl Durchg�nge: " + n);
		System.out.println(("Dauer FileCreate: " +(endwrite - startwrite)) + " ms");
		System.out.println(("Dauer Insert: " +(ende - start)) + " ms");
		System.out.println("Gesamtdauer: " +((ende - start) + (endwrite - startwrite)) + " ms");
		
		/* Der nachfolgende Block ist f�r den Fall, dass man die Datenbank sp�ter auf InnoDB-Basis laufen lassen will */
//		start = System.currentTimeMillis();
//		table.updateEngine(remote);
//		ende = System.currentTimeMillis();
//		System.out.println(("Dauer Update: " +(ende - start)) + " ms");
		
		/* Abschlie�end werden die erzeugten Dateien gel�scht, dies bringt bei wiederholten Durchg�ngen einen Vorteil von ~1.2 s. */
		table.deleteFiles(filepaths);
	}

}
