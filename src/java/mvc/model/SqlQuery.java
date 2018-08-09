/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import mvc.exception.LogbookException;
import oracle.jdbc.OraclePreparedStatement;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Carlos pinto jimenez
 */
public class SqlQuery {

    ModelSma model;

    public SqlQuery(ModelSma model) {
        this.model = model;
    }

    public static List dataTable(String table, Session sesion, ModelSma model) {

        try {

            String sqlCmd = " select tc.column_name nomfld, \n"
                    + "         tc.data_Type typfld,\n"
                    + "         tc.nullable nulfld,\n"
                    + "         tc.data_default deffld,\n"
                    + "         tc.data_scale sclfld\n"
                    + "    from All_Tab_Columns tc \n"
                    + "    join all_tables at\n"
                    + "      on at.table_name = tc.table_name\n"
                    + "   where lower( tc.table_name ) = '" + table.toLowerCase() + "'\n"
                    + "     and at.owner = user\n"
                    + "     and tc.owner = user";

            model.list(sqlCmd, sesion);
            return model.getList();

        } catch (Exception e) {
            Util.logError(e);
            return new LinkedList();
        }

    }

    private Map convertMap(Map datos) {
        Map aux = null;
        if (datos.isEmpty()) {
            return new HashMap();
        }
        aux = new HashMap();
        ArrayList<String> keys = Util.keys(datos);
        for (String a : keys) {
            aux.put(a.toLowerCase(), datos.get(a));
        }
        return aux;
    }

    public synchronized String saveMap(Map datos, String table, Session session) throws Exception {
        Session sesion = session == null ? HibernateUtil.getNewSession() : session;
        Transaction tran = null;
        String result_key = null;
        if (session == null) {
            tran = HibernateUtil.getNewTransaction(sesion);
        }

        if (datos == null) {
            throw new Exception("No se definieron los Datos");
        }
        datos = convertMap(datos);
        Util.setModel(model);

        Connection con;
        OraclePreparedStatement pst;
        String sql = "insert into " + table + " ( ", values = "( ";

        try {

            if (session == null) {
                tran.begin();
            }

            con = model.getConnection(sesion);

            int i = 1;
            Object value = null;
            String key;

            List data = dataTable(table, sesion, model);
            key = model.callFunctionOrProcedureNotTransaction("sma_system_manager.table_primary_key( '" + table + "')", sesion);

            if (datos.get(key) != null || datos.get(key.toUpperCase()) != null) {
                result_key = datos.get(key).toString();
            } else {
                result_key = model.callFunctionOrProcedureNotTransaction("sma_system_manager.get_key( '" + table.substring(3, 6) + "', '" + key + "')", sesion);
                datos.put(key.toLowerCase(), result_key);
            }
            datos.put("idesxu", model.getSessionSxu());

            Map aux;
            Map longs = new HashMap();
            for (Object a : data) {
                aux = (HashMap) a;
                sql += Util.validStr(aux, "NOMFLD").toLowerCase() + " , ";
                values += " ? , ";
            }
            sql = sql.substring(0, sql.length() - 2);
            values = values.substring(0, values.length() - 2);

            sql += " ) ";
            values += " ) ";
            String finalSql = sql + " values " + values;
            pst = (OraclePreparedStatement) con.prepareStatement(finalSql);

            String name, type, deflt, nul, scale;
            for (Object a : data) {
                aux = (HashMap) a;
                name = Util.validStr(aux, "NOMFLD").toLowerCase();
                type = Util.validStr(aux, "TYPFLD").toLowerCase();
                deflt = Util.validStr(aux, "DEFFLD");
                nul = Util.validStr(aux, "NULFLD");
                scale = Util.validStr(aux, "SCLFLD");

                if (type.equals("long")) {
                    longs.put(i, value);
                    i++;
                    continue;
                }

                value = datos.get(name);

                if (value == null) {
                    if (!nul.toLowerCase().equals("y")) {
                        if (deflt.contains("'")) {
                            value = deflt.replaceAll("'", "");
                        } else {
                            value = deflt.trim().isEmpty() ? " " : deflt;
                        }
                    }
                }

//                value = ((Map.Entry) it.next()).getValue();
                if (type.contains("varchar2") || type.contains("char") || type.contains("long") || type.contains("clob")) {
                    pst.setString(i, (value == null ? "" : value.toString()).replaceAll("&#39;", "").replaceAll("&#34;", ""));
                } else if (type.contains("date") || type.contains("timestamp")) {
                    if (value instanceof String) {
                        if (value.toString().equals("null")) {
                            pst.setDate(i, (java.sql.Date) null);
                        } else {
                            pst.setDate(i, new java.sql.Date(Util.toDate((String) value).getTime()));
                        }
                    } else if (value == null) {
                        pst.setDate(i, (java.sql.Date) null);
                    } else {
                        pst.setDate(i, new java.sql.Date(((Date) value).getTime()));
                    }
                } else if (type.contains("integer")) {
                    value = (value == null || value.toString().isEmpty()) ? "0" : value;
                    pst.setInt(i, Integer.valueOf((value + "")));
                } else if ((type.contains("number") || type.contains("double") || type.contains("numeric") || type.contains("float"))
                        && (scale.trim().isEmpty() || scale.trim().equals("0"))) {
                    value = (value == null || value.toString().isEmpty()) ? "0" : value;
                    pst.setInt(i, Double.valueOf((value + "")).intValue());
                } else if ((type.contains("number") || type.contains("double") || type.contains("numeric")) || type.contains("float") && Integer.valueOf(scale.trim()) > 0) {
                    value = (value == null || value.toString().isEmpty()) ? "0" : value;
                    pst.setDouble(i, Double.valueOf((value + "")));
                } else if (value instanceof FileInputStream || value instanceof ByteArrayInputStream || type.contains("file") || type.contains("blob")) {
                    if (value == null) {
                        pst.setNull(i, Types.BLOB);
                    } else if (value instanceof File) {
                        pst.setBytes(i, IOUtils.toByteArray(new FileInputStream((File) value)));
                    } else {
                        pst.setBytes(i, IOUtils.toByteArray((FileInputStream) value));
                    }
                }
                i++;

            }

            if (!longs.isEmpty()) {
                Iterator it = ((Map) longs).entrySet().iterator();
                Object entry = null;
                while (it.hasNext()) {
                    entry = ((Map.Entry) it.next());
                    key = ((Map.Entry) entry).getKey().toString();
                    value = ((Map.Entry) entry).getValue();
                    pst.setString(Integer.valueOf(key), value.toString());
                }
            }

            pst.executeUpdate();

            if (session == null) {
                tran.commit();
            }
        } catch (Exception e) {

            if (session == null) {
                tran.rollback();
                sesion.close();
            }
            Util.logError(e);

            throw new LogbookException(e);
        }
        return result_key;
    }

    public synchronized void updateMap(Map datos, String table, String where, Session session) throws Exception {
        Session sesion = session == null ? HibernateUtil.getNewSession() : session;
        Transaction tran = null;
        if (session == null) {
            tran = HibernateUtil.getNewTransaction(sesion);
        }

        if (datos == null) {
            throw new Exception("No se definieron los Datos");
        }
        datos = convertMap(datos);
        Util.setModel(model);

        Connection con;
        OraclePreparedStatement pst;
        String sql = "update " + table + " set ";

        try {

            if (session == null) {
                tran.begin();
            }

            con = model.getConnection(sesion);

            int i = 1;
            Object value = null;
            String key;

            List data = dataTable(table, sesion, model);
            String keyTable = model.callFunctionOrProcedureNotTransaction("sma_system_manager.table_primary_key( upper( '" + table + "' ) )", sesion);

            Map aux;
            Map longs = new HashMap();
            ArrayList<String> keys = Util.keys(datos);
            String nomkey;
            for (Object a : data) {
                aux = (HashMap) a;
                nomkey = Util.validStr(aux, "NOMFLD").toLowerCase();
                if (Util.arrayHasObject(keys, nomkey)) {
                    sql += nomkey + " = ? , ";
                }
            }
            sql = sql.substring(0, sql.length() - 2);

            sql += " where " + model
                    .callFunctionOrProcedureNotTransaction(" sma_system_manager.make_where( '" + where + "' , '" + keyTable + "' , '" + table + "') ", sesion);

            pst = (OraclePreparedStatement) con.prepareStatement(sql);

            String name, type, deflt, nul, scale;
            for (Object a : data) {
                aux = (HashMap) a;
                name = Util.validStr(aux, "NOMFLD").toLowerCase();
                type = Util.validStr(aux, "TYPFLD").toLowerCase();
                deflt = Util.validStr(aux, "DEFFLD");
                nul = Util.validStr(aux, "NULFLD");
                scale = Util.validStr(aux, "SCLFLD");

                if (!Util.arrayHasObject(keys, name)) {
                    continue;
                }

                value = datos.get(name);

                if (value == null) {
                    if (!nul.toLowerCase().equals("y")) {
                        if (deflt.contains("'")) {
                            value = deflt.replaceAll("'", "");
                        }
                    }
                }

                if (type.equals("long")) {
                    longs.put(i, value);
                    i++;
                    continue;
                }

                if (type.contains("varchar2") || type.contains("char") || type.contains("long") || type.contains("clob")) {
                    pst.setString(i, (String) value);
                } else if (type.contains("date") || type.contains("timestamp")) {
                    if (value instanceof String) {
                        if (value.toString().equals("null")) {
                            pst.setDate(i, (java.sql.Date) null);
                        } else {
                            pst.setDate(i, new java.sql.Date(Util.toDate((String) value).getTime()));
                        }
                    } else if (value == null) {
                        pst.setDate(i, (java.sql.Date) null);
                    } else {
                        pst.setDate(i, new java.sql.Date(((Date) value).getTime()));
                    }
                } else if (type.contains("integer")) {
                    pst.setInt(i, Integer.valueOf((value + "")));
                } else if ((type.contains("number") || type.contains("double") || type.contains("numeric"))
                        && (scale.trim().isEmpty() || scale.trim().equals("0"))) {
                    pst.setInt(i, Double.valueOf((value + "")).intValue());
                } else if ((type.contains("number") || type.contains("double") || type.contains("numeric")) && Integer.valueOf(scale.trim()) > 0) {
                    pst.setDouble(i, Double.valueOf((value + "")));
                } else if (value instanceof FileInputStream || value instanceof ByteArrayInputStream || type.contains("file") || type.contains("blob")) {
                    if (value == null) {
                        pst.setNull(i, Types.BLOB);
                    } else if (value instanceof File) {
                        pst.setBytes(i, IOUtils.toByteArray(new FileInputStream((File) value)));
                    } else {
                        pst.setBytes(i, IOUtils.toByteArray((FileInputStream) value));
                    }
                }
                i++;

            }
            if (!longs.isEmpty()) {
                Iterator it = ((Map) longs).entrySet().iterator();
                Object entry = null;
                while (it.hasNext()) {
                    entry = ((Map.Entry) it.next());
                    key = ((Map.Entry) entry).getKey().toString();
                    value = ((Map.Entry) entry).getValue();
                    pst.setString(Integer.valueOf(key), value.toString());
                }
            }

            pst.executeUpdate();

            if (session == null) {
                tran.commit();
            }
        } catch (Exception e) {

            if (session == null) {
                tran.rollback();
                sesion.close();
            }
            Util.logError(e);

            throw new LogbookException(e);
        }

    }

    public synchronized void deleteMap(String table, String where, Session session) throws Exception {
        Session sesion = session == null ? HibernateUtil.getNewSession() : session;
        Transaction tran = null;
        if (session == null) {
            tran = HibernateUtil.getNewTransaction(sesion);
        }

        Util.setModel(model);

        Connection con;
        OraclePreparedStatement pst;
        String sql = "delete from " + table + " where ";

        try {

            if (session == null) {
                tran.begin();
            }

            con = model.getConnection(sesion);

            String keyTable = model.callFunctionOrProcedureNotTransaction("sma_system_manager.table_primary_key( upper( '" + table + "' ) )", sesion);

            sql += model
                    .callFunctionOrProcedureNotTransaction(" sma_system_manager.make_where( '" + where + "' , '" + keyTable + "' , '" + table + "') ", sesion);

            pst = (OraclePreparedStatement) con.prepareStatement(sql);

            pst.executeUpdate();

            if (session == null) {
                tran.commit();
            }
        } catch (Exception e) {

            if (session == null) {
                tran.rollback();
                sesion.close();
            }
            Util.logError(e);

            throw new LogbookException(e);
        }

    }

}
