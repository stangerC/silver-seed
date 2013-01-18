package com.silver.seed.service;

import com.silver.seed.dao.CrudDao;
import com.silver.seed.entity.Entity;
import java.io.Serializable;

public abstract class BaseEntityCRUDService<T extends Entity, ID extends Serializable, E extends CrudDao<T, ID>> 
	implements EntityCRUDService<T, ID> {
	private E entityDao;

	public E getEntityDAO() {
		return entityDao;
	}

	public void setEntityDAO(E entityDAO) {
		this.entityDao = entityDAO;
	}	
	
	public T retrieve(ID id) {		
		return entityDao.retrieve(id);
	}
	
	public void create(T entity) {
		entityDao.create(entity);
	}
	
	public void update(T entity) {
		entityDao.update(entity);
	}
	
	public void delete(ID id) {
		entityDao.delete(id);
	}
	
	public void delete(T entity) {
		entityDao.delete(entity);
	}
}
