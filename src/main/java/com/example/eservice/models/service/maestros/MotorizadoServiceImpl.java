package com.example.eservice.models.service.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.IMotorizadoDao;
import com.example.eservice.models.entity.maestros.Motorizado;

@Service
public class MotorizadoServiceImpl implements IMotorizadoService {
	
	@Autowired
	private IMotorizadoDao motorizadoDao;

	@Override
	public List<Motorizado> getAll() {
		return motorizadoDao.findAll();
	}

	@Override
	public void asignarRutaMotorizado(Integer motorizadoId, Integer rutaId) {
		motorizadoDao.asignarRutaMotorizado(motorizadoId, rutaId);
	}

	@Override
	public Motorizado getById(Integer id) {
		return motorizadoDao.getById(id);
	}

	@Override
	public List<Integer> getRutasIdFromMotorizado(Integer motorizadoId) {
		return motorizadoDao.getRutasIdFromMotorizado(motorizadoId);
	}

}