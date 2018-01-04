package de.whs.dbi.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;

/**
 * RemoteLoggerIF bescheibt die Schnittstelle eines Remote Loggers.
 */
public interface RemoteLoggerIF extends Remote
{
    /**
     * Loggt eine Nachricht mithilfe des RemoteLoggers.
     * 
     * @param lvl
     *            Level
     * @param msg
     *            String
     * @throws RemoteException
     */
    public void log(Level lvl, String msg) throws RemoteException;
}
