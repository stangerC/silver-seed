package com.silver.seed.core.service;

import com.silver.seed.core.entity.Entity;
import java.io.Serializable;

public interface EntityCRUDService<T extends Entity, ID extends Serializable> {
	public T retrieve(ID id);
	
	public T create(T entity);
	
	public void update(T entity);
	
	public void delete(ID id);
	
	public void delete(T entity);
		
}
