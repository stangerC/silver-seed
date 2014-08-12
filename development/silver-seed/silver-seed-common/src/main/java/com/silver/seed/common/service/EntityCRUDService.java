package com.silver.seed.common.service;

import com.silver.seed.common.entity.Entity;
import java.io.Serializable;

public interface EntityCRUDService<T extends Entity, ID extends Serializable> {
	public T retrieve(ID id);
	
	public void create(T entity);
	
	public void update(T entity);
	
	public void delete(ID id);
	
	public void delete(T entity);
		
}
