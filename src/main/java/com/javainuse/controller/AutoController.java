package com.javainuse.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.javainuse.model.Auto;
import com.javainuse.service.AutoService;

@RestController
public class AutoController {

	@Autowired
	AutoService autoService;
	
	
	
	@GetMapping("/autos")
	public ResponseEntity<?> getAllAutos()
	{	
		return new ResponseEntity< List<Auto> > (this.autoService.traerTodos(), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/autos/{patente}")
	public ResponseEntity<?> getAutoPorId(@PathVariable int patente)
	{
		Auto autito = this.autoService.traerUno(patente);
		if(autito != null)
		{
			return new ResponseEntity<Auto>(autito, HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("No se encontro el auto", HttpStatus.BAD_REQUEST);
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')") 
	@PostMapping("/auto")
	public Auto insertAuto(@RequestBody Auto auto)
	{
		return this.autoService.agregarAuto(auto);
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@PutMapping("/auto")
	public ResponseEntity<?> editAuto(@RequestBody Auto auto) 
	{
		try {
			Auto newAuto = this.autoService.modificarAuto(auto);
			
			return new ResponseEntity<Auto>(newAuto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro el auto", HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')") 
	@DeleteMapping("/auto/{patente}")
	public ResponseEntity<?> deleteAuto(@PathVariable Integer patente)
	{
		try {
			this.autoService.eliminarAuto(patente);
			return new ResponseEntity<String>("Se elimino el auto", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("No se encontro el auto", HttpStatus.NOT_FOUND);
		}
	}
	
	
}
