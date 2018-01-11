package benchmark.mybenchmark;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.whs.dbi.transactions.WeightedTransaction;
import de.whs.dbi.util.Configuration;
import de.whs.dbi.util.ParameterGenerator;

public class EinzahlungTX extends WeightedTransaction
{
    private int n;

    public EinzahlungTX(Connection con, int weight, Configuration config) throws Exception
    {
        super(con, weight, config);
        this.n = Integer.parseInt(config.getBenchmarkProperty("user.n"));
    }

    @Override
    public void run() throws SQLException
    {
        Statement stmt = connection.createStatement();
        int accid = ParameterGenerator.generateRandomInt(1, n*100000);
        int tellerid = ParameterGenerator.generateRandomInt(1, n*10);
        int branchid = ParameterGenerator.generateRandomInt(1, n);
        int delta = ParameterGenerator.generateRandomInt(0, 10000);

//        ResultSet rs = stmt.executeQuery("UPDATE branches as b, accounts as a, tellers as t"
//                + " SET b.balance = b.balance + "+ delta + ", a.balance = a.balance + " + delta + ", t.balance = t.balance + " + delta
//                + " WHERE b.branchid = a.branchid AND b.branchid = t.branchid AND " + accid  + "= a.accid");
        
        ResultSet rs = stmt.executeQuery("UPDATE accounts"
                    + " SET balance = balance + "+ delta
                    + " WHERE accid =" + accid + ";");
        
                rs = stmt.executeQuery("UPDATE tellers"
                    + " SET balance = balance + "+ delta
                    + " WHERE tellerid =" + tellerid + ";");
                                
                rs = stmt.executeQuery("UPDATE branches"
                    + " SET balance = balance + "+ delta
                    + " WHERE branchid =" + branchid + ";");
                        
        rs = stmt.executeQuery("INSERT into history VALUES(" + accid + "," + tellerid + "," + delta + "," + branchid 
                + ", (SELECT balance FROM accounts WHERE accid =" + accid + "), 'Random Transaction');");
        
        
        if (rs.next())
        {
            rs.getInt(1);
        }
        rs.close();
        stmt.close();
    }
}
