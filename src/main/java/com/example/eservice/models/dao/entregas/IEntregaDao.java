package com.example.eservice.models.dao.entregas;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.entregas.Entrega;

public interface IEntregaDao extends JpaRepository<Entrega, Integer> {
	
	@Query(value="EXEC entregas_upd_entregas_by_tienda ?1 , ?2", nativeQuery=true)
	void updateRutaRecojo(Integer clienteId, Integer userId);
	
	@Query(value="EXEC entregas_sel_listado_motorizado_recojo ?1", nativeQuery=true)
	List<Map<String, Object>> listPedidosForMotorizadoRecojo(Integer userId);
	
	@Query(value="EXEC entregas_sel_listado_motorizado ?1 , ?2", nativeQuery=true)
	List<Map<String, Object>> listPedidosForMotorizado(Integer userId, Integer clienteId);
	
	@Query(value="EXEC entregas_sel_listado_motorizado_entrega ?1", nativeQuery=true)
	List<Map<String, Object>> listPedidosForMotorizadoEntrega(Integer userId);
	
	@Query(value="EXEC entregas_sel_listado_motorizado_inventario ?1", nativeQuery=true)
	List<Map<String, Object>> listPedidosForMotorizadoInventario(Integer userId);
	
	@Query(value="SELECT DISTINCT cli.id, razon_social\r\n"
			+ "FROM mae_cliente cli\r\n"
			+ "JOIN ent_entrega ent ON ent.cliente_id = cli.id AND (ent.evidencia IS NULL OR ent.evidencia_cobro IS NULL)", nativeQuery=true)
	List<Map<String, Object>> getListClientesSinEvidencia();
	
	@Query(value="SELECT "
			+ "	FORMAT(ent.id, '00000000') as nro_pedido, "
			+ "	FORMAT(ent.fecha_entrega, 'dd MMM yyyy', 'es-ES') as fecha,"
			+ " ent.nombres,"
			+ " ent.evidencia,"
			+ " ent.evidencia_cobro, "
			+ " ent.monto_cobro,"
			+ " ent.precio "
			+ "FROM ent_entrega ent "
			+ "WHERE ent.cliente_id = ?1 AND (ent.evidencia IS NULL OR ent.evidencia_cobro IS NULL)", nativeQuery=true)
	List<Map<String, Object>> getEntregasSinEvidenciaByCliente(Integer clienteId);
	
	@Query("SELECT e FROM Entrega e WHERE e.id = ?1")
	Entrega getEntregaById(Integer id);
	
	@Query("SELECT COUNT(e.id) "
			+ " FROM Entrega e "
			+ " WHERE e.cliente.id = ?1 "
			+ " AND (( ?2 IS NULL) OR (e.fechaEntrega = ?2 )) "
			+ " AND (( ?3 = '') OR (UPPER(e.nombres) LIKE UPPER(CONCAT('%', ?3 , '%')) ))")
	Integer getCantEntregasByCliente(Integer clienteId, Date fecha, String razonSocial);
	
	@Query(value="EXEC entregas_sel_listado_cliente ?1 , ?2 , ?3 , ?4 , ?5, ?6", nativeQuery=true)
	List<Map<String, Object>> getEntregasByCliente(Integer clienteId, String fecha, String razonSocial, String codigo, Integer page, Integer size);
	
	@Query(value="EXEC sel_entregas_saldos ?1 , ?2", nativeQuery=true)
	List<Map<String, Object>> getSaldoByCliente(Integer clienteId, String fecha);
	
	@Query(value="EXEC entregas_insert_masivo\r\n"
			+ "	@celular_alt = ?12,\r\n"
			+ "	@observacion = ?13,\r\n"
			+ "	@fecha = ?1,\r\n"
			+ "	@monto = ?2, \r\n"
			+ "	@nombreCompleto = ?3, \r\n"
			+ "	@celular = ?4, \r\n"
			+ "	@distrito = ?5, \r\n"
			+ "	@direccion = ?6, \r\n"
			+ "	@referencia = ?7, \r\n"
			+ "	@gps = ?8,\r\n"
			+ "	@cliente_id = ?9, "
			+ " @sede_id = ?10,"
			+ " @producto = ?11", nativeQuery=true)
	Map<String, Object> insertMasivo(String fecha, String monto, String nombreCompleto, String celular, String distrito, String direccion, 
			String referencia, String gps, Integer cliente_id, Integer sede_id, String producto, String celularAlt, String observacion);
	
	@Query(value="EXEC entregas_sel_aprobaciones_diarias ?1", nativeQuery=true)
	List<Map<String, Object>> getAprobacionesDiarias(String fecha);
	
	@Query(value="EXEC entregas_update_aprobacion ?1", nativeQuery=true)
	void aprobarEntrega(Integer id);
	
	@Query(value="EXEC sel_list_entregas 	@nombreComercial = ?1 ,\r\n"
			+ "	@nombreDistrito = ?2 ,\r\n"
			+ "	@fechaDesde = ?3 ,\r\n"
			+ "	@fechaHasta = ?4 ,\r\n"
			+ "	@codigo = ?5 ,\r\n"
			+ "	@page = ?6 ,\r\n"
			+ "	@size = ?7", nativeQuery=true)
	List<Map<String, Object>> getListadoEntregas(String nombreComercial, String distrito, String fechaDesde, String fechaHasta, String codigo, Integer page, Integer size);
}