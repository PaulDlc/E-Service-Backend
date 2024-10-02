package com.example.eservice.controller.seguridad;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.VariablesGlobales;
import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.example.eservice.models.entity.seguridad.PasswordResetToken;
import com.example.eservice.models.entity.seguridad.Role;
import com.example.eservice.models.entity.seguridad.Usuario;
import com.example.eservice.models.service.IEmailService;
import com.example.eservice.models.service.auxiliares.IConfiguracionService;
import com.example.eservice.models.service.seguridad.IPasswordResetTokenService;
import com.example.eservice.models.service.seguridad.IRoleService;
import com.example.eservice.models.service.seguridad.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {
	
	@Autowired
	private IConfiguracionService confService;

	@Autowired
	private IEmailService emailService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IPasswordResetTokenService passwordResetTokenService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@SuppressWarnings("unchecked")
	@PostMapping("/usuario/create")
	public ResponseEntity<?> createUsuario(@RequestBody String body) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			
			Map<String, String> map = mapper.readValue(body, Map.class);
			
			Integer usuarios = usuarioService.validUsuarioExistente(
					map.get("username").toString(), 
					map.get("email").toString());
			
			if(usuarios > 0) {
				response.put("mensaje", "Ya existe un usuario con el username o correo ingresado");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			}
			
			Map<String, Object> token = usuarioService.registrarUsuario(body);
		    
		    String content = "Hola "+token.get("nombre_completo").toString() +"\n"
		 	    		+ "¡Felicitaciones! Su cuenta se creó exitosamente."+"\n"
		 	    		+ "Su nombre de usuario es: "+token.get("username").toString()+"\n"
		 	    		+ "Por favor de click sobre el enlace que figura a continuación para que usted genere su propia contraseña."+"\n"
		 	    		+ VariablesGlobales.RUTA_FRONTEND +"/user/reestablecerPassword/"+token.get("token").toString()+"\n"
		 	    		+ "\n"
		 	    		+ "Cordialmente"+ "\n"
		 	    		+ "E-Service";
		    
		    emailService.sendEmail(token.get("email").toString(), "Registro de Contraseña de usuario E-Service", content, "", null, null);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al registrar usuario.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/usuario/update/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Map<String, Object> body) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Integer usuarios = usuarioService.validUsuarioExistente(body.get("username").toString(), body.get("correo").toString());
			
			if(usuarios > 0) {
				response.put("mensaje", "Ya existe un usuario con el username o correo ingresado");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			}
			
			Usuario usuario = usuarioService.findUsuarioById(id);
			
			usuario.setUsername(body.get("username").toString());
			usuario.setEmail(body.get("correo").toString());
			
			usuarioService.saveUsuario(usuario);
			roleService.updateRolesUsuarios(body.get("roles").toString().replace("[", "").replace("]", ""), id);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error al actualizar usuario.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}	
	
	@PutMapping("/usuario/change-pswrd")
	public ResponseEntity<?> changedPasswd(@RequestBody Map<String, Object> body) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			String pastPswd = body.get("pastPswd").toString();
			String newPswd = body.get("newPswd").toString();
			String username = body.get("username").toString();
			Integer usr_id = Integer.parseInt(body.get("user_id").toString());
			
			Usuario usrO = usuarioService.findByUsername(username);
			
			if(!passwordEncoder.matches(pastPswd, usrO.getPassword())) {
				response.put("mensaje", "Contraseña actual incorrecta.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
			}
			
			newPswd = passwordEncoder.encode(newPswd);
			
			usuarioService.savePswd(usr_id, newPswd);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar usuario.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/usuario/inactivar/{id}/{ind}")
	public ResponseEntity<?> inactivar(@PathVariable Integer id, @PathVariable Integer ind) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			usuarioService.inactivarUsuario(id, ind);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar usuario.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@GetMapping("/usuario/list-maestro")
	public List<Map<String, Object>> listadoMaestro(@RequestParam(value="nombre", required=false, defaultValue="") String nombre,
									 @RequestParam(value="username", required=false, defaultValue="") String username,
									 @RequestParam(value="correo", required=false, defaultValue="") String correo,
									 @RequestParam(value="indVerInactivos", required=false, defaultValue="0") Integer indVerInactivos) {
		
		List<Map<String, Object>> listado = usuarioService.listMaestro(nombre, username, correo, indVerInactivos);
		List<Map<String, Object>> listadoReturn = new ArrayList<>();
		
		for(Map<String, Object> item: listado) {
			
			Map<String, Object> rol = roleService.listadoRolesUsuarios(Integer.parseInt(item.get("id").toString())).get(0);
			
			Role rolAux = roleService.getRoleById(Integer.parseInt(rol.get("seg_rol_id").toString()));
			
			TablaAuxiliarDetalle tipoDoc = confService.findById(Integer.parseInt(item.get("tipo_documento_id").toString()), "TDOCUS");
			
			Map<String, Object> newItem = new HashMap<>(item);
			
			newItem.put("rol", rolAux);
			newItem.put("tipDoc", tipoDoc);
			
			listadoReturn.add(newItem);
			
		}
		
		return listadoReturn;
	}

	@GetMapping("/role/asignables")
	public List<Role> getRolesAsignables() {
		return roleService.getRolesAsignables();
	}

	@GetMapping("/role/all")
	public List<Role> getAllModulo() {
		return roleService.findAll();
	}
	
	@PostMapping("/usuario/save-cliente")
	public ResponseEntity<?> saveUsuarioCliente(@RequestBody Usuario usuario) {
		Map<String, Object> response = new HashMap<>();
		
		Usuario userExist = usuarioService.findByUsername(usuario.getUsername());
		
		if(userExist != null) {
			
			response.put("mensaje", "Existe un usuario con el username ingresado. Por favor, ingrese otro username");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CONFLICT);
			
		}
		
		System.out.println(usuario.getPassword());
		
		TablaAuxiliarDetalle estado = confService.findById(0, "ESTUSU");
		Role rolCliente = roleService.getRoleById(3);
		
		List<Role> roles = new ArrayList<>();
		
		roles.add(rolCliente);
		usuario.setRoles(roles);
		usuario.setEnabled(false);
		usuario.setEstado(estado);
		
		Usuario user = usuarioService.saveUsuario(usuario);
		
		response.put("userId", user.getId());
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}

	@GetMapping("/usuario/comprobar/{token}")
	public ResponseEntity<?> showChangePasswordPage(@PathVariable String token) {

		Map<String, Object> response = new HashMap<>();

		PasswordResetToken passToken = usuarioService.findByToken(token);

		if (!isTokenValidado(passToken)) {
			response.put("ind", 0);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			response.put("ind", 1);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);
		}
	}

	@PutMapping("/usuario/changedPassword/{token}/{password}")
	public ResponseEntity<?> update(@PathVariable String token, @PathVariable String password) {

		PasswordResetToken passToken = usuarioService.findByToken(token);
		Usuario usuarioActual = null;
		Usuario usuarioUpdate = null;
		usuarioActual = usuarioService.findUsuarioById(passToken.getUser().getId());

		Map<String, Object> response = new HashMap<>();

		if (!isTokenValidado(passToken)) {
			response.put("mensaje", "Error, el token es invalido");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (usuarioActual == null) {
			response.put("mensaje", "Error, no se pudo editar: El usuario no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			usuarioActual.setPassword(passwordEncoder.encode(password));
			usuarioActual.setEnabled(true);
			usuarioUpdate = usuarioService.saveUsuario(usuarioActual);

			passToken.setEnabled(false);
			passToken.setUser(usuarioUpdate);
			passwordResetTokenService.save(passToken);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "ControlEnvioDetalle actualizada exitosamente");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	private boolean isTokenValidado(PasswordResetToken passToken) {
		Date fecha = new Date();

		if (passToken == null) {
			return false;
		}

		if (passToken.getUser() == null) {
			return false;
		}

		if (!passToken.getUser().getEnabled()) {
			return false;
		}

		if (passToken.getExpiryDate().getTime() < fecha.getTime()) {
			return false;
		}

		if (!passToken.getEnabled()) {
			return false;
		}

		return true;
	}
}