package de.whs.dbi.loaddriver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import de.whs.dbi.benchmark.BenchmarkControllerIF.STAGE;
import de.whs.dbi.statistics.LocalResult;

/**
 * LoadDriverIF beschreibt die Schnittstelle eines Load Drivers.
 */
public interface LoadDriverIF extends Remote, Callable<LocalResult>
{
    /**
     * De-Registriert den LoadDriver.
     * 
     * @throws RemoteException
     * @throws SQLException
     */
    public void close() throws RemoteException, SQLException;

    /**
     * Gibt die aktuelle Phase zurueck.
     * 
     * @return Phase
     * @throws RemoteException
     */
    public STAGE getStage() throws RemoteException;

    /**
     * Legt die aktuelle Phase fest.
     * 
     * @param stage
     *            Phase
     * @throws RemoteException
     */
    public void setStage(STAGE stage) throws RemoteException;

    /**
     * Initiiert die Uebertragung des lokalen Log File an den Benchmark Controller.
     * 
     * @throws RemoteException
     */
    public void sendLogFile() throws RemoteException;
    
    // TODO Methode, um Zwischenresultat abzufragen=???
}
