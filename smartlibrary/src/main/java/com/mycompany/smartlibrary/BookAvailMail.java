/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartlibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author prana
 */
/*public class BookAvailMail {
    
}*/


public class BookAvailMail {
    Connect cObj = new Connect();
    Connection con = cObj.dbConnect();
    PreparedStatement pst;
    ResultSet rs, rsd, rsstatus;
    
    EmailUtil eobj = new EmailUtil();

    public void availMail(String bookid)
    {
        String bookidIssue = bookid;
        try 
        {
            pst = con.prepareStatement("select Nid, memberid, bookid from BookAvailMail");
            rsd = pst.executeQuery();
            while(rsd.next())
            {  
                String bid = rsd.getString("bookID");

                if(bookidIssue.equals(bid))
                {
                    String nid = rsd.getString("Nid");
                    String mid = rsd.getString("memberID");

                    pst = con.prepareStatement("select emailID from Members where memberID = ? ");
                    pst.setString(1, mid);
                    rs = pst.executeQuery();
                    rs.next();
                    String mailId = rs.getString("emailID");

                    String mailSub = "Book Available";

                    pst = con.prepareStatement("select bookName from books where bookID = ? ");
                    pst.setString(1, bid);
                    rs = pst.executeQuery();
                    rs.next();
                    String bookName = rs.getString("bookName");

                    String mailText = "Dear member,"
                                + "\nThe book \""+bookName+"\" is available in the library NOW.";

                    int getReturn = eobj.sendEmail(mailId, mailSub, mailText);

                    if(getReturn == 1)
                    {
                        pst = con.prepareStatement("delete from BookAvailMail where Nid = ? ")  ;
                        pst.setString(1, nid);
                        pst.executeUpdate();
                    }
                }
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(BookIssue.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQLException");
        }
    }
}

        
