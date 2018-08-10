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
public class AdmCitiesAction extends Action {

    private JSONObject json;
    private String sqlCmd;

    @Override
    public void run() throws ServletException, IOException {
        
        String event = Util.getStrRequest("event", request);
        json = new JSONObject();

        try {
            if (event.equals("LIST")) {
                viewCity();
            } else if (event.equals("DELETE")) {
                delCity();
            } else if (event.equals("SAVE")) {
                saveCity();
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

    private void viewCity() throws Exception {
        try {
            String ideciu = Util.getStrRequest("ideciu", request);

            List smaciu = smaCiu(ideciu);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"IDECIU", "NOMPAI",       "NOMDPT", "CODCIU",        "NPQCIU", "NOMCIU",   "EDIT",   "DELETE", "IDEDPT"});
            tab.setTitles(new String[]{     "Id",   "País", "Departamento", "Código", "Identificador", "Ciudad", "Editar", "Eliminar", "IdDpto"});
            tab.setWidths(new int[]{0, 200, 300, 50, 150, 350, 50, 50, 0});
            tab.setHiddens(new int[]{0,8});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqCiu");
            tab.setFilterToolbar(true);
            tab.setDataList(smaciu);
            tab.setPaginador("jqCiuP");
            
            tab.setGroupFields("NOMPAI,NOMDPT");
            tab.setGroupText("<b><i>País : {0}</i></b>,<b><i>Departamento : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false,false});
            tab.setGroupOrder("asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "350");
            tab.setOptions(map);

            map = new LinkedHashMap();
            map.put("6", "formatterEdit");
            map.put("7", "formatterDelete");
            tab.setFormatter(map);

            map = new LinkedHashMap();
            map.put("6","center");
            map.put("7","center");
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

    private void delCity() throws Exception {
        
        String ideciu = Util.getStrRequest("id", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        
        try {
            model.deleteLogBook("smaciu", ideciu, sesion);
            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);
            tra.commit();
        } catch (Exception e) {
            json.put("exito", "ERROR");
            json.put("msg", "No se pudo eliminar, existen otras estructuras que tienen esa ciudad");
            write(json);
            tra.rollback();
//            Util.logError(e);
        }
        write(json);
    }

    private void saveCity() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject formu = Util.getJsonRequest("formulario", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        try {

            tra.begin();
            Map    datos  = Util.map("smaciu", formu);
            String ideciu = Util.validStr(datos, "ideciu").toString();
            String codciu = Util.validStr(datos, "codciu").toString();
            String npqciu = Util.validStr(datos, "npqciu").toString();
            String nomciu = Util.validStr(datos, "nomciu").toString();

            if(ideciu.isEmpty()){
                sqlCmd = "select distinct codciu \n"
                       + "  from smaciu \n"
                       + " where codciu = '" + codciu + "' \n"
                       + "   and idedpt in ( \n"
                       + "                   select smadpt.idedpt \n"
                       + "                     from smapai \n"
                       + "                     join smadpt \n"
                       + "                       on smadpt.idepai = smapai.idepai \n"
                       + "                    where smapai.codcia = '" + model.getCodCia() + "' \n"
                       + "                  )";

                sqlCmd = (String) model.getData(sqlCmd, null);
                if(!sqlCmd.isEmpty()){

                    json.put("exito", "ERROR");
                    json.put("msg", "Ya existe ese código de ciudad");
                    tra.rollback();

                }else{

                    sqlCmd = "select distinct nomciu \n"
                           + "  from smapai \n"
                           + "  join smadpt \n"
                           + "    on smadpt.idepai = smapai.idepai \n"
                           + "  join smaciu \n"                           
                           + "    on smaciu.idedpt = smadpt.idedpt \n"
                           + "   and ( smaciu.npqciu = '" + npqciu + "' or \n"
                           + "         smaciu.nomciu = '" + nomciu + "' ) \n"
                           + " where smapai.codcia = '" + model.getCodCia() + "' \n";

                    sqlCmd = (String) model.getData(sqlCmd, null);
                    if(!sqlCmd.isEmpty()){

                        json.put("exito", "ERROR");
                        json.put("msg", "Ya existe ese nombre de ciudad");
                        tra.rollback();

                    }else{

                        ideciu = model.saveLogBook(datos, "smaciu", sesion);
                        json.put("exito", "OK");
                        json.put("msg", model.MSG_SAVE);
                        tra.commit();

                    }
                }

            } else {
                    model.updateLogBook(datos, "smaciu", ideciu.toString().trim(), sesion);
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

        String ideciu = Util.getStrRequest("id", request);
        try {

            List smaciu = smaCiu(ideciu);

            HashMap hasCiu = new HashMap();

            if (!smaciu.isEmpty()) {
                hasCiu = (HashMap) smaciu.get(0);
            }

            json.put("exito", "OK");
            json.put("smaciu", hasCiu);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        write(json);
    }

    private List smaCiu(String ideciu) {

        try {

            sqlCmd = "  select smaciu.ideciu \n" 
                   + "       , smaciu.idedpt \n" 
                   + "       , smaciu.codciu \n" 
                   + "       , smaciu.nomciu \n" 
                   + "       , smaciu.npqciu \n" 
                   + "       , smadpt.nomdpt \n" 
                   + "       , smapai.nompai \n" 
                   + "       , smapai.idepai \n" 
                   + "    from smapai \n" 
                   + "    join smadpt \n" 
                   + "      on smadpt.idepai = smapai.idepai \n"
                   + "    join smaciu \n" 
                   + "      on smaciu.idedpt = smadpt.idedpt \n"
                   + "   where smapai.codcia = '" + model.getCodCia() + "' \n";

            if (!ideciu.trim().isEmpty()) {
                sqlCmd += " and smaciu.ideciu = '" + ideciu + "' \n";
            }
            sqlCmd +=  "order by smapai.nompai \n"
                   +   "       , smadpt.nomdpt \n"
                   +   "       , smaciu.nomciu " ;
                    
            
            model.list(sqlCmd, null);
            return model.getList();
        } catch (Exception e) {
            Util.logError(e);
            return new LinkedList();
        }
    }

}
