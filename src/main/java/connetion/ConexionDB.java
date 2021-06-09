package connetion;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Kentucky
 */
public class ConexionDB {

    protected Connection conexion;
    protected Statement sentencia;
    protected PreparedStatement sentenciaPreparada;
    protected ResultSet resultSet;

    /**
     * @param claseNombre
     * @param cadenaConexion
     */
    public ConexionDB(String claseNombre, String cadenaConexion) {
        try {
            Class.forName(claseNombre);
            conexion = DriverManager.getConnection(cadenaConexion);
            conexion.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ConexionDB(String claseNombre, String cadenaConexion, String usuario, String pass) {
        try {
            Class.forName(claseNombre);
            conexion = DriverManager.getConnection(cadenaConexion, usuario, pass);
            conexion.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Devuelve la sentencia
     *
     * @return Sentencia de la conexión
     */
    public Statement getSentencia() {
        return sentencia;
    }

    /**
     * Devuelve la conexión
     *
     * @return Conexión
     */
    public Connection getconexion() {
        return conexion;
    }

    /**
     * Devuelve el ResultSet
     *
     * @return ResultSet
     */
    public ResultSet getResultSet() {
        return resultSet;
    }

    /**
     * Devuelve la sentencia preparada
     *
     * @return PreparedStatement
     */
    public PreparedStatement getSentenciaPreparada() {
        return sentenciaPreparada;
    }

    
    /**
     * Cierra el ResultSet
     */
    public void cerrarResult() {
        try {
            resultSet.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la sentencia
     */
    public void cerrarSentencia() {
        try {
            sentencia.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cierra la conexion
     */
    public void cerrarConexion() {
        try {
            if (resultSet != null) {
                cerrarResult();
            }
            if (sentencia != null) {
                cerrarSentencia();
            }
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devuelve al resultset los resultados de una consulta
     *
     * @param consulta Consulta a ejecutar
     * @throws java.sql.SQLException
     */
    public void ejecutarConsulta(String consulta) throws SQLException {

        sentencia = conexion.createStatement();
        resultSet = sentencia.executeQuery(consulta);

    }

    /**
     * Devuelve el numero de filas afectadas por un delete, update o insert No
     * hace commit
     *
     * @param instruccion Instruccion a afectar (Insert, Update o Delete)
     * @return Números de filas afectadas
     */
    public int ejecutarInstruccion(String instruccion) throws SQLException {

        int filas = 0;

        sentencia = conexion.createStatement();
        filas = sentencia.executeUpdate(instruccion);

        return filas;
    }

    


   

    /**
     * Devuelve al resultset los resultados de una consulta
     *
     * @param consulta Consulta a ejecutar
     * @throws java.sql.SQLException
     */
    public void ejecutarConsultaPreparada(String consulta) throws SQLException {

        this.sentenciaPreparada = this.conexion.prepareStatement(consulta);
        resultSet = sentenciaPreparada.executeQuery();

    }

    

    /**
     * Devuelve el numero de filas afectadas por un delete, update o insert
     *
     * @param consulta Instruccion a ejecutar
     * @return
     * @throws java.sql.SQLException
     */
    public int ejecutarInstruccionPreparada(String consulta) throws SQLException {

        this.sentenciaPreparada = this.conexion.prepareStatement(consulta);
        int filas = sentenciaPreparada.executeUpdate();
        return filas;
    }

   

}
