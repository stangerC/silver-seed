package com.silver.seed.service;

import com.silver.seed.entity.Entity;
import java.io.Serializable;

public interface EntityCRUDService<T extends Entity> {
	public T get(Serializable id);
	
	public void save(T entity);
	
	public void update(T entity);
	
	public void delete(Serializable id);
	
	public void delete(T entity);
		
}
