package com.TodoArte.Classes;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.TodoArte.Enums.MensajesExcepciones;

@Entity
@Table(name = "fuente")
public class Fuente implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre")
    private String nombre;

    public Fuente() {
    }
    
    public Fuente(int id, String nombre) {
    	if(nombre.equals("")){
    		throw new RuntimeException(MensajesExcepciones.nombre);
    	}
    	
		this.id = id;
		this.nombre = nombre;
	}
	
	public String getNombre() {
	        return this.nombre;
	}
	
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}