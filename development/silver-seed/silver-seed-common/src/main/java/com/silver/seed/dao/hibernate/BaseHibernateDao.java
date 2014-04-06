package com.silver.seed.dao.hibernate;

import com.silver.seed.dao.CrudDao;
import com.silver.seed.entity.Entity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseHibernateDao<T extends Entity, ID extends Serializable> extends HibernateDaoSupport implements CrudDao<T, ID> {

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
    public BaseHibernateDao() {
        entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void create(T entity) {
        getSession().save(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    public void delete(ID id) {
        T entity = (T) getSession().load(entityClass, id);
        getSession().delete(entity);
    }

    public void delete(Collection<ID> ids) {
        throw new UnsupportedOperationException();
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public T retrieve(ID id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 执行hql查询，返回一个包含实体元素的List
     *
     * @param hql hql语句
     * @return 通过hql查询出来的实体集合，以List形式返回
     */
    public List<T> query(String hql) {
        return getHibernateTemplate().find(hql);
    }

    /**
     * 带参数执行hql查询，返回一个包含满足查询条件的实体的List
     *
     * @param hql hql语句
     * @param parameters 查询参数
     * @return 通过hql查询出来的实体集合，以List形式返回
     */
    public List<T> query(String hql, Object[] parameters) {
        return getHibernateTemplate().find(hql, parameters);
    }

    public List<T> queryBySql(String sql) {
        throw new UnsupportedOperationException();
    }

    public List<T> queryBySql(String sql, Object[] parameters) {
        throw new UnsupportedOperationException();
    }
}
