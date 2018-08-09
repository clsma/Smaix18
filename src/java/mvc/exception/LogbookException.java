/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

/**
 *
 * @author clsma
 */
public class LogbookException extends Exception {

    public LogbookException(String msg) {
        super(resultInfo(msg, "(LOGBOOK)"));
    }

    public LogbookException(SQLException msg) {

        super(resultInfo(getException(msg), "(LOGBOOK)"));
    }

    public LogbookException(Exception msg) {

        super(resultInfo(getException(msg), "(LOGBOOK)"));
    }

    public static String getException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static String resultInfo(String msg, String with) {
        String aux = "";
        String find = msg.contains("&") ? "&" :with;
        if (msg.contains(find)) {
            aux = msg.substring(0, msg.lastIndexOf(find));
            aux = aux.substring(msg.indexOf(find)).replace(find, "");
            return "~Fixed~" + aux + "~Fixed~";
        }
        return msg;

    }

    public static String resultFixed(String msg, String with) {
        if (msg.contains(with)) {
            return msg.replaceAll(with, "");
        }
        return "";

    }
}
