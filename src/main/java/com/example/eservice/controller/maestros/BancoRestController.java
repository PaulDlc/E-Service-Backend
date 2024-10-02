package com.example.eservice.controller.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.maestros.Banco;
import com.example.eservice.models.service.maestros.IBancoService;

@RestController
@RequestMapping("/api")
public class BancoRestController {
	@Autowired
	private IBancoService bancoService;
	
	@GetMapping("/banco/all")
	public List<Banco> getAll(){
		return bancoService.getAll();
	}
}