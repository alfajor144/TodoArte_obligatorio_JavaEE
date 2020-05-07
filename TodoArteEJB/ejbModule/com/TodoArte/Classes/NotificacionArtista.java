package com.TodoArte.Classes;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.TodoArte.Enums.MensajesExcepciones;

@Entity
@Table(name = "notificacionArtista")
public class NotificacionArtista implements Serializable {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @Column(name = "titulo")
    private String titulo;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Basic
    @Column(name = "fechaYhora")
    private Date fechaYHora;

    public NotificacionArtista() {
    }

    public NotificacionArtista(int id, String titulo, String descripcion, Date fechaYHora) {
    	if(titulo.equals("")){
            throw new RuntimeException(MensajesExcepciones.titulo);
		}
    	if(descripcion.equals("")){
            throw new RuntimeException(MensajesExcepciones.descripcion);
		}
    	if(fechaYHora == null){
            throw new RuntimeException(MensajesExcepciones.fechaYHora);
		}
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaYHora = fechaYHora;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
    	if(titulo.equals("")){
            throw new RuntimeException(MensajesExcepciones.titulo);
		}
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
    	if(descripcion.equals("")){
            throw new RuntimeException(MensajesExcepciones.descripcion);
		}
        this.descripcion = descripcion;
    }

    public Date getFechaYHora() {
        return this.fechaYHora;
    }

    public void setFechaYHora(Date fechaYHora) {
    	if(fechaYHora == null){
            throw new RuntimeException(MensajesExcepciones.fechaYHora);
		}
        this.fechaYHora = fechaYHora;
    }
}