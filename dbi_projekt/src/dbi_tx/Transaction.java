package dbi_tx;

import java.sql.*;
/**
 * Enthaelt Funktionen, um eine Last auf der Benchmark-DB eine Last zu simulieren und 10 Minuten lang die Transactions/second misst.
 * Die Last wird threaded simuliert.*/
public class Transaction {
	
	/** Ruft Kontostand eines Accounts ab
	 * @param accid Die Account-ID des gewuenschten Accounts, dessen Stand aberufen werden soll
	 * @return Der Kontostand des ausgewaehlten Accounts*/
	static int KontostandTX (int accid)
	{
		Connection conn = null;
		Statement stmt = null;
		
		String query = "SELECT balance "
				+ "FROM accounts as a "
				+ "WHERE a.accid = accid;" ;
		int kontostand = 0;
		
		return kontostand;
	}
	
	/**
	 * 
	 * @param accid Account-ID des ausgewaehlten Accounts
	 * @param tellerid ID des Geldautomaten, an dem die TX durchgefuehrt wird
	 * @param branchid ID der Zweigstelle der Bank
	 * @param delta Einzahlungsbetrag
	 * @return Der ermittelte neue Kontostand
	 */
	static int EinzahlungTX (int accid, int tellerid, int branchid, int delta)
	{
		int kontostand = 0;
		String query = "UPDATE branches as b, accounts as a, tellers as t,"
				+ "SET b.balance +=" + delta + ", a.balance +=" + delta + ", t.balance +=" + delta + ","
				+ "WHERE b.branchid = a.branchid AND b.branchid = t.branchid AND" + accid  + "= a.accid;" ;
		return kontostand;
	}
	
	/**
	 * Gibt die Anzahl der durchgefuehrten Einzahlungen mit genau dem Betrag delta zurueck
	 * @param delta Einzahlungsbetrag, der abgefragt werden soll
	 * @return Anzahl der durchgefuehrten Einzahlungen in Hoehe von delta
	 */
	static int AnalyseTX (int delta)
	{
		int anzahl = 0;
		String query = "SELECT count(*)"
				+ "FROM history as h"
				+ "WHERE h.delta = " + delta + ";" ;
				
		return anzahl;
	}
	
	/**
	 * Hilfsfunktion
	 * @return
	 */
	int Einzahlungsbetrag()
	{
		return (int) (Math.random()*10000 +1);
	}
	
	public static void main(String[] args) {
		
	}
}
