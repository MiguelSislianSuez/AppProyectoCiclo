/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Kentucky
 */
public class GraficosControladorController extends ControladorConNavegabilidad implements Initializable {

    @FXML
    private LineChart<?, ?> grafico;
    @FXML
    private Button volver;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cargarGraficos();
    }

    public void cargarGraficos() {
        //defining the axes
        grafico.getData().clear();
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Numero de año");
        //creating the chart
        //grafico = new LineChart<Number, Number>(xAxis, yAxis);

        grafico.setTitle("Rango edades de los pacientes");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Décadas");
        //populating the series with data
        //llamar a la matriz y recorrer
        TablaPacientesDao grafica = new TablaPacientesDao();

        ArrayList<ValorGrafica> rellenarGrafico = grafica.rellenarGrafico();
        for (int row = 0; row < rellenarGrafico.size(); row++) {
            series.getData().add(new XYChart.Data(rellenarGrafico.get(row).getAnio(), rellenarGrafico.get(row).getValor()));

        }

        grafico.getData().add(series);

    }

    @FXML
    private void mostrarPantalla(ActionEvent event) {
        this.layout.mostrarComoPantallaActual("a");
        grafico.getData().clear();
    }

}
