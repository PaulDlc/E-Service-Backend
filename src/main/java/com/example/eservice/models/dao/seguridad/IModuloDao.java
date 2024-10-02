package com.example.eservice.models.dao.seguridad;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eservice.models.entity.seguridad.Modulo;

public interface IModuloDao extends JpaRepository<Modulo, Integer> {
	
}