package mvc.controller;import mvc.model.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import mvc.util.LectorXML;
import org.json.simple.JSONObject;

/**
 * @descripcion Clase CRUD Idiomas
 * @nombre AdmCreateLanguageAction
 * @copyrigth ClSMA Ltda
 * @author Juan Camilo Wong
 * @fechaModificacion 17/07/2014
 */
public class AdmCreateLanguageAction extends Action {

    private String nxtPge = "";
    private String sqlCmd = "";
    private String events = "";
    private String mensaje = "";

    private List list;
    private Hashtable smaTmp;
    private List lstDta;

    /**
     * Ejecuta la accion
     *
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void run() throws ServletException, IOException {

        list = new LinkedList();
        // Recuperacion de estado de la peticion.  
        events = request.getParameter("events")
                != null ? request.getParameter("events") : "";
        String codlng = request.getParameter("codlng")
                != null ? request.getParameter("codlng") : "";
        String nomlng = request.getParameter("nomlng")
                != null ? request.getParameter("nomlng") : "";
        String nrolng = request.getParameter("nrolng")
                != null ? request.getParameter("nrolng") : "";

        try {

            if (events.equals("SEARCH")) {
                buscarIdioma(nrolng);
            } else if (events.equals("SAVE")) {
                guardarIdioma(codlng, nomlng);
                return;
            } else if (events.equals("EDIT")) {
                editarIdioma(nrolng, codlng, nomlng);
                return;
            } else if (events.equals("DELETE")) {
                eliminarIdioma(nrolng);
                return;
            }

            // Reenviar a la pagina JSP de confirmacion
            this.rd = application.getRequestDispatcher(nxtPge);
            if (this.rd == null) {
                throw new ServletException("No se pudo encontrar " + nxtPge);
            }
            this.rd.forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error "
                    + "Clase [" + this.getClass().getName()
                    + "] Metodo [run()] " + e.getMessage());
        }
    }

    /**
     * @descripcion Existe Lenguaje
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 28/07/2014
     * @fecha_modificacion 28/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private boolean existeIdioma(String codlng, String nomlng) throws
            SQLException, IOException {
        boolean flag = true;

        sqlCmd = "select * "
                + "  from smalng"
                + " where upper(codlng) = upper('" + codlng.trim() + "')";

        model.listGenericHash(sqlCmd);
        lstDta = model.getList();

        if (!lstDta.isEmpty()) {
            mensaje = "Existe un registro con el codigo (" + codlng.trim() + ")";
            flag = false;
        }

        sqlCmd = "select * "
                + "  from smalng"
                + " where upper(nomlng) = upper('" + nomlng.trim() + "')";

        model.listGenericHash(sqlCmd);
        lstDta = model.getList();

        if (!lstDta.isEmpty()) {
            mensaje = "Existe un registro con el nombre (" + nomlng.trim() + ")";
            flag = false;
        }

        return flag;
    }

    /**
     * @descripcion Guardar Lenguaje
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 18/07/2014
     * @fecha_modificacion 19/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void guardarIdioma(String codlng, String nomlng)
            throws SQLException, IOException, Exception {
        JSONObject jotason = new JSONObject();
        if (!existeIdioma(codlng, nomlng)) {
            jotason.put("exito", "ERROR");
            jotason.put("icon", "ERROR");
            jotason.put("mensaje", "Existe un registro con el nombre " + nomlng);
        } else {

            String sqlXml = LectorXML.getSqlXML(getUrlXml(), "INSERT_SMALNG ");
            Object[] values = new Object[4];

            values[0] = model.getSequence("nrolng");
            values[1] = codlng;
            values[2] = nomlng;
            values[3] = "I";

            try {
                model.save(sqlXml, values, "smalng", "I");
                jotason.put("exito", "EXIT");
                jotason.put("icon", "OK");
                jotason.put("mensaje", "Registro exitoso");
            } catch (Exception ex) {
                jotason.put("exito", "ERROR");
                jotason.put("icon", "ERROR");
                jotason.put("mensaje", ex.getLocalizedMessage());
                Util.logError(ex);

            }
        }
        response.getWriter().write(jotason.toJSONString());
    }

    /**
     * @descripcion Editar Lenguaje
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 18/07/2014
     * @fecha_modificacion 19/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void editarIdioma(String nrolng, String codlng, String nomlng)
            throws IOException, SQLException {
        JSONObject jotason = new JSONObject();
        String sqlXml = LectorXML.getSqlXML(getUrlXml(), "UPDATE_SMALNG ");
        Object[] values = new Object[4];

        values[0] = codlng;
        values[1] = nomlng;
        values[2] = "U";
        values[3] = nrolng;

        try {
            model.update(sqlXml, values, "SMALNG");
            jotason.put("exito", "EXIT");
            jotason.put("icon", "OK");
            jotason.put("mensaje", "Actualización exitosa");
        } catch (Exception ex) {
            jotason.put("exito", "ERROR");
            jotason.put("icon", "ERROR");
            jotason.put("mensaje", ex.getLocalizedMessage());
            Util.logError(ex);
        }
        response.getWriter().write(jotason.toJSONString());
    }

    /**
     * @descripcion Eliminar Lenguaje
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 18/07/2014
     * @fecha_modificacion 19/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void eliminarIdioma(String nrolng)
            throws IOException, SQLException {
        JSONObject jotason = new JSONObject();
        String sqlXml = LectorXML.getSqlXML(getUrlXml(), "DELETE_SMALNG ");
        Object[] values = new Object[1];

        values[0] = nrolng;

        try {
            model.delete(sqlXml, values, "SMALNG");
            jotason.put("exito", "EXIT");
            jotason.put("icon", "OK");
            jotason.put("mensaje", "Eliminación exitosa");
        } catch (Exception ex) {
            jotason.put("exito", "ERROR");
            jotason.put("icon", "ERROR");
            if(ex.getLocalizedMessage().toLowerCase().contains("could not execute native bulk manipulation query")){
                jotason.put("mensaje", "El registro ya ha sido asignado, no puede ser eliminado");
            }else{
                jotason.put("mensaje", ex.getLocalizedMessage());
            }
            
            Util.logError(ex);
        }
        response.getWriter().write(jotason.toJSONString());
    }

    /**
     * @descripcion Buscar Lenguaje
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 18/07/2014
     * @fecha_modificacion 19/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void buscarIdioma(String nrolng) throws IOException, SQLException {

        sqlCmd = "select * "
                + "  from smalng"
                + " where nrolng = '" + nrolng + "'";

        model.listGenericHash(sqlCmd);
        lstDta = model.getList();

        if (!lstDta.isEmpty()) {
            smaTmp = (Hashtable) lstDta.get(0);
        }

        request.setAttribute("smalng", smaTmp);
        nxtPge = "/vista/generales/formCreateLanguage.jsp?events=EDIT";
    }
}
