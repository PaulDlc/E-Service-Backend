package com.example.eservice.models.dao.ubicacion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eservice.models.entity.ubicacion.Departamento;

public interface IDepartamentoDao extends JpaRepository<Departamento, Integer> {
	
}