package com.javainuse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javainuse.dao.AutoDao;
import com.javainuse.model.Auto;

@Service
public class AutoService {
	
	@Autowired
	AutoDao autoDao;
	
	public List<Auto> traerTodos()
	{
		List<Auto> lista = (List<Auto>) this.autoDao.findAll();
		return lista;
	}
	
	public Auto traerUno(int patente)
	{
		return this.autoDao.findByPatente(patente);
	}
	
	public Auto agregarAuto(Auto auto)
	{
		return this.autoDao.save(auto);
	}
	
	public Auto modificarAuto(Auto auto)
	{
		Auto newAuto = this.autoDao.findById(auto.getPatente()).get();
		newAuto.setAnio(auto.getAnio());
		newAuto.setColor(auto.getColor());
		newAuto.setMarca(auto.getMarca());
		newAuto.setModelo(auto.getModelo());

		return this.autoDao.save(newAuto);
	}
	
	public boolean eliminarAuto(int patente)
	{
		Auto auto = this.autoDao.findById(patente).get();
		this.autoDao.delete(auto);
		return true;
	}
	
	
}
