package com.example.eservice.models.service.seguridad;

import java.util.List;

import com.example.eservice.models.entity.seguridad.SubModulo;

public interface ISubModuloService {

	public List<SubModulo> findAll();
	
	public List<SubModulo> findAllByIdModulo(Integer id);

}