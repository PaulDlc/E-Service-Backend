package com.example.eservice.controller.ubicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.ubicacion.Departamento;
import com.example.eservice.models.service.ubicacion.IDepartamentoService;

@RestController
@RequestMapping("/api")
public class DepartamentoRestController {
	@Autowired
	private IDepartamentoService departamentoService;
	
	@GetMapping("/departamento/all")
	public List<Departamento> getListadoDepartamento() {
		return departamentoService.getListado();
	}
}