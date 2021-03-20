/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Kentucky
 */
public class TablaPacientesDao {

    public TablaPacientesDao() {

        crearTablaSiNoExiste();

    }

    public void crearTablaSiNoExiste() {

        try (Connection conexionDatabase
                = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS pacientes"
                    + "(id INTEGER auto_increment, "
                    + " nombre VARCHAR (255), "
                    + " apellido VARCHAR (255), "
                    + " dni VARCHAR (255), "
                    + " email VARCHAR (255), "
                    + " no_ss VARCHAR (255), "
                    + " telefono VARCHAR (255), "
                    + " anio DATE, "
                    + " url VARCHAR (10000),"
                    + " datos VARCHAR (255),"
                    + " h BOOLEAN,"
                    + " l BOOLEAN,"
                    + " g BOOLEAN,"
                    + " t BOOLEAN,"
                    + " b BOOLEAN,"
                    + " i BOOLEAN)";

            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TablaPacientes getPacienteByDni(String dni) {

        TablaPacientes encontrado = null;

        try (Connection conexionDatabase
                = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String sql = "SELECT * FROM pacientes WHERE dni = '" + dni + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                //creamos nuestro objeto vacio departamento al que le damos unos valores
                //y luego a nuestra lista pacientes le pasamos  que invocará los valores
                TablaPacientes paciente = new TablaPacientes();
                paciente.setId(resultSet.getInt("id"));
                paciente.setNombre(resultSet.getString("nombre"));
                paciente.setApellido(resultSet.getString("apellido"));
                paciente.setDni(resultSet.getString("dni"));
                paciente.setEmail(resultSet.getString("email"));
                paciente.setNoSS(resultSet.getString("no_ss"));
                paciente.setTelefono(resultSet.getString("telefono"));
                paciente.setAnio((resultSet.getDate("anio")).toLocalDate());
                paciente.setUrl(resultSet.getString("url"));
                paciente.setDatos(resultSet.getString("datos"));

                paciente.setH(resultSet.getBoolean("h"));
                paciente.setL(resultSet.getBoolean("l"));
                paciente.setG(resultSet.getBoolean("g"));
                paciente.setT(resultSet.getBoolean("t"));
                paciente.setB(resultSet.getBoolean("b"));
                paciente.setI(resultSet.getBoolean("i"));

                encontrado = paciente;
                //System.out.println(paciente.getDatos());
                //pacientes.add(paciente);
                //System.out.println(paciente);

            }

            statement.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public void guardar(TablaPacientes paciente) {
        LocalDate ld = paciente.getAnio();
        java.sql.Date sqlDate = java.sql.Date.valueOf(ld);
        try (Connection conexionDatabase = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String sql = "INSERT INTO pacientes (nombre, apellido, dni, email, no_ss, telefono, anio, url, datos, h, l ,g, t, b, i)"
                    + "VALUES ('" + paciente.getNombre()
                    + "', '" + paciente.getApellido()
                    + "', '" + paciente.getDni()
                    + "', '" + paciente.getEmail()
                    + "', '" + paciente.getNoSS()
                    + "', '" + paciente.getTelefono()
                    + "', '" + sqlDate
                    + "', '" + paciente.getUrl()
                    + "', '" + paciente.getDatos()
                    + "', " + paciente.isH()
                    + " , " + paciente.isL()
                    + " , " + paciente.isG()
                    + " , " + paciente.isT()
                    + " , " + paciente.isB()
                    + " , " + paciente.isI() + ")";

            //ejecutamos la consulta
            statement.executeUpdate(sql);
            System.out.println("Información guardada en la base de datos H2");

        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al guardar información: " + e.getMessage());
        }

    }

    public int[][] rellenarGrafico() {
        
        //ArrayList<ArrayList<Integer>> rellenaGrafico = new ArrayList<>();

        int[][] rellenarGrafico = new int[5][2];
        try (Connection conexionDatabase = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {

            Statement statement = conexionDatabase.createStatement();
            String sql = "SELECT year(anio) AS year, COUNT(*) AS num_pacientes FROM pacientes GROUP BY year(anio) ORDER BY year(anio)";
            //guardar en en mia mtriz las filas y columnas y llamarlas desde la clase GRafica
            //ejecutamos la consulta
            //statement.executeQuery(sql);
            ResultSet rs = statement.executeQuery(sql);
            int contador = 0;

            while (rs.next()) {
                rellenarGrafico[contador][0] = rs.getInt(1);
                rellenarGrafico[contador][1] = rs.getInt(2);

                contador++;

                System.out.println("Año" + rs.getInt(1) + "|Pacientes: " + rs.getString(2) + "\r\n");
            }

            System.out.println("Información extraida correctamente");

        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al guardar información: " + e.getMessage());
        }
        return rellenarGrafico;
    }

    public void actualizar(TablaPacientes paciente) {
        LocalDate ld = paciente.getAnio();

        java.sql.Date sqlDate = java.sql.Date.valueOf(ld);

        try (Connection conexionDatabase
                = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String sql = "UPDATE pacientes SET nombre='" + paciente.getNombre()
                    + "', apellido='" + paciente.getApellido()
                    + "', dni='" + paciente.getDni()
                    + "', email='" + paciente.getEmail()
                    + "', no_ss='" + paciente.getNoSS()
                    + "', telefono='" + paciente.getTelefono()
                    + "', anio='" + sqlDate
                    + "', datos='" + paciente.getDatos()
                    + "', h=" + paciente.isH()
                    + ", l=" + paciente.isL()
                    + ", g=" + paciente.isG()
                    + ", t=" + paciente.isT()
                    + ", b=" + paciente.isB()
                    + ", i=" + paciente.isI()
                    + " WHERE id=" + paciente.getId();
            System.out.println("CONSULTA" + sql);
                    
            statement.executeUpdate(sql);

        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al actualizar información: " + e.getMessage());
        }

        
    }

    public void eliminar(TablaPacientes paciente) {
        try (Connection conexionDatabase = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String sql = "DELETE FROM pacientes WHERE id = " + paciente.getId();
            System.out.println("MOSTRAR CONSULTA " +sql);
            statement.executeUpdate(sql);
        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al eliminar información: " + e.getMessage());
        }

    }

    public void guardarOactualizar(TablaPacientes paciente) {
        if (paciente.getId() == 0) {
            guardar(paciente);

        } else {
            actualizar(paciente);

        }
    }

    public ObservableList<TablaPacientes> cargarPacientesDB() {

        ObservableList<TablaPacientes> pacientes = FXCollections.observableArrayList();
        try (Connection conexionDatabase = DriverManager.getConnection(ParametrosConexion.URL_CONN, ParametrosConexion.URL_BD, ParametrosConexion.CONTR_BD)) {
            Statement statement = conexionDatabase.createStatement();
            String sql = "SELECT * FROM pacientes ORDER BY id";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                TablaPacientes paciente = new TablaPacientes();
                paciente.setId(resultSet.getInt("id"));

                paciente.setNombre(resultSet.getString("nombre"));
                paciente.setApellido(resultSet.getString("apellido"));
                paciente.setDni(resultSet.getNString("dni"));
                paciente.setEmail(resultSet.getString("email"));
                paciente.setNoSS(resultSet.getNString("no_ss"));
                paciente.setTelefono(resultSet.getString("telefono"));
                paciente.setAnio((resultSet.getDate("anio")).toLocalDate());
                paciente.setUrl(resultSet.getString("url"));
                paciente.setDatos(resultSet.getString("datos"));

                paciente.setH(resultSet.getBoolean("h"));
                paciente.setL(resultSet.getBoolean("l"));
                paciente.setG(resultSet.getBoolean("g"));
                paciente.setT(resultSet.getBoolean("t"));
                paciente.setB(resultSet.getBoolean("b"));
                paciente.setI(resultSet.getBoolean("i"));

                //System.out.println(paciente.getDatos());
                pacientes.add(paciente);
                //System.out.println(paciente);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(pacientes.toString());
        return pacientes;

    }

}
