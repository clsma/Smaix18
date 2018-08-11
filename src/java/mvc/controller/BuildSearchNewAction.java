package mvc.controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;
import mvc.model.Util;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Carlos Pinto 20/03/2015 BuildSearchNewAction Clase que se encarga de
 * la construccion de la ventana de búsquedas
 *
 */
public class BuildSearchNewAction extends Action {

    private String sqlCmd;

    @Override
    public void run() throws ServletException, IOException {
        String state = Util.getStrRequest("state", request);

        if (state.equals("INIT")) {
            initSch();
        } else if (state.equals("VALUES")) {
            getValues();
        } else if (state.equals("FILTER")) {
            filter();
        }
    }

    private void initSch() throws IOException {
        String nrosch = Util.getStrRequest("nrosch", request);
        String params = Util.getStrRequest("params", request);
        String generalFilter = Util.getStrRequest("generalFilter", request);
        String rowsNum = Util.getStrRequest("rowsNum", request);
        int lenCam = 0;

        JSONObject json = new JSONObject();

        try {
            sqlCmd = "select * from smasch where nrosch='" + nrosch + "'";
            model.list(sqlCmd, null);
            Map hassch;

            if (!model.getList().isEmpty()) {
                hassch = (HashMap) model.getList().get(0);
                lenCam = Util.validStr(hassch, "CAMSCH").split(",").length;
                String tabsch = Util.validStr(hassch, "TABSCH");

                if (tabsch.contains("?")) {
                    json.put("type", "PRO");
                    String filt[] = Util.validStr(hassch, "FILSCH").split(",");
                    Object obj[] = new Object[model.charCount(tabsch, "?") - 1];

                    if (!Util.validStr(hassch, "FILSCH").trim().equals("")) {
                        String param_[] = params.split(",");

                        if (filt.length != param_.length) {
                            json = new JSONObject();
                            json.put("exito", "ERROR");
                            json.put("msg", "Los parámetros enviados al búscador son diferentes a los establecidos en la consulta");
                            response.getWriter().write(JSONValue.toJSONString(json));
                            return;
                        }
                        System.arraycopy(param_, 0, obj, 0, filt.length);
                    }

                    tabsch = tabsch.replace("pcodciasesion", "'" + model.getCodCia() + "'");
                    tabsch = tabsch.replace("pcodprssesion", "'" + model.getCodPrs() + "'");
                    sqlCmd = sqlCmd.replace("pnroprssesion", "'" + model.getNroPrs() + "'");
                    sqlCmd = sqlCmd.replace("pnrousrsesion", "'" + model.getNroUsr()+ "'");

                    List list = model.listarSP(tabsch, obj);
                    List listModel = new LinkedList();
                    List titles = new LinkedList();

                    if (!list.isEmpty()) {
                        String wids[] = Util.validStr(hassch, "WIDSCH").split(",");
                        String tits[] = Util.validStr(hassch, "TITSCH").split(",");
                        String camsch = Util.validStr(hassch, "CAMSCH");

                        int i = 0;
                        List<String> resultColumn = model.getColumnNames();
                        Map aux;
                        for (String cols : resultColumn) {
                            aux = new HashMap();
                            if (camsch.trim().toLowerCase().contains(cols.trim().toLowerCase())) {
                                aux.put("name", cols);
                                aux.put("index", cols);
                                if (wids.length == tits.length) {
                                    aux.put("width", wids[i]);
                                }
                                aux.put("sortable", true);
                                listModel.add(aux);
                                titles.add(tits[i]);
                                i++;
                            }
                        }

                        json.put("coltitles", titles);

                    } else {
                        json = new JSONObject();
                        json.put("exito", "NODATA");
                        json.put("msg", "La consulta no generó resultados");
                        json.put("empty", true);
//                        json.put("sql", sqlCmd);
                        response.getWriter().write(JSONValue.toJSONString(json));
                        return;
                    }

                    json.put("exito", "OK");
                    json.put("modelCols", listModel);
                    json.put("modelTitl", titles);
                    json.put("lista", list);
                    json.put("descripcion", Util.validStr(hassch, "DSPSCH"));

                } else {
                    json.put("type", "SQL");
                    sqlCmd = "select " + Util.validStr(hassch, "CAMSCH") + " from ";
                    sqlCmd += Util.validStr(hassch, "TABSCH");

                    String where = Util.validStr(hassch, "WHRSCH");
                    String filt[] = Util.validStr(hassch, "FILSCH").split(",");
                    if (!where.trim().equals("")) {

                        sqlCmd += (where.toLowerCase().trim().startsWith("join") ? " "
                                : where.toLowerCase().trim().startsWith("left") ? " "
                                : where.toLowerCase().trim().startsWith("inner") ? " "
                                : " where ") + where;
                    }

                    if (!Util.validStr(hassch, "FILSCH").trim().equals("")) {
                        String param_[] = params.split(",");

                        if (filt.length != param_.length) {
                            json = new JSONObject();
                            json.put("exito", "ERROR");
                            json.put("msg", "Los parámetros enviados al búscador son diferentes a los establecidos en la consulta");
                            response.getWriter().write(JSONValue.toJSONString(json));
                            return;
                        }

                        int j = 0;
                        String parameter = param_[j].replace("#", ",");
                        for (String flt : filt) {
                            parameter = param_[j].replace("#", ",");
                            sqlCmd = sqlCmd.replace(flt.trim(), "'" + parameter + "'");
                            j++;
                        }
                    }
                    if (sqlCmd.indexOf("order by") == -1) {
                        sqlCmd += " order by " + (lenCam >= 2 ? 2 : lenCam) + " asc";
                    }

                    sqlCmd = sqlCmd.replace("pcodciasesion", "'" + model.getCodCia() + "'");
                    sqlCmd = sqlCmd.replace("pcodprssesion", "'" + model.getCodPrs() + "'");
                    sqlCmd = sqlCmd.replace("pnroprssesion", "'" + model.getNroPrs() + "'");
                    sqlCmd = sqlCmd.replace("pnrousrsesion", "'" + model.getNroUsr()+ "'");

                    model.list((Boolean.parseBoolean(generalFilter) ? "select * from (" + sqlCmd + ") where rownum <= " + rowsNum : sqlCmd), null);
                    set("sqlCmd", sqlCmd, session);

                    List listModel = new LinkedList();
                    Hashtable aux = null;
                    List titles = new LinkedList();

                    if (!model.getList().isEmpty()) {
                        String wids[] = Util.validStr(hassch, "WIDSCH").split(",");
                        String tits[] = Util.validStr(hassch, "TITSCH").split(",");

                        int i = 0;
                        List<String> resultColumn = model.getColumnNames();

                        for (String cols : resultColumn) {
                            aux = new Hashtable();

                            aux.put("name", cols);
                            aux.put("index", cols);
                            if (wids.length == tits.length) {
                                aux.put("width", wids[i]);
                            }
                            aux.put("sortable", true);
                            listModel.add(aux);
                            titles.add(tits[i]);
                            i++;
                        }

                        json.put("coltitles", titles);
//                        json.put("sql", sqlCmd);

                    } else {
                        json = new JSONObject();
                        json.put("exito", "NODATA");
                        json.put("msg", "La consulta no generó resultados");
//                        json.put("sql", sqlCmd);
                        json.put("empty", true);
                        response.getWriter().write(JSONValue.toJSONString(json));
                        return;
                    }

                    json.put("exito", "OK");
                    json.put("modelCols", listModel);
                    json.put("modelTitl", titles);
                    json.put("lista", model.getList());
                    json.put("descripcion", Util.validStr(hassch, "DSPSCH"));
                }
            } else {
                json = new JSONObject();
                json.put("exito", "NODATA");
                json.put("msg", "No existe consulta en el servidor");
                response.getWriter().write(JSONValue.toJSONString(json));
                return;
            }

        } catch (Exception e) {
            Util.logError(e);
            System.out.println("Consulta : " + sqlCmd);
            json = new JSONObject();
            json.put("exito", "ERROR");
            json.put("msg", "Error al generar el formulario");
        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }

    public void getValues() throws IOException {

        String nrosch = Util.getStrRequest("nrosch", request);
        String params = Util.getStrRequest("params", request);
        String pk = "";
        String pk_val = Util.getStrRequest("pk_val", request);

        int lenCam = 0;

        JSONObject json = new JSONObject();

        try {
            sqlCmd = "select * from smasch where nrosch='" + nrosch + "'";
            model.listGenericHash(sqlCmd);
            Hashtable hassch;

            if (!model.getList().isEmpty() && !pk_val.equals("")) {
                hassch = (Hashtable) model.getList().get(0);
                lenCam = Util.validStr(hassch, "CAMSCH").split(",").length;

                sqlCmd = "select " + Util.validStr(hassch, "CAMSCH") + " from ";
                sqlCmd += Util.validStr(hassch, "TABSCH");

                String where = Util.validStr(hassch, "WHRSCH");
                String filt[] = Util.validStr(hassch, "FILSCH").split(",");
                if (!where.trim().equals("")) {

                    sqlCmd += (where.toLowerCase().trim().startsWith("join") ? " " : " where ") + where;
                }

                if (!Util.validStr(hassch, "FILSCH").trim().equals("")) {
                    String param_[] = params.split(",");

                    if (filt.length != param_.length) {
                        json = new JSONObject();
                        json.put("exito", "ERROR");
                        json.put("msg", "Los parámetros enviados son diferentes a los establecidos en la consulta");
                        response.getWriter().write(JSONValue.toJSONString(json));
                        return;
                    }

                    int j = 0;

                    for (String flt : filt) {
                        sqlCmd = sqlCmd.replace(flt.trim(), "'" + param_[j] + "'");
                        j++;
                    }
                }
                if (sqlCmd.indexOf("order by") == -1) {
                    sqlCmd += " order by " + (lenCam >= 2 ? 2 : lenCam) + " asc";
                }

                sqlCmd = sqlCmd.replace("pcodciasesion", "'" + model.getCodCia() + "'");
                sqlCmd = sqlCmd.replace("pcodprssesion", "'" + model.getCodPrs() + "'");
                sqlCmd = sqlCmd.replace("pnroprssesion", "'" + model.getNroPrs() + "'");
                sqlCmd = sqlCmd.replace("pnrousrsesion", "'" + model.getNroUsr()+ "'");

                model.listGenericHash(sqlCmd);

                pk = model.getColumnNames().get(0);

                model.listGenericHash("select * from (" + sqlCmd + ") a where cast(" + pk + " as varchar(4000)) = '" + pk_val + "'");
                set("sqlCmd", sqlCmd, session);

                List listModel = new LinkedList();
                Hashtable aux = null;
                List titles = new LinkedList();

                if (model.getList().isEmpty()) {
                    json.put("exito", "ERROR");
                    response.getWriter().write(JSONValue.toJSONString(json));
                    return;
                }

                List<String> resultColumn = model.getColumnNames();
                aux = (Hashtable) model.getList().get(0);

                json.put("exito", "EXITO");
                json.put("data_show", aux.get(resultColumn.get(1)).toString());
                json.put("data_hidden", aux.get(pk).toString());

            }

        } catch (Exception e) {

            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("mensaje", e.getLocalizedMessage());
            json.put("data-show", "");
            json.put("data-hidden", "");
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    private void filter() {

        String sql;
        String camsch = Util.getStrRequest("camsch", request);
        String filter = Util.getStrRequest("filter", request);
        String rowsNum = Util.getStrRequest("rowsNum", request);

        sqlCmd = get("sqlCmd", session);

        try {
            sql = "select *\n"
                    + " from (" + sqlCmd + ")\n"
                    + "where upper(" + camsch + ") like upper('%" + filter + "%')\n"
                    + "  and rownum <= " + rowsNum;
            model.list(sql, null);

            response.getWriter().write(JSONValue.toJSONString(model.getList()));

        } catch (SQLException e) {
            Util.logError(e);
        } catch (IOException e) {
            Util.logError(e);
        }

    }

    public static void set(String name, Object value, HttpSession sesion) {
        sesion.setAttribute(name, value);
    }

    public static String get(String name, HttpSession sesion) {
        return (String) sesion.getAttribute(name);
    }

}

class makeControlSearch extends TagSupport {

    private String nrosch;
    private String pkey;
    private String isHtml;
    private String params;
    private String hideColumn;
    private String title;
    private int size;
    private int onSelect;
    private int beforeOpen;
    private int onClose;

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(this.make());
        } catch (Exception ex) {
            throw new JspException("Class mvc.controller.jQgridTab Metodo [doEndTag] " + ex.getMessage());
        }
        return SKIP_BODY;
    }

    private String make() {

        return "";

    }

}
