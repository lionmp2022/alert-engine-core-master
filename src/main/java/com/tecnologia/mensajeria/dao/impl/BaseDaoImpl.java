package com.tecnologia.mensajeria.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.tecnologia.mensajeria.dao.BaseDao;




public class BaseDaoImpl<T> implements BaseDao<T>  {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	private static final Logger LOGGER = Logger.getLogger(BaseDaoImpl.class);
		
	private Class<T> clase;
	
	public BaseDaoImpl(Class<T> clase) {
        this.clase = clase;
    }


	public T actualizar(T objeto) {
		LOGGER.info("actualiza: " + objeto);
		entityManager.merge(objeto);
				
		return objeto;
	}

	public void eliminar(T objeto) {
		entityManager.remove(objeto);		
	}
	
	public T insertar(T objeto) {
		LOGGER.info("insertar:" + objeto);
		entityManager.persist(objeto);
		
		return objeto;
	}

	public List<T> listar() {
		List<T> results = entityManager.createQuery("select T from " + clase.getSimpleName() + " T").getResultList();
		return results;
	}	
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
		 
		if(c==null){
			return null;
		}
		List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c) {
	      r.add(clazz.cast(o));
	    }  
	    return r;
	}
	
}