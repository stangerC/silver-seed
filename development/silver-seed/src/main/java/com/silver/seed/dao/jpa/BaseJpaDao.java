
package com.silver.seed.dao.jpa;

import com.silver.seed.dao.CrudDao;
import com.silver.seed.entity.Entity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

/**
 * JPA的基础DAO类。不要与spring一起使用，要采用JTA的方式 
 * @author Liaojian
 */
public abstract class BaseJpaDao<T extends Entity, ID extends Serializable> implements CrudDao<T, ID> {         
    
    private EntityManager em;
    private Class<T> entityClass;
    
    public BaseJpaDao() {
        entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];        
    }
    
    public EntityManager getEntityManager() {
        return em;
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }
    
    public void create(T entity) {        
        try {
            em = getEntityManager();            
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void create(Collection<T> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(ID id) {        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            T entity = em.find(entityClass, id);
            EntityTransaction et = em.getTransaction();
            if(entity != null) {
                em.remove(entity);
            }
           em.getTransaction().commit();
        }finally {
            if(em != null) {
                em.close();
            }
        }
    }

    public void delete(T entity) {       
        try {
            em = getEntityManager();             
            if(entity != null) {
                em.remove(entity);
            }
        }finally {
            if(em != null) {
                em.close();
            }
        }
    }

    public void delete(Collection<ID> ids) {
        for(ID id : ids) {
            delete(id);
        }
    }

    public void update(T entity) {        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();       
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void update(Collection<T> entities) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public T retrieve(ID id) {
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }        
}
