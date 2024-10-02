package com.example.eservice.models.dao.auxiliares;

import org.springframework.data.repository.CrudRepository;

import com.example.eservice.models.entity.auxiliares.TablaAuxiliar;

public interface ITablaAuxiliarDao extends CrudRepository<TablaAuxiliar, String>{
	
	public TablaAuxiliar findByCodTablaAuxiliar(String codTablaAuxiliar);
	
}