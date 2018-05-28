package br.com.processboss.core.service;

import java.util.List;

public interface IService<T, ID> {
	T findById(ID id);  
    List<T> listAll();  
    T save(T entity);  
    T update(T entity); 
    T saveOrUpdate(T entity);  
    void delete(T entity);  
}
