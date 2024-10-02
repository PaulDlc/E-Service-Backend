package com.example.eservice.controller.ubicacion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.ubicacion.Distrito;
import com.example.eservice.models.service.ubicacion.IDistritoService;

@RestController
@RequestMapping("/api")
public class DistritoRestController {
	
	@Autowired
	private IDistritoService distritoService;
	
	@GetMapping("/distrito/select/{ind}")
	public List<Distrito> getDistritoSelect(@PathVariable Integer ind) {
		return distritoService.getDistritoSelect(ind);
	}
	
	@PutMapping("/distrito/update")
	public ResponseEntity<?> update(@Valid @RequestBody Distrito distrito, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Distrito dist = distritoService.save(distrito);
			response.put("distrito", dist);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error en el servidor");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}