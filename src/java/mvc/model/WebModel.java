package mvc.model;

import javax.servlet.*;
import javax.servlet.http.*;

/** 
 *  Subcalse de Model especifica de HTPP. Implementa vinculacion y
 *  desviculacion de sesion. Hace posible que la conexion a base de
 *  datos se desconecte cuando transcurre completamente el tiempo
 *  de espera de la sesion o cuando es invalida.
 */
public class WebModel extends ModelSma implements HttpSessionBindingListener{

    /**
     * Inicializa la conexion a base de datos
     * @param context
     */
     public void init(ServletContext context)throws ServletException{
         this.application = context;
       // Configura la propiedad del controlador JDBC del  modelo
       // a partir de un valor de ambito de aplicacion del web.xml
//       String jdbcDriver = context.getInitParameter("jdbcDriver");
//       if(jdbcDriver == null)
//         throw new ServletException ("Propiedad jdbcDriver no especificada");
//
//       setJdbcDriver(jdbcDriver);
//
//       // Hacer lo mismo para la URL
//       String databaseURL = context.getInitParameter("databaseURL");
//       if(databaseURL == null)
//         throw new ServletException ("Propiedad databaseURL no especificada");
//       setDatabaseURL(databaseURL);
//
//       // Confiuguro propiedad de Url para Nombre y Password de usuario
//	     String databaseUser = context.getInitParameter("databaseUser");
//       setDatabaseUser(databaseUser);
//       String databasePassword = context.getInitParameter("databasePassword");
//       setDatabasePassword(databasePassword);
//	     String useBitacora = context.getInitParameter("useBitacora");
         
         
//	     setUseBitacora(useBitacora);

       // Conectar a base de datos
//       try {
//         connect();
//       }catch(SQLException e){
//         throw new ServletException (e.getMessage());
//       }
     }

     /**
      * Invocado cuando el modelo se vincula a una sesion
      */
      public void valueBound(HttpSessionBindingEvent event){
      }

     /**
      * Invocado cuando el modelo se quita de una sesion
      */
      public void valueUnbound(HttpSessionBindingEvent event){
//      	this.setSwdisconnect(true);
//        disconnect();
      }
}