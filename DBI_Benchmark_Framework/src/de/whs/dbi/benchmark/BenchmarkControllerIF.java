package de.whs.dbi.benchmark;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.whs.dbi.loaddriver.LoadDriverIF;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.RemoteLoggerIF;

/**
 * BenchmarkIF beschreibt die Schnittstelle eines Benchmark Controllers.
 */
public interface BenchmarkControllerIF extends Remote
{
    /**
     * Aufzaehlungstyp fuer die moeglichen Phasen des Benchmarks
     */
    public static enum STAGE
    {
        INIT, READY, WARMUP, BENCHMARK, COOLDOWN, FINISHED
    }

    /**
     * Registriert einen LoadDriver im Benchmark und gibt eine eindeutige ID
     * fuer den LoadDriver zurueck.
     * 
     * @param loadDriver zu registrierendes LoadDriver-Objekt
     * @return ID des LoadDrivers
     * @throws Exception
     */
    public int registerLoadDriver(LoadDriverIF loadDriver) throws Exception;

    /**
     * Stellt die genutzte Konfiguration zur Verfuegung.
     * 
     * @return Configuration
     * @throws RemoteException
     */
    public Configuration getConfiguration() throws RemoteException;

    /**
     * Stellt den RemoteLogger zur Verfuegung.
     * 
     * @return RemoteLoggerIF
     * @throws RemoteException
     */
    public RemoteLoggerIF getRemoteLogger() throws RemoteException;
}
