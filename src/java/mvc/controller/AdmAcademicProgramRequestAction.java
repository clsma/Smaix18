package mvc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import mvc.model.HibernateUtil;
import mvc.model.ModelSma;
import mvc.model.Util;
import mvc.util.ClsmaException;
import mvc.util.ClsmaTypeException;
import mvc.util.UtilConstantes;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import taglib.form.inputForm;

public class AdmAcademicProgramRequestAction extends Action {

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
        agnprs =  get("agnprs", session);
        prdprs = get("prdprs", session);
        nropkp = get("nropkp", session);
        idedks = get("idedks", session);
        type = get("type", session);
        isPgm = getObject("isPgm", session) == null ? false : (Boolean) getObject("isPgm", session);

        System.out.println("Event " + event);
        try {
            if ("GETCAL".equals(event)) {
                getCalendario();
                return;
            }
            if ("SHOWSEM".equals(event)) {
                showSemesters();
                return;
            }
            if ("LSTPGMS".equals(event)) {
                listPrograms();
                return;
            }
            if ("PROJ-SMT".equals(event)) {
                genSemesterProject();
                return;
            }            
            if ("GROUPS".equals(event)) {
                getGroups();
            }            
            if ("DATAMAT".equals(event)) {
                getDataMat();
            } 
            if ("SAVE".equals(event)) {
                savePak();
                return;
            }             
            if ("GETCRS".equals(event)) {
                listCrs();
                return;
            }             
            if ("DATACRS".equals(event)) {
                getDataCrs();
                return;
            } 
            if ("SAVECRS".equals(event)) {
                saveCrs();
                return;
            }             
            if ("DELCRS".equals(event)) {
                deleteCrs();
            } 
            if ("GETSMT".equals(event)) {
                getSmt();
            }
            if ("DELSMT".equals(event)) {
                deleteSmt();
            }             
            if ("SAVESMT".equals(event)) {
                saveSmt();
            } 
            if ("DELGRP".equals(event)) {
                delGrp();
            }             
            if ("GETLBR".equals(event)) {
                lstLbr();
            } 
            if ("GETELC".equals(event)) {
                getElectives();
            }             
            if ("SAVEADD".equals(event)) {
                saveAdd();
            }            
            if ("LSTPXG".equals(event)) {
                lstPxg();
                return;
            }
            if ("DELPXG".equals(event)) {
                deletePxg();
                return;
            }
            if ("DTLPXG".equals(event)) {
                detailPxg();
                return;
            }
            
            if (event.equals("LSTDKS")) {
                lstDks();
            }  else if (event.equals("SMTCRS")) {
                ComboSmt();
            } else if (event.equals("SAVEPDA")) {
                savePda();
            } else if (event.equals("DELPDA")) {
                delpda();
            }else if (event.equals("LSTINTEGRATED")) {
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
        openSqlCommand();
        setSqlCommand("SELECT smapak.* , \n");
        setSqlCommand("       smapak.nompgm || ' - Pensum: ' || smapak.nropsm nompgm_group,\n");
        setSqlCommand("       nomcrs || ' - ' || codcrs nomcrs_cod,\n");
        setSqlCommand("       nriprs || ' - ' || nombre nomprf_ful\n");
        setSqlCommand("FROM table( SMA_ACADEMIC_PROGRAM_PROJECT.groups_detail(\n");
        setSqlCommand("                                    p_codcia => '").append(model.getCodCia()).append("',\n");
        setSqlCommand("                                    p_codprs => '").append(model.getCodPrs()).append("',\n");
        setSqlCommand("                                    p_idedks => '").append(idedks).append("',\n");
        setSqlCommand("                                    p_idepgm => '").append(idepgm).append("',\n");
        setSqlCommand("                                    p_agnprs => '").append(agnprs).append("',\n");
        setSqlCommand("                                    p_prdprs => '").append(prdprs).append("') ) smapak \n");
        setSqlCommand("WHERE smapak.codpgm IN ( \n");
        setSqlCommand("                         SELECT nropgm \n");
        setSqlCommand("                           FROM smapgm \n");
        setSqlCommand("                          WHERE idepgm = '").append(idepgm).append("' \n");
        setSqlCommand("                       ) \n");
        setSqlCommand("ORDER BY nommat asc");

        model.list(getSqlCommand(), null);

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
            openSqlCommand();

            setSqlCommand("select smamat.nromat , \n");
            setSqlCommand( "          teopsm teomat, \n");
            setSqlCommand( "          prapsm pramat, \n");
            setSqlCommand( "          invpsm invmat, \n");
            setSqlCommand( "          crdpsm crdmat,\n");
            setSqlCommand( "          ntspsm ntsmat,\n");
            setSqlCommand( "          lblpsm lblmat,\n");
            setSqlCommand( "          habpsm habmat,\n");
            setSqlCommand( "          knppsm knpmat,\n");
            setSqlCommand( "          pjapsm pjamat,\n");
            setSqlCommand( "          pjbpsm pjbmat,\n");
            setSqlCommand( "          pjcpsm pjcmat,\n");
            setSqlCommand( "          pjdpsm pjdmat,\n");
            setSqlCommand( "          smapsm.invpsm invmat,\n");
            setSqlCommand( "          nommat,\n");
            setSqlCommand( "          codmat,\n");
            setSqlCommand( "          mdlmat,\n");
            setSqlCommand( "          smapsm.codpsm\n");
            setSqlCommand( "    from smapsm\n");
            setSqlCommand( "    join smamat\n");
            setSqlCommand( "      on smamat.nromat = smapsm.nromat\n");
            setSqlCommand( "   where smapsm.codpsm = '" ).append( codpsm ).append( "'");
            model.list(getSqlCommand(), null);
            if (!model.getList().isEmpty()) {
                datos = ((HashMap) model.getList().get(0));
            } else {
                datos = new HashMap();
            }

        } else {
            openSqlCommand();
            setSqlCommand("select smapak.* ,");
            setSqlCommand( "      smamat.* ,");
            setSqlCommand( "      smapak_.*, ");
            setSqlCommand( "      smagrp.idecrs ,");
            setSqlCommand( "      smacrs.nomcrs ,");
            setSqlCommand( "      smacrs.bgncrs,");
            setSqlCommand( "      smacrs.endcrs ,");
            setSqlCommand( "      smagrp.codpsm ,");
            setSqlCommand( "      sma_academic_department.user_access_subject(\n");
            setSqlCommand( "                                p_codcia => '" ).append( model.getCodCia() ).append( "',\n");
            setSqlCommand( "                                p_codprs => '" ).append( model.getCodPrs() ).append( "',\n");
            setSqlCommand( "                                 p_codmat => smamat.codmat\n");
            setSqlCommand( "                              ) canedit\n");
            setSqlCommand( "  from table ( SMA_ACADEMIC_PROGRAM_PROJECT.groups_data( p_agnprs => '" ).append( agnprs ).append( "' , p_prdprs => '" ).append( prdprs ).append( "' ) )smapak\n");
            setSqlCommand( "  join smamat\n");
            setSqlCommand( "    on smamat.nromat = smapak.nromat\n");
            setSqlCommand( "  join SMARPA smapak_\n");
            setSqlCommand( "    on smapak_.nropak = smapak.nropak\n");
            setSqlCommand( "  left join SMARGR smagrp");
            setSqlCommand( "    on smagrp.nropak = smapak.nropak\n");
            setSqlCommand( "  left join SMARCR smacrs");
            setSqlCommand( "    on smacrs.idecrs = smagrp.idecrs\n");
            setSqlCommand( " where smapak.nropak = '" ).append( nropak ).append( "'");

            model.list(getSqlCommand(), null);
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
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("select smaelm.nroelm , \n")
                    .append( "       smaelm.nomelm ,\n")
                    .append( "       nvl( cnthrs , 0 ) cnthrs,\n")
                    .append( "       smahpr.nrohpr")
                    .append( "  from smaelm \n")
                    .append( "  left join smahpr\n")
                    .append( "    on smaelm.nroelm = smahpr.tpohrs\n")
                    .append( "   and smahpr.nroprf = '" ).append( nroprf ).append( "'\n");
        if (!agnprs.trim().isEmpty() || !prdprs.trim().isEmpty()) {
            sqlCommand.append("   and smahpr.agnprs || smahpr.prdprs = '" ).append( agnprs ).append( prdprs ).append( "'\n");
        }
        sqlCommand.append(" where codelm = 'PRF'\n")
                .append( "   and tipelm = 'HOUR'\n")
                .append( "   and swhelm = 1");

        model.list(sqlCommand.toString(), null);
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

            Map smapak = Util.map("smarpa", form);
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
                        + "                                                                                   p_nromat => '" + Util.validStr(form, "nromat") + "' )", sesion);
                smapak.put("codpak", codpak);

                nropak = model.saveLogBook(smapak, "smarpa", sesion);
                json.put("msg", model.MSG_SAVE);
            } else {
                model.updateLogBook(smapak, "smarpa", nropak, sesion);
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

                    String idesmt = "SMA_ACADEMIC_PROGRAM_PROJECT.semesteir_create( "
                            + "                                                p_codpsm => '" + codpsm + "', "
                            + "                                                p_nropkp => '" + nropkp + "' )";
                    idesmt = model.callFunctionOrProcedureNotTransaction(idesmt, sesion);

                    String idecrs = "SMA_ACADEMIC_PROGRAM_PROJECT.course_create( "
                            + "                                               p_codpsm => '" + codpsm + "', "
                            + "                                               p_idesmt => '" + idesmt + "' )";
                    idecrs = model.callFunctionOrProcedureNotTransaction(idecrs, sesion);

                    smagrp.put("idecrs", idecrs);
                }

                Map auxgrp = model.copy("smargr", "nropak='" + nropak + "'", sesion);
                if (auxgrp == null || auxgrp.isEmpty()) {
                    model.saveLogBook(smagrp, "smargr", sesion);
                } else {
                    model.updateLogBook(smagrp, "smargr", "nropak=~" + nropak + "~|", sesion);
                }

            }

            if (!codmdl.trim().isEmpty()) {

                Map smamat = model.copy("smamat", "codmat = '" + codmdl + "'", sesion);
                //Verificar que no exista el grupo integrada
                String nropakmdl = (String) model.getData("select nropak from smarpa where nromat = '"
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
                smamdl.put("stdpak", "Generado");
                smamdl.put("knppak", Util.validStr(smamat, "KNPMAT"));

                smagrp = new HashMap();
                smagrp.put("codpsm", codpsm);
                smagrp.put("idecrs", Util.validStr(form, "idecrs"));
                smagrp.put("nrogrp", "A1");
                smagrp.put("kpcgrp", Util.validStr(form, "kpcpak"));
                smagrp.put("stdgrp", "Generado");
                smagrp.put("tpogrp", obtenerTipoGrupo("NGR"));
                smagrp.put("tpomat", "NGR");

                //Si no existe entonces creo integrada
                if (nropakmdl.trim().isEmpty()) {
                    nropakmdl = model.saveLogBook(smamdl, "smarpa", sesion);
                    smagrp.put("nropak", nropakmdl);
                } else {
                    model.updateLogBook(smamdl, "nropak", nropakmdl, sesion);
                }

                String idegrpmdl = (String) model.getData("select idegrp from smargr where nropak = '"
                        + nropakmdl + "'", sesion);

                if (idegrpmdl.trim().isEmpty()) {
                    model.saveLogBook(smagrp, "smargr", sesion);
                } else {
                    model.updateLogBook(smagrp, "smargr", idegrpmdl, sesion);
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
        //json.put("nropkp","NA");
        if(nropkp == null || nropkp.isEmpty()){
            idepgm = Util.validStr(form, "idepgm");
            openSqlCommand();
            setSqlCommand("  SMA_ACADEMIC_PROGRAM_PROJECT.project_gen ( p_idesxu => '").append(model.getSessionSxu()).append("' ,\n").
                                                                append("p_idepgm => '").append(idepgm).append("' ,\n").
                                                                append("o_nropkp => ? ) ");
            
           Map mapResult = (HashMap) model.callStoredProcedure(getSqlCommand(), 2, null);

           nropkp = mapResult.get("1").toString();                                   
           form.put("nropkp", nropkp);
           //json.put("nropkp", nropkp);
           set("nropkp", nropkp, session);
        }
        
        Map datos = Util.map("smarsm", form);
        if(datos.get("nropkp") == null){
            datos.put("nropkp", nropkp);
        }
        datos.put("smtpsm", Util.validStr(form, "nropssShw"));
        datos.put("stdsmt", "Abierto");
        model.saveLogBook(datos, "smarsm", null);
        /*openSqlCommand();
        setSqlCommand("  SMA_ACADEMIC_PROGRAM_PROJECT.semesters_gen( p_idesxu => '").append(model.getSessionSxu()).append("' ,\n").
                                                             append("p_nropkp => '").append(nropkp).append("' ,\n").
                                                             append("p_idepgm => '").append(idepgm).append("' ,\n").
                                                             append("p_idesmt => '").append("").append("' ,\n").
                                                             append("p_nropsd => '").append("").append("' ,\n").
                                                             append("p_smtpsm => '").append("").append("' ,\n").
                                                             append("p_qrsppk => '").append("").append("' ,\n").
                                                             append("o_errors => ? )"); 
        */        
        json.put("exito", "OK");
        json.put("msg", model.MSG_SAVE);

        write(json);

    }

    private void getSmt() throws Exception {
        /*if(nropkp == null){
            nropkp = Util.getStrRequest("nropkp", request);
        }*/
        openSqlCommand();
        setSqlCommand("select smasmt.idesmt , \n");
        setSqlCommand("       smapss.smtpsm ,\n");
        setSqlCommand("       nompsd ,\n");
        setSqlCommand("       smapsd.nropsm ,\n");
        setSqlCommand("       nompgm\n");
        setSqlCommand("  from SMARSM smasmt\n");
        setSqlCommand("  join smapss\n");
        setSqlCommand("    on smapss.nropss = smasmt.nropss\n");
        setSqlCommand("  join smapsd\n");
        setSqlCommand("    on smapsd.nropsd = smapss.nropsd\n");
        setSqlCommand("  join smapgm\n");
        setSqlCommand("    on smapgm.idepgm = smapsd.idepgm\n");
        setSqlCommand(" where smasmt.nropkp = '" ).append( nropkp ).append( "'\n");
        setSqlCommand("  order by  nompgm  asc ,smtpsm asc");

        model.list(getSqlCommand(), null);

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

    private void showSemesters() throws SQLException, IOException {

        JSONObject json = new JSONObject();
        String idepgm = Util.getStrRequest("idepgm", request);
        String state = Optional.ofNullable(Util.getStrRequest("state", request)).orElse("PROJ")  ;
        openSqlCommand();
        /*if ("PROJ".equals(state)) {
            setSqlCommand("SMA_ACADEMIC_PROGRAM_PROJECT.project_semesters ");

        } else {
            setSqlCommand("sma_academic_programmer.copy_semesters ");

        }*/
        setSqlCommand("SMA_ACADEMIC_PROGRAM_PROJECT.project_semesters ");
        setSqlCommand("( p_codcia => '").append(model.getCodCia()).append("'\n");
        setSqlCommand(", p_codprs => '").append(model.getCodPrs()).append("'\n");
        setSqlCommand(", p_idepgm => '").append(idepgm).append("'\n");
        setSqlCommand(", o_smapkp => ? )");

        String columna = "IDESMT,NOMPRG,NROPGM,NOMSCN,JNDPGM,SMTPSM,NROPSM,STDPKP,NROCRS,IDEPGM,SAVE,VIEW";
        String title = "Id,Programa,Número,Sede,Jornada,Sem,Reforma,Estado,Cursos,Id_pgm,Proyectar,Consultar";
        try {

            List lista = model.listarSP(getSqlCommand(), new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(UtilConstantes.STR_COMA));
            tab.setTitles(title.split(UtilConstantes.STR_COMA));
            tab.setSelector("jqSmt_Proj");
            tab.setPaginador("jqSmtP_Proj");
            tab.setWidths(new int[]{0, 200, 40, 150, 60, 20, 120, 0, 80, 0, 60, 60});
            tab.setHiddens(new int[]{0, 7, 9});
            tab.setKeys(new int[]{0});
            tab.setDataList(lista);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "200");
            map.put("caption", "Semestres");
            map.put("multiselect", false);
            map.put("gridComplete", "gridPensum");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("6", "formatPensum");
            map.put("8", "setNumberCourse");

            if ("PROJ".equals(state)) {
                map.put("10", "setGen");
            }
            map.put("11", "setViewProject");

            tab.setFormatter(map);
            json.put("exito", "OK");
            json.put("idepgm", idepgm);
            json.put("semesters", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }finally{
            write(json);
        }
        
    }

    private void listSemesters() throws SQLException, IOException {

        JSONObject json = new JSONObject();
        String idepgm = Util.getStrRequest("idepgm", request);
        openSqlCommand();
        setSqlCommand("sma_academic_programmer.semesters( p_codcia => '").append(model.getCodCia()).append("'\n");
        setSqlCommand("                                 , p_codprs => '").append(model.getCodPrs()).append("'\n");
        setSqlCommand("                                 , p_idepgm => '").append(idepgm).append("'\n");
        setSqlCommand("                                 , o_smasmt => ? )");

        String columna = "SMTPSM,SEMPSM,IDESMT,SAVE,DELETE"; //,VIEW";
        String title = "No. Sem,Semestre,Id,Guardar,Eliminar";//,Consultar";
        try {

            List lista = model.listarSP(sqlCmd, new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(","));
            tab.setTitles(title.split(","));
            tab.setSelector("jqSem");
            tab.setWidths(new int[]{40, 140, 0, 60, 60, 60});
            tab.setHiddens(new int[]{0});
            tab.setKeys(new int[]{0});
            tab.setDataList(lista);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "300");
            map.put("caption", "Semestres");
            map.put("multiselect", true);
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("3", "setGenSemester");
            map.put("4", "setDelSemester");
//            map.put("11", "setViewProject");

            tab.setFormatter(map);
            json.put("exito", "OK");
            json.put("idepgm", idepgm);
            json.put("semestre", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }

    private String ComboSmt() throws IOException, Exception {
        openSqlCommand();
        setSqlCommand(" select smasmt.idesmt , smasmt.smtpsm  || ' - ' || nropsm || ' - '|| nompsd nomsmt\n");
        setSqlCommand("  from SMARSM smasmt\n");
        setSqlCommand("  join smapss\n");
        setSqlCommand("    on smapss.nropss = smasmt.nropss\n");
        setSqlCommand("  join smapsd\n");
        setSqlCommand("    on smapsd.nropsd = smapss.nropsd\n");
        setSqlCommand(" where smasmt.nropkp = '" ).append(nropkp ).append("'\n");
        setSqlCommand(" order by smasmt.smtpsm");

        inputForm combo = new inputForm();
        combo.setFilter(getSqlCommand());
        combo.setName("idesmt");
        combo.setType("select");
        combo.setTitle("Semestre para el curso");
        return combo.getHtml();

    }

    private void saveCrs() throws Exception {
        JSONObject form = Util.getJsonRequest("form", request);

        Map datos = Util.map("smarcr", form);

        String idesmt = Util.validStr(datos, "idesmt");
        String idecrs = Util.validStr(datos, "idecrs");

        if (idecrs.trim().isEmpty()) {

            String nrocrs = model.callFunctionOrProcedure("SMA_ACADEMIC_PROGRAM_PROJECT.course_sequence('" + idesmt + "')");
            String codcrs = model.callFunctionOrProcedure("SMA_ACADEMIC_PROGRAM_PROJECT.course_code('" + idesmt + "')");
            datos.put("nrocrs", nrocrs);
            datos.put("fchcrs", new Date());
            datos.put("codcrs", codcrs);
            datos.put("stdcrs", "Generado");

//                if ( Util.validStr(datos, "tpocrs").trim().equals(request) )
            model.saveLogBook(datos, "smarcr", null);
            json.put(UtilConstantes.STR_KEY_JSON_MSG, model.MSG_SAVE);
        } else {
            model.updateLogBook(datos, "smarcr", idecrs, null);
            json.put(UtilConstantes.STR_KEY_JSON_MSG, model.MSG_UPDATE);
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
        tab.setDataList(smacrs(UtilConstantes.STR_VACIO));
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
        openSqlCommand();
        setSqlCommand("SELECT smacrs.*,\n");
        setSqlCommand("       tpocrs nomtpo,\n");
        setSqlCommand("       nompsd\n");
        setSqlCommand(" FROM SMARCR smacrs\n");
        setSqlCommand(" JOIN SMARSM smasmt\n");
        setSqlCommand("   ON smasmt.idesmt = smacrs.idesmt \n");
        setSqlCommand(" JOIN smapss\n");
        setSqlCommand("   ON smapss.nropss = smasmt.nropss \n");
        setSqlCommand(" JOIN smapsd \n");
        setSqlCommand("   ON smapsd.nropsd = smapss.nropsd \n");
        setSqlCommand("WHERE smasmt.nropkp = '" ).append( nropkp ).append( "'\n");
//                    + "    and smacrs.tpocrs != 'Electiva'";

        if (!idecrs.trim().isEmpty()) {
            setSqlCommand(" AND smacrs.idecrs = '" ).append( idecrs ).append( "'");
        }

        setSqlCommand( " ORDER BY nomcrs asc,codcrs asc");

        model.list(getSqlCommand(), null);
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

        sqlCmd = "SMA_ACADEMIC_PROGRAM_PROJECT.course_delete( p_idecrs => '" + idecrs + "' , p_forze => " + cofirm + ")";
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
        Transaction transaccion = null;

        try {
            Session sesion = this.getNewSession();
            transaccion = HibernateUtil.getNewTransaction(sesion);
            transaccion.begin();
           
            model.deleteLogBook("smapxx", "nropak=~" + nropak + "~|", sesion);
            if (isPgm) {
                model.deleteLogBook("smargr", "nropak=~" + nropak + "~|", sesion);
            }
            model.deleteLogBook("smarpa", nropak, sesion);

            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);
            transaccion.commit();

            write(json);
        } catch (Exception e) {
            if(transaccion != null)
                transaccion.rollback();
            throw e;
        }

    }

    private void deleteSmt() throws Exception {
        String idesmt = Util.getStrRequest("a", request);
        openSqlCommand();
        setSqlCommand(" SELECT count( idegrp ) count \n");
        setSqlCommand("   FTOM SMARCR smacrs \n");
        setSqlCommand("   JOIN SMARGR smagrp \n");
        setSqlCommand("     ON smagrp.idecrs = smacrs.idecrs \n");
        setSqlCommand("  WHERE smacrs.idesmt = '" ).append( idesmt ).append( "'");

        sqlCmd = (String) model.getData(getSqlCommand(), null);
        if (Integer.parseInt(sqlCmd) > 0) {
            json.put("exito", "ERROR");
            json.put("msg", "Hay cursos con grupos creados en este semestre.No puede eliminar.");
            write(json);
            return;
        }

        model.deleteLogBook("smarsm", idesmt, null);

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
        openSqlCommand();
        setSqlCommand("SELECT * \n");
        setSqlCommand("  FROM table( SMA_ACADEMIC_PROGRAM_PROJECT.groups_detail(\n");
        setSqlCommand("                                           p_codcia => '" ).append( model.getCodCia() ).append( "',\n");
        setSqlCommand("                                           p_codprs => '" ).append( model.getCodPrs() ).append( "',\n");
        setSqlCommand("                                           p_idedks => '" ).append( idedks ).append( "',\n");
        setSqlCommand("                                           p_agnprs => '" ).append( agnprs ).append( "',\n");
        setSqlCommand("                                           p_prdprs => '" ).append( prdprs ).append( "',\n");
        setSqlCommand("                                           p_type => 'LBR') )");
        setSqlCommand("  order by smtpsm asc");

        model.list(getSqlCommand(), null);

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
        openSqlCommand();
        setSqlCommand("select smaelc.* , codmat || ' - ' || nommat || ' - Sem. ' || smtpsm || ' Pensum: ' || nompsd  nommatelc\n");
        setSqlCommand("  from table( SMA_ACADEMIC_PROGRAM_PROJECT.elective_data( p_nropkp => ' " ).append( nropkp ).append( "' , \n");
        setSqlCommand("                               p_codcia => '" ).append( model.getCodCia() ).append( "' , ");
        setSqlCommand("                               p_codprs => '" ).append( model.getCodPrs() ).append( "' ) ) smaelc ");
     
        model.list(getSqlCommand(), null);

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

        Map data = Util.map("smapxx", form);
        String nropxg = Util.validStr(form, "nropxx");

        if (nropxg.trim().isEmpty()) {
            model.saveLogBook(data, "smapxx", null);
            json.put("msg", model.MSG_SAVE);
        } else {
            model.updateLogBook(data, "smapxx", nropxg, null);
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
    
    private List smapxx(String nropak, String nropxg) throws SQLException {

        sqlCmd = "select smapxx.* , smaprs.apeprs || ' '  || smaprs.nomprs nombre , smaprs.nriprs\n"
                + "  from smapxx\n"
                + "  join smaprf\n"
                + "    on smaprf.nroprf = smapxx.nroprf\n"
                + "  join smaprs\n"
                + "    on smaprs.nroprs = smaprf.nroprs\n"
                + " where smapxx.nropak = '" + nropak + "'";

        if (!nropxg.trim().isEmpty()) {
            sqlCmd += " and smapxx.nropxg = '" + nropxg + "'";
        }
        model.list(sqlCmd, null);
        return model.getList();

    }

    private void lstPxg() throws Exception {
        String nropak = Util.getStrRequest("a", request);

        List data = smapxx(nropak, "");

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

        model.deleteLogBook("smapxx", nropxg, null);

        json.put("exito", "OK");
        json.put("msg", model.MSG_DELETE);

        write(json);
    }

    private void detailPxg() throws Exception {
        String nropxg = Util.getStrRequest("a", request);
        String nropak = Util.getStrRequest("b", request);

        List data = smapxx(nropak, nropxg);
        Map smapxx = new HashMap();
        if (!data.isEmpty()) {
            smapxx = ((HashMap) model.getList().get(0));
        }

        json.put("exito", "OK");
        json.put("smapxg", smapxx);

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

    private void listPrograms() throws SQLException, IOException {

        json = new JSONObject();
        openSqlCommand();
        
        setSqlCommand("SMA_ACADEMIC_PROGRAM_PROJECT.project_programs( p_codcia => '").append(model.getCodCia()).append("'\n");
        setSqlCommand("                                        , p_codprs => '").append(model.getCodPrs()).append("'\n");
        setSqlCommand("                                        , o_smaapj => ? )");

        //String columna = "IDEPGM,NROPKP,NOMPRG,NROPGM,NOMPGM,NOMSCN,JNDPGM,SAVE,COPY,VIEW,EDIT,STDPKP,FCIPKP,FCVPKP,IDEDKS,CODPKP";
        //String title = "Id,Id_Prog,Programa,Código,Prog-sede-Jorn,Sede,Jornada,Proyectar,Copiar,Consultar, Edit, STDPKP, fcipkp, fcvpkp, idedks, TITULO";
          String columna = "IDEPGM,NROPKP,NOMPRG,NROPGM,NOMPGM,NOMSCN,JNDPGM,SAVE,EDIT,STDPKP,FCIPKP,FCVPKP,IDEDKS";
          String title = "Id,Id_Prog,Programa,Código,Prog-sede-Jorn,Sede,Jornada,Solicitar, Edit, stdpkp, fcipkp, fcvpkp, idedks";
        try {
            List lista = model.listarSP(getSqlCommand(), new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(UtilConstantes.STR_COMA));
            tab.setTitles(title.split(UtilConstantes.STR_COMA));
            /*tab.setSelector("jqPtj");
             tab.setPaginador("jqPtjP");*/
            tab.setSelector("jqPgm");
            //tab.setFilterToolbar(true);
            tab.setPaginador("pagerPgm");
            //tab.setWidths(new int[]{0, 0, 350, 80, 250, 280, 60, 60, 60, 60, 60});
            tab.setWidths(new int[]{0, 0, 350, 80, 250, 280, 60, 60, 60});
            tab.setHiddens(new int[]{0, 1, 9, 10, 11, 12});
            //tab.setHiddens(new int[]{0, 1, 10, 11, 12, 13});
            tab.setKeys(new int[]{0});
            tab.setDataList(lista);
            tab.setGroupFields("NOMPRG");
            tab.setGroupText("<b><i>Programa : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false});
            tab.setGroupOrder("asc");
            tab.setFormatDate(new int[]{10, 11});

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "500");
            map.put("caption", "Programas Asignados");
            tab.setOptions(map);

            map = new LinkedHashMap();
            /*map.put("7", "setGenProject");
            map.put("8", "setCopProject");
            map.put("9", "setVieProject");
            map.put("10", "setEditProject");*/

            map.put("7", "setGenProject");            
            map.put("8", "setEditProject");

            tab.setFormatter(map);
            json.put("exito", "OK");
            json.put("html", tab.getHtml());
            write(json);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
    }

    private void showPrograms() throws Exception {
        String ag = Util.getStrRequest("agnprs", request);
        String pr = Util.getStrRequest("prdprs", request);

        if (!ag.trim().isEmpty()) {
            agnprs = ag;
            prdprs = pr;
            set("agnprs", ag, session);
            set("prdprs", pr, session);
        }

        sqlCmd = "sma_academic_planning_manager.programs( p_codcia => '" + model.getCodCia() + "' \n"
                + "                                      , p_codprs => '" + model.getCodPrs() + "' \n"
                + "                                      , p_agnprs => '" + agnprs + "'\n"
                + "                                      , p_prdprs => '" + prdprs + "'\n"
                + "                                      , o_smapgm => ? )";

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
        tab.setFormatDate(new int[]{7, 8});

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

        sqlCmd = "sma_academic_planning_manager.modules_conf( p_codcia => '" + model.getCodCia() + "' \n"
                + "                                          , p_codprs => '" + model.getCodPrs() + "' \n"
                + "                                          , p_idepgm => '" + idepgm + "' \n"
                + "                                          , o_smamai  => ? )";

        List lista = model.listarSP(sqlCmd, new Object[]{});

        jQgridTab tab = new jQgridTab();
        tab.setColumns(new String[]{"NROMAI", "CODPSM", "SMTPSM", "NROPSM", "NOMPSD", "CODMAT", "NOMMAT", "CODMOD", "NOMMOD", "PTJMAI", "SAVE", "DEL"});
        tab.setTitles(new String[]{"", "", "No", "Sem", "Pensum", "Código", "Asignatura", "Cód.Mod", "Módulo", "Porcentaje (%)", "Guarda", "Del."});
        tab.setWidths(new int[]{0, 0, 40, 50, 250, 100, 280, 100, 280, 70, 60});
        tab.setHiddens(new int[]{0, 1});
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

        JSONObject data;
        JSONArray jsonParr = Util.getJArrayRequest("json", request);
        String nromai = "";
        String ptjmai = "";

        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);

        try {

            tra.begin();
            for (Object o : jsonParr) {
                data = (JSONObject) o;
                nromai = data.get("mdl").toString();
                ptjmai = data.get("ptj").toString();
                sqlCmd = "sma_academic_planning_manager.modules_update( p_idesxu => '" + model.getSessionSxu() + "' \n"
                        + "                                            , p_nromai => '" + nromai + "' \n"
                        + "                                            , p_ptjmai =>  " + ptjmai + " )";
                model.callProcedure(sqlCmd, sesion);
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

    private void getCalendario() throws IOException {
        JSONObject json = new JSONObject();
        List lstDta = new LinkedList();
        try {
            lstDta = getDatosCalendario("PKP");
            if (lstDta.isEmpty()) {
                throw new ClsmaException(ClsmaTypeException.ERROR, "La actividad de PROYECCION ACADEMICA (PKP) no se encuentra activa");
            }
            //smaTmp = (Hashtable) list.get(0);
            String agnprs = ((Hashtable) (lstDta.get(0))).get("AGNPRS").toString();
            String prdprs = ((Hashtable) (lstDta.get(0))).get("PRDPRS").toString();
            json.put("agnprs", agnprs);
            json.put("prdprs", prdprs);
            set("agnprs", agnprs, session);
            set("prdprs", prdprs, session);
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, "OK");
        } catch (ClsmaException cle) {
            cle.writeJsonError(json);
        } catch (Exception e) {
            Util.logError(e);
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, "ERROR");
            json.put(UtilConstantes.STR_KEY_JSON_MSG, model.MSG_ERROR);
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    private List<LinkedHashMap> getDatosCalendario(String codaxk) throws Exception {
        sqlCmd = "select distinct agnprs,prdprs from table (sma_academic.calendar_activity_all('" + model.getCodCia() + "','" + model.getCodPrs() + "','" + codaxk + "')) order by agnprs,prdprs";

        model.listGenericHash(sqlCmd);
        List<LinkedHashMap> lista = model.getList();
        return lista;
    }

    private void genSemesterProject() throws Exception {

        String idepgm = Util.getStrRequest("pgm", request);
        String nropsd = Util.getStrRequest("psm", request);
        String smtpsm = Util.getStrRequest("sem", request);
        String idesmt = Util.getStrRequest("id", request);
        String qrsppk = Util.getStrRequest("qrs", request);

        Session sesion = this.getNewSession();
        Transaction trans = null;
        try {
            trans = HibernateUtil.getNewTransaction(sesion);
            trans.begin();
            openSqlCommand();
            setSqlCommand("SMA_ACADEMIC_PROGRAM_PROJECT.semesters_gen( p_idesxu => '" ).append( model.getSessionSxu() ).append( "' \n");
            setSqlCommand("                                         , p_idepgm => '" ).append( idepgm ).append( "' \n");
            setSqlCommand("                                         , p_idesmt => '" ).append( idesmt ).append( "' \n");
            setSqlCommand("                                         , p_nropsd => '" ).append( nropsd ).append( "' \n");
            setSqlCommand("                                         , p_smtpsm => '" ).append( smtpsm ).append( "' \n");
            setSqlCommand("                                         , p_qrsppk =>  " ).append( qrsppk ).append( " \n");
            setSqlCommand("                                         , o_errors => ? ");
            setSqlCommand("                                         , o_nropkp => ? )");


            Map mapResult = (HashMap) model.callStoredProcedure(getSqlCommand(), 2, null);

            String error = mapResult.get("1").toString();
            if (!error.toLowerCase().contains("exito")) {
                json.put("fnc", UtilConstantes.STR_VACIO);
                throw new ClsmaException(ClsmaTypeException.ERROR, error);                                                                
            } 
            String vNropkp = mapResult.get("2").toString();
            if(vNropkp != null){
                set("nropkp", vNropkp, session);
            }
            json.put("exito", "OK");
            json.put("msg", "La proyección de la programación se ha realizado exitosamente!");
            trans.commit();
            
        } catch(ClsmaException ex){
            if(trans != null)
                trans.rollback();
            ex.writeJsonError(json);            
            
        } catch (Exception e) {
            if(trans != null)
                trans.rollback();
            Util.logError(e);
            json.put("exito", ClsmaTypeException.ERROR.toString());
            json.put("msg", model.setError(e));
        }finally{
            write(json);
        }
        
    }
    
     public void write(Object jsonValue) throws IOException {
        response.getWriter().write(JSONValue.toJSONString(jsonValue));
    }

}
