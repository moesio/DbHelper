package com.seimos.android.dbhelper.dao.test;

import android.content.Context;

import com.seimos.android.dbhelper.dao.GenericDaoImpl;
import com.seimos.android.dbhelper.database.test.Something;

/**
 * @author moesio @ gmail.com
 * @date Dec 16, 2016 6:22:53 PM
 */
public class SomethingDaoImpl extends GenericDaoImpl<Something> implements SomethingDao {

	public SomethingDaoImpl(Context context) {
		super(context);
	}
}
