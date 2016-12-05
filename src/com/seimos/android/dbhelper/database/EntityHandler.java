package com.seimos.android.dbhelper.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Aug 6, 2015 7:38:45 PM
 */
public class EntityHandler {

	private Class<? extends BaseEntity> entityClass;
	private Context context;

	@SuppressWarnings("unused")
	private EntityHandler() {
	}

	public EntityHandler(Context context, Class<? extends BaseEntity> entityClass) {
		this.context = context;
		this.entityClass = entityClass;
	}

	public String getTableName() {
		return toDatabaseName(entityClass.getSimpleName());
	}

	public String[] getColumns() {
		Field[] fields = entityClass.getDeclaredFields();
		String[] columns = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			columns[i] = toDatabaseName(fields[i].getName());
		}
		return columns;
	}

	public ContentValues createContentValues(BaseEntity entity) {
		ContentValues values = new ContentValues();

		Field[] declaredFields = entity.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			Object invoke = Reflection.invoke(entity, field.getName());
			String databaseFieldName = toDatabaseName(field.getName());
			if (invoke != null) {
				if (Reflection.isEntity(invoke)) {
					Field idField = Reflection.getIdField(field.getType());
					values.put(databaseFieldName, (Integer) Reflection.invoke(invoke, idField.getName()));
				} else if (field.getType() == Enum.class) {
					throw new RuntimeException();
				} else if (field.getType() == Integer.class) {
					values.put(databaseFieldName, (Integer) invoke);
				} else if (field.getType() == Boolean.class) {
					values.put(databaseFieldName, (Boolean) invoke);
				} else if (field.getType() == Date.class) {
					values.put(databaseFieldName, Filter.getStringValue(invoke));
				} else if (field.getType() == Double.class) {
					values.put(databaseFieldName, (Double) invoke);
				} else if (field.getType() == Long.class) {
					values.put(databaseFieldName, (Long) invoke);
				} else {
					values.put(databaseFieldName, (String) invoke);
				}
			}
		}
		return values;
	}

	public String toDatabaseName(String name) {
		Locale locale = new Locale("EN_us");
		return name.replaceAll("(\\W)", "_$1").toLowerCase(locale);
	}

	public List<BaseEntity> extract(final Cursor cursor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException,
			NoSuchMethodException {
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		if (cursor.moveToFirst()) {
			do {
				list.add(createEntityFromCursor(cursor));
			} while (cursor.moveToNext());
		} else {
			list = Collections.emptyList();
		}
		return list;
	}

	@SuppressLint("SimpleDateFormat")
	public BaseEntity createEntityFromCursor(final Cursor cursor) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		BaseEntity entity = entityClass.newInstance();
		String[] columnNames = cursor.getColumnNames();
		for (String columnName : columnNames) {
			try {
				Field field = entityClass.getDeclaredField(columnName);
				Class<?> type = field.getType();
				Method method = entityClass.getMethod(Reflection.getSetter(columnName), type);

				if (type == Boolean.class) {
					method.invoke(entity, cursor.getInt(cursor.getColumnIndex(columnName)));
				} else if (type == Integer.class) {
					method.invoke(entity, cursor.getInt(cursor.getColumnIndex(columnName)));
				} else if (type == String.class) {
					method.invoke(entity, cursor.getString(cursor.getColumnIndex(columnName)));
				} else if (type == Date.class) {
					String value = cursor.getString(cursor.getColumnIndex(columnName));
					Date date = null;
					try {
						date = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					} catch (ParseException e) {
					}
					method.invoke(entity, date);
				} else if (type == Double.class) {
					method.invoke(entity, cursor.getDouble(cursor.getColumnIndex(columnName)));
				} else if (type == Long.class) {
					method.invoke(entity, cursor.getLong(cursor.getColumnIndex(columnName)));
				} else {
					// TODO Test deep associations more than one level, ie, nested association
					
					BaseEntity association = (BaseEntity) type.newInstance();
					Method methodAssociation = association.getClass().getMethod(Reflection.getSetter(Reflection.getIdField(association.getClass()).getName()), Integer.class);
					// TODO Verify when id field is not an Integer
					Integer idValue = cursor.getInt(cursor.getColumnIndex(columnName));
					methodAssociation.invoke(association, idValue);

					@SuppressWarnings("unchecked")
					EntityHandler entityHandler = new EntityHandler(context, (Class<? extends BaseEntity>) field.getType());
					SQLiteDatabase database = DatabaseUtil.openForRead(context);
					String idFieldName = Reflection.getIdField(field.getType()).getName();
					// TODO Verify when id field is not an Integer
					Cursor cursorAssociation = database.query(entityHandler.getTableName(), entityHandler.getColumns(), idFieldName + " = ?", new String[] { idValue.toString()}, null, null, idFieldName,	"1");
					if (cursorAssociation.moveToFirst()) {
						association = entityHandler.createEntityFromCursor(cursorAssociation);
					}
					database.close();

					method.invoke(entity, association);
				}
			} catch (NoSuchFieldException e) {
			}
		}
		return entity;
	}
}
