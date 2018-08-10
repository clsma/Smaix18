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
 * @Archivo AdmCountriesAction
 */
public class AdmCountriesAction extends Action {

    private JSONObject json;
    private String sqlCmd;

    @Override
    public void run() throws ServletException, IOException {
        String event = Util.getStrRequest("event", request);
        json = new JSONObject();

        try {
            if (event.equals("LIST")) {
                listCountries();
            } else if (event.equals("DELETE")) {
                delCountries();
            } else if (event.equals("SAVE")) {
                saveCountries();
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

    private void listCountries() throws Exception {
        try {
            String idepai = Util.getStrRequest("idepai", request);

            List smapai = smaPai(idepai);

            jQgridTab tab = new jQgridTab();
            tab.setColumns(new String[]{"IDEPAI", "NOMCNT", "CODPAI", "ISAPAI", "ISBPAI", "NOMPAI", "EDIT", "DELETE"});
            tab.setTitles(new String[]{"Id", "Continente", "Código", "Isa", "Isb", "Nombre", "Editar", "Eliminar"});
            tab.setWidths(new int[]{0, 120, 90, 40, 40, 300, 50, 50});
            tab.setHiddens(new int[]{0});
            tab.setKeys(new int[]{0});
            tab.setSelector("jqPai");
            tab.setFilterToolbar(true);
            tab.setDataList(smapai);
            tab.setPaginador("jqPaiP");

            tab.setGroupFields("NOMCNT");
            tab.setGroupText("<b><i>Continente : {0}</i></b>");
            tab.setGroupCollapse(false);
            tab.setGroupColumnShow(new Boolean[]{false});
            tab.setGroupOrder("asc");

            LinkedHashMap map = new LinkedHashMap();
            map.put("align", "center");
            map.put("height", "200");
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

    private void delCountries() throws Exception {
        
        String nropai = Util.getStrRequest("id", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        
        try {
            model.deleteLogBook("smapai", nropai, sesion);
            json.put("exito", "OK");
            json.put("msg", model.MSG_SAVE);
            tra.commit();
        } catch (Exception e) {
            json.put("exito", "ERROR");
            json.put("msg", "No se pudo eliminar, existen ciudades que tienen ese país");
            write(json);
            tra.rollback();
//            Util.logError(e);
        }
        write(json);
    }

    private void saveCountries() throws Exception {
        JSONObject json = new JSONObject();
        JSONObject formu = Util.getJsonRequest("formulario", request);
        Session sesion = this.getNewSession();
        Transaction tra = HibernateUtil.getNewTransaction(sesion);
        try {

            tra.begin();
            Map    datos  = Util.map("smapai", formu);
            String idepai = Util.validStr(datos, "idepai").toString();
            String codpai = Util.validStr(datos, "codpai").toString();
            String nompai = Util.validStr(datos, "nompai").toString();
            
            
            if(idepai.isEmpty()){
                  
            
                sqlCmd = "select distinct codpai \n"
                       + "  from smapai \n"
                       + " where codcia = '" + model.getCodCia() + "' \n"
                       + "   and codpai = '" + codpai + "'";
             
                sqlCmd = (String) model.getData(sqlCmd, null);
                if(!sqlCmd.isEmpty()){

                    json.put("exito", "ERROR");
                    json.put("msg", "Ya existe ese código de país");
                    write(json);
                    tra.rollback();

                }else{

                    sqlCmd = "select distinct nompai \n"
                           + "  from smapai \n"
                           + " where codcia = '" + model.getCodCia() + "' \n"
                           + "   and nompai = '" + nompai + "'";

                    sqlCmd = (String) model.getData(sqlCmd, null);
                    if(!sqlCmd.isEmpty()){
                        json.put("exito", "ERROR");
                        json.put("msg", "Ya existe ese nombre de país");
                        write(json);
                        tra.rollback();
                    }else{
                        datos.put("codcia", model.getCodCia());
                        idepai = model.saveLogBook(datos, "smapai", sesion);
                        json.put("exito", "OK");
                        json.put("msg", model.MSG_SAVE);
                        tra.commit();
                    }
                }

            } else {
                    model.updateLogBook(datos, "smapai", idepai.toString().trim(), sesion);
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

        String nropai = Util.getStrRequest("id", request);
        try {

            List smapai = smaPai(nropai);

            HashMap hasLgn = new HashMap();

            if (!smapai.isEmpty()) {
                hasLgn = (HashMap) smapai.get(0);
            }

            json.put("exito", "OK");
            json.put("smapai", hasLgn);

        } catch (Exception e) {
            Util.logError(e);
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
        }
        write(json);
    }

    private List smaPai(String idepai) {

        try {

            sqlCmd = "   select smapai.idepai \n" 
                   + " 	      , smapai.codpai \n" 
                   + " 	      , smapai.isapai \n" 
                   + "	      , smapai.isbpai \n" 
                   + "	      , smapai.nompai \n" 
                   + "	      , nvl(smapai.codcnt,'000') codcnt \n"
                   + "	      , nvl(vewcnt.nomcnt,' ') nomcnt \n"
                   + "     from smapai \n"
                   + "left join vewcnt \n"
                   + "       on vewcnt.codcnt = smapai.codcnt \n"
                   + "    where smapai.codcia = '" + model.getCodCia() + "' \n";

            if (!idepai.trim().isEmpty()) {
                sqlCmd += " and smapai.idepai = '" + idepai + "' \n";
            }
            sqlCmd +=  "order by smapai.nompai ";
            
            model.list(sqlCmd, null);
            return model.getList();
        } catch (Exception e) {
            Util.logError(e);
            return new LinkedList();
        }
    }

}

