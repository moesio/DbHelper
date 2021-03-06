package com.seimos.android.dbhelper.dao;

import java.util.List;

import com.seimos.android.dbhelper.persistence.Filter;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:43:47 PM
 */
public interface GenericDao<Entity extends com.seimos.android.dbhelper.persistence.BaseEntity> {
	long create(Entity entity);

	Entity retrieve(Object id);

	List<Entity> list();

	List<Entity> filter(List<Filter> filtersList);
	
	List<Entity> filter(Filter... filters);

	boolean update(Entity entity);

	boolean delete(Object id);

}
