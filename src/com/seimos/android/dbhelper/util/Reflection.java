package com.seimos.android.dbhelper.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

import com.seimos.android.dbhelper.annotation.Id;
import com.seimos.android.dbhelper.database.BaseEntity;

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

	public static Object invoke(Object entity, String property) {
		Class<?> clazz = entity.getClass();

		Object invocation = null;
		try {
			Method method = clazz.getMethod(Reflection.getGetter(property));
			invocation = method.invoke(entity);
		} catch (Exception e) {
			// em caso de não existir o método. Praticamente impossível, já que
			// estou trazendo um método preexistente
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
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Id.class)) {
				return field;
			}
		}
		return null;
	}
}
