import java.sql.*;
import java.util.Scanner;

public class mariadb 
{
	public static void main (String args[]) 
	{
		try 
		{
			Scanner sc = new Scanner(System.in);
			Connection conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/test", "dbi", "dbi_pass");
			Statement stmt = conn.createStatement();
			System.out.println("Bitte ProduktID angeben");
			String pid = sc.nextLine();
			sc.close();
			ResultSet rs = stmt.executeQuery
					(
					"select distinct aid, sum(dollars) as sum " +
					"from orders " +
					"where pid = " + "'" + pid + "'" +
					" group by aid " +
					"order by sum desc;"
					);
			while(rs.next()) 
			{
				System.out.println(rs.getString("aid") + " " + rs.getString("sum") );
			}
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
