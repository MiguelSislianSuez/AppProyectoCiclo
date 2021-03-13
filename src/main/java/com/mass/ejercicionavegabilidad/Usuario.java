/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author Kentucky
 */
class Usuario {

    private String usuario;
    private String apellido_1;
    private String apellido_2;
    private LocalDate anio;
    private String password;
    
    
    public Usuario(){};

    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }
    
    
    public Usuario(String usuario, String apellido_1, String apellido_2, LocalDate anio, String password) {
        this.usuario = usuario;
        this.apellido_1 = apellido_1;
        this.apellido_2 = apellido_2;
        this.anio = anio;
        this.password = password;
    }
     

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getApellido_1() {
        return apellido_1;
    }

    public void setApellido_1(String apellido_1) {
        this.apellido_1 = apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public void setApellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public LocalDate getAnio() {
        return anio;
    }

    public void setAnio(LocalDate anio) {
        this.anio = anio;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
        
        
        
    
    /*public boolean login() throws SQLException {
        //Instanciamos la clase conH2 y hacemos una consulta para verificar que el user existe en la bd
        ConexionH2 conn = new ConexionH2();
        String H2 = "";
        H2 += "SELECT * ";
        H2 += "FROM usuarios ";
        H2 += "WHERE lower(usuario) = '" + usuario.toLowerCase() + "' and password = '" + password + "'";

        ResultSet rs = conn.ejecutarConsulta(H2);
        boolean hayUsuarios = rs.next();//devuelve un true si hay un valor despues

        return hayUsuarios;

    }*/

}
