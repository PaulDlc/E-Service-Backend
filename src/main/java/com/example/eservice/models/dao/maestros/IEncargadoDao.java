package com.example.eservice.models.dao.maestros;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eservice.models.entity.maestros.EncargadoSede;

public interface IEncargadoDao extends JpaRepository<EncargadoSede, Integer> {

}