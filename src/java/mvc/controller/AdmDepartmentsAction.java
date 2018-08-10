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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List; 
import java.util.Map;
import javax.servlet.ServletException;
import mvc.model.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Ing. Jacobo Llanos
 * @Created 2/09/2016 10:33:31 AM
 * @Archivo AdmUniversitiesAction
 */
public class AdmDepartmentsAction extends Action {

    private JSONObject json;
    private String sqlCmd;

    @Override
    public void run() throws ServletException, IOException {
        
        String event = Util.getStrRequest("event", request);
        json = new JSONObject();

        try {
            if (event.equals("LIST")) {
                viewDepartament();
            } else if (event.equals("DELETE")) {
                delDepartament();
            } else if (event.equals("SAVE")) {
                saveDepartament();
            } else if (event.equals("EDIT")) {
                fillform();
            }

        } catch (Exception e) {
            Util.logError(e);
        }
    }

    private void write(Object js) throws IOException {
        response.getWriter().write(JSONValue.toJSONString(js));

    }

    private void viewDepartament() throws Exception {
        try {
            String idedpt = Util.getStrRequest("idedpt", request);

            List smadpt = smaDpt(idedpt);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"IDEDPT", "NOMPAI", "CODDPT",        "NPQDPT",       "NOMDPT",   "EDIT",   "DELETE", "IDEPAI"});
            tab.setTitles(new String[]{     "Id",   "País", "Código", "Identificador", "Departamento", "Editar", "Eliminar", "IdPais"});
            tab.setWidths(new int[]{0, 300, 50, 150, 350, 50, 50,0});
            tab.setHiddens(new int[]{0,7});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqDpt");
            tab.setFilterToolbar(true);
            tab.setDataList(smadpt);
            tab.setPaginador("jqDptP");
            
            tab.setGroupFields("NOMPAI");
            tab.setGroupText("<b><i>País : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false});
            tab.setGroupOrder("asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "350");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("5", "formatterEdit");
            map.put("6", "formatterDelete");
            tab.setFormatter(map);

            map = new LinkedHashMap();
            map.put("5","center");
            map.put("6","center");
            tab.setColumnAlign(map);
            
            json.put("exito", "OK");
            json.put("html", tab.getHtml());

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        write(json);
    }

    private void delDepartament() throws Exception {
        
        String idedpt = Util.getStrRequest("id", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        
        try {
            model.deleteLogBook("smadpt", idedpt, sesion);
            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);
            tra.commit();
        } catch (Exception e) {
            json.put("exito", "ERROR");
            json.put("msg", "No se pudo eliminar, existen otras estructuras que tienen ese departamento");
            write(json);
            tra.rollback();
//            Util.logError(e);
        }
        write(json);
    }

    private void saveDepartament() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject formu = Util.getJsonRequest("formulario", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        try {

            tra.begin();
            Map    datos  = Util.map("smadpt", formu);
            String idedpt = Util.validStr(datos, "idedpt").toString();
            String coddpt = Util.validStr(datos, "coddpt").toString();
            String npqdpt = Util.validStr(datos, "npqdpt").toString();
            String nomdpt = Util.validStr(datos, "nomdpt").toString();

            if(idedpt.isEmpty()){
                sqlCmd = "select distinct coddpt \n"
                       + "  from smadpt \n"
                       + " where coddpt = '" + coddpt + "'";

                sqlCmd = (String) model.getData(sqlCmd, null);
                if(!sqlCmd.isEmpty()){

                    json.put("exito", "ERROR");
                    json.put("msg", "Ya existe ese código de departamento");
                    tra.rollback();

                }else{

                    sqlCmd = "select distinct nomdpt \n"
                           + "  from smadpt \n"
                           + " where npqdpt = '" + npqdpt + "' \n"
                           + "    or nomdpt = '" + nomdpt + "'";

                    sqlCmd = (String) model.getData(sqlCmd, null);
                    if(!sqlCmd.isEmpty()){

                        json.put("exito", "ERROR");
                        json.put("msg", "Ya existe ese nombre de departamento");
                        tra.rollback();

                    }else{

                        idedpt = model.saveLogBook(datos, "smadpt", sesion);
                        json.put("exito", "OK");
                        json.put("msg", model.MSG_SAVE);
                        tra.commit();

                    }
                }

            } else {
                    model.updateLogBook(datos, "smadpt", idedpt.toString().trim(), sesion);
                    json.put("exito", "OK");
                    json.put("msg", model.MSG_SAVE);
                    tra.commit();
            }

        } catch (Exception e) {
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
            write(json);
            tra.rollback();
            Util.logError(e);
        }
        write(json);
    }

    private void fillform() throws Exception {

        String idedpt = Util.getStrRequest("id", request);
        try {

            List smadpt = smaDpt(idedpt);

            HashMap hasDpt = new HashMap();

            if (!smadpt.isEmpty()) {
                hasDpt = (HashMap) smadpt.get(0);
            }

            json.put("exito", "OK");
            json.put("smadpt", hasDpt);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        write(json);
    }

    private List smaDpt(String idedpt) {

        try {

            sqlCmd = "  select smadpt.idedpt \n" 
                   + "       , smadpt.coddpt \n" 
                   + "       , smadpt.nomdpt \n" 
                   + "       , smadpt.npqdpt \n" 
                   + "       , smapai.nompai \n" 
                   + "       , smadpt.idepai \n" 
                   + "    from smapai \n" 
                   + "    join smadpt \n" 
                   + "      on smadpt.idepai = smapai.idepai \n"
                   + "   where smapai.codcia = '" + model.getCodCia() + "' \n";

            if (!idedpt.trim().isEmpty()) {
                sqlCmd += " and smadpt.idedpt = '" + idedpt + "' \n";
            }
            sqlCmd +=  "order by smapai.nompai \n"
                   +          ", smadpt.nomdpt ";
            
            model.list(sqlCmd, null);
            return model.getList();
        } catch (Exception e) {
            Util.logError(e);
            return new LinkedList();
        }
    }

}
