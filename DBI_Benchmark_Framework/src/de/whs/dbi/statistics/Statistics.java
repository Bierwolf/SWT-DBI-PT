package de.whs.dbi.statistics;

/**
 * TODO noch zu formulieren!
 *
 */
public class Statistics
{
    protected static LocalResult localResult;

    public static LocalResult getLocalResult()
    {
        return localResult;
    }

    public static void addResult(String txName, long duration, boolean successful)
    {
        localResult.addResult(txName, duration, successful);
    }

    // muss am Anfang aufgerufen werden, damit Statistics initialisiert werden
    // koennen
    public static void initialize(String loadDriverName, int benchmarkDuration)
    {
        localResult = new LocalResult(loadDriverName, benchmarkDuration);
    }
}
