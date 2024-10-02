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

import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="mae_cliente")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle tipoDocumento;
	
	@Column(length = 15)
	private String nroDocumento;
	
	@Column(length = 300)
	private String razonSocial;
	
	@Column(length = 300)
	private String nombreComercial;
	
	@Column(length = 300)
	private String nombreCompleto;

	@Column(length = 15)
	private String celular;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Banco banco;

	@Column(length = 50)
	private String nroCuentaInterbancaria;

	@Column(length = 250)
	private String titularBanco;

	@Column(length = 30)
	private String nroCuenta;

	@Column(length = 15)
	private String nroYape;

	@Column(length = 250)
	private String titularYape;

	@Column(length = 15)
	private String nroPlin;

	@Column(length = 250)
	private String titularPlin;
	
	private Integer usuario;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "cliente_id")
	private List<SedeCliente> sedes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TablaAuxiliarDetalle getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TablaAuxiliarDetalle tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTitularBanco() {
		return titularBanco;
	}

	public void setTitularBanco(String titularBanco) {
		this.titularBanco = titularBanco;
	}

	public String getNroCuenta() {
		return nroCuenta;
	}

	public void setNroCuenta(String nroCuenta) {
		this.nroCuenta = nroCuenta;
	}

	public String getNroCuentaInterbancaria() {
		return nroCuentaInterbancaria;
	}

	public void setNroCuentaInterbancaria(String nroCuentaInterbancaria) {
		this.nroCuentaInterbancaria = nroCuentaInterbancaria;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getNroYape() {
		return nroYape;
	}

	public void setNroYape(String nroYape) {
		this.nroYape = nroYape;
	}

	public String getTitularYape() {
		return titularYape;
	}

	public void setTitularYape(String titularYape) {
		this.titularYape = titularYape;
	}

	public String getNroPlin() {
		return nroPlin;
	}

	public void setNroPlin(String nroPlin) {
		this.nroPlin = nroPlin;
	}

	public String getTitularPlin() {
		return titularPlin;
	}

	public void setTitularPlin(String titularPlin) {
		this.titularPlin = titularPlin;
	}

	public List<SedeCliente> getSedes() {
		return sedes;
	}

	public void setSedes(List<SedeCliente> sedes) {
		this.sedes = sedes;
	}

	private static final long serialVersionUID = 1L;
}
