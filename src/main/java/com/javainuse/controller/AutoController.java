package com.javainuse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.dao.AutoDao;
import com.javainuse.model.Auto;

@RestController
public class AutoController {

	@Autowired
	AutoDao autoDao;
	
	
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')") 
	@RequestMapping({ "/helloa" })
	public String firstPage() {
		return "Hello World";
	}
	
	@RequestMapping({ "/helloAdmina" })
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	public String firstAdminPage() {
		return "Soy admin";
	}
	
	
	
	@GetMapping("/autos")
	public ResponseEntity<?> getAllAutos()
	{	
		return new ResponseEntity< Iterable<Auto> > (this.autoDao.findAll(), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/autos/{patente}")
	public ResponseEntity<?> getAutoPorId(@PathVariable int patente)
	{
		Auto autito = this.autoDao.findByPatente(patente);
		if(autito != null)
		{
			return new ResponseEntity<Auto>(autito, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("No se encontro el auto", HttpStatus.BAD_REQUEST);

	}
	
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')") 
	@PostMapping("/auto")
	public Auto insertarAuto(@RequestBody Auto auto)
	{
		return this.autoDao.save(auto);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@PutMapping("/auto")
	public ResponseEntity<?> editPersona(@RequestBody Auto auto) 
	{
		try {
			Auto newAuto = this.autoDao.findById(auto.getPatente()).get();
			newAuto.setAnio(auto.getAnio());
			newAuto.setColor(auto.getColor());
			newAuto.setMarca(auto.getMarca());
			newAuto.setModelo(auto.getModelo());
			
			return new ResponseEntity<Auto>(this.autoDao.save(newAuto), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro el auto", HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@DeleteMapping("/auto/{patente}")
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	public ResponseEntity<?> eliminarAuto(@PathVariable Integer patente)
	{
		try {
			Auto auto= this.autoDao.findById(patente).get();
			this.autoDao.delete(auto);
			return new ResponseEntity<String>("Se elimino el auto", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro el auto", HttpStatus.NOT_FOUND);
		}
	}
	
	
}
