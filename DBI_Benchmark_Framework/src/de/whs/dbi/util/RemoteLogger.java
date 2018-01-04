package de.whs.dbi.util;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ein Remote Logger empfaengt und verarbeitet Protokollmeldungen ueber RMI.
 */
public class RemoteLogger extends UnicastRemoteObject implements RemoteLoggerIF
{

    private static final long serialVersionUID = 887245597519002664L;

    protected Logger logger;

    /**
     * Der Konstruktor initialisiert den Remote-Logger.
     * 
     * @param level
     *            Log-Level
     * @throws SecurityException
     * @throws IOException
     */
    public RemoteLogger(Level level) throws SecurityException, IOException
    {
        logger = Logger.getLogger("de.whs.dbi");
        logger.setLevel(Level.ALL);
    }

    @Override
    public synchronized void log(Level level, String message) throws RemoteException
    {
        logger.log(level, message + "\n");
    }
}
