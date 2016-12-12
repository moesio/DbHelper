package com.seimos.android.dbhelper.test;

import java.util.Date;

import com.seimos.android.dbhelper.database.BaseEntity;

/**
	 * @author moesio @ gmail.com
	 * @date Dec 10, 2016 10:28:13 PM
	 */
public class Something extends BaseEntity {

	Long id;
	String name;
	Integer integer;
	Boolean bool;
	Date date;
	Double doub;

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

	public Integer getInteger() {
		return integer;
	}

	public Something setInteger(Integer integer) {
		this.integer = integer;
		return this;
	}

	public Boolean getBool() {
		return bool;
	}

	public Something setBool(Boolean bool) {
		this.bool = bool;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public Something setDate(Date date) {
		this.date = date;
		return this;
	}

	public Double getDoub() {
		return doub;
	}

	public Something setDoub(Double doub) {
		this.doub = doub;
		return this;
	}

}