package br.com.processboss.core.persistence.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import br.com.processboss.core.persistence.dao.IDAO;


//Adicionando heranca ao HibernateDaoSupport temos diversas funcionalidades do Spring  
//junto ao Hibernate, implementamos tambem nosso DAO generico para ter mobilidade.  
public class GenericDAO<T, ID extends Serializable> extends
		HibernateDaoSupport implements IDAO<T, ID> {

	private static Log LOG = LogFactory.getLog(GenericDAO.class);

	// Nosso construtor vai setar automaticamente via Reflection qual classe
	// estamos tratando.
	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	// Classe que sera persistida.
	private Class<T> persistentClass;

	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public void delete(T entity) {
		try {
			this.getHibernateTemplate().delete(entity);
		} catch (final HibernateException ex) {
			GenericDAO.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	public T findById(ID id) {
		try {
			return (T) this.getHibernateTemplate()
					.get(getPersistentClass(), id);
		} catch (final HibernateException ex) {
			GenericDAO.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	public List<T> listAll() {
		try {
			return this.getHibernateTemplate().loadAll(getPersistentClass());
		} catch (final HibernateException ex) {
			GenericDAO.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	public T save(T entity) {
		try {
			this.getHibernateTemplate().save(entity);
			return entity;
		} catch (final HibernateException ex) {
			GenericDAO.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}
	
	public T update(T entity) {
		try {
			this.getHibernateTemplate().update(entity);
			return entity;
		} catch (final HibernateException ex) {
			GenericDAO.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}
	
	public T saveOrUpdate(T entity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
			return entity;
		} catch (final HibernateException ex) {
			LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		try {
			Criteria crit = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(getPersistentClass());
			for (Criterion c : criterion) {
				crit.add(c);
			}
			return crit.list();
		} catch (final HibernateException ex) {
			GenericDAO.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}
}