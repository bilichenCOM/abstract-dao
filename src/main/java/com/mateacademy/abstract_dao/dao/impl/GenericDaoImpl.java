package com.mateacademy.abstract_dao.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.mateacademy.abstract_dao.dao.GenericDao;
import com.mateacademy.abstract_dao.db.ColumnNameResolver;
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
		properties = ColumnNameResolver.resolveColumnNamesFor(properties, clazz);
		QueryExecutor.createTableIfNotExists(tableName, properties);
		QueryExecutor.insertIntoTable(tableName, properties);
		return t;
	}

	@Override
	public T get(ID id) {
		String tableName = TableNameResolver.getTableNameFor(clazz);
		Set<String> fieldSet = ReflectionUtils.retrieveFieldsSet(clazz);
		fieldSet = ColumnNameResolver.resolveColumnNamesFor(fieldSet, clazz);
		Map<String, Object> properties = QueryExecutor.retrieve(tableName, fieldSet, id);
		properties = ColumnNameResolver.resolveFieldNamesFor(properties, clazz);
		return ReflectionUtils.createNewInstanceWithProperties(clazz, properties);
	}

	@Override
	public T update(T t) {
		String tableName = TableNameResolver.getTableNameFor(t);
		Map<String, Object> properties = ReflectionUtils.retrieveFieldsMap(t);
		properties = ColumnNameResolver.resolveColumnNamesFor(properties, clazz);
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
		columns = ColumnNameResolver.resolveColumnNamesFor(columns, clazz);
		List<Map<String, Object>> properties = QueryExecutor.retrieveAll(tableName, columns);
		return properties.stream()
				.map(m -> ColumnNameResolver.resolveColumnNamesFor(m, clazz))
				.map(m -> ReflectionUtils.createNewInstanceWithProperties(clazz, m))
				.collect(Collectors.toList());
	}
}