package com.example.eservice.models.entity.entregas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.example.eservice.models.entity.auxiliares.TablaAuxiliarDetalle;
import com.example.eservice.models.entity.maestros.Cliente;
import com.example.eservice.models.entity.maestros.Motorizado;
import com.example.eservice.models.entity.maestros.SedeCliente;
import com.example.eservice.models.entity.ubicacion.Distrito;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="ent_entrega")
public class Entrega implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 15)
	private String codigo;
	
	@Column(length = 300)
	private String evidencia;
	
	@Column(length = 300)
	private String evidenciaCobro;
	
	@Column(length = 300)
	private String nombres;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntrega;

	@Column(length = 15)
	private String celular;

	@Column(length = 15)
	private String celularAlternativo;

	@Column(length = 250)
	private String direccion;

	@Column(length = 400)
	private String referencia;

	@Column(length = 400)
	private String observacionStr;

	@Column(length = 250)
	private String nombreProducto;

	@Column(columnDefinition = "NUMERIC(20,2)")
	private BigDecimal montoCobro;

	@Column(columnDefinition = "NUMERIC(20,2)")
	private BigDecimal precio;

	@Column(length = 400)
	private String ubicacionGPS;
	
	private Integer tipoEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Motorizado motorizadoAsignado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Distrito distrito;
	
	private Boolean indAprobado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle estado;
	
	private Integer usuario;
	
	private Integer indListado;

	private String accionNoEntrega;
	
	private Integer indIrCentral;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private SedeCliente sede;

	@Column(length = 250)
	private String direccionAux;

	@Column(length = 400)
	private String referenciaAux;

	@Column(length = 400)
	private String ubicacionGPSAux;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Distrito distritoAux;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Motorizado motorizadoRecojo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Motorizado motorizadoEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle metodoPago;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private TablaAuxiliarDetalle observacion;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "entrega_id")
	List<Incidencia> incidencias;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "entrega_id")
	List<ObservacionesEntrega> observaciones;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "entrega_id")
	List<ProblemasEntrega> problemas;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "entrega_id")
	List<LogFechasEntrega> logFechas;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "entrega_id")
	private List<ContactoEntrega> contactos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true)
	private Cliente cliente;

	private Integer idUsuarioCrea;

	private Integer idUsuarioModifica;

	@Column(length = 400)
	private String fotoCliente;

	@Column(length = 400)
	private String fotoPago;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModifica;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEvidencia() {
		return evidencia;
	}

	public String getEvidenciaCobro() {
		return evidenciaCobro;
	}

	public void setEvidenciaCobro(String evidenciaCobro) {
		this.evidenciaCobro = evidenciaCobro;
	}

	public String getObservacionStr() {
		return observacionStr;
	}

	public void setObservacionStr(String observacionStr) {
		this.observacionStr = observacionStr;
	}

	public void setEvidencia(String evidencia) {
		this.evidencia = evidencia;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Integer getIndIrCentral() {
		return indIrCentral;
	}

	public void setIndIrCentral(Integer indIrCentral) {
		this.indIrCentral = indIrCentral;
	}

	public String getAccionNoEntrega() {
		return accionNoEntrega;
	}

	public void setAccionNoEntrega(String accionNoEntrega) {
		this.accionNoEntrega = accionNoEntrega;
	}

	public String getCelularAlternativo() {
		return celularAlternativo;
	}

	public void setCelularAlternativo(String celularAlternativo) {
		this.celularAlternativo = celularAlternativo;
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

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getMontoCobro() {
		return montoCobro;
	}

	public void setMontoCobro(BigDecimal montoCobro) {
		this.montoCobro = montoCobro;
	}

	public String getUbicacionGPS() {
		return ubicacionGPS;
	}

	public void setUbicacionGPS(String ubicacionGPS) {
		this.ubicacionGPS = ubicacionGPS;
	}

	public Distrito getDistrito() {
		return distrito;
	}

	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}

	public Boolean getIndAprobado() {
		return indAprobado;
	}

	public void setIndAprobado(Boolean indAprobado) {
		this.indAprobado = indAprobado;
	}

	public TablaAuxiliarDetalle getEstado() {
		return estado;
	}

	public void setEstado(TablaAuxiliarDetalle estado) {
		this.estado = estado;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Motorizado getMotorizadoRecojo() {
		return motorizadoRecojo;
	}

	public void setMotorizadoRecojo(Motorizado motorizadoRecojo) {
		this.motorizadoRecojo = motorizadoRecojo;
	}

	public Motorizado getMotorizadoEntrega() {
		return motorizadoEntrega;
	}

	public void setMotorizadoEntrega(Motorizado motorizadoEntrega) {
		this.motorizadoEntrega = motorizadoEntrega;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	public List<LogFechasEntrega> getLogFechas() {
		return logFechas;
	}

	public void setLogFechas(List<LogFechasEntrega> logFechas) {
		this.logFechas = logFechas;
	}

	public List<ContactoEntrega> getContactos() {
		return contactos;
	}

	public void setContactos(List<ContactoEntrega> contactos) {
		this.contactos = contactos;
	}

	public List<ObservacionesEntrega> getObservaciones() {
		return observaciones;
	}

	public List<ProblemasEntrega> getProblemas() {
		return problemas;
	}

	public void setProblemas(List<ProblemasEntrega> problemas) {
		this.problemas = problemas;
	}

	public void setObservaciones(List<ObservacionesEntrega> observaciones) {
		this.observaciones = observaciones;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getIdUsuarioCrea() {
		return idUsuarioCrea;
	}

	public void setIdUsuarioCrea(Integer idUsuarioCrea) {
		this.idUsuarioCrea = idUsuarioCrea;
	}

	public Integer getIdUsuarioModifica() {
		return idUsuarioModifica;
	}

	public void setIdUsuarioModifica(Integer idUsuarioModifica) {
		this.idUsuarioModifica = idUsuarioModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

	public Integer getTipoEntrega() {
		return tipoEntrega;
	}

	public void setTipoEntrega(Integer tipoEntrega) {
		this.tipoEntrega = tipoEntrega;
	}

	public SedeCliente getSede() {
		return sede;
	}

	public void setSede(SedeCliente sede) {
		this.sede = sede;
	}

	public String getDireccionAux() {
		return direccionAux;
	}

	public void setDireccionAux(String direccionAux) {
		this.direccionAux = direccionAux;
	}

	public String getReferenciaAux() {
		return referenciaAux;
	}

	public void setReferenciaAux(String referenciaAux) {
		this.referenciaAux = referenciaAux;
	}

	public String getUbicacionGPSAux() {
		return ubicacionGPSAux;
	}

	public void setUbicacionGPSAux(String ubicacionGPSAux) {
		this.ubicacionGPSAux = ubicacionGPSAux;
	}

	public Distrito getDistritoAux() {
		return distritoAux;
	}

	public void setDistritoAux(Distrito distritoAux) {
		this.distritoAux = distritoAux;
	}

	public TablaAuxiliarDetalle getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(TablaAuxiliarDetalle metodoPago) {
		this.metodoPago = metodoPago;
	}

	public TablaAuxiliarDetalle getObservacion() {
		return observacion;
	}

	public void setObservacion(TablaAuxiliarDetalle observacion) {
		this.observacion = observacion;
	}

	public Integer getIndListado() {
		return indListado;
	}

	public void setIndListado(Integer indListado) {
		this.indListado = indListado;
	}

	public String getFotoCliente() {
		return fotoCliente;
	}

	public void setFotoCliente(String fotoCliente) {
		this.fotoCliente = fotoCliente;
	}

	public String getFotoPago() {
		return fotoPago;
	}

	public void setFotoPago(String fotoPago) {
		this.fotoPago = fotoPago;
	}

	public Motorizado getMotorizadoAsignado() {
		return motorizadoAsignado;
	}

	public void setMotorizadoAsignado(Motorizado motorizadoAsignado) {
		this.motorizadoAsignado = motorizadoAsignado;
	}

	@PrePersist
	public void prePersist() {
		this.setFechaRegistro(new Date());
	}

	private static final long serialVersionUID = 1L;

}