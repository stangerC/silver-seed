
package com.silver.seed.dao.jpa;

import com.silver.seed.dao.CrudDao;
import com.silver.seed.entity.Entity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Liaojian
 */
public abstract class BaseJpaDao<T extends Entity, ID extends Serializable> implements CrudDao<T, ID> {     
    
    private EntityManagerFactory emf;
    private Class<T> entityClass;
    
    public BaseJpaDao(EntityManagerFactory emf) {
        entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.emf = emf;
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(T entity) {
        EntityManager em = null;
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
        EntityManager em = null;
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
       EntityManager em = null;
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
        EntityManager em = null;
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
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }        
}
