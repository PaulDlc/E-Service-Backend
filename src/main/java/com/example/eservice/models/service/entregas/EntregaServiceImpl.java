package com.example.eservice.models.service.entregas;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.helper.ExcelAprobaciones;
import com.example.eservice.models.dao.entregas.IEntregaDao;
import com.example.eservice.models.entity.entregas.Entrega;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@Service
public class EntregaServiceImpl implements IEntregaService {
	
	@Autowired
	private IEntregaDao entregaDao;

	@Override
	public Entrega save(Entrega entrega) {
		return entregaDao.save(entrega);
	}

	@Override
	public List<Map<String, Object>> getListadoEntregas(String nombreComercial, String distrito, String fechaDesde, String fechaHasta, String codigo, Integer page, Integer size) {
		return entregaDao.getListadoEntregas(nombreComercial, distrito, fechaDesde, fechaHasta, codigo, page, size);
	}

	@Override
	public void aprobarEntrega(Integer id) {
		entregaDao.aprobarEntrega(id);
	}

	@Override
	public void reporteAprobacionesDiarias(String fecha) throws IOException, InvalidFormatException {
		List<Map<String, Object>> aprobaciones = entregaDao.getAprobacionesDiarias(fecha);
		ExcelAprobaciones.generarProtocolo(aprobaciones);
	}

	@Override
	public Map<String, Object> insertMasivo(String fecha, String monto, String nombreCompleto, String celular, String distrito, String direccion, 
			String referencia, String gps, Integer cliente_id, Integer sede_id, String producto, String celularAlt, String observacion) {
		return entregaDao.insertMasivo(fecha, monto, nombreCompleto, celular, distrito, direccion, referencia, gps, cliente_id, sede_id, producto, celularAlt, observacion);
	}

	@Override
	public List<Map<String, Object>> getEntregasByCliente(Integer clienteId, String fecha, String razonSocial, String codigo, Integer page, Integer size) {
		return entregaDao.getEntregasByCliente(clienteId, fecha, razonSocial, codigo, page, size);
	}

	@Override
	public Integer getCantEntregasByCliente(Integer clienteId, Date fecha, String razonSocial) {
		return entregaDao.getCantEntregasByCliente(clienteId, fecha, razonSocial);
	}

	@Override
	public Entrega getEntregaById(Integer id) {
		return entregaDao.getEntregaById(id);
	}

	@Override
	public List<Map<String, Object>> getListClientesSinEvidencia() {
		return entregaDao.getListClientesSinEvidencia();
	}

	@Override
	public List<Map<String, Object>> getEntregasSinEvidenciaByCliente(Integer clienteId) {
		return entregaDao.getEntregasSinEvidenciaByCliente(clienteId);
	}

	@Override
	public List<Map<String, Object>> listPedidosForMotorizado(Integer userId, Integer clienteId) {
		return entregaDao.listPedidosForMotorizado(userId, clienteId);
	}

	@Override
	public List<Map<String, Object>> listPedidosForMotorizadoEntrega(Integer userId) {
		return entregaDao.listPedidosForMotorizadoEntrega(userId);
	}

	@Override
	public List<Map<String, Object>> listPedidosForMotorizadoInventario(Integer userId) {
		return entregaDao.listPedidosForMotorizadoInventario(userId);
	}

	@Override
	public List<Map<String, Object>> listPedidosForMotorizadoRecojo(Integer userId) {
		return entregaDao.listPedidosForMotorizadoRecojo(userId);
	}

	@Override
	public void updateRutaRecojo(Integer clienteId, Integer userId) {
		entregaDao.updateRutaRecojo(clienteId, userId);
	}

	@Override
	public List<Map<String, Object>> getSaldoByCliente(Integer clienteId, String fecha) {
		return entregaDao.getSaldoByCliente(clienteId, fecha);
	}
	
}