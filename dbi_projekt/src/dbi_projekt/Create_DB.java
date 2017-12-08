package dbi_projekt;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.*;

public class Create_DB 
{
	static final String USER = "dbi";
	static final String PASS = "dbi_pass";
	static final String CON_URL = "jdbc:mariadb://localhost:3306/";
	static final String CON_URL_DB = "jdbc:mariadb://localhost:3306/benchmark";
	static final String CON_REMOTE = "jdbc:mariadb://192.168.122.43:3306/";
	static final String CON_REMOTE_DB = "jdbc:mariadb://192.168.122.43:3306/benchmark";
	
	void createDatabase()
	{	
		Connection conn = null;
		Statement stmt = null;
		
		try {
			System.out.println("Creating DB..");
			conn = DriverManager.getConnection(CON_URL, USER, PASS);
			//conn = DriverManager.getConnection(CON_REMOTE, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS benchmark");
			stmt.executeUpdate("SET @@session.unique_checks = 0;");
			stmt.executeUpdate("SET @@session.foreign_key_checks = 0;");
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
	void deleteDatabase()
	{	
		Connection conn = null;
		Statement stmt = null;
		
		try {
			System.out.println("Deleting DB..");
			conn = DriverManager.getConnection(CON_URL, USER, PASS);
			//conn = DriverManager.getConnection(CON_REMOTE, USER, PASS);
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
	void createTables ()
	{		
		 Connection conn = null;
		 Statement stmt = null;
		 int affected;
		 
		
		 String table_branches = "create table IF NOT EXISTS branches" + 
		 		"( branchid int not null," + 
		 		"branchname char(20) not null," + 
		 		"balance int not null," + 
		 		"address char(72) not null," + 
		 		"primary key (branchid) );"
		 		;
		 
		 String table_accounts ="create table IF NOT EXISTS accounts" + 
				"( accid int not null," + 
		 		"name char(20) not null," + 
		 		"balance int not null," + 
		 		"branchid int not null," + 
		 		"address char(68) not null," + 
		 		"primary key (accid)," + 
		 		"foreign key (branchid) references branches(branchid) );"
				 ;
		 
		 String table_tellers = "create table IF NOT EXISTS tellers" + 
				" ( tellerid int not null," + 
		 		"tellername char(20) not null," + 
		 		"balance int not null," + 
		 		"branchid int not null," + 
		 		"address char(68) not null," + 
		 		"primary key (tellerid)," + 
		 		"foreign key (branchid) references branches(branchid) );"
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
		 			"foreign key (branchid) references branches(branchid));"
				 ;
		try {
			System.out.println("Creating tables..");
			conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			//conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
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
		
	void deleteTables()
	{
		Connection conn = null;
		 Statement stmt = null;
		 
		String drop_branches = "DROP TABLE IF EXISTS branches;";
		String drop_tellers = "DROP TABLE IF EXISTS tellers;";
		String drop_accounts = "DROP TABLE IF EXISTS accounts;";
		String drop_history = "DROP TABLE IF EXISTS history;";
		
		try {
			System.out.println("Deleting tables..");
			conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			//conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
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
		String insert = null ;
		
		Writer writer = null;
				
		int rndm = 0;
		
		try
		{
			conn = DriverManager.getConnection(CON_URL_DB, USER, PASS);
			//conn = DriverManager.getConnection(CON_REMOTE_DB, USER, PASS);
			conn.setAutoCommit (false);
			stmt = conn.createStatement();
			
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("branches.txt"), "utf-8"));
			} catch (IOException ex) {
			  // report
			} 
			
			for (int i = 1; i <= n; i++)
			{
				//insert = "insert into branches values ("+i + ","+ "'" + branchname +  "'" + ", 0" +  "," + "'" + adress_branches + "');";
				//stmt.executeUpdate(("insert into branches values ("+i + ","+ "'" + branchname +  "'" + ", 0" +  "," + "'" + adress_branches + "');"));	 //adress
				writer.write(i + ","+ branchname + ",0" +  "," + adress_branches + ";");
				//(int) Math.random();
			}
			conn.commit();
			
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("accounts.txt"), "utf-8"));
			} catch (IOException ex) {
			  // report
			} 
			
			for (int i = 1; i <= n * 100000; i++)
			{
				rndm = (int) Math.random()+1;
				
				//insert = ("insert into accounts values ("+i + "," + "'" + name_account +  "'"+ ", 0" +  ","	+ rndm + ","+ "'" + adress_accounts + "');");
				//stmt.executeUpdate("insert into accounts values ("+i + "," + "'" + name_account +  "'"+ ", 0" +  ","	+ rndm + ","+ "'" + adress_accounts + "');");
				writer.write(i + "," + name_account + ",0" +  ","	+ rndm + ","+ adress_accounts + ";");
				//(int) Math.random();
			}
			conn.commit();
			
			try {
			    writer = new BufferedWriter(new OutputStreamWriter(
			          new FileOutputStream("tellers.txt"), "utf-8"));
			} catch (IOException ex) {
			  // report
			} 
			
			for (int i = 1; i <= n * 10; i++)
			{
				rndm = (int) Math.random()+1;
				
				//insert = ("insert into tellers values ("+i + ","+ "'" + name_teller +  "'"+ ", 0" +  ","+ rndm + ","+ "'" + adress_teller + "');");
				//stmt.executeUpdate("insert into tellers values ("+i + ","+ "'" + name_teller +  "'"+ ", 0" +  ","+ rndm + ","+ "'" + adress_teller + "');");
				writer.write(i + ","+  name_teller + ",0" +  ","+ rndm + ","+ adress_teller + ";");
			}
			
			conn.commit();
			conn.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
			// TODO Auto-generated catch block
		}catch (IOException ex) {
			  // report
			} finally {
			   try {writer.close();} catch (Exception ex) {/*ignore*/}
			}
	}
}
