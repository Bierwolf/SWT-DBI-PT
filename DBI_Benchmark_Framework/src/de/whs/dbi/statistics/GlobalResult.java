package de.whs.dbi.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregiertes Resultobjekt, welches global vom BenchmarkController berechnet
 * wird und die LocalResults aller Loaddriver zusammenfasst.
 *
 */
public class GlobalResult implements Serializable
{
    private static final long serialVersionUID = 1L;

    protected String benchmarkName;
    protected int benchmarkDuration;
    protected List<LocalResult> localResults;

    public GlobalResult(String benchmarkName, int benchmarkDuration)
    {
        this.benchmarkName = benchmarkName;
        this.benchmarkDuration = benchmarkDuration;
        localResults = new ArrayList<LocalResult>();
    }

    public void addLocalResult(LocalResult result)
    {
        localResults.add(result);
    }

    public String getBenchmarkName()
    {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName)
    {
        this.benchmarkName = benchmarkName;
    }

    public List<LocalResult> getLocalResults()
    {
        return localResults;
    }

    public int getBenchmarkDuration()
    {
        return benchmarkDuration;
    }

    public String getMinimalReport()
    {
        int totalSuccess = 0;
        int totalFailed = 0;
        String report = "";
        report += "Name: " + benchmarkName + "\n";
        for (LocalResult localResult : localResults)
        {
            for (TXResult r : localResult.getTxToResults().values())
            {
                totalSuccess += r.getSuccessfulTransactions();
                totalFailed += r.getFailedTransactions();
            }
        }
        report += "\t Erfolgreich: " + totalSuccess + "\n";
        report += "\t Fehlgeschlagen: " + totalFailed + "\n";
        report += "\t Durchsatz erfolgreicher TX/s: " + totalSuccess / benchmarkDuration + "\n\n";
        return report;
    }

    public String getReport()
    {
        String report = getMinimalReport();
        for (LocalResult localResult : localResults)
        {
            report += localResult.getReport();
        }
        return report;
    }
}
