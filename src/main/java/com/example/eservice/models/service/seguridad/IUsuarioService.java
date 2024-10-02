package com.example.eservice.models.service.seguridad;

import java.util.List;
import java.util.Map;

import com.example.eservice.models.entity.seguridad.PasswordResetToken;
import com.example.eservice.models.entity.seguridad.Usuario;
import com.example.eservice.models.entity.seguridad.UsuarioResponse;

public interface IUsuarioService {
	List<Integer> getAyuda();
	UsuarioResponse findUsuarioResponseByUsername(String username);
	Usuario findByUsername(String username);
	Integer findIdByUsernameAndPassword(String username, String pswd);
	void savePswd(Integer id, String pswd);
	
	Map<String, Object> registrarUsuario(String json);
	
	UsuarioResponse findUsuarioResponseById(Integer id);
	
	public Usuario findUsuarioById(Integer id);
	
	public Usuario saveUsuario(Usuario usuario);
	
	public void createPasswordResetTokenForUsuario(Usuario usuario, String token);
	
	public PasswordResetToken findByToken(String token);
	
	public Usuario findUsuarioByCorreo(String correo, Integer user_id);

	List<Map<String, Object>> listMaestro(String nombre, String username, String correo, Integer indVerInactivos);

	void inactivarUsuario(Integer id, Integer ind);
	
	Integer validUsuarioExistente(String username, String correo);
}