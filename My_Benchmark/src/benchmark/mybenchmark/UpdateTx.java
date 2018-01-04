package benchmark.mybenchmark;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.whs.dbi.transactions.WeightedTransaction;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.ParameterGenerator;

public class UpdateTx extends WeightedTransaction
{
    private int n;

    public UpdateTx(Connection con, int weight, Configuration config) throws Exception
    {
        super(con, weight, config);
        this.n = Integer.parseInt(config.getBenchmarkProperty("user.n"));
    }

    public void run() throws SQLException
    {
        Statement stmt = connection.createStatement();
        int newBalance = ParameterGenerator.generateRandomInt(0, 100);
        int accid = ParameterGenerator.generateRandomInt(1, n*100000);
        stmt.executeUpdate("UPDATE accounts SET balance=" + newBalance + " WHERE accid=" + accid);
        stmt.close();  
    }
}
