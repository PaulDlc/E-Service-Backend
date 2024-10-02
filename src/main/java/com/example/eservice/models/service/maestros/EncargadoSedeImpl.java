package com.example.eservice.models.service.maestros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.IEncargadoDao;
import com.example.eservice.models.entity.maestros.EncargadoSede;

@Service
public class EncargadoSedeImpl implements IEncargadoSedeService {
	
	@Autowired
	private IEncargadoDao encargadoDao;

	@Override
	public EncargadoSede save(EncargadoSede encargado) {
		return encargadoDao.save(encargado);
	}

}