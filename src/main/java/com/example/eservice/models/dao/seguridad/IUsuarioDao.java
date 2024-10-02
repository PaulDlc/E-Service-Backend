package com.example.eservice.models.dao.seguridad;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.eservice.models.entity.seguridad.Usuario;
import com.example.eservice.models.entity.seguridad.UsuarioResponse;

public interface IUsuarioDao extends JpaRepository<Usuario, Integer>{
	
	@Query(value="EXEC usuarios_insert_create_usuario_token @json = ?1", nativeQuery=true)
	Map<String, Object> registrarUsuario(String json);
	
	@Query("SELECT u FROM Usuario u WHERE u.username = ?1")
	Usuario findByUsername(String username);
	
	@Query(value="UPDATE seg_usuario SET [password] = ?2 WHERE id = ?1 SELECT 1", nativeQuery=true)
	void savePswd(Integer id, String pswd);
	
	@Query("SELECT u.id FROM Usuario u WHERE u.username = ?1 AND u.password = ?2")
	Integer findIdByUsernameAndPassword(String username, String pswd);

	@Query("SELECT u FROM Usuario u WHERE u.id = ?1")
	UsuarioResponse findUsuarioResponseById(Integer id);

	@Query("SELECT u FROM Usuario u WHERE u.username = ?1")
	UsuarioResponse findUsuarioResponseByUsername(String username);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	Usuario findUserByCorreo(String email, Integer user_id);
	
	@Query(value="SELECT usu.id FROM seg_usuario usu "
			+ " JOIN seg_usuario_rol urol ON urol.seg_usuario_id = usu.id "
			+ " JOIN seg_rol rol ON rol.id = urol.seg_rol_id "
			+ " WHERE usu.estado_id = 1 AND rol.id = 1", nativeQuery=true)
	List<Integer> getAyuda();
	
	@Query(value="EXEC usuarios_sel_listado_maestro @nombre = ?1 , @username = ?2 , @correo = ?3 , @indVerInactivos = ?4", nativeQuery=true)
	List<Map<String, Object>> listMaestro(String nombre, String username, String correo, Integer indVerInactivos);
	
	@Query(value="UPDATE seg_usuario SET estado_id = ?2 , enabled = ?2 WHERE id = ?1 ; SELECT 1", nativeQuery=true)
	void inactivarUsuario(Integer id, Integer ind);
	
	@Query("SELECT COUNT(u.id) FROM Usuario u WHERE u.username = ?1 OR u.email = ?2")
	Integer validUsuarioExistente(String username, String correo);
}