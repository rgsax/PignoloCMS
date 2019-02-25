package it.unical.pignolo.dao;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class DAO<T> {
	protected Connection connection = null; 
	
	public DAO(Connection c) {
		this.connection = c; 
	}
	
    public abstract Optional<T> get(Long id);
    public abstract List<T> getAll();
    public abstract void save(T t);
    public abstract void update(T t);  
    public abstract void delete(T t);
}
