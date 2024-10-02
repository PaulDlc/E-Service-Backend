package com.example.eservice.models.service.entregas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.entregas.IObservacionesEntrega;
import com.example.eservice.models.entity.entregas.ObservacionesEntrega;

@Service
public class ObservacionesEntregaServiceImpl implements IObservacionesEntregaService {
	@Autowired
	private IObservacionesEntrega obsDao;

	@Override
	public ObservacionesEntrega save(ObservacionesEntrega observacion) {
		return obsDao.save(observacion);
	}
}
