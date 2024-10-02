package com.example.eservice.models.service.maestros;

import java.util.List;

import com.example.eservice.models.entity.maestros.Motorizado;

public interface IMotorizadoService {
	List<Motorizado> getAll();
	void asignarRutaMotorizado(Integer motorizadoId, Integer rutaId);
	Motorizado getById(Integer id);
	List<Integer> getRutasIdFromMotorizado(Integer motorizadoId);
}