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
			//stmt.executeUpdate("SET @@session.unique_checks = 0;");
			//stmt.executeUpdate("SET @@session.foreign_key_checks = 0;");
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
				}
			catch (SQLException se) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	
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
			// TODO Auto-generated catch block
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
				}
			catch (SQLException se) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	
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
			// TODO Auto-generated catch block
			System.out.println("Connection failed! create_tables");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
		}
			catch (SQLException se) {
				// TODO Auto-generated catch block
			}
		}
	}
		
	
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
			// TODO Auto-generated catch block
			System.out.println("Connection failed! delete");
			e.printStackTrace();
			}
		finally {
			try	{
				if (stmt != null)
					conn.close();
		}
			catch (SQLException se) {
				se.printStackTrace();
				// TODO Auto-generated catch block
			}
		}
	}
	
	
	void execute(String[] filepaths, Boolean remote) 
	{
		Connection conn = null;
		Statement stmt = null;
		
		try {
			System.out.println("Loading Files..");
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
			// TODO Auto-generated catch block
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
		finally {
			try	{
				if (stmt != null)
					conn.close();
				}
			catch (SQLException se) {
				// TODO Auto-generated catch block
			}
		}
	}
	
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
	
	/*
	 * Wenn du willst, kannst du die Tables noch in einzelne Files packen. Das mit BEGIN und END soll bei MariaDB ein Puffern von je 1000 TX-Blöcken bewirken und ist deswegen eigentlich ziemlich wichtig.#
	 * (Habs noch nicht ausprobiert.)
	 * Die Methode gibt den absoluten Pfad der .txt zurück. Damit kann man dann ein SQL-Statement Maria-DB übergeben
	 * Branches und Tellers vielleicht besser über die alte Methode übergeben
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
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("branches.txt"), "utf-8"));
		    File branches = new File("branches.txt");
		    
		    //Branches
		    for (int i = 1; i <= n; i++)
			{
				writer.write(i + "," + branchname +  ",0," + adress_branches + ";");
			}
		    writer.flush();
		    
		    writer.close();
		    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("accounts.txt"), "utf-8"));
			    File accounts = new File("accounts.txt");
		    
		    //Accounts
			for (int i = 1; i <= n * 100000; i++)
			{
				
				rndm = (int) (Math.random() * n + 1);
				writer.write((i +"," + name_account + ",0," + rndm + "," + adress_accounts + ";"));

				if (i %500000 == 0)
					writer.flush();
			}
			writer.close();
			writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("tellers.txt"), "utf-8"));
			    File tellers = new File("tellers.txt");
			    
			//Tellers
			for (int i = 1; i <= n * 10; i++)
			{
				rndm = (int) (Math.random() * n +1);
				writer.write(i + "," + name_teller +  ",0," + rndm + "," + adress_teller + ";");
						
			}
			writer.close();
			
			//Muss leider so, funktioniert aber
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
}
