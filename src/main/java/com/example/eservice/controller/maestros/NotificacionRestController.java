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

import com.example.eservice.models.service.maestros.INotificacionService;

@RestController
@RequestMapping("/api")
public class NotificacionRestController {
	@Autowired
	private INotificacionService notService;
	
	@PutMapping("/notificacion/atender/{id}")
	public ResponseEntity<?> atender(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		
		notService.atender(id);
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}
	
	@GetMapping("/notificacion/list/{idUsuario}")
	public List<Map<String, Object>> getNotifications(@PathVariable Integer idUsuario) {
		return notService.getNotifications(idUsuario);
	}
	
	@GetMapping("/notificacion/cant/{idUsuario}")
	public Integer getCantNotifications(@PathVariable Integer idUsuario) {
		return notService.getCantNotifications(idUsuario);
	}
}