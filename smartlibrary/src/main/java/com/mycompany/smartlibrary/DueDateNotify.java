/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartlibrary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//import java.sql.ResultSetMetaData;
//import java.text.ParsePosition;
//import java.util.Calendar;
//import java.util.Vector;
//import javax.swing.table.DefaultTableModel;

/**
 *
 * @author prana
 */
public class DueDateNotify {
    Connect cObj = new Connect();
    Connection con = cObj.dbConnect();
    PreparedStatement pst;
    ResultSet rs, rsd, rsstatus;
    
    EmailUtil eobj = new EmailUtil();

    public void dueNotify()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);

        try 
        {
            pst = con.prepareStatement("select memberID, bookID, issueDate, returnDate, issueID from bookIssue");
            rsd = pst.executeQuery();
            while(rsd.next())
            {
                /***** 
                 *[1] Code segment to find the difference between today's date and due date 
                 *****/
                String issuedate = rsd.getString("issueDate");
                String retdate = rsd.getString("returnDate");
                
                String issueID = rsd.getString("issueID");

                Date parsedDate = null;                            
                try 
                {
                    parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(retdate);
                }
                catch (ParseException ex) 
                {
                    Logger.getLogger(DueDateNotify.class.getName()).log(Level.SEVERE, null, ex);
                }
                Date d1 = null;
                Date d2 = null;
                try 
                {
                    d1 = sdf.parse(str);
                    d2 = parsedDate;
                } 
                catch (ParseException ex)
                {
                    Logger.getLogger(SmartLibrary.class.getName()).log(Level.SEVERE, null, ex);
                }
                long diff = d2.getTime() - d1.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long difference = time.convert(diff, TimeUnit.MILLISECONDS);
                System.out.println("\nThe difference in days is : "+difference+"\n");
                /*****End of [1]*****/
                
                /***** 
                 *[2] Send mail 
                 *****/
                if(difference<=1)
                {
                    pst = con.prepareStatement("SELECT issueID FROM duealertsent WHERE issueID = ?");
                    pst.setString(1, issueID);
                    rsstatus = pst.executeQuery();
                    String issueIDcheck = null;
                    if(rsstatus.next())
                    {   //rsstatus.next(); 
                        issueIDcheck = rsstatus.getString("issueID");
                        //System.out.println("\nissueIDcheck = "+issueIDcheck+"\tissueID = "+issueID+"\n");
                    }
                    if(!issueID.equals(issueIDcheck))
                    {
                        String mid = rsd.getString("memberID");
                        String bid = rsd.getString("bookID");

                        pst = con.prepareStatement("select emailID from Members where memberID = ? ");
                        pst.setString(1, mid);
                        rs = pst.executeQuery();
                        rs.next();
                        String mailIdOnissue = rs.getString("emailID");

                        String mailSubOnissue = "Book return date alert";

                        pst = con.prepareStatement("select bookName from books where bookID = ? ");
                        pst.setString(1, bid);
                        rs = pst.executeQuery();
                        rs.next();
                        String bookName = rs.getString("bookName");

                        String mailTextOnissue = "Dear member,"
                                + "\nThe book \""+bookName+"\" was issued to you on "+issuedate+"."
                                + "\nReturn date is: "+retdate;

                        int getReturn = eobj.sendEmail(mailIdOnissue, mailSubOnissue, mailTextOnissue);

                        //System.out.println("\n"+getReturn+"\n");
                        if(getReturn == 1)
                        {
                            pst = con.prepareStatement("insert into duealertsent(issueID) values(?)")  ;
                            pst.setString(1, issueID);
                           // pst.setInt(2, getReturn);
                            pst.executeUpdate();

                        }
                    }
                    else
                        System.out.println("\n Due date alert already sent \n");
                }
                /***** End of [2] *****/
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(BookIssue.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQLException");
        }
    }
}

        
