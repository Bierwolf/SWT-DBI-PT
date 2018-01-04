package benchmark.mybenchmark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.whs.dbi.transactions.WeightedTransaction;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.ParameterGenerator;

public class SelectTx extends WeightedTransaction
{
    private int n;

	public SelectTx(Connection con, int weight, Configuration config) throws Exception
	{
		super(con, weight, config);
        this.n = Integer.parseInt(config.getBenchmarkProperty("user.n"));
	}

	@Override
	public void run() throws SQLException
	{
		Statement stmt = connection.createStatement();
        int accid = ParameterGenerator.generateRandomInt(1, n*100000);
		ResultSet rs = stmt.executeQuery("SELECT balance FROM accounts WHERE accid=" + accid);
		if (rs.next())
		{
			rs.getInt(1);
		}
		rs.close();
		stmt.close();
	}
}
