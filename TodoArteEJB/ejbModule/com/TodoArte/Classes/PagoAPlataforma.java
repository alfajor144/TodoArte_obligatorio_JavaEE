package com.TodoArte.Classes;

import java.util.Date;

public class PagoAPlataforma {
    private int id;
    private float monto;
    private Date fechaYHora;

    public PagoAPlataforma() {
    }

    public PagoAPlataforma(int id, float monto, Date fechaYHora) {
        this.id = id;
        this.monto = monto;
        this.fechaYHora = fechaYHora;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getMonto() {
        return this.monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFechaYHora() {
        return this.fechaYHora;
    }

    public void setFechaYHora(Date fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
}