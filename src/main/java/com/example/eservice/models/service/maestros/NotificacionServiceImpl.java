package com.example.eservice.models.service.maestros;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.INotificacionDao;
import com.example.eservice.models.entity.maestros.Notificacion;

@Service
public class NotificacionServiceImpl implements INotificacionService {
	
	@Autowired
	private INotificacionDao notificacionDao;

	@Override
	public Notificacion save(Notificacion notificacion) {
		return notificacionDao.save(notificacion);
	}

	@Override
	public Integer getCantNotifications(Integer idUsuario) {
		return notificacionDao.getCantNotifications(idUsuario);
	}

	@Override
	public List<Map<String, Object>> getNotifications(Integer idUsuario) {
		return notificacionDao.getNotifications(idUsuario);
	}

	@Override
	public void atender(Integer id) {
		notificacionDao.atender(id);
	}
}