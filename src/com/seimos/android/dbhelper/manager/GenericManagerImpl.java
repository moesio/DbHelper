package com.seimos.android.dbhelper.manager;

import java.util.List;

import android.content.Context;

import com.seimos.android.dbhelper.dao.GenericDao;
import com.seimos.android.dbhelper.persistence.BaseEntity;
import com.seimos.android.dbhelper.persistence.Filter;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:43:31 PM
 */
public abstract class GenericManagerImpl<Entity extends BaseEntity, Dao extends GenericDao<Entity>> implements GenericManager<Entity> {

	public abstract GenericDao<Entity> getDao();

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

	@Override
	public List<Entity> filter(Filter... filters) {
		return getDao().filter(filters);
	}

	public boolean update(Entity entity) {
		return getDao().update(entity);
	}

	public boolean delete(Object id) {
		return getDao().delete(id);
	}
}
