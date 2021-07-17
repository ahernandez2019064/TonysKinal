package org.allanhernandez.bean;

import java.util.Date;

public class Presupuesto {
    private int codigoPresupuesto;
    private Date fechaSolicitad;
    private double cantidadPresupuesto;
    private int codigoEmpresa;

    public Presupuesto() {
    }

    public Presupuesto(int codigoPresupuesto, Date fechaSolicitad, double cantidadPresupuesto, int codigoEmpresa) {
        this.codigoPresupuesto = codigoPresupuesto;
        this.fechaSolicitad = fechaSolicitad;
        this.cantidadPresupuesto = cantidadPresupuesto;
        this.codigoEmpresa = codigoEmpresa;
    }
    

    public int getCodigoPresupuesto() {
        return codigoPresupuesto;
    }

    public void setCodigoPresupuesto(int codigoPresupuesto) {
        this.codigoPresupuesto = codigoPresupuesto;
    }

    public Date getFechaSolicitad() {
        return fechaSolicitad;
    }

    public void setFechaSolicitad(Date fechaSolicitad) {
        this.fechaSolicitad = fechaSolicitad;
    }

    public double getCantidadPresupuesto() {
        return cantidadPresupuesto;
    }

    public void setCantidadPresupuesto(double cantidadPresupuesto) {
        this.cantidadPresupuesto = cantidadPresupuesto;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
    
    
}
 