package com.seimos.android.dbhelper.criterion.test;

import java.util.Date;

import com.seimos.android.dbhelper.criterion.BaseEntity;
import com.seimos.android.dbhelper.persistence.Id;
import com.seimos.android.dbhelper.persistence.Temporal;
import com.seimos.android.dbhelper.persistence.TemporalType;

/**
 * @author moesio @ gmail.com
 * @date Dec 10, 2016 10:28:13 PM
 */
public class Something extends BaseEntity {

	public static final String[] TABLE_CREATION_QUERY = new String[] { "CREATE TABLE something (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, aInteger INTEGER, aBoolean BOOLEAN, aDate TIMESTAMP, dateOnly TIMESTAMP, timeOnly TIMESTAMP, fullDateTime TIMESTAMP, aDouble REAL)" };
	public static final String[] COLUMNS = new String[] { "id", "name", "aInteger", "aBoolean", "aDate", "aDouble", "dateOnly", "timeOnly", "fullDateTime" };
	@Id
	Long id;
	String name;
	Integer aInteger;
	Boolean aBoolean;
	Date aDate;
	Double aDouble;
	@Temporal(TemporalType.DATE)
	Date dateOnly;
	@Temporal(TemporalType.TIME)
	Date timeOnly;
	@Temporal(TemporalType.TIMESTAMP)
	Date fullDateTime;

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