package com.seimos.android.dbhelper.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.seimos.android.dbhelper.criterion.BaseEntity;
import com.seimos.android.dbhelper.criterion.DatabaseHelper;
import com.seimos.android.dbhelper.criterion.EntityHandler;
import com.seimos.android.dbhelper.criterion.Filter;
import com.seimos.android.dbhelper.util.Application;
import com.seimos.android.dbhelper.util.Reflection;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:47:59 PM
 */
public class GenericDaoImpl<Entity extends BaseEntity> implements GenericDao<Entity> {

	/**
	 * @author moesio @ gmail.com
	 * @date Aug 3, 2015 7:33:50 PM
	 */
	private class FilterManager {
		private Filter[] filters;
		private String projection;
		private String[] args;
		private String orderBy;

		public FilterManager(Filter[] filters) {
			this.filters = filters;
			split();
		}

		private void split() {
			if (filters != null && filters.length > 0) {
				ArrayList<String> argsBuilder = new ArrayList<String>();
				StringBuilder projectionBuilder = new StringBuilder();
				StringBuilder orderBuilder = new StringBuilder();
				for (int i = 0; i < filters.length; i++) {
					if (filters[i].getOrderBy() != null) {
						orderBuilder.append(filters[i].getOrderBy().toString());

						if (i < (filters.length - 1)) {
							orderBuilder.append(", ");
						}
					} else {
						projectionBuilder.append(filters[i].getClausule());
						String arg = null;
						try {
							arg = Reflection.getStringValue(entityClass, filters[i].getColumn(), filters[i].getValue());
						} catch (NoSuchFieldException e) {
							Log.d(Application.getName(context), "There is no field " + filters[i].getColumn() + " in " + entityClass.getCanonicalName());
						}

						if (arg != null) {
							argsBuilder.add(arg);
						}

						if (i < (filters.length - 1)) {
							projectionBuilder.append(", ");
						}
					}
				}
				if (projectionBuilder.length() > 0) {
					this.projection = projectionBuilder.toString();
					this.orderBy = orderBuilder.toString();
				}
				if (argsBuilder.size() > 0) {
					args = new String[argsBuilder.size()];
					argsBuilder.toArray(args);
				}
			}
		}

		public String getProjection() {
			return projection;
		}

		public String[] getArgs() {
			return args;
		}

		public String getOrderBy() {
			return orderBy;
		}
	}

	private Context context;
	private Class<Entity> entityClass;
	private EntityHandler entityHandler;

	@SuppressWarnings("unchecked")
	public GenericDaoImpl(Context context) {
		this.context = context;
		this.entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityHandler = new EntityHandler(context, entityClass);
	}

	public boolean create(Entity entity) {
		SQLiteDatabase connection = null;
		long id = 0;
		try {
			connection = DatabaseHelper.open();
			ContentValues values = entityHandler.createContentValues(entity);
			id = connection.insert(entityHandler.getTableName(), null, values);
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error while creating " + entityClass.getSimpleName());
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return id > 0;
	}

	@SuppressWarnings("unchecked")
	public Entity retrieve(Object id) {
		SQLiteDatabase connection = null;
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		try {
			connection = DatabaseHelper.open();
			Cursor cursor;
			try {
				Field idField = Reflection.getIdField(entityClass);
				String idValue;
				if (idField.getClass().equals(Date.class)) {
					idValue = Reflection.getDateFormat(idField).format(id);
				} else {
					idValue = id.toString();
				}
				String idFieldName = idField.getName();
				cursor = connection.query(entityHandler.getTableName(), entityHandler.getColumns(), idFieldName + " = ?", new String[] { idValue }, null, null, idFieldName, "1");
				list = entityHandler.extract(cursor);
			} catch (Exception e) {
				Log.e(Application.getName(context), "Error while retrieving.");
				Log.e(Application.getName(context), e.getMessage());
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error while retriving " + entityClass.getSimpleName());
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
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
		SQLiteDatabase connection = null;
		List<BaseEntity> list = new ArrayList<BaseEntity>();
		try {
			connection = DatabaseHelper.open();
			Cursor cursor;
			try {
				FilterManager filterManager = new FilterManager(filters);
				String orderBy = filterManager.getOrderBy();
				cursor = connection.query(entityHandler.getTableName(), entityHandler.getColumns(), filterManager.getProjection(), filterManager.getArgs(), null, null, orderBy);
				list = entityHandler.extract(cursor);
			} catch (Exception e) {
				Log.e(Application.getName(context), "Error in filter");
				Log.e(Application.getName(context), e.getMessage());
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error while filtering " + entityClass.getSimpleName());
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return (List<Entity>) list;
	}

	public boolean update(Entity entity) {
		SQLiteDatabase connection = null;
		try {
			connection = DatabaseHelper.open();
			ContentValues values = entityHandler.createContentValues(entity);
			Field idField = Reflection.getIdField(entityClass);
			Method method = entityClass.getMethod(Reflection.getGetter(idField));
			int affectedRows = connection.update(entityHandler.getTableName(), values, "id = ?", new String[] { method.invoke(entity).toString() });
			return affectedRows != 0;
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error while updating " + entityClass.getSimpleName());
			return false;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public boolean delete(Object id) {
		SQLiteDatabase connection = null;
		int affectedRows = 0;
		try {
			connection = DatabaseHelper.open();
			affectedRows = connection.delete(entityHandler.getTableName(), "id = ?", new String[] { id.toString() });
			connection.close();
		} catch (Exception e) {
			Log.e(Application.getName(context), "Error while deleting " + entityClass.getSimpleName());
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return affectedRows > 0;
	}

}
