package framework;

import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractDao<T extends Identifizierbar> implements Dao<T> {

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
		if(cache.containsKey(id)) return cache.get(id);
		T result = doRead(id);
		cache.put(id, result);
		return result;
	}

	protected abstract T doRead(long id) throws SQLException;

	@Override
	public void update(T t) throws SQLException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(T t) throws SQLException{
		if(cache.containsValue(t)) cache.remove(t.getId());
		doDelete(t);
		
	}

	protected abstract void doDelete(T t) throws SQLException;
	
}
