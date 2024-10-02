package com.example.eservice.models.service.maestros;

import java.util.List;
import java.util.Map;

import com.example.eservice.models.entity.maestros.Cliente;

public interface IClienteService {
	Cliente getClienteByUserId(Integer id);
	Cliente getClienteById(Integer id);
	void activarUsuario(Integer id, String psswrd);
	void desactivarUsuario(Integer id);
	Map<String, Object> getDatosActivacionUsuario(Integer id);
	Cliente save(Cliente cliente);
	Integer getIdByNroDocumento(String nroDocumento);
	List<Map<String, Object>> getListadoUsuario(String nroDocumento, String razonSocial, String nombreComercial, Integer page, Integer size);
	List<Map<String, Object>> getAllClientes();
}