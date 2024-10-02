package com.example.eservice.models.service.maestros;

import java.util.List;

import com.example.eservice.models.entity.maestros.Ruta;

public interface IRutaService {
	List<Ruta> getAll();
	Ruta save(Ruta ruta);
	Ruta getById(Integer id);
	List<Integer> getDistritosIdFromRuta(Integer rutaId);
	void asignarDistritoRuta(Integer distritoId, Integer rutaId);
	void deleteRuta(Integer rutaId);
	List<Integer> getIdsByDistrito(Integer distritoId);
	List<Ruta> getListRutaByIds(List<Integer> ids);
}