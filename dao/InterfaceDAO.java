package dao;

import java.util.List;


public interface InterfaceDAO<T> {
	void insert(T t);
	void delete(int id);
	void update(T t);
	List<T> getAll();
	T getById(int id);
}
