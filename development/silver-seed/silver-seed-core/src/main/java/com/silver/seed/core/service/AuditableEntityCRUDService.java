package com.silver.seed.core.service;

import com.silver.seed.core.entity.AuditableEntity;
import com.silver.seed.core.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

@Deprecated
public abstract class AuditableEntityCRUDService<T extends AuditableEntity, ID extends Serializable, E extends JpaRepository<T, ID>>
	extends BaseEntityCRUDService<T, ID, E> {


}
