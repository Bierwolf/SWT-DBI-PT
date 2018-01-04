package de.whs.dbi.statistics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Aggregiertes Resultobjekt, welches lokal vom Loaddriver berechnet wird.
 *
 */
public class LocalResult implements Serializable
{
    private static final long serialVersionUID = 1L;

    protected Map<String, TXResult> txToResults;
    protected String loadDriverName;
    protected int benchmarkDuration;

    public LocalResult(String loadDriverName, int benchmarkDuration)
    {
        this.loadDriverName = loadDriverName;
        this.benchmarkDuration = benchmarkDuration;
        txToResults = new HashMap<String, TXResult>();
    }

    public void addResult(String txName, long duration, boolean successful)
    {
        if (!txToResults.containsKey(txName))
        {
            txToResults.put(txName, new TXResult());
        }
        txToResults.get(txName).addResult(duration, successful);
    }

    public Map<String, TXResult> getTxToResults()
    {
        return txToResults;
    }

    public String getLoadDriverName()
    {
        return loadDriverName;
    }

    public void setLoadDriverName(String loadDriverName)
    {
        this.loadDriverName = loadDriverName;
    }

    public String getReport()
    {
        String report = "";
        report += "Name: " + loadDriverName + "\n";
        Set<Entry<String, TXResult>> entrySet = txToResults.entrySet();
        for (Entry<String, TXResult> entry : entrySet)
        {
            report += "\tTransaction: " + entry.getKey() + "\n";
            report += "\t\t Erfolgreich: " + entry.getValue().getSuccessfulTransactions() + "\n";
            report += "\t\t Fehlgeschlagen: " + entry.getValue().getFailedTransactions() + "\n";
            float duration = (float)entry.getValue().getDuration();
            int successful = entry.getValue().getSuccessfulTransactions();
            String averageDuration = (successful > 0)?(String.format("%.2f", duration/successful) + " ms"):"FEHLER!!!";
            report += "\t\t Durchschnittsdauer erfolgreicher TXs: " + averageDuration + "\n";
            report += "\n";
        }
        return report;
    }

}
