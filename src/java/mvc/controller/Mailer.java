/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;import mvc.model.Util;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

/**
 *
 * @author Carlos Pinto Jimenez ( Cl-Sma )
 * @Created 20/09/2016 11:31:36 AM
 * @Archivo AdmMailAction
 */
class Mail {

    private String mail_host;
    private String to_ems;
    private String from_ems;
    private String Cc_ems[];
    private String Cco_ems[];
    private String subject_ems;
    private String content_ems;
    private Object files_ems[];

    public Mail() {
    }

    public String getTo_ems() {
        return to_ems;
    }

    public void setTo_ems(String to_ems) {
        this.to_ems = to_ems;
    }

    public String getFrom_ems() {
        return from_ems;
    }

    public void setFrom_ems(String from_ems) {
        this.from_ems = from_ems;
    }

    public String[] getCc_ems() {
        return Cc_ems;
    }

    public void setCc_ems(String[] Cc_ems) {
        this.Cc_ems = Cc_ems;
    }

    public String[] getCco_ems() {
        return Cco_ems;
    }

    public void setCco_ems(String[] Cco_ems) {
        this.Cco_ems = Cco_ems;
    }

    public String getSubject_ems() {
        return subject_ems;
    }

    public void setSubject_ems(String subject_ems) {
        this.subject_ems = subject_ems;
    }

    public String getContent_ems() {
        return content_ems;
    }

    public void setContent_ems(String content_ems) {
        this.content_ems = content_ems;
    }

    public Object[] getFiles() {
        return files_ems;
    }

    public void setFiles(Object[] files) {
        this.files_ems = files;
    }

    public String getMail_host() {
        return mail_host;
    }

    public void setMail_host(String mail_host) {
        this.mail_host = mail_host;
    }

    public void sendMail() throws Exception {

        if (from_ems == null || from_ems.trim().isEmpty()) {
            throw new Exception("No se ha definido remitente del correo");
        }

        if (mail_host == null || mail_host.trim().isEmpty()) {
            throw new Exception("No se ha definido servidor de correo");
        }

        if (to_ems == null || to_ems.trim().isEmpty()) {
            throw new Exception("No se ha definido destinatario del correo");
        }

        if (to_ems == null || to_ems.trim().isEmpty()) {
            throw new Exception("No se ha definido destinatario del correo");
        }

        Properties props = System.getProperties();
        // XXX - could use Session.getTransport() and Transport.connect()
        // XXX - assume we're using SMTP
        if (mail_host != null) {
            props.put("mail.smtp.host", mail_host);
        }

        // Get a Session object
        Session session = Session.getInstance(props, null);

        //Creamos la variable mensaje
        Message msg = new MimeMessage(session);
        //Agregamos remitente
        msg.setFrom(new InternetAddress(from_ems));
        //Agregamos receptor primario

        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to_ems, false));

        if (Cc_ems != null) {
            for (String a : Cc_ems) {
                msg.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(a, false));
            }

        }
        if (Cco_ems != null) {
            for (String a : Cco_ems) {
                msg.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(a, false));
            }

        }
        //Agregamos el asunto del mensaje
        msg.setSubject(subject_ems);
        content_ems = content_ems.replace("[ENTER]", "\n");

        if (files_ems != null) {
            // Agregamos el/los archivos enviados

            MimeMultipart mp = new MimeMultipart();

            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(content_ems);
            mp.addBodyPart(mbp1);

            MimeBodyPart mbp2 = new MimeBodyPart();

            File f = null;
            boolean hasFiles = false;
            for (Object a : files_ems) {

                try {
                    if (a instanceof String) {
                        f = new File(a.toString());
                    } else if (a instanceof File && ((File) a).isFile()) {
                        f = (File) a;
                    } else {
                        continue;
                    }

                    mbp2.attachFile(f);
                    hasFiles = true;
                } catch (Exception e) {
                    System.err.println("Error parseando Archivos: " + e.getMessage());
                }
            }

            if (hasFiles) {
                mp.addBodyPart(mbp2);
            }
            //Agregamos un cuerpo multipart
            msg.setContent(mp);

        } else {
            msg.setText(content_ems);
        }

        msg.setHeader("X-Mailer", "Cl-Sma");
        msg.setSentDate(new Date());

        // Enviamos el mensaje
        Transport.send(msg);

    }

}

public abstract class Mailer {

    private static String _sender;
    private static String _server;
    private static ServletContext _app;
    private static Map _config;
    
    public static Mail provide() {

        Mail m = new Mail();
        m.setFrom_ems(Mailer._sender);
        m.setMail_host(Mailer._server);
        return m;
    }

    public static String getSender() {
        return _sender;
    }

    public static void setSender(String _sender) {
        Mailer._sender = _sender;
    }

    public static String getServer() {
        return _server;
    }

    public static void setServer(String _server) {
        Mailer._server = _server;
    }

    public static ServletContext getApp() {
        return _app;
    }

    public static void setApp(ServletContext _app) {
        Mailer._app = _app;
    }

    public static Map getConfig() {
        return _config;
    }

    public static void setConfig(Map _config) {
        Mailer._config = _config;
    }

}
