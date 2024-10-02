package com.example.eservice.controller.maestros;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.maestros.Ruta;
import com.example.eservice.models.service.maestros.IRutaService;

@RestController
@RequestMapping("/api")
public class RutaRestController {
	
	@Autowired
	private IRutaService rutaService;
	
	@GetMapping("/ruta/get-list/distrito/{distritoId}")
	public List<Ruta> getListByDistrito(@PathVariable Integer distritoId) {
		List<Integer> rutasId = rutaService.getIdsByDistrito(distritoId);
		return rutaService.getListRutaByIds(rutasId);
	}
	
	@PutMapping("/ruta/delete/{rutaId}")
	public ResponseEntity<?> deleteRuta(@PathVariable Integer rutaId) {
		Map<String, Object> response = new HashMap<>();
		
		rutaService.deleteRuta(rutaId);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ruta/all")
	public List<Ruta> getAll() {
		return rutaService.getAll();
	}
	
	@PutMapping("/ruta/asignacion-distrito/{distritoId}/{rutaId}")
	public ResponseEntity<?> asignacionRuta(@PathVariable Integer distritoId, @PathVariable Integer rutaId) {
		Map<String, Object> response = new HashMap<>();
		
		rutaService.asignarDistritoRuta(distritoId, rutaId);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/ruta/distritos-id/{rutaId}")
	public List<Integer> getRutasId(@PathVariable Integer rutaId) {
		
		return rutaService.getDistritosIdFromRuta(rutaId);
		
	}
	
	@PutMapping("/ruta/update")
	public ResponseEntity<?> update(@RequestBody Ruta ruta) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Ruta updateRuta = rutaService.getById(ruta.getId());
			updateRuta.setNombre(ruta.getNombre());
			updateRuta = rutaService.save(updateRuta);
			response.put("ruta", updateRuta);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", e.getCause().getMessage());
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CREATED);
	}
	
	@PostMapping("/ruta/create")
	public ResponseEntity<?> create(@RequestBody Ruta ruta) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Ruta newRuta = rutaService.save(ruta);
			response.put("ruta", newRuta);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", e.getCause().getMessage());
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CREATED);
	}
}