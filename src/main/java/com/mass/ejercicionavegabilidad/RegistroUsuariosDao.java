/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;


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

    
    
    public RegistroUsuariosDao(){
        crearTablaSiNoExiste();
    }

    public boolean login(Usuario usuario) {

        try (Connection conexionDatabase
                = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String H2 = "";
            H2 += "SELECT * ";
            H2 += "FROM usuarios ";
            H2 += "WHERE lower(usuario) = '" + usuario.getUsuario().toLowerCase() + "' and password = '" + usuario.getPassword() + "'";

            ResultSet rs = statement.executeQuery(H2);
            if (rs.next()) {
                return true;

            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al enontrar el nombre o usuario");
        }

    }

    public void crearTablaSiNoExiste() {

        try (Connection conexionDatabase
                = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            //String drop = "DROP TABLE usuarios";
            String sql = "CREATE TABLE IF NOT EXISTS usuarios"
                    + "(id INTEGER auto_increment, "
                    + " usuario VARCHAR (255), "
                    + " primerApellido VARCHAR (255),"
                    + " segundoapellido VARCHAR (255),"
                    + " anio DATE,"
                    + " password VARCHAR (255))";
            //statement.executeUpdate(drop);
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void registrar(Usuario usuario) {

        LocalDate dt = usuario.getAnio();
        Date sqlDate = Date.valueOf(dt);//pasar de java a sql el formato de fecha

        try (Connection conexionDatabase
                = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement st = conexionDatabase.createStatement();
            String sql = "INSERT INTO usuarios (usuario, primerApellido, segundoApellido, anio, password)"
                    + "VALUES ('" + usuario.getUsuario() + "', '" + usuario.getApellido_1() + "', '" + usuario.getApellido_2() + "', '" + sqlDate + "', '" + usuario.getPassword() + "')";

            st.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException("Ha ocurrido un problema al intentar insertar en la base de datos" + e.getMessage());
        }

    }
}
