package mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import mvc.model.HibernateUtil;
import mvc.model.ModelSma;
import mvc.model.Util;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.hibernate.Transaction;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @Author Ing.J.Llanos Cerro
 * @Fecha
 */
public class AdmAcademicProgramProjectAction extends Action {

    private String nxtPge;
    private String sqlCmd;
    private String events;
    private String codkls; 
    PrintWriter writer;
    private List list;
    private Hashtable smaTmp;
 
    public void run() throws ServletException, IOException {
        events = Util.validStr(request.getParameter("events"));
        writer = response.getWriter();
        try {
            if (events.equals("GETCAL")) {
                getCalendario();
                return;
            } else if (events.equals("LIST")) {
                showPrograms();
                return ;
            } else if (events.equals("SHOWSEM")) {
                showSemesters();
                return;
            } else if (events.equals("SHOWCRS")) {
                showCourses();
                return;
            } else if (events.equals("PROJECT")) {
                genProject();
                return;
            } else if (events.equals("VIEWPPK")) {
                showGenerated();
                return;
            } else if (events.equals("DELPPK")) {
                delProjected();
                return;
            } else if (events.equals("PROJECTS")) {
                genProjects();
                return;
            } else if (events.equals("COPY")) {
                genCopy();
                return;
            } else if (events.equals("COPIES")) {
                genCopySelected();
                return;
            } else if (events.equals("GETKLS")) {
                getKls();
                return;
            } else if (events.equals("GETPGMKLS")) {
                getPgmkls();
                return;
            } else if (events.equals("SAVE")) {
                saveProject();
                return;
            } else if (events.equals("SHOW")) {
                projected();
                return ;
            } else if (events.equals("DELGRP")) {
                delGroup();
                return ;
            } else if (events.equals("DELSEM")) {
                delSemester();
                return ;
            }
            rd.forward(request, response);
        } catch (Exception e) {
            Util.logError(e);
        }
    }

       private void getCalendario() throws IOException {
        JSONObject json = new JSONObject();
        List lstDta = new LinkedList();
        try {
            lstDta = getDatosCalendario("PKP");
            if (!lstDta.isEmpty()) {
               //smaTmp = (Hashtable) list.get(0);
                String agnprs = ((Hashtable) (lstDta.get(0))).get("AGNPRS").toString();
                String prdprs = ((Hashtable) (lstDta.get(0))).get("PRDPRS").toString();
                json.put("agnprs", agnprs);
                json.put("prdprs", prdprs);
                json.put("exito", "OK");
            } else {
                json.put("exito", "ERROR");
                json.put("msg", "La actividad de PROYECCION ACADEMICA (PKP) no se encuentra activa");
            }
        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.MSG_ERROR);
        }
        
        response.getWriter().write(JSONValue.toJSONString(json));
    }
       
    private void getKls() throws SQLException, IOException {
        String codkls = (request.getParameter("codkls") != null) ? request.getParameter("codkls").trim() : "";
        Hashtable smaTmp = new Hashtable();

        sqlCmd = "select agnprs, "
                + " prdprs "
                + " from table(sma_academic.calendar_activity('" + model.getCodCia() + "' , '" + model.getCodPrs() + "' ,'ALL', "
                + " '" + "PKP" + "' )) "
                + " where codkls = '" + codkls + "'"
                + " order by agnprs desc, prdprs desc ";

        model.listGenericHash(sqlCmd);

        if (!model.getList().isEmpty()) {
            smaTmp = (Hashtable) model.getList().get(0);
        }

        response.getWriter().write(JSONValue.toJSONString(smaTmp));
    }
    
    private void getPgmkls() throws IOException, SQLException, Exception {

        JSONObject json = new JSONObject();

        try {
            String codkls = (request.getParameter("codkls") != null) ? request.getParameter("codkls").trim() : "";

            sqlCmd = "select * from table (sma_academic_program.calendar_programs('" + codkls + "'))";
            model.listGenericHash(sqlCmd);

            jQgridTab jq = new jQgridTab();
            jq.setModel(model);
            jq.setTitles(new String[]{"", "Nro Programa", "Nombre Programa", "Tipo", "Jornada", "Modalidad", "Calendario", ""});
            jq.setColumns(new String[]{"IDEPGM", "NROPGM", "NOMPGM", "TPOPGM", "JNDPGM", "STGPGM", "NOMKLS", "CODKLS"});
            jq.setSelector("jqTab");
            jq.setDataList(model.getList());
            jq.setPaginador("pagerTab");
//            jq.setGroupFields("KLSIN");
//            jq.setGroupText("<b>Calendario : {0}</b>");

            jq.setWidths(new int[]{0, 100, 350, 100, 100, 100, 300, 0});
            jq.setHiddens(new int[]{0, 7, 6});

            LinkedHashMap option = new LinkedHashMap();
            option.put("onSelectRow", "chkPgm");
            option.put("width", "auto");
            option.put("caption", null);

            jq.setOptions(option);

            json.put("exito", "OK");
            json.put("html", jq.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("html", model.MSG_ERROR);

        }

        response.getWriter().write(JSONValue.toJSONString(json));
    }

        private void showPrograms()
              throws SQLException, IOException {

            sqlCmd = "";
        list = new LinkedList();

        JSONObject json = new JSONObject();
//        String agnprs = Util.getStrRequest("agnprs", request);
//        String prdprs = Util.getStrRequest("prdprs", request);
 
        sqlCmd = "sma_academic_programmer.project_programs( p_codcia => '" + model.getCodCia() + "'\n" +
                 "                                        , p_codprs => '" + model.getCodPrs() + "'\n" +
                 "                                        , o_smapkp => ? )";

//        String columna = "NROPKP,AGNPRS,PRDPRS,NOMPGM,NOMPKP,IDEPGM,NROPGM,STDPKP,NOMSCN,SAVE,VIEW";
//        String title   = "Id,Año,período,Programa,Proyección,Código,Id Pgm,Estado,Sede,Proyectar,Consultar";

        String columna = "IDEPGM,NROPKP,NOMPRG,NROPGM,NOMPGM,NOMSCN,JNDPGM,SAVE,COPY,VIEW";
        String title   = "Id,Id_Prog,Programa,Código,Prog-sede-Jorn,Sede,Jornada,Proyectar,Copiar,Consultar";
        try { 
            List lista = model.listarSP(sqlCmd, new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(","));
            tab.setTitles(title.split(","));
            tab.setSelector("jqPtj");
            tab.setPaginador("jqPtjP");
//            tab.setWidths(new int[]{0, 40, 20, 300, 300, 50, 0, 80, 300, 60, 60, 60});
//            tab.setHiddens(new int[]{0, 5});
            tab.setWidths(new int[]{0, 0, 350, 80, 250, 280, 60, 60,60});
            tab.setHiddens(new int[]{0, 1});
            tab.setKeys(new int[]{1});
            tab.setDataList(lista);
            tab.setGroupFields("NOMPRG");
            tab.setGroupText("<b><i>Programa : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false});
            tab.setGroupOrder("asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "550");
            map.put("caption", "Programas");
//            map.put("multiselect", true);
//            map.put("loadComplete", "selCompleteValues");
            tab.setOptions(map);

            map = new LinkedHashMap();
//            map.put("6", "formatoSave");
            map.put("7", "setGenProject");
            map.put("8", "setCopProject");
            map.put("9", "setVieProject");

            tab.setFormatter(map);
            json.put("exito", "OK");
            json.put("project", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter()
                  .write(JSONValue.toJSONString(json));

    }


        private void showSemesters()
              throws SQLException, IOException {

            sqlCmd = "";
        list = new LinkedList();
 
        JSONObject json = new JSONObject();
//        String agnprs = Util.getStrRequest("agnprs", request);
//        String prdprs = Util.getStrRequest("prdprs", request);
        String idepgm = Util.getStrRequest("idepgm", request);
        String state = Util.getStrRequest("state", request);
 
        if(state.equals("PROJ")){
            sqlCmd = "sma_academic_programmer.project_semesters( p_codcia => '" + model.getCodCia() + "'\n" +
                     "                                         , p_codprs => '" + model.getCodPrs() + "'\n" +
                     "                                         , p_idepgm => '" + idepgm + "'\n" +
                     "                                         , o_smapkp => ? )";
        }else{
            sqlCmd = "sma_academic_programmer.copy_semesters( p_codcia => '" + model.getCodCia() + "'\n" +
                     "                                      , p_codprs => '" + model.getCodPrs() + "'\n" +
                     "                                      , p_idepgm => '" + idepgm + "'\n" +
                     "                                      , o_smapkp => ? )";
        }
        
        String columna = "IDESMT,NOMPRG,NROPGM,NOMSCN,JNDPGM,SMTPSM,NROPSM,STDPKP,NROCRS,IDEPGM,SAVE,COPY,VIEW";
        String title   = "Id,Programa,Número,Sede,Jornada,Sem,Reforma,Estado,Cursos,Id_pgm,Proyectar,Copiar,Consultar";
        try {

            List lista = model.listarSP(sqlCmd, new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(","));
            tab.setTitles(title.split(","));
            tab.setSelector("jqSmt");
            tab.setPaginador("jqSmtP");
            tab.setWidths(new int[]{0, 300, 40, 150, 60, 20, 120, 0, 80, 0, 60, 60, 60});
            tab.setHiddens(new int[]{0,7,9});
            tab.setKeys(new int[]{0});
            tab.setDataList(lista);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "270");
            map.put("caption", "Semestres");
            map.put("multiselect", true);
            map.put("gridComplete", "gridPensum");
            tab.setOptions(map);

            map = new LinkedHashMap();
//            if(state.equals("COPY")){
               map.put("6", "formatPensum");
//            }
            map.put("8", "setNumberCourse");
            if(state.equals("PROJ")){
               map.put("10", "setGen"); 
            }else{
               map.put("11", "setCopyProject");
            }
               map.put("12", "setViewProject");

            tab.setFormatter(map);
            json.put("exito", "OK");
            json.put("idepgm", idepgm);
            json.put("semesters", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter()
                  .write(JSONValue.toJSONString(json));

    }
            
        private void listSemesters()
              throws SQLException, IOException {

            sqlCmd = "";
        list = new LinkedList();

        JSONObject json = new JSONObject();
        String idepgm = Util.getStrRequest("idepgm", request);

        sqlCmd = "sma_academic_programmer.semesters( p_codcia => '" + model.getCodCia() + "'\n" +
                 "                                 , p_codprs => '" + model.getCodPrs() + "'\n" +
                 "                                 , p_idepgm => '" + idepgm + "'\n" +
                 "                                 , o_smasmt => ? )";

        String columna = "SMTPSM,SEMPSM,IDESMT,SAVE,DELETE"; //,VIEW";
        String title   = "No. Sem,Semestre,Id,Guardar,Eliminar";//,Consultar";
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
//            tab.setGroupFields("NOMPGM,SMTPSM");
//            tab.setGroupText("<b><i>Programa : {0}</i></b>,<b><i>Semestre : {0}</i></b>");
//            tab.setGroupCollapse(false);
//            tab.setGroupColumnShow(new Boolean[]{false, true});
//            tab.setGroupOrder("asc,asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center"); 
            map.put("height", "300");
            map.put("caption", "Semestres");
            map.put("multiselect", true);
//            map.put("loadComplete", "selCompleteValues");
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
    

        private void showCourses()
              throws SQLException, IOException {

            sqlCmd = "";
//        list = new LinkedList();
        
        JSONObject data ;
/*        String smt = "";
        String strsmt = "";
        JSONArray jsonParr = Util.getJArrayRequest("json", request);
        
        for (Object o : jsonParr ) {
            data = (JSONObject) o;
            smt = data.get("smtpsm").toString();
            strsmt += smt + "," ;
        }*/
        JSONObject json = new JSONObject();
        //String idepgm = Util.getStrRequest("idepgm", request);
        String idesmt = Util.getStrRequest("idesmt", request);

        sqlCmd = "sma_academic_programmer.courses( p_codcia => '" + model.getCodCia() + "'\n" +
                 "                               , p_codprs => '" + model.getCodPrs() + "'\n" +
                 "                               , p_idesmt => '" + idesmt + "'\n" +
                 "                               , o_smacrs => ? )";

        String columna = "IDECRS,NOMPGM,SMTPSM,NROCRS,NROMAT,CODMAT,NOMMAT,CODPAK,NRIPRS,APEPRS,NOMPRS,IDEGRP,NROPSD,DELETE,NROPAK"; //,NRIPRS,NOMPRF"; 
        String title   = "Id_curso,Programa,Sem,Curso,Id_Mat,Código,Materia,Grupo,Id_Doc,Apellidos,Nombres,Id_grup,Pensum,Eliminar,IdPak"; //,Cédula,Docente"; 
        try {
 
            List lista = model.listarSP(sqlCmd, new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(","));
            tab.setTitles(title.split(","));
            tab.setSelector("jqCrs");
            tab.setPaginador("jqCrsP");
            tab.setWidths(new int[]{0, 300, 60, 40, 0, 120, 300, 70, 80, 150, 150, 0, 0, 0, 0 });
            tab.setHiddens(new int[]{0,11,12,13,14});
            tab.setKeys(new int[]{11});
            tab.setDataList(lista);
            tab.setGroupFields("NOMPGM,SMTPSM,NROCRS");
            tab.setGroupText("<b><i>Programa : {0}</i></b>,<b><i>Semestre : {0}</i></b>,<b><i>Curso : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false, false, false});
            tab.setGroupOrder("asc,asc,asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "250");
            map.put("caption", "Cursos");
//            map.put("multiselect", true);
            map.put("rownumbers", true);
//            map.put("onSelectRow", "activate");
//            map.put("onSelectAll", "activateAll");
//            map.put("loadComplete", "selectCourses");
            tab.setOptions(map);
/*
            map = new LinkedHashMap();
//            map.put("3", "setGenSemester");
//            map.put("9", "setGenProject");
            map.put("13", "setDelGroup");
//            map.put("11", "setViewProject");
            tab.setFormatter(map);
*/
            json.put("exito", "OK");
            json.put("cursos", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }        
        
   private List<LinkedHashMap> getDatosCalendario(String codaxk) throws Exception {
        sqlCmd = "select distinct agnprs,prdprs from table (sma_academic.calendar_activity_all('" + model.getCodCia() + "','" + model.getCodPrs() + "','" + codaxk + "')) order by agnprs,prdprs";

        model.listGenericHash(sqlCmd);
        List<LinkedHashMap> lista = model.getList();
        return lista;
    }        
        

    private void saveProject() throws Exception {
        
//        JSONArray arrSel = (JSONArray) JSONValue.parse(Util.getStrRequest("select", request));
        JSONArray arrSel = Util.getJArrayRequest("select", request);
        JSONObject json = new JSONObject();
        Session sesion = this.getNewSession();
        Transaction tans = HibernateUtil.getNewTransaction(sesion);
        JSONObject data ;

        Map datos = null;

        try {

            tans.begin();
            String idepgm = Util.getStrRequest("idepgm", request);
            String agnprs = Util.getStrRequest("agnprs", request);
            String prdprs = Util.getStrRequest("prdprs", request);
            String smtpsm = "";
            String nropsd = "";
            String nromat = "";
            String states = "";
//            String strpsd = "";
//            String strmat = "";
            String strsmt = "";
            
            for (Object o : arrSel) {
                 data = (JSONObject) o;
                 nropsd = data.get("nropsd").toString();
                 nromat = data.get("nromat").toString();
                 smtpsm = data.get("smtpsm").toString();
                 if(!strsmt.equals(smtpsm)){
                   states = "init";
                 }
                 strsmt = smtpsm;

                sqlCmd = "sma_academic_programmer.project( p_idesxu => '" + model.getSessionSxu()+ "' \n" +
                         "                               , p_idepgm => '" + idepgm + "' \n" +
                         "                               , p_agnprs => '" + agnprs + "' \n" +
                         "                               , p_prdprs => '" + prdprs + "' \n" +
                         "                               , p_nropsd => '" + nropsd + "' \n" +
                         "                               , p_smtpsm => '" + smtpsm + "' \n" +
                         "                               , p_nromat => '" + nromat + "' \n" +
                         "                               , p_stdpkp => '" + states + "' \n" +
                         "                               , o_nropkp => ?  \n" +
                         "                               , o_nropak => ? )";
                states = "next";
    
                Map m = (HashMap) model.callStoredProcedure(sqlCmd, 2, null);
            }
            tans.commit();

            json.put("exito", "OK");
            json.put("msg", ModelSma.MSG_SAVE);
        } catch (Exception e) {
            tans.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }

        response.getWriter().write(JSONValue.toJSONString(json));

    }        


    private void projected()
              throws SQLException, IOException {

            sqlCmd = "";
        list = new LinkedList();
 
        JSONObject json = new JSONObject();
        String idepgm = Util.getStrRequest("idepgm", request);
        String nropkp = Util.getStrRequest("nropkp", request);
        sqlCmd = "sma_academic_programmer.projected( p_codcia => '" + model.getCodCia() + "'\n" +
                 "                                 , p_codprs => '" + model.getCodPrs() + "'\n" +
                 "                                 , p_idepgm => '" + idepgm + "'\n" +
                 "                                 , p_nropkp => '" + nropkp + "'\n" +
                 "                                 , o_smapkp => ? )";

        String columna = "NROPKP,NOMPGM,SMTPSM,AGNPRS,PRDPRS,NROPGM,STDPKP,NOMSCN,NROCRS,IDECRS,IDEGRP,CODMAT,NOMMAT,CODPAK,EDIT,DELETE,VIEW";
        String title   = "Id,Programa,Sem,Año,Período,Número,Estado,Sede,Curso,ID_Curso,Id_grp,Código,Materia,Grupo,Guardar,Eliminar,Consultar";
        try {
 
            List lista = model.listarSP(sqlCmd, new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(","));
            tab.setTitles(title.split(","));
            tab.setSelector("jqPtjed");
            tab.setPaginador("jqPtjedP");
            tab.setWidths(new int[]{0, 0, 0, 40, 20, 50, 80, 150, 40, 0, 0, 100, 300, 60, 0, 60, 0});
            tab.setHiddens(new int[]{0,9,14,16});
            tab.setKeys(new int[]{1});
            tab.setDataList(lista);
            tab.setGroupFields("NOMPGM,SMTPSM,NROCRS");
            tab.setGroupText("<b><i>Programa : {0}</i></b>,<b><i>Semestre : {0}</i></b>,<b><i>Curso : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false, true, true});
            tab.setGroupOrder("asc,asc,asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "550");
            map.put("caption", "Programación");
//            map.put("multiselect", true);
//            map.put("loadComplete", "selCompleteValues");
            tab.setOptions(map);

            map = new LinkedHashMap();
/*            map.put("6", "formatoSave");
            map.put("9", "setGenProject");
            map.put("10", "setDelproject");*/
            map.put("15", "setDeleteGroup");
            tab.setFormatter(map);

            json.put("exito", "OK");
            json.put("project", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter()
                  .write(JSONValue.toJSONString(json));

    }

    private void delGroup() throws IOException {


        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction((Session) sesion);
        JSONObject json = new JSONObject();

        String idesxu = model.getSessionSxu();
        String idegrp = Util.getStrRequest("id", request);
        String o_errors = "";

        sqlCmd = "sma_academic_programmer.group_delete( p_idesxu => '" + idesxu + "'\n" +
                 "                                    , p_idegrp => '" + idegrp + "'\n" +
                 "                                    , o_errors => ? )";

        try {
             tra.begin();

             Map m = (HashMap) model.callStoredProcedure(sqlCmd, 3, sesion);
             o_errors = m.get("1").toString();
             if (!o_errors.toLowerCase().contains("exito")) {
                json.put("exito", "ERROR");
                json.put("fnc", "");
                json.put("msg", "Error al Eliminar el grupo.");
                tra.rollback();
             }
            json.put("msg", "El registro se ha eliminado exitosamente!");
            json.put("fnc", "reload()");
            json.put("exito", "OK");
            tra.commit();
        } catch (Exception e) {
            tra.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }

    private void delSemester() throws IOException {


        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction((Session) sesion);
        JSONObject json = new JSONObject();

        String idesxu = model.getSessionSxu();
        String idesmt = Util.getStrRequest("id", request);
        String o_errors = "";

        sqlCmd = "sma_academic_programmer.semester_delete( p_idesxu => '" + idesxu + "'\n" +
                 "                                       , p_idesmt => '" + idesmt + "'\n" +
                 "                                       , o_errors => ? )";

        try {
             tra.begin();

             Map m = (HashMap) model.callStoredProcedure(sqlCmd, 3, sesion);
             o_errors = m.get("1").toString();
             if (!o_errors.toLowerCase().contains("exito")) {
                json.put("exito", "ERROR");
                json.put("fnc", "");
                json.put("msg", "Error al Eliminar el grupo.");
                tra.rollback();
             }
            json.put("msg", "El registro se ha eliminado exitosamente!");
            json.put("fnc", "reload()");
            json.put("exito", "OK");
            tra.commit();
        } catch (Exception e) {
            tra.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }




    private void genProject() throws Exception {
        
//        JSONArray arrSel = (JSONArray) JSONValue.parse(Util.getStrRequest("select", request));

//        Integer i_qrsppk = Integer.parseInt(Util.getStrRequest("grs", request)); 
        String idepgm = Util.getStrRequest("pgm" , request);
        String nropsd = Util.getStrRequest("psm", request);
        String smtpsm = Util.getStrRequest("sem", request);
        String idesmt = Util.getStrRequest("id" , request);
        String qrsppk = Util.getStrRequest("qrs", request);
        
        JSONObject json = new JSONObject();
        Session sesion = this.getNewSession();
        Transaction trans = HibernateUtil.getNewTransaction(sesion);
//        JSONObject data ;

//        Map datos = null;

        try {

            trans.begin();

            sqlCmd = "sma_academic_programmer.semesters_gen( p_idesxu => '" + model.getSessionSxu()+ "' \n" +
                     "                                     , p_idepgm => '" + idepgm + "' \n" +
                     "                                     , p_idesmt => '" + idesmt + "' \n" +
                     "                                     , p_nropsd => '" + nropsd + "' \n" +
                     "                                     , p_smtpsm => '" + smtpsm + "' \n" +
                     "                                     , p_qrsppk =>  " + qrsppk + " \n" +
                     "                                     , o_errors => ? )";
                 
            Map m = (HashMap) model.callStoredProcedure(sqlCmd, 2, null);

            String error = m.get("1").toString();
            if (!error.toLowerCase().contains("exito")) {
                json.put("exito", "ERROR");
                json.put("fnc", "");
                json.put("msg", error);
                trans.rollback();
            } else {
                json.put("exito", "OK");
                json.put("msg", "La proyección de la programación se ha realizado exitosamente!" );
                trans.commit();
            } 
        } catch (Exception e) {
            trans.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }        


    private void genProjects() throws Exception {
        
//        JSONArray arrSel = (JSONArray) JSONValue.parse(Util.getStrRequest("select", request));
        JSONArray arrSel = Util.getJArrayRequest("select", request);
        JSONObject json = new JSONObject();
        Session sesion = this.getNewSession();
        Transaction trans = HibernateUtil.getNewTransaction(sesion);
        JSONObject data ;

        try {

            trans.begin();
            String idesmt = "";
            String qrsppk = "";
            String idepgm = "";
            String smtpsm = "";
            String nropsd = "";
            String errors = "";
            
            for (Object o : arrSel) {
                 data = (JSONObject) o;
                 idepgm = data.get("pgm").toString();
                 nropsd = data.get("psm").toString();
                 smtpsm = data.get("sem").toString();
                 idesmt = data.get("id").toString();
                 qrsppk = data.get("qrs").toString();

                sqlCmd = "sma_academic_programmer.semesters_gen( p_idesxu => '" + model.getSessionSxu()+ "' \n" +
                         "                                     , p_idepgm => '" + idepgm + "' \n" +
                         "                                     , p_idesmt => '" + idesmt + "' \n" +
                         "                                     , p_nropsd => '" + nropsd + "' \n" +
                         "                                     , p_smtpsm => '" + smtpsm + "' \n" +
                         "                                     , p_qrsppk =>  " + qrsppk + " \n" +
                         "                                     , o_errors => ? )";

                Map m = (HashMap) model.callStoredProcedure(sqlCmd, 2, null);

                errors = m.get("1").toString();
                if (errors.toLowerCase().contains("error")) {
                    json.put("exito", "ERROR");
                    json.put("fnc", "");
                    json.put("msg", errors);
                    trans.rollback();
                    break ;
                } 
            }
            if (errors.toLowerCase().contains("exito")) {
                trans.commit();
                json.put("exito", "OK");
                json.put("msg", ModelSma.MSG_SAVE);
            }
        } catch (Exception e) {
            trans.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }

        response.getWriter().write(JSONValue.toJSONString(json));

    }        


    
    


    private void genCopy() throws Exception {
        
        String idesmt = Util.getStrRequest("id" , request);
        String qrsppk = Util.getStrRequest("qrs", request);
        String nropsd = Util.getStrRequest("psm", request);
        String smtpsm = Util.getStrRequest("sem", request);
        String chkprf = Util.getStrRequest("chkprf", request);
        String chkhro = Util.getStrRequest("chkhro", request);

        JSONObject json = new JSONObject();
        Session sesion = this.getNewSession();
        Transaction trans = HibernateUtil.getNewTransaction(sesion);

        try {

            trans.begin();
 
            sqlCmd = "sma_academic_programmer.semesters_copy( p_idesxu => '" + model.getSessionSxu()+ "' \n" +
                     "                                      , p_idesmt => '" + idesmt + "' \n" +
                     "                                      , p_qrsppk =>  " + qrsppk + " \n" +
                     "                                      , p_nropsd => '" + nropsd + "' \n" +
                     "                                      , p_smtpsm => '" + smtpsm + "' \n" +
                     "                                      , p_chkprf => '" + chkprf + "' \n" +
                     "                                      , p_chkpda => '" + chkhro + "' \n" +
                     "                                      , o_errors => ? )";
                 
            Map m = (HashMap) model.callStoredProcedure(sqlCmd, 2, null);
            if (!m.get("exito").equals("OK")) {
                json.put("exito", "ERROR");
                json.put("fnc", "");
                String[] parts = m.get("msg").toString().split("&");
                json.put("msg", parts[1]);
                trans.rollback();
            } else {
                String error = m.get("1").toString();
                if (!error.toLowerCase().contains("exito")) {
                    json.put("exito", "ERROR");
                    json.put("fnc", "");
                    json.put("msg", error);
                    trans.rollback();
                }else{
                    json.put("exito", "OK");
                    json.put("fnc", "reload()");
                    json.put("msg", "La Copia de la programación se ha realizado exitosamente!" );
                    trans.commit();
                }
            } 
        } catch (Exception e) {
            trans.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));

    }        


    private void genCopySelected() throws Exception {
        
//        JSONArray arrSel = (JSONArray) JSONValue.parse(Util.getStrRequest("select", request));
        JSONArray arrSel = Util.getJArrayRequest("select", request);
        JSONObject json = new JSONObject();
        Session sesion = this.getNewSession();
        Transaction trans = HibernateUtil.getNewTransaction(sesion);
        JSONObject data ;

        try {

            trans.begin();
            String idesmt = "";
            String qrsppk = "";
            String smtpsm = "";
            String nropsd = "";
            String errors = "";
            String chkprf = Util.getStrRequest("chkprf", request);
            String chkhro = Util.getStrRequest("chkhro", request);
            
            for (Object o : arrSel) {
                 data = (JSONObject) o;
                 nropsd = data.get("psm").toString();
                 smtpsm = data.get("sem").toString();
                 idesmt = data.get("id").toString();
                 qrsppk = data.get("qrs").toString();

                sqlCmd = "sma_academic_programmer.semesters_copy( p_idesxu => '" + model.getSessionSxu()+ "' \n" +
                         "                                      , p_idesmt => '" + idesmt + "' \n" +
                         "                                      , p_nropsd => '" + nropsd + "' \n" +
                         "                                      , p_smtpsm => '" + smtpsm + "' \n" +
                         "                                      , p_qrsppk =>  " + qrsppk + " \n" +
                         "                                      , p_chkprf => '" + chkprf + "' \n" +
                         "                                      , p_chkpda => '" + chkhro + "' \n" +
                         "                                      , o_errors => ? )";

                Map m = (HashMap) model.callStoredProcedure(sqlCmd, 2, null);

                errors = m.get("1").toString();
                if (errors.toLowerCase().contains("error")) {
                    json.put("exito", "ERROR");
                    json.put("fnc", "");
                    json.put("msg", errors);
                    trans.rollback();
                    break ;
                } 
            }
            if (errors.toLowerCase().contains("exito")) {
                trans.commit();
                json.put("exito", "OK");
                json.put("fnc", "reload()");
                json.put("msg", ModelSma.MSG_SAVE);
            }
        } catch (Exception e) {
            trans.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }

        response.getWriter().write(JSONValue.toJSONString(json));

    }        

        private void showGenerated()
              throws SQLException, IOException {

            sqlCmd = "";
            list = new LinkedList();

            JSONObject json = new JSONObject();
            String idepgm = Util.getStrRequest("idepgm", request);

            sqlCmd = "sma_academic_programmer.generated( p_codcia => '" + model.getCodCia() + "'\n" +
                     "                                 , p_codprs => '" + model.getCodPrs() + "'\n" +
                     "                                 , p_idepgm => '" + idepgm + "'\n" +
                     "                                 , o_smappk => ? )";

            String columna = "NROPPK,IDECRS,SMTPSM,NROCRS,NOMCRS,NROPSM,DELETE"; //,VIEW";
            String title   = "Id,Id.Crs,Sem,No Crs,Curso,Reforma,Eliminar";//,Consultar";
            try {

                List lista = model.listarSP(sqlCmd, new Object[]{});

                jQgridTab tab = new jQgridTab();
                tab.setColumns(columna.split(","));
                tab.setTitles(title.split(","));
                tab.setSelector("jqPpk");
                tab.setWidths(new int[]{0, 0, 40, 40, 260, 60, 60});
                tab.setHiddens(new int[]{0,1});
                tab.setKeys(new int[]{0});
                tab.setDataList(lista);

                LinkedHashMap map = new LinkedHashMap();
                map.put("align", "center"); 
                map.put("height", "300");
                map.put("caption", "Generados");
//                map.put("multiselect", true);
                tab.setOptions(map);

                map = new LinkedHashMap();
                map.put("6", "setDelPpk");

                tab.setFormatter(map);
                json.put("exito", "OK");
                json.put("idepgm", idepgm);
                json.put("pjcted", tab.getHtml());

            } catch (Exception e) {
                Util.logError(e);
                json.put("exito", "ERROR");
                json.put("msg", model.setError(e));
            }
            response.getWriter().write(JSONValue.toJSONString(json));

    }
        
    private void delProjected() throws IOException {


        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction((Session) sesion);
        JSONObject json = new JSONObject();
 
        String idesxu = model.getSessionSxu();
        String nroppk = Util.getStrRequest("id", request);
        String o_errors = "";

        sqlCmd = "sma_academic_programmer.project_delete( p_idesxu => '" + idesxu + "'\n" +
                 "                                      , p_nroppk => '" + nroppk + "'\n" +
                 "                                      , o_errors => ? )";

        try {
             tra.begin();

             Map m = (HashMap) model.callStoredProcedure(sqlCmd, 3, sesion);
             o_errors = m.get("1").toString();
             if (!o_errors.toLowerCase().contains("exito")) {
                json.put("exito", "ERROR");
                json.put("fnc", "");
                json.put("msg", "Error al Eliminar la proyección.");
                tra.rollback();
             }
            json.put("msg", "El registro se ha eliminado exitosamente!");
            json.put("fnc", "reload()");
            json.put("exito", "OK");
            tra.commit();
        } catch (Exception e) {
            tra.rollback();
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        response.getWriter().write(JSONValue.toJSONString(json));
    }        

    
}

