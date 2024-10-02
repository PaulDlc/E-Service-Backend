package com.example.eservice.models.dao.seguridad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.seguridad.SubModulo;

public interface ISubModuloDao extends JpaRepository<SubModulo, Integer>{
	
	@Query("SELECT sm FROM SubModulo sm WHERE sm.modulo.id = ?1")
	List<SubModulo> getSubModuloByIdModulo(Integer id);
}