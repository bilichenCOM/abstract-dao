package com.mateacademy.db;

import java.util.Optional;

import com.mateacademy.dim.SqlTypeAnalog;

public class SqlDataTypeResolver {

	public static Optional<String> resolveSqlTypeFor(Object obj) {
		String sqlDataType = null;
		if (obj instanceof Byte) {
			sqlDataType = SqlTypeAnalog.BYTE.getSqlType();
		} else if (obj instanceof Short) {
			sqlDataType = SqlTypeAnalog.SHORT.getSqlType();
		} else if (obj instanceof Character) {
			sqlDataType = SqlTypeAnalog.CHARACTER.getSqlType();
		} else if (obj instanceof Integer) {
			sqlDataType = SqlTypeAnalog.INTEGER.getSqlType();
		} else if (obj instanceof Float) {
			sqlDataType = SqlTypeAnalog.FLOAT.getSqlType();
		} else if (obj instanceof Double) {
			sqlDataType = SqlTypeAnalog.DOUBLE.getSqlType();
		} else if (obj instanceof Long) {
			sqlDataType = SqlTypeAnalog.LONG.getSqlType();
		} else if (obj instanceof String) {
			sqlDataType = SqlTypeAnalog.STRING.getSqlType();
		} else {
			return Optional.empty();
		}

		return Optional.of(sqlDataType);
	}

	public static boolean isQuotesNeeded(Object obj) {
		String sqlDataType = resolveSqlTypeFor(obj).orElseThrow(
				() -> new IllegalArgumentException("unexpected datatype: " + obj.getClass().getTypeName()));

		if (sqlDataType.equals(SqlTypeAnalog.STRING.getSqlType())) {
			return true;
		} else if (sqlDataType.equals(SqlTypeAnalog.CHARACTER.getSqlType())) {
			return true;
		}

		return false;
	}
}