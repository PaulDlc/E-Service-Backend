package com.example.eservice.models.dao.maestros;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eservice.models.entity.maestros.SedeCliente;

public interface ISedeClienteDao extends JpaRepository<SedeCliente, Integer> {

}