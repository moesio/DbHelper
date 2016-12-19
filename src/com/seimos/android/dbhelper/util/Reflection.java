package com.seimos.android.dbhelper.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;

import com.seimos.android.dbhelper.criterion.BaseEntity;
import com.seimos.android.dbhelper.exception.ReflectionException;
import com.seimos.android.dbhelper.persistence.Id;
import com.seimos.android.dbhelper.persistence.Temporal;

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

	/* (non-Javadoc)
	 * @see java.beans.Introspector#decapitalize(java.lang.String)
	 */
	private static String capitalize(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		char[] chars = name.toCharArray();
		if (name.length() > 1 && !(Character.isLowerCase(chars[0]) && Character.isLowerCase(chars[1]))) {
			return name;
		}
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public static String getGetter(Field field) {
		return "get" + capitalize(field.getName());
	}

	public static String getGetter(String property) {
		return "get" + capitalize(property);
	}

	public static String getSetter(String property) {
		return "set" + capitalize(property);
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

	//	private static boolean isCollection(Class<?> clazz, String attributePath) {
	//		try {
	//			Field field = clazz.getDeclaredField(attributePath);
	//			if (field.getType().isAssignableFrom(List.class)) {
	//				return true;
	//			}
	//		} catch (SecurityException e) {
	//		} catch (NoSuchFieldException e) {
	//		}
	//		return false;
	//	}

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

		Field field;
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			if ((clazz.getEnclosingClass() != null && field.getType().equals(clazz.getEnclosingClass())) || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			fieldList.add(field);
		}
		Field[] ownFields = new Field[fieldList.size()];
		return fieldList.toArray(ownFields);
	}

	public static void setValue(Object object, String columnName, Object value) {
		Class<? extends Object> clazz = object.getClass();
		try {
			Method method = clazz.getMethod(getSetter(columnName), value.getClass());
			method.invoke(object, value);
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Field field;
			try {
				field = clazz.getDeclaredField(columnName);
				field.setAccessible(true);
				field.set(object, value);
			} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e1) {
				throw new IllegalArgumentException("Cannot set value to field " + columnName + " in class " + clazz);
			}
		}

	}

	public static String getStringValue(Object value) {
		if (value != null) {
			// TODO Create Temporal annotation for choose format
			return (value.getClass() == Date.class) ? ((String) DateFormat.format("yyyy-MM-dd", (Date) value)) : (value.toString());
		} else {
			return "";
		}
	}
	
	public static SimpleDateFormat getDateFormat(Field field) {
		String dateFormat = null;
		if (field.isAnnotationPresent(Temporal.class)) {
			Temporal temporal = field.getAnnotation(Temporal.class);
			switch (temporal.value()) {
			case DATE:
				dateFormat = "yyyy-MM-dd";
				break;
			case TIME:
				dateFormat = "HH:mm:ss";
				break;
			case TIMESTAMP:
				dateFormat = "yyyy-MM-dd HH:mm:ss";
				break;
			}
		} else {
			dateFormat = "yyyy-MM-dd";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
		return simpleDateFormat;
	}
	
}
