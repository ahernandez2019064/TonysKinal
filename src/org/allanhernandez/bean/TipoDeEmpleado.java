package org.allanhernandez.bean;

public class TipoDeEmpleado {
    private int codigoTipoEmpleado;
    private String descripción;

    public TipoDeEmpleado() {
    }

    public TipoDeEmpleado(int codigoTipoEmpleado, String descripción) {
        this.codigoTipoEmpleado = codigoTipoEmpleado;
        this.descripción = descripción;
    }

    public int getCodigoTipoEmpleado() {
        return codigoTipoEmpleado;
    }

    public void setCodigoTipoEmpleado(int codigoTipoEmpleado) {
        this.codigoTipoEmpleado = codigoTipoEmpleado;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }
    
    public String toString(){
        return getCodigoTipoEmpleado ()+ " | " + getDescripción();
    }
    
}

