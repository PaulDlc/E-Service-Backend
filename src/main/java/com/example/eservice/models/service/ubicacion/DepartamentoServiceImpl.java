package com.example.eservice.models.service.ubicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.ubicacion.IDepartamentoDao;
import com.example.eservice.models.entity.ubicacion.Departamento;

@Service
public class DepartamentoServiceImpl implements IDepartamentoService {
	
	@Autowired
	private IDepartamentoDao departamentoDao;

	@Override
	public List<Departamento> getListado() {
		return departamentoDao.findAll();
	}

}