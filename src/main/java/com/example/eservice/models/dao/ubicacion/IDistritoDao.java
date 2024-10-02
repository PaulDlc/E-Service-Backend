package com.example.eservice.models.dao.ubicacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.ubicacion.Distrito;

public interface IDistritoDao extends JpaRepository<Distrito, Integer> {
	@Query("SELECT d FROM Distrito d WHERE d.estado = 1 AND (0 = ?1 OR d.indCobertura = 1) ")
	List<Distrito> getDistritoSelect(Integer ind);
	
}