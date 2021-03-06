package com.TodoArte.Classes;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.TodoArte.Enums.MensajesExcepciones;
import com.TodoArte.Enums.Privacidad;
import com.TodoArte.Enums.TipoContenido;
import com.TodoArte.JPAControllerClasses.ComentarioJpaController;
import com.TodoArte.JPAControllerClasses.ReporteJpaController;
import com.TodoArte.JPAControllerClasses.ValoracionJpaController;
import com.TodoArte.JPAControllerClasses.VentaJpaController;

@Entity
@Table(name = "contenido")
public class Contenido implements Serializable {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "tipoContenido")
	@Enumerated(EnumType.STRING)
	private TipoContenido tipo;
	
	@Column(name = "privacidad")
	@Enumerated(EnumType.STRING)
	private Privacidad privacidad;
	
	@Column(name = "precio")
	private float precio;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "archivo")
	private byte[] archivo;
	
	@Basic
	@Column(name = "fechaPublicado")
	private Date fechaPublicado;
	
	@Column(name = "bloqueado")
	private boolean bloqueado;
	
	@Column(name = "eliminado")
	private boolean eliminado;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
	private CategoriaContenido miCategoria;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contenido")
	@MapKey(name = "id")
	private Map<Integer, Venta> MisVentas;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contenido")
	@MapKey(name = "id")
	private Map<Integer, Reporte> MisReporte;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contenido")
	@MapKey(name = "id")
	private Map<Integer, Comentario> MisComentario;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_contenido")
	@MapKey(name = "id")
	private Map<Integer, Valoracion> MisValoracion;

	public Contenido() {
		MisVentas = new TreeMap<Integer, Venta>();
		MisReporte = new TreeMap<Integer, Reporte>();
		MisComentario = new TreeMap<Integer, Comentario>();
		MisValoracion = new TreeMap<Integer, Valoracion>();
	}
	
	public Contenido(int id, TipoContenido tipo, Privacidad privacidad, int precio, String descripcion, String titulo, byte[] archivo, Date fechaPublicado, boolean bloqueado, boolean eliminado, CategoriaContenido miCategoria) {
		if(tipo == null){
    		throw new RuntimeException(MensajesExcepciones.tipoContenido);
    	}
		if(privacidad == null){
    		throw new RuntimeException(MensajesExcepciones.privacidadContenido);
    	}
		if(privacidad != Privacidad.Premium){
			precio = 0;
		}
		if(privacidad == Privacidad.Premium){
			if (precio <= 0) {
	    		throw new RuntimeException(MensajesExcepciones.precio);
			}
		}
		
		if(descripcion.equals("")){
    		throw new RuntimeException(MensajesExcepciones.descripcion);
    	}
		
		if(titulo.equals("")){
    		throw new RuntimeException(MensajesExcepciones.titulo);
    	}
		
		if(miCategoria == null){
    		throw new RuntimeException(MensajesExcepciones.categoria);
    	}
		
		Date fechaActual = new Date(System.currentTimeMillis());
		
		this.id = id;
		this.tipo = tipo;
		this.privacidad = privacidad;
		this.precio = precio;
		this.descripcion = descripcion;
		this.titulo = titulo;
		this.archivo = archivo;
		this.fechaPublicado = fechaActual;
		this.bloqueado = false;
		this.eliminado = false;
		this.miCategoria = miCategoria;
		MisVentas = new TreeMap<Integer, Venta>();
		MisReporte = new TreeMap<Integer, Reporte>();
		MisComentario = new TreeMap<Integer, Comentario>();
		MisValoracion = new TreeMap<Integer, Valoracion>();
	}
	
	//***************************************************************************	
	public boolean fanYaValoro(String idFan) {
		for (Map.Entry<Integer, Valoracion> entry : MisValoracion.entrySet()) {
			if (entry.getValue().getMiFan().getNikname().equals(idFan)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean fanYaCompro(String idFan) {
		for (Map.Entry<Integer, Venta> entry : MisVentas.entrySet()) {
			if (entry.getValue().getMiFan().getNikname().equals(idFan)) {
				return true;
			}
		}
		return false;
	}
	
	public void crearValoracion(Valoracion val, Fan fan) {
		// vincular la valoracion con el fan
		// persistir la valoracion
		// agregarlo a la coleccion
		if (fanYaValoro(fan.getNikname())) {
			throw new RuntimeException(MensajesExcepciones.fanYaValoro);
		}
		val.setMiFan(fan);
		new ValoracionJpaController().create(val);
		this.MisValoracion.put(val.getId(), val);
	}

	public void crearComentario(Comentario comentario, Fan fan) {
		// vincular el comentario con el fan
		// persistir el comentario
		// agregarlo a la coleccion
		
		comentario.setMiFan(fan);
		new ComentarioJpaController().create(comentario);
		this.MisComentario.put(comentario.getId(), comentario);
	}
	
	public void crearReporte(Reporte reporte, Fan fan) {
		// vincular el reporte con el fan
		// persistir el reporte
		// agregarlo a la coleccion
		
		reporte.setMiFan(fan);
		new ReporteJpaController().create(reporte);
		this.MisReporte.put(reporte.getId(), reporte);
	}

	public void crearVenta(Fan fan) {
		// crear una nueva venta
		// vincular la venta con el fan
		// persistir la venta
		// agregarlo a la coleccion

		if (fanYaCompro(fan.getNikname())) {
			throw new RuntimeException(MensajesExcepciones.fanYaCompro);
		}
		Date fechaYHora = new Date(System.currentTimeMillis());
		Venta venta = new Venta(0, this.getPrecio(), fechaYHora, fan);
		new VentaJpaController().create(venta);
		this.MisVentas.put(venta.getId(), venta);
	}

	/**
	 * Calcula el promedio de las valoraciones recibidas
	 * @return promedio de las valoraciones
	 */
	public int getValoracionCalculada() {
		int ret = 0;
		for (Map.Entry<Integer, Valoracion> entry : this.MisValoracion.entrySet()) {
			ret += entry.getValue().getVal();
		}
		if (this.MisValoracion.size() > 0) {
			ret = ret / this.MisValoracion.size();
		}
		return ret;
	}
	
	
	//***************************************************************************
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoContenido getTipo() {
		return tipo;
	}

	public void setTipo(TipoContenido tipo) {
		this.tipo = tipo;
	}

	public Privacidad getPrivacidad() {
		return privacidad;
	}

	public void setPrivacidad(Privacidad privacidad) {
		this.privacidad = privacidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {	
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public Date getFechaPublicado() {
		return fechaPublicado;
	}

	public void setFechaPublicado(Date fechaPublicado) {
		this.fechaPublicado = fechaPublicado;
	}

	public boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public boolean getEliminado() {
		return eliminado;
	}

	public void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public CategoriaContenido getMiCategoria() {
		return miCategoria;
	}

	public void setMiCategoria(CategoriaContenido miCategoria) {
		this.miCategoria = miCategoria;
	}

	public Map<Integer, Venta> getMisVentas() {
		return MisVentas;
	}

	public void setMisVentas(Map<Integer, Venta> misVentas) {
		MisVentas = misVentas;
	}

	public Map<Integer, Reporte> getMisReporte() {
		return MisReporte;
	}

	public void setMisReporte(Map<Integer, Reporte> misReporte) {
		MisReporte = misReporte;
	}

	public Map<Integer, Comentario> getMisComentario() {
		return MisComentario;
	}

	public void setMisComentario(Map<Integer, Comentario> misComentario) {
		MisComentario = misComentario;
	}

	public Map<Integer, Valoracion> getMisValoracion() {
		return MisValoracion;
	}

	public void setMisValoracion(Map<Integer, Valoracion> misValoracion) {
		MisValoracion = misValoracion;
	}
}