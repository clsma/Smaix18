/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


/*
 *   Document   : FilterListener
 *   Created on : 15/06/2016, 10:00:53 AM
 *   Author     : Cl-sma Carlos Pinto
 */
@WebFilter(urlPatterns = "/servlet/*" , asyncSupported = true)
public class FilterListener implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Application Filter Started " + new Date());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest) request);
        HttpServletResponse resp = ((HttpServletResponse) response);
        HttpSession sesion = req.getSession();

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String ajax = req.getHeader("Ajax-Request");
        //Verifico que haya sesión en la aplicación
        if ((sesion == null || sesion.getAttribute("model") == null)
                && !isFromSystemPages(req.getServletContext(), req.getRequestURI())) {
//                ){
            //Verifico que haya sido petición ajax y retorno mensaje de fin de sesión
            if (ajax != null && !ajax.equals("")) {
                JSONObject json = new JSONObject();
                json.put("msg", "El servidor ha finalizado su sesión por inactividad.<br/>Recargue la página e ingrese nuevamente.");
                json.put("sessionEnd", true);
                resp.getWriter().write(JSONValue.toJSONString(json));
                response.flushBuffer();
            } else {
                //En caso de no ser petición ajax retorno a la página de login con el mensaje
                req.setAttribute("msgReason", "El servidor ha finalizado su sesión por inactividad.<br/>Recargue la página e ingrese nuevamente.");
                resp.sendRedirect(req.getServletContext().getContextPath() + "/vista/sistemas/login/login.jsp");
            }
            return;
        }
        chain.doFilter(request, response);
        
    }

    @Override
    public void destroy() {

    }

    private boolean isFromSystemPages(ServletContext context, String page) {
        ArrayList<String> arrPages = new ArrayList<String>();
        arrPages.add(context.getContextPath() + "/vista/sistemas/login/login.jsp");
        arrPages.add(context.getContextPath() + "/servlet/InitLogin/Application");
        return Util.arrayHasObject(arrPages, page);

    }

}
