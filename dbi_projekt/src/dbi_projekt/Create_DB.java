package dbi_projekt;

import java.sql.*;

public class Create_DB 
{
	static final String USER = "dbi";
	static final String PASS = "dbi_pass";
	static final String CONURL = "jdbc:mariadb://127.0.0.1:3306/benchmark";
	
	void createDatabase()
	{	
		Connection conn = null;
		Statement stmt = null;
		 
		String create_database = "CREATE DATABASE benchmark";
		
		try {
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(CONURL, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate(create_database);
			conn.commit();
			
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
	void createTables ()
	{		
		 Connection conn = null;
		 Statement stmt = null;
		 int affected;
		 
		
		 String table_branches = "create table branches" + 
		 		"( branchid int not null," + 
		 		"branchname char(20) not null," + 
		 		"balance int not null," + 
		 		"address char(72) not null," + 
		 		"primary key (branchid) );"
		 		;
		 
		 String table_accounts ="( accid int not null," + 
		 		"name char(20) not null," + 
		 		"balance int not null," + 
		 		"branchid int not null," + 
		 		"address char(68) not null," + 
		 		"primary key (accid)," + 
		 		"foreign key (branchid) references branches );"
				 ;
		 
		 String table_tellers = "( tellerid int not null," + 
		 		"tellername char(20) not null," + 
		 		"balance int not null," + 
		 		"branchid int not null," + 
		 		"address char(68) not null," + 
		 		"primary key (tellerid)," + 
		 		"foreign key (branchid) references branches );"
		 		;
		 		
		 	String table_history = "create table history" + 
		 			"( accid int not null," + 
		 			"tellerid int not null," + 
		 			"delta int not null," + 
		 			"branchid int not null," + 
		 			"accbalance int not null," + 
		 			"cmmnt char(30) not null," + 
		 			"foreign key (accid) references accounts," + 
		 			"foreign key (tellerid) references tellers," + 
		 			"foreign key (branchid) references branches );"
				 ;
		try {
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(CONURL, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			affected = stmt.executeUpdate ((table_branches + table_accounts + table_tellers + table_history));
			conn.commit();
			//System.out.println ("Affected: " + affected);
			
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
		
	/*
	 * WICHTIG: Es fehlt IF EXISTS
	 */
	void deleteTables()
	{
		Connection conn = null;
		 Statement stmt = null;
		 
		String drop_branches = "DROP TABLE branches";
		String drop_tellers = "DROP TABLE tellers";
		String drop_accounts = "DROP TABLE accounts";
		String drop_history = "DROP TABLE history";
		
		try {
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(CONURL, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate((drop_branches + drop_accounts + drop_tellers + drop_history));
			conn.commit();
		}
		catch (SQLException e) {
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
	
	void fill (int n)
	{
		Connection conn = null;
		 Statement stmt = null;
		 
		String branchname = "ABCDEFGHIJKLMNOPQRST";
		String adress_branches =  "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrst";
		String name_account = "abcdefghijklmnopqrst";
		String adress_accounts = "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnop";
		String name_teller = "abcdefghijklmnopqrst";
		String adress_teller = "AbcdefghijklmnopqrstuvwxyzAbcdefghijklmnopqrstuvwxyzAbcdefghijklmnop";
		String cmmnt = "AbcdefghijklmnopqrstuvwxyzAbcd";
		
		int rndm = 0;
		
		try
		{
			conn = DriverManager.getConnection(CONURL, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			for (int i = 1; i <= n; i++)
			{
				
				stmt.executeUpdate("insert into branches values ("
						+i + ","	//branchid
						+ "'" + branchname +  "'" //branchname
						+ ", 0" +  "," //balance
						+ "'" + adress_branches + "';"); //adress
				//(int) Math.random();
			}
			conn.commit();
			
			for (int i = 1; i <= n * 100000; i++)
			{
				rndm = (int) Math.random();
				
				stmt.executeUpdate("insert into branches accounts ("
						+i + ","
						+ "'" + name_account +  "'"
						+ ", 0" +  ","
						+ rndm + ","
						+ "'" + adress_accounts + "';");
				//(int) Math.random();
			}
			conn.commit();
			
			for (int i = 1; i <= n * 10; i++)
			{
				rndm = (int) Math.random();
				
				stmt.executeUpdate("insert into branches tellers ("
						+i + ","
						+ "'" + name_teller +  "'"
						+ ", 0" +  ","
						+ rndm + ","
						+ "'" + adress_teller + "';");
			}
			
			conn.commit();
		}
		catch (SQLException se) {
			// TODO Auto-generated catch block
		}
	}
}
