package de.whs.dbi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Eine Konfiguration beinhaltet alle variablen und einstellbaren Eigenschaften
 * zu einem Benchmark-Lauf. Konfigurationen sind serialisierbar und koennen daher
 * per RMI als Aufrufparameter uebertragen werden.
 */
public class Configuration implements Serializable
{
    private static final long serialVersionUID = -4491234893133092327L;

    /**
     * XML-Konfigurationsdatei mit allgemeinen Benchmarkeinstellungen
     */
    protected final static String BENCHMARK_FILE = "Benchmark.xml";

    /**
     * XML-Konfigurationsdatei zum Lastprofil
     */
    protected final static String TRANSACTIONS_FILE = "Transactions.xml";

    /**
     * XML-Konfigurationsdatei zur RMI-Registry und der Socket-Connection
     */
    protected final static String Communication_FILE = "Communication.xml";
    
    /**
     * Bezeichnung fuer einen BenchmarkController
     */
    public final static String BENCHMARKCONTROLLER = "BenchmarkController";    // wird beim binden der Registry verwendet. 
    

    // Properties der Konfigurationen
    protected Properties pBenchmark;
    protected Properties pTransactions;
    protected Properties pCommunication;

    /**
     * Standard Konfiguration fuer einen Benchmark
     */
    protected final static Properties pBenchmarkDefaults;

    /**
     * Initialisiert die Standard-Konfiguration fuer einen Benchmark.
     */
    static
    {
        pBenchmarkDefaults = new Properties();
        pBenchmarkDefaults.setProperty("benchmark.name", "");
        pBenchmarkDefaults.setProperty("database.jdbc.driver", "");
        pBenchmarkDefaults.setProperty("database.jdbc.url", "");
        pBenchmarkDefaults.setProperty("loaddrivers", "0");
        pBenchmarkDefaults.setProperty("log.level", "CONFIG");
        pBenchmarkDefaults.setProperty("warmup.time", "0");
        pBenchmarkDefaults.setProperty("benchmark.time", "0");
        pBenchmarkDefaults.setProperty("cooldown.time", "0");
        pBenchmarkDefaults.setProperty("thinktime", "0");
    }

    /**
     * Der Konstruktor initialisiert die Konfiguration.
     * 
     * @throws InvalidPropertiesFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Configuration() throws InvalidPropertiesFormatException, FileNotFoundException, IOException
    {
        pBenchmark = new Properties(pBenchmarkDefaults);
        pTransactions = new Properties();
        pCommunication = new Properties();
    }

    /**
     * Laedt die Konfiguration fuer den Benchmark.
     * 
     * @throws InvalidPropertiesFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void loadBenchmarkConfiguration() throws InvalidPropertiesFormatException, FileNotFoundException, IOException
    {
        pBenchmark.loadFromXML(new FileInputStream(new File(BENCHMARK_FILE)));
    }

    /**
     * Laedt die Konfigruation fuer die Transaktionen.
     * 
     * @throws InvalidPropertiesFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void loadTransactionsConfiguration() throws InvalidPropertiesFormatException, FileNotFoundException, IOException
    {
        pTransactions.loadFromXML(new FileInputStream(new File(TRANSACTIONS_FILE)));
    }

    /**
     * Loggt die Konfiguration fuer den Benchmark.
     * 
     * @param log
     */
    public void logBenchmarkConfiguration(Logger log)
    {
        for (String key : pBenchmark.stringPropertyNames())
        {
            log.config(key + " = " + pBenchmark.getProperty(key));
        }
    }

    /**
     * Loggt die Konfiguration fuer die Transaktionen.
     * 
     * @param log
     */
    public void logTransactionConfiguration(Logger log)
    {
        for (String key : pTransactions.stringPropertyNames())
        {
            log.config("Transaktion " + key + " " + pTransactions.getProperty(key));
        }
    }

    /**
     * Laedt die Konfiguration fuer die RMI-Registry und die Socket-Connection.
     * 
     * @throws InvalidPropertiesFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void loadCommunicationConfiguration() throws InvalidPropertiesFormatException, FileNotFoundException, IOException
    {
        pCommunication.loadFromXML(new FileInputStream(new File(Communication_FILE)));
    }

    /**
     * Speichert die Konfiguration fuer den Benchmark.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveBenchmarkConfiguration() throws FileNotFoundException, IOException
    {
        pBenchmark.storeToXML(new FileOutputStream(new File(BENCHMARK_FILE)), "Benchmark");
    }

    /**
     * Speichert die Konfiguration fuer die Transaktionen.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveTransactionsConfiguration() throws FileNotFoundException, IOException
    {
        pTransactions.storeToXML(new FileOutputStream(new File(TRANSACTIONS_FILE)), "Transactions");
    }

    /**
     * Speichert die Konfiguration fuer die RMI-Registry und die Socket-Connection.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveCommunicationConfiguration() throws FileNotFoundException, IOException
    {
        pCommunication.storeToXML(new FileOutputStream(new File(Communication_FILE)), "COMMUNICATION");
    }


    /**
     * Gibt alle Transaktionen zurueck.
     * 
     * 
     * @return Transaktionen
     */
    public Set<String> getTransactions()
    {
        return pTransactions.stringPropertyNames();
    }

    /**
     * Gibt die Gewichtung einer Transaktion zurueck.
     * 
     * @param transaction
     *            Transaktion
     * @return Gewichtung
     */
    public Integer getTransactionWeight(String transaction)
    {
        int i = Integer.parseInt(pTransactions.getProperty(transaction));
        if (i > 0) return i;
        return 0;
    }

    /**
     * Fuegt eine Transaktion hinzu.
     * 
     * @param transaction
     *            Transaktion
     * @param weight
     *            Gewichtung
     */
    public void addTransaction(String transaction, int weight)
    {
        pTransactions.setProperty(transaction, Integer.toString(weight));
    }

    /**
     * Entfernt eine Transaktion.
     * 
     * @param transaction
     *            Transaktion
     */
    public void removeTransaction(String transaction)
    {
        if (pTransactions.containsKey(transaction)) pTransactions.remove(transaction);
    }

    /**
     * Gibt einen Benchmark-Konfigurationsparameter zurueck.
     * 
     * @param key
     *            Parameterschluessel
     * @return Parameterwert
     * @throws Exception
     */
    public String getBenchmarkProperty(String key) throws Exception
    {
        String value = pBenchmark.getProperty(key);
        if (value == null) throw new Exception("Der Benchmark-Konfigurationsparameter \"" + key + "\" ist nicht vorhanden.");
        return value;
    }

    /**
     * Ueberprueft, ob ein Benchmark-Konfigurationsparameter festgelegt ist.
     * 
     * @param key   Parameterschluessel
     * @return boolean
     */
    public boolean issetBenchmarkProperty(String key)
    {
        return pBenchmark.containsKey(key);
    }

    /**
     * Legt einen Benchmark-Konfigurationsparameter fest.
     * 
     * @param key
     *            Parameterschluessel
     * @param value
     *            Parameterwert
     */
    public void setBenchmarkProperty(String key, String value)
    {
        pBenchmark.setProperty(key, value);
    }

    /**
     * Gibt einen Communication-Konfigurationsparameter zurueck.
     * 
     * @param key
     *            Parameterschluessel
     * @return Parameterwert
     * @throws Exception
     */
    public String getCommunicationProperty(String key) throws Exception
    {
        String value = pCommunication.getProperty(key);
        if (value == null) throw new Exception("Der Communication-Konfigurationsparameter \"" + key + "\" ist nicht vorhanden.");
        return value;
    }

    /**
     * Ueberprueft, ob ein Communication-Konfigurationsparameter festgelegt ist.
     * 
     * @param key
     *            Parameterschluessel
     * @return boolean
     */
    public boolean issetCommunicationProperty(String key)
    {
        return pCommunication.containsKey(key);
    }

    /**
     * Legt einen Communication-Konfigurationsparameter fest.
     * 
     * @param key
     *            Parameterschluessel
     * @param value
     *            Parameterwert
     */
    public void setCommunicationProperty(String key, String value)
    {
        pCommunication.setProperty(key, value);
    }
}
