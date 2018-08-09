/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package mvc.controller;

import mvc.model.Util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mvc.model.HibernateUtil;
import mvc.model.ModelSma;
import mvc.model.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Carlos pinto
 * @Descripcion Se encarga de administrar las variables de sesion
 * @Descripcion Y las seccionales de la pagina inicio
 */
public class AdmInitPageAction extends Action implements Serializable {

    private String sqlCmd;
    public static Map menuList = new HashMap();

    @Override
    public void run() throws ServletException, IOException {
        String even = Util.getStrRequest("state", request);
        try {
            if (even.equals("GETSEDE")) {
                getSedes();
                return;
            } else if (even.equals("GETMNU")) {
                getMenu();
                return;
            } else if (even.equals("KEYSESSION")) {
                sessionvalid();
                return;
            } else if (even.equals("GETACTIVITY")) {
                verActividad();
            } else if (even.equals("RAPRM")) {
                realReturn();
                return;
            } else if (even.equals("DELMNX")) {
                deleteMnu();
                return;
            } else if (even.equals("SVEMNU")) {
                saveMenu();
                return;
            } else if (even.equals("ALERT")) {
                getAlerts();
                return;
            }
        } catch (Exception e) {
            Util.logError(e);
        }

    }

    public AdmInitPageAction() {
        menuList.put("001", "/vista/mainMenu.jsp");
        menuList.put("002", "/vista/sistemas/mmenu/menu/mainMenu.jsp");
    }

    private void getSedes() throws ServletException, IOException {

        JSONObject json = new JSONObject();
        String ruta = Util.getStrRequest("ruta", request);
        String html = "";
        try {

            sqlCmd = "select nomscn,npqscn,webcia"
                    + " from smascn "
                    + "join smacia on smacia.codcia=smascn.codcia "
                    + "where smacia.codcia='" + model.getCodCia() + "'";
            model.listGenericHash(sqlCmd);
            Hashtable hasSede;
            int i = 0;
            Iterator it = model.getList().iterator();

            while (it.hasNext()) {
                hasSede = (Hashtable) it.next();
                html += "<li>\n"
                        + "<div class=\"image-style2 image-style2a\"><img src=\""
                        + ruta + "/vista/img/init/no_image_icon.gif\" width=\"65\" height=\"48\" alt=\"\" />"
                        + "<span></span></div>\n"
                        + "<h3><a href=\"" + Util.validStr(hasSede, "WEBCIA") + "\" style=\"color: #214243;font-size:12px\" target=\"blank\">"
                        + Util.validStr(hasSede, "NOMSCN") + "</a></h3>\n"
                        + "<p>" + Util.validStr(hasSede, "NPQSCN") + "</p> \n"
                        + "</li>\n"
                        + "</ul>\n";

            }

            json.put("Sedes", html);
        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException(e.getMessage());
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    private void getMenu() throws IOException {
        JSONObject json = new JSONObject();
        try {
            sqlCmd = "select distinct tipmnx as tipmnu,"
                    + "codmnx as codmnu,"
                    + "submnx as submnu,"
                    + "cscmnx as cscmnu,"
                    + "prmmnx as prmmnu,"
                    + "prcmnx as prcmnu,"
                    + "pmtmnx as pmtmnu,"
                    + "keymnx as keymnu,"
                    + "nommnx as nommnu,"
                    + "idemnx as idemnu "
                    + " FROM table ( sma_system_login.menus_gets( '" + model.getCodCia() + "', " + "'" + model.getCodPrs() + "', 'SMAX'  ) ) "
                    + "order by tipmnx,codmnx,submnx,cscmnx";

            model.listGenericHash(sqlCmd);
            json.put("exito", "OK");
            json.put("lista", model.getList());
        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.MSG_ERROR);
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    private void sessionvalid() throws IOException {
        JSONObject json = new JSONObject();
        String sess = (String) session.getAttribute(model.getCodPrs());
        try {

            json.put("session", (sess == null) ? "FALSE" : "TRUE");

            Map smaemf = model.copy("smaemf", "codcia = '" + model.getCodCia() + "'", null);

            Mailer.setSender(Util.validStr(smaemf, "EMLEMF"));
            Mailer.setServer(Util.validStr(smaemf, "SVREMF"));

        } catch (Exception e) {
            Util.logError(e);
            json.put("session", (sess == null) ? "FALSE" : "TRUE");
        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }

    private void verActividad() {

        Boolean performing = Boolean.valueOf(false);
        Boolean required = Boolean.valueOf(false);
        JSONObject retorno = new JSONObject();
        try {
            sqlCmd = "select *"
                    + " from table ( sma_system_login.user_get_activity( p_codcia => '" + model.getCodCia() + "' , p_nrousr => '" + model.getNroUsr() + "' ))";
            model.list(sqlCmd, null);
            List<HashMap> list = model.getList();
            if (!list.isEmpty()) {

                Map datos = null;
                String sqlHlq = null;
                for (Map activity : list) {

                    sqlCmd = "select nrohlu from smahlu where nrousr = '" + model.getNroUsr() + "' and nrohlq = '" + activity.get("NROHLQ") + "'";
                    model.list(sqlCmd, null);
                    if (model.getList().isEmpty()) {

                        datos = new HashMap();
                        datos.put("nrousr", model.getNroUsr());
                        datos.put("nrohlq", activity.get("NROHLQ"));
                        datos.put("lsthlu", new Date());
                        model.save(datos, "smahlu", null);
                    }

                    if (("1".equals(activity.get("REQHLQ").toString().toLowerCase())) && (!required.booleanValue())) {
                        required = Boolean.valueOf(true);
                    }
                    if ("INF".equals(activity.get("TPOHLQ").toString())) {
                        performing = Boolean.valueOf(true);
                        retorno.put("activity", activity);
                    } else {
                        performing = Boolean.valueOf(true);
                        try {

                            sqlHlq = Util.validStr(activity, "SQLHLQ");
                            sqlHlq = sqlHlq.replace("~codcia~", model.getCodCia());
                            sqlHlq = sqlHlq.replace("~nrousr~", "'" + model.getNroUsr() + "'");
                            sqlHlq = sqlHlq.replace("~nrohlq~", "'" + activity.get("NROHLQ").toString() + "'");

                            if (sqlHlq.trim().isEmpty()) {
                                sqlHlq = "true";
                            } else {
                                sqlHlq = model.callFunctionOrProcedure(sqlHlq).toLowerCase();
                            }

                            if (sqlHlq.equals("true")) {
                                retorno.put("activity", activity);
                                break;
                            }

                        } catch (Exception e) {
                            Util.logError(e);
                        }
                    }

                }
            }

            retorno.put("performing", performing);

            response.getWriter().write(retorno.toJSONString());

        } catch (Exception e) {
            Util.logError(e);
        }
    }

    private void realReturn() throws Exception {

        JSONObject json = new JSONObject();
        try {

            json.put("rausr", model.getCompany().getDontCare().get("user"));
            json.put("rapsw", model.getCompany().getDontCare().get("pass"));
            json.put("raurl", model.getCompany().getDontCare().get("url"));

        } catch (Exception e) {
            Util.logError(e);

        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }

    private void deleteMnu() throws IOException {

        ArrayList<String> menu = model.getCompany().getMenu();
        menu.addAll((ArrayList<String>) session.getAttribute("menu"));
        Session sesion = this.getNewSession();
        Transaction tran = HibernateUtil.getNewTransaction(sesion);
        try {

            tran.begin();

            for (Object a : menu) {

                model.deleteLogBook("smamng", "idemnx=~" + a + "~|", sesion);
                model.deleteLogBook("smamnu", "idemnx=~" + a + "~|", sesion);
                model.deleteLogBook("smamnx", "idemnx=~" + a + "~|", sesion);

            }

            tran.commit();
        } catch (Exception e) {
            Util.logError(e);
            tran.rollback();
            response.getWriter().write("ERROR");
        }
        response.getWriter().write("OK");

    }

    public static void endActivity(String tpoevn, String codhlq, ModelSma model) throws Exception {

        String sqlCmd = "select smahlq.nrohlq \n"
                + "     from smahlq\n"
                + "     join smahlu\n"
                + "       on smahlu.nrohlq = smahlq.nrohlq\n"
                + "    where nrousr = '" + model.getNroUsr() + "'\n"
                + "      and tposgu = '" + tpoevn + "'\n"
                + "      and codhlq = '" + codhlq + "'\n"
                + "      and trunc( sysdate ) \n"
                + "     between trunc( bgnhlq ) and trunc( endhlq )"
                + "      and stdhlu != 'Finalizada'";

        model.list(sqlCmd, null);
        if (model.getList().isEmpty()) {
            return;
        }

        Map smahlq = (HashMap) model.getList().get(0);

        Map datos = new HashMap();
//        datos.put("nrohlq", smahlq.get("NROHLQ"));
//        datos.put("nrousr", model.getNroUsr());
        datos.put("endhlu", new Date());
//        datos.put("lsthlu", new Date());
        datos.put("stdhlu", "Finalizada");

        model.updateLogBook(datos, "smahlu", "nrohlq=~" + smahlq.get("NROHLQ") + "~|nrousr=~" + model.getNroUsr() + "~|", null);

    }

    private void saveMenu() throws Exception {

        String menu = Util.getStrRequest("menu", request);
        JSONObject json = new JSONObject();
        try {

            sqlCmd = "select nromxu from smamxu where nrousr = '" + model.getNroUsr() + "'";
            String _menu = (String) model.getData(sqlCmd, null);

            Map datos = new HashMap();
            datos.put("nrousr", model.getNroUsr());
            datos.put("nromnu", menu);
            session.setAttribute("menu_" + model.getNroUsr(), menu);

            if (_menu.trim().isEmpty()) {

                model.saveLogBook(datos, "smamxu", null);
            } else {
                model.updateLogBook(datos, "smamxu", _menu, null);
            }

            json.put("exito", "OK");
            json.put("msg", model.MSG_UPDATE);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    public static void sendRedirectMenu(HttpServletResponse response, HttpSession sesion, String idemenu) {
        if (sesion.getAttribute("model") == null || !((ModelSma) sesion.getAttribute("model")).isLogged()) {
            return;
        }
        String sesMenu = sesion.getAttribute("menu_" + ((ModelSma) sesion.getAttribute("model")).getNroUsr()).toString();
        if (!sesMenu.equals(idemenu)) {
            try {
                response.sendRedirect(sesion.getServletContext().getContextPath() + File.separator + (String) menuList.get(sesMenu));
            } catch (IOException ex) {
                Util.logError(ex);
            }
        }
    }

    private void getAlerts() throws IOException {
        JSONObject json = new JSONObject();
        try {

            sqlCmd = " select smaalt.* ,\n"
                    + "        nomelm\n"
                    + "   from smaalt\n"
                    + "   join smaelm\n"
                    + "     on smaelm.nroelm = smaalt.tpoalt\n"
                    + "    and smaelm.tipelm = 'TYPE'\n"
                    + "    and smaelm.codelm = 'ALT'\n"
                    + "  where trunc( sysdate ) >= trunc( fhialt ) \n"
                    + "    and sysdate\n"
                    + "between to_date( trunc( sysdate ) || ' ' || hrialt , 'DD/MM/YY HH:MI AM')\n"
                    + "    and to_date( trunc( nvl( smaalt.fhvalt , sysdate ) ) || ' ' || nvl( trim( hrvalt ) , sma_general_date.hours() ), 'DD/MM/YY HH:MI AM')";

            model.list(sqlCmd, null);
            json.put("exito", "OK");
            json.put("lstData", model.getList());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }
}
