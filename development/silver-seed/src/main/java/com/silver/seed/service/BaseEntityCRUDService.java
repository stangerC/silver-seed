package com.silver.seed.service;

import com.silver.seed.dao.hibernate.DAO;
import com.silver.seed.entity.Entity;
import java.io.Serializable;

public abstract class BaseEntityCRUDService<T extends Entity, E extends DAO<T>> 
	implements EntityCRUDService<T> {
	private E entityDAO;

	public E getEntityDAO() {
		return entityDAO;
	}

	public void setEntityDAO(E entityDAO) {
		this.entityDAO = entityDAO;
	}	
	
	public T get(Serializable id) {		
		return entityDAO.get(id);
	}
	
	public void save(T entity) {
		entityDAO.save(entity);
	}
	
	public void update(T entity) {
		entityDAO.update(entity);
	}
	
	public void delete(Serializable id) {
		entityDAO.delete(id);
	}
	
	public void delete(T entity) {
		entityDAO.delete(entity);
	}
}
