/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mass.ejercicionavegabilidad;

import connetion.ConexionMySQL;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Kentucky
 */
public class Utils {
    

    public static String getTemplateEmail(String nameTemplate){
        
            String contenido="";
        
        try(BufferedReader br=new BufferedReader(new FileReader("template_email/" + nameTemplate))){
            
            String c;
            while((c=br.readLine())!= null){
                contenido+=c;
            }
            
        }catch(FileNotFoundException e){
            return null;
        }catch(IOException e){
            return null;
        }
        return contenido;
        
    }
    
    public static ConexionMySQL createConnection(){
        
        return new ConexionMySQL("localhost","3306", "PacientesDB", "root", "147852*Zeus*");
    }
}
