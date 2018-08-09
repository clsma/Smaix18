/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.util.Date;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import mvc.controller.InitLoginApplicationAction;


/*
 *   Document   : SessionListener
 *   Created on : 15/06/2016, 08:38:45 AM
 *   Author     : Cl-sma Carlos Pinto
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ModelSma model = ((ModelSma) se.getSession().getAttribute("model"));
        if (model.isLogged()) {
            System.out.println("Sesion terminada " + new Date() + " Verificando y acabando sesion del usuario  " + model.getCodPrs());
            InitLoginApplicationAction.decrementSessions(se.getSession().getServletContext());
            InitLoginApplicationAction.kill(model, se.getSession().getServletContext(), se.getSession() , "");
        }
    }

}
