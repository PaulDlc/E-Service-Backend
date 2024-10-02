package com.example.eservice.models.dao.seguridad;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.seguridad.Role;

public interface IRoleDao extends JpaRepository<Role, Integer> {
	@Query("SELECT r FROM Role r WHERE r.id != 3")
	List<Role> getRolesAsignables();
	
	@Query("SELECT r FROM Role r WHERE r.id = ?1")
	Role getRoleById(Integer id);
	
	@Query("SELECT u.roles FROM Usuario u WHERE u.id = ?1")
	List<Role> findByUserId(Integer userId);

	@Query("SELECT r FROM Role r WHERE UPPER(r.nombreDetallado) LIKE UPPER(CONCAT('%', ?1 , '%'))")
	List<Role> autocompleteAsignacion(String term);
	
	@Query(value="SELECT rol.seg_rol_id, rl.nombre_detallado "
			+ "FROM seg_usuario_rol rol "
			+ "JOIN seg_rol rl ON rl.id = rol.seg_rol_id "
			+ "WHERE rol.seg_usuario_id = ?1", nativeQuery=true)
	List<Map<String, Object>> listadoRolesUsuarios(Integer usuarioId);
	
	@Query(value="EXEC web_m001_man_update_roles @listRoles = ?1 , @idUsuario = ?2", nativeQuery=true)
	void updateRolesUsuarios(String listRoles, Integer isUsuario);
}