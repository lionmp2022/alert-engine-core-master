package com.tecnologia.mensajeria.dao;

import java.util.List;


/**
 * Interface generica para todos los DAO.
 * Define los metodos basicos para el manejo de una entidad.
 * 
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * Lista con todos los objetos en base de datos
	 * @return Lista
	 */
	public List<T> listar();

	/**
	 * Inserta un objetoen la base de datos
	 * @param objeto Entidad a guardar
	 * @return regresa Entidad
	 */
	public T insertar(T objeto);
	
	/**
	 * Actualiza un objeto en base de datos
	 * @param objeto Entidad a actualizar
	 * @return regresa Entidad
	 */
	public T actualizar(T objeto);

	/**
	 * Elimina un objeto en base de datos 
	 * @param objeto Entidad a eliminar
	 * 
	 */
	public void eliminar(T objeto);
}