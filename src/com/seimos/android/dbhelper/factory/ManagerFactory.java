package com.seimos.android.dbhelper.factory;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import android.content.Context;

import com.seimos.android.dbhelper.manager.GenericManager;

/**
 * @author moesio @ gmail.com
 * @date Nov 16, 2016 8:23:15 PM
 */
public class ManagerFactory {

	private static HashMap<Class<?>, GenericManager<?>> managers = new HashMap<Class<?>, GenericManager<?>>();

	@SuppressWarnings("unchecked")
	public static <T extends GenericManager<?>> T getManager(Context context, Class<T> clazz) {

		GenericManager<?> manager = managers.get(clazz);

		if (manager == null) {
			try {
				Constructor<T> constructor = clazz.getConstructor(Context.class);
				try {
					T newInstance = constructor.newInstance(context);
					managers.put(clazz, newInstance);
				} catch (Exception e) {
					throw new RuntimeException("Can not instantiate class " + clazz.getName());
				}
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("Manager has not constructor Manager(Context)");
			}
		}
		
		return (T) managers.get(clazz);
	}
}
