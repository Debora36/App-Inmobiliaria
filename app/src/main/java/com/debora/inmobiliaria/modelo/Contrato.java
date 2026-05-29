package com.debora.inmobiliaria.modelo;
import java.io.Serializable;
public class Contrato implements Serializable{
    private int idContrato, idInquilino, idInmueble;
    private String fechaInicio, fechaFinalizacion;
    private Double montoAlquiler;
    private Inquilino inquilino;
    private Inmueble inmueble;
    private Boolean estado;

    public Contrato(int idContrato, int idInquilino, int idInmueble, String fechaInicio, String fechaFinalizacion, Double montoAlquiler, Inquilino inquilino, Inmueble inmueble, Boolean estado) {
        this.idContrato = idContrato;
        this.idInquilino = idInquilino;
        this.idInmueble = idInmueble;
        this.fechaInicio = fechaInicio;
        this.fechaFinalizacion = fechaFinalizacion;
        this.montoAlquiler = montoAlquiler;
        this.inquilino = inquilino;
        this.inmueble=inmueble;
        this.estado=estado;
    }

    public Contrato() {
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Double getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(Double montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }

    public String getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(String fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaIniciom(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
