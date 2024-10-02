package com.example.eservice.models.dao.maestros;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.maestros.Notificacion;

public interface INotificacionDao extends JpaRepository<Notificacion, Integer>{
	@Query(value="SELECT COUNT(id) FROM mae_notificacion WHERE id_usuario = ?1 AND ind_atendido = 0", nativeQuery = true)
	Integer getCantNotifications(Integer idUsuario);
	
	@Query(value="UPDATE mae_notificacion SET ind_atendido = 1 WHERE id = ?1 ; SELECT 1", nativeQuery = true)
	void atender(Integer id);
	
	@Query(value="SELECT \r\n"
			+ "noti.*, usu.nombre as usuario, 0 as ind_responder, '' as text_resp\r\n"
			+ "FROM mae_notificacion noti\r\n"
			+ "JOIN seg_usuario usu ON usu.id = noti.id_emisor\r\n"
			+ "WHERE noti.id_usuario = ?1 "
			+ "ORDER BY noti.id DESC", nativeQuery = true)
	List<Map<String, Object>> getNotifications(Integer idUsuario);
}