package com.silver.seed.core.service;

import com.silver.seed.core.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public abstract class BaseEntityCRUDService<T extends Entity, ID extends Serializable, E extends JpaRepository<T, ID>>
	implements EntityCRUDService<T, ID> {

    protected E repository;

	public E getRepository() {
        return repository;
    }

	public T retrieve(ID id) {		
		return getRepository().findOne(id);
	}
	
	public T create(T entity) {
        return getRepository().saveAndFlush(entity);
	}
	
	public void update(T entity) {
        getRepository().saveAndFlush(entity);
	}
	
	public void delete(ID id) {
        getRepository().delete(id);
	}
	
	public void delete(T entity) {
        getRepository().delete(entity);
	}

    public void save(T entity) {
        getRepository().save(entity);
    }

    public void flush() {getRepository().flush();}
}
