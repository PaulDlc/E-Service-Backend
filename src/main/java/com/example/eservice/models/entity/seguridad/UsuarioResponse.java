package com.example.eservice.models.entity.seguridad;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;

@Projection(name="usuarioResponse", types= {Usuario.class})
public interface UsuarioResponse {
	Integer getId();
	TablaAuxiliarDetalle getTipoDocumento();
	String getNroDocumento();
	TablaAuxiliarDetalle getEstado();
	String getNombre();
	String getUsername();
	String getEmail();

	List<Role> getRoles();
	Boolean getEnabled();
}