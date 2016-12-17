package com.seimos.android.dbhelper.manager.test;

import android.content.Context;

import com.seimos.android.dbhelper.dao.GenericDao;
import com.seimos.android.dbhelper.dao.test.SomethingDao;
import com.seimos.android.dbhelper.dao.test.SomethingDaoImpl;
import com.seimos.android.dbhelper.database.test.Something;
import com.seimos.android.dbhelper.manager.GenericManagerImpl;

/**
 * @author moesio @ gmail.com
 * @date Dec 16, 2016 6:26:02 PM
 */
public class SomethingManagerImpl extends GenericManagerImpl<Something, SomethingDao> implements SomethingManager {

	private SomethingDaoImpl dao;

	public SomethingManagerImpl(Context context) {
		super(context);
		this.dao = new SomethingDaoImpl(context);
	}

	@Override
	public GenericDao<Something> getDao() {
		return dao;
	}

}
