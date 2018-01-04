package de.whs.dbi.loaddriver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.whs.dbi.benchmark.BenchmarkControllerIF;
import de.whs.dbi.benchmark.BenchmarkControllerIF.STAGE;
import de.whs.dbi.statistics.LocalResult;
import de.whs.dbi.statistics.Statistics;
import de.whs.dbi.transactions.Transaction;
import de.whs.dbi.transactions.WeightedTransaction;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.ParameterGenerator;
import de.whs.dbi.util.RemoteLoggerIF;

/**
 * LoadDriver realisiert einen einzelnen Load Driver.
 */
public class LoadDriver extends UnicastRemoteObject implements LoadDriverIF
{
    private static final long serialVersionUID = -3045240234269310354L;

    protected Registry registry;
    protected Connection connection;
    protected Integer loadDriverID;
    protected Configuration config;
    protected BenchmarkControllerIF bController;

    protected RemoteLoggerIF globalLogger;      // nutzt zum Loggen den RemoteLogger des BenchmarkControllers
    protected Logger localLogger;               // loggt lokal in die Console und in ein Log-File
    protected String localLogPath;
    protected String localLogFileName;
    protected FileHandler fileHandler;
    protected ArrayList<Transaction> transactions = new ArrayList<Transaction>();        // TODO: kommt in den Emulator
    protected ArrayList<Integer> weights = new ArrayList<Integer>();                     // TODO: kommt in den Emulator

    protected STAGE stage = STAGE.INIT;

    /**
     * Der Konstruktor initialisiert den LoadDriver, sodass dieser im Benchmark
     * verfuegbar ist.
     * 
     * @throws Exception
     */
    public LoadDriver() throws Exception
    {
        config = new Configuration();
        config.loadCommunicationConfiguration();

        registry = LocateRegistry.getRegistry(config.getCommunicationProperty("server.host"),
                Integer.parseInt(config.getCommunicationProperty("registry.port")));
        bController = (BenchmarkControllerIF) registry.lookup(Configuration.BENCHMARKCONTROLLER);

        config = bController.getConfiguration();

        loadDriverID = bController.registerLoadDriver(this);
        initLogger();
        localLogger.info("LoadDriver ist mit BenchmarkController verbunden.\n");
        globalLogger.log(Level.INFO, "Loadriver " + loadDriverID + " says hello!" );
        connection = openConnection(config);
        loadWeightedTransactions();                  // TODO: kommt in den Emulator

        Statistics.initialize("Loaddriver " + loadDriverID, Integer.parseInt(config.getBenchmarkProperty("benchmark.time")));

        stage = STAGE.READY;
    }

    /**
     * Initialisiert die Transaktionen mit den dazugehoerigen Gewichtungen.
     * 
     * @throws Exception 
     * @throws NumberFormatException 
     */
    protected void loadWeightedTransactions() throws NumberFormatException, Exception        // TODO: kommt in den Emulator
    {
        Integer weight = 0;
        for (String transactionName : config.getTransactions())
        {
            WeightedTransaction tx = createWeightedTransaction(transactionName, config.getTransactionWeight(transactionName), connection);
            transactions.add(tx);
            weight += config.getTransactionWeight(transactionName);
            weights.add(weight);
        }
    }

    /**
     * Erstellt ein gewichtetes Transaktionsobjekt zu einem vorgegebenen Methodennamen aus
     * dem Lastprofil.
     *  
     */
    protected final WeightedTransaction createWeightedTransaction(String transactionName, int weight, Connection con) throws NumberFormatException, Exception    // TODO: kommt in den Emulator
    {
        WeightedTransaction tx =  (WeightedTransaction) Class.forName(transactionName).getConstructor(Connection.class, int.class, Configuration.class).newInstance(con, weight, config);
        return tx;
    }

    /**
     * Waehlt eine Transaktion unter der Beruecksichtigung der Gewichtungen aus.
     * 
     * @return Ausgewaehlte Transaktion
     */
    protected Transaction chooseTransaction()       // TODO: kommt in den Emulator
    {
        int i;
        Integer randomWeight = ParameterGenerator.generateRandomInt(1, weights.get(weights.size() - 1));

        for (i = 0; i < transactions.size(); i++)
        {
            Integer weight = weights.get(i);
            if (randomWeight <= weight) break;
        }

        return transactions.get(i);
    }

    /**
     * Stellt die Datenbankverbindung her.
     * 
     * @param config
     * @throws Exception 
     */
    protected Connection openConnection(Configuration config) throws Exception
    {
        Connection con;
        String driver = config.getBenchmarkProperty("database.jdbc.driver");
        loadJDBCDriver(driver);
        if ((config.issetBenchmarkProperty("database.jdbc.user")) && (config.issetBenchmarkProperty("database.jdbc.password")))
        {
            String url = config.getBenchmarkProperty("database.jdbc.url");
            String user = config.getBenchmarkProperty("database.jdbc.user");
            String password =  config.getBenchmarkProperty("database.jdbc.password");
            con = DriverManager.getConnection(url, user, password);
        }
        else
        {
            String url = config.getBenchmarkProperty("database.jdbc.url");
            con = DriverManager.getConnection(url);
        }
        con.setAutoCommit(false);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    /**
     * laedt den JDBC Treiber
     * @param driver Name des Treibers
     */
    protected void loadJDBCDriver(String driver)
    {
        if(!driver.equals("")) {
            try
            {
                Class.forName(driver);
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * De-Registriert den LoadDriver.
     * 
     * @throws SQLException
     * @throws NoSuchObjectException
     */
    public void close() throws NoSuchObjectException, SQLException
    {
        connection.close();
        UnicastRemoteObject.unexportObject(this, true);
    }

    /**
     * Fuehrt den Benchmark aus und gibt das Ergebnis zurueck.
     * 
     * @return Ergebnis
     * @throws Exception
     */
    @Override
    public LocalResult call() throws Exception
    {
        while (stage != STAGE.FINISHED)
        {
            // Ausfuehrung der Transaktion
            Transaction transaction = chooseTransaction();
            try
            {
                long duration = System.currentTimeMillis();
                transaction.execute();
                duration = System.currentTimeMillis() - duration;
                String logEntry = new StringBuilder().append("Dauer der erfolgreichen Transaktion \"").append(transaction.getTxName()).append("\" in ms: ").append(duration).toString();
                if (stage == STAGE.BENCHMARK)
                {
                    Statistics.addResult(transaction.getTxName(), duration, true);
                    localLogger.log(Level.FINE, logEntry);                
                }
                else
                {
                    localLogger.log(Level.FINEST, logEntry);
                }
            }
            catch (Exception e)
            {
                if (stage == STAGE.BENCHMARK)
                {
                    Statistics.addResult(transaction.getTxName(), 0, false);
                }
                String logEntry = new StringBuilder().append("Fehlgeschlagene Transaktion \"").append(transaction.getTxName()).append("\"").toString();
                localLogger.log(Level.WARNING, logEntry, e.getCause());
            }
            finally
            {
                // Nach JEDER (auch fehlgeschlagenen) TX soll gewartet werden
                Thread.sleep(Integer.parseInt(config.getBenchmarkProperty("thinktime")));
            }
        }
        // Logge Statistiken lokal beim Loaddriver
        String report = Statistics.getLocalResult().getReport();
        localLogger.info(report);
        return Statistics.getLocalResult();
    }

    /**
     * Gibt die aktuelle Phase zurueck.
     * 
     * @return Phase
     * @throws RemoteException
     */
    @Override
    public STAGE getStage() throws RemoteException
    {
        return stage;
    }

    protected void initLogger() throws Exception
    {
        // LocalLogger
        localLogFileName = "benchmark_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_ld_" + loadDriverID + ".log";
        localLogPath = config.getBenchmarkProperty("log.directory");
        Level lvl = Level.parse(config.getBenchmarkProperty("log.level"));
        fileHandler = new FileHandler(localLogPath + localLogFileName);
        fileHandler.setLevel(lvl);
        localLogger = Logger.getLogger(LoadDriver.class.getCanonicalName() + "_" + loadDriverID + "_local");
        localLogger.addHandler(fileHandler);
        localLogger.setLevel(Level.ALL);
        // GlobalLogger
        globalLogger = bController.getRemoteLogger();
    }

    /**
     * Legt die aktuelle Phase fest.
     * 
     * @param stage
     *            Phase
     * @throws RemoteException
     */
    @Override
    public void setStage(STAGE stage) throws RemoteException
    {
        this.stage = stage;
        localLogger.info("State changed to " + stage + "\n");
    }

    protected void transferLogFile(Socket socket) throws NumberFormatException, UnknownHostException, IOException, Exception
    {
        fileHandler.close(); // Fuegt den Closing-Tag </log> am Ende des Log-Files hinzu.
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.println(localLogFileName);
        pw.flush();
        Path path = Paths.get(localLogPath + localLogFileName);
        try (Scanner scanner = new Scanner(path))
        { // Ressourcen, die in den Klammern stehen, werden automatisch am Ende des Blocks geschlossen
            while (scanner.hasNextLine())
            {
                // process each line in some way
                pw.println(scanner.nextLine());
            }
        }
        pw.flush();
        pw.close();
        socket.close();
    }

    protected Socket connectToServer(int maxRetries, int delayMillis)
    {
        Socket socket;
        int retries = 0;
        while (retries < maxRetries)
        {
            try
            {
                socket = new Socket(config.getCommunicationProperty("server.host"), Integer.parseInt(config.getCommunicationProperty("log.port")));
                return socket;
            }
            catch (Exception e)
            {
                localLogger.log(Level.SEVERE, "Couldn't connect to server. Going to retry in " + delayMillis + "ms");
                retries++;
            }
        }
        localLogger.log(Level.SEVERE, "Couldn't connect to server. No more retries.");
        return null;
    }

    /**
     * Initiiert die Uebertragung des lokalen Log File an den Benchmark Controller.
     * 
     * @throws RemoteException
     */
    @Override
    public void sendLogFile() throws RemoteException
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    Socket socket = connectToServer(10, 1000);
                    transferLogFile(socket);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    /**
     * Hauptprogramm zum Starten eines LoadDrivers.
     * 
     * @param args
     *            Uebergebene Aufrufparameter
     */
    public static void main(String[] args)
    {
        try
        {
            new LoadDriver();
            System.out.println("Der Load Driver wurde gestartet.\n");
        }
        catch (Exception e)
        {
            System.out.println("Der Load Driver konnte nicht korrekt gestartet werden! Wurde der Benchmark Controller tatsaechlich vorher gestartet?\n");
            e.printStackTrace();
            System.exit(0);
        }
    }

}
