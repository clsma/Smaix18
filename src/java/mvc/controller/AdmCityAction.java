package mvc.controller;import mvc.model.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import mvc.model.Util;
import org.json.simple.JSONObject; 

/**
 * @descripcion Clase que se encarga del administracion de las Ciudades
 * @nombre AdmCityAction
 * @copyrigth ClSMA Ltda
 * @author Carlos Pinto
 * @fechaModificacion 18/07/2014
 */
public class AdmCityAction extends Action {

    // Constantes.
    private String codcia = "";
    private String codprs = "";
    private String tpoprs = "";
    // Variables String.
    private String nxtPge = "";
    private String sqlCmd = "";
    private String events = "";
    private String nrociu = "";
    private List list;
    private Hashtable smaTmp;
    private List lstDta;

    @Override
    public void run() throws ServletException, IOException {
        // Se inicializa el valor de las constantes.
        codcia = model.getCodCia();
        codprs = model.getCodPrs();
        tpoprs = model.getTpoPrs();

        events = request.getParameter("state")
                != null ? request.getParameter("state") : "";
        nrociu = request.getParameter("nrociu")
                != null ? request.getParameter("nrociu") : "";

        try {
            if (events.equals("SEARCH")) {
                buscarCiudad();
                return;
            } else if (events.equals("SAVE")) {
                guardarCiudad();
                return;
            } else if (events.equals("EDIT")) {
                editarCiudad();
                return;
            } else if (events.equals("DELETE")) {
                eliminarCiudad();
                return;
            } else if (events.equals("LISTA")) {
//                getLista();
//                return;
            } else if (events.equals("CONT")) {
//                String sesion = (String)application.getAttribute("prs_" + model.getCodPrs());
//                if (sesion == (null) || sesion.equals("")) {
//
//                    String value = Util.getStrRequest("value", request);
//                    application.setAttribute("prs_" + model.getCodPrs(), value);
//                } else {
//                    response.getWriter().write("Mira ve : " + sesion);
//
//                }
                return;

            } else if (events.equals("img")) {
                getImage();
                return;
            }
            // Reenviar a la pagina JSP de confirmacion
            this.rd = application.getRequestDispatcher(nxtPge);

            if (this.rd == null) {
                throw new ServletException("No se pudo encontrar " + nxtPge);
            }

            this.rd.forward(request, response);
        } catch (SQLException e) {
            Util.logError(e);
        }
    }

    /**
     * @descripcion Elimina Ciudad
     * @nombre AdmCreateDocumentAction
     * @copyrigth ClSMA Ltda
     * @author Carlos Pinto
     * @fechaModificacion 18/07/2014
     */
    private void eliminarCiudad() throws IOException, SQLException {

        String ideciu = request.getParameter("ideciu")
                != null ? request.getParameter("ideciu") : "";

        String sqlSCN = "select * from smascn join smaciu on smaciu.ideciu=smascn.ideciu "
                + "where smaciu.ideciu='" + ideciu + "'";
        model.listGenericHash(sqlSCN);
        if (!model.getList().isEmpty()) {
            response.getWriter().print("0#No puede eliminar. hay Seccional con esta ciudad asignada");
            return;
        }

        String sqlSde = "select * from smasde join smaciu on smaciu.codciu=smasde.codciu "
                + "where smaciu.ideciu='" + ideciu + "'";

        model.listGenericHash(sqlSde);
        if (!model.getList().isEmpty()) {
            response.getWriter().print("0#No puede eliminar. hay Sede con esta ciudad asignada");
            return;
        }

        try {

            model.deleteLogBook("smaciu", ideciu, null);
            response.getWriter().print("1#" + model.MSG_DELETE);
        } catch (Exception ex) {
            response.getWriter().print("0#" + model.MSG_ERROR);
            Util.logError(ex);
        }

    }

    /**
     * @descripcion Edita Ciudad
     * @nombre AdmCreateDocumentAction
     * @copyrigth ClSMA Ltda
     * @author Carlos Pinto
     * @fechaModificacion 18/07/2014
     */
    private void editarCiudad() throws IOException, SQLException {

        String codciu = request.getParameter("codciu")
                != null ? request.getParameter("codciu") : "";
        String nomciu = request.getParameter("nomciu")
                != null ? request.getParameter("nomciu") : "";
        String npqciu = request.getParameter("npqciu")
                != null ? request.getParameter("npqciu") : "";
        String idedpt = request.getParameter("idedpt")
                != null ? request.getParameter("idedpt") : "";
        String stdciu = request.getParameter("stdciu")
                != null ? request.getParameter("stdciu") : "Activa ..";
        String idciu = request.getParameter("ideciu")
                != null ? request.getParameter("ideciu") : "";

        Map datos = new HashMap();
        datos.put("codciu", codciu);
        datos.put("nomciu", nomciu);
        datos.put("npqciu", npqciu);
        datos.put("stdciu", stdciu);
        datos.put("idedpt", idedpt);

        try {
            model.updateLogBook(datos, "smaciu", idciu, null);
            response.getWriter().print("1#" + model.MSG_UPDATE);
        } catch (Exception ex) {
            response.getWriter().print("0#" + model.MSG_ERROR);
            Util.logError(ex);
        }
    }

    /**
     * @descripcion Guarda Ciudad
     * @nombre AdmCreateDocumentAction
     * @copyrigth ClSMA Ltda
     * @author Carlos Pinto
     * @fechaModificacion 18/07/2014
     */
    private void guardarCiudad() throws SQLException, IOException {

        String codciu = request.getParameter("codciu")
                != null ? request.getParameter("codciu") : "";
        String nomciu = request.getParameter("nomciu")
                != null ? request.getParameter("nomciu") : "";
        String npqciu = request.getParameter("npqciu")
                != null ? request.getParameter("npqciu") : "";
        String idedpt = request.getParameter("idedpt")
                != null ? request.getParameter("idedpt") : "";
        String stdciu = request.getParameter("stdciu")
                != null ? request.getParameter("stdciu") : "Activa ..";

        String sql = "select * from smaciu where codciu='"
                + codciu + "' or nomciu='" + nomciu + "'";

        model.list(sql, null);
        List lis = model.getList();

        if (lis.isEmpty()) {
            try {

                Map datos = new HashMap();
//                datos.put("ideciu", model.getSequence("ideciu"));
                datos.put("codciu", codciu);
                datos.put("nomciu", nomciu);
                datos.put("npqciu", npqciu);
                datos.put("idedpt", idedpt);
                datos.put("stdciu", stdciu);

//                model.saveLogBook(datos, "SMACIU", null);
                model.saveLogBook(datos, "smaciu", null);
//                model.save(sqlXml, values, "SMACIU", "I");
                response.getWriter().print("1#" + model.MSG_SAVE);
            } catch (Exception ex) {
                response.getWriter().print("0#" + model.MSG_ERROR);
                Util.logError(ex);
            }
        } else {
            response.getWriter().print("0#Ya Existe Ciudad con este código o nombre.."
                    + "\nNo puede guardar");
        }
    }

    /**
     * @descripcion Busca Ciudad
     * @nombre AdmCreateDocumentAction
     * @copyrigth ClSMA Ltda
     * @author Carlos Pinto
     * @fechaModificacion 18/07/2014
     */
    private void buscarCiudad() throws SQLException, IOException {
        String ideciu = request.getParameter("codciu")
                != null ? request.getParameter("codciu") : "";
        sqlCmd = "SELECT * FROM SMACIU , SMADPT  where CODCIU = '" + ideciu
                + "'  AND SMACIU.IDEDPT=SMADPT.IDEDPT ";

        model.listGenericHash(sqlCmd);
        lstDta = model.getList();

        if (!lstDta.isEmpty()) {
            smaTmp = (Hashtable) lstDta.get(0);
        }

        nxtPge = "/vista/generales/formCity.jsp?state=EDIT";
        JSONObject respuesta = new JSONObject();
        respuesta.put("ideciu", smaTmp.get("IDECIU"));
        respuesta.put("codciu", smaTmp.get("CODCIU"));
        respuesta.put("nomciu", smaTmp.get("NOMCIU"));
        respuesta.put("npqciu", smaTmp.get("NPQCIU"));
        respuesta.put("idedpt", smaTmp.get("IDEDPT"));
        respuesta.put("stdciu", smaTmp.get("STDCIU"));
        respuesta.put("nomdpt", smaTmp.get("NOMDPT"));
        response.getWriter().print(respuesta.toJSONString());
    }

//    private void getLista() throws IOException, SQLException {
//
//        sqlCmd = "select codciu,nomciu,ideciu from smaciu";
//
//        LinkedHashMap options = new LinkedHashMap();
//        options.put("loadComplete", "alerta");
//        options.put("align", "center");
//
//        LinkedHashMap hasFormatter = new LinkedHashMap();
//        hasFormatter.put("0", "callFormat");
//
//        jQgridTab jq = new jQgridTab();
//        jq.setModel(model);
//        jq.setTitles(new String[]{"Codigo", "Nombre", "Numero"});
//        jq.setStatement(sqlCmd);
//        jq.setSelector("jqTable");
//        jq.setPaginador("jqPager");
//        jq.setOptions(options);
//        jq.setFormatter(hasFormatter);
//
//        response.getWriter().write(jq.getHtml());
//
//    }
    private void getImage() throws SQLException, IOException {

        sqlCmd = "select objbrc from smabrc";
        byte[] bytes = model.bytesImgDB(sqlCmd, null);
        String data = Util.byteTo64Str(bytes);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.setContentLength(bytes.length);
        OutputStream os = response.getOutputStream();
        os.write(bytes);
//        response.getWriter().write("data:image/jpg;base64," + data);

    }
}
