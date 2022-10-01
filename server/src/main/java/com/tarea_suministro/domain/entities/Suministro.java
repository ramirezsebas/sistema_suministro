package com.tarea_suministro.domain.entities;

public class Suministro {
    private String nis;
    private String usuario;
    private double consumo;
    private Estado estado;

    public Suministro(String nis, String usuario, double consumo, Estado estado) {
        this.nis = nis;
        this.usuario = usuario;
        this.consumo = consumo;
        this.estado = estado;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Suministro [nis=" + nis + ", consumo=" + consumo + ", estado=" + estado + "]";
    }
}
