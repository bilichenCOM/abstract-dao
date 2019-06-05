package com.mateacademy.dim;

public enum SqlTypeAnalog {

	BYTE("INT"),
	SHORT("INT"),
	CHARACTER("CHAR(1)"),
	BOOLEAN("BOOLEAN"),
	INTEGER("INT"),
	FLOAT("FLOAT(8)"),
	DOUBLE("REAL"),
	LONG("BIGINT"),

	STRING("VARCHAR");

	private String sqlType;

	private SqlTypeAnalog(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String toString() {
		return sqlType;
	}
}
