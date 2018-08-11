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
        JSONObject formHttp = Util.getJsonRequest("formulario", request);
        Transaction transaccion = null;
        try {
            Session sesion = this.getNewSession();
            transaccion = HibernateUtil.getNewTransaction(sesion);
            transaccion.begin();
            Map    datos  = Util.map("smaciu", formHttp);
            String ideciu = Util.validStr(datos, "ideciu").toString();
            String codciu = Util.validStr(datos, "codciu").toString();
            String npqciu = Util.validStr(datos, "npqciu").toString();
            String nomciu = Util.validStr(datos, "nomciu").toString();
            openSqlCommand();
            if(ideciu.isEmpty()){
                sqlCommand.append("select distinct codciu \n")
                       .append( "  from smaciu \n")
                       .append( " where codciu = '" + codciu + "' \n")
                       .append( "   and idedpt in ( \n")
                       .append( "                   select smadpt.idedpt \n")
                       .append( "                     from smapai \n")
                       .append( "                     join smadpt \n")
                       .append( "                       on smadpt.idepai = smapai.idepai \n")
                     //  .append( "                    where smapai.codcia = '" + model.getCodCia() + "' \n")
                       .append( "                  )");

                sqlCmd = (String) model.getData(sqlCommand.toString(), null);
                if(!sqlCmd.isEmpty()){
                    throw new ClsmaException(ClsmaTypeException.ERROR, "Ya existe ese código de ciudad");                   
                }else{
                    openSqlCommand();
                    sqlCommand.append("select distinct nomciu \n")
                            .append("  from smapai \n")
                            .append("  join smadpt \n")
                            .append("    on smadpt.idepai = smapai.idepai \n")
                            .append("  join smaciu \n")
                            .append("    on smaciu.idedpt = smadpt.idedpt \n")
                            .append("   and ( smaciu.npqciu = '" + npqciu + "' or \n")
                            .append("         smaciu.nomciu = '" + nomciu + "' ) \n");
                            //append(" where smapai.codcia = '" + model.getCodCia() + "' \n");

                    sqlCmd = (String) model.getData(sqlCommand.toString(), null);
                    if(!sqlCmd.isEmpty()){
                        throw new ClsmaException(ClsmaTypeException.ERROR, "Ya existe ese nombre de ciudad"); 
                    }else{
                        ideciu = model.saveLogBook(datos, "smaciu", sesion);
                        json.put("exito", "OK");
                        json.put("msg", model.MSG_SAVE);
                    }
                }

            } else {
                    model.updateLogBook(datos, "smaciu", ideciu.toString().trim(), sesion);
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
        openSqlCommand();
        try {
        sqlCommand.append( "  select smaciu.ideciu \n" )
                   .append( "       , smaciu.idedpt \n" )
                   .append( "       , smaciu.codciu \n" )
                   .append( "       , smaciu.nomciu \n" )
                   .append( "       , smaciu.npqciu \n" )
                   .append( "       , smadpt.nomdpt \n" )
                   .append( "       , smapai.nompai \n" )
                   .append( "       , smapai.idepai \n" )
                   .append( "    from smapai \n" )
                   .append( "    join smadpt \n" )
                   .append( "      on smadpt.idepai = smapai.idepai \n")
                   .append( "    join smaciu \n" )
                   .append( "      on smaciu.idedpt = smadpt.idedpt \n");
                   //.append( "   where smapai.codcia = '" + model.getCodCia() + "' \n");

            if (!ideciu.trim().isEmpty()) {
                sqlCommand.append( " WHERE  smaciu.ideciu = '" + ideciu + "' \n");
            }
            sqlCommand.append("order by smapai.nompai \n")
                      .append("       , smadpt.nomdpt \n")
                      .append("       , smaciu.nomciu " );
                                
            model.list(sqlCommand.toString(), null);
            return model.getList();
        } catch (Exception e) {
            Util.logError(e);
            return new LinkedList();
        }
    }

}
