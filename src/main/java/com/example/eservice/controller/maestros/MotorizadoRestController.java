package com.example.eservice.controller.maestros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.maestros.Motorizado;
import com.example.eservice.models.service.maestros.IMotorizadoService;

@RequestMapping("/api")
@RestController
public class MotorizadoRestController {
	@Autowired
	private IMotorizadoService motorizadoService;
	
	@GetMapping("/motorizado/rutas-id/{motorizadoId}")
	public List<Integer> getRutasId(@PathVariable Integer motorizadoId) {
		
		return motorizadoService.getRutasIdFromMotorizado(motorizadoId);
		
	}
	
	@PutMapping("/motorizado/asignacion-ruta/{motorizadoId}/{rutaId}")
	public ResponseEntity<?> asignacionRuta(@PathVariable Integer motorizadoId, @PathVariable Integer rutaId) {
		Map<String, Object> response = new HashMap<>();
		
		motorizadoService.asignarRutaMotorizado(motorizadoId, rutaId);
		
		response.put("motorizado", motorizadoService.getById(motorizadoId));
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/motorizado/all")
	public List<Motorizado> getAll() {
		return motorizadoService.getAll();
	}
}
