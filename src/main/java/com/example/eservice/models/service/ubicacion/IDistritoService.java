package com.example.eservice.models.service.ubicacion;

import java.util.List;

import com.example.eservice.models.entity.ubicacion.Distrito;

public interface IDistritoService {
	Distrito save(Distrito distrito);
	List<Distrito> getDistritoSelect(Integer ind);
}