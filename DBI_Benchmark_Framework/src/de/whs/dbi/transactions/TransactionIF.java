package de.whs.dbi.transactions;

public interface TransactionIF
{
    public void execute() throws Exception;                 // fuehrt die TX aus 
    public void setInputData(Object input);                 // stellt Input-Parameter zur Verfuegung 
    public Object getOutputData();                          // gibt den Output-Parameter einer TX zurueck
}
