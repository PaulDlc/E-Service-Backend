package com.example.eservice.models.service.auxiliares;

import java.util.List;

import com.example.eservice.models.entity.auxiliares.Parametro;

public interface IParametroService {
	
	public Parametro getParametroById(Integer id);
	
	public Parametro getValorIntervalo();
	
	public List<Parametro> getAllParametros();
	
	public Parametro save(Parametro parametro);

}