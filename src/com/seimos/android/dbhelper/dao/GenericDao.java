package com.seimos.android.dbhelper.dao;

import java.util.List;

import com.seimos.android.dbhelper.criterion.Filter;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:43:47 PM
 */
public interface GenericDao<Entity extends com.seimos.android.dbhelper.criterion.BaseEntity> {
	boolean create(Entity entity);

	Entity retrieve(Object id);

	List<Entity> list();

	List<Entity> filter(Filter... filters);

	boolean update(Entity entity);

	boolean delete(Object id);

}
