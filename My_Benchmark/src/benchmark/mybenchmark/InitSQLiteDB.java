package benchmark.mybenchmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.ParameterGenerator;

/**
 * Die Klasse InitSQLiteDB initialisiert die SQLite-Datenbank.
 */
public class InitSQLiteDB
{
	/**
	 * Datenbankverbindung
	 */
	protected static Connection connection;

	/**
	 * Konfiguration
	 */
	protected static Configuration config;

	/**
	 * Zufaellige Strings
	 */
	protected static String str20 = ParameterGenerator.generateRandomString(20);
	protected static String str68 = ParameterGenerator.generateRandomString(68);

	/**
	 * Stellt eine Datenbankverbindung her und deaktiviert AutoCommit. 
	 * Die benoetigte JDBC-URL wird aus der Konfiguration gelesen.
	 * 
	 * @throws Exception 
	 */
	protected static void openConnection() throws Exception
	{
		if ((config.issetBenchmarkProperty("database.jdbc.user")) && (config.issetBenchmarkProperty("database.jdbc.password")))
		{
			connection = DriverManager.getConnection(config.getBenchmarkProperty("database.jdbc.url"), config.getBenchmarkProperty("database.jdbc.user"), config.getBenchmarkProperty("database.jdbc.password"));
		}
		else
		{
			connection = DriverManager.getConnection(config.getBenchmarkProperty("database.jdbc.url"));
		}
		connection.setAutoCommit(false);
	}

	/**
	 * Schliesst die Datenbankverbindung
	 * 
	 * @throws SQLException
	 */
	protected static void closeConnection() throws SQLException
	{
		connection.close();
	}

	/**
	 * Loescht, falls vorhanden, die bisherigen Tabellen.
	 * 
	 * @throws SQLException
	 */
	protected static void dropTables() throws SQLException
	{
		Statement stmt = connection.createStatement();
		stmt.execute("DROP TABLE IF EXISTS accounts");
		stmt.close();
		connection.commit();
	}

	/**
	 * Erstellt die Tabellen.
	 * 
	 * @throws SQLException
	 */
	protected static void createTables() throws SQLException
	{
		Statement stmt = connection.createStatement();
		stmt.execute("CREATE  TABLE accounts (accid INT NOT NULL, name VARCHAR(20) NULL, balance INT NULL, branchid INT NULL, address VARCHAR(68) NULL,	PRIMARY KEY (accid))");
		stmt.close();
		connection.commit();
	}
	
	/**
	 * Fuehrt optimierende Konfigurationseinstellungen im DBMS
	 * und an der Datenbank durch.
	 * 
	 * @throws SQLException
	 */
	protected static void tuneDatabase() throws SQLException 
	{
		
	}

	/**
	 * Legt die neuen Datensaetze an.
	 * 
	 * @param n Benutzerdefinierter Parameter n
	 * @throws SQLException
	 */
	protected static void insertRows(int n) throws SQLException
	{		
		Statement stmt = connection.createStatement();
		
		for (int i = 1; i <= n * 100000; i++)
		{
			int balance = ParameterGenerator.generateRandomInt(0, 100);
			int branchid = ParameterGenerator.generateRandomInt(1, n);
			stmt.executeUpdate("INSERT INTO accounts (accid, name, balance, branchid, address) VALUES (" + i + ", \"" + str20 + "\", " + balance + ", " + branchid + ", \"" + str68 + "\")");
		}
		
		stmt.close();
		connection.commit();
	}

	/**
	 * Hauptprogramm zum Starten der Initialisierung der Datenbank.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Initialisierung der Datenbank:\n");
			
			// Laedt SQLite
			Class.forName("org.sqlite.JDBC");

			// Laedt die Konfiguration
			config = new Configuration();
			config.loadBenchmarkConfiguration();
			
			openConnection();
			System.out.println("Loeschen vorhandener Relationen ...");
			dropTables();
			System.out.println("Anlegen des Datenbankschemas ...");
			createTables();
			System.out.println("Durchfuehrung von Tuning-Einstellungen ...");
			tuneDatabase();
			System.out.println("Einfuegen der Tupel ...\n");
			
			// Merken des Start-Zeitpunktes der Initialisierung
			long duration = System.currentTimeMillis();
			
			insertRows(Integer.parseInt(config.getBenchmarkProperty("user.n")));
			
			// Berechnung der Zeitdauer der Initialisierung
			duration = System.currentTimeMillis()-duration;
			
			closeConnection();

			System.out.println("Einfuegedauer: " + duration/1000 + " Sekunden");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
