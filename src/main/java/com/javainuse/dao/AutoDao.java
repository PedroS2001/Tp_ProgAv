package com.javainuse.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javainuse.model.Auto;

@Repository
public interface AutoDao extends CrudRepository<Auto, Integer> {
	
	Auto findByModelo(String modelo);
	Auto findByMarca(String marca);
	Auto findByPatente(int patente);
}
