package framework;

import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractDao<T> implements Dao<T> {

	private HashMap<Long, T> cache = new HashMap<Long, T>();
	
	@Override
	public void create(T t) throws SQLException{
		if(cache.containsValue(t)) throw new SQLException("Objekt bereits enthalten");
		long id = doInsert(t);
		cache.put(id, t);		
	}
	
	protected abstract long doInsert(T t) throws SQLException;

	@Override
	public T read(long id) throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(T t) throws SQLException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T t) throws SQLException{
		// TODO Auto-generated method stub
		
	}	

}
