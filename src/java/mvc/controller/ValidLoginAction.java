package mvc.controller;import mvc.model.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import mvc.model.Util;

/**
 * @descripcion Clase que se encarga de la autenticación del usuario al sistema
 * @nombre ValidLoginAction
 * @copyrigth ClSMA Ltda
 * @author Efrain Blanco
 * @fechaModificacion 10/07/2014
 */
public class ValidLoginAction extends Action {

    String codcia = "";

    /**
     * Ejecuta la accion
     *
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void run() throws ServletException, IOException {

        String nextPage = "";
        codcia = "";
        String codprs = "";
        String tpoprs = "";
        String aftpsw = "";
        String befpsw = "";
        String sqlCmd = "";
        String logout = "";
        String nomprs = "";
        String nomtpo = "";
        String nriprs = "";
        String endSes = ""; // para indicar que se acabo la sesion
        Long segInSession;

        // String dtabse = "";
        List list = new LinkedList();
        try {
            // Obtener los parametros request
            logout = request.getParameter("logout");
            endSes = request.getParameter("endSes");

            if (logout.equals("In")) {
                session.removeAttribute("NomTpo");
                session.removeAttribute("NomPrs");
                session.removeAttribute("keyssn");
                session.removeAttribute(model.getCodPrs());
                model.setCodCia("");
                model.setTpoPrs("");
                model.setCodPrs("");

                Map<String, String[]> request_ = request.getParameterMap();
                Iterator it = request_.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry object = (Map.Entry) it.next();
                    String key = (String) object.getKey();
//                    String value = (String) ((String[]) object.getValue())[0];

                    if (key.startsWith("prs") && key.endsWith("cod")) {
                        codprs = Util.getStrRequest("prs" + md5("codprs").toLowerCase() + "cod", request);
                        codprs = mvc.model.ClCompanyInformation.out32(codprs);
                    }

                    if (key.startsWith("prs") && key.endsWith("psw")) {
                        befpsw = Util.getStrRequest("prs" + md5("pswprs").toLowerCase() + "psw", request);
                        befpsw = mvc.model.ClCompanyInformation.out32(befpsw);
                    }

                    if (key.startsWith("cia") && key.endsWith("cod")) {
                        codcia = Util.getStrRequest("cia" + md5("codcia").toLowerCase() + "cod", request);
                        codcia = mvc.model.ClCompanyInformation.out32(codcia);
                    }

                }
                // Desabilitar desconexion automatica
                String initCia = session.
                        getServletContext().getInitParameter("codCia");

                codcia = codcia != null
                        ? codcia : initCia;

//                codprs = request.getParameter("codprs").trim();
                tpoprs = request.getParameter("tpoprs");
//                befpsw = request.getParameter("pswprs").trim();

                long contextSession = 0;
                String ipRequest = "";

                if (application.getAttribute("session_" + codcia + "_" + codprs) != null) {
                    contextSession = (Long) application.getAttribute("session_" + codcia + "_" + codprs);
                    ipRequest = (String) application.getAttribute("ip_" + codcia + "_" + codprs);
                }

                Object[] result = model.validLogin(codcia, codprs, befpsw);
                boolean existe = ((String) result[0]).
                        equals("true") ? true : false;
                tpoprs = (String) result[1];

                if (existe) {
//                    this.newConnection(tpoprs, codprs, befpsw);

                    sqlCmd = "select * "
                            + " from smaprs "
                            + " join smausr"
                            + " on smausr.nroprs = smaprs.nroprs"
                            + " where smaprs.codcia = '" + codcia + "' "
                            + " and smausr.codusr = '" + codprs + "'";
                    model.listGenericHash(sqlCmd);

                    list = model.getList();

                    if (!list.isEmpty() /*&& uvalid.equals("t")*/) {
                        model.setCodCia(codcia);
                        model.setTpoPrs(tpoprs);
                        model.setCodPrs(codprs);

                        nriprs = ((Hashtable) list.
                                get(0)).get("NRIPRS").toString();
                        String emlprs = ((Hashtable) list.
                                get(0)).get("EMLPRS").toString();
                        String dirprs = ((Hashtable) list.
                                get(0)).get("DIRPRS").toString();

                        segInSession = System.currentTimeMillis() / 1000;
                        long sesgundos_init = session.getMaxInactiveInterval() + segInSession;
                        boolean isActive = false;

                        if (application.getAttribute("session_" + codcia + "_" + codprs) != null) {

                            if (contextSession < sesgundos_init
                                    && (!ipRequest.equals(request.getRemoteAddr()))
                                    && (!codprs.equals("73099361"))) {
                                isActive = true;
                            }
                        }

                        if (isActive) {
                            request.setAttribute("Mensaje",
                                    "No se puede acceder a la aplicación, Este usuario ya ha iniciado sesión !");
                            nextPage = "/vista/Confirmacion.jsp";

                        } else if (codprs.equals(befpsw) || nriprs.equals(befpsw)) {
                            request.setAttribute("Mensaje",
                                    "Por seguridad le recomendamos "
                                    + "cambiar su clave.<br>Es "
                                    + "necesario que su clave sea "
                                    + "diferente a su codigo o "
                                    + "numero de indentificacion!");

                            request.setAttribute("Send",
                                    "/vista/formChagePassword.jsp"
                                    + "?codprs=" + codprs
                                    + "&tpoprs=" + tpoprs
                                    + "&tpolgn=PSWOUT");
                            nextPage = "/vista/Confirmacion.jsp";
                        } else {

                            String nombres = ((Hashtable) list.
                                    get(0)).get("NOMPRS").toString();
                            String apellidos = ((Hashtable) list.
                                    get(0)).get("APEPRS").toString();
                            String nroprs = ((Hashtable) list.
                                    get(0)).get("NROPRS").toString();
                            String nrousr = ((Hashtable) list.
                                    get(0)).get("NROUSR").toString();

                            nomprs = nombres + " " + apellidos;

                            session.setAttribute("NomPrs", nomprs);
                            session.setAttribute("keyssn", "1");
                            session.setAttribute(codprs, aftpsw);
                            model.setCodCia(codcia);
                            model.setNomprs(nombres);
                            model.setApeprs(apellidos);
                            model.setTpoPrs(tpoprs);
                            model.setCodPrs(codprs);
                            model.setNroPrs(nroprs);
                            model.setNroUsr(nrousr);

                            sqlCmd = "sma_system_manager.system_init('" + nrousr + "','" + session.getId() + "')";
                            String exito = model.callFunctionOrProcedure(sqlCmd);
                            if (exito.indexOf("Exito/") != -1) {
                                model.setSessionId(exito.replace("Exito/", ""));
                                nextPage = "/vista/Main.jsp";
                                application.setAttribute("session_" + codcia + "_" + codprs, segInSession);
                                application.setAttribute("ip_" + codcia + "_" + codprs, request.getRemoteAddr());
                            }

                        }
                    } else {
                        request.setAttribute("Mensaje",
                                "Código o Password incorrecto para ingresar al sistema.");
                        request.setAttribute("tpomsg", "1");
                        request.setAttribute("bton", "Cerrar");
                        nextPage = "/vista/Confirmacion.jsp";
                    }
                    // Activar desconexion automatica
                } else {
                    request.setAttribute("Mensaje",
                            "Código o Password incorrecto para ingresar al sistema.");
                    request.setAttribute("tpomsg", "1");
                    request.setAttribute("bton", "Cerrar");
                    nextPage = "/vista/Confirmacion.jsp";
                }
            } else {
                session.removeAttribute("NomTpo");
                session.removeAttribute("NomPrs");
                session.removeAttribute("keyssn");
                session.removeAttribute(model.getCodPrs());

                if (model.getSessionId() != null) {

                    sqlCmd = "sma_system_manager.system_end('" + model.getSessionId() + "')";
                    String exito = model.callFunctionOrProcedure(sqlCmd);
                    if (exito.indexOf("Exito/") != -1) {
                        nextPage = "/vista/init.jsp";
                        request.setAttribute("endSes", endSes);
                    }
                }
                nextPage = "/vista/init.jsp";
                application.removeAttribute("session_" + model.getCodCia() + "_" + model.getCodPrs());
                application.removeAttribute("ip_" + model.getCodCia() + "_" + model.getCodPrs());

                model.setCodCia(null);
                model.setNroPrs(null);
                model.setNroUsr(null);
                model.setTpoPrs(null);
                session.invalidate();
                model.setCompany(null);

            }

            // Reenviar a la pagina JSP de confirmacion
            this.rd = application.getRequestDispatcher(nextPage);
            if (this.rd == null) {
                throw new ServletException("No se pudo encontrar " + nextPage);
            }
            this.rd.forward(request, response);

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException("Error Clase ["
                    + this.getClass().getName() + "] "
                    + "Metodo [run()] " + e.getMessage());
        }
    }

    private String md5(String text) {
        try {
            return (String) model.getData("select sma_system_crypto.md5('" + text + "') md5 from dual", null);
        } catch (SQLException ex) {
            Util.logError(ex);
            return null;
        }

    }

} // End Class
