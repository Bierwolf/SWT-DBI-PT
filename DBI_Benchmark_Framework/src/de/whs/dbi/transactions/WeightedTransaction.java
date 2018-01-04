package de.whs.dbi.transactions;

import java.sql.Connection;

import de.whs.dbi.util.Configuration;

/**
 * Gewichtete Transaktion
 *
 */
public abstract class WeightedTransaction extends Transaction
{
	protected int weight;
	
	public WeightedTransaction(Connection con, int weight, Configuration config) throws Exception
	{
		super(con, config);
		this.weight = weight;
	}

	public int getWeight()
	{
		return weight;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}	
}
