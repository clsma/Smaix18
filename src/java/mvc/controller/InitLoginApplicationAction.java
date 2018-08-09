/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import mvc.model.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.exception.LogbookException;
import static mvc.model.ClCompanyInformation.out32;
import mvc.model.ModelSma;
import mvc.model.Util;
import mvc.model.WebModel;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author clsma
 */
public class InitLoginApplicationAction extends Action {

    private String sqlCmd;
    private String nextPage;

    @Override
    public void run() throws ServletException, IOException {

        try {

            String event = Util.getStrRequest("event", request);

            if (event.equals("IN")) {
                logIn();
                return;
            } else if (event.equals("OUT")) {
                logOut();
            } else if (event.equals("KILL")) {
                killSession();
            } else if (event.equals("CHN")) {
                change();
                return;
            } else if (event.equals("LSTSESSION")) {
                lstSession();
                return;
            }

            this.rd = application.getRequestDispatcher(nextPage);
            if (this.rd == null) {
                throw new ServletException("No se pudo encontrar " + nextPage);
            }
            this.rd.forward(request, response);

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException(e);
        }
    }

    public static String value(String field, JSONObject objeto) {

        String _fuield = field.substring(3) + md5(field) + field.substring(0, 3);
        _fuield = md5(_fuield);
        _fuield = Util.validStr(objeto, _fuield);
        _fuield = out32(_fuield);

        return _fuield;
    }

    private void logIn() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject formulario = Util.getJsonRequest("request", request);
        boolean isMobile = Boolean.valueOf(formulario.get("navigator").toString());
        ArrayList<String> users = null;
        String type = "ERROR";

        try {

            String codcia = "cia" + md5("codcia") + "cod";
            codcia = md5(codcia);
            codcia = Util.validStr(formulario, codcia);
            codcia = out32(codcia);

            String codprs = "rTxt" + md5("userTxt") + "use";
            codprs = md5(codprs);
            codprs = Util.validStr(formulario, codprs);
            codprs = out32(codprs);

            String pswprs = "sTxt" + md5("passTxt") + "pas";
            pswprs = md5(pswprs);
            pswprs = Util.validStr(formulario, pswprs);
            pswprs = out32(pswprs);

            String tpousr = "Txt" + md5("tpoTxt") + "tpo";
            tpousr = md5(tpousr);
            tpousr = Util.validStr(formulario, tpousr);
            tpousr = out32(tpousr);

            String nrousr_ = "Txt" + md5("usrTxt") + "usr";
            nrousr_ = md5(nrousr_);
            nrousr_ = Util.validStr(formulario, nrousr_);
            nrousr_ = out32(nrousr_);

            String clientKey = Util.validStr(formulario, "clientKey");
            clientKey = session.getId() + clientKey + session.getCreationTime();

            if (!clientKey.equals(model.getCompany().getAccesRuntimeKey())) {
                throw new LogbookException("&Intento de acceso ilegal al sistema.&");
            }

            boolean keep = Integer.parseInt(Util.validStr(formulario, "keep")) > 0;
            keep = true;
            long contextSession = 0;
            String ipRequest = "";

            Map datos = model.callStoredProcedure("sma_system_login.user_login("
                    + "                                  p_codcia => '" + codcia + "',\n"
                    + "                                  p_codusr => '" + codprs + "',\n"
                    + "                                  p_pswusr => '" + pswprs + "',\n"
                    + "                                  p_outtype => ?,\n"
                    + "                                  p_outmessage => ?,\n"
                    + "                                  p_rolcount => ?,"
                    + "                                  p_outnrousr => ?,"
                    + "                                  p_sessiontime => ?)", 3, null);

            Map smausr = null;
//            System.out.println(datos);
            if (!datos.isEmpty() && (datos.get("exito") != null && datos.get("exito").equals("ERROR"))) {
                throw new LogbookException(datos.get("msg").toString());
            }

            if (!datos.get("1").equals("OK")) {
                type = datos.get("1").toString();
                throw new LogbookException(datos.get("2").toString());
            }

            int sesstime = datos.get("5") != null ? Integer.parseInt(datos.get("5").toString()) : 10;
            String nrousr = datos.get("4").toString();
            nrousr = nrousr_.trim().isEmpty() ? nrousr : nrousr_;

            sqlCmd = "select smausr.codusr , "
                    + "      smaprs.nomprs , "
                    + "      smaprs.apeprs ,"
                    + "      smaprs.nriprs ,"
                    + "      smaprs.nroprs ,"
                    + "      smaprs.codprs ,"
                    + "      smasgu.tposgu"
                    + "  from smaprs"
                    + "  join smausr"
                    + "    on smausr.nroprs = smaprs.nroprs "
                    + "  join smasgu"
                    + "    on smasgu.nrosgu = smausr.nrosgu "
                    + "  and smaprs.codcia = '" + codcia + "'"
                    + " where smausr.nrousr = '" + nrousr + "'";
            model.list(sqlCmd, null);

            if (model.getList().isEmpty()) {
                throw new LogbookException("&No se encontraron datos del usuario : [ " + codprs + " ]&");
            }

            smausr = (HashMap) model.getList().get(0);
            String codusr = Util.validStr(smausr, "CODUSR");
            String nroprs = Util.validStr(smausr, "NROPRS");

            String count = Util.validStr(datos, "3").split(",").length + "";
            count = count.trim().isEmpty() ? "0" : count;
            String roles = Util.validStr(datos, "3");

            if (tpousr.isEmpty() && nrousr_.isEmpty() && Integer.valueOf(count) > 1 && roles.trim().contains("BAS")) {
                showTable(nrousr, nroprs, count);
                return;
            }

            Long segInSession = System.currentTimeMillis();
            boolean isActive = false;

            if (application.getAttribute("session_" + codcia + "_" + codusr) != null) {

                contextSession = (Long) application.getAttribute("session_" + codcia + "_" + codusr);
                ipRequest = (String) application.getAttribute("ip_" + codcia + "_" + codusr);
                boolean validThisUserTime = ((segInSession - contextSession) / 1000) < session.getMaxInactiveInterval();
                boolean validThisUserIp = ipRequest.equals(request.getRemoteAddr());
                boolean validThisUserCod = codusr.equals("73099361");

                if (validThisUserTime && !validThisUserIp && !validThisUserCod) {
                    isActive = true;
                }

            } else if (keep) {
                application.setAttribute("session_" + codcia + "_" + codusr, segInSession);
                application.setAttribute("ip_" + codcia + "_" + codusr, request.getRemoteAddr());
            }

            if (isActive) {
                throw new LogbookException("(LOGBOOK)El usuario [ " + codprs + " ] ya ha iniciado sesión.(LOGBOOK)");
            }

            tpousr = tpousr.isEmpty() ? Util.validStr(smausr, "TPOSGU") : tpousr;

            model.setCodCia(codcia);
            model.setCodPrs(codusr);
            model.setNroPrs(Util.validStr(smausr, "NROPRS"));
            model.setNroUsr(nrousr);
            model.setTpoPrs(tpousr);
            model.setNomprs(Util.validStr(smausr, "NOMPRS"));
            model.setApeprs(Util.validStr(smausr, "APEPRS"));
            model.setLogged(true);
            session.setMaxInactiveInterval(sesstime * 60);

            session.setAttribute("keyssn", "IN");
            session.setAttribute(codusr, session.getId());

            String sessionStartedUser = (String) application.getAttribute("sessxu_" + codusr);
            String idesxiStartedUser = (String) application.getAttribute("idesxu_" + codusr);
            String sql_ = "sma_system_manager.state_session('" + idesxiStartedUser + "')";
            sql_ = model.callFunctionOrProcedure(sql_);
            if (sessionStartedUser == null || sql_.equals("OUT")) {

                sqlCmd = "sma_system_login.system_init('" + model.getNroUsr() + "','" + session.getId() + "' , '" + request.getRemoteAddr() + "' , ? , ? , ?)";
                Map exito = model.callStoredProcedure(sqlCmd, 3, null);
                // exito - 1 : State : 'Exito' || 'Error';
                // exito - 2 : 'Idesxu';
                // exito - 3 : 'Sessxu' : session.getId();

                if (!exito.isEmpty() && exito.get("1").toString().toLowerCase().equals("exito")) {

                    model.setSessionId(exito.get("3").toString());
                    model.setSessionSxu(exito.get("2").toString());

                    if (keep) {
                        application.setAttribute("sessxu_" + codusr, exito.get("3").toString());
                        application.setAttribute("idesxu_" + codusr, exito.get("2").toString());
                    }

                } else {
                    throw new LogbookException("(LOGBOOK)Resultado inesperado al iniciar la sesión(LOGBOOK)");
                }

            } else {
                model.setSessionId((String) sessionStartedUser);
                model.setSessionSxu((String) idesxiStartedUser);
                sql_ = "sma_system_login.system_init_ip('" + idesxiStartedUser + "' , '" + request.getRemoteAddr() + "')";
                model.callFunctionOrProcedure(sql_);
            }

            sqlCmd = "select nromnu from smamxu where nrousr = '" + nrousr + "'";
            sqlCmd = (String) model.getData(sqlCmd, null);
            if (sqlCmd.equals("001") || sqlCmd.trim().isEmpty()) {
                sqlCmd = "001";
                nextPage = "/vista/sistemas/mmenu/menu/mainMenu.jsp";
            } else {
                nextPage = "/vista/mainMenu.jsp";
            }
            session.setAttribute("menu_" + nrousr, sqlCmd);

            if (keep) {
                application.setAttribute(request.getRemoteAddr(), session.getId());
            }

            Util.setModel((ModelSma) model);
            incrementSessions(application);
            json.put("exito", "OK");
            json.put("type", "OK");
            json.put("goto", nextPage);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("type", type);
            json.put("msg", LogbookException.resultFixed(e.getMessage(), "~Fixed~"));
        }

        response.getWriter()
                .write(JSONValue.toJSONString(json));
    }

    private void incrementSessions(ServletContext sc) {
        int last = Integer.valueOf(sc.getAttribute("numberOfSession").toString()) + 1;
        sc.setAttribute("numberOfSession", last);
    }

    public static void decrementSessions(ServletContext sc) {
        int last = Integer.valueOf(sc.getAttribute("numberOfSession").toString()) - 1;
        sc.setAttribute("numberOfSession", last < 0 ? 0 : last);
    }

    public static int getNumberOfSession(ServletContext sc) {
        return Integer.valueOf(sc.getAttribute("numberOfSession").toString());
    }

    public static synchronized final String isRegistered(HttpServletRequest request) {

        return "";

    }

    public static String md5(String text) {
        try {
            return ((String) new WebModel().getData("select sma_system_crypto.md5('" + text + "') md5 from dual", null)).toLowerCase();
        } catch (SQLException ex) {
            Util.logError(ex);
            return null;
        }

    }

    public static boolean inArray(ArrayList arr, String data) {

        if (arr != null) {

            for (Object a : arr) {
                if (data.equals(a.toString())) {
                    return true;
                }

            }

        }

        return false;
    }

    private void logOut() throws Exception {
        nextPage = "/vista/sistemas/login/login.jsp";
        kill(model, application, session, "");
        session.invalidate();  

    }

    private void killSession() throws Exception {

        String codprs = Util.getStrRequest("codprs", request);
        session.removeAttribute(codprs);

        String ajax = request.getHeader("Ajax-Request");
        if (ajax == null) {
            throw new ServletException("Recurso no encontrado, Comuniquese con el administrador del sistema.");
        }
        kill(model, application, session, codprs);
        response.getWriter().write("Exito");
        return;

    }

    private void showTable(String nrousr, String nroprs, String count) throws Exception {
        JSONObject json = new JSONObject();

        try {
            jQgridTab tab = new jQgridTab();
            if (Integer.valueOf(count) > 1) {

                sqlCmd = "select smausr.nrousr , \n"
                        + "       case when tposgu not in ( 'BAS' , 'EGR' ) then 'FUNCIONARIOS'\n"
                        + "            else smasgu.nomsgu  end nomsgu,\n"
                        + "        case when tposgu not in ( 'BAS' , 'EGR' ) then 'PRS'\n"
                        + "            else smasgu.tposgu  end tposgu,\n"
                        + "       nropgm ,\n"
                        + "       nompgm ,\n"
                        + "       codbas\n"
                        + "  from smausr\n"
                        + "  join smaprs\n"
                        + "    on smaprs.nroprs = smausr.nroprs\n"
                        + "  left join smabas\n"
                        + "    on smabas.codbas = smausr.codusr\n"
                        + "  left join smapgm\n"
                        + "    on smapgm.idepgm = smabas.idepgm\n"
                        + "  join table( sma_system_roles.user_roles( p_codcia => smaprs.codcia \n"
                        + "                                         , p_codusr => smausr.codusr ) ) smasgu \n"
                        + "    on smasgu.nrousr = smausr.nrousr\n"
                        + "where smaprs.nroprs = '" + nroprs + "'\n"
                        + "  and stdusr = 'Activo..'"
                        + "  group by smausr.nrousr , \n"
                        + "           case when tposgu not in ( 'BAS' , 'EGR' ) then 'FUNCIONARIOS'\n"
                        + "                else smasgu.nomsgu  end ,\n"
                        + "            case when tposgu not in ( 'BAS' , 'EGR' ) then 'PRS'\n"
                        + "                else smasgu.tposgu  end ,\n"
                        + "            nropgm ,\n"
                        + "           nompgm ,\n"
                        + "           codbas"
                        + "  order by nomsgu asc";
                model.list(sqlCmd, null);

                tab.setColumns(new String[]{"TPOSGU", "NOMSGU", "NROPGM", "NOMPGM", "CODBAS", "NROUSR"});
                tab.setTitles(new String[]{"Tipo Usuario", "Descripción", "Nro. Prog", "Programa", "Código", "NROUSR"});
                tab.setWidths(new int[]{100, 200, 50, 250, 100, 0});
                tab.setKeys(new int[]{5});
                tab.setHiddens(new int[]{5});
                tab.setSelector("jqRoles");
                tab.setDataList(model.getList());

                LinkedHashMap map = new LinkedHashMap();
                map.put("align", "center");
                map.put("height", "auto");
                map.put("onSelectRow", "initLogin");
                tab.setOptions(map);

                json.put("exito", "OK");
                json.put("show", "table");
                json.put("html", tab.getHtml());

            }

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "OK");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));
        return;
    }

    private void change() throws IOException {
        String nriprs = Util.getStrRequest("b", request);
        String idepgm = Util.getStrRequest("c", request);
        String tpoprs = Util.getStrRequest("d", request);
        JSONObject json = new JSONObject();

        try {

            sqlCmd = "sma_system_roles.restore_password( p_tpoprs => '" + tpoprs + "' , "
                    + "                                  p_nriprs => '" + nriprs + "' ,"
                    + "                                  p_idepgm => '" + idepgm + "' ,"
                    + "                                  o_exito  => ?  ,"
                    + "                                  o_result => ?  )";

            Map result = model.callStoredProcedure(sqlCmd, 4, null);
            if (!result.get("1").equals("OK")) {
                json.put("exito", "ERROR");
                json.put("msg", result.get("2").toString().replaceAll("&", ""));
                response.getWriter().write(JSONValue.toJSONString(json));
                return;
            }

            json.put("exito", "OK");
            json.put("msg", result.get("2").toString().replaceAll("&", ""));

        } catch (Exception e) {
            Util.logError(e);
            json.put("msg", "Su contraseña no pudo ser cambiada, Comuniquese con el administrador del sistema.");
            json.put("exito", "ERROR");
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    private void lstSession() throws Exception {
        JSONObject json = new JSONObject();

        try {

            sqlCmd = "select smausr.nrousr ,\n"
                    + "       case when tposgu = 'BAS' then 'Grupo Estudiante'\n"
                    + "            when tposgu = 'EGR' then 'Grupo Egresados'\n"
                    + "            else 'Grupo Funcionarios o Personas' end nomsgu,\n"
                    + "       case when tposgu in ( 'BAS' ,'EGR' ) then smapgm.nropgm || ' ' || smapgm.nompgm\n"
                    + "            else '  -  ' end nompgm,     \n"
                    + "       smaprs.nriprs , \n"
                    + "       replace( replace( smaprs.apeprs || ' ' || smaprs.nomprs , '\r' , '' ) , '\n', '' )nombre ,\n"
                    + "       codusr ,\n"
                    + "       max( nipsxi )nipsxi ,\n"
                    + "       to_char( max(  bgnsxi ) , 'DD/MM/YYYY HH.MI.SS AM') fchsxi\n"
                    + "  from smaprs\n"
                    + "  join smausr\n"
                    + "    on smausr.nroprs = smaprs.nroprs\n"
                    + "  left join smabas\n"
                    + "    on smabas.codbas = smausr.codusr\n"
                    + "  left join smaegr\n"
                    + "    on smaegr.codegr = smausr.codusr\n"
                    + "  left join smapgm\n"
                    + "    on smapgm.idepgm = smabas.idepgm\n"
                    + "    or smapgm.idepgm = smaegr.idepgm\n"
                    + "  join smasgu\n"
                    + "    on smasgu.nrosgu = smausr.nrosgu\n"
                    + "  join smasxu\n"
                    + "    on smasxu.nrousr = smausr.nrousr\n"
                    + "  join smasxi\n"
                    + "    on smasxi.idesxu = smasxu.idesxu\n"
                    + " where stdsxu = 'Iniciado..'\n"
                    + "   and smausr.codusr != '73099361'\n"
                    + " group by smausr.nrousr ,\n"
                    + "       case when tposgu = 'BAS' then 'Grupo Estudiante'\n"
                    + "            when tposgu = 'EGR' then 'Grupo Egresados'\n"
                    + "            else 'Grupo Funcionarios o Personas' end ,\n"
                    + "       case when tposgu in ( 'BAS' ,'EGR' ) then smapgm.nropgm || ' ' || smapgm.nompgm\n"
                    + "            else '  -  ' end ,     \n"
                    + "       smaprs.nriprs , \n"
                    + "       smaprs.apeprs || ' ' || smaprs.nomprs  ,\n"
                    + "       codusr \n"
                    + "       order by max(  bgnsxi )  desc ,nombre asc";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();

            tab.setColumns(new String[]{"CODUSR", "NRIPRS", "NOMBRE", "NOMSGU", "NOMPGM", "FCHSXI", "NIPSXI", "KILL"});
            tab.setTitles(new String[]{"", "Identificación", "Usuario", "Rol/Grupo", "Programa", "Fecha", "Ip", "Cerrar"});
            tab.setWidths(new int[]{0, 100, 230, 120, 230, 150, 100, 60});
            tab.setKeys(new int[]{0});
            tab.setHiddens(new int[]{0});
            tab.setSelector("jqUsers");
            tab.setDataList(model.getList());
            tab.setPaginador("pagerUsers");
            tab.setFilterToolbar(true);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "300");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("7", "formKill");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    public static void kill(ModelSma model, ServletContext application, HttpSession sesion, String codprs) {
        if (!model.isLogged()) {
            return;
        }
        codprs = (codprs == null || codprs.trim().isEmpty()) ? model.getCodPrs() : codprs;
        sesion.removeAttribute(codprs);

        String sessionStartedUser = (String) application.getAttribute("sessxu_" + codprs);
        String idesxiStartedUser = (String) application.getAttribute("idesxu_" + codprs);

        try {

            String nrousr = (String) model.getData("select nrousr from smausr where codusr = '" + codprs + "' and stdusr = 'Activo..'", null);

            if (idesxiStartedUser != null && sessionStartedUser != null && !codprs.equals("73099361")) {

                String sqlCmd = "sma_system_login.system_end('" + nrousr + "')";
                String exito = model.callFunctionOrProcedure(sqlCmd);
                if (exito.indexOf("Exito/") != -1) {
                }
            }

        } catch (Exception e) {
            Util.logError(e);
        }

        application.removeAttribute("session_" + model.getCodCia() + "_" + codprs);
        application.removeAttribute("ip_" + model.getCodCia() + "_" + codprs);
        application.removeAttribute("sessxu_" + codprs);
        application.removeAttribute("idesxu_" + codprs);
        application.removeAttribute(codprs);
        application.removeAttribute(model.ipAddresRequest);

        return;
    }

}
