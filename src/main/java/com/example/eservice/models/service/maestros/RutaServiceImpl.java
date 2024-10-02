package com.example.eservice.models.service.maestros;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.maestros.IRutaDao;
import com.example.eservice.models.entity.maestros.Ruta;

@Service
public class RutaServiceImpl implements IRutaService {
	
	@Autowired
	private IRutaDao rutaDao;

	@Override
	public List<Ruta> getAll() {
		return rutaDao.findAll();
	}

	@Override
	public Ruta save(Ruta ruta) {
		return rutaDao.save(ruta);
	}

	@Override
	public Ruta getById(Integer id) {
		return rutaDao.getById(id);
	}

	@Override
	public List<Integer> getDistritosIdFromRuta(Integer rutaId) {
		return rutaDao.getDistritosIdFromRuta(rutaId);
	}

	@Override
	public void asignarDistritoRuta(Integer distritoId, Integer rutaId) {
		rutaDao.asignarDistritoRuta(distritoId, rutaId);
	}

	@Override
	public void deleteRuta(Integer rutaId) {
		rutaDao.deleteRuta(rutaId);
	}

	@Override
	public List<Integer> getIdsByDistrito(Integer distritoId) {
		return rutaDao.getIdsByDistrito(distritoId);
	}

	@Override
	public List<Ruta> getListRutaByIds(List<Integer> ids) {
		return rutaDao.getListRutaByIds(ids);
	}
}