package mvc.controller;import mvc.model.Util;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mvc.model.ModelSma;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONValue;

/**
 * La clase base para todas las transiciones de estado
 */
public abstract class Action implements Serializable {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected ServletContext application;
    protected ModelSma model;
    protected RequestDispatcher rd = null;
    protected SessionFactory sessionInstance = null;
    protected Session currentSession = null;
    protected StringBuilder sqlCommand;
    private  StringBuilder sqlCmd;


    /**
     * *
     * Ejecuta la accion. Las subclases deberian sobreescribir este metodo y
     * hacer que reenvie la peticion a la siguiente componenete de la vista
     * cuando termine de procesar.
     *
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public abstract void run() throws ServletException, IOException;

    /**
     * Configura la peticion.
     *
     * @param request la peticion
     * @throws java.io.UnsupportedEncodingException
     */
    public void setRequest(HttpServletRequest request)
            throws UnsupportedEncodingException {
        this.request = request;
        this.request.setCharacterEncoding("UTF-8");
    }

    /**
     * Configura el objeto session.
     *
     * @param session
     */
    public void setSession(HttpSession session) {
        this.session = session;
    }

    /**
     * Configura la respuesta.
     *
     * @param response la respuesta
     */
    public void setResponse(HttpServletResponse response) {
        this.response = response;
        this.response.setHeader("Content-Type", "text/html; charset=UTF-8");
    }

    /**
     * Configura el contexto de servlet
     *
     * @param application la applicacion.
     */
    public void setApplication(ServletContext application) {
        this.application = application;
    }

    /**
     * Configura el modelo.
     *
     * @param model el modelo
     */
    public void setModel(ModelSma model) {
        this.model = model;
    }

    public String getUrlXml() {
        String xmlFile = "";
        // Para debug desarollo
            xmlFile = getClass().getProtectionDomain().getCodeSource().getLocation().toString() +
                  getClass().getPackage().getName().replaceAll("\\.", java.io.File.separator)+
                  java.io.File.separator+"xml"+java.io.File.separator+getClass().getSimpleName()+".xml"; 
        // Para producción             
//            xmlFile = 
//                   getClass().getProtectionDomain().getCodeSource().getLocation().toString()
//                           .replace(getClass().getSimpleName()+".class", "")
//                    +"xml"+java.io.File.separator+getClass().getSimpleName()+".xml" ;
        return xmlFile;
    }

    public Session getNewSession() {
        currentSession = sessionInstance.openSession();
        return currentSession;
    }

    public void CloseCurrentSession() {
        if (currentSession != null && currentSession.isOpen()) {
            currentSession.clear();
            currentSession.close();
        }
    }

    public void setSessionInstance(SessionFactory sessionFactory) {
        this.sessionInstance = sessionFactory;
    }

    //Codigo definido fuera del estandar de oracle
    final String ERROR_CODE_PROCEDURE = "ORA-20101";

    public boolean isTrownProcedure(Exception ex) {
        return ex.getMessage().startsWith(ERROR_CODE_PROCEDURE);
    }

    public String getMessage(String errorMessage) {
        return errorMessage.split(":")[1];
    }
    
    public void openSqlCommand(){
        if(sqlCmd == null){
            sqlCmd = new StringBuilder();             
        }else{
            sqlCmd.setLength(0);
        }
        sqlCommand = sqlCmd;
    }
    
    public StringBuilder setSqlCommand(String linea){
        sqlCommand.append(linea);
        return sqlCommand;
    }
    public String getSqlCommand(){
        System.out.println(sqlCommand.toString());
        return sqlCommand.toString();
    }     
    
    public void writeJsonResponse(Object jsonValue) throws IOException {
        response.getWriter().write(JSONValue.toJSONString(jsonValue));
    }
}
