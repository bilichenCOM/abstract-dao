package com.mateacademy.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mateacademy.dao.GenericDao;
import com.mateacademy.db.QueryExecutor;
import com.mateacademy.db.TableNameResolver;
import com.mateacademy.utils.ReflectionUtils;

public class GenericDaoImpl<T, ID> implements GenericDao<T, ID> {

	@Override
	public T save(T t) {
		String tableName = TableNameResolver.getTableNameFor(t);
		Map<String, Object> properties = ReflectionUtils.retrieveFieldsMap(t);
		QueryExecutor.createTableIfNotExists(tableName, properties);
		QueryExecutor.insertIntoTable(tableName, properties);
		return t;
	}

	@Override
	public T get(Class<T> clazz, ID id) {
		String tableName = TableNameResolver.getTableNameFor(clazz);
		Set<String> fieldsSet = ReflectionUtils.retrieveFieldsSet(clazz);
		Map<String, Object> properties = QueryExecutor.retrieve(tableName, fieldsSet, id);
		return ReflectionUtils.createNewInstanceWithProperties(clazz, properties);
	}

	@Override
	public T update(T t) {
		return null;
	}

	@Override
	public void delete(Class<T> clazz, ID id) {
	}

	@Override
	public List<T> getAll(Class<T> clazz) {
		return null;
	}
}