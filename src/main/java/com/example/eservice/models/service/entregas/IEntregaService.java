package com.example.eservice.models.service.entregas;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.eservice.models.entity.entregas.Entrega;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public interface IEntregaService {
	List<Map<String, Object>> listPedidosForMotorizadoRecojo(Integer userId);
	List<Map<String, Object>> listPedidosForMotorizado(Integer userId, Integer clienteId);
	List<Map<String, Object>> listPedidosForMotorizadoEntrega(Integer userId);
	List<Map<String, Object>> listPedidosForMotorizadoInventario(Integer userId);
	List<Map<String, Object>> getListClientesSinEvidencia();
	List<Map<String, Object>> getEntregasSinEvidenciaByCliente(Integer clienteId);
	Entrega getEntregaById(Integer id);
	Integer getCantEntregasByCliente(Integer clienteId, Date fecha, String razonSocial);
	List<Map<String, Object>> getEntregasByCliente(Integer clienteId, String fecha, String razonSocial, String codigo, Integer page, Integer size);
	Map<String, Object> insertMasivo(String fecha, String monto, String nombreCompleto, String celular, String distrito, String direccion, 
			String referencia, String gps, Integer cliente_id, Integer sede_id, String producto, String celularAlt, String observacion);
	void reporteAprobacionesDiarias(String fecha) throws IOException, InvalidFormatException;
	void aprobarEntrega(Integer id);
	List<Map<String, Object>> getListadoEntregas(String nombreComercial, String distrito, String fechaDesde, String fechaHasta, String codigo, Integer page, Integer size);
	Entrega save(Entrega entrega);
	void updateRutaRecojo(Integer clienteId, Integer userId);
	List<Map<String, Object>> getSaldoByCliente(Integer clienteId, String fecha);
}