package dbi_projekt;

import java.io.*;
import java.sql.*;

public class Create_DB 
{
	static final String USER = "dbi";
	static final String PASS = "dbi_pass";
	static final String CON_URL = "jdbc:mariadb://localhost:3306/";
	static final String CON_URL_DB = "jdbc:mariadb://localhost:3306/benchmark";
	static final String CON_REMOTE = "jdbc:mariadb://192.168.122.43:3306/";
	static final String CON_REMOTE_DB = "jdbc:mariadb://192.168.122.43:3306/benchmark";
	static final String FILE_NAME = "accounts.txt";
	static final String[] filepaths = null; //Dieser ist nur für Exceptions
	
	/**
	 * Erstellt eine Datenbank auf einem durch 'remote' spezifierten DBMS. 
	 * @param remote 'true', falls Remote Connection benutzt werden soll
	 */
	void createDatabase(Boolean remote)
	{	
		Connection conn = null;
		Statement stmt = null;
		
		try {
			System.out.println("Creating DB..");
			if (remote)
				conn = DriverManager.getConnection(CON_REMOTE, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL, USER, PASS);
			
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS benchmark");
			stmt.executeUpdate("SET @@session.unique_checks = 0;");
			stmt.executeUpdate("SET @@session.foreign_key_checks = 0;");
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
				}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * Löscht die vorher mit createDatabase() erstellte Datenbank auf dem durch 'remote' angegebenen DBMS.
	 * @param remote 'true', falls Remote Connection benutzt werden soll
	 */
	void deleteDatabase(Boolean remote)
	{	
		Connection conn = null;
		Statement stmt = null;
		
		try {
			System.out.println("Deleting DB..");
			if (remote) 
				conn = DriverManager.getConnection(CON_REMOTE, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP DATABASE IF EXISTS benchmark");
			
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
				}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * Erstellt die vorgegebenen Tables in dem durch 'remote' angegebenen DBMS.
	 * @param remote 'true', falls Remote Connection benutzt werden soll.
	 */
	void createTables (Boolean remote)
	{		
		 Connection conn = null;
		 Statement stmt = null;		 
		 /* Strings müssen vorher erstellt werden, da Concat in executeUpdate() nicht erlaubt ist. Java Magic, fuck that */
		 String table_branches = "create table IF NOT EXISTS branches" + 
		 		"( branchid int not null," + 
		 		"branchname char(20) not null," + 
		 		"balance int not null," + 
		 		"address char(72) not null," + 
		 		"primary key (branchid) )" +
		 		" ENGINE = MEMORY;"
		 		;
		 
		 String table_accounts ="create table IF NOT EXISTS accounts" + 
				"( accid int not null," + 
		 		"name char(20) not null," + 
		 		"balance int not null," + 
		 		"branchid int not null," + 
		 		"address char(68) not null," + 
		 		"primary key (accid)," + 
		 		"foreign key (branchid) references branches(branchid) )" +
		 		" ENGINE = MEMORY ;"
				 ;
		 
		 String table_tellers = "create table IF NOT EXISTS tellers" + 
				" ( tellerid int not null," + 
		 		"tellername char(20) not null," + 
		 		"balance int not null," + 
		 		"branchid int not null," + 
		 		"address char(68) not null," + 
		 		"primary key (tellerid)," + 
		 		"foreign key (branchid) references branches(branchid) )" +
		 		" ENGINE = MEMORY;"
		 		;
		 		
		 	String table_history = "create table IF NOT EXISTS history" + 
		 		"( accid int not null," + 
		 		"tellerid int not null," + 
		 		"delta int not null," + 
		 		"branchid int not null," + 
		 		"accbalance int not null," + 
		 		"cmmnt char(30) not null," + 
		 		"foreign key (accid) references accounts(accid) ," + 
		 		"foreign key (tellerid) references tellers(tellerid)," + 
		 		"foreign key (branchid) references branches(branchid))" +
			 	" ENGINE = MEMORY;"
				 ;
		
		 try {
			System.out.println("Creating tables..");
			if (remote)
				conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate (table_branches);
			stmt.executeUpdate (table_accounts);
			stmt.executeUpdate (table_tellers);
			stmt.executeUpdate (table_history);
			conn.commit();
			conn.close();
			
			
		} catch (SQLException e) {
			System.out.println("Connection failed! Could not create tables!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
		}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
		
	/**
	 * Löscht alle Tables der Test-Datenbank, sofern sie vorhanden sind.
	 * @param remote 'true', falls Remote Connection benutzt werden soll
	 */
	void deleteTables(Boolean remote)
	{
		Connection conn = null;
		 Statement stmt = null;
		 
		String drop_branches = "DROP TABLE IF EXISTS branches;";
		String drop_tellers = "DROP TABLE IF EXISTS tellers;";
		String drop_accounts = "DROP TABLE IF EXISTS accounts;";
		String drop_history = "DROP TABLE IF EXISTS history;";
		
		try {
			System.out.println("Deleting tables..");
			if (remote)
				conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate (drop_history);
			stmt.executeUpdate (drop_tellers);
			stmt.executeUpdate (drop_accounts);
			stmt.executeUpdate (drop_branches); 
			
			conn.commit();
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("Connection failed! Could not delete tables.");
			e.printStackTrace();
			}
		finally {
			try	{
				if (stmt != null)
					conn.close();
		}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * Übergibt die .txt-Dateien an das DBMS, wo diese intern verarbeitet werden.
	 * @param filepaths Enthält die absoluten Pfade der erstellten .txt-Dateien mit den Daten.
	 * @param remote 'true', falls Remote Connection benutzt werden soll.
	 */
	void execute(String[] filepaths, Boolean remote) 
	{
		Connection conn = null;
		Statement stmt = null;
		
		try {
			System.out.println("Loading files into DB..");
			if (remote) 
				conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			String insertbranches = ("load data local infile '" + filepaths[0]  +"'"
					+ " into table branches"
					+ " fields terminated by ','"
					+ " lines terminated by ';';");
			String insertaccounts = ("load data local infile '"+ filepaths[1] +"'"
					+ " into table accounts"
					+ " fields terminated by ','"
					+ " lines terminated by ';';");
			String inserttellers = ("load data local infile '"+ filepaths[2] +"'"
					+ " into table tellers"
					+ " fields terminated by ','"
					+ " lines terminated by ';';");
			stmt.executeUpdate(insertbranches);
			conn.commit();
			stmt.executeUpdate(insertaccounts);
			conn.commit();
			stmt.executeUpdate(inserttellers);
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("Connection failed! Could not insert!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
				}
			catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}
	
	/**
	 * Veraltete Befüllfunktion. Übergibt einfache Statements an das DBMS.
	 * @param n Anzahl der Durchgänge(kann vom Nutzer beliebig gesteuert werden).
	 * @param remote 'true', falls Remote Connection benutzt werden soll.
	 */
	void fill (int n, Boolean remote)
	{	
		
		Connection conn = null;
		 Statement stmt = null;
		 
		String branchname = "ABCDEFGHIJKLMNOPQRST";
		String adress_branches =  "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrst";
		String name_account = "abcdefghijklmnopqrst";
		String adress_accounts = "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnop";
		String name_teller = "abcdefghijklmnopqrst";
		String adress_teller = "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnop";
//		String cmmnt = "AbcdefghijklmnopqrstuvwxyzAbcd";
//		String insert = null ;
		
		int rndm = 0;
		/*
		 *  SQL-Statements für die einzelnen Tupel werden hier per Concatenate zusammengefügt und eingesetzt 
		 */
		try
		{
			if (remote)
				conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			
			//Branches Insert
			for (int i = 1; i <= n; i++)
			{
				stmt.executeUpdate(("insert into branches values ("+i + ","+ "'" + branchname +  "', 0, '" + adress_branches + "');"));
			}
			conn.commit();
			
			//Accounts Insert
			for (int i = 1; i <= n * 100000; i++)
			{
				rndm = (int) (Math.random() * n + 1);
				stmt.executeUpdate(("insert into accounts values ("+i + ", '" + name_account +  "', 0, " + rndm + ", '" + adress_accounts + "');"));
			}
			conn.commit();
			
			//Teller Insert
			for (int i = 1; i <= n * 10; i++)
			{
				rndm = (int) (Math.random() * n + 1);
				stmt.executeUpdate("insert into tellers values (" + i + ",'" + name_teller +  "', 0," + rndm + ",'" + adress_teller + "');");
			}
			conn.commit();
			conn.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	/**
	 * Methode erstellt .txt-Dateien mit Insert-Statements für branches, accounts und tellers. Diese wird dann in execute() für den Insert benutzt.
	 * @param n Anzahl der Durchgänge (kann vom Nutzer beliebig gesteuert werden).
	 * @return  Gibt ein String-Array mit den absoluten Pfaden der angelegten Dateien zurück ([0]: branches.txt, [1]: accounts.txt, [2]: tellers.txt).
	 */
	String[] writeSQLFile (int n) throws IOException
	{
		String branchname = "ABCDEFGHIJKLMNOPQRST";
		String adress_branches =  "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrst";
		String name_account = "abcdefghijklmnopqrst";
		String adress_accounts = "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnop";
		String name_teller = "abcdefghijklmnopqrst";
		String adress_teller = "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnop";
		
		int rndm = 1;
		Writer writer = null;
		
		try {
			//Branches
			writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("branches.txt"), "utf-8"));
		    File branches = new File("branches.txt");	//Mit diesem File kann am Ende der abs. Dateipfad einfach abgerufen werden
		    
		    for (int i = 1; i <= n; i++)
			{
				writer.write(i + "," + branchname +  ",0," + adress_branches + ";");
			}
		    writer.flush();
		    writer.close();
		    
		  //Accounts
		    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("accounts.txt"), "utf-8"));
			    File accounts = new File("accounts.txt");
		    
		    for (int i = 1; i <= n * 100000; i++)
			{
				rndm = (int) (Math.random() * n + 1);
				writer.write((i +"," + name_account + ",0," + rndm + "," + adress_accounts + ";"));

				if (i %500000 == 0)
					writer.flush();
			}
			writer.close();
			
			//Tellers
			writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("tellers.txt"), "utf-8"));
			    File tellers = new File("tellers.txt");
			
			for (int i = 1; i <= n * 10; i++)
			{
				rndm = (int) (Math.random() * n +1);
				writer.write(i + "," + name_teller +  ",0," + rndm + "," + adress_teller + ";");
						
			}
			writer.close();
			
			//Muss leider so, funktioniert aber (auch wenn es Zeit kostet)
			String[] filepaths = new String[3];
			filepaths[0] = branches.getAbsolutePath().replace("\\", "/");
			filepaths[1] = accounts.getAbsolutePath().replace("\\", "/");
			filepaths[2] = tellers.getAbsolutePath().replace("\\", "/"); 
			return filepaths;
		} 
		catch (IOException ex) {
			  System.out.println("File creation failed!");
			}
		finally
		{
			writer.close();
		}
		return filepaths;
	}
	/**
	 * Löscht die mit writeSQLFile() angelegten .txt-Dateien wieder (accounts.txt nimmt sonst bei n=50 ein knappes halbes GB auf der Platte ein!). Wird nach dem Benchmarking durchgeführt.
	 * @param filepath Array mit den absoluten Pfaden der angelegten Dateien ([0]: branches.txt, [1]: accounts.txt, [2]: tellers.txt).
	 */
	void deleteFiles(String[] filepaths)
	{
		System.out.println();
		for(String s : filepaths)
		{
			File file = new File(s);
	         
	        if(file.delete())
	        {
	            System.out.println(s +" deleted successfully");
	        }
	        else
	        {
	            System.out.println("Failed to delete " +s);
	        }
		}
	}
	/**
	 * Wird diese Funktion aufgerufen, so werden alle vorhandenen Test-Tables der Benchmark-Datenbank auf die InnoDB-Engine umgestellt.
	 * @param remote 'true', falls Remote Connection benutzt werden soll.
	 */
	void updateEngine(Boolean remote)
	{
		Connection conn = null;
		Statement stmt = null;		 
		/* Strings müssen vorher erstellt werden, da Concat in executeUpdate() nicht erlaubt ist. Java Magic, fuck that */
		String update_branches = "alter table branches ENGINE = INNODB;" ;
		String update_accounts = "alter table accounts ENGINE = INNODB;" ;
		String update_tellers = "alter table tellers ENGINE = INNODB;" ;
		String update_history = "alter table history ENGINE = INNODB;" ;
			
		try {
			System.out.println("Updating table engine..");
			if (remote)
				conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
			else
				conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate (update_branches);
			conn.commit();
			stmt.executeUpdate (update_accounts);
			conn.commit();
			stmt.executeUpdate (update_tellers); 
			conn.commit();
			stmt.executeUpdate (update_history);
			conn.commit();
			conn.close();
				
				
		} catch (SQLException e) {
				System.out.println("Connection failed! (updateEngine() )");
				e.printStackTrace();
			}
			finally {
				try	{
					if (stmt != null)
						conn.close();
			}
				catch (SQLException se) {
					se.printStackTrace();
				}
		}
	}
}

