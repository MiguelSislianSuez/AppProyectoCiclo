/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Kentucky
 */
public class HiloMail extends Thread {

    private String correo;
    private String asunto;
    private String mensaje;

    public HiloMail(String asunto, String mensaje, String correo) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.correo = correo;
        
    }

   

    public void run() {
        
        

        try {
            Mail m = new Mail("config/configuracion.prop");
            m.enviarEmailHtml(asunto, mensaje, correo);
            System.out.println("Se ha enviado!!");
        } catch (IOException | MessagingException ex) {
            Logger.getLogger(HiloMail.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
