package com.example.eservice.models.service.ubicacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.ubicacion.IDistritoDao;
import com.example.eservice.models.entity.ubicacion.Distrito;

@Service
public class DistritoServiceImpl implements IDistritoService {
	
	@Autowired
	private IDistritoDao distritoDao;

	@Override
	public Distrito save(Distrito distrito) {
		return distritoDao.save(distrito);
	}

	@Override
	public List<Distrito> getDistritoSelect(Integer ind) {
		return distritoDao.getDistritoSelect(ind);
	}
}