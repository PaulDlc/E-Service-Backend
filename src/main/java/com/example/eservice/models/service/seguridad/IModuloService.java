package com.example.eservice.models.service.seguridad;

import java.util.List;

import com.example.eservice.models.entity.seguridad.Modulo;

public interface IModuloService {
	List<Modulo> findAll();
	List<Modulo> findModulosByUsername(String username);
}