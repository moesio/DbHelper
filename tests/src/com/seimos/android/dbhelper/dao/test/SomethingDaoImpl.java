package com.seimos.android.dbhelper.dao.test;

import android.content.Context;

import com.seimos.android.dbhelper.criterion.test.Something;
import com.seimos.android.dbhelper.dao.GenericDaoImpl;

/**
 * @author moesio @ gmail.com
 * @date Dec 16, 2016 6:22:53 PM
 */
public class SomethingDaoImpl extends GenericDaoImpl<Something> implements SomethingDao {

	public SomethingDaoImpl(Context context) {
		super(context);
	}
}
