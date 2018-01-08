package benchmark.mybenchmark;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import de.whs.dbi.transactions.WeightedTransaction;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.ParameterGenerator;

public class AnalyseTX extends WeightedTransaction
{
    public AnalyseTX(Connection con, int weight, Configuration config) throws Exception
    {
        super(con, weight, config);
    }

    public void run() throws SQLException
    {
        Statement stmt = connection.createStatement();
        int delta = ParameterGenerator.generateRandomInt(0, 10000);
        stmt.executeUpdate("SELECT count(*) FROM history WHERE delta = " + delta);
        stmt.close();  
    }
}
