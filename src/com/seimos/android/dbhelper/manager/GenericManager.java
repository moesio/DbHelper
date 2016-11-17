package com.seimos.android.dbhelper.manager;

import java.util.List;

/**
 * @author moesio @ gmail.com
 * @date Jul 28, 2015 5:39:36 PM
 */
public interface GenericManager<Entity> {
	boolean create(Entity entity);

	Entity retrieve(Object id);

	List<Entity> list();

	boolean update(Entity entity);

	boolean delete(Object id);

}
