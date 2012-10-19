package com.silver.seed.dao.hibernate;

import com.silver.seed.entity.Entity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseHibernateDAO<T extends Entity> extends HibernateDaoSupport implements DAO<T>{ 
	/**
	 * DAO类对应的实体类的Class实例
	 */
	private Class<T> entityClass;
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	/**
	 * 默认构造函数。子类的构造函数必须执行该构造函数。
	 */
	@SuppressWarnings("unchecked")
	public BaseHibernateDAO() {
		entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public void save(T entity) {
		getSession().save(entity);
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}
		
	@SuppressWarnings("unchecked")
	public void delete(Serializable id) {
		T entity = (T)getSession().load(entityClass, id);
		getSession().delete(entity);
	}
		
	public void delete(Serializable[] ids) {
		throw new UnsupportedOperationException();		
	}
	
	public void update(T entity) {
		getSession().update(entity);
	}
	
	@SuppressWarnings("unchecked")	
	public T get(Serializable id) {
		return (T)getSession().get(entityClass, id);
	}
		
	@SuppressWarnings("unchecked")
	public List<T> query(String hql) {		
		return getHibernateTemplate().find(hql);
	}
		
	@SuppressWarnings("unchecked") 
	public List<T> query(String hql, Object[] parameters){
		return getHibernateTemplate().find(hql, parameters);
	}
		
	public List<T> queryBySql(String sql) {
		throw new UnsupportedOperationException();
	}
		
	public List<T> queryBySql(String sql, Object[] parameters) {
		throw new UnsupportedOperationException();
	}
}
