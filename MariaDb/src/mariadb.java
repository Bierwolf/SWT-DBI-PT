import java.sql.*;

public class mariadb {
	public static void main (String args[]) 
	{
		try 
		{
			Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/test", "dbi", "dbi_pass");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery
					(
					"select distinct ordno, pid " +
					"from orders " +
					"where qty >= 1000"
					);
			while(rs.next()) 
			{
				System.out.println(rs.getString("ordno") + " " + rs.getString("pid") );
			}
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
