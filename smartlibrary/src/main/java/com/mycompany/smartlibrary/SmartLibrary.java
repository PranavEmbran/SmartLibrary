/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

/*import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.Locale;
import java.util.concurrent.TimeUnit;*/

package com.mycompany.smartlibrary;

/**
 *
 * @author prana
 */
public class SmartLibrary {
    public static void main(String[] args) {
        System.out.println("***** Welcome to Smart Library System *****");
        Login log = new Login();
        log.setVisible(true);   //log.show();
        
        DueDateNotify dobj = new DueDateNotify();
        dobj.dueNotify();
        
        //Chart1 c1obj = new Chart1();
        
   }
}
        
        

    
        
        






