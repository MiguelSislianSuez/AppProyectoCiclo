/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Kentucky
 */
public class RelojController implements Initializable, Observer {

    @FXML
    private Label cronometro;

    public void initialize(URL url, ResourceBundle rb) {
        LocalDateTime l = LocalDateTime.now();
        RelojDigital r = new RelojDigital(l.getHour(), l.getMinute(), l.getSecond());
        r.addObserver(this);
        Thread reloj = new Thread(r);
        reloj.start();

    }

    public void update(Observable o, Object arg) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //update application thread
                cronometro.setText((String) arg);
            }
        });

    }

}
