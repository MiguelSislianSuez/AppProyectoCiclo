/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import connetion.ConexionMySQL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kentucky
 */
public class RegistroUsuariosDao {

    public RegistroUsuariosDao() {
        crearTablaSiNoExiste();
    }

    public boolean login(Usuario usuario) {

        try {
            //Select usuario 
            ConexionMySQL conexionDatabase = Utils.createConnection();
            System.out.println("ENTRO EN LOGIN");
            String H2 = "";
            H2 += "SELECT * ";
            H2 += "FROM usuarios ";
            H2 += "WHERE lower(usuario) = '" + usuario.getUsuario().toLowerCase() + "' and password = MD5('" + usuario.getPassword() + "')";

            System.out.println(H2);
            conexionDatabase.ejecutarConsulta(H2);
            ResultSet rs = conexionDatabase.getResultSet();

            if (rs.next()) {
                conexionDatabase.cerrarConexion();
                return true;

            } else {
                conexionDatabase.cerrarConexion();
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error al enontrar el nombre o usuario");
        }

    }

    public void crearTablaSiNoExiste() {

        try {
            ConexionMySQL conexionDatabase = Utils.createConnection();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios"
                    + "(id INTEGER auto_increment, "
                    + " usuario VARCHAR (100), "
                    + " primerApellido VARCHAR (100),"
                    + " segundoapellido VARCHAR (40),"
                    + " anio DATE,"
                    + " password VARCHAR (100))";
            conexionDatabase.ejecutarInstruccion(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void registrar(Usuario usuario) {

        LocalDate dt = usuario.getAnio();
        Date sqlDate = Date.valueOf(dt);//pasar de java a sql el formato de fecha

        try {
            ConexionMySQL conexionDatabase = Utils.createConnection();
            System.out.println("ENTRO EN Registro");
            String sql = "INSERT INTO usuarios (usuario, primerApellido, segundoApellido, anio, password)"
                    + "VALUES ('" + usuario.getUsuario() + "', '" + usuario.getApellido_1() + "', '" + usuario.getApellido_2() + "', '" + sqlDate + "', MD5('" + usuario.getPassword() + "'))";

            System.out.println(sql);
            conexionDatabase.ejecutarInstruccion(sql);

        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un problema al intentar insertar en la base de datos" + e.getMessage());
        }

    }
}
