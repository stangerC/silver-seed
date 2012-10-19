package com.silver.seed.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import com.silver.seed.entity.Entity;
/**
 * hibernate dao接口，定义了常用的持久层方法签名
 * @author liaojian
 *
 * @param <T>	进行增删改查的实体类
 */
public interface DAO<T extends Entity> {
	/**
	 * 保存一个实体到数据库
	 * @param entity
	 * 		需要保存到数据库的实体类
	 */
	public void save(T entity);
	/**
	 * 删除一个实体对应的数据库记录
	 * @param entity
	 * 		需要删除的实体
	 */
	public void delete(T entity);
	
	public void delete(Serializable id);
	
	public void delete(Serializable[] ids);
	/**
	 * 更新一个实体到数据库
	 * @param entity
	 * 		需要更新的实体
	 */
	public void update(T entity);
	/**
	 * 根据id获取一个实体
	 * @param id
	 * 		实体在数据库中记录的主键id值
	 * @return
	 * 		对应id的实体
	 */
	public T get(Serializable id);
	/**
	 * 执行hql查询，返回一个包含实体元素的List
	 * @param hql
	 * 		hql语句
	 * @return
	 * 		通过hql查询出来的实体集合，以List形式返回
	 */
	public List<T> query(String hql);
	/**
	 * 带参数执行hql查询，返回一个包含满足查询条件的实体的List
	 * @param hql
	 * 		hql语句
	 * @param parameters
	 * 		查询参数
	 * @return
	 * 		通过hql查询出来的实体集合，以List形式返回
	 */
	public List<T> query(String hql, Object[] parameters);
	
	public List<T> queryBySql(String sql);
	
	public List<T> queryBySql(String sql, Object[] parameters);
}
