/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartlibrary;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author prana
 */
public class EmailUtil {

   // public static void main(String[] args) {
    public int sendEmail(String gMail, String subject, String messageString){
        String toEmailAccount = gMail;
        String sendMessage = messageString;
        String mailSubject = subject; 
        final String username = "smartprojectprogram@gmail.com";
        final String password = "gugdjvvsdnxgfxsl"; //App password generated by Google for the particular GMail account.
//xiqzfmduvbfjalpg
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        int returnValue=0;
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("smartprojectprogram@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAccount/*"pranavembrans@gmail.com, pranavembran@gmail.com"*/)
            );
            message.setSubject(mailSubject/*"Testing Gmail SSL"*/);
            message.setText(sendMessage/*"Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!"*/);

            Transport.send(message);

            System.out.println("\n email sent \n");
            returnValue=1;

        } 
        catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("\n\n email not sent \n\n");

        }
      return returnValue;
    }

}
    