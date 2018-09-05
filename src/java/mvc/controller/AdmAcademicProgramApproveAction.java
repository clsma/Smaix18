/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import static mvc.controller.AdmAcademicProgramRequestAction.set;
import mvc.model.Util;
import mvc.util.ClsmaException;
import mvc.util.ClsmaTypeException;
import mvc.util.UtilConstantes;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author developer
 */
public class AdmAcademicProgramApproveAction extends Action {

    private String agnprs;
    private String prdprs;
    private String accion;
    private String nxtPge;

    @Override
    public void run() throws ServletException, IOException {

        accion = Util.getStrRequest("event", request);
        agnprs = Util.getStrRequest("agnprs", request);
        prdprs = Util.getStrRequest("prdprs", request);
        try {
            if ("GETCAL".equals(accion)) {
                getCalendario();
                return;
            }
            if ("GET_LIST_PROGRAM_APPROVE".equals(accion)) {
                getListAcaProgApprove(agnprs, prdprs);
                return;
            }

            if ("GET_TABLE_SEMESTER".equals(accion)) {
                getListSemester();
                return;
            }
            
            if ("GET_TABLE_TEACHER".equals(accion)){     
                getListTabDocentes();
                return;
        }           

            rd = application.getRequestDispatcher(nxtPge);
            if (rd == null) {
                throw new ServletException("No se pudo encontrar " + nxtPge);
            }
            rd.forward(request, response);

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException("Error Clase [" + getClass().getName() + "] Metodo [run()] " + e.getMessage());
        }
    }

    private void getListTabDocentes() throws IOException {
        System.out.println("######################################################################### ");
        String anio = request.getParameter("anio") != null ? request.getParameter("anio").trim() : "";
        String periodo = request.getParameter("periodo") != null ? request.getParameter("periodo").trim() : "";
        String program = request.getParameter("programa") != null ? request.getParameter("programa").trim() : "";
        openSqlCommand();
        setSqlCommand(" select distinct smaprf.codprs, trim(smaprf.apeprs)||' '||trim(smaprf.nomprs) as nomprs, ");
        setSqlCommand("case when smaelm.nomelm isnull then 'NO DEFINIDO' else smaelm.nomelm end as nomelm, sum((smagrp.smngrp * ");
        setSqlCommand("(case when smapxg.tpohrs = 'Catedra' then smapxg.hrsprf else 0 end))) over (partition by smapxg.codprs) ");
        setSqlCommand("+ (smagrp.smngrp * (select case when sum(smaxda.hrsxda) isnull then 0 else sum(smaxda.hrsxda) end ");
        setSqlCommand("from smaxda where smaxda.agnprs = smagrp.agnprs and smaxda.prdprs = smagrp.prdprs and smaxda.codprf = smapxg.codprs)) ");
        setSqlCommand("hrstot from smagrp join smapxg on smapxg.codgrp = smagrp.codgrp join smaprf on smaprf.codprs = smapxg.codprs");
        setSqlCommand("join smampy on smampy.codprs = smapxg.codprs left join smaelm on smaelm.codelm = 'PRF' and smaelm.tipelm = 'KLC' ");
        setSqlCommand("and smaelm.nroelm = smampy.klcprs join smaapp on smaapp.codprf = smapxg.codprs and smaapp.codgrp = smagrp.codgrp ");
        setSqlCommand("and smaapp.tpoapp = 'DKN' and smaapp.stdapp = 'Aprobada' where smagrp.agnprs = '").append( anio ).append( "' ");
        setSqlCommand(" and smagrp.prdprs = '").append( periodo ).append( "'   and smagrp.nropgm = '").append( program ).append( "' ");
        try {
            model.listGenericHash(getSqlCommand());
            List lista = model.getList();
            StringBuilder HTML_ = new StringBuilder(UtilConstantes.STR_VACIO);
            Hashtable infoSemester, infoTeacher = new Hashtable();            
            String prs, estado  = "";
            boolean band = false;                        
            if (!lista.isEmpty()){
                HTML_.append("<div id='acordeon'>");
                
                for (int i = 0; i < lista.size(); i++){
                    infoSemester = (Hashtable)lista.get(i);
                    
                    HTML_.append("<h3> \n<div> \n<div class='cProfesores'> \n\t&nbsp;&nbsp;&nbsp; ");
                    HTML_.append(infoSemester.get("codprs") ).append( " " ).append( infoSemester.get("nomprs") ).append( " (" ).append( infoSemester.get("nomelm") ).append( ")\n" );
                    HTML_.append("</div> \n" );
                    HTML_.append("<div class='cHorasTotales'>" ).append( infoSemester.get("hrstot") ).append( " Horas Cátedra en el programa</div> \n" );
                    HTML_.append("</div> \n</h3> \n <div> \n");
                    HTML_.append("<table style=\"width: 100%;\"> \n");
                    HTML_.append("<tr> \n <td> \n");
                    HTML_.append("<fieldset> \n");
                    HTML_.append("<table class='simple' style=\"width: 100%;\"> \n");
                    HTML_.append("<tr> \n");
                    HTML_.append("<td style='text-align:center'> Semestre</td> \n<td style='text-align:center'> Programa</td> \n<td style='text-align:center'> Materia</td> \n<td style='text-align:center'> Grupo</td> \n<td style='text-align:center'> Horas</td>  \n<td style='text-align:center'> Tipo hora</td>  \n<td style='text-align:center'>Estado</td>   \n<td style='text-align:center'>Mark</td>  \n<td style='text-align:center'>Estado</td>  \n");
                    HTML_.append("</tr> \n");
                    openSqlCommand();
                    setSqlCommand("select * from sma_approve_academic_teacher_list('" ).append(model.getCodCia()).append("','")
                            .append(model.getCodPrs()).append("','")
                            .append(program).append("','")
                            .append(anio).append("','")
                            .append(periodo).append("','")
                            .append(infoSemester.get("codprs")).append("')");
                    
                    model.listGenericHash(getSqlCommand());
                    
                    List infoTeachers = model.getList();
                    for (int j = 0; j < infoTeachers.size(); j++){
                        infoTeacher = (Hashtable)infoTeachers.get(j);
                        
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("smtpsm") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:left' > " ).append( infoTeacher.get("nompgm") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:left' > " ).append( infoTeacher.get("nommat") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("nrogrp") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("hrsprf") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("tpohrs") ).append( "</td> \n");
                        if ("Catedra".equals(infoTeacher.get("tpohrs"))) {
                            HTML_.append("<td class='C_APRO" ).append( infoTeacher.get("stdapp") ).append( "' style='text-align:center' > " );
                            HTML_.append(infoTeacher.get("stdapp")).append("</td> \n");
                        } else {
                            HTML_.append("<td style='text-align:center' > </td> \n");
                        }
                        if ("APROBADA".equals(infoTeacher.get("stdapp"))) {
                            HTML_.append("<td style='text-align:center' > </td> \n");
                            HTML_.append("<td style='text-align:center' > </td> \n");
                        } else if ("Catedra".equals(infoTeacher.get("tpohrs"))){
                            HTML_.append("<td align=\"center\" > <input type=\"checkbox\" id=\"codgrp\" name=\"codgrp\" onclick=\"validCombo(this)\" value=\"" ).append( infoTeacher.get("codgrp") ).append( "~" ).append( infoSemester.get("codprs") ).append( "\"></td> \n");
                            HTML_.append("<td align=\"center\" > <select name=\"stdapp_" ).append( infoTeacher.get("codgrp") ).append( "~" ).append( infoSemester.get("codprs") ).append( "\" onclick=\"show_txt(this)\" class=\"combo\" id=\"stdapp_" ).append( infoTeacher.get("codgrp") ).append( "~" ).append( infoSemester.get("codprs") ).append( "\" title=\"Tipo de horas\" >" );
                            HTML_.append(" <option value=\"\" style=\"background-color: #FFFFFF\"></option>" );
                            HTML_.append(" <option value=\"Aprobada\" style=\"background-color: #F2F2F2\">Aprobada</option>" );
                            HTML_.append(" <option value=\"Negada\" style=\"background-color: #FFFFFF\">Negada</option>" );
                            HTML_.append(" </select>" );
                            HTML_.append("<input type=\"hidden\" id=\"codprf_" ).append( infoTeacher.get("codgrp") ).append( "~" );
                            HTML_.append(infoSemester.get("codprs") ).append( "\" name=\"codprf_" ).append( infoTeacher.get("codgrp") ).append( "~" );
                            HTML_.append( infoSemester.get("codprs") ).append( "\" value=\"" ).append( infoSemester.get("codprs") ).append( "\">" );                       
                            HTML_.append("<input type=\"hidden\" id=\"nropkp_" ).append( infoTeacher.get("codgrp") ).append( "~" );
                            HTML_.append( infoSemester.get("codprs") ).append( "\" name=\"nropkp_" ).append( infoTeacher.get("codgrp") ).append( "~" );
                            HTML_.append( infoSemester.get("codprs") ).append( "\" value=\"" ).append( infoTeacher.get("nropkp") ).append( "\">" );
                            HTML_.append("</td> \n");
                            HTML_.append("</tr> \n");
                        }else{
                            HTML_.append("<td style='text-align:center' > </td> \n");
                            HTML_.append("<td style='text-align:center' > </td> \n");
                        }
                        HTML_.append("<tr> \n");
                        HTML_.append("<td colspan=\"10\" align=\"center\"> \n");
                        HTML_.append("<table id=\"tbl_" ).append( infoTeacher.get("codgrp") ).append( "~" ).append( infoSemester.get("codprs") );
                        HTML_.append("\" style=\"width: 100%; display: none;\" class=\"tbljusti\"> \n");
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style=\"width: 100%;\"  align=\"center\"> \n");
                        HTML_.append("<fieldset> \n");
                        HTML_.append("<legend>JUSTIFICACION</legend> \n");
                        HTML_.append("<textarea id=\"jstapp_" ).append( infoTeacher.get("codgrp") ).append( "~" ).append( infoSemester.get("codprs") ).append( "\" name=\"jstapp_" );
                        HTML_.append(infoTeacher.get("codgrp")).append( "~" ).append( infoSemester.get("codprs") ).append( "\" cols=\"120\" rows=\"4\" style=\"width: 100%;\"></textarea> \n");
                        HTML_.append("</fieldset> \n");
                        HTML_.append("</td> \n");
                        HTML_.append("</tr> \n");
                        HTML_.append("</table > \n");
                        HTML_.append("</td> \n");
                        HTML_.append("</tr> \n");
                    }
                    
                    HTML_.append("</table > \n");
                    HTML_.append("</fieldset> \n");
                    HTML_.append("</td> \n");
                    HTML_.append("</tr> \n");
                    
                    openSqlCommand();
                    setSqlCommand(" select smaxda.nroxda,  smaxda.codprf,  smapgm.nropgm,  smapgm.nompgm,  smahec.nomhec,  smaxda.hrsxda,  ");
                    setSqlCommand("smaxda.tpohrs,  sum(case when tpohrs = 'Catedra' then hrsxda else 0 end) over () as totctd, " );
                    setSqlCommand("sum(case when tpohrs = 'Tiempo completo' then hrsxda else 0 end) over () as totcom,  smaxda.stdxda  from smaxda  ");
                    setSqlCommand("join smahec  on smahec.nrohec = smaxda.nrohec  join smapgm  on smapgm.nropgm = smaxda.nropgm ");
                    setSqlCommand("where smaxda.stdxda = 'Confirmado'  and smaxda.stdpln = 'Confirmado'  and smaxda.agnprs = '" );
                    setSqlCommand( anio ).append( "'  and smaxda.prdprs = '" ).append( periodo ).append( "' and smaxda.codprf = '" )
                            .append( infoSemester.get("codprs") ).append( "'");
                    
                    model.listGenericHash(getSqlCommand());
                    
                    if (!model.getList().isEmpty()){
                        List lstDta = model.getList();
                        Hashtable smaTmp = new Hashtable();
                        
                        HTML_.append("<tr> \n");
                        HTML_.append("<td> \n");
                        HTML_.append("<fieldset> \n");
                        HTML_.append("<legend style=\"text-align : center;\">Horas Especiales</legend > \n");
                        HTML_.append("<table class='simple' style=\"width: 100%;\"> \n");
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style='text-align:center'> Programa</td><td style='text-align:center'> Concepto</td><td style='text-align:center'> Horas</td><td style='text-align:center'> Tipo hora</td> \n");
                        HTML_.append("</tr> \n");
                        
                        for (int k = 0; k < lstDta.size(); k++) {
                            smaTmp = (Hashtable)lstDta.get(k);
                            HTML_.append("<tr> \n");
                            HTML_.append("<td style='text-align:left'>" ).append( smaTmp.get("nompgm").toString() ).append( "</td> \n");
                            HTML_.append("<td style='text-align:left'>" ).append( smaTmp.get("nomhec").toString() ).append( "</td> \n");
                            HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("hrsxda").toString() ).append( "</td> \n");
                            HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("tpohrs").toString() ).append( "</td> \n");
                            HTML_.append("</tr> \n");
                        }
                        HTML_.append("<tr> \n");
                        HTML_.append("<td colspan=\"10\"><hr/></td> \n");
                        HTML_.append("</tr> \n");
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style='text-align:right'><b>Total Catedra: </b></td> \n");
                        HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("totctd").toString() ).append( "</td> \n");
                        HTML_.append("<td style='text-align:right'><b>Total Tiempo Completo: </b></td> \n");
                        HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("totcom").toString() ).append( "</td> \n");
                        HTML_.append("</tr> \n");
                        
                        HTML_.append("</table > \n");
                        HTML_.append("</fieldset> \n");
                        HTML_.append("</td> \n");
                        HTML_.append("</tr> \n");
                    }
                    HTML_.append("</table > \n");
                    HTML_.append("</div> \n");
                }
                
                HTML_.append("</div> \n");
            }else {
                HTML_.append("No Hay datos para mostrar");
            }
            response.getWriter().print(HTML_);            
        } catch (Exception exep) {
            response.getWriter().print("Ha ocurrido un error: " + exep.getMessage());            
        }finally{
            return;
        }
        
    }
    
    private void getListSemester() throws  IOException {
        JSONObject json = new JSONObject();
        String idepgm = Util.getStrRequest("idepgm", request);
        String nropkp = Util.getStrRequest("nropkp", request);
        
        //String codkls = Util.getStrRequest("codkls", request);
        openSqlCommand();
        setSqlCommand("SMA_ACADEMIC_PROG_APPROVE.LST_REQ_SEMESTERS(pcodcia => '").append(model.getCodCia()).append("'\n, ")
                                                            .append("pcodprs => '").append(model.getCodPrs()).append("'\n, ")
                                                            .append("pnropkp => '").append(nropkp).append("'\n,")
                                                            .append("pidepgm => '").append(idepgm).append("'\n,")
                                                            .append(" osmaaux => ? )");
        
         String columna = "SMTPSM,CODMAT,NOMMAT,NROGRP,NOMPRF,NUMHRS_";
        String title = "Semestre,Cod. Materia,Materia,Grupo,Docente,Horas";
        try {
            List lista = model.listarSP(getSqlCommand(), new Object[]{});
            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(UtilConstantes.STR_COMA));
            tab.setTitles(title.split(UtilConstantes.STR_COMA));
            tab.setSelector("jqSmt");
            tab.setPaginador("pagerSmt");
            tab.setWidths(new int[]{60, 60, 300, 60, 300,60});
            //tab.setHiddens(new int[]{0, 4});
            //tab.setKeys(new int[]{0});
            tab.setDataList(lista);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "200");
            //map.put("ondblClickRow", "detailProgram");
            //map.put("onSelectRow", "detailProgram");

            tab.setOptions(map);

            json.put(UtilConstantes.STR_KEY_JSON_EXITO, "OK");
            json.put("html", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, UtilConstantes.STR_KEY_JSON_ERROR);
            json.put("msg", model.setError(e));
        } finally {
            writeJsonResponse(json);
        } 
    }
        
    private void getListAcaProgApprove(String agnprs, String prdprs) throws IOException {
        JSONObject json = new JSONObject();

        openSqlCommand();

        setSqlCommand("SMA_ACADEMIC_PROG_APPROVE.LST_REQ_PROGRAMS(pcodcia =>'").append(model.getCodCia()).append("'\n")
                .append(",pcodprs => '").append(model.getCodPrs()).append("'\n")
                .append(",pagnprs => '").append(agnprs).append("'\n")
                .append(",pprdprs => '").append(prdprs).append("'\n")
                .append(", osmaaux => ? ) ");
        String columna = "IDEPGM,NROPKP,NROPGM,NOMPGM,STGPGM,";
        String title = "IDEPGM,NROPKP,Id,Programa,Jornada";
        try {
            List lista = model.listarSP(getSqlCommand(), new Object[]{});

            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(UtilConstantes.STR_COMA));
            tab.setTitles(title.split(UtilConstantes.STR_COMA));
            tab.setSelector("jqPgm");
            tab.setPaginador("pagerPgm");
            tab.setWidths(new int[]{0, 0, 60, 300, 100});
            tab.setHiddens(new int[]{0, 1});
            tab.setKeys(new int[]{0});
            tab.setDataList(lista);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "200");
            //map.put("ondblClickRow", "detailProgram");
            map.put("onSelectRow", "detailProgram");

            tab.setOptions(map);

            json.put(UtilConstantes.STR_KEY_JSON_EXITO, "OK");
            json.put("html", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, UtilConstantes.STR_KEY_JSON_ERROR);
            json.put("msg", model.setError(e));
        } finally {
            writeJsonResponse(json);
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
        openSqlCommand();
        setSqlCommand("select distinct agnprs,prdprs from table (sma_academic.calendar_activity_all('")
                .append(model.getCodCia()).append("','")
                .append(model.getCodPrs()).append("','")
                .append(codaxk).append("')) order by agnprs,prdprs");

        model.listGenericHash(getSqlCommand());
        List<LinkedHashMap> lista = model.getList();
        return lista;
    }

}
