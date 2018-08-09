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
 * @descripcion Clase que se encarga de la administracion de paises
 * @nombre AdmCountryAction
 * @copyrigth ClSMA Ltda
 * @author Erick Castillo Caballero
 * @fechaModificacion 15/07/2014
 */
public class AdmCountryAction extends Action {

    // Constantes.
    private String codcia = "";
    private String codprs = "";
    private String tpoprs = "";

    // Variables String.
    private String nxtPge = "";
    private String sqlCmd = "";
    private String events = "";
    private String exito = "";
    private String idepai = "";
    private List list;
    private Hashtable smaTmp;
    private List lstDta;

    @Override
    public void run() throws ServletException, IOException {

        // Se inicializa el valor de las constantes.
        codcia = model.getCodCia();
        codprs = model.getCodPrs();
        tpoprs = model.getTpoPrs();

        list = new LinkedList();
        // Recuperación de estado de la petición.  
        events = request.getParameter("state")
                != null ? request.getParameter("state") : "";
        idepai = request.getParameter("idepai")
                != null ? request.getParameter("idepai") : "";

        try {

            if (events.equals("SEARCH")) {
                buscarPaises();
                return;
            } else if (events.equals("SAVE")) {
                guardarPaises();
                return;
            } else if (events.equals("EDIT")) {
                modificarPaises();
                return;
            } else if (events.equals("DELETE")) {
                eliminarPaises();
                return;
            }

            // Reenviar a la pagina JSP de confirmacion
            this.rd = application.getRequestDispatcher(nxtPge);
            if (this.rd == null) {
                throw new ServletException("No se pudo encontrar " + nxtPge);
            }
            this.rd.forward(request, response);

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException("Error "
                    + "Clase [" + this.getClass().
                    getName() + "] Metodo [run()] " + e.getMessage());
        }
    }

    /**
     * @descripcion Eliminar un pais
     * @autor Erick Castillo Caballero
     * @fecha_creacion 15/07/2014
     * @fecha_modificacion 15/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void eliminarPaises() throws SQLException, IOException, Exception {
        idepai = request.getParameter("idepai")
                != null ? request.getParameter("idepai") : "";

        boolean valpai = exitenDepartamentos(idepai);
        
        if(valpai == true){
            response.getWriter().print("ERROR##No se puede eliminar "
                    + "el pais, porque ya tiene departamentos asociados");
            return;
        }
        
        String sqlXml = LectorXML.getSqlXML(getUrlXml(), "DELETE_SMAPAI");

        Object[] values = new Object[1];
        values[0] = idepai;

        try {
            model.delete(sqlXml, values, "SMAPAI");
            response.getWriter().print("EXITO##Registro Eliminado con exito");
        } catch (Exception e) {
            response.getWriter().print(e.getMessage());
            Util.logError(e);
            throw new Exception(e.getMessage());
        }
    }

    /**
     * @descripcion Modificar un pais
     * @autor Erick Castillo Caballero
     * @fecha_creacion 15/07/2014
     * @fecha_modificacion 15/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void modificarPaises() throws SQLException, IOException {
        idepai = request.getParameter("idepai")
                != null ? request.getParameter("idepai") : "";
        String codpai = request.getParameter("codpai")
                != null ? request.getParameter("codpai") : "";
        String isapai = request.getParameter("isapai")
                != null ? request.getParameter("isapai") : "";
        String isbpai = request.getParameter("isbpai")
                != null ? request.getParameter("isbpai") : "";
        String nompai = request.getParameter("nompai")
                != null ? request.getParameter("nompai") : "";

        String sqlXml = LectorXML.getSqlXML(getUrlXml(), "UPDATE_SMAPAI");

        Object[] values = new Object[6];
        values[0] = codpai;
        values[1] = isapai;
        values[2] = isbpai;
        values[3] = nompai.toUpperCase();
        values[4] = " ";
        values[5] = idepai;

        try {
            model.update(sqlXml, values, "SMAPAI");
            response.getWriter().print("EXITO##Registro Actualizado con exito");
        } catch (Exception e) {
            response.getWriter().print(e.getMessage());
            Util.logError(e);
            try {
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(AdmCityAction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @descripcion Guardar un pais
     * @autor Erick Castillo Caballero
     * @fecha_creacion 15/07/2014
     * @fecha_modificacion 15/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void guardarPaises() throws SQLException, IOException, Exception {
        
        idepai = request.getParameter("idepai")
                != null ? request.getParameter("idepai") : "";
        String codpai = request.getParameter("codpai")
                != null ? request.getParameter("codpai") : "";
        String isapai = request.getParameter("isapai")
                != null ? request.getParameter("isapai") : "";
        String isbpai = request.getParameter("isbpai")
                != null ? request.getParameter("isbpai") : "";
        String nompai = request.getParameter("nompai")
                != null ? request.getParameter("nompai") : "";

        if (existePais(codpai)) {
            response.getWriter().print("ERROR##Ya existe un registro "
                    + "con este codigo "+codpai+" , por favor verifique");
            return;
        }
        
        if (existeNombrePais(nompai.trim())) {
            response.getWriter().print("ERROR##Ya existe un registro "
                    + "con este nombre "+nompai.toUpperCase()+" , por favor verifique");
            return;
        }

        String sqlXml = LectorXML.getSqlXML(getUrlXml(), "INSERT_SMAPAI");

        String[] values = new String[5];
        values[0] = codpai;
        values[1] = isapai;
        values[2] = isbpai;
        values[3] = nompai.toUpperCase();
        values[4] = " ";

        try {
            model.callSqlInsert(sqlXml, values, "smapai");
            response.getWriter().print("EXITO##Registro exitoso");
        } catch (Exception ex) {
            response.getWriter().print(ex.getMessage());
            Util.logError(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * @descripcion Buscar todos los paises
     * @autor Erick Castillo Caballero
     * @fecha_creacion 15/07/2014
     * @fecha_modificacion 15/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private void buscarPaises() throws SQLException, IOException, Exception {

        sqlCmd = "select * "
                + "  from smapai"
                + " where idepai = '" + idepai + "'";

        model.listGenericHash(sqlCmd);
        lstDta = model.getList();
        smaTmp = new Hashtable();

        if (!lstDta.isEmpty()) {
            smaTmp = (Hashtable) lstDta.get(0);
        }
        request.setAttribute("smaPai", smaTmp);
        nxtPge = "/vista/generales/formCountry.jsp?state=EDIT";

        JSONObject respuesta = new JSONObject();

        respuesta.put("codpai", smaTmp.get("CODPAI"));
        respuesta.put("nompai", smaTmp.get("NOMPAI"));
        respuesta.put("isapai", smaTmp.get("ISAPAI"));
        respuesta.put("isbpai", smaTmp.get("ISBPAI"));
        response.getWriter().print(respuesta.toJSONString());
    }

    /**
     * @descripcion validacion para verificar si existe el codigo y nombre del pais 
     * un pais con los mismos datos
     * @autor Erick Castillo Caballero
     * @fecha_creacion 28/07/2014
     * @fecha_modificacion 28/07/2014
     * @throws IOException
     * @throws SQLException
     */
    private boolean existePais(String codpai) throws SQLException {
        
        String valid = "select codpai,"
                            + "nompai "
                       + "from smapai "
                      + "where trim(codpai) = '"+codpai+"' ";
        
        model.listGenericHash(valid);
        list = model.getList();
        
        if(!list.isEmpty()){
            return true;
        }
        
        return false;
    }
    
    /**
     * @descripcion validacion para verificar si existe un pais con el nombre ingresado
     * @autor Erick Castillo Caballero
     * @fecha_creacion 25/08/2014
     * @fecha_modificacion 25/08/2014
     * @throws IOException
     * @throws SQLException
     */
    private boolean existeNombrePais(String nompai) throws SQLException {
        
        String valid = "select codpai,"
                            + "nompai "
                       + "from smapai "
                      + "where lower(trim(nompai)) = '"+nompai.trim()+"' ";
        
        model.listGenericHash(valid);
        list = model.getList();
        
        if(!list.isEmpty()){
            return true;
        }
        
        return false;
    }
    
    /**
     * @descripcion validacion para ver si los paises tiene asociados departamentos
     * @autor Erick Castillo Caballero
     * @fecha_creacion 05/08/2014
     * @fecha_modificacion 05/08/2014
     * @throws SQLException
     */
    private boolean exitenDepartamentos(String idepai) throws SQLException{
        
        sqlCmd = "select idepai "
                 + "from smadpt "
                + "where idepai = '"+idepai+"' ";
        
        model.listGenericHash(sqlCmd);
        list = model.getList();
        
        if(!list.isEmpty()){
            return true;
        }
        
        return false;
    }
}