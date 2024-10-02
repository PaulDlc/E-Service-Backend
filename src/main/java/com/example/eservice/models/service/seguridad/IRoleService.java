package com.example.eservice.models.service.seguridad;

import java.util.List;
import java.util.Map;

import com.example.eservice.models.entity.seguridad.Role;

public interface IRoleService {

	public List<Role> findAll();
	
	List<Role> getRolesAsignables();
	
	Role getRoleById(Integer id);

	List<Role> findByUserId(Integer userId);
	
	List<Role> autocompleteAsignacion(String term);

	List<Map<String, Object>> listadoRolesUsuarios(Integer usuarioId);

	void updateRolesUsuarios(String listRoles, Integer isUsuario);
}