/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.password_recovery;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ArsenyPC
 */
public class SmtpMessageSender {
        public Session createSession(String smtpHost, int smtpPort,
                                 String username, String password) {
        Properties props = new Properties();
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return Session.getInstance(props,
                createAuthenticator(username, password));
    }

    public MimeMessage createMimeMessage(Session session, String subject,
                                         String from, String to, Message.RecipientType recipientType)
            throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(recipientType, InternetAddress.parse(to));
        msg.setSubject(subject);
        msg.setContent(new MimeMultipart());
        return msg;
    }

   
    public MimeMessage addText(MimeMessage message, String text, String charset, String type) throws IOException, MessagingException {
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText(text, charset, type);
        MimeMultipart multipart = (MimeMultipart) message.getContent();
        multipart.addBodyPart(textPart);
        return message;
    }

        
    public void sendMimeMessage(MimeMessage message) throws MessagingException {
        Transport.send(message);
    }

    private Authenticator createAuthenticator(final String username,
                                              final String password) {
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
    }
    
    public void sendEmail(String emailTo, String text) throws MessagingException, IOException
    {
     String smtpHost = "smtp.gmail.com"; //host of smtp mail server
        int smtpPort = 465;               //port of smtp mail server
        String username = "comicszonetracker"; //your username e-mail account
        String password = "special4CZT"; //your password e-mail account

        SmtpMessageSender messageSender = new SmtpMessageSender();

        //creates the new JavaMail session
        Session session = messageSender.createSession(smtpHost,
                                                      smtpPort,
                                                      username,
                                                      password);
        //creates the message with empty content
        MimeMessage message = createMimeMessage(session, "Recovery Password", "comicszonetracker@gmail.com",emailTo, Message.RecipientType.TO); 
        //adds the plain text
//        messageSender.addText(message,text,"utf-8","plain");
        message.setText(text, "UTF-8","html");
        messageSender.sendMimeMessage(message); 
    }
}
