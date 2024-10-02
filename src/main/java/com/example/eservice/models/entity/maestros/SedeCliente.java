package com.example.eservice.models.entity.maestros;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.eservice.models.entity.ubicacion.Distrito;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="mae_sede_cliente")
public class SedeCliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50)
	private String nombre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Distrito distrito;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Ruta rutaAsignada;

	@Column(length = 250)
	private String direccion;

	@Column(length = 250)
	private String referencia;

	@Column(length = 400)
	private String ubicacionGPS;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "sede_id")
	private List<EncargadoSede> encargados;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getUbicacionGPS() {
		return ubicacionGPS;
	}

	public void setUbicacionGPS(String ubicacionGPS) {
		this.ubicacionGPS = ubicacionGPS;
	}

	public List<EncargadoSede> getEncargados() {
		return encargados;
	}

	public void setEncargados(List<EncargadoSede> encargados) {
		this.encargados = encargados;
	}

	public Ruta getRutaAsignada() {
		return rutaAsignada;
	}

	public void setRutaAsignada(Ruta rutaAsignada) {
		this.rutaAsignada = rutaAsignada;
	}

	private static final long serialVersionUID = 1L;

}
