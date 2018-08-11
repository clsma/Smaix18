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
import mvc.util.ClsmaException;
import mvc.util.ClsmaTypeException;
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
            if(!(e instanceof ClsmaException))
                Util.logError(e);
        }
    }

    private void write(Object js) throws IOException {
        response.getWriter().write(JSONValue.toJSONString(js));

    }

    private void listCountries() throws Exception {
        try {
            String idepai = Util.getStrRequest("idepai", request);

            List smapai = getListaPaises(idepai);

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
        
        Transaction transaccion = null;
         
        try {
            Session sesion = this.getNewSession();
            transaccion = HibernateUtil.getNewTransaction(sesion);      
            transaccion.begin();
            model.deleteLogBook("smapai", nropai, sesion);
            json.put("exito", "OK");
            json.put("msg", model.MSG_DELETE);            
        } catch (Exception e) {              
            write(json);
            if (transaccion != null) {
                transaccion.rollback();
            }
            throw new ClsmaException(ClsmaTypeException.ERROR.getDescription(), "No se pudo eliminar, existen ciudades que tienen ese país", e);
            
            
        }finally{
            if (transaccion != null )
                transaccion.commit();
        }
        write(json);
    }

    private void saveCountries() throws Exception {
        JSONObject formu = Util.getJsonRequest("formulario", request);
        Transaction transaccion = null;
        openSqlCommand();
        try {
            Session sesion = this.getNewSession();
            transaccion = HibernateUtil.getNewTransaction(sesion);
            transaccion.begin();
            Map    datos  = Util.map("smapai", formu);
            String idepai = Util.validStr(datos, "idepai").toString();
            String codpai = Util.validStr(datos, "codpai").toString();
            String nompai =  Util.validStr(datos, "nompai").toString().toUpperCase();
                      
            if(idepai.isEmpty()){
                
                sqlCommand.append("select distinct codpai \n")
                        .append("  from smapai \n")
                        .append(" where codpai = '").append(codpai).append("'");

                sqlCmd = (String) model.getData(sqlCommand.toString(), null);
                
                if(sqlCmd.isEmpty()){
                   openSqlCommand();                                        
                    sqlCommand.append( "select distinct nompai \n")
                              .append( "  from smapai \n")
                              .append( " where UPPER(nompai) = '" )
                              .append( nompai ) 
                              .append(  "'");

                    sqlCmd = (String) model.getData(sqlCommand.toString(), null);
                    if(sqlCmd.isEmpty()){
                        datos.put("codcia", model.getCodCia());
                        idepai = model.saveLogBook(datos, "smapai", sesion);
                        json.put("exito", "OK");
                        json.put("msg", model.MSG_SAVE);
                    }else{                        
                        throw new ClsmaException(ClsmaTypeException.ERROR.getDescription(), "Ya existe ese nombre de país"); 
                    }
                }else{                      
                    throw new ClsmaException(ClsmaTypeException.ERROR.getDescription(), "Ya existe ese código de país");                                        
               }

            } else {                
                    model.updateLogBook(datos, "smapai", idepai.toString().trim(), sesion);
                    json.put("exito", "OK");
                    json.put("msg", model.MSG_SAVE);
                    
            }
             transaccion.commit();
        } catch (ClsmaException cle) {
             cle.writeJsonError(json);

             if(transaccion != null) 
                transaccion.rollback();             
        } catch (Exception e) {
            json.put("exito", "ERROR");
            json.put("msg", model.setError(e));
            
            if(transaccion != null) 
                transaccion.rollback();
            Util.logError(e);
        }finally{
            write(json);
        }
        
    }

    private void fillform() throws Exception {

        String nropai = Util.getStrRequest("id", request);
        try {

            List smapai = getListaPaises(nropai);

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

    private List getListaPaises(String idepai) {
        sqlCommand = new StringBuilder();
        try {
            sqlCommand.append("SELECT smapai.idepai, \n" )
                      .append("       smapai.codpai, \n" )          
                      .append("       smapai.isapai, \n" )
                      .append("	      smapai.isbpai, \n" )
                      .append("	      smapai.nompai, \n" )
                      .append("	      nvl(smapai.codcnt,'000') codcnt, \n")
                      .append("	      nvl(vewcnt.nomcnt,' ') nomcnt \n")
                      .append("  FROM smapai \n")
                      .append("LEFT JOIN vewcnt \n")
                      .append("       ON vewcnt.codcnt = smapai.codcnt \n");
                      
                      /*.append(       " smapai.codcia = '" )
                      .append(model.getCodCia() )
                      .append("' \n");*/

            if (!idepai.trim().isEmpty()) {
                sqlCommand.append(" WHERE smapai.idepai = '" );
                sqlCommand.append(  idepai );
                sqlCommand.append("' \n");
            }
            sqlCommand.append(  "ORDER BY smapai.nompai ");
            
            model.list(sqlCommand.toString(), null);
            return model.getList();
        } catch (Exception e) {
            Util.logError(e);
            return new LinkedList();
        }
    }

}

