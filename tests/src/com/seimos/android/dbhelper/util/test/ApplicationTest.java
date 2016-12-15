package com.seimos.android.dbhelper.util.test;

import org.junit.Test;

import android.test.AndroidTestCase;

import com.seimos.android.dbhelper.util.Application;

/**
 * @author moesio @ gmail.com
 * @date Dec 14, 2016 6:02:59 PM
 */
public class ApplicationTest extends AndroidTestCase {

	@Test
	public final void testGetName() {
		assertEquals("DBHelperTest", Application.getName(getContext()));
		try {
			Application.getName(null);
			fail("Context must be different from null");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test
	public final void testGetVersion() {
		assertEquals(1, (int) Application.getVersion(getContext()));
		try {
			Application.getVersion(null);
			fail("Context must be different from null");
		} catch (IllegalArgumentException e) {
		}
	}

}
