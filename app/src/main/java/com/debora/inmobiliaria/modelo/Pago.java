package com.debora.inmobiliaria.modelo;

import java.io.Serializable;

public class Pago implements Serializable {
    private int idPago, idContrato;
    private String fechaPago, detalle;
    private Double monto;
    private Boolean estado;
    private Contrato contrato;

    public Pago(int idPago, int idContrato, String fechaPago, String detalle, Double monto, Boolean estado, Contrato contrato) {
        this.idPago = idPago;
        this.idContrato = idContrato;
        this.fechaPago = fechaPago;
        this.detalle = detalle;
        this.monto = monto;
        this.estado = estado;
        this.contrato = contrato;
    }

    public Pago() {
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
