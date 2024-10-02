package com.example.eservice.models.service.maestros;

import java.util.List;
import java.util.Map;

import com.example.eservice.models.entity.maestros.Notificacion;

public interface INotificacionService {
	Integer getCantNotifications(Integer idUsuario);
	void atender(Integer id);
	Notificacion save(Notificacion notificacion);
	List<Map<String, Object>> getNotifications(Integer idUsuario);
}