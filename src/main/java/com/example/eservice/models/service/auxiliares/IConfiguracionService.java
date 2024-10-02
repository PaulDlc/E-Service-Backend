package com.example.eservice.models.service.auxiliares;

import java.util.List;

import com.example.eservice.models.entity.auxiliares.TablaAuxiliar;
import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;

public interface IConfiguracionService {
	public TablaAuxiliarDetalle findTablaAuxiliarDetalleById(Integer id, String codTablaAuxiliar);

	public TablaAuxiliarDetalle findTablaAuxiliarDetalleByNombre(String nombre, String codTablaAuxiliar);
	
	public List<String> listTablaAuxiliarDetalleByCodigo(String codTablaAuxiliar);
	
	public List<TablaAuxiliarDetalle> autocompleteList(String codTablaAuxiliar, String nombre);
	
	public List<TablaAuxiliarDetalle> listTablaAuxiliarDetalleDropdownByCodigo(String codTablaAuxiliar);
	
	public List<TablaAuxiliarDetalle> listTablaAuxiliarDetalleDropdownByCodigoMerma(String codTablaAuxiliar, Integer id);
	
	public TablaAuxiliarDetalle findById(Integer id, String codTablaAuxiliar);
	
	public List<TablaAuxiliar> findAll();
	public TablaAuxiliar findByCodTablaAux(String cod);
	
	public List<TablaAuxiliarDetalle> listTablaAuxiliarDetalleByCodigoAuxiliar(String codTablaAuxiliar);
	
	public TablaAuxiliarDetalle save(TablaAuxiliarDetalle tablaAuxiliarDetalle);

}