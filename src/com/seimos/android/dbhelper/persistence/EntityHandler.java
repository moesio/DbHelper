package com.seimos.android.dbhelper.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.seimos.android.dbhelper.util.Application;
import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Aug 6, 2015 7:38:45 PM
 */
public class EntityHandler {

	private Class<? extends BaseEntity> entityClass;
	private Context context;

	public EntityHandler(Context context, Class<? extends BaseEntity> entityClass) {
		if (context == null || entityClass == null) {
			throw new NullPointerException("Neither parameter context nor entity class can be null");
		}
		this.context = context;
		this.entityClass = entityClass;
	}

	public String getTableName() {
		return entityClass.getSimpleName();
	}

	public String[] getColumns() {
		Field[] fields = Reflection.getInnerDeclaredFields(entityClass);
		String[] columns = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			columns[i] = fields[i].getName();
		}
		Arrays.sort(columns);
		return columns;
	}

	public ContentValues createContentValues(BaseEntity entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity can not be null");
		}
		ContentValues contentValues = new ContentValues();

		Field[] declaredFields = Reflection.getInnerDeclaredFields(entity.getClass());
		for (Field field : declaredFields) {
			Object value = Reflection.getValue(entity, field.getName());
			String databaseFieldName = field.getName();
			if (value != null) {
				if (Reflection.isEntity(value)) {
					Field idField = Reflection.getIdField(field.getType());
					try {
						contentValues.put(databaseFieldName, (Integer) idField.get(value));
					} catch (IllegalAccessException | IllegalArgumentException e) {
					}
				} else if (field.getType() == Integer.class) {
					contentValues.put(databaseFieldName, (Integer) value);
				} else if (field.getType() == Boolean.class) {
					contentValues.put(databaseFieldName, (Boolean) value);
				} else if (field.getType() == Date.class) {
					contentValues.put(databaseFieldName, Reflection.getDateFormat(field).format(value));
				} else if (field.getType() == Double.class) {
					contentValues.put(databaseFieldName, (Double) value);
				} else if (field.getType() == Long.class) {
					contentValues.put(databaseFieldName, (Long) value);
				} else {
					contentValues.put(databaseFieldName, value.toString());
				}
			} else {
				contentValues.putNull(databaseFieldName);
			}
		}
		return contentValues;
	}

	public List<BaseEntity> extract(final Cursor cursor) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException,
			NoSuchMethodException {
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		if (cursor.moveToFirst()) {
			do {
				BaseEntity entity = createEntityFromCursor(cursor);
				if (entity != null) {
					list.add(entity);
				}
			} while (cursor.moveToNext());
		}
		return list;
	}

	public BaseEntity createEntityFromCursor(final Cursor cursor) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		BaseEntity entity = entityClass.newInstance();
		if (cursor.getCount() > 0) {
			String[] columnNames = cursor.getColumnNames();
			for (String columnName : columnNames) {
				try {
					Field field = entityClass.getDeclaredField(columnName);
					Class<?> type = field.getType();

					Object value = null;
					if (type == Boolean.class) {
						value = cursor.getInt(cursor.getColumnIndex(columnName)) == 1 ? true : false;
					} else if (type == Integer.class) {
						value = cursor.getInt(cursor.getColumnIndex(columnName));
					} else if (type == String.class) {
						value = cursor.getString(cursor.getColumnIndex(columnName));
					} else if (type == Date.class) {
						String stringValue = cursor.getString(cursor.getColumnIndex(columnName));
						if (stringValue != null) {
							try {
								value = Reflection.getDateFormat(field).parse(stringValue);
							} catch (ParseException e) {
							}
						}
					} else if (type == Double.class) {
						value = cursor.getDouble(cursor.getColumnIndex(columnName));
					} else if (type == Long.class) {
						value = cursor.getLong(cursor.getColumnIndex(columnName));
					} else {
						// TODO Test deep associations more than one level, ie, nested association

						BaseEntity association = (BaseEntity) type.newInstance();
						Method methodAssociation = association.getClass().getMethod(Reflection.getSetter(Reflection.getIdField(association.getClass()).getName()), Integer.class);
						// TODO Verify when id field is not an Integer
						Integer idValue = cursor.getInt(cursor.getColumnIndex(columnName));
						methodAssociation.invoke(association, idValue);

						@SuppressWarnings("unchecked")
						EntityHandler entityHandler = new EntityHandler(context, (Class<? extends BaseEntity>) field.getType());
						SQLiteDatabase database = DatabaseHelper.open();
						String idFieldName = Reflection.getIdField(field.getType()).getName();
						// TODO Verify when id field is not an Integer
						Cursor cursorAssociation = database.query(entityHandler.getTableName(), entityHandler.getColumns(), idFieldName + " = ?",
								new String[] { idValue.toString() }, null, null, idFieldName, "1");
						if (cursorAssociation.moveToFirst()) {
							association = entityHandler.createEntityFromCursor(cursorAssociation);
						}
						database.close();

						value = association;
					}
					if (value != null) {
						Reflection.setValue(entity, columnName, value);
					}
				} catch (NoSuchFieldException e) {
					Log.d(Application.getName(context), "There is no " + columnName + " field for " + entityClass.getCanonicalName());
				}
			}
		}
		return entity;
	}
}
