package com.example.eservice.models.dao.maestros;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.maestros.Motorizado;

public interface IMotorizadoDao extends JpaRepository<Motorizado, Integer> {
	@Query(value="EXEC motorizado_asignacion_ruta @motorizado_id = ?1 , @ruta_id = ?2", nativeQuery=true)
	void asignarRutaMotorizado(Integer motorizadoId, Integer rutaId);
	
	@Query(value="SELECT ruta_id FROM mae_ruta_motorizado WHERE motorizado_id = ?1", nativeQuery=true)
	List<Integer> getRutasIdFromMotorizado(Integer motorizadoId);
}