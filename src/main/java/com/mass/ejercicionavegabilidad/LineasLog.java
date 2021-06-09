/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

/**
 *
 * @author Kentucky
 */
public class LineasLog {
    
    private String linea;

    public LineasLog(String linea) {
        this.linea = linea;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "LineasLog{linea=" + linea + "}";
    }
    
    
    
}
