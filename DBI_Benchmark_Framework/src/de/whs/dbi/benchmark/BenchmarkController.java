package de.whs.dbi.benchmark;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import de.whs.dbi.loaddriver.LoadDriverIF;
import de.whs.dbi.statistics.GlobalResult;
import de.whs.dbi.statistics.LocalResult;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.RemoteLogger;
import de.whs.dbi.util.RemoteLoggerIF;

/**
 * Diese Klasse realisiert den Benchmark Controller.
 */
public class BenchmarkController extends UnicastRemoteObject implements BenchmarkControllerIF
{
    private static final long serialVersionUID = 5637573890009643976L;

    protected Configuration config;
    protected ArrayList<LoadDriverIF> loaddrivers;
    protected Registry registry;
    protected RemoteLoggerIF remoteLogger;
    protected LogFileServer lfs;
    protected ExecutorService es;
    protected Logger bcLogger;

    /**
     * Der Konstruktor initialisiert den BenchmarkController, sodass die
     * LoadDriver gestartet werden koennen.
     * 
     * @throws Exception
     */
    public BenchmarkController() throws Exception
    {
        config = new Configuration();
        config.loadCommunicationConfiguration();
        config.loadBenchmarkConfiguration();
        config.loadTransactionsConfiguration();
        String logDir = config.getBenchmarkProperty("log.directory") + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "/";
        lfs = new LogFileServer(Integer.parseInt(config.getBenchmarkProperty("loaddrivers")), Integer.parseInt(config.getCommunicationProperty("log.port")), logDir);

        remoteLogger = new RemoteLogger(Level.parse(config.getBenchmarkProperty("log.level")));
        for (Handler handler : Logger.getLogger("").getHandlers())
        {
            if (handler instanceof ConsoleHandler) handler.setLevel(Level.INFO);
        }


        // init bcLogger
        bcLogger = Logger.getLogger("BenchmarkController Logger");  
        bcLogger.setUseParentHandlers(false);   // disables output to console
        FileHandler fh;  

        fh = new FileHandler(logDir + "result.log");  
        bcLogger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  

        // config.logBenchmarkConfiguration(log);
        // config.logTransactionConfiguration(log);

        loaddrivers = new ArrayList<LoadDriverIF>();
        startRegistry();
        es = Executors.newCachedThreadPool();
    }

    /**
     * Startet die RMI-Registry.
     * 
     * @throws Exception
     */
    protected void startRegistry() throws Exception
    {
        registry = LocateRegistry.createRegistry(Integer.parseInt(config.getCommunicationProperty("registry.port")));
        registry.bind(Configuration.BENCHMARKCONTROLLER, this);
    }

    /**
     * Stoppt die RMI-Registry.
     * 
     * @throws AccessException
     * @throws RemoteException
     * @throws NotBoundException
     */
    protected void stopRegistry() throws AccessException, RemoteException, NotBoundException
    {
        registry.unbind(Configuration.BENCHMARKCONTROLLER);
        UnicastRemoteObject.unexportObject(remoteLogger, true);
        UnicastRemoteObject.unexportObject(registry, true);
        UnicastRemoteObject.unexportObject(this, true);
    }

    /**
     * Ueberprueft die Initialisierung der LoadDriver.
     * 
     * @return Wahrheitswert, der angibt, ob der Load Driver initialisiert ist
     * @throws Exception
     */
    protected boolean isInitiated() throws Exception
    {
        if (loaddrivers.size() < Integer.parseInt(config.getBenchmarkProperty("loaddrivers")))
        {
            return false;
        }
        for (LoadDriverIF loaddriver : loaddrivers)
        {
            if (loaddriver.getStage() != STAGE.READY)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Registriert einen LoadDriver im BenchmarkController und gibt eine
     * eindeutige ID fuer den LoadDriver zurueck.
     * 
     * @param loadDriver
     *            zu registrierendes LoadDriver-Objekt
     * @return ID des LoadDrivers
     * @throws Exception
     */
    @Override
    public synchronized int registerLoadDriver(LoadDriverIF loadDriver) throws Exception
    {
        if (loaddrivers.size() >= Integer.parseInt(config.getBenchmarkProperty("loaddrivers")))
        {
            throw new AlreadyBoundException(); // ToDo: richtige Exception?!
        }
        loaddrivers.add(loadDriver);
        System.out.println("Loaddriver " + loaddrivers.size() + "  verbunden." + " Ausstehend: "
                + (Integer.parseInt(config.getBenchmarkProperty("loaddrivers")) - loaddrivers.size()) + "\n");
        return loaddrivers.size();
    }

    /**
     * De-Registriert alle LoadDriver.
     * 
     * @throws RemoteException
     * @throws SQLException
     */
    protected void closeLoadDrivers() throws RemoteException, SQLException
    {
        for (LoadDriverIF loaddriver : loaddrivers)
        {
            loaddriver.close();
        }
    }

    /**
     * Schliesst den BenchmarkController.
     * 
     * @throws RemoteException
     * @throws SQLException
     * @throws NotBoundException
     */
    protected void close() throws RemoteException, SQLException, NotBoundException
    {
        closeLoadDrivers();
        stopRegistry();
    }

    /**
     * Stellt die genutzte Konfiguration zur Verfuegung.
     * 
     * @return Configuration
     * @throws RemoteException
     */
    @Override
    public Configuration getConfiguration() throws RemoteException
    {
        return config;
    }

    /**
     * Stellt den RemoteLogger zur Verfuegung.
     * 
     * @return RemoteLoggerIF
     * @throws RemoteException
     */
    @Override
    public RemoteLoggerIF getRemoteLogger() throws RemoteException
    {
        return remoteLogger;
    }

    /**
     * Fuehrt den BenchmarkController aus und gibt das Ergebnis zurueck.
     * 
     * @return Ergebnisobjekt mit den Benchmark-Ergebnissen
     * @throws Exception
     */
    protected GlobalResult run() throws Exception
    {
        GlobalResult globalResult = new GlobalResult(config.getBenchmarkProperty("benchmark.name"), Integer.parseInt(config.getBenchmarkProperty("benchmark.time")));
        ArrayList<Future<LocalResult>> futures = new ArrayList<Future<LocalResult>>();

        setStage(STAGE.WARMUP);
        remoteLogger.log(Level.INFO, "Phase: WARMUP");

        for (LoadDriverIF loaddriver : loaddrivers)
        {
            futures.add(es.submit(loaddriver));
        }

        Thread.sleep(Integer.parseInt(config.getBenchmarkProperty("warmup.time")) * 1000);
        setStage(STAGE.BENCHMARK);
        remoteLogger.log(Level.INFO, "Phase: BENCHMARK");
        Thread.sleep(Integer.parseInt(config.getBenchmarkProperty("benchmark.time")) * 1000);
        setStage(STAGE.COOLDOWN);
        remoteLogger.log(Level.INFO, "Phase: COOLDOWN");
        Thread.sleep(Integer.parseInt(config.getBenchmarkProperty("cooldown.time")) * 1000);
        setStage(STAGE.FINISHED);
        remoteLogger.log(Level.INFO, "Phase: FINISHED");

        for (Future<LocalResult> future : futures)
        {
            globalResult.addLocalResult(future.get());
        }
        remoteLogger.log(Level.INFO, globalResult.getReport());
        bcLogger.log(Level.INFO, globalResult.getReport());
        return globalResult;
    }

    /**
     * Startet den LogFileServer und sammelt mit diesem die LogFiles der
     * LoadDriver ein.
     * @throws RemoteException 
     * @throws InterruptedException 
     */
    protected void collectLogFiles() throws RemoteException,
    InterruptedException
    {
        lfs.start();    // startet den Server als Background-Thread
        for (LoadDriverIF ld : loaddrivers)
        {   // alle Loaddriver werden aufgefordert, die Logfiles zu uebermitteln
            ld.sendLogFile();
        }
        while (lfs.isAlive())
        {   // wartet so lange bis der LogFileServer alle LogFiles empfangen hat
            Thread.sleep(100);
        }
    }

    /**
     * Legt die aktuelle Phase fest.
     * 
     * @param stage
     *            Phase
     * @throws RemoteException
     */
    protected void setStage(STAGE stage) throws RemoteException
    {
        for (LoadDriverIF loaddriver : loaddrivers)
        {
            loaddriver.setStage(stage);
        }
    }

    protected void shutdown() throws RemoteException, SQLException, NotBoundException {
        es.shutdown();
        close();
    }

    /**
     * Hauptprogramm zum Starten des BenchmarkControllers.
     * 
     * @param args
     *            Aufrufparameter
     */
    public static void main(String[] args)
    {
        try
        {
            BenchmarkController bController = new BenchmarkController();
            System.out.println("Der Benchmark Controller wurde gestartet.\n");
            System.out.println("Die Load Driver koennen jetzt auch gestartet werden!\n");

            do  // warte bis alle LoadDriver gestartet sind
            {
                Thread.sleep(1000);
            } while (!bController.isInitiated());

            System.out.println("Der Benchmark-Lauf wird gestartet.\n");
            bController.run(); // gibt GlobalResult zurueck - wird derzeit nicht weiter genutzt
            System.out.println("Der Benchmark-Lauf wurde ausgefuehrt.\n");            
            System.out.println("Logfiles werden am BenchmarkController gesammelt...\n");
            bController.collectLogFiles();
            System.out.println("LogFiles empfangen.\n");
            System.out.println("BenchmarkController wird beendet.\n");
            bController.shutdown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
