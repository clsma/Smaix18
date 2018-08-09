/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import mvc.controller.Mailer;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author clsma
 */
public class ClCompanyInformation implements HttpSessionBindingListener, Serializable {

    public Hashtable hasCia;
    public String STYLE;
    public String NOMBRE_CIA;
    public String NIT_CIA;
    public String LEMA_CIA;
    public String ATENCION_CIA;
    public String TELEFONO_CIA;
    public String EMAIL_CIA;
    public String WEB_CIA;
    public String BASE;
    public String CONTROL;
    public String REPORT_PROVIDER;
    public String IP;
    private ArrayList<String> systemPages;
    private WebModel model;
    private Map dontCare;
    private ServletContext application;
    private ArrayList<String> menu;
    private String accesRuntimeKey;

    public String getREPORT_PROVIDER() {
        return REPORT_PROVIDER;
    }

    public void setREPORT_PROVIDER(String REPORT_PROVIDER) {
        this.REPORT_PROVIDER = REPORT_PROVIDER;
    }

    public String getAccesRuntimeKey() {
        return accesRuntimeKey;
    }

    public void setAccesRuntimeKey(String accesRuntimeKey) {
        this.accesRuntimeKey = accesRuntimeKey;
    }

    public ClCompanyInformation() {
    }

    public Hashtable getHasCia() {
        return hasCia;
    }

    public void setHasCia(Hashtable hasCia) {
        this.hasCia = hasCia;
    }

    public String getSTYLE() {
        return STYLE;
    }

    public void setSTYLE(String STYLE) {
        this.STYLE = STYLE;
    }

    public String getNOMBRE_CIA() {
        return NOMBRE_CIA.trim();
    }

    public void setNOMBRE_CIA(String NOMBRE_CIA) {
        this.NOMBRE_CIA = NOMBRE_CIA;
    }

    public String getLEMA_CIA() {
        return LEMA_CIA;
    }

    public void setLEMA_CIA(String LEMA_CIA) {
        this.LEMA_CIA = LEMA_CIA;
    }

    public String getATENCION_CIA() {
        return ATENCION_CIA;
    }

    public void setATENCION_CIA(String ATENCION_CIA) {
        this.ATENCION_CIA = ATENCION_CIA;
    }

    public String getTELEFONO_CIA() {
        return TELEFONO_CIA;
    }

    public void setTELEFONO_CIA(String TELEFONO_CIA) {
        this.TELEFONO_CIA = TELEFONO_CIA;
    }

    public String getEMAIL_CIA() {
        return EMAIL_CIA;
    }

    public void setEMAIL_CIA(String EMAIL_CIA) {
        this.EMAIL_CIA = EMAIL_CIA;
    }

    public String getBASE() {
        return BASE;
    }

    public void setBASE(String BASE) {
        this.BASE = BASE;
    }

    public String getCONTROL() {
        return CONTROL;
    }

    public void setCONTROL(String CONTROL) {
        this.CONTROL = CONTROL;
    }

    public String getNIT_CIA() {
        return NIT_CIA;
    }

    public void setNIT_CIA(String NIT_CIA) {
        this.NIT_CIA = NIT_CIA;
    }

    public Map getDontCare() {
        return dontCare;
    }

    public void setDontCare(Map dontCare) {
        this.dontCare = dontCare;
    }

    public ServletContext getApplication() {
        return application;
    }

    public void setApplication(ServletContext application) {
        this.application = application;
    }

    public ArrayList<String> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<String> menu) {
        this.menu = menu;
    }
 
    public String getWEB_CIA() {
        return WEB_CIA;
    }

    public void setWEB_CIA(String WEB_CIA) {
        this.WEB_CIA = WEB_CIA;
    }

    public void ClCompanyInformation() {

        if (this.model == null) {
            this.model = new WebModel();
        }
        try {

            menu = new ArrayList<String>();
            initSystempages();

            Configuration confi = new Configuration().configure();

            String url = new String(confi.getProperty("hibernate.connection.url"));
            String user = new String(confi.getProperty("hibernate.connection.username"));
            String pass = new String(confi.getProperty("hibernate.connection.password"));

            Map data = new HashMap();
            data.put("user", in32(user));
            data.put("pass", in32(pass));
            data.put("url", in32(url));
            setDontCare(data);

            //model.listGenericHash("select * from smacia where codcia = '" + model.getCodCia() + "'");
            model.listGenericHash("select * from smacia ");
            setHasCia((Hashtable) this.model.getList().get(0));
            setSTYLE(Util.validStr(hasCia, "THMCIA"));
            setNOMBRE_CIA(Util.validStr(hasCia, "NOMCIA"));
            setLEMA_CIA(Util.validStr(hasCia, "LEMCIA"));
            setATENCION_CIA(Util.validStr(hasCia, "CTGCIA"));
            setTELEFONO_CIA(Util.validStr(hasCia, "TELCIA"));
            setEMAIL_CIA(Util.validStr(hasCia, "EMLCIA"));
            setNIT_CIA(Util.validStr(hasCia, "NITCIA"));
            setWEB_CIA(Util.validStr(hasCia, "WEBCIA"));
            setREPORT_PROVIDER(Util.validStr(hasCia, "RPTCIA"));

            model.list("select * from smaemf", null);
            if (!model.getList().isEmpty()) {
                Map datos = (HashMap) model.getList().get(0);
                Mailer.setSender(Util.validStr(datos, "EMLEMF"));
                Mailer.setServer(Util.validStr(datos, "SVREMF"));
                Mailer.setConfig((HashMap) model.getList().get(0));
            }

//            String tempKey = this.hasCia.get("CODCIA").toString();
//            tempKey += new Date().getTime() + "";
        } catch (Exception e) {
            Util.logError(e);
        }
    }

    public void initContext() {

        String appPath = application.getRealPath("/");
//        System.out.println("Path : " + appPath);
        String sep = File.separator;
        String local = appPath + "utility" + sep;
        application.setAttribute("application", appPath);
        application.setAttribute("localPath", local);
        application.setAttribute("uploadPath", local + "upload" + sep);
        application.setAttribute("photoPath", local + "photos" + sep);
        application.setAttribute("reportPath", local + "reportes" + sep);
        application.setAttribute("pqrsPath", local + "pqrs" + sep);
        application.setAttribute("pqrsPathLoader", application.getContextPath() + sep + "utility" + sep + "pqrs" + sep);
        application.setAttribute("codCia", Util.validStr(hasCia, "CODCIA"));
        application.setAttribute("projectPath", application.getContextPath() + sep);
    }

    public String hasPassport(String idemnx, WebModel model)
            throws SQLException {

        String sqlCmd = " select sma_system_login.user_init_option('"
                + model.getCodCia() + "','" + model.getCodPrs() + "','" + idemnx + "') op from dual";
        String Access = "FALSE";
        try {
            Access = (String) model.getData(sqlCmd, null);
        } catch (Exception e) {
            Util.logError(e);
            Access = "FALSE";
        }

        return Access;

    }

    public String getKeyMnu(String prmmnx, String params, WebModel model)
            throws SQLException, UnsupportedEncodingException {

        String sqlCmd = "select idemnx \n"
                + "        FROM smamnx \n"
                + "        where prcmnx like '%" + URLDecoder.decode(prmmnx, "UTF-8") + "%'";
        if (params != null) {
            sqlCmd += " and pmtmnx like '%" + URLDecoder.decode(params, "UTF-8") + "%'";
        }
        sqlCmd += " and rownum<=1";

        try {

            String idemnx = (String) model.getData(sqlCmd, null);
            return (idemnx).equals("") ? "OTHER" : idemnx;
        } catch (Exception e) {
            Util.logError(e);

            return null;

        }
    }

    public String verifyAccess(String idemnx, String path, String params, WebModel model)
            throws SQLException, UnsupportedEncodingException {

        this.model = model;

        if (model.getCompany() != null && model.getCodPrs() != null) {

            if (path != null) {
                if (!path.equals("")) {

                    // Verifico que no sea una página del sistema.. o ya agregada en la sesión..
                    if (SystemPages(path, params)) {
                        return "TRUE";
                    }

                    //Verifico el id del menu
                    if (idemnx.equals("")) {
                        idemnx = getKeyMnu(path, params, model);
                    }

                    // Si no es página del sistema y es página incluida o pagina cargada por load o cualquier otro método
                    if (!SystemPages(path, params) && idemnx.equals("OTHER")) {
                        includeSystemPage(path, params);
                        return "TRUE";

                        //Si es una página del menú , verifico que tengo acceso a esta página
                    } else if ((idemnx != null && !idemnx.equals("")) && !idemnx.equals("OTHER")) {
                        //Si No tengo acceso
                        if (hasPassport(idemnx, model).equals("FALSE") && model.isLogged()) {
                            return "FALSE";

                            //Si tengo acceso a esta página, la agrego a la cola de páginas con el fin de no consultarla nuevamente,
                            //Esto solo ocurrira en la sesion activa, cada vez que se inicie sesion, se borraran las páginas que no son del sistema
                        } else {
                            includeSystemPage(path, params);
                        }
                    } else {
                        return "FALSE";
                    }

                }
            }

        }
        return "TRUE";

    }

    private void initSystempages() throws SQLException {

        systemPages = new ArrayList<String>();
        if (systemPages.size() <= 0) {

            String sqlCmd = "select rutpge from smapge where stdpge = 1";
            systemPages = (ArrayList) this.model.getData(sqlCmd, null);
        }

    }

    private void includeSystemPage(String path, String params) throws SQLException {
        if (systemPages == null) {
            initSystempages();
        } else if (params != null) {
            systemPages.add(path + "?" + params);
        } else {
            systemPages.add(path);
        }
    }

    private boolean SystemPages(String page, String params) {

        for (String a : systemPages) {

            if (params == null) {
                if (a.trim().contains(page.trim())) {
                    return true;
                }
            } else if (a.trim().contains(page.trim() + "?") || a.trim().contains(page.trim())) {
                return true;
            }
        }

        return false;

    }

    public static String in32(String value) {

        String aux = "";
        char a[] = value.toCharArray();

        for (int i = 0; i < a.length; i++) {

            aux += (char) (a[i] + (char) 32);
        }

        return aux;
    }

    public static String out32(String value) {

        String aux = "";
        char a[] = value.toCharArray();

        for (int i = 0; i < a.length; i++) {
            aux += (char) (a[i] + (char) -32);
        }

        return aux;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {

    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {

    }

    public void validDontCare(WebModel model) {

        try {

            model.listGenericHash("select * from smaprn where ideser = 'db_inf_1' and codprn = 'd_b'");
            if (model.getList().isEmpty()) {

                Map data = new HashMap();

                data.put("ideser", "db_inf_1");
                data.put("codprn", "d_b");
                data.put("sccprn", "Info");
                data.put("keyprn", "Info");
                data.put("valprn", "#user:" + getDontCare().get("user") + "#pass:" + getDontCare().get("pass") + "#url:" + getDontCare().get("url") + "#");

                model.saveLogBook(data, "smaprn", null);

            }
        } catch (Exception e) {
            Util.logError(e);
        }

    }
}
