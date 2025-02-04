/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartlibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prana
 */

public class Connect 
{
    Connection conp;
    public Connection dbConnect()
    {
        try
        { 
            Class.forName("com.mysql.cj.jdbc.Driver");
            conp=DriverManager.getConnection("jdbc:mysql://localhost:3306/smartlibrary","root","2001");            
        } 
        catch (SQLException | ClassNotFoundException | NullPointerException ex) 
        {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conp;
    }
}
