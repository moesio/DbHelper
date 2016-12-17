package com.seimos.android.dbhelper.factory.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.factory.ManagerFactory;
import com.seimos.android.dbhelper.manager.test.SomethingManagerImpl;

/**
 * @author moesio @ gmail.com
 * @date Dec 16, 2016 6:13:54 PM
 */
public class ManagerFactoryTest extends AndroidTestCase {

	@Test
	public final void testGetManager() {
		assertEquals(new SomethingManagerImpl(getContext()).getClass(), ManagerFactory.getManager(getContext(), SomethingManagerImpl.class).getClass());
	}

}
