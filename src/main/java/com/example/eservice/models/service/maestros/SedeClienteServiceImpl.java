package com.example.eservice.models.service.maestros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.ISedeClienteDao;
import com.example.eservice.models.entity.maestros.SedeCliente;

@Service
public class SedeClienteServiceImpl implements ISedeClienteService {
	@Autowired
	private ISedeClienteDao sedeDao;

	@Override
	public SedeCliente save(SedeCliente sede) {
		return sedeDao.save(sede);
	}
	
	
}