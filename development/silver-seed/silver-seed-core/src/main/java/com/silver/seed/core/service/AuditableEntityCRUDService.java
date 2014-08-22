package com.silver.seed.core.service;

import com.silver.seed.core.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public abstract class AuditableEntityCRUDService<T extends Entity, ID extends Serializable, E extends JpaRepository<T, ID>>
	implements EntityCRUDService<T, ID> {

	public abstract E getRepository();

	public T retrieve(ID id) {		
		return getRepository().getOne(id);
	}
	
	public void create(T entity) {
        getRepository().save(entity);
	}
	
	public void update(T entity) {
        getRepository().save(entity);
	}
	
	public void delete(ID id) {
        getRepository().delete(id);
	}
	
	public void delete(T entity) {
        getRepository().delete(entity);
	}
}
