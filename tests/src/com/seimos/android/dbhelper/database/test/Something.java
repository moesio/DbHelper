package com.seimos.android.dbhelper.database.test;

import java.util.Date;

import com.seimos.android.dbhelper.database.BaseEntity;

/**
 * @author moesio @ gmail.com
 * @date Dec 10, 2016 10:28:13 PM
 */
public class Something extends BaseEntity {

	public static final String[] TABLE_CREATION_QUERY = new String[] { "CREATE TABLE something (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, ainteger INTEGER, aboolean BOOLEAN, adate TIMESTAMP, adouble REAL)" };
	public static final String[] COLUMNS = new String[] { "id", "name", "aInteger", "aBoolean", "aDate", "aDouble" };;
	Long id;
	String name;
	Integer aInteger;
	Boolean aBoolean;
	Date aDate;
	Double aDouble;

	@Override
	public String toString() {
		return "" + id;
	}

	public Long getId() {
		return id;
	}

	public Something setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Something setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getaInteger() {
		return aInteger;
	}

	public Something setaInteger(Integer aInteger) {
		this.aInteger = aInteger;
		return this;
	}

	public Boolean getaBoolean() {
		return aBoolean;
	}

	public Something setaBoolean(Boolean aBoolean) {
		this.aBoolean = aBoolean;
		return this;
	}

	public Date getaDate() {
		return aDate;
	}

	public Something setaDate(Date aDate) {
		this.aDate = aDate;
		return this;
	}

	public Double getaDouble() {
		return aDouble;
	}

	public Something setaDouble(Double aDouble) {
		this.aDouble = aDouble;
		return this;
	}
}