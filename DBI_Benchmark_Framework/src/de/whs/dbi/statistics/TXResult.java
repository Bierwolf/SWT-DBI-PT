package de.whs.dbi.statistics;

import java.io.Serializable;

/**
 * Aggregierte Messergebnisse einer einzelnen Transaktion
 *
 */
public class TXResult implements Serializable
{
    private static final long serialVersionUID = 1L;

    protected int successfulTransactions; // Summe der erfolgreichen Transactions
    protected int failedTransactions;     // Summe der nicht erfolgreichen TXs
    protected long duration;              // Gesamtdauer aller erfolgreichen TXs

    public TXResult()
    {
        duration = 0;
        failedTransactions = 0;
        successfulTransactions = 0;
    }

    /**
     * Fuegt eine neue Messung der bisherigen Statistik hinzu.
     * 
     * @param duration
     *            Dauer einer einzelnen TX
     * @param successful
     *            True, wenn erfolgreich
     */
    public void addResult(long duration, boolean successful)
    {
        if (successful)
        {
            successfulTransactions++;
            this.duration += duration;      // nur Dauern erfolgreicher TXs aufsummieren  
        }
        else
        {
            failedTransactions++;
        }
    }

    public int getSuccessfulTransactions()
    {
        return successfulTransactions;
    }

    public int getFailedTransactions()
    {
        return failedTransactions;
    }

    public long getDuration()
    {
        return duration;
    }
}
