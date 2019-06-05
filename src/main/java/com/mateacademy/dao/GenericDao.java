package com.mateacademy.dao;

import java.util.List;

public interface GenericDao<T, ID> {

	T save(T t);

	T get(Class<T> clazz, ID id);

	T update(T t);

	void delete(Class<T> clazz, ID id);

	List<T> getAll(Class<T> clazz);
}
