package com.example.eservice.models.dao.maestros;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.maestros.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Integer>{
	
	@Query("SELECT c FROM Cliente c WHERE c.usuario = ?1")
	Cliente getClienteByUserId(Integer id);
	
	@Query("SELECT c FROM Cliente c WHERE c.id = ?1")
	Cliente getClienteById(Integer id);
	
	@Query(value="UPDATE seg_usuario\r\n"
			+ "SET enabled = 0, estado_id = 0 \r\n"
			+ "WHERE id = ?1 "
			+ "SELECT 1", nativeQuery=true)
	void desactivarUsuario(Integer id);
	
	@Query(value="UPDATE seg_usuario\r\n"
			+ "SET enabled = 1, estado_id = 1, password = ?2 \r\n"
			+ "WHERE id = ?1 "
			+ "SELECT 1", nativeQuery=true)
	void activarUsuario(Integer id, String psswrd);
	
	@Query(value="SELECT \r\n"
			+ "	cli.razon_social AS nombre_completo,\r\n"
			+ "	usu.username,\r\n"
			+ "	usu.password, \r\n"
			+ "	usu.email,\r\n"
			+ "	cli.usuario\r\n"
			+ "FROM mae_cliente cli\r\n"
			+ "JOIN seg_usuario usu ON usu.id = cli.usuario\r\n"
			+ "WHERE cli.id = ?1", nativeQuery=true)
	Map<String, Object> getDatosActivacionUsuario(Integer id);
	
	@Query(value="SELECT id FROM mae_cliente WHERE nro_documento = ?1", nativeQuery=true)
	Integer getIdByNroDocumento(String nroDocumento);

	@Query(value="SELECT "
			+ " cli.id,"
			+ " cli.nro_documento,"
			+ " cli.razon_social,"
			+ "	cli.nombre_comercial,"
			+ " usu.id AS usuario_id, "
			+ " usu.estado_id "
			+ "FROM mae_cliente cli\r\n"
			+ "JOIN seg_usuario usu ON usu.id = cli.usuario", nativeQuery=true)
	List<Map<String, Object>> getAllClientes();

	@Query(value="SELECT "
			+ " cli.id,"
			+ "	tdoc.abreviatura AS tipo_doc,"
			+ " cli.nro_documento,"
			+ " cli.razon_social,"
			+ "	cli.nombre_comercial,"
			+ " usu.estado_id,"
			+ "	(SELECT COUNT(id) FROM mae_cliente) as total\r\n"
			+ "FROM mae_cliente cli\r\n"
			+ "JOIN seg_usuario usu ON usu.id = cli.usuario\r\n"
			+ "JOIN cfg_tabla_auxiliar_detalle tdoc ON tdoc.id = cli.tipo_documento_id AND tdoc.cod_tabla_auxiliar = cli.tipo_documento_cod_tabla_auxiliar\r\n"
			+ "WHERE UPPER(cli.nro_documento) LIKE UPPER('%' + ?1 + '%')\r\n"
			+ "AND UPPER(cli.razon_social) LIKE UPPER('%' + ?2 + '%')\r\n"
			+ "AND UPPER(cli.nombre_comercial) LIKE UPPER('%' + ?3 + '%')\r\n"
			+ "ORDER BY cli.id DESC\r\n"
			+ "OFFSET (?4 - 1) * ?5 ROWS\r\n"
			+ "FETCH NEXT ?5 ROWS ONLY", nativeQuery=true)
	List<Map<String, Object>> getListadoUsuario(String nroDocumento, String razonSocial, String nombreComercial, Integer page, Integer size);
}