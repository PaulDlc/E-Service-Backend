package com.example.eservice.controller.auxiliar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.eservice.models.entity.auxiliares.TablaAuxiliar;
import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.example.eservice.models.service.auxiliares.IConfiguracionService;

@RestController
@RequestMapping("/api")
public class TablaAuxiliarDetalleRestController {
	
	@Autowired
	private IConfiguracionService configuracionService;
	
	@GetMapping("/tablaAuxiliar/get-cod/{codAux}")
	public TablaAuxiliar getByCodAux(@PathVariable String codAux) {
		return configuracionService.findByCodTablaAux(codAux);
	}
	
	@GetMapping("/tabla_auxiliar_detalle/nombre")
	public ResponseEntity<?> show(
	  @RequestParam(value = "codigo" ,required = true ) String codTablaAuxiliar,
	  @RequestParam(value = "nombre" ,required = true ) String nombre) {
		
		TablaAuxiliarDetalle estado = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			estado = configuracionService.findTablaAuxiliarDetalleByNombre(nombre, codTablaAuxiliar);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (estado == null) {
			response.put("mensaje", "El estado no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TablaAuxiliarDetalle>(estado, HttpStatus.OK); 
	}
	
	@GetMapping("/tabla_auxiliar_detalle/id")
	public ResponseEntity<?> showI(
	  @RequestParam(value = "codigo" ,required = true ) String codTablaAuxiliar,
	  @RequestParam(value = "id" ,required = true ) Integer id) {
		
		TablaAuxiliarDetalle estado = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			estado = configuracionService.findTablaAuxiliarDetalleById(id, codTablaAuxiliar);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (estado == null) {
			response.put("mensaje", "El estado no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TablaAuxiliarDetalle>(estado, HttpStatus.OK); 
	}
	
	@GetMapping("/tabla_auxiliar_detalle/autocomplete/{codTablaAuxiliar}/{nombre}")
	public List<TablaAuxiliarDetalle> autocompletado(
	  @RequestParam(value = "codigo" ,required = true ) String codTablaAuxiliar,
	  @RequestParam(value = "term" ,required = true ) String term) {
		return configuracionService.autocompleteList(codTablaAuxiliar, term);
	}
	
	@GetMapping("/tabla_auxiliar_detalle/combo_box/{codTablaAuxiliar}")
	public List<TablaAuxiliarDetalle> comboBox(@PathVariable String codTablaAuxiliar) {
		return configuracionService.listTablaAuxiliarDetalleDropdownByCodigo(codTablaAuxiliar);
	}
	
	@GetMapping("/tabla_auxiliar_detalle/listaXCodAux/{codTablaAuxiliar}")
	public List<TablaAuxiliarDetalle> listaTablaAuxDetByCod(@PathVariable String codTablaAuxiliar) {
		return configuracionService.listTablaAuxiliarDetalleByCodigoAuxiliar(codTablaAuxiliar);
	}
	
	@PutMapping("/tabla_auxiliar_detalle/update/{nombre}/{cod_tabla_auxiliar}")
	public ResponseEntity<?> update(@Valid @RequestBody TablaAuxiliarDetalle tablaAuxiliarDetalle, BindingResult result, 
			@PathVariable String nombre, @PathVariable String cod_tabla_auxiliar){
		TablaAuxiliarDetalle tablaAuxiliarDetalleActual = configuracionService.findTablaAuxiliarDetalleByNombre(nombre, cod_tabla_auxiliar);
		
		TablaAuxiliarDetalle tablaAuxiliarDetalleUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(tablaAuxiliarDetalleActual == null) {
			response.put("mensaje", "Error, no se pudo editar: El tablaAuxiliarDetalle no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			tablaAuxiliarDetalleActual.setNombre(tablaAuxiliarDetalle.getNombre());
			tablaAuxiliarDetalleActual.setAbreviatura(tablaAuxiliarDetalle.getAbreviatura());
			tablaAuxiliarDetalleActual.setValor(tablaAuxiliarDetalle.getValor());
			tablaAuxiliarDetalleActual.setObservacion(tablaAuxiliarDetalle.getObservacion());
			tablaAuxiliarDetalleActual.setIndHabilitado(tablaAuxiliarDetalle.getIndHabilitado());
			
			tablaAuxiliarDetalleUpdate = configuracionService.save(tablaAuxiliarDetalleActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "tablaAuxiliarDetalle actualizado exitosamente");
		response.put("tablaAuxiliarDetalle", tablaAuxiliarDetalleUpdate);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/tabla_auxiliar_detalle")
	public ResponseEntity<?> create(@Valid @RequestBody TablaAuxiliarDetalle tablaAuxiliarDetalle, BindingResult result) {
		TablaAuxiliarDetalle tablaAuxiliarDetalleNew = null;
		
		Map<String, Object> response = new HashMap<>();
		
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			tablaAuxiliarDetalleNew = configuracionService.save(tablaAuxiliarDetalle);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la BD");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "tablaAuxiliarDetalleNew registrado exitosamente");
		response.put("tablaAuxiliarDetalle", tablaAuxiliarDetalleNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

}