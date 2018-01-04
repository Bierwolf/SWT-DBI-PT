package de.whs.dbi.benchmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.whs.dbi.util.Configuration;

/**
 * InitMyDatabase ist eine weiter auszuprogrammierende Klasse zur
 * Initialisierung der Datenbank.
 */
public class InitMyDatabase
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
     * Stellt eine Datenbankverbindung her und deaktiviert AutoCommit. Die
     * benoetigte JDBC-URL wird aus der Konfiguration gelesen.
     * 
     * @throws Exception
     */
    protected static void openConnection() throws Exception
    {
        if ((config.issetBenchmarkProperty("database.jdbc.user")) && (config.issetBenchmarkProperty("database.jdbc.password")))
        {
            connection = DriverManager.getConnection(config.getBenchmarkProperty("database.jdbc.url"), config.getBenchmarkProperty("database.jdbc.user"),
                    config.getBenchmarkProperty("database.jdbc.password"));
        }
        else
        {
            connection = DriverManager.getConnection(config.getBenchmarkProperty("database.jdbc.url"));
        }
        connection.setAutoCommit(false);
    }

    /**
     * Schliesst die Datenbankverbindung.
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

    }

    /**
     * Erstellt die Tabellen.
     * 
     * @throws SQLException
     */
    protected static void createTables() throws SQLException
    {

    }

    /**
     * Fuehrt optimierende Konfigurationseinstellungen im DBMS und an der
     * Datenbank durch.
     * 
     * @throws SQLException
     */
    protected static void tuneDatabase() throws SQLException
    {

    }

    /**
     * Legt die neuen Datensaetze an.
     * 
     * @throws SQLException
     */
    protected static void insertRows() throws SQLException
    {

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

            insertRows();

            // Berechnung der Zeitdauer der Initialisierung
            duration = System.currentTimeMillis() - duration;

            closeConnection();

            System.out.println("Einfuegedauer: " + duration / 1000 + " Sekunden\n");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
