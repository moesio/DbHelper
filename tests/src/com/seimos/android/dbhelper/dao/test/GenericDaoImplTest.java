package com.seimos.android.dbhelper.dao.test;

import org.junit.BeforeClass;
import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.dao.GenericDaoImpl;
import com.seimos.android.dbhelper.database.DatabaseHelper;
import com.seimos.android.dbhelper.database.DatabaseHelper.Patch;
import com.seimos.android.dbhelper.test.Something;

/**
 * @author moesio @ gmail.com
 * @date Dec 12, 2016 10:05:50 PM
 */
public class GenericDaoImplTest extends AndroidTestCase {

	private GenericDaoImpl<Something> genericDaoImpl;

	@BeforeClass
	public void init() {
		genericDaoImpl = new GenericDaoImpl<Something>(getContext());
	}

	@Test
	public final void testGenericDaoImpl() {
	}

	@Test
	public final void testCreate() {
	}

	@Test
	public final void testRetrieve() {
	}

	@Test
	public final void testList() {
	}

	@Test
	public final void testFilter() {
	}

	@Test
	public final void testUpdate() {
	}

	@Test
	public final void testDelete() {
	}

}
