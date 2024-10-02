package com.example.eservice.controller.maestros;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.maestros.Cliente;
import com.example.eservice.models.entity.maestros.EncargadoSede;
import com.example.eservice.models.entity.maestros.SedeCliente;
import com.example.eservice.models.entity.seguridad.Usuario;
import com.example.eservice.models.entity.seguridad.UsuarioResponse;
import com.example.eservice.models.service.IEmailService;
import com.example.eservice.models.service.maestros.IClienteService;
import com.example.eservice.models.service.maestros.IEncargadoSedeService;
import com.example.eservice.models.service.maestros.ISedeClienteService;
import com.example.eservice.models.service.seguridad.IUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class ClienteRestController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private ISedeClienteService sedeService;
	
	@Autowired
	private IEncargadoSedeService encargadoService;

	@Autowired
	private IEmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@SuppressWarnings("unchecked")
	@PutMapping("/cliente/edit")
	public ResponseEntity<?> edit(@RequestBody Map<String, Object> body) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			Map<String, Object> clienteMap = (Map<String, Object>) body.get("cliente");
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			Cliente cliente = objectMapper.convertValue(clienteMap, Cliente.class);
			
			Usuario usuarioUpdate = usuarioService.findUsuarioById(Integer.parseInt(body.get("usuarioId").toString()));
			
			usuarioUpdate.setEmail(body.get("usuarioCorreo").toString());
			
			List<SedeCliente> sedes = cliente.getSedes();
			
			for (SedeCliente sede: sedes) {
				List<EncargadoSede> encargados = sede.getEncargados();
				
				for(EncargadoSede encargado: encargados) {
					encargado = encargadoService.save(encargado);
				}
				
				sede = sedeService.save(sede);
			}
			
			cliente.setSedes(sedes);
			
			clienteService.save(cliente);
			usuarioService.saveUsuario(usuarioUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error con el servidor.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}
	
	@GetMapping("/cliente/usr/{usuarioId}")
	public Cliente getClienteByUsuario(@PathVariable Integer usuarioId) {
		return clienteService.getClienteByUserId(usuarioId);
	}
	
	@GetMapping("/cliente/sedes/{id}")
	public List<SedeCliente> getSedeByCliente(@PathVariable Integer id) {
		return clienteService.getClienteById(id).getSedes();
	}
	
	@GetMapping("/cliente/find/{id}")
	public Map<String, Object> getClienteById(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		Cliente cliente = clienteService.getClienteById(id);
		UsuarioResponse usuario = usuarioService.findUsuarioResponseById(cliente.getUsuario());
		
		response.put("usuario", usuario);
		response.put("cliente", cliente);
		
		return response;
	}
	
	@PutMapping("/cliente/desactivar-cliente/{clienteId}")
	public ResponseEntity<?> desactivateCliente(@PathVariable Integer clienteId) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, Object> datos = clienteService.getDatosActivacionUsuario(clienteId);
			
			Integer usuarioId = Integer.parseInt(datos.get("usuario").toString());
			
			clienteService.desactivarUsuario(usuarioId);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error con el servidor.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/cliente/activar-cliente/{clienteId}")
	public ResponseEntity<?> activateCliente(@PathVariable Integer clienteId) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Map<String, Object> datos = clienteService.getDatosActivacionUsuario(clienteId);
			
			String content = "Hola "+datos.get("nombre_completo").toString() +"\n"
	 	    		+ "¡Felicitaciones! Su cuenta se creó exitosamente."+"\n"
	 	    		+ "Su nombre de usuario es: "+datos.get("username").toString()+"\n"
	 	    		+ "\n"
	 	    		+ "Cordialmente,\n"
	 	    		+ "E-Service.";
			
			emailService.sendEmail(datos.get("email").toString(), "Registro exitoso de cuenta", content, "", null, null);
			
			String psswrd = passwordEncoder.encode(datos.get("password").toString());
			Integer usuarioId = Integer.parseInt(datos.get("usuario").toString());
			
			clienteService.activarUsuario(usuarioId, psswrd);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error con el servidor.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/cliente/all/listado")
	public List<Map<String, Object>> getAllListado() {
		return clienteService.getAllClientes();
	}
	
	@GetMapping("/cliente/listado")
	public List<Map<String, Object>> getListado(
				@RequestParam(value="nroDocumento", defaultValue="", required=false) String nroDocumento,
				@RequestParam(value="razonSocial", defaultValue="", required=false) String razonSocial,
				@RequestParam(value="nombreComercial", defaultValue="", required=false) String nombreComercial,
				@RequestParam(value="page", required=true) Integer page
			) {
		return clienteService.getListadoUsuario(nroDocumento, razonSocial, nombreComercial, page, 30);
	}
	
	@PostMapping("/cliente/save")
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		Integer id = clienteService.getIdByNroDocumento(cliente.getNroDocumento());
		
		if(id != null) {
			response.put("mensaje", "Tienda ya registrada con el número de documento.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CONFLICT);
		}
		
		try {
			List<SedeCliente> sedes = cliente.getSedes();
			
			for (SedeCliente sede: sedes) {
				List<EncargadoSede> encargados = sede.getEncargados();
				
				for(EncargadoSede encargado: encargados) {
					encargado = encargadoService.save(encargado);
				}
				
				sede = sedeService.save(sede);
			}
			
			cliente.setSedes(sedes);
			
			clienteService.save(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error con el servidor.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}

    @SuppressWarnings("deprecation")
	public static <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T instance = clazz.newInstance();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance, value);
            } catch (NoSuchFieldException e) {
                System.out.println("Campo no encontrado: " + fieldName);
            }
        }

        return instance;
    }
}