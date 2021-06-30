/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import connetion.ConexionMySQL;
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

        try {
            ConexionMySQL conexionDatabase = Utils.createConnection();

            String sql = "CREATE TABLE IF NOT EXISTS pacientes"
                    + "(id INTEGER auto_increment, "
                    + " nombre VARCHAR (255), "
                    + " apellido VARCHAR (255), "
                    + " dni VARCHAR (255), "
                    + " peso DOUBLE, "
                    + " altura DOUBLE, "
                    + " email VARCHAR (255), "
                    + " no_ss VARCHAR (255), "
                    + " telefono VARCHAR (255), "
                    + " anio DATE, "
                    + " url VARCHAR (10000),"
                    + " datos VARCHAR (255))";
            conexionDatabase.ejecutarInstruccion(sql);

            String sql2 = "CREATE TABLE IF NOT EXISTS orientaciones"
                    + "(id_paciente INTEGER auto_increment, "
                    + " h BOOLEAN,"
                    + " l BOOLEAN,"
                    + " g BOOLEAN,"
                    + " t BOOLEAN,"
                    + " b BOOLEAN,"
                    + " i BOOLEAN)";
            conexionDatabase.ejecutarInstruccion(sql2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TablaPacientes getPacienteByDni(String dni) {

        TablaPacientes encontrado = null;

        try {

            ConexionMySQL conexionDatabase = Utils.createConnection();

            String sql = "SELECT * FROM pacientes p INNER JOIN orientaciones o WHERE p.id = o.id_paciente AND dni = '" + dni + "'";
            conexionDatabase.ejecutarConsulta(sql);
            ResultSet resultSet = conexionDatabase.getResultSet();

            if (resultSet.next()) {
                //creamos nuestro objeto vacio departamento al que le damos unos valores
                //y luego a nuestra lista pacientes le pasamos  que invocará los valores
                TablaPacientes paciente = new TablaPacientes();
                paciente.setId(resultSet.getInt("id"));
                paciente.setNombre(resultSet.getString("nombre"));
                paciente.setApellido(resultSet.getString("apellido"));
                paciente.setDni(resultSet.getString("dni"));
                paciente.setPeso(resultSet.getDouble("peso"));
                paciente.setAltura(resultSet.getDouble("altura"));
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

            }

            //statement.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encontrado;
    }

    public void guardar(TablaPacientes paciente) {
        LocalDate ld = paciente.getAnio();
        java.sql.Date sqlDate = java.sql.Date.valueOf(ld);

        try {

            ConexionMySQL conexionDatabase = Utils.createConnection();
            String sql = "INSERT INTO pacientes (nombre, apellido, dni, peso, altura, email, no_ss, telefono, anio, url, datos)"
                    + "VALUES ('" + paciente.getNombre()
                    + "', '" + paciente.getApellido()
                    + "', '" + paciente.getDni()
                    + "', '" + paciente.getPeso()
                    + "', '" + paciente.getAltura()
                    + "', '" + paciente.getEmail()
                    + "', '" + paciente.getNoSS()
                    + "', '" + paciente.getTelefono()
                    + "', '" + sqlDate
                    + "', '" + paciente.getUrl()
                    + "', '" + paciente.getDatos() + "')";

            System.out.println("SQL 1" + sql);
            conexionDatabase.ejecutarInstruccion(sql);

            sql = "SELECT MAX(id) AS last_id FROM pacientes";
            conexionDatabase.ejecutarConsulta(sql);
            ResultSet rs2 = conexionDatabase.getResultSet();

            if (rs2.next()) {
                int id = rs2.getInt("last_id");

                System.out.println("ESTE ES EL ID " + id);

                sql = "INSERT INTO orientaciones (id_paciente, h, l ,g, t, b, i)"
                        + "VALUES ("
                        + id
                        + ", " + paciente.isH()
                        + " , " + paciente.isL()
                        + " , " + paciente.isG()
                        + " , " + paciente.isT()
                        + " , " + paciente.isB()
                        + " , " + paciente.isI()
                        + ")";
                System.out.println("SQL 2" + sql);
                conexionDatabase.ejecutarInstruccion(sql);
                ResultSet rs3 = conexionDatabase.getResultSet();

            }

            String contenidoHtml = Utils.getTemplateEmail("pruebasjavafx.html");
            System.out.println(contenidoHtml);

            HiloMail hiloEmail = new HiloMail("Hospital", contenidoHtml, paciente.getEmail());
            hiloEmail.start();

        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al guardar información: " + e.getMessage());
        }

    }

    public ArrayList<ValorGrafica> rellenarGrafico() {//*********VALORES DEL LINE CHART

        ArrayList<ValorGrafica> rellenaGrafico = new ArrayList<>();

        try {

            ConexionMySQL conexionDatabase = Utils.createConnection();
            String sql = "SELECT year(anio) AS year, COUNT(*) AS num_pacientes FROM pacientes GROUP BY year(anio) ORDER BY year(anio)";

            conexionDatabase.ejecutarConsulta(sql);
            ResultSet rs = conexionDatabase.getResultSet();

            int contador = 0;

            while (rs.next()) {
                ValorGrafica valorGrafica = new ValorGrafica(rs.getInt(1), rs.getInt(2));
                rellenaGrafico.add(valorGrafica);

                contador++;

                System.out.println("Año" + rs.getInt(1) + "|Pacientes: " + rs.getString(2) + "\r\n");
            }

            System.out.println("Información extraida correctamente");

        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al guardar información: " + e.getMessage());
        }
        return rellenaGrafico;
    }

    public void actualizar(TablaPacientes paciente) {
        LocalDate ld = paciente.getAnio();

        java.sql.Date sqlDate = java.sql.Date.valueOf(ld);

        try {
            ConexionMySQL conexionDatabase = Utils.createConnection();
            String sql = "UPDATE pacientes SET nombre='"
                    + paciente.getNombre()
                    + "', apellido='" + paciente.getApellido()
                    + "', dni='" + paciente.getDni()
                    + "', peso='" + paciente.getPeso()
                    + "', altura='" + paciente.getAltura()
                    + "', email='" + paciente.getEmail()
                    + "', no_ss='" + paciente.getNoSS()
                    + "', telefono='" + paciente.getTelefono()
                    + "', anio='" + sqlDate
                    + "', datos='" + paciente.getDatos()
                    + "'"
                    + " WHERE id=" + paciente.getId();
            System.out.println("CONSULTA" + sql);

            conexionDatabase.ejecutarInstruccionPreparada(sql);

            sql = "UPDATE orientaciones SET "
                    + " h=" + paciente.isH()
                    + ", l=" + paciente.isL()
                    + ", g=" + paciente.isG()
                    + ", t=" + paciente.isT()
                    + ", b=" + paciente.isB()
                    + ", i=" + paciente.isI()
                    + " WHERE id_paciente=" + paciente.getId();
            System.out.println("CONSULTA" + sql);
            conexionDatabase.ejecutarInstruccionPreparada(sql);

            HiloMail hiloEmail = new HiloMail("Hospital", "Se ha actualizado correctamente en nuesta base de datos", paciente.getEmail());
            hiloEmail.start();

        } catch (Exception e) {
            throw new RuntimeException("Ocurrido un error al actualizar información: " + e.getMessage());
        }

    }

    public void eliminar(TablaPacientes paciente) {
        try {
            ConexionMySQL conexionDatabase = Utils.createConnection();

            String sql = "DELETE FROM pacientes WHERE id = " + paciente.getId();
            System.out.println("MOSTRAR CONSULTA " + sql);
            conexionDatabase.ejecutarInstruccion(sql);

            sql = "DELETE FROM orientaciones WHERE id_paciente = " + paciente.getId();
            System.out.println("MOSTRAR CONSULTA " + sql);
            conexionDatabase.ejecutarInstruccion(sql);

            HiloMail hiloEmail = new HiloMail("Hospital", "Se ha eliminado correctamente en nuesta base de datos", paciente.getEmail());
            hiloEmail.start();

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
        try {
            ConexionMySQL conexionDatabase = Utils.createConnection();
            String sql = "SELECT * FROM pacientes p INNER JOIN orientaciones o ON p.id = o.id_paciente ORDER BY id";

            conexionDatabase.ejecutarConsulta(sql);
            ResultSet resultSet = conexionDatabase.getResultSet();

            while (resultSet.next()) {
                TablaPacientes paciente = new TablaPacientes();//*
                paciente.setId(resultSet.getInt("id"));//*
                paciente.setNombre(resultSet.getString("nombre"));//*
                paciente.setApellido(resultSet.getString("apellido"));
                paciente.setDni(resultSet.getNString("dni"));
                paciente.setPeso(resultSet.getDouble("peso"));
                paciente.setAltura(resultSet.getDouble("altura"));
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
                pacientes.add(paciente);//*

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pacientes;

    }

}
