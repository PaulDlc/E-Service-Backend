package com.example.eservice.models.service.maestros;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.IClienteDao;
import com.example.eservice.models.entity.maestros.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {
	
	@Autowired
	private IClienteDao clienteDao;

	@Override
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	public Integer getIdByNroDocumento(String nroDocumento) {
		return clienteDao.getIdByNroDocumento(nroDocumento);
	}

	@Override
	public List<Map<String, Object>> getListadoUsuario(String nroDocumento, String razonSocial, String nombreComercial, Integer page,
			Integer size) {
		return clienteDao.getListadoUsuario(nroDocumento, razonSocial, nombreComercial, page, size);
	}

	@Override
	public Map<String, Object> getDatosActivacionUsuario(Integer id) {
		return clienteDao.getDatosActivacionUsuario(id);
	}

	@Override
	public void activarUsuario(Integer id, String psswrd) {
		clienteDao.activarUsuario(id, psswrd);
	}

	@Override
	public void desactivarUsuario(Integer id) {
		clienteDao.desactivarUsuario(id);
	}

	@Override
	public Cliente getClienteById(Integer id) {
		return clienteDao.getClienteById(id);
	}

	@Override
	public Cliente getClienteByUserId(Integer id) {
		return clienteDao.getClienteByUserId(id);
	}

	@Override
	public List<Map<String, Object>> getAllClientes() {
		return clienteDao.getAllClientes();
	}
}