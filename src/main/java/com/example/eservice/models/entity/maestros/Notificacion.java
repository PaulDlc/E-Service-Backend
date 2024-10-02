package com.example.eservice.models.entity.maestros;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mae_notificacion")
public class Notificacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 1000)
	private String mensaje;

	private Integer idEmisor;

	private Integer idUsuario;

	private Integer indAtendido;
	
	@Column(length = 200)
	private String link;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Integer getIdEmisor() {
		return idEmisor;
	}

	public void setIdEmisor(Integer idEmisor) {
		this.idEmisor = idEmisor;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIndAtendido() {
		return indAtendido;
	}

	public void setIndAtendido(Integer indAtendido) {
		this.indAtendido = indAtendido;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}