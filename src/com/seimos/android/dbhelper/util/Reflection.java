package com.seimos.android.dbhelper.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.seimos.android.dbhelper.annotation.Id;
import com.seimos.android.dbhelper.database.BaseEntity;
import com.seimos.android.dbhelper.exception.ReflectionException;

public class Reflection {

	public static boolean isEntity(Object entity) {
		return entity instanceof BaseEntity;
	}

	public static boolean isEntity(Class<?> clazz) {
		try {
			clazz.asSubclass(BaseEntity.class);
			return true;
		} catch (ClassCastException e) {
			return false;
		}
	}

	public static String getGetter(Field field) {
		String fieldName = field.getName();
		String methodName = ((field.getType() == Boolean.TYPE) ? "is" : "get") + fieldName.substring(0, 1).toUpperCase(Locale.US) + fieldName.substring(1);
		return methodName;
	}

	public static String getGetter(String property) {
		return "get" + property.substring(0, 1).toUpperCase(Locale.US) + property.substring(1);
	}

	public static String getSetter(String property) {
		return "set" + property.substring(0, 1).toUpperCase(Locale.US) + property.substring(1);
	}

	public static Object getValue(Object object, String property) {
		Class<?> clazz = object.getClass();
		Object invocation = null;
		Method method;
		try {
			method = clazz.getMethod(Reflection.getGetter(property));
			invocation = method.invoke(object);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			try {
				Field field = object.getClass().getDeclaredField(property);
				field.setAccessible(true);
				invocation = field.get(object);
			} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e1) {
				throw new ReflectionException(property + " not accessible on " + object.getClass().getName());
			}
		}
		return invocation;
	}

	public static boolean isCollection(Class<?> clazz, String attributePath) {
		try {
			Field field = clazz.getDeclaredField(attributePath);
			if (field.getType().isAssignableFrom(List.class)) {
				return true;
			}
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		}
		return false;
	}

	public static Field getIdField(Class<?> clazz) {
		Field[] fields = getInnerDeclaredFields(clazz);
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Get only declared fields. It means that if parameter <code>class</code> is an
	 * inner class, a invisble field created at runtime referencing outer class, will be not
	 * considered
	 * 
	 * @param clazz
	 * @return
	 */
	public static Field[] getInnerDeclaredFields(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		ArrayList<Field> fieldList = new ArrayList<Field>();

		for (int i = 0; i < fields.length; i++) {
			if (clazz.getEnclosingClass() != null && fields[i].getType().equals(clazz.getEnclosingClass())) {
				continue;
			}
			fieldList.add(fields[i]);
		}
		Field[] ownFields = new Field[fieldList.size()];
		return fieldList.toArray(ownFields);
	}

}
