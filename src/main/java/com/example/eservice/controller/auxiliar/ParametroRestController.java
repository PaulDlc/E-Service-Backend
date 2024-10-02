package com.example.eservice.controller.auxiliar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.auxiliares.Parametro;
import com.example.eservice.models.service.auxiliares.IParametroService;

@RestController
@RequestMapping("/api")
public class ParametroRestController {
	
	@Autowired
	private IParametroService paramService;
	
	@GetMapping("/parametro/find/{id}")
	public Parametro getParametroById(@PathVariable Integer id) {
		return paramService.getParametroById(id);
	}
}