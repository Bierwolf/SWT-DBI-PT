package kundeDAO;

import framework.AbstractDao;

import java.sql.*;

public class ArtikelDAO extends AbstractDao<Artikel>{

    
    private Connection db;
    private ResultSet rs;
    
    private static ArtikelDAO instance = new ArtikelDAO();
    
    private ArtikelDAO() {
        try {
			  db = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/tests", "dbi", "dbi_pass");        	
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /* ArtikelDAO ist als Singleton implementiert */
	public static ArtikelDAO getInstance() {
    	return instance;
    }
    
    /* Es folgen die so genannten CRUD-Methoden:
     * (C)reate
     * (R)ead
     * (U)pdate
     * (D)elete
     */
    private final static String insertStatementString =
            "INSERT INTO ARTIKEL VALUES(?, ?, ?)";

    private final static String findStatementString =
            "SELECT ATNR, NAME, PREIS FROM Artikel WHERE ATNR = ?";

 
    /** Methode zum Laden eines Artikels in den Speicher aus dem Resultset */
    private Artikel load(ResultSet rs) throws SQLException {
        Long id = new Long(rs.getLong(1));
        Artikel result = (Artikel) cache.get(id);
        if (result != null) {
            return result;
        }
        String name = rs.getString(2);
        int preis = rs.getInt(3);
        result = new Artikel(id, name, preis);
        cache.put(id, result);
        return result;
    }
    private final static String updateStatementString =
            "UPDATE ARTIKEL SET NAME = ?, PREIS = ? WHERE ATNR = ?";


    
    private final static String deleteStatementString =
            "DELETE FROM ARTIKEL WHERE ATNR = ?";


	@Override
	protected long doInsert(Artikel kd) throws SQLException {
		PreparedStatement insertStatement = null;
        try {
            insertStatement = db.prepareStatement(insertStatementString);
            insertStatement.setLong(1, kd.getId());
            insertStatement.setString(2, kd.getName());
            insertStatement.setInt(3, kd.getPreis());
            insertStatement.execute();
            cache.put(kd.getId(), kd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kd.getId();
	}

	@Override
	protected Artikel doRead(long id) throws SQLException {
		PreparedStatement findStatement = null;
		Artikel result = null;
        rs = null;
        try {
            findStatement = db.prepareStatement(findStatementString);
            findStatement.setLong(1, id);
            rs = findStatement.executeQuery();
            rs.next();
            result = load(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
	}

	@Override
	protected void doUpdate(Artikel kd) throws SQLException {
		PreparedStatement updateStatement = null;
        try {
            updateStatement = db.prepareStatement(updateStatementString);
            updateStatement.setLong(3, kd.getId());
            updateStatement.setString(1, kd.getName());
            updateStatement.setInt(2, kd.getPreis());
            updateStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	@Override
	protected void doDelete(Artikel kd) throws SQLException {
		PreparedStatement deleteStatement = null;
        try {
            deleteStatement = db.prepareStatement(deleteStatementString);
            deleteStatement.setLong(1, kd.getId());
            deleteStatement.execute();
            cache.remove(kd.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
