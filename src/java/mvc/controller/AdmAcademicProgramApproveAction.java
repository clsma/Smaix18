/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import java.io.IOException;
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
    private String idepgm;
    private String nropkp;

    @Override
    public void run() throws ServletException, IOException {

        accion = Util.getStrRequest("event", request);
        agnprs = Util.getStrRequest("agnprs", request);
        prdprs = Util.getStrRequest("prdprs", request);
        idepgm = Util.getStrRequest("idepgm", request);
        nropkp = Util.getStrRequest("nropkp", request);
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
            
            if("GET_TABLE_SUMMARY".equals(accion)){              
                listSummary();
                return;
            }

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException("Error Clase [" + getClass().getName() + "] Metodo [run()] " + e.getMessage());
        }
    }

    private void listSummary() throws IOException{       
        JSONObject json = new JSONObject();
        openSqlCommand();
        setSqlCommand("SMA_ACADEMIC_PROG_APPROVE.LST_REQ_SUMMARY( pcodcia => '").append(model.getCodCia()).append("', ")
                                                        .append("pidepgm => '").append(idepgm).append("', ")
                                                        .append("pagnprs => '").append(agnprs).append("', ")
                                                        .append("pprdprs => '").append(prdprs).append("', ")
                                                        .append("pnropkp => '").append(nropkp).append("', ")
                                                        .append("osmaaux =>  ? ) ");
        
        
        String columna = "SMTPSM,HORAS,VALOR,(HORAS*VALOR)SUBTOTAL";
        String title = "Semestre,Horas,V. Catedra,Subtotal";
        try {
            List lista = model.listarSP(getSqlCommand(), new Object[]{});
            jQgridTab tab = new jQgridTab();
            tab.setColumns(columna.split(UtilConstantes.STR_COMA));
            tab.setTitles(title.split(UtilConstantes.STR_COMA));
            tab.setSelector("jqSmm");
            tab.setPaginador("pagerSmm");
            tab.setWidths(new int[]{60, 60, 60, 60});            
            tab.setDataList(lista);

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "200");
            //map.put("ondblClickRow", "detailProgram");
            //map.put("onSelectRow", "detailProgram");

            tab.setOptions(map);

            json.put(UtilConstantes.STR_KEY_JSON_EXITO, "OK");
            json.put(UtilConstantes.STR_KEY_JSON_HTML, tab.getHtml());
            
         } catch (Exception e) {
            Util.logError(e);
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, UtilConstantes.STR_KEY_JSON_ERROR);
            json.put("msg", model.setError(e));
        } finally {
            writeJsonResponse(json);
        } 
     
    }

    private void getListTabDocentes() throws IOException {
        JSONObject json = new JSONObject();
        openSqlCommand();
        setSqlCommand("SMA_ACADEMIC_PROG_APPROVE.LST_REQ_TEACHERS(pcodcia => '").append(model.getCodCia()).append("', ")
                                                         .append("pidepgm => '").append(idepgm).append("', ")
                                                         .append("pnropkp => '").append(nropkp).append("', ")
                                                         .append("osmaaux => ? )");                                                                       
        try {
            
            List lista = model.listarSP(getSqlCommand(), new Object[]{});                    
            StringBuilder HTML_ = new StringBuilder(UtilConstantes.STR_VACIO);
            LinkedHashMap infoSemester, infoTeacher = new LinkedHashMap();            
            if (lista.isEmpty()){
                throw new ClsmaException(ClsmaTypeException.INFO, "<p>No Hay datos para mostrar</p>");                
            }
                HTML_.append("<div id='acordeon'>");
                
                for (int i = 0; i < lista.size(); i++){
                    infoSemester = (LinkedHashMap)lista.get(i);
                    
                    HTML_.append("<h3> \n<div> \n<div class='cProfesores'> \n\t&nbsp;&nbsp;&nbsp; ");
                    HTML_.append(infoSemester.get("CODPRS") ).append( " " ).append( infoSemester.get("NOMPRS") ).append( " (" ).append( infoSemester.get("NOMELM") ).append( ")\n" );
                    HTML_.append("</div> \n" );
                    HTML_.append("<div class='cHorasTotales'>" ).append( infoSemester.get("HRSTOT") ).append( " Horas Cátedra en el programa</div> \n" );
                    HTML_.append("</div> \n</h3> \n <div> \n");
                    HTML_.append("<table style=\"width: 100%;\"> \n");
                    HTML_.append("<tr> \n <td> \n");
                    HTML_.append("<fieldset> \n");
                    HTML_.append("<table class='simple' style=\"width: 100%;\"> \n");
                    HTML_.append("<tr> \n");
                    HTML_.append("<td style='text-align:center'> Semestre</td> \n<td style='text-align:center'> Programa</td> \n<td style='text-align:center'> Materia</td> \n<td style='text-align:center'> Grupo</td> \n<td style='text-align:center'> Horas</td>  \n<td style='text-align:center'> Tipo hora</td>  \n<td style='text-align:center'>Estado</td>   \n<td style='text-align:center'>Mark</td>  \n<td style='text-align:center'>Estado</td>  \n");
                    HTML_.append("</tr> \n");
                    openSqlCommand();
                    setSqlCommand("SMA_ACADEMIC_PROG_APPROVE.LST_REQ_GROUPS( pcodcia => '").append(model.getCodCia()).append("',")
                                                                   .append(" pcodprs => '").append(model.getCodPrs()).append("',")
                                                                   .append(" pnropkp => '").append(nropkp).append("',")
                                                                   .append(" pidepgm => '").append(idepgm).append("', ")
                                                                   .append(" pnroprf => '").append(infoSemester.get("NROPRF")).append("', ")
                                                                   .append(" osmaaux => ? ) ");
                                        
                    List infoTeachers = model.listarSP(getSqlCommand(), new Object[]{});
                    for (int j = 0; j < infoTeachers.size(); j++){
                        infoTeacher = (LinkedHashMap)infoTeachers.get(j);
                        
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("SMTPSM") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:left' > " ).append( infoTeacher.get("NOMPGM") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:left' > " ).append( infoTeacher.get("NOMMAT") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("NROGRP") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("HRSPRF") ).append( "</td> \n");
                        HTML_.append("<td style='text-align:center' > " ).append( infoTeacher.get("TPOHRS") ).append( "</td> \n");
                        if ("Catedra".equals(infoTeacher.get("TPOHRS"))) {
                            HTML_.append("<td class='C_APRO" ).append( infoTeacher.get("STDAPP") ).append( "' style='text-align:center' > " );
                            HTML_.append(infoTeacher.get("STDAPP")).append("</td> \n");
                        } else {
                            HTML_.append("<td style='text-align:center' > </td> \n");
                        }
                        if ("APROBADA".equals(infoTeacher.get("STDAPP"))) {
                            HTML_.append("<td style='text-align:center' > </td> \n");
                            HTML_.append("<td style='text-align:center' > </td> \n");
                        //} else if ("Catedra".equals(infoTeacher.get("TPOHRS"))){
                        }else{
                            HTML_.append("<td align=\"center\" > <input type=\"checkbox\" id=\"codgrp\" name=\"codgrp\" onclick=\"validCombo(this)\" value=\"" ).append( infoTeacher.get("CODGRP") ).append( "~" ).append( infoSemester.get("CODPRS") ).append( "\"></td> \n");
                            HTML_.append("<td align=\"center\" > <select name=\"stdapp_" ).append( infoTeacher.get("CODGRP") ).append( "~" ).append( infoSemester.get("CODPRS") ).append( "\" onclick=\"show_txt(this)\" class=\"combo\" id=\"stdapp_" ).append( infoTeacher.get("CODGRP") ).append( "~" ).append( infoSemester.get("CODPRS") ).append( "\" title=\"Tipo de horas\" >" );
                            HTML_.append(" <option value=\"\" style=\"background-color: #FFFFFF\"></option>" );
                            HTML_.append(" <option value=\"Aprobada\" style=\"background-color: #F2F2F2\">Aprobada</option>" );
                            HTML_.append(" <option value=\"Negada\" style=\"background-color: #FFFFFF\">Negada</option>" );
                            HTML_.append(" </select>" );
                            HTML_.append("<input type=\"hidden\" id=\"codprf_" ).append( infoTeacher.get("CODGRP") ).append( "~" );
                            HTML_.append(infoSemester.get("CODPRS") ).append( "\" name=\"codprf_" ).append( infoTeacher.get("CODGRP") ).append( "~" );
                            HTML_.append( infoSemester.get("CODPRS") ).append( "\" value=\"" ).append( infoSemester.get("CODPRS") ).append( "\">" );                       
                            HTML_.append("<input type=\"hidden\" id=\"nropkp_" ).append( infoTeacher.get("CODGRP") ).append( "~" );
                            HTML_.append( infoSemester.get("CODPRS") ).append( "\" name=\"nropkp_" ).append( infoTeacher.get("CODGRP") ).append( "~" );
                            HTML_.append( infoSemester.get("CODPRS") ).append( "\" value=\"" ).append( infoTeacher.get("NROPKP") ).append( "\">" );
                            HTML_.append("</td> \n");
                            HTML_.append("</tr> \n");
                        /*}else{
                            HTML_.append("<td style='text-align:center' > </td> \n");
                            HTML_.append("<td style='text-align:center' > </td> \n");*/
                        }
                        HTML_.append("<tr> \n");
                        HTML_.append("<td colspan=\"10\" align=\"center\"> \n");
                        HTML_.append("<table id=\"tbl_" ).append( infoTeacher.get("CODGRP") ).append( "~" ).append( infoSemester.get("CODPRS") );
                        HTML_.append("\" style=\"width: 100%; display: none;\" class=\"tbljusti\"> \n");
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style=\"width: 100%;\"  align=\"center\"> \n");
                        HTML_.append("<fieldset> \n");
                        HTML_.append("<legend>JUSTIFICACION</legend> \n");
                        HTML_.append("<textarea id=\"jstapp_" ).append( infoTeacher.get("CODGRP") ).append( "~" ).append( infoSemester.get("CODPRS") ).append( "\" name=\"jstapp_" );
                        HTML_.append(infoTeacher.get("CODGRP")).append( "~" ).append( infoSemester.get("CODPRS") ).append( "\" cols=\"120\" rows=\"4\" style=\"width: 100%;\"></textarea> \n");
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
                    
                    setSqlCommand("SMA_ACADEMIC_PROG_APPROVE.LST_SPECIALHOURS_TEACHERS( pcodcia => '").append(model.getCodCia()).append("',")
                                                                              .append(" pcodprs => '").append(model.getCodPrs()).append("',")
                                                                              .append(" pnroprf => '").append(infoTeacher.get("NROPRF")).append("',")
                                                                              .append(" pagnprs => '").append(agnprs).append("',")
                                                                              .append(" pprdprs => '").append(prdprs).append("',")
                                                                              .append(" osmaaux => ? ) ");                                                                                                                                    
                    
                    List listaSht =model.listarSP(getSqlCommand(), new Object[]{});
                    if (!listaSht.isEmpty()){
                        List lstDta = listaSht; 
                        LinkedHashMap smaTmp = new LinkedHashMap();
                        
                        HTML_.append("<tr> \n");
                        HTML_.append("<td> \n");
                        HTML_.append("<fieldset> \n");
                        HTML_.append("<legend style=\"text-align : center;\">Horas Especiales</legend > \n");
                        HTML_.append("<table class='simple' style=\"width: 100%;\"> \n");
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style='text-align:center'> Programa</td><td style='text-align:center'> Concepto</td><td style='text-align:center'> Horas</td><td style='text-align:center'> Tipo hora</td> \n");
                        HTML_.append("</tr> \n");
                        
                        for (int k = 0; k < lstDta.size(); k++) {
                            smaTmp = (LinkedHashMap)lstDta.get(k);
                            HTML_.append("<tr> \n");
                            HTML_.append("<td style='text-align:left'>" ).append( smaTmp.get("NOMPGM").toString() ).append( "</td> \n");
                            HTML_.append("<td style='text-align:left'>" ).append( smaTmp.get("NOMHEC").toString() ).append( "</td> \n");
                            HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("HRSXDA").toString() ).append( "</td> \n");
                            HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("TPOHRS").toString() ).append( "</td> \n");
                            HTML_.append("</tr> \n");
                        }
                        HTML_.append("<tr> \n");
                        HTML_.append("<td colspan=\"10\"><hr/></td> \n");
                        HTML_.append("</tr> \n");
                        HTML_.append("<tr> \n");
                        HTML_.append("<td style='text-align:right'><b>Total Catedra: </b></td> \n");
                        HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("TOTCTD").toString() ).append( "</td> \n");
                        HTML_.append("<td style='text-align:right'><b>Total Tiempo Completo: </b></td> \n");
                        HTML_.append("<td style='text-align:center'>" ).append( smaTmp.get("TOTCOM").toString() ).append( "</td> \n");
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
                                  
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, "OK");
            json.put(UtilConstantes.STR_KEY_JSON_HTML, HTML_.toString());
            
        }catch(ClsmaException e){
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, UtilConstantes.STR_KEY_JSON_ERROR);
            json.put(UtilConstantes.STR_KEY_JSON_HTML, e.getMessage());
        } catch (Exception exep) {
            Util.logError(exep);            
            json.put(UtilConstantes.STR_KEY_JSON_EXITO, UtilConstantes.STR_KEY_JSON_ERROR);
            json.put(UtilConstantes.STR_KEY_JSON_HTML, model.setError(exep));
        }finally{
            writeJsonResponse(json);
        }        
    }
    
    private void getListSemester() throws  IOException {
        JSONObject json = new JSONObject();        
        
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
            json.put(UtilConstantes.STR_KEY_JSON_HTML, tab.getHtml());

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
                                                        .append(",osmaaux => ? ) ");
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
