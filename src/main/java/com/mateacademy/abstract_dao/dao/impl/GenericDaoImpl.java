package com.mateacademy.abstract_dao.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.mateacademy.abstract_dao.dao.GenericDao;
import com.mateacademy.abstract_dao.db.QueryExecutor;
import com.mateacademy.abstract_dao.db.TableNameResolver;
import com.mateacademy.abstract_dao.utils.ReflectionUtils;

public class GenericDaoImpl<T, ID> implements GenericDao<T, ID> {

	private Class<T> clazz;

	public GenericDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public T save(T t) {
		String tableName = TableNameResolver.getTableNameFor(t);
		Map<String, Object> properties = ReflectionUtils.retrieveFieldsMap(t);
		QueryExecutor.createTableIfNotExists(tableName, properties);
		QueryExecutor.insertIntoTable(tableName, properties);
		return t;
	}

	@Override
	public T get(ID id) {
		String tableName = TableNameResolver.getTableNameFor(clazz);
		Set<String> fieldsSet = ReflectionUtils.retrieveFieldsSet(clazz);
		Map<String, Object> properties = QueryExecutor.retrieve(tableName, fieldsSet, id);
		return ReflectionUtils.createNewInstanceWithProperties(clazz, properties);
	}

	@Override
	public T update(T t) {
		String tableName = TableNameResolver.getTableNameFor(t);
		Map<String, Object> properties = ReflectionUtils.retrieveFieldsMap(t);
		QueryExecutor.update(tableName, properties);
		return t;
	}

	@Override
	public void delete(ID id) {
		String tableName = TableNameResolver.getTableNameFor(clazz);
		QueryExecutor.delete(tableName, id);
	}

	@Override
	public List<T> getAll() {
		String tableName = TableNameResolver.getTableNameFor(clazz);
		Set<String> columns = ReflectionUtils.retrieveFieldsSet(clazz);
		List<Map<String, Object>> properties = QueryExecutor.retrieveAll(tableName, columns);
		return properties.stream()
				.map(m -> ReflectionUtils.createNewInstanceWithProperties(clazz, m))
				.collect(Collectors.toList());
	}
}