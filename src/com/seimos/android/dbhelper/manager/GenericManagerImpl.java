package com.seimos.android.dbhelper.manager;

import java.util.List;

import android.content.Context;

import com.seimos.android.dbhelper.dao.GenericDao;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:43:31 PM
 */
public abstract class GenericManagerImpl<Entity extends com.seimos.android.dbhelper.database.BaseEntity, Dao extends GenericDao<Entity>> implements GenericManager<Entity> {

	public abstract GenericDao<Entity> getDao();

	@SuppressWarnings("unused")
	private GenericManagerImpl() {
	}
	
	public GenericManagerImpl(Context context) {
	}

	public boolean create(Entity entity) {
		return getDao().create(entity);
	}
	
	public Entity retrieve(Object id) {
		return getDao().retrieve(id);
	}

	public List<Entity> list() {
		return getDao().list();
	}

	public boolean update(Entity entity) {
		return getDao().update(entity);
	}
	
	public boolean delete(Object id) {
		return getDao().delete(id);
	}
}
