package dbi_projekt;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		long start, ende, startwrite, endwrite; //Für die Zeitmessung
		int n; //Anzahl d. Durchgänge
		Boolean remote = false; //'true', falls Remote DB-Connection geöffnet werden soll (wird manuell geändert)
		String [] filepaths = null;
		Create_DB table = new Create_DB();
		
		table.deleteDatabase(remote);
		table.createDatabase(remote);
		table.createTables(remote);
		
		/* Geht schneller im Vergleich zu statischem n */
		Scanner scanner = new Scanner (System.in);
		System.out.printf ("Anzahl Durchgänge? ");
		n = scanner.nextInt();
		scanner.close();
	
		System.out.println ("Starting to measure time..");
		/* Die Zufallszahlen werden erstellt. Sie später in writeSQLFile() einfach nur aus dem Array ausgelesen. */
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

		start = System.currentTimeMillis();
		
		/* Files werden an das DBMS übergeben und dort ausgelesen und inserted */
		table.execute(filepaths, remote);
		
		ende = System.currentTimeMillis();
		
		System.out.println ("\nAnzahl Durchgänge: " + n);
		System.out.println(("Dauer FileCreate: " +(endwrite - startwrite)) + " ms");
		System.out.println(("Dauer Insert: " +(ende - start)) + " ms");
		System.out.println("Gesamtdauer: " +((ende - start) + (endwrite - startwrite)) + " ms");
		
		/* Der nachfolgende Block ist für den Fall, dass man die Datenbank später auf InnoDB-Basis laufen lassen will */
		start = System.currentTimeMillis();
		/* Der nachfolgende Block ist für den Fall, dass man die Datenbank später auf InnoDB-Basis laufen lassen will. */
//		start = System.currentTimeMillis();
//		table.updateEngine(remote);
		ende = System.currentTimeMillis();
		System.out.println(("Dauer Update: " +(ende - start)) + " ms");
		
		/* Abschließend werden die erzeugten Dateien gelöscht, dies bringt bei wiederholten Durchgängen einen Vorteil von ~1.2 s beim Erstellen der Files
		 * und müllt die Platte nicht zu.
		 * */
		table.deleteFiles(filepaths);
	}

}
