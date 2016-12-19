package com.seimos.android.dbhelper.manager;

import java.util.List;

import com.seimos.android.dbhelper.criterion.Filter;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:39:36 PM
 */
public interface GenericManager<Entity> {
	boolean create(Entity entity);

	Entity retrieve(Object id);

	List<Entity> list();

	List<Entity> filter(Filter... filters);

	boolean update(Entity entity);

	boolean delete(Object id);

}
