package framework;

import java.sql.SQLException;

public interface Dao<T> {
	void create(T t) throws SQLException;
	T read(long id)throws SQLException;
	void update(T t)throws SQLException;
	void delete(T t)throws SQLException;
}
