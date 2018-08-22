package mvc.controller;import mvc.model.Util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import mvc.model.HibernateUtil;
import mvc.model.ModelSma;
import mvc.model.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @descripcion Clase manejo de calendarios
 * @nombre AdmCalendarAction
 * @copyrigth ClSMA Ltda
 * @author Juan Camilo Wong
 * @fechaModificacion 11/09/2014
 */
public class AdmCalendarAction extends Action {

    private String sqlCmd = "";

    /**
     * Ejecuta la accion
     *
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    @Override
    public void run() throws ServletException, IOException {

        // Recuperacion de estado de la peticion.  
        String events = request.getParameter("events")
                != null ? request.getParameter("events") : "";
        String codkls = request.getParameter("codkls")
                != null ? request.getParameter("codkls") : "";
        String tpokls = request.getParameter("tpokls")
                != null ? request.getParameter("tpokls") : "";
        String stdkls = request.getParameter("stdkls")
                != null ? request.getParameter("stdkls") : "";

        try {

            if (events.equals("LSTKLS")) {
                listarCalendarios(tpokls, stdkls);
            } else if (events.equals("SHOWKLS")) {
                buscarCalendario(codkls);
            } else if (events.equals("GETPGM")) {
                response.getWriter().write(listarProgramas(codkls));
            } else if (events.equals("SAVEAXK")) {
                guardarActividades(codkls);
            } else if (events.equals("SAVEPGM")) {
                guardarProgramas(codkls);
            } else if (events.equals("SAVEKLS")) {
                guardarCalendario();
            } else if (events.equals("SAVEDKS")) {
                saveDks(codkls);
            } else if (events.equals("EDITAXK")) {
                editAxk();
                return;
            } else if (events.equals("LISTAXK")) {
                listAxk();
                return;
            } else if (events.equals("DELAXK")) {
                delAxk();
                return;
            }

        } catch (Exception e) {
            throw new ServletException("Error "
                    + "Clase [" + this.getClass().getName()
                    + "] Metodo [run()] " + e.getMessage());
        }
    }

    /**
     * @descripcion Listar calendarios
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 11/09/2014
     * @fecha_modificacion 11/09/2014
     * @throws IOException
     * @throws SQLException
     */
    private void listarCalendarios(String tpokls, String stdkls)
            throws IOException, SQLException {

        sqlCmd
                = "   select smakls.codkls,\n"
                + "          smakls.nomkls,\n"
                + "          smakls.agnprs, \n"
                + "          smakls.prdprs, \n"
                + "          smaelm.dspelm tpokls\n"
                + "     from smakls\n"
                + "     join smaelm\n"
                + "       on smaelm.nomelm = smakls.tpokls\n"
                + "      and smaelm.codelm = 'KLS'\n"
                + "      and smaelm.tipelm = 'TYPE'\n"
                + "    where smakls.codcia = '" + model.getCodCia() + "' "
                + "      and smakls.tpokls = '" + tpokls + "'\n"
                + "      and smakls.stdkls = '" + stdkls + "'\n"
                + " order by smakls.agnprs desc, smakls.prdprs desc, "
                + "          smakls.nomkls";

        model.listGenericHash(sqlCmd);
        List lstDta = model.getList();
        jQgridTab qt = new jQgridTab();

        qt.setDataList(lstDta);
        qt.setSelector("lstkls");
        qt.setTitles(new String[]{"Código", "Nombre", "Año", "Tipo", "Periodo"});
        qt.setColumns(new String[]{"CODKLS", "NOMKLS", "AGNPRS", "TPOKLS", "PRDPRS"});
        qt.setWidths(new int[]{100, 300, 100, 100, 100});
        qt.setKeys(new int[]{0});

        LinkedHashMap options = new LinkedHashMap();
        options.put("ondblClickRow", "showCalendar");
        options.put("caption", "Calendarios");
        qt.setOptions(options);
        qt.setFilterToolbar(true);
        qt.setPaginador("pgKls");

        response.getWriter().write(qt.getHtml());
    }

    /**
     * @descripcion Buscar Calendario
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 11/09/2014
     * @fecha_modificacion 11/09/2014
     * @throws IOException
     * @throws SQLException
     */
    private void buscarCalendario(String codkls) {

        try {
            sqlCmd
                    = "select *\n"
                    + "  from smakls\n"
                    + " where codcia = '" + model.getCodCia() + "'\n"
                    + "   and codkls = '" + codkls + "'\n";

            model.listGenericHash(sqlCmd);
            List lstDta = model.getList();
            Hashtable smaTmp_ = new Hashtable();

            if (!lstDta.isEmpty()) {
                smaTmp_.put("smakls", lstDta.get(0));
                smaTmp_.put("smaaxk", listActivities(codkls));
                smaTmp_.put("smakxp", listarProgramas(codkls));
                smaTmp_.put("smakxd", listarDepartamentos(codkls));
            }

            response.getWriter().write(JSONValue.toJSONString(smaTmp_));

        } catch (SQLException e) {
            System.out.print(e.getLocalizedMessage());
        } catch (IOException e) {
            System.out.print(e.getLocalizedMessage());
        }
    }

    private String listActivities(String codkls) {

        try {
            sqlCmd
                    = " select smaelm.nroelm,\n"
                    + "            smaelm.dspelm,\n"
                    + "            case when smaaxk.bgnaxk is null\n"
                    + "                  then sysdate\n"
                    + "                  else smaaxk.bgnaxk\n"
                    + "            end bgnaxk,\n"
                    + "            case when smaaxk.endaxk is null\n"
                    + "                 then sysdate + 1\n"
                    + "                 else smaaxk.endaxk\n"
                    + "            end endaxk,\n"
                    + "            smaaxk.agnprs,\n"
                    + "            smaaxk.prdprs,\n"
                    + "            smaaxk.tipaxk,\n"
                    + "            smavld.codvld,\n"
                    + "            smaaxk.nroaxk       \n"
                    + "       from smaelm\n"
                    + "       join smavld\n"
                    + "         on smavld.tpovld = 'AXK'\n"
                    + "        and smavld.codvld = smaelm.nroelm\n"
                    + "        and smavld.codprs = '" + model.getCodPrs() + "'\n"
                    + "  left join smaaxk\n"
                    + "         on smaaxk.codkls = '" + codkls + "'\n"
                    + "        and smaaxk.codaxk = smaelm.nroelm\n"
                    + "      where smaelm.codelm = 'KLS' \n"
                    + "        and smaelm.tipelm = 'DATE'\n"
                    + "   order by smaelm.nroelm;";

            model.list(sqlCmd, null);

            jQgridTab qt = new jQgridTab();

            qt.setDataList(model.getList());
            qt.setSelector("lstAxk");
            qt.setColumns(new String[]{"NROELM", "CODVLD", "DSPELM", "BGNAXK", "ENDAXK", "AGNPRS", "PRDPRS", "TIPAXK", "NROAXK", "SVE", "DEL"});
            qt.setTitles(new String[]{"NROELM", "Id", "Actividad", "Inicio", "Fin", "Año", "Periodo", "Tipo", "nroaxk", "Guardar", "Del."});
            qt.setHiddens(new int[]{0, 8});
            qt.setWidths(new int[]{0, 50, 250, 140, 140, 80, 80, 90, 0, 60, 60});
            qt.setKeys(new int[]{0});
            qt.setFilterToolbar(true);
            qt.setPaginador("pglstAxk");

            LinkedHashMap options = new LinkedHashMap();

            options.put("multiselect", true);
            options.put("loadComplete", "selectAll");
            options.put("onSelectRow", "selectRow");
            qt.setOptions(options);

            options = new LinkedHashMap();

            options.put("3", "setInput");
            options.put("4", "setInput");
            options.put("5", "setInput");
            options.put("6", "setInput");
            options.put("7", "setInput");
            options.put("9", "setInput");
            options.put("10", "setInput");
            qt.setFormatter(options);

            options = new LinkedHashMap();
            options.put("BGNAXK", new String[]{"search-false"});
            options.put("ENDAXK", new String[]{"search-false"});
            options.put("AGNPRS", new String[]{"search-false"});
            options.put("PRDPRS", new String[]{"search-false"});
            qt.setColumnOptions(options);

            return qt.getHtml();

        } catch (SQLException e) {
            System.out.print(e.getLocalizedMessage());
            return "";
        }
    }

    /**
     * @param codkls
     * @descripcion Guardar Actividades de calendario
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 16/09/2014
     * @fecha_modificacion 16/09/2014
     * @throws IOException
     * @throws SQLException
     */
    public void guardarActividades(String codkls) throws IOException, Exception {

        JSONObject resp = new JSONObject();
        JSONObject smaAxk;
        Session s = this.getNewSession();
        Transaction t = HibernateUtil.getNewTransaction(s);
        Hashtable smaaxk;
        Map datos;

        try {

            t.begin();

            String lstAxk = Util.validStr(request.getParameter("lstAxk"));
            String arrSel = Util.validStr(request.getParameter("arrSel"));
            JSONArray array = (JSONArray) JSONValue.parse(arrSel);
            String nroaxk;

            sqlCmd
                    = "select nroaxk\n"
                    + "  from smaaxk\n"
                    + " where codkls = '" + codkls + "'\n"
                    + "   and codaxk not in ('" + lstAxk.replaceAll(",", "','") + "')";

            model.listGenericHashNotTransaction(sqlCmd, s);

            if (!model.getList().isEmpty()) {
                for (Object ob : model.getList()) {

                    smaaxk = (Hashtable) ob;
                    model.deleteLogBook("smaaxk", smaaxk.get("NROAXK").toString(), s);

                }
            }

            for (Object ob : array) {

                smaAxk = (JSONObject) ob;
                sqlCmd
                        = "select nroaxk "
                        + "  from smaaxk"
                        + "  where codaxk= '" + smaAxk.get("codaxk").toString() + "'"
                        + "    and codkls = '" + codkls + "'";

                nroaxk = model.getData(sqlCmd, s).toString();

                datos = new HashMap();

                datos.put("bgnaxk", Util.toDate(smaAxk.get("bgnaxk").toString()));
                datos.put("endaxk", Util.toDate(smaAxk.get("endaxk").toString()));
                datos.put("agnprs", smaAxk.get("agnprs").toString());
                datos.put("prdprs", smaAxk.get("prdprs").toString());

                datos.put("tipaxk", smaAxk.get("tipaxk").toString());

                if (nroaxk.trim().equals("")) {
                    datos.put("codkls", codkls);
                    datos.put("codaxk", smaAxk.get("codaxk").toString());
                    datos.put("stdaxk", "Activo..");

                    model.saveLogBook(datos, "smaaxk", s);
                } else {

                    model.updateLogBook(datos, "smaaxk", nroaxk, s);
                }

            }

            t.commit();
            resp.put("msg", ModelSma.MSG_SAVE);
            resp.put("fnc", "");
            resp.put("type", "OK");

        } catch (Exception ex) {

            t.rollback();
            System.out.println(ex.getLocalizedMessage());

            resp.put("msg", model.setError(ex));
            resp.put("fnc", "");
            resp.put("type", "ERROR");
        }

        response.getWriter().write(resp.toJSONString());
    }

    /**
     * @descripcion Listar Programas
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 16/09/2014
     * @fecha_modificacion 16/09/2014
     * @throws IOException
     * @throws SQLException
     */
    private String listarProgramas(String codkls) {

        try {

            String tpopgm = Util.validStr(request.getParameter("tpopgm"));
            String stgpgm = Util.validStr(request.getParameter("stgpgm"));

            sqlCmd
                    = "   select smapgm.idepgm,\n"
                    + "          smapgm.nropgm,\n"
                    + "          smapgm.nompgm,\n"
                    + "          smapgm.nompgm,\n"
                    + "          smapgm.tpopgm,\n"
                    + "          smapgm.stgpgm,\n"
                    + "          smascn.nomscn,\n"
                    + "          smakxp.nrokxp\n"
                    + "     from smapgm\n"
                    + "     join smapxp\n"
                    + "       on smapxp.nropgm = smapgm.nropgm\n"
                    + "      and smapxp.codprs = '" + model.getCodPrs() + "'\n"
                    + "     join smascn"
                    + "       on smascn.idescn = smapgm.idescn\n"
                    + "left join smakxp \n"
                    + "       on smakxp.codkls = '" + codkls + "'\n"
                    + "      and smapgm.idepgm = smakxp.idepgm\n"
                    + "    where smapgm.tpopgm = '" + tpopgm + "'\n"
                    + "      and smapgm.stgpgm = '" + stgpgm + "'\n"
                    + " order by smapgm.nompgm";

            model.listGenericHash(sqlCmd);

            jQgridTab qt = new jQgridTab();

            qt.setModel(model);
            qt.setSelector("lstKxp");
            qt.setDataList(model.getList());
            qt.setColumns(new String[]{"NROKXP", "IDEPGM", "NROPGM", "NOMPGM", "TPOPGM", "STGPGM", "NOMSCN"});
            qt.setTitles(new String[]{"", "", "Código", "Programa", "Tipo", "Modalidad", "Seccional"});
            qt.setWidths(new int[]{0, 0, 100, 400, 100, 100, 200});
            qt.setGeneralFilter(true);
            qt.setFilterToolbar(true);
            qt.setPaginador("pglstKxp");
            qt.setHiddens(new int[]{0, 1});
            qt.setKeys(new int[]{1});

            LinkedHashMap options = new LinkedHashMap();

            options.put("multiselect", true);
            options.put("loadComplete", "selectAll");
            options.put("rowNum", 1000);
            qt.setOptions(options);
            return qt.getHtml();

        } catch (SQLException e) {
            Util.logError(e);
            return "";
        }

    }

    /**
     * @param codkls
     * @descripcion Guardar Programas
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 16/09/2014
     * @fecha_modificacion 16/09/2014
     * @throws IOException
     */
    public void guardarProgramas(String codkls) throws IOException, Exception {

        Session s = this.getNewSession();
        Transaction t = HibernateUtil.getNewTransaction(s);

        Hashtable smakxp;
        Map datos;
        JSONObject resp = new JSONObject();

        String lstKxp = Util.validStr(request.getParameter("lstKxp")).trim();
        String tpopgm = Util.validStr(request.getParameter("tpopgm"));
        String stgpgm = Util.validStr(request.getParameter("stgpgm"));
        String aux_seles = lstKxp.isEmpty() ? "' '" : "'" + lstKxp.replaceAll(",", "','") + "'";

        try {

            t.begin();

            sqlCmd
                    = " select nrokxp\n"
                    + "    from smakxp\n"
                    + "    join smapgm\n"
                    + "      on smapgm.idepgm = smakxp.idepgm\n"
                    + "   where codkls='" + codkls + "'\n"
                    + "     and tpopgm = '" + tpopgm + "'\n"
                    + "     and stgpgm = '" + stgpgm + "'\n"
                    + "      /*or codkls in (\n"
                    + "                  select codkls\n"
                    + "                   from smatxk \n"
                    + "                  where codtxk = '" + codkls + "'\n"
                    + "                )*/\n"
                    + "     and smakxp.idepgm not in ( " + aux_seles + "  )  ";

            model.listGenericHash(sqlCmd);

            if (!model.getList().isEmpty()) {
                for (Object ob : model.getList()) {

                    smakxp = (Hashtable) ob;
                    model.deleteLogBook("smakxp", smakxp.get("NROKXP").toString(), s);

                }
            }

            sqlCmd = "select *\n"
                    + "  from table(sma_system_general.split_by_symbol('" + lstKxp + "',','))\n"
                    + " where element not in (select smakxp.idepgm \n"
                    + "                       from smakxp \n"
                    + "                      where codkls = '" + codkls + "') \n"
                    + "   and element is not null";

            model.listGenericHash(sqlCmd);

            for (Object a : model.getList()) {

                smakxp = (Hashtable) a;

                datos = new HashMap();
                datos.put("codkls", codkls);
                datos.put("idepgm", Util.validStr(smakxp, "ELEMENT"));
                datos.put("stdkxp", "Activa ..");

                model.saveLogBook(datos, "smakxp", s);

            }

            t.commit();
            resp.put("msg", ModelSma.MSG_SAVE);
            resp.put("fnc", "");
            resp.put("type", "OK");

        } catch (Exception e) {

            Util.logError(e);
            t.rollback();

            resp.put("msg", model.setError(e));
            resp.put("fnc", "");
            resp.put("type", "ERROR");
        }

        response.getWriter().write(resp.toJSONString());

    }

    /**
     * @descripcion Guardar Calendario
     * @autor Juan Camilo Wong Ramirez
     * @fecha_creacion 16/09/2014
     * @fecha_modificacion 16/09/2014
     * @throws IOException
     */
    public void guardarCalendario() throws IOException, Exception {

        JSONObject resp = new JSONObject();

        try {

            String codkls = Util.validStr(request.getParameter("codkls"));
            String nomkls = Util.validStr(request.getParameter("nomkls"));
            String agnprs = Util.validStr(request.getParameter("agnprs"));
            String prdprs = Util.validStr(request.getParameter("prdprs"));
            String tpokls = Util.validStr(request.getParameter("tpokls"));
            String stdkls = Util.validStr(request.getParameter("stdkls"));

            Map datos = new HashMap();

            datos.put("NOMKLS", nomkls);
            datos.put("CODCIA", model.getCodCia());
            datos.put("AGNPRS", agnprs);
            datos.put("PRDPRS", prdprs);
            datos.put("TPOKLS", tpokls);
            datos.put("STDKLS", stdkls);
            datos.put("FCHKLS", new Date());

            sqlCmd = "select * "
                    + " from smakls "
                    + " where codkls = '" + codkls + "'";

            model.listGenericHash(sqlCmd);

            if (!model.getList().isEmpty()) {

                model.updateLogBook(datos, "smakls", codkls, null);

                resp.put("msg", ModelSma.MSG_UPDATE);
                resp.put("type", "OK");

            } else {

                codkls = model.saveLogBook(datos, "smakls", null);

                resp.put("msg", ModelSma.MSG_SAVE);
                resp.put("type", "OK");
                resp.put("codkls", codkls);

            }

        } catch (Exception ex) {

            resp.put("msg", model.setError(ex));
            resp.put("type", "ERROR");
            resp.put("fnc", "");
            System.out.println(ex.getLocalizedMessage());
        }

        response.getWriter().write(resp.toJSONString());

    }

    private String listarDepartamentos(String codkls) {

        try {
            sqlCmd
                    = "  select smadks.idedks,\n"
                    + "          smadks.nomdks,\n"
                    + "          smadks.stddks,\n"
                    + "          smakxd.nrokxd\n"
                    + "     from smadks\n"
                    + "left join smakxd \n"
                    + "       on smakxd.idedks = smadks.idedks\n"
                    + "      and smakxd.codkls =  '" + codkls + "'\n"
                    + " order by smadks.nomdks";

            model.listGenericHash(sqlCmd);

            jQgridTab qt = new jQgridTab();

            qt.setModel(model);
            qt.setSelector("lstKxd");
            qt.setDataList(model.getList());
            qt.setColumns(new String[]{"IDEDKS", "NROKXD", "NOMDKS", "STDDKS"});
            qt.setTitles(new String[]{"", "", "Nombre", "Estado"});
            qt.setWidths(new int[]{0, 0, 300, 100});
            qt.setGeneralFilter(true);
            qt.setFilterToolbar(true);
            qt.setPaginador("pglstKxd");
            qt.setHiddens(new int[]{0, 1});
            qt.setKeys(new int[]{0});

            LinkedHashMap options = new LinkedHashMap();

            options.put("multiselect", true);
            options.put("loadComplete", "selectAll");
            qt.setOptions(options);

            return qt.getHtml();

        } catch (SQLException e) {

            Util.logError(e);
            return "";
        }
    }

    private void saveDks(String codkls) throws IOException, Exception {

        String lstdks = Util.validStr(request.getParameter("lstdks")).trim();

        Session s = this.getNewSession();
        Transaction t = HibernateUtil.getNewTransaction(s);
        Hashtable smakxd;
        Map datos;
        JSONObject resp = new JSONObject();
        String aux_seles = lstdks.isEmpty() ? "' '" : "'" + lstdks.replaceAll(",", "','") + "'";

        try {

            t.begin();

            sqlCmd
                    = "  select nrokxd\n"
                    + "    from smakxd \n"
                    + "   where codkls='" + codkls + "'\n"
                    + "     and smakxd.idedks not in ( " + aux_seles + "   )  ";

            model.listGenericHash(sqlCmd);

            if (!model.getList().isEmpty()) {
                for (Object ob : model.getList()) {

                    smakxd = (Hashtable) ob;
                    model.deleteLogBook("smakxd", smakxd.get("NROKXD").toString(), s);

                }
            }

            sqlCmd = " select *\n"
                    + "   from table(sma_system_general.split_by_symbol('" + lstdks + "',','))\n"
                    + "  where element not in (\n"
                    + "                           select smakxd.idedks \n"
                    + "                         from smakxd\n"
                    + "                        where smakxd.codkls= '" + codkls + "'"
                    + "                        )\n"
                    + "    and element is not null";

            model.listGenericHash(sqlCmd);

            for (Object a : model.getList()) {

                smakxd = (Hashtable) a;

                datos = new HashMap();
                datos.put("codkls", codkls);
                datos.put("idedks", Util.validStr(smakxd, "ELEMENT"));
                datos.put("stdkxd", "Activa ..");

                model.saveLogBook(datos, "smakxd", s);

            }

            t.commit();
            resp.put("msg", ModelSma.MSG_SAVE);
            resp.put("fnc", "");
            resp.put("type", "OK");

        } catch (Exception e) {

            Util.logError(e);
            t.rollback();

            resp.put("msg", model.setError(e));
            resp.put("fnc", "");
            resp.put("type", "ERROR");
        }

        response.getWriter().write(resp.toJSONString());
    }

    private void editAxk() throws Exception {

        JSONObject form = Util.getJsonRequest("form", request);
        JSONObject json = new JSONObject();
        String codkls = Util.getStrRequest("codkls", request);

        try {

            Map datos = Util.map("smaaxk", form);
            datos.put("codkls", codkls);
            datos.put("codaxk", form.get("NROELM"));

            String nroaxk = "";
            if ((nroaxk = Util.validStr(datos, "nroaxk")).trim().isEmpty()) {
                model.saveLogBook(datos, "smaaxk", null);
                json.put("msg", model.MSG_SAVE);
            } else {
                model.updateLogBook(datos, "smaaxk", nroaxk, null);
                json.put("msg", model.MSG_UPDATE);
            }
            json.put("exito", "OK");

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(json.toJSONString());
    }

    private void listAxk() throws Exception {
        JSONObject json = new JSONObject();
        String codkls = Util.getStrRequest("codkls", request);
        try {

            json.put("exito", "OK");
            json.put("html", listActivities(codkls));

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(json.toJSONString());
    }

    private void delAxk() throws Exception {

        JSONObject json = new JSONObject();
        String nroaxk = Util.getStrRequest("form", request);
        String codkls = Util.getStrRequest("codkls", request);
        try {

            model.deleteLogBook("smaaxk", "nroaxk=~" + nroaxk + "~|codkls=~" + codkls + "~|", null);

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(json.toJSONString());

    }
}
