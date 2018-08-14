/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import mvc.model.Util;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import mvc.model.HibernateUtil;
import mvc.model.ModelSma;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import taglib.form.inputForm;

/**
 *
 * @author Carlos Pinto Jimenez ( Cl-Sma )
 * @Created 13/07/2016 06:15:46 PM
 * @Archivo AdmAcademicProgramAction
 */
public class AdmAcademicPlanningManagerAction extends Action {

    public static String agnprs;
    public static String prdprs;

    private JSONObject json;
    private String sqlCmd;
    private String idedks;
    private String nropkp;
    private String type;
    private boolean isPgm;
    private String idepgm;

    @Override
    public void run() throws ServletException, IOException {
        String event = Util.getStrRequest("event", request);
        json = new JSONObject();
        agnprs = get("agnprs", session);
        prdprs = get("prdprs", session);
        nropkp = get("nropkp", session);
        idedks = get("idedks", session);
        type = get("type", session);
        isPgm = getObject("isPgm", session) == null
                ? false
                : (Boolean) getObject("isPgm", session);

        try {
            if (event.equals("LSTPGMS")) {
                showPrograms();
            } else if (event.equals("LSTDKS")) {
                lstDks();
            } else if (event.equals("GROUPS")) {
                getGroups();
            } else if (event.equals("DATAMAT")) {
                getDataMat();
            } else if (event.equals("SAVE")) {
                savePak();
            } else if (event.equals("SAVESMT")) {
                saveSmt();
            } else if (event.equals("GETSMT")) {
                getSmt();
            } else if (event.equals("SMTCRS")) {
                ComboSmt();
            } else if (event.equals("SAVECRS")) {
                saveCrs();
            } else if (event.equals("GETCRS")) {
                listCrs();
            } else if (event.equals("DATACRS")) {
                getDataCrs();
            } else if (event.equals("DELCRS")) {
                deleteCrs();
            } else if (event.equals("DELGRP")) {
                delGrp();
            } else if (event.equals("DELSMT")) {
                deleteSmt();
            } else if (event.equals("SAVEPDA")) {
                savePda();
            } else if (event.equals("DELPDA")) {
                delpda();
            } else if (event.equals("GETLBR")) {
                lstLbr();
            } else if (event.equals("GETELC")) {
                getElectives();
            } else if (event.equals("LSTINTEGRATED")) {
                lstIntegrated();
            } else if (event.equals("LSTMOD")) {
                listModules();
            } else if (event.equals("LSTINT")) {
                listIntegrated();
            } else if (event.equals("MODCONF")) {
                confModules();
            } else if (event.equals("SAVEMDL")) {
                saveMdl();
            } else if (event.equals("SAVEMAI")) {
                saveMai();
            } else if (event.equals("DELMDL")) {
                deleteMdl();
            } else if (event.equals("SAVEADD")) {
                saveAdd();
            } else if (event.equals("LSTPXG")) {
                lstPxg();
            } else if (event.equals("DELPXG")) {
                deletePxg();
            } else if (event.equals("DTLPXG")) {
                detailPxg();
            } else if (event.equals("SAVEHPR")) {
                saveHpr();
            } else if (event.equals("HRSPRF")) {
                hrsPrf();
            }

        } catch (Exception e) {
            Util.logError(e);
            Map datos = new HashMap();
            datos.put("exito", ModelSma.TYPE_ERROR);
            datos.put("type", ModelSma.TYPE_ERROR);
            datos.put("msg", model.setError(e)); // model.setError(e) 
            datos.put("icon", ModelSma.TYPE_ERROR);
            write(datos);
        }
    }

    private void write(Object js) throws IOException {
        response.getWriter().write(JSONValue.toJSONString(js));

    }

    public static String validRol(ModelSma model, String tporol) throws SQLException {


            String sqlCmd = "select *\n"
                    + "    from table( sma_system_roles.user_roles( p_codusr => '" + model.getCodPrs() + "' ) ) \n"
                    + "   where tposgu in ( '" + tporol.replace(",", "','") + "' )";

            model.list(sqlCmd, null);
            return (model.getList().isEmpty() ? "false" : "true");

    }

    public static String validCalendar(ModelSma model) throws SQLException {


        String sqlCmd = "select *\n"
                    + "    from table( sma_calendar.activity_user( p_codcia => '" + model.getCodCia() + "' , "
                    + "                                            p_codprs => '" + model.getCodPrs() + "' ,"
                    + "                                            p_codaxk => 'PAK' ) ) ";

            model.list(sqlCmd, null);

            if (model.getList().isEmpty()) {
                return "false";
            }

            Map datos = (HashMap) model.getList().get(0);

            agnprs = Util.validStr(datos, "AGNPRS");
            prdprs = Util.validStr(datos, "PRDPRS");

            return (agnprs.trim().isEmpty() ? "false" : "true");

           
    }

    public static void set(String name, Object value, HttpSession sesion) {
        sesion.setAttribute(name, value);
    }

    public static String get(String name, HttpSession sesion) {
        return (String) sesion.getAttribute(name);
    }

    public static Object getObject(String name, HttpSession sesion) {
        return sesion.getAttribute(name);
    }

    private void lstDks() throws Exception {
        String ag = Util.getStrRequest("a", request);
        String pr = Util.getStrRequest("p", request);

        if (!ag.trim().isEmpty()) {
            agnprs = ag;
            prdprs = pr;
            set("agnprs", ag, session);
            set("prdprs", pr, session);
        }


            sqlCmd = "select smadks.idedks , smadks.nomdks , smadks.coddks , smapkp.nropkp, fcipkp , fcvpkp\n"
                    + "  from smapkp\n"
                    + "  join smadks\n"
                    + "    on smadks.idedks = smapkp.codpkp\n"
                    + "  join table( sma_academic.departments_assign( p_codcia => '" + model.getCodCia() + "' , p_codprs => '" + model.getCodPrs() + "' ) ) sma"
                    + "    on sma.idedks = smadks.idedks\n"
                    + "  where agnprs || prdprs = '" + agnprs + prdprs + "'\n"
                    + "   order by nompkp asc";

            model.list(sqlCmd, null);
//            
            jQgridTab tab = new jQgridTab();
//            
            tab.setDataList(model.getList());
            tab.setColumns(new String[]{"IDEDKS", "NOMDKS", "CODDKS", "NROPKP", "FCIPKP", "FCVPKP"});
            tab.setTitles(new String[]{"", "Departamento", "Código", "", "", ""});
            tab.setWidths(new int[]{0, 500, 100, 0, 0, 0});
            tab.setHiddens(new int[]{0, 3, 4, 5});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqDks");
            tab.setFilterToolbar(true);
            tab.setPaginador("pagerDks");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "250");
            map.put("ondblClickRow", "detailDepartment");
            tab.setOptions(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());


        write(json);
    }

    private void getGroups() throws Exception {
        idedks = Util.getStrRequest("a", request);
        type = Util.getStrRequest("b", request);
        nropkp = Util.getStrRequest("c", request);
        idepgm = Util.getStrRequest("d", request);

        set("idedks", idedks, session);
        set("nropkp", nropkp, session);
        set("type", type, session);


            sqlCmd = "sma_academic_department.has_programs( p_idedks => '" + idedks + "' )";
            isPgm = !model.callFunction(sqlCmd, new Object[]{}).trim().equals("false");
            set("isPgm", isPgm, session);

            sqlCmd = "select smapak.* , \n"
                    + "       smapak.nompgm || ' - Pensum: ' || smapak.nropsm nompgm_group,\n"
                    + "       nomcrs || ' - ' || codcrs nomcrs_cod,\n"
                    + "       nriprs || ' - ' || nombre nomprf_ful\n"
                    + "from table( sma_academic_department.groups_detail(\n"
                    + "                                                    p_codcia => '" + model.getCodCia() + "',\n"
                    + "                                                    p_codprs => '" + model.getCodPrs() + "',\n"
                    + "                                                    p_idedks => '" + idedks + "',\n"
                    + "                                                    p_agnprs => '" + agnprs + "',\n"
                    + "                                                    p_prdprs => '" + prdprs + "') ) smapak \n"
                    + "where smapak.codpgm in ( \n"
                    + "                         select nropgm \n"
                    + "                           from smapgm \n"
                    + "                          where idepgm = '" + idepgm + "' \n"
                    + "                       ) \n"
                    + "order by nommat asc";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            LinkedHashMap map = new LinkedHashMap();

            String columns[] = new String[]{"NROPAK", "CODMAT", "NOMMAT", "CODPAK", "NRIPRS", "NOMBRE", "DEL", "INTEGRATED"};
            String titles[] = new String[]{"", "Código", "Asignatura", "Grupo", "Idenfiticación", "Docente", "Del", ""};
            int widths[] = new int[]{0, 100, 250, 60, 100, 220, 60, 0};
            map.put("6", "delGrp");
            tab.setHiddens(new int[]{0, 7});

            if (isPgm) {
                columns = new String[]{"NROPAK", "SMTPSM", "NOMPGM_GROUP", "NOMCRS_COD", "CODMAT", "NOMMAT", "CODPAK", "NOMPRF_FUL", "CODPSM", "DEL", "INTEGRATED"};
                titles = new String[]{"", "Sem.", "Programa", "Curso", "Código", "Asignatura", "Grupo", "Docente", "", "Del", ""};
                widths = new int[]{0, 30, 130, 140, 90, 220, 30, 160, 0, 40, 0};
                map = new LinkedHashMap();
                map.put("9", "delGrp");
                tab.setHiddens(new int[]{0, 8, 10});
            }
            tab.setFormatter(map);
            tab.setColumns(columns);
            tab.setTitles(titles);
            tab.setWidths(widths);

            tab.setKeys(new int[]{0});
            tab.setSelector("jqGroup");
            tab.setPaginador("pagerGroup");
            tab.setFilterToolbar(true);
            tab.setDataList(model.getList());

            if (type.equals("MAT")) {
                if (isPgm) {
                    tab.setGroupFields("NOMPGM_GROUP,SMTPSM");
                    tab.setGroupText("<b>Programa {0}</b>,<b>Semestre {0}</b>");
                    tab.setGroupColumnShow(new Boolean[]{true, false});
                    tab.setGroupCollapse(false);
                }
            } else {

                tab.setGroupFields("NOMPRF_FUL");
                tab.setGroupText("<b>Docente {0}</b>");
                tab.setGroupColumnShow(new Boolean[]{true});
                tab.setGroupCollapse(false);

            }

            map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "300");
            map.put("ondblClickRow", "detailGrp");
            map.put("subGrid", true);
            map.put("subGridRowExpanded", "expandSubGrid");
            map.put("subGridRowColapsed", "collapseSubGrid");
            map.put("subGridBeforeExpand", "checkBeforeExpand");
            map.put("loadComplete", "loadComplete");

            tab.setOptions(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());
            json.put("ispgm", isPgm);
            json.put("combo", ComboSmt());

        write(json);

    }

    private void getDataMat() throws Exception {
        String nromat = Util.getStrRequest("a", request);
        String nropak = Util.getStrRequest("b", request);
        String codpsm = Util.getStrRequest("c", request);

            Map datos = null;
            if (!nromat.trim().isEmpty() && codpsm.trim().isEmpty()) {
                datos = model.copy("smamat", "nromat='" + nromat + "'", null);
            } else if (!codpsm.trim().isEmpty()) {
                sqlCmd = "select smamat.nromat , \n"
                        + "          teopsm teomat, \n"
                        + "          prapsm pramat, \n"
                        + "          invpsm invmat, \n"
                        + "          crdpsm crdmat,\n"
                        + "          ntspsm ntsmat,\n"
                        + "          lblpsm lblmat,\n"
                        + "          habpsm habmat,\n"
                        + "          knppsm knpmat,\n"
                        + "          pjapsm pjamat,\n"
                        + "          pjbpsm pjbmat,\n"
                        + "          pjcpsm pjcmat,\n"
                        + "          pjdpsm pjdmat,\n"
                        + "          smapsm.invpsm invmat,\n"
                        + "          nommat,\n"
                        + "          codmat,\n"
                        + "          mdlmat,\n"
                        + "          smapsm.codpsm\n"
                        + "    from smapsm\n"
                        + "    join smamat\n"
                        + "      on smamat.nromat = smapsm.nromat\n"
                        + "   where smapsm.codpsm = '" + codpsm + "'";
                model.list(sqlCmd, null);
                if (!model.getList().isEmpty()) {
                    datos = ((HashMap) model.getList().get(0));
                } else {
                    datos = new HashMap();
                }

            } else {

                sqlCmd = "select smapak.* ,"
                        + "      smamat.* ,"
                        + "      smapak_.*, "
                        + "      smagrp.idecrs ,"
                        + "      smacrs.nomcrs ,"
                        + "      smacrs.bgncrs,"
                        + "      smacrs.endcrs ,"
                        + "      smagrp.codpsm ,"
                        + "      sma_academic_department.user_access_subject(\n"
                        + "                                                     p_codcia => '" + model.getCodCia() + "',\n"
                        + "                                                     p_codprs => '" + model.getCodPrs() + "',\n"
                        + "                                                     p_codmat => smamat.codmat\n"
                        + "                                                  ) canedit\n"
                        + "  from table ( sma_academic_program.groups_data( p_agnprs => '" + agnprs + "' , p_prdprs => '" + prdprs + "' ) )smapak\n"
                        + "  join smamat\n"
                        + "    on smamat.nromat = smapak.nromat\n"
                        + "  join smapak smapak_\n"
                        + "    on smapak_.nropak = smapak.nropak\n"
                        + "  left join smagrp"
                        + "    on smagrp.nropak = smapak.nropak\n"
                        + "  left join smacrs"
                        + "    on smacrs.idecrs = smagrp.idecrs\n"
                        + " where smapak.nropak = '" + nropak + "'";

                model.list(sqlCmd, null);
                if (!model.getList().isEmpty()) {
                    datos = ((HashMap) model.getList().get(0));
                } else {
                    datos = new HashMap();
                }

            }

            if (!nropak.trim().isEmpty()) {

                json.put("hrsprf", tblPrfHrs(Util.validStr(datos, "NROPRF"), agnprs, prdprs, model));

            }

            json.put("exito", "OK");
            json.put("smamat", datos);

        write(json);
    }

    public static String tblPrfHrs(String nroprf, String agnprs, String prdprs, ModelSma model) throws SQLException {


            String sqlCmd = "select smaelm.nroelm , \n"
                    + "       smaelm.nomelm ,\n"
                    + "       nvl( cnthrs , 0 ) cnthrs,\n"
                    + "       smahpr.nrohpr"
                    + "  from smaelm \n"
                    + "  left join smahpr\n"
                    + "    on smaelm.nroelm = smahpr.tpohrs\n"
                    + "   and smahpr.nroprf = '" + nroprf + "'\n";
            if (!agnprs.trim().isEmpty() || !prdprs.trim().isEmpty()) {
                sqlCmd += "   and smahpr.agnprs || smahpr.prdprs = '" + agnprs + prdprs + "'\n";
            }
            sqlCmd += " where codelm = 'PRF'\n"
                    + "   and tipelm = 'HOUR'\n"
                    + "   and swhelm = 1";

            model.list(sqlCmd, null);
            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"NROELM", "NOMELM", "CNTHRS", "NROHPR"});
            tab.setTitles(new String[]{"", "Descripción", "cant.", "NROHPR"});
            tab.setWidths(new int[]{0, 200, 60, 0});
            tab.setHiddens(new int[]{0, 3});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqHrsPrf");
            tab.setDataList(model.getList());

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("2", "formatHrsPrf");
            tab.setFormatter(map);
            return tab.getHtml();

    }

    private void savePak() throws Exception {
        JSONObject form = Util.getJsonRequest("form", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {

            tra.begin();

            Map smapak = Util.map("smapak", form);
            Map smagrp = null;

            String codpsm = Util.validStr(form, "codpsm");
            String codmdl = Util.validStr(form, "mdlmat");
            String nropak = Util.validStr(smapak, "nropak");
            String codpak = Util.validStr(smapak, "codpak");

            smapak.put("stdpak", "Generado");
            smapak.put("smnpak", "16");

            if (Util.validStr(smapak, "nroprf").trim().isEmpty()) {
                smapak.put("nroprf", " ");
            }

            if (Util.validStr(form, "mdlmat").trim().equals("1")) {
                smapak.put("tpomat", "MDL");
//                smapak.put("tpopak", obtenerTipoGrupo("MDL"));
            } else {
                smapak.put("tpomat", Util.validStr(smapak, "tpomat"));
//                smapak.put("tpopak", obtenerTipoGrupo(Util.validStr(smapak, "tpomat")));
            }

            if (nropak.trim().isEmpty()) {

                codpak = model.callFunctionOrProcedureNotTransaction("sma_academic_department.group_sequence( p_nropkp => '" + nropkp + "' ,"
                        + "                                                                                          p_nromat => '" + Util.validStr(form, "nromat") + "' )", sesion);
                smapak.put("codpak", codpak);

                nropak = model.saveLogBook(smapak, "smapak", sesion);
                json.put("msg", model.MSG_SAVE);
            } else {
                model.updateLogBook(smapak, "smapak", nropak, sesion);
                json.put("msg", model.MSG_UPDATE);
            }

            if (!codpsm.trim().isEmpty() && !codpsm.equals("X")) {

                smagrp = new HashMap();
                smagrp.put("nropak", nropak);
                smagrp.put("codpsm", codpsm);
                smagrp.put("idecrs", Util.validStr(form, "idecrs"));
                smagrp.put("nrogrp", codpak);
                smagrp.put("kpcgrp", Util.validStr(form, "kpcpak"));
                smagrp.put("stdgrp", "Aprobado..");
                smagrp.put("tpogrp", Util.validStr(smapak, "tpopak"));
                smagrp.put("tpomat", Util.validStr(smapak, "tpomat"));

                if (Util.validStr(smapak, "tpomat").equals("ELC")) {

                    String idesmt = "sma_academic_department.semesteir_create( "
                            + "                                                p_codpsm => '" + codpsm + "', "
                            + "                                                p_nropkp => '" + nropkp + "' )";
                    idesmt = model.callFunctionOrProcedureNotTransaction(idesmt, sesion);

                    String idecrs = "sma_academic_department.course_create( "
                            + "                                               p_codpsm => '" + codpsm + "', "
                            + "                                               p_idesmt => '" + idesmt + "' )";
                    idecrs = model.callFunctionOrProcedureNotTransaction(idecrs, sesion);

                    smagrp.put("idecrs", idecrs);
                }

                Map auxgrp = model.copy("smagrp", "nropak='" + nropak + "'", sesion);
                if (auxgrp == null || auxgrp.isEmpty()) {
                    model.saveLogBook(smagrp, "smagrp", sesion);
                } else {
                    model.updateLogBook(smagrp, "smagrp", "nropak=~" + nropak + "~|", sesion);
                }

            }

            if (!codmdl.trim().isEmpty()) {

                Map smamat = model.copy("smamat", "codmat = '" + codmdl + "'", sesion);
                //Verificar que no exista el grupo integrada
                String nropakmdl = (String) model.getData("select nropak from smapak where nromat = '"
                        + Util.validStr(smamat, "NROMAT") + "' and nropkp = '"
                        + Util.validStr(smapak, "NROPKP") + "' ", sesion);

                Map smamdl = new HashMap();
                smamdl.put("nromat", Util.validStr(smamat, "NROMAT"));
                smamdl.put("nropkp", Util.validStr(smapak, "nropkp"));
                smamdl.put("codpak", "A1");
                smamdl.put("kpcpak", Util.validStr(smapak, "kpcpak"));
                smamdl.put("bgnpak", smapak.get("bgnpak"));
                smamdl.put("endpak", smapak.get("endpak"));
                smamdl.put("tpopak", Util.validStr(smapak, "tpopak"));
                smamdl.put("tpomat", "NGR");
                smamdl.put("pjapak", Util.validStr(smamat, "PJAMAT"));
                smamdl.put("pjbpak", Util.validStr(smamat, "PJBMAT"));
                smamdl.put("pjcpak", Util.validStr(smamat, "PJCMAT"));
                smamdl.put("pjdpak", Util.validStr(smamat, "PJDMAT"));
                smamdl.put("crdakd", Util.validStr(smamat, "CRDMAT"));
                smamdl.put("stdpak", "Aprobado");
                smamdl.put("knppak", Util.validStr(smamat, "KNPMAT"));

                smagrp = new HashMap();
                smagrp.put("codpsm", codpsm);
                smagrp.put("idecrs", Util.validStr(form, "idecrs"));
                smagrp.put("nrogrp", "A1");
                smagrp.put("kpcgrp", Util.validStr(form, "kpcpak"));
                smagrp.put("stdgrp", "Aprobado..");
                smagrp.put("tpogrp", obtenerTipoGrupo("NGR"));
                smagrp.put("tpomat", "NGR");

                //Si no existe entonces creo integrada
                if (nropakmdl.trim().isEmpty()) {
                    nropakmdl = model.saveLogBook(smamdl, "smapak", sesion);
                    smagrp.put("nropak", nropakmdl);
                } else {
                    model.updateLogBook(smamdl, "nropak", nropakmdl, sesion);
                }

                String idegrpmdl = (String) model.getData("select idegrp from smagrp where nropak = '"
                        + nropakmdl + "'", sesion);

                if (idegrpmdl.trim().isEmpty()) {
                    model.saveLogBook(smagrp, "smagrp", sesion);
                } else {
                    model.updateLogBook(smagrp, "smagrp", idegrpmdl, sesion);
                }

            }

            json.put("exito", "OK");
            tra.commit();
            
            write(json);
        } catch (Exception e) {
            tra.rollback();
            throw e;
        }
        
    }

    private void saveSmt() throws Exception {
        JSONObject form = Util.getJsonRequest("form", request);

            Map datos = Util.map("smasmt", form);
            datos.put("smtpsm", Util.validStr(form, "nropssShw"));
            datos.put("stdsmt", "Abierto");
            model.saveLogBook(datos, "smasmt", null);

            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);

        write(json);

    }

    private void getSmt() throws Exception {

            sqlCmd = "select smasmt.idesmt , \n"
                    + "       smapss.smtpsm ,\n"
                    + "       nompsd ,\n"
                    + "       smapsd.nropsm ,\n"
                    + "       nompgm\n"
                    + "  from smasmt\n"
                    + "  join smapss\n"
                    + "    on smapss.nropss = smasmt.nropss\n"
                    + "  join smapsd\n"
                    + "    on smapsd.nropsd = smapss.nropsd\n"
                    + "  join smapgm\n"
                    + "    on smapgm.idepgm = smapsd.idepgm\n"
                    + " where smasmt.nropkp = '" + nropkp + "'\n"
                    + "  order by  nompgm  asc ,smtpsm asc";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"IDESMT", "SMTPSM", "NOMPGM", "NROPSM", "DEL"});
            tab.setTitles(new String[]{"", "Semestre", "Nombre pénsum", "Nro. pensum", "Del."});
            tab.setWidths(new int[]{0, 80, 300, 100, 60});
            tab.setHiddens(new int[]{0});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqSmt");
            tab.setFilterToolbar(true);
            tab.setDataList(model.getList());
            tab.setPaginador("pagerSmt");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            map.put("rowNum", 20);
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("4", "delSmt");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());

            json.put("combo", ComboSmt());

        write(json);

    }

    private String ComboSmt() throws IOException, Exception {


        sqlCmd = " select smasmt.idesmt , smasmt.smtpsm  || ' - ' || nropsm || ' - '|| nompsd nomsmt\n"
                    + "  from smasmt\n"
                    + "  join smapss\n"
                    + "    on smapss.nropss = smasmt.nropss\n"
                    + "  join smapsd\n"
                    + "    on smapsd.nropsd = smapss.nropsd\n"
                    + " where smasmt.nropkp = '" + nropkp + "'\n"
                    + " order by smasmt.smtpsm";

            inputForm combo = new inputForm();
            combo.setFilter(sqlCmd);
            combo.setName("idesmt");
            combo.setType("select");
            combo.setTitle("Semestre para el curso");
            return combo.getHtml();

    }

    private void saveCrs() throws Exception {
        JSONObject form = Util.getJsonRequest("form", request);

            Map datos = Util.map("smacrs", form);

            String idesmt = Util.validStr(datos, "idesmt");
            String idecrs = Util.validStr(datos, "idecrs");

            if (idecrs.trim().isEmpty()) {

                String nrocrs = model.callFunctionOrProcedure("sma_academic_department.course_sequence('" + idesmt + "')");
                String codcrs = model.callFunctionOrProcedure("sma_academic_department.course_code('" + idesmt + "')");
                datos.put("nrocrs", nrocrs);
                datos.put("fchcrs", new Date());
                datos.put("codcrs", codcrs);
                datos.put("stdcrs", "Generado");

//                if ( Util.validStr(datos, "tpocrs").trim().equals(request) )
                model.saveLogBook(datos, "smacrs", null);
                json.put("msg", model.MSG_SAVE);
            } else {
                model.updateLogBook(datos, "smacrs", idecrs, null);
                json.put("msg", model.MSG_UPDATE);
            }

            json.put("exito", "OK");

        write(json);
    }

    private void listCrs() throws Exception {

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"IDECRS", "NOMPSD", "NOMCRS", "CODCRS", "NOMTPO", "DEL"});
            tab.setTitles(new String[]{"", "Pénsum", "Curso", "Código", "Tipo", "Del."});
            tab.setWidths(new int[]{0, 150, 250, 120, 120, 60});
            tab.setHiddens(new int[]{0});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqCrs");
            tab.setFilterToolbar(true);
            tab.setDataList(smacrs(""));
            tab.setPaginador("pagerCrs");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            map.put("ondblClickRow", "detailCrs");
            map.put("rowNum", 20);
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("5", "delCrs");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());
            
        write(json);
    }

    private List smacrs(String idecrs) throws SQLException {

            sqlCmd = "select smacrs.*,\n"
                    + "         tpocrs nomtpo,\n"
                    + "         nompsd\n"
                    + "   from smacrs\n"
                    + "   join smasmt\n"
                    + "     on smasmt.idesmt = smacrs.idesmt\n"
                    + "   join smapss\n"
                    + "     on smapss.nropss = smasmt.nropss\n"
                    + "   join smapsd \n"
                    + "     on smapsd.nropsd = smapss.nropsd\n"
                    + "  where smasmt.nropkp = '" + nropkp + "'\n";
//                    + "    and smacrs.tpocrs != 'Electiva'";

            if (!idecrs.trim().isEmpty()) {
                sqlCmd += " and smacrs.idecrs = '" + idecrs + "'";
            }

            sqlCmd += " order by nomcrs asc,codcrs asc";

            model.list(sqlCmd, null);
            return model.getList();
            
    }

    private void getDataCrs() throws Exception {
        String idecrs = Util.getStrRequest("a", request);

            List data = smacrs(idecrs);

            Map smacrs = new HashMap();
            if (!data.isEmpty()) {
                smacrs = (HashMap) data.get(0);
            }
            json.put("exito", "OK");
            json.put("smacrs", smacrs);

        write(json);
    }

    private void deleteCrs() throws Exception {

        String idecrs = Util.getStrRequest("a", request);
        String cofirm = Util.getStrRequest("b", request);

            sqlCmd = "sma_academic_department.course_delete( p_idecrs => '" + idecrs + "' , p_forze => " + cofirm + ")";
            String exito = model.callFunctionOrProcedure(sqlCmd);

            if (exito.equals("confirm")) {
                json.put("exito", "CONFIRM");
                write(json);
                return;
            }

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);

        write(json);

    }

    private void delGrp() throws Exception {
        String nropak = Util.getStrRequest("a", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {
            tra.begin();

            sqlCmd = "select count( nronts ) count\n"
                    + " from smants\n"
                    + "where nropak = '" + nropak + "'";
            sqlCmd = (String) model.getData(sqlCmd, sesion);
            if (Integer.parseInt(sqlCmd) > 0) {
                json.put("exito", "ERROR");
                json.put("msg", "Este grupo posee estudiantes matriculados, No puede eliminar.");
                write(json);
                return;
            }

            model.deleteLogBook("smapxg", "nropak=~" + nropak + "~|", sesion);
            if (isPgm) {
                model.deleteLogBook("smagrp", "nropak=~" + nropak + "~|", sesion);
            }
            model.deleteLogBook("smapak", nropak, sesion);

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);
            tra.commit();
            
            write(json);
        } catch (Exception e) {
            tra.rollback();
            throw e;
        }

    }

    private void deleteSmt() throws Exception {
        String idesmt = Util.getStrRequest("a", request);

            sqlCmd = " select count( idegrp ) count\n"
                    + "   from smacrs\n"
                    + "   join smagrp\n"
                    + "     on smagrp.idecrs = smacrs.idecrs\n"
                    + "  where smacrs.idesmt = '" + idesmt + "'";

            sqlCmd = (String) model.getData(sqlCmd, null);
            if (Integer.parseInt(sqlCmd) > 0) {
                json.put("exito", "ERROR");
                json.put("msg", "Hay cursos con grupos creados en este semestre.No puede eliminar.");
                write(json);
                return;
            }

            model.deleteLogBook("smasmt", idesmt, null);

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);
        write(json);
    }

    private void savePda() throws Exception {
        String nromat = Util.getStrRequest("nromat", request);
        String bgnpda = Util.getStrRequest("bgn", request);
        String endpda = Util.getStrRequest("end", request);
        String prdpda = Util.getStrRequest("prd", request);
        String datpda = Util.getStrRequest("dat", request);
        String schpda = Util.getStrRequest("sch", request);

        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {

            tra.begin();

            sqlCmd = "select smapak.nropak\n"
                    + "  from table( sma_academic_program.groups_data ) smapak\n"
                    + " where nropkp = '" + nropkp + "'\n"
                    + "   and nromat = '" + nromat + "'\n"
                    + "   and tpomat = 'MAT'";

            model.list(sqlCmd, sesion);

            if (model.getList().isEmpty()) {
                json.put("exito", "ERROR");
                json.put("msg", "Esta asignatura no posee grupos programados.");
                write(json);
                return;
            }

            Map data;
            String nropak = null;
            for (Object a : model.getList()) {
                data = (HashMap) a;
                nropak = Util.validStr(data, "NROPAK");

                data = new HashMap();
                data.put("nropak", nropak);
                data.put("datpda", datpda);
                data.put("schpda", schpda);
                data.put("bgnpda", Util.toDate(bgnpda));
                data.put("endpda", Util.toDate(endpda));
                data.put("tpopda", "SCH");
                data.put("prdpda", prdpda);
                data.put("stdpda", "Activa..");
                data.put("codaul", "000000");
                data.put("codcia", model.getCodCia());

                model.saveLogBook(data, "smapda", sesion);

            }

            tra.commit();
            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);
            write(json);

        } catch (Exception e) {
            tra.rollback();
            throw e;
        }
        
    }

    private void delpda() throws Exception {
        String nromat = Util.getStrRequest("mat", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {

            tra.begin();

            sqlCmd = "select smapak.nropak\n"
                    + "  from table( sma_academic_program.groups_data ) smapak\n"
                    + " where nropkp = '" + nropkp + "'\n"
                    + "   and nromat = '" + nromat + "'";

            model.list(sqlCmd, sesion);

            if (model.getList().isEmpty()) {
                json.put("exito", "OK");
                json.put("msg", model.MSG_DELETE);
                write(json);
                return;
            }

            Map data;
            String nropak = null;
            for (Object a : model.getList()) {
                data = (HashMap) a;
                nropak = Util.validStr(data, "NROPAK");

                model.deleteLogBook("smapgr", "nropak=~" + nropak + "~|", sesion);
                model.deleteLogBook("smapda", "nropak=~" + nropak + "~|", sesion);

            }

            tra.commit();
            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);
            
            write(json);

        } catch (Exception e) {
            tra.rollback();
            throw e;
        }
        
    }

    public static String obtenerTipoGrupo(String tpomat) {

        if ("VKC".equals(tpomat)) {
            return "<Vacacion>";
        } else if ("ELC".equals(tpomat)) {
            return "<Electiva>";
        } else if ("NVL".equals(tpomat)) {
            return "<Nivelato>";
        } else if ("RMD".equals(tpomat)) {
            return "<Remedial>";
        } else if ("OPT".equals(tpomat)) {
            return "<Optativa>";
        } else if ("LBR".equals(tpomat)) {
            return "<Libre>";
        } else if ("NGR".equals(tpomat)) {
            return "<Integrad>";
        } else if ("MDL".equals(tpomat)) {
            return "<Modulo>";
        } else if ("ENG".equals(tpomat)) {
            return "<Ingles>";
        } else if ("VRT".equals(tpomat)) {
            return "<Virtual>";
        } else {
            return "<Regular>";
        }
    }

    private void lstLbr() throws Exception {

            sqlCmd = "select *\n"
                    + "from table( sma_academic_department.groups_detail(\n"
                    + "                                                    p_codcia => '" + model.getCodCia() + "',\n"
                    + "                                                    p_codprs => '" + model.getCodPrs() + "',\n"
                    + "                                                    p_idedks => '" + idedks + "',\n"
                    + "                                                    p_agnprs => '" + agnprs + "',\n"
                    + "                                                    p_prdprs => '" + prdprs + "',\n"
                    + "                                                    p_type => 'LBR') )"
                    + "  order by smtpsm asc";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            LinkedHashMap map = new LinkedHashMap();

            String columns[] = new String[]{"NROPAK", "CODMAT", "NOMMAT", "CODPAK", "NRIPRS", "NOMBRE", "DEL"};
            String titles[] = new String[]{"", "Código", "Asignatura", "Grupo", "Idenfiticación", "Docente", "Del"};
            int widths[] = new int[]{0, 100, 280, 60, 100, 250, 60};
            map.put("6", "delGrp");

            tab.setFormatter(map);
            tab.setColumns(columns);
            tab.setTitles(titles);
            tab.setWidths(widths);
            tab.setHiddens(new int[]{0, 8});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqLbr");
            tab.setPaginador("pagerLbr");
            tab.setFilterToolbar(true);
            tab.setDataList(model.getList());

            map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "350");
            map.put("ondblClickRow", "detailLbr");
            map.put("loadComplete", "loadCompleteLbr");
            tab.setOptions(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());

        write(json);
    }

    private void getElectives() throws Exception {

            sqlCmd = "select smaelc.* , codmat || ' - ' || nommat || ' - Sem. ' || smtpsm || ' Pensum: ' || nompsd  nommatelc\n"
                    + "   from table( sma_academic_department"
                    + "               .elective_data( p_nropkp => '" + nropkp + "' , "
                    + "                               p_codcia => '" + model.getCodCia() + "' , "
                    + "                               p_codprs => '" + model.getCodPrs() + "' ) ) smaelc ";
            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"SMTPSM", "CODPSM", "NOMMATELC", "NROMAT", "CODPSE", "NOMPSE", "CODPAK", "NRIPRS", "NOMBRE", "BGNPAK", "ENDPAK", "NOMPSN", "DEL"});
            tab.setTitles(new String[]{"", "", "", "", "Código", "Asignatura", "Grupo", "Identificación", "Docente", "Inicio", "Final", "", "Del."});
            tab.setWidths(new int[]{0, 0, 0, 0, 100, 220, 50, 110, 200, 70, 70, 0, 50});
            tab.setHiddens(new int[]{0, 1, 3});
            tab.setSelector("jqElectiva");
            tab.setFilterToolbar(true);
            tab.setDataList(model.getList());
            tab.setPaginador("pagerElc");

            tab.setGroupCollapse(false);
            tab.setGroupFields("NOMPSN,NOMMATELC");
            tab.setGroupText("<b>Línea: {0}</b>,<b>Electiva: {0}</b>");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "300");
            map.put("onSelectRow", "newElec");
            map.put("loadComplete", "loadCompleteElc");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("6", "detailElective");
            map.put("12", "delGrp");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());
       
        write(json);

    }

    private void lstIntegrated() throws Exception {
        String nromat = Util.getStrRequest("a", request);

            sqlCmd = "   select \n"
                    + "       ' X ' codpsm,\n"
                    + "       smapak.codpkp idepgm    ,\n"
                    + "       ' X ' codpgm,\n"
                    + "       smapak.nompkp ,\n"
                    + "       ' X ' smtpsm, \n"
                    + "       smapak.nromat , \n"
                    + "       smapak.nommat , \n"
                    + "       smapak.codmat ,\n"
                    + "       smamat.mdlmat ,\n"
                    + "       smapak.nropak ,\n"
                    + "       smapak.codpak , \n"
                    + "       smapak.nroprf ,\n"
                    + "       smapak.nombre ,\n"
                    + "       smapak.nriprs , \n"
                    + "       sma_academic_department.user_access_subject(\n"
                    + "                                                     p_codcia => '" + model.getCodCia() + "',\n"
                    + "                                                     p_codprs => '" + model.getCodPrs() + "',\n"
                    + "                                                     p_codmat => smamat.codmat\n"
                    + "                                                    ) canedit"
                    + "  from table( sma_academic_program.groups_data( p_agnprs => '" + agnprs + "' , p_prdprs => '" + prdprs + "' ) ) smapak\n"
                    + "  join smamat\n"
                    + "    on smamat.nromat = smapak.nromat\n"
                    + " where smamat.mdlmat = '" + nromat + "'"
                    + "   and smapak.nropkp = '" + nropkp + "'";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            LinkedHashMap map = new LinkedHashMap();

            String columns[] = new String[]{"NROPAK", "CODMAT", "NOMMAT", "CODPAK", "NRIPRS", "NOMBRE", "DEL"};
            String titles[] = new String[]{"", "Código", "Asignatura", "Grupo", "Idenfiticación", "Docente", "Del"};
            int widths[] = new int[]{0, 100, 230, 60, 100, 220, 50};

            tab.setFormatter(map);
            tab.setColumns(columns);
            tab.setTitles(titles);
            tab.setWidths(widths);
            tab.setHiddens(new int[]{0, 8});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqMatIntegrated" + nromat);
            tab.setDataList(model.getList());

            map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            map.put("ondblClickRow", "detailGrp");
            map.put("loadComplete", "loadCompleteIntegrated");

            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("6", "delGrp");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());

        write(json);
    }

    private void listModules() throws Exception {
        String nromat = Util.getStrRequest("a", request);

            sqlCmd = "select smamat.nromat ,"
                    + "      smamat.nommat ,"
                    + "      smamat.codmat ,"
                    + "      nvl( smamdl.ptjmdl, 0 ) ptjmdl ,"
                    + "      smamdl.nromdl\n"
                    + "   from smamat\n"
                    + "   left join smamdl\n"
                    + "     on smamdl.nromat = '" + nromat + "'\n"
                    + "    and smamdl.mdlmat = smamat.nromat\n"
                    + "  where smamat.mdlmat = '" + nromat + "'";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"NROMAT", "CODMAT", "NOMMAT", "PTJMDL", "NROMDL"});
            tab.setTitles(new String[]{"", "Código", "Asignatura", "Puntaje", ""});
            tab.setWidths(new int[]{0, 120, 250, 80, 0});
            tab.setHiddens(new int[]{0, 4});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqModules");
            tab.setDataList(model.getList());

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("3", "formatPtj");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());

        write(json);

    }

    private void listIntegrated() throws Exception {

            sqlCmd = "select smamat.nromat,\n"
                    + "        smamat.codmat || ' - ' ||  smamat.nommat nommat,\n"
                    + "        matmdl.nromat matmdl, \n"
                    + "        matmdl.nommat nommdl,\n"
                    + "        matmdl.codmat codmdl,\n"
                    + "        smamdl.ptjmdl,\n"
                    + "        smamdl.nromdl \n"
                    + "   from smamdl\n"
                    + "   join smamat\n"
                    + "     on smamdl.nromat = smamat.nromat\n"
                    + "   join smamat matmdl\n"
                    + "     on matmdl.nromat = smamdl.mdlmat\n"
                    + "  where smamdl.nropkp = '" + nropkp + "'";

            model.list(sqlCmd, null);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"NROMDL", "NOMMAT", "CODMAT", "MATMDL", "NOMMDL", "CODMDL", "PTJMDL", "DEL"});
            tab.setTitles(new String[]{"", "Asignatura", "Código", "", "Módulo", "código", "Porcentaje (%)", "Del."});
            tab.setWidths(new int[]{0, 250, 100, 0, 250, 100, 70, 60});
            tab.setHiddens(new int[]{0, 3, 2});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqMdl");
            tab.setDataList(model.getList());
            tab.setFilterToolbar(true);

            tab.setGroupFields("NOMMAT");
            tab.setGroupText("<b>Asignatura Integrada: {0}</b>");
            tab.setGroupCollapse(false);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("7", "delMdl");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());
            
        write(json);
    }

    private void saveMdl() throws Exception {
        JSONArray arrMdl = Util.getJArrayRequest("form", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        try {

            tra.begin();
            JSONObject aux;
            Map datos = null;
            for (Object a : arrMdl) {
                aux = (JSONObject) a;

                datos = new HashMap();
                datos.put("nromat", Util.validStr(aux, "nromat"));
                datos.put("mdlmat", Util.validStr(aux, "mdlmat"));
                datos.put("nropkp", nropkp);
                datos.put("ptjmdl", Util.validStr(aux, "ptjmdl"));

                if (!Util.validStr(aux, "nromdl").trim().isEmpty()) {
                    model.updateLogBook(datos, "smamdl", Util.validStr(aux, "nromdl"), sesion);
                } else {
                    model.saveLogBook(datos, "smamdl", sesion);
                }

            }

            tra.commit();

            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);
            write(json);

        } catch (Exception e) {
            tra.rollback();
            throw e;
        }
        
    }

    private void deleteMdl() throws Exception {
        String nromdl = Util.getStrRequest("a", request);

            model.deleteLogBook("smamdl", nromdl, null);

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);

        write(json);
    }

    private void saveAdd() throws Exception {
        JSONObject form = Util.getJsonRequest("a", request);

            Map data = Util.map("smapxg", form);
            String nropxg = Util.validStr(form, "nropxg");

            if (nropxg.trim().isEmpty()) {
                model.saveLogBook(data, "smapxg", null);
                json.put("msg", model.MSG_SAVE);
            } else {
                model.updateLogBook(data, "smapxg", nropxg, null);
                json.put("msg", model.MSG_UPDATE);
            }
            json.put("exito", "OK");

        write(json);
    }

    private List smapxg(String nropak, String nropxg) throws SQLException {

            sqlCmd = "select smapxg.* , smaprs.apeprs || ' '  || smaprs.nomprs nombre , smaprs.nriprs\n"
                    + "  from smapxg\n"
                    + "  join smaprf\n"
                    + "    on smaprf.nroprf = smapxg.nroprf\n"
                    + "  join smaprs\n"
                    + "    on smaprs.nroprs = smaprf.nroprs\n"
                    + " where smapxg.nropak = '" + nropak + "'";

            if (!nropxg.trim().isEmpty()) {
                sqlCmd += " and smapxg.nropxg = '" + nropxg + "'";
            }
            model.list(sqlCmd, null);
            return model.getList();

    }

    private void lstPxg() throws Exception {
        String nropak = Util.getStrRequest("a", request);

            List data = smapxg(nropak, "");

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"NROPXG", "NRIPRS", "NOMBRE", "HRSPRF", "TPOHRS", "STDPXG", "DEL"});
            tab.setTitles(new String[]{"", "Identificación", "Docente", "Horas", "Tipo Horas", "Estado", "Del."});
            tab.setWidths(new int[]{0, 90, 250, 60, 100, 80, 50});
            tab.setHiddens(new int[]{0});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqPrfAdd");
            tab.setFilterToolbar(true);
            tab.setDataList(data);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "auto");
            map.put("ondblClickRow", "detailPxg");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("6", "formatDelAdd");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());

        write(json);

    }

    private void deletePxg() throws Exception {
        String nropxg = Util.getStrRequest("a", request);

            model.deleteLogBook("smapxg", nropxg, null);

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);

        write(json);
    }

    private void detailPxg() throws Exception {
        String nropxg = Util.getStrRequest("a", request);
        String nropak = Util.getStrRequest("b", request);

            List data = smapxg(nropak, nropxg);
            Map smapxg = new HashMap();
            if (!data.isEmpty()) {
                smapxg = ((HashMap) model.getList().get(0));
            }

            json.put("exito", "OK");
            json.put("smapxg", smapxg);

        write(json);
    }

    private void saveHpr() throws Exception {
        String nroprf = Util.getStrRequest("nroprf", request);
        JSONArray arrPrf = Util.getJArrayRequest("arrHrs", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {

            tra.begin();
            JSONObject aux;
            Map datos = null;
            String nrohpr;
            for (Object a : arrPrf) {
                aux = (JSONObject) a;
                datos = new HashMap();
                datos.put("tpohrs", Util.validStr(aux, "nroelm"));
                datos.put("cnthrs", Util.validStr(aux, "cnthrs"));
                datos.put("prdprs", Util.validStr(aux, "prdprs"));
                datos.put("agnprs", Util.validStr(aux, "agnprs"));
                datos.put("nroprf", nroprf);

                nrohpr = Util.validStr(aux, "nrohpr");

                if (nrohpr.trim().isEmpty()) {
                    model.saveLogBook(datos, "smahpr", sesion);
                } else {
                    model.updateLogBook(datos, "smahpr", nrohpr, sesion);
                }

            }

            tra.commit();

            json.put("exito", "OK");
            json.put("msg", model.MSG_UPDATE);
            write(json);

        } catch (Exception e) {
            tra.rollback();
            throw e;
        }

    }

    private void hrsPrf() throws Exception {
        String nroprf = Util.getStrRequest("a", request);

            json.put("html", tblPrfHrs(nroprf, agnprs, prdprs, model));
            json.put("exito", "OK");

        write(json);
    }








    
    private void showPrograms() throws Exception {
        String ag = Util.getStrRequest("a", request);
        String pr = Util.getStrRequest("p", request);

        if (!ag.trim().isEmpty()) {
            agnprs = ag;
            prdprs = pr;
            set("agnprs", ag, session);
            set("prdprs", pr, session);
        }


            sqlCmd = "sma_academic_planning_manager.programs( p_codcia => '" + model.getCodCia() + "' \n" +
                     "                                      , p_codprs => '" + model.getCodPrs() + "' \n" +
                     "                                      , p_agnprs => '" + agnprs + "'\n" +
                     "                                      , p_prdprs => '" + prdprs + "'\n" +
                     "                                      , o_smapgm => ? )";

            List lista = model.listarSP(sqlCmd, new Object[]{});
//            
            jQgridTab tab = new jQgridTab();
//            
            tab.setDataList(lista);
            tab.setColumns(new String[]{"IDEDKS", "CODDKS", "NOMDKS", "IDEPGM", "NROPGM", "NOMPGM", "NROPKP", "FCIPKP", "FCVPKP"});
            tab.setTitles(new String[]{"Id Dep", "Código", "Departamento", "Id Prog", "Número", "Programa", "", "Inicio", "Fin"});
            tab.setWidths(new int[]{0, 40, 280, 0, 45, 330, 0, 80, 80});
            tab.setHiddens(new int[]{0, 3, 6});
            tab.setKeys(new int[]{3});
            tab.setSelector("jqPgm");
            tab.setFilterToolbar(true);
            tab.setPaginador("pagerPgm");
            tab.setFormatDate(new int[]{7,8});

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "250");
            map.put("ondblClickRow", "detailProgram");
            tab.setOptions(map);

            json.put("exito", "OK");
            json.put("html", tab.getHtml());


        write(json);
    }

    private void confModules() throws Exception {

        String idepgm = Util.getStrRequest("pgm", request);

        sqlCmd = "sma_academic_planning_manager.modules_conf( p_codcia => '" + model.getCodCia() + "' \n" +
                 "                                          , p_codprs => '" + model.getCodPrs() + "' \n" +
                 "                                          , p_idepgm => '" + idepgm + "' \n" +
                 "                                          , o_smamai  => ? )";

        List lista = model.listarSP(sqlCmd, new Object[]{});

            
        jQgridTab tab = new jQgridTab();
        tab.setColumns(new String[]{"NROMAI", "CODPSM", "SMTPSM", "NROPSM", "NOMPSD", "CODMAT", "NOMMAT", "CODMOD", "NOMMOD", "PTJMAI", "SAVE", "DEL"});
        tab.setTitles(new String[]{"", "", "No", "Sem", "Pensum", "Código", "Asignatura", "Cód.Mod", "Módulo", "Porcentaje (%)", "Guarda", "Del."});
        tab.setWidths(new int[]{0, 0, 40, 50, 250, 100, 280, 100, 280, 70, 60});
        tab.setHiddens(new int[]{0,1});
        tab.setKeys(new int[]{0});
        tab.setSelector("jqMai");
        tab.setDataList(lista);
        tab.setFilterToolbar(true);

        tab.setGroupFields("NOMPSD,SMTPSM,NOMMAT");
        tab.setGroupText("<b><i>Pensum: {0}</i></b>,<b><i>Sem: {0}</i></b>,<b><i>Asignatura Integrada: {0}</i></b>");
        tab.setGroupCollapse(false);

        LinkedHashMap map = new LinkedHashMap();
        map.put("align", "center");
        map.put("height", "auto");
        tab.setOptions(map);

        map = new LinkedHashMap();
        map.put("9", "confPtj");
        map.put("10", "savePtj");
        map.put("11", "delMai");
        tab.setFormatter(map);

        json.put("exito", "OK");
        json.put("html", tab.getHtml());

        write(json);
    }
        

    private void saveMai() throws Exception {

        JSONObject data ;
        JSONArray jsonParr = Util.getJArrayRequest("json", request);
        String nromai = "";
        String ptjmai = "";

        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {

            tra.begin();
            for (Object o : jsonParr ) {
                data = (JSONObject) o;
                nromai = data.get("mdl").toString();
                ptjmai = data.get("ptj").toString();
                sqlCmd = "sma_academic_planning_manager.modules_update( p_idesxu => '" + model.getSessionSxu() + "' \n" +
                         "                                            , p_nromai => '" + nromai + "' \n" + 
                         "                                            , p_ptjmai =>  " + ptjmai + " )";
                model.callProcedure(sqlCmd,sesion);
            }
            tra.commit();
            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);
            write(json);

        } catch (Exception e) {
            tra.rollback();
            throw e;
        }

    }
        
    
}



