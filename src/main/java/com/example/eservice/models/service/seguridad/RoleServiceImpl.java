package com.example.eservice.models.service.seguridad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eservice.models.dao.seguridad.IRoleDao;
import com.example.eservice.models.entity.seguridad.Role;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;
	
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public Role getRoleById(Integer id) {
		return roleDao.getById(id);
	}

	@Override
	public List<Role> autocompleteAsignacion(String term) {
		return roleDao.autocompleteAsignacion(term);
	}

	@Override
	public List<Map<String, Object>> listadoRolesUsuarios(Integer usuarioId) {
		return roleDao.listadoRolesUsuarios(usuarioId);
	}

	@Override
	public void updateRolesUsuarios(String listRoles, Integer isUsuario) {
		roleDao.updateRolesUsuarios(listRoles, isUsuario);
	}

	@Override
	public List<Role> findByUserId(Integer userId) {
		return roleDao.findByUserId(userId);
	}

	@Override
	public List<Role> getRolesAsignables() {
		return roleDao.getRolesAsignables();
	}
}