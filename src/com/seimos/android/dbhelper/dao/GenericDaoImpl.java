package com.seimos.android.dbhelper.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.seimos.android.dbhelper.database.BaseEntity;
import com.seimos.android.dbhelper.database.DatabaseUtil;
import com.seimos.android.dbhelper.database.EntityHandler;
import com.seimos.android.dbhelper.database.Filter;
import com.seimos.android.dbhelper.database.FilterManager;
import com.seimos.android.dbhelper.util.Application;
import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:47:59 PM
 */
public abstract class GenericDaoImpl<Entity extends BaseEntity> implements GenericDao<Entity> {

	private Context context;
	private Class<Entity> entityClass;
	private EntityHandler entityHandler;

	@SuppressWarnings("unused")
	private GenericDaoImpl() {
	}

	@SuppressWarnings("unchecked")
	public GenericDaoImpl(Context context) {
		this.context = context;
		this.entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityHandler = new EntityHandler(context, entityClass);
	}

	public boolean create(Entity entity) {
		SQLiteDatabase database = DatabaseUtil.openForRead(context);
		ContentValues values = entityHandler.createContentValues(entity);
		long id = database.insert(entityHandler.getTableName(), null, values);
		database.close();
		return id != 0;
	}

	@SuppressWarnings("unchecked")
	public Entity retrieve(Object id) {
		SQLiteDatabase database = DatabaseUtil.openForRead(context);
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		Cursor cursor;
		try {
			Field idField = Reflection.getIdField(entityClass);
			String idValue = Filter.getStringValue(id);
			String idFieldName = idField.getName();
			cursor = database.query(entityHandler.getTableName(), entityHandler.getColumns(), idFieldName + " = ?", new String[] { idValue }, null, null, idFieldName, "1");
			list = entityHandler.extract(cursor);
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error while retrieving.");
			Log.e(Application.getName(context), e.getMessage());
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		database.close();
		try {
			return (Entity) list.get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public List<Entity> list() {
		return filter();
	}

	@SuppressWarnings("unchecked")
	public List<Entity> filter(Filter... filters) {
		SQLiteDatabase database = DatabaseUtil.openForRead(context);
		Cursor cursor;
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		try {
			FilterManager filterManager = new FilterManager(filters);
			String orderBy = filterManager.getOrderBy();
			cursor = database.query(entityHandler.getTableName(), entityHandler.getColumns(), filterManager.getSelection(), filterManager.getArgs(), null, null, orderBy);
			list = entityHandler.extract(cursor);
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error in filter");
			Log.e(Application.getName(context), e.getMessage());
			Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		database.close();
		return (List<Entity>) list;
	}

	public boolean update(Entity entity) {
		SQLiteDatabase database = DatabaseUtil.openForWrite(context);
		ContentValues values = entityHandler.createContentValues(entity);
		try {
			Field idField = Reflection.getIdField(entityClass);
			Method method = entityClass.getMethod(Reflection.getGetter(idField));
			int affectedRows = database.update(entityHandler.getTableName(), values, "id = ?", new String[] { method.invoke(entity).toString() });
			return affectedRows != 0;
		} catch (Exception e) {
			return false;
		} finally {
			database.close();
		}
	}

	public boolean delete(Object id) {
		SQLiteDatabase database = DatabaseUtil.openForWrite(context);
		int affectedRows = database.delete(entityHandler.getTableName(), "id = ?", new String[] { id.toString() });
		database.close();
		return affectedRows > 0;
	}

}
