package com.example.eservice.controller.entregas;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.eservice.VariablesGlobales;
import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.example.eservice.models.entity.entregas.ContactoEntrega;
import com.example.eservice.models.entity.entregas.Entrega;
import com.example.eservice.models.entity.entregas.ObservacionesEntrega;
import com.example.eservice.models.entity.entregas.ProblemasEntrega;
import com.example.eservice.models.entity.maestros.Cliente;
import com.example.eservice.models.entity.maestros.Motorizado;
import com.example.eservice.models.service.IUploadFileService;
import com.example.eservice.models.service.auxiliares.IConfiguracionService;
import com.example.eservice.models.service.entregas.IContactoService;
import com.example.eservice.models.service.entregas.IEntregaService;
//import com.example.eservice.models.service.entregas.IObservacionesEntregaService;
import com.example.eservice.models.service.maestros.IClienteService;
import com.example.eservice.models.service.maestros.IMotorizadoService;

@RestController
@RequestMapping("/api")
public class EntregaRestController {
	
	@Autowired
	private IEntregaService entregaService;
	
	/*@Autowired
	private IObservacionesEntregaService obsService;*/
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IConfiguracionService auxiliarService;
	
	@Autowired
	private IUploadFileService uploadService;
	
	@Autowired
	private IContactoService contactoService;
	
	@Autowired
	private IMotorizadoService motorizadoService;
	
	@GetMapping("/entrega/get-foto/{filename:.+}")
	public ResponseEntity<Resource> getFoto(@PathVariable String filename) {
		Resource res = null;
		try {
			res = uploadService.cargar(filename, VariablesGlobales.EVIDENCIAS_PAGO);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(res);
	}
	
	@PutMapping("/entrega/set-entregar/{id}/{metodoId}")
	public ResponseEntity<?> entregar(@PathVariable Integer id, @PathVariable Integer metodoId, @RequestBody Map<String, Object> fotos) {
		Map<String, Object> response = new HashMap<>();

		try {
			
			TablaAuxiliarDetalle auxiliar = auxiliarService.findById(8, "ESTPED");
			TablaAuxiliarDetalle metodoPago = auxiliarService.findById(metodoId, "METPGO");
			
			Entrega entrega = entregaService.getEntregaById(id);
			
			if (fotos.get("fotoCliente") != null ) {
				String fotoCliente = fotos.get("fotoCliente").toString();				
				fotoCliente = uploadService.saveBase64ImageRandom2(fotoCliente, VariablesGlobales.EVIDENCIAS_PAGO, "cliente_" + id);
				entrega.setFotoCliente(fotoCliente);
				
			}
			
			if (fotos.get("fotoPago") != null ) {
				String fotoPago = fotos.get("fotoPago").toString();
				fotoPago = uploadService.saveBase64ImageRandom2(fotoPago, VariablesGlobales.EVIDENCIAS_PAGO, "pago_" + id);
				entrega.setFotoPago(fotoPago);
				
			}
			
			entrega.setMetodoPago(metodoPago);
			entrega.setEstado(auxiliar);
			
			entregaService.save(entrega);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registros");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/entrega/recojo/{clienteId}/{userId}")
	public ResponseEntity<?> updateRutaRecojo(@PathVariable Integer clienteId, @PathVariable Integer userId) {
		Map<String, Object> response = new HashMap<>();

		try {
			entregaService.updateRutaRecojo(clienteId, userId);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registros");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/entrega/asignar/{id}")
	public ResponseEntity<?> asignarMotorizado(@PathVariable Integer id, 
			@RequestParam(value="mr", required=false) Integer motorizadoRecojo, 
			@RequestParam(value="me", required=false) Integer motorizadoEntrega, 
			@RequestParam(value="monto", required=false) Double monto) {
		Map<String, Object> response = new HashMap<>();
		
		Entrega entregaUpdate = entregaService.getEntregaById(id);
		
		Motorizado motRecojo = motorizadoRecojo != null ? motorizadoService.getById(motorizadoRecojo):null;
		Motorizado motEntrega= motorizadoEntrega != null ? motorizadoService.getById(motorizadoEntrega) : null;
		
		entregaUpdate.setMotorizadoRecojo(motRecojo);
		entregaUpdate.setMontoCobro(new BigDecimal(monto));
		entregaUpdate.setMotorizadoEntrega(motEntrega);
		
		entregaService.save(entregaUpdate);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/entrega/setend/{id}/{idAux}/{ind}")
	public ResponseEntity<?> setEnd(@PathVariable Integer idAux, @PathVariable Integer ind, @PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Entrega entregaUpdate = entregaService.getEntregaById(id);
			
			String textAux = (ind == 1?"PROENT":"METPGO");
			
			TablaAuxiliarDetalle auxiliar = auxiliarService.findById(idAux, textAux);

			if (ind == 1) {
				entregaUpdate.setObservacion(auxiliar);
			} else {
				entregaUpdate.setMetodoPago(auxiliar);
			}
			
			entregaService.save(entregaUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registro");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}	
	
	@PutMapping("/entrega/set-estado/{estadoId}/{id}")
	public ResponseEntity<?> setEstado(@PathVariable Integer estadoId, @PathVariable Integer id, 
			@RequestParam(value="idAux", required=false) Integer idAux, 
			@RequestParam(value="iic", required=false, defaultValue="0") Integer indIrCentral, 
			@RequestParam(value="fr", required=false) String fechaReprogramacion) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Entrega entregaUpdate = entregaService.getEntregaById(id);
			
			TablaAuxiliarDetalle estado = auxiliarService.findById(estadoId, "ESTPED");
			entregaUpdate.setEstado(estado);
			entregaUpdate.setIndIrCentral(indIrCentral);
			
			if (fechaReprogramacion != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date date = formatter.parse(fechaReprogramacion);
				entregaUpdate.setFechaEntrega(date);
			}
			
			if(estadoId == 4 || estadoId == 8 || estadoId == 7 || estadoId == 9) {
				
				String textAux = "";

				if (estadoId == 4 || estadoId == 8 || estadoId == 9) {
					textAux = "PROENT";
				} else if(estadoId == 7) {
					textAux = "METPGO";
				}
				
				TablaAuxiliarDetalle auxiliar = auxiliarService.findById(idAux, textAux);

				if (estadoId == 4 || estadoId == 9 || estadoId == 8) {
					entregaUpdate.setObservacion(auxiliar);
				} else if(estadoId == 7) {
					entregaUpdate.setMetodoPago(auxiliar);
				}
				
			}
			
			if(estadoId == 5) {
				
				entregaUpdate.setIndListado(1);
				
			}
			
			entregaService.save(entregaUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registro");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("/entrega/obs/{id}")
	public ResponseEntity<?> setObservadoState(@PathVariable Integer id, @RequestBody List<Integer> observaciones) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Entrega entregaUpdate = entregaService.getEntregaById(id);
			
			TablaAuxiliarDetalle estado = auxiliarService.findById(6, "ESTPED");
			entregaUpdate.setEstado(estado);
			
			List<ProblemasEntrega> observs = new ArrayList<>();
			
			for(Integer obs: observaciones) {
				ProblemasEntrega obser = new ProblemasEntrega();
				obser.setObservacion(auxiliarService.findById(obs, "OBSREC"));
				
				//ObservacionesEntrega newObser = obsService.save(obser);
				
				observs.add(obser);
			}
			
			entregaUpdate.setProblemas(observs);
			
			entregaService.save(entregaUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registro");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/entrega/core/{id}/{estadoId}")
	public ResponseEntity<?> updateEstadoByMotorizado(@PathVariable Integer id, @PathVariable Integer estadoId,
			@RequestBody List<Integer> observaciones) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Entrega entregaUpdate = entregaService.getEntregaById(id);
			
			TablaAuxiliarDetalle estado = auxiliarService.findById(estadoId, "ESTPED");
			entregaUpdate.setEstado(estado);
			
			List<ObservacionesEntrega> observs = new ArrayList<>();
			
			for(Integer obs: observaciones) {
				ObservacionesEntrega obser = new ObservacionesEntrega();
				obser.setObservacion(auxiliarService.findById(obs, "OBSENT"));
				
				//ObservacionesEntrega newObser = obsService.save(obser);
				
				observs.add(obser);
			}
			
			entregaUpdate.setObservaciones(observs);
			
			entregaService.save(entregaUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registro");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/entrega/motorizado-inventario/{usrId}")
	public List<Map<String, Object>> listPedidosForMotorizadoInventario(@PathVariable Integer usrId) {		
		return entregaService.listPedidosForMotorizadoInventario(usrId);
	}
	
	@GetMapping("/entrega/motorizado-entrega/{usrId}")
	public List<Map<String, Object>> listPedidosForMotorizadoEntrega(@PathVariable Integer usrId) {		
		return entregaService.listPedidosForMotorizadoEntrega(usrId);
	}
	
	@GetMapping("/entrega/motorizado-recojo/{usrId}")
	public List<Map<String, Object>> listPedidosForMotorizadoRecojo(@PathVariable Integer usrId) {		
		return entregaService.listPedidosForMotorizadoRecojo(usrId);
	}
	
	@GetMapping("/entrega/motorizado-recojo-detalle/{usrId}/{clienteId}")
	public List<Map<String, Object>> listPedidosForMotorizadoRecojoDetalle(@PathVariable Integer usrId, @PathVariable Integer clienteId) {		
		return entregaService.listPedidosForMotorizado(usrId, clienteId);
	}
	
	@GetMapping("/entrega/get-foto/{ind}/{filename:.+}")
	public ResponseEntity<Resource> getEvidencia(@PathVariable Integer ind, @PathVariable String filename) {
		Resource res = null;
		try {

			String ruta = VariablesGlobales.EVIDENCIAS_PAGO;
			res = uploadService.cargar(filename, ruta);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(res, HttpStatus.OK);
	}
	
	@PutMapping("/entrega/subir-evidencia/{id}/{ind}")
	public ResponseEntity<?> subirEvidencia(@PathVariable Integer id, 
			@PathVariable Integer ind,
			@RequestParam(value="file", required=true) MultipartFile file) {
		Map<String, Object> response = new HashMap<>();
		
		Entrega entrega = entregaService.getEntregaById(id);

		String ruta = ind==1?VariablesGlobales.EVIDENCIAS_COBRO:VariablesGlobales.EVIDENCIAS_PAGO;
		
		if(file.isEmpty() || file.getSize() == 0) {
			response.put("mensaje", "Archivo no legible.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CONFLICT);
		}
		
		try {
			String nombre = uploadService.copiar(file, ruta);
			
			if(ind == 1) {
				entrega.setEvidenciaCobro(nombre);
			} else if(ind == 2) {
				entrega.setEvidencia(nombre);
			}
			
			entregaService.save(entrega);
		} catch (IOException e) {
			e.printStackTrace();
			response.put("Error", "Error en el servidor.");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);		
	}
	
	@GetMapping("/entrega/pagos-cobros")
	public List<Map<String, Object>> getListadoPagosCobros() {
		List<Map<String, Object>> newlistado = new ArrayList<>();
		
		List<Map<String, Object>> listado = entregaService.getListClientesSinEvidencia();
		
		for(Map<String, Object> cliente: listado) {
			List<Map<String, Object>> detalle = entregaService.getEntregasSinEvidenciaByCliente(Integer.parseInt(cliente.get("id").toString()));
			
			Map<String, Object> newCliente = new HashMap<>(cliente);
			
			newCliente.put("detalle", detalle);
			
			newlistado.add(newCliente);
		}
		
		return newlistado;
	}
	
	@PutMapping("/entrega/update-id")
	public ResponseEntity<?> updateId(@Valid @RequestBody Entrega entrega, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			entregaService.save(entrega);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registro");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PutMapping("/entrega/update/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id,@Valid @RequestBody Entrega entrega, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Entrega entregaUpdate = entregaService.getEntregaById(id);
			
			//entregaUpdate.setMotorizado(entrega.getMotorizado());
			entregaUpdate.setPrecio(entrega.getPrecio());
			entregaUpdate.setUbicacionGPS(entrega.getUbicacionGPS());
			entregaUpdate.setUbicacionGPSAux(entrega.getUbicacionGPSAux());
			TablaAuxiliarDetalle estado = auxiliarService.findById(2, "ESTPED");
			entregaUpdate.setEstado(estado);
			
			entregaService.save(entregaUpdate);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("Error", "Error al actualizar registro");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/entrega/detail/{id}")
	public Entrega getEntregaById(@PathVariable Integer id) {
		return entregaService.getEntregaById(id);
	}
	
	@GetMapping("/entrega/saldos/{clienteId}")
	public List<Map<String, Object>> getSaldosByCliente(@PathVariable Integer clienteId,
											@RequestParam(value="fecha", required=true) String fecha
	) {

		return entregaService.getSaldoByCliente(clienteId, fecha);
	}
	
	@GetMapping("/entrega/list/{usuarioId}")
	public List<Map<String, Object>> getEntregasByUser(@PathVariable Integer usuarioId,
											@RequestParam(value="fecha", required=false) String fecha,
											@RequestParam(value="cliente", required=false, defaultValue="") String clienteStr,
											@RequestParam(value="codigo", required=false, defaultValue="") String codigoStr,
											@RequestParam(value="page", required=true) Integer page
	) {
		
		Cliente cliente =  clienteService.getClienteByUserId(usuarioId);
		List<Map<String, Object>> entregas = entregaService.getEntregasByCliente(cliente.getId(), fecha, clienteStr, codigoStr, page, 400);

		return entregas;
	}

    @PostMapping("/entrega/upload/{usrId}")
    public ResponseEntity<?> handleFileUpload(@PathVariable Integer usrId, @RequestParam(value="sedeId", required=true) Integer sedeId, @RequestParam(value="file", required=true) MultipartFile file) {
		
    	Map<String, Object> response = new HashMap<>();
		
		Integer clienteId = clienteService.getClienteByUserId(usrId).getId();
		
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            
            int j = 0;
            
            String errorRowMessage = "";
            
            for (Row row : sheet) {
                
                boolean rowValid = true;
            	
                int i = 0;
                
                String fechaAux = null, fecha = null, monto = null, nombreCompleto = null, celular = null, celularAlt = null, distrito = null, direccion = null, referencia = null, observacion = null, gps = null, producto = null;
                
                for (Cell cell : row) {
                    String cellValue = getCellValueAsString(cell);
                    
                    if(i == 0) {
                    	fechaAux = cellValue;
                    } else if (i == 1) {
                    	producto = cellValue;
                    } else if (i == 2) {
                    	monto = cellValue;
                    } else if (i == 3) {
                    	nombreCompleto = cellValue;
                    } else if (i == 4) {
                    	celular = cellValue;
                    } else if (i == 5) {
                    	celularAlt = cellValue;
                    } else if (i == 6) {
                    	distrito = cellValue;
                    } else if (i == 7) {
                    	direccion = cellValue;
                    } else if (i == 8) {
                    	referencia = cellValue;
                    } else if (i == 9) {
                    	observacion = cellValue;
                    } else if (i == 10) {
                    	gps = cellValue;
                    }
                    
                    i = i + 1;
                }
                
                String errorMessage = "";
                
                if(j > 0) {
                    
                    if(fechaAux == null || fechaAux.length() == 0) {
                    	rowValid = false;
                    	errorMessage += "- Fecha vacía en la fila " + j + "\n";
                    } else {
                    	try {
                        	SimpleDateFormat formatoOriginal = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                            Date fechaD = formatoOriginal.parse(fechaAux);
                            SimpleDateFormat nuevoFormato = new SimpleDateFormat("yyyy-MM-dd");
                            fecha = nuevoFormato.format(fechaD);
						} catch (Exception e) {
							e.printStackTrace();
	                    	errorMessage += "- La fecha ingresada en la fila " + j + " no corresponde a un formato de fecha.\n";
	                    	rowValid = false;
						}
                    }
                    
                    if(monto == null || monto.length() == 0) {
                    	errorMessage += "- Monto vacío en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(monto != null && !esNumeroDecimal(monto)) {
                    	errorMessage += "- El monto ingresado en la fila " + j + " no corresponde a un número.\n";
                    	rowValid = false;
                    }
                    
                    if(nombreCompleto == null || nombreCompleto.length() == 0) {
                    	errorMessage += "- Nombre de cliente vacío en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(celular == null || celular.length() == 0) {
                    	errorMessage += "- Celular de cliente vacío en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(celular != null && celular.length() != 0 && (!celular.matches("\\d+") || !celular.startsWith("9") || celular.length() != 9)) {
                    	errorMessage += "- El celular ingresado en la fila " + j + " no corresponde a un número celular, deben ser 9 números juntos.\n";
                    	rowValid = false;
                    }
                    
                    if(distrito == null || distrito.length() == 0) {
                    	errorMessage += "- Distrito vacío en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(direccion == null || direccion.length() == 0) {
                    	errorMessage += "- Dirección vacía en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(gps == null || gps.length() == 0) {
                    	errorMessage += "- Enlace GPS vacío en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(gps != null && !gps.contains("https://maps.app.goo.gl/")) {
                    	errorMessage += "- El campo GPS no corresponde a un enlace de Google Maps en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(producto == null || producto.length() == 0) {
                    	errorMessage += "- Producto vacío en la fila " + j + ".\n";
                    	rowValid = false;
                    }
                    
                    if(rowValid) {
                    	
                    	Map<String, Object> entrega = entregaService.insertMasivo(fecha, monto, nombreCompleto, celular, distrito, direccion, referencia, gps, clienteId, sedeId, producto, celularAlt, observacion);
                    	
                    	if(entrega.get("mensaje") != null) {
                    		errorRowMessage += entrega.get("mensaje").toString() + " en la fila " + j + ".\n";
                    	} else {
                    		entregaService.aprobarEntrega(Integer.parseInt(entrega.get("new_ent_id").toString()));
                    	}
                    	
                    } else {
                        errorRowMessage += errorMessage;
                    }
                	
                }
                
                j = j+ 1;
            }

            if(errorRowMessage.length() > 0) {
            	response.put("mensaje", errorRowMessage);
    			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.CONFLICT);
            }

        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
    }

    public static boolean esNumeroDecimal(String texto) {
        try {
            Double.parseDouble(texto);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	@GetMapping("/entrega/reporte/aprobaciones-diarias")
	public ResponseEntity<Resource> descargarAprobaciones(@RequestParam(value="fecha", required=true) String fecha){
		
		Resource recurso = null;
		
		try {
			entregaService.reporteAprobacionesDiarias(fecha);
			recurso = uploadService.cargar("REPORTE_APROBACION_" + fecha +  ".xlsx", VariablesGlobales.APROBACIONES_DIARIAS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ recurso.getFilename() +"\"");
		
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
		
	}
	
	@PutMapping("/entrega/aprobar/{id}")
	public ResponseEntity<?> aprobar(@PathVariable Integer id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			entregaService.aprobarEntrega(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error con el servidor.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}
	
	@GetMapping("/entrega/listado")
	public List<Map<String, Object>> getListado(
				@RequestParam(value="nombreComercial", defaultValue="", required=false) String nombreComercial,
				@RequestParam(value="distrito", defaultValue="", required=false) String distrito,
				@RequestParam(value="fechaDesde", required=false) String fechaDesde,
				@RequestParam(value="fechaHasta", required=false) String fechaHasta,
				@RequestParam(value="codigo", required=false) String codigo,
				@RequestParam(value="page", required=true) Integer page
			) {
		return entregaService.getListadoEntregas(nombreComercial, distrito, fechaDesde, fechaHasta, codigo, page, 400);
	}

	@PostMapping("/entrega/create")
	public ResponseEntity<?> create(@Valid @RequestBody Entrega entrega, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			Cliente cliente = clienteService.getClienteByUserId(entrega.getIdUsuarioCrea());
			TablaAuxiliarDetalle estado = auxiliarService.findById(0, "ESTPED");
			
			List<ContactoEntrega> contactos = entrega.getContactos();
			
			for(ContactoEntrega contacto: contactos) {
				contacto = contactoService.save(contacto);
			}
			
			entrega.setCliente(cliente);
			entrega.setEstado(estado);
			
			Entrega ent = entregaService.save(entrega);
			entregaService.aprobarEntrega(ent.getId());
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Error con el servidor.");
			return new ResponseEntity<Map<String, Object>> (response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>> (response, HttpStatus.OK);
	}
	
	private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Unsupported cell type";
        }
    }
}