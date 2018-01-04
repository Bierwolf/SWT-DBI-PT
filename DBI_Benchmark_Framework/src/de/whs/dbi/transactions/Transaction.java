package de.whs.dbi.transactions;

import java.sql.Connection;
import java.sql.SQLException;

import de.whs.dbi.util.Configuration;

/**
 * Die abstrakte Klasse Transaction kapselt im Framework eine einzelne Transaktion
 * aus dem genutzten Lastprofil.
 */
public abstract class Transaction implements TransactionIF
{
    protected Connection connection;            // Datenbankverbindung
    protected Configuration config;
    
    public Transaction(Connection con, Configuration config)
    {
        this.connection = con;
        this.config = config;
    }
    
    // Abstrakte Methoden    
    protected abstract void run() throws SQLException;
    
    // leer implementierte Methoden
    @Override
    public void setInputData(Object input)
    { }
    
    @Override
    public Object getOutputData()
    {
        return null;
    }
    
    // implementierte Methoden    
    
    /**
     * Beginnt eine Transaktion.
     * 
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException
    {
        // Kann vom User ueberschrieben werden.
        // Standardmaessig ist aber beim Start einer JDBC-TX nichts zu tun.
    }

    /**
     * Beendet eine Transaktion.
     * 
     * @throws SQLException
     */
    public void commitTransaction() throws SQLException
    {
        connection.commit();
    }

    /**
     * Setzt eine Transaktion zurueck.
     * 
     */
    public void rollbackTransaction() 
    {
        try
        {
            connection.rollback();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Fuehrt die Transaktion aus.
     * 
     * @throws Exception
     */
    public void execute() throws Exception 
    {
        try
        {
            beginTransaction();
            run();
            commitTransaction();
        } catch (Exception e)
        {
            e.printStackTrace();
            rollbackTransaction();      // Rollback soll (wie das Commit auch) hier in der execute-Methode gesetzt werden
            throw e;                    // bevor die Exception im LoadDriver verarbeitet wird.
        }
    }

    /**
     * Gibt den Namen der Transaktion mit Package zurueck.
     * 
     * @return Name
     */
    public String getFullName()
    {
        return getClass().getName();
    }

    public String getTxName()
    {
        String[] txArray = getFullName().split("\\.");
        String tmp = txArray[txArray.length-1];
        return tmp;
    }
}
