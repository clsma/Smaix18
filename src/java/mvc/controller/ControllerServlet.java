package mvc.controller;import mvc.model.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javassist.compiler.JvstCodeGen;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mvc.model.HibernateUtil;
import mvc.model.ModelSma;
import mvc.model.Util;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.LoggerFactory;

/**
 * El componente controlador de la arquitectura Modelo-Vista-Controlador para el
 * sistema Help
 */
@WebServlet( asyncSupported = true )
public class ControllerServlet extends HttpServlet {

    /**
     * Gestiona una peticion GET HTPP
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

     /**
     * Gestiona una peticion GET HTPP
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        HttpSession session = request.getSession(); // false para no crear nuevas sesiones en caso de que haya sido invalidada.
        
//        Map actionMap = null;
//        ArrayList<String> menu = null;
        ServletContext context = null;
        Action action = null;
        try {
            
            ModelSma model = session != null ? (ModelSma) session.getAttribute("model") : null;
            context = getServletContext();
            if (session == null || model == null) {

                String ajax = request.getHeader("Ajax-Request");
                if (ajax != null) {
                    JSONObject json = new JSONObject();
                    json.put("exito", "ERROR");
                    json.put("type", "ERROR");
                    json.put("msg", "El servidor ha finalizado su sesión por inactividad.<br/>Recargue la página e ingrese nuevamente.");
                    response.getWriter().write(JSONValue.toJSONString(json));
                    return;
                } else {
                    request.setAttribute("msgReason", "El servidor ha finalizado su sesión por inactividad.<br/>Recargue la página e ingrese nuevamente.");
                    getServletContext().getRequestDispatcher("/vista/sistemas/login/login.jsp").forward(request, response);
                    return;
                }

            }

//            actionMap = (Map) session.getAttribute("actionMap");
//            menu = (ArrayList<String>) session.getAttribute("menu");

//            if (actionMap == null) {
//                actionMap = new HashMap();
//                menu = new ArrayList<String>();
//                session.setAttribute("actionMap", actionMap);
//                session.setAttribute("menu", menu);
//            }

            // Obtener el estado y el evento de la informacion de la ruta
            String pathInfo = request.getPathInfo(); 
            if (pathInfo == null) {
                throw new ServletException("Estado Interno Invalido - No hay informacion de la ruta");
            }

            // Cargar el objeto accion (action) que gestiona el estado y el evento
//            action = (Action) actionMap.get(pathInfo);
//            if (action == null) {
                // Esta es la primera vez que el servlet esta accion
                // Obtiene el nombre del estado y el evento de pathInfo(informacion de la ruta)

                //La ruta obligatoriamente debe contener 2 '/' ej: si la clase se llama  ClienteDatos.java
                //el action del formulario seria algo asi : <%=CONTROLLER%>/Cliente/Datos
                StringTokenizer st = new StringTokenizer(pathInfo, "/");
                if (st.countTokens() != 2) {
                    throw new ServletException("Estado Interno Invalido - No hay informacion de la ruta [" + pathInfo + "]");
                }
                String state = st.nextToken();
                String event = st.nextToken();
                String idemnx = Util.getStrRequest("seek", request);

                // Formar el nombre de la clase a partir del estado y el evento
                String className = "mvc.controller." + state + event + "Action";
                // Cargar la clase y crea una instancia
                  Class actionClass;
                try {
                   
                     actionClass = Class.forName(className);
                    action = (Action) actionClass.newInstance();
                    

                    // Guardar en memoria cache el ejemplar en un plan de accion    
//                    actionMap.put(pathInfo, action);
                } catch (Exception e) {
                    Util.logError(e);
//                    menu.add(idemnx);
                    throw new ServletException("No se pudo cargar la clase " + className
                            + ": " + e.getMessage());
                }

//            }// End If

//            // Asegurar que existe un modelo en la session
//            ModelSma model = (ModelSma) session.getAttribute("model");
//            if (model == null) {
////                request.setAttribute("Type", "init");
////                throw new ServletException("SHWUSR:Sesion terminada.<br>Intente de nuevo ingresando a la aplicación");
////                response.sendRedirect(getServletContext().getContextPath() + "/vista/sistemas/login/login.jsp");
//                request.setAttribute("msgReason", "Su sesión Finalizó, Inicie Nuevamente");
//                getServletContext().getRequestDispatcher("/vista/sistemas/login/login.jsp").forward(request, response);
//                return;
//            }

            // Ahora ejecutar la accion. La accion deberia ejecutar a
            // RequestDispatcher.forward() cuando termine
            action.setRequest(request);
            action.setResponse(response);
            action.setSession(session);
            action.setApplication(context);
            model.setRequest(request);
            action.setModel(model);
            Util.loger = LoggerFactory.getLogger(actionClass);
            //Agrego el session factory del hibernate al action para que sea accecible desde la interfaz a cada clase
            action.setSessionInstance(HibernateUtil.getSessionFactory());
            action.run();
            //Luego de la ejecución de la clase termino la sesino hibernate
            action.CloseCurrentSession();
        } catch (ServletException e) {
            Util.logError(e);
            if (action != null) {
                action.CloseCurrentSession();
            }
            // Usar la pagina Jsp de error para todos los errores de servlet
            request.setAttribute("javax.servlet.jsp.jspException", e);
            RequestDispatcher rd = context.getRequestDispatcher("/vista/errorPage.jsp");
            if (response.isCommitted()) {
                rd.include(request, response);
            } else {
                rd.forward(request, response);
            }
        }
    }// END doPost
}
