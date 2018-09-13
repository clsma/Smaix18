package mvc.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mvc.exception.LogbookException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.json.simple.JSONObject;

/**
 * Esta clase es una adaptacion de las necesidades de la logica de modelo para
 * la aplicacion de la compañia SMA. Toma como base a clase Generica o Base
 * Model.
 */
public class ModelSma extends Model implements Serializable {

    private String agnprs = "";
    private String prdprs = "";
    private String agnnxt = "";
    private String prdnxt = "";
    private String agnbef = "";
    private String prdbef = "";

    private String formatDateShow = "dd/MM/yyyy";

    public static final String MSG_PROCESS_EXECUTED  = "Proceso ejecutado de forma correcta";
    public static final String MSG_PROCESS_ERROR  = "Error al ejecutar el proceso";
    
    public static final String MSG_UPDATE = "Registro actualizado con éxito";
    public static final String MSG_DELETE = "Registro eliminado con éxito";
    public static final String MSG_SAVE = "Registro exitoso";
    public static final String MSG_ERROR = "Resultado inesperado. Comuníquese con el administrador del sistema";
    public static final String CODE_ERROR = "Consulte : ( ## ).";
    public static String TYPE_ERROR = "ERROR";
    private HttpServletRequest request;
    protected ServletContext application;
    public String ipAddresRequest;
    private SqlQuery sqlHandler;
    private boolean logged;

    public ModelSma() {
        sqlHandler = new SqlQuery(this);
    }

    // Metodos de configuracion y de base de datos
    // ========================================================================
    /**
     * Devuelve el A|o de procesamiento actual en session
     */
    public String getAgnPrs() {
        return agnprs;
    }

    /**/
    // Devuelve el Periodo de procesamiento actual en session
    public String getPrdPrs() {
        return prdprs;
    }

    /**
     * Devuelve el A|o proximo de procesamiento en session
     */
    public String getAgnNxt() {
        return agnnxt;
    }

    /**
     * Devuelve el Periodo proximo de procesamiento en session
     */
    public String getPrdNxt() {
        return prdnxt;
    }

    /**
     * Configura el a|o de procesamiento actual en session
     *
     * @param agnprs del objeto
     */
    public void setAgnPrs(String agnprs) {
        this.agnprs = agnprs;
    }

    /**
     * Configura el periodo de procesamiento actual en session
     *
     * @param prdprs del objeto
     */
    public void setPrdPrs(String prdprs) {
        this.prdprs = prdprs;
    }

    /**
     * Configura el a|o proximo de procesamiento actual en session
     *
     * @param agnprs del objeto
     */
    public void setAgnNxt(String agnnxt) {
        this.agnnxt = agnnxt;
    }

    /**
     * Devuelve el A|o anterior de procesamiento en session
     */
    public String getAgnBef() {
        return agnbef;
    }

    /**
     * Devuelve el Periodo anterior de procesamiento en session
     */
    public String getPrdBef() {
        return prdbef;
    }

    /**
     * Configura el a|o proximo de procesamiento actual en session
     *
     * @param agnprs del objeto
     */
    public void setAgnBef(String agnbef) {
        this.agnbef = agnbef;
    }

    /**
     * Configura el periodo proximo de procesamiento actual en session
     *
     * @param prdprs del objeto
     */
    public void setPrdBef(String prdbef) {
        this.prdbef = prdbef;
    }

    /**
     * Configura el periodo proximo de procesamiento actual en session
     *
     * @param prdprs del objeto
     */
    public void setPrdNxt(String prdnxt) {
        this.prdnxt = prdnxt;
    }

    /**
     * Configura el formato de fechas para mostrar en las vistas
     *
     * @param formatDateShow el formato de fechas para mostrar en las vistas
     */
    public void setFormatDateShow(String formatDateShow) {
        this.formatDateShow = formatDateShow;
    }

    /**
     * Devuelve el formato de fechas para mostrar en las vistas
     */
    public String getFormatDateShow() {
        return formatDateShow;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    /**
     * *************** MODIFICAIONES 09 SEPT 2009 *********************
     */
    /**
     * Metodo que realiza una consulta rapida de un dato en la base de datos
     *
     * @param field El campo a consultar
     * @param table La tabla de donde se va a consultar
     * @param where La condicion de la consulta
     * @return String Cadena con el dato consultado
     */
    @Deprecated
    public String getData(String field, String table, String where) throws SQLException {
        try {
            String sqlQuery = "select " + field + " from " + table + " "
                    + (!where.equals("") ? "where " + where : "");
            this.list(sqlQuery, null);
            List lstDat = this.getList();
            if (lstDat.size() > 0) {
                Map hshDat = (HashMap) lstDat.get(0);

                /* Reeemplazar espacios dobles por uno solo. 
                 Se hace nada mas para la variable field para poder 
                 comprobar si tine un alias  (as)  
                 */
                while (field.indexOf("  ") > 0) {
                    field = field.replace("  ", " ");
                }

                //verificar si el campo solicitado tiene un alias (as)
                if (field.toLowerCase().indexOf(" as ") > 0) {
                    String array_[] = field.toUpperCase().split(" AS ");
                    field = array_[1].trim();
                } else {
                    field = field.replaceAll(" ", "");
                }

                field = field.replace("distinct", "");//MODIFICACION 15 enero 2010 (EFRAIN BLANCO)

                return hshDat.get(field.toUpperCase()).toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new SQLException("Error Clase [ModelSma] Metodo [getData()] " + e);
        }
    }

    @Deprecated
    public String getDataNotTransaction(String field, String table, String where, Session sesion) throws SQLException {
        try {
            String sqlQuery = "select " + field + " from " + table + " "
                    + (!where.equals("") ? "where " + where : "");
            this.list(sqlQuery, sesion);
            List lstDat = this.getList();
            if (lstDat.size() > 0) {
                Map hshDat = (HashMap) lstDat.get(0);

                /* Reeemplazar espacios dobles por uno solo. 
                 Se hace nada mas para la variable field para poder 
                 comprobar si tine un alias  (as)  
                 */
                while (field.indexOf("  ") > 0) {
                    field = field.replace("  ", " ");
                }

                //verificar si el campo solicitado tiene un alias (as)
                if (field.toLowerCase().indexOf(" as ") > 0) {
                    String array_[] = field.toUpperCase().split(" AS ");
                    field = array_[1].trim();
                } else {
                    field = field.replaceAll(" ", "");
                }

                field = field.replace("distinct", "");//MODIFICACION 15 enero 2010 (EFRAIN BLANCO)

                return hshDat.get(field.toUpperCase()).toString();
            } else {
                return "";
            }
        } catch (Exception e) {
            throw new SQLException("Error Clase [ModelSma] Metodo [getData()] " + e);
        }
    }

    public int getInt(String field, String table, String where, Session sesion)
            throws SQLException {

        String resp = "";

        if (sesion == null) {
            resp = getData(field, table, where);
        } else {
            resp = getDataNotTransaction(field, table, where, sesion);
        }

        return resp.equals("") ? 0 : Integer.valueOf(resp);
    }

    public double getDouble(String field, String table, String where, Session sesion)
            throws SQLException {

        String resp = "";

        if (sesion == null) {
            resp = getData(field, table, where);
        } else {
            resp = getDataNotTransaction(field, table, where, sesion);
        }

        return resp.equals("") ? 0.0 : Double.valueOf(resp);
    }

    public List<LinkedHashMap> listarSP(final String procedure,
            final Object[] parameters, Session session) throws Exception {

        final List<LinkedHashMap> resultList = new ArrayList<LinkedHashMap>();
        Session sesion = session == null ? HibernateUtil.getNewSession() : session;

        try {

            sesion.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    int index = 1;
                    ResultSet rs = null;
                    CallableStatement pstmt = null;
                    try {

                        rs = null;
                        pstmt = connection.
                                prepareCall("BEGIN " + procedure + "; END;");

                        for (Object parameter : parameters) {
                            String type = parameter.getClass().getSimpleName();

                            if ("String".equals(type)) {
                                pstmt.setString(index, parameter.toString());
                            } else if ("Integer".equals(type)) {
                                pstmt.setInt(index, (Integer) parameter);
                            } else if ("Date".equals(type)) {
                                pstmt.setDate(index,
                                        new java.sql.Date(
                                                ((Date) parameter).getTime()));
                            } else if ("Double".equals(type)) {
                                pstmt.setDouble(index, (Double) parameter);
                            } else {
                                throw new SQLException("No se "
                                        + "ha definido el tipo de dato "
                                        + "para el valor con indice: " + index);
                            }
                            index++;
                        }
                        //REF CURSORs
                        pstmt.registerOutParameter(index, OracleTypes.CURSOR);

                        pstmt.executeQuery();
                        rs = ((OracleCallableStatement) pstmt).getCursor(index);

                        LinkedHashMap fila;
                        String dato;
                        while (rs.next()) {
                            fila = new LinkedHashMap();

                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                                dato = rs.getString(i);
                                dato = dato == null ? "" : dato;
                                fila.put(rs.getMetaData().getColumnName(i), dato.trim());
                            }

                            if (!fila.isEmpty()) {
                                resultList.add(fila);
                            }
                        }

                    } catch (Exception e) {
                        Util.logError(e);
                        throw new SQLException(e.getMessage());
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    }
                }
            });

        } finally {
            if (session == null) {
                try {
                    sesion.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
        return resultList;
    }

    public List<LinkedHashMap> listarSP(final String procedure,
            final Object[] parameters) throws Exception {

        final List<LinkedHashMap> resultList = new ArrayList<LinkedHashMap>();
        Session session = HibernateUtil.getNewSession();
        final ArrayList<String> columns = new ArrayList<String>();

        try {

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    int index = 1;
                    ResultSet rs = null;
                    CallableStatement pstmt = null;
                    try {

                        rs = null;
                        pstmt = connection.
                                prepareCall("BEGIN " + procedure + "; END;");

                        for (Object parameter : parameters) {
                            String type = parameter.getClass().getSimpleName();

                            if ("String".equals(type)) {
                                pstmt.setString(index, parameter.toString());
                            } else if ("Integer".equals(type)) {
                                pstmt.setInt(index, (Integer) parameter);
                            } else if ("Date".equals(type)) {
                                pstmt.setDate(index,
                                        new java.sql.Date(
                                                ((Date) parameter).getTime()));
                            } else if ("Double".equals(type)) {
                                pstmt.setDouble(index, (Double) parameter);
                            } else {
                                throw new SQLException("No se "
                                        + "ha definido el tipo de dato "
                                        + "para el valor con indice: " + index);
                            }
                            index++;
                        }
                        //REF CURSORs
                        pstmt.registerOutParameter(index, OracleTypes.CURSOR);

                        pstmt.executeQuery();
                        rs = ((OracleCallableStatement) pstmt).getCursor(index);

                        LinkedHashMap fila;
                        String dato;
                        boolean isAdded = false;
                        while (rs.next()) {
                            fila = new LinkedHashMap();

                            for (int i = 1; i <= rs.getMetaData().getColumnCount(); ++i) {
                                dato = rs.getString(i) == null ? "" : rs.getString(i);
                                dato = dato.replaceAll("\"", "&quot;");
                                dato = dato.replaceAll("#", "\\#");
                                dato = dato.replaceAll("\n", "\\\\n");
                                dato = dato.replaceAll("\r", "\\\\r");
                                fila.put(rs.getMetaData().getColumnName(i), dato.trim());

                                if (!isAdded) {
                                    columns.add(rs.getMetaData().getColumnName(i));
                                }

                            }
                            isAdded = true;
                            if (!fila.isEmpty()) {
                                resultList.add(fila);
                            }
                        }

                    } catch (Exception e) {
                        Util.logError(e);
                        throw new SQLException(e.getMessage());
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    }
                }
            });

        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
        setColumnNames(columns);
        return resultList;
    }

    public Object[] validLogin(final String codcia,
            final String codprs, final String pswprs) throws SQLException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        final Object[] resultExiste = new Object[2];

        try {

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {

                    CallableStatement callStmt = null;
                    try {
                        callStmt = connection.
                                prepareCall("{ CALL SMA_SYSTEM_LOGIN."
                                        + "USER_LOGIN( ?, ?, ?, ?, ?, ?, ? )}");

                        callStmt.setString(1, codcia);
                        callStmt.setString(2, codprs);
                        callStmt.setString(3, pswprs);
                        callStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
                        callStmt.registerOutParameter(5, java.sql.Types.VARCHAR);
                        callStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
                        callStmt.registerOutParameter(7, java.sql.Types.VARCHAR);

                        //Ejecutamos procedimiento
                        callStmt.executeUpdate();

                        //Obtenemos los valores de salida
                        resultExiste[0] = callStmt.getString(7);
                        resultExiste[1] = callStmt.getString(4);

                    } catch (Exception ex) {
                        resultExiste[0] = "false";
                        Util.logError(ex);
                    } finally {
                        if (callStmt != null) {
                            callStmt.close();
                        }
                    }
                }
            });
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
        return resultExiste;
    }

    public boolean existeRegistro(String tabla, String where) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        String sqlCmd = "SELECT COUNT(0) FROM " + tabla + " WHERE " + where;

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);
            BigDecimal count = (BigDecimal) query.uniqueResult();
            if (count.intValue() >= 1) {
                return true;
            } else {
                return false;
            }
        } catch (HibernateException e) {
            Util.logError(e);
            throw e;
        } catch (Exception e) {
            Util.logError(e);
            throw e;
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
    }

    public String callFunction(final String function,
            final Object[] values) throws Exception, SQLException {

        final Session session = HibernateUtil.getSessionFactory().openSession();
        final String[] returnValue = new String[1];
        returnValue[0] = "ERROR";

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                CallableStatement callStmt = null;
                int index = 2;
                try {

                    callStmt = connection.
                            prepareCall("{ ? = call " + function + " }");

                    callStmt.registerOutParameter(1, java.sql.Types.VARCHAR);

                    for (Object value : values) {
                        String type = value.getClass().getSimpleName();
                        if ("String".equals(type)) {
                            callStmt.setString(index, value.toString());
                        } else if ("Integer".equals(type)) {
                            callStmt.setInt(index, (Integer) value);
                        } else if ("Date".equals(type)) {
                            callStmt.setDate(index, new java.sql.Date(((Date) value).getTime()));
                        } else if ("Double".equals(type)) {
                            callStmt.setDouble(index, (Double) value);
                        } else {
                            throw new SQLException("No se ha "
                                    + "definido el tipo de dato "
                                    + "para el valor con indice: " + index);
                        }
                        index++;
                    }
                    //Ejecutamos funcion
                    callStmt.executeUpdate();
                    //Obtenemos los valores de salida
                    returnValue[0] = callStmt.getString(1);

                } catch (Exception e) {
                    Util.logError(e);
                    throw new SQLException(e.getMessage());
                } finally {
                    if (callStmt != null) {
                        callStmt.close();
                    }
                    session.close();
                }
            }
        });
        return returnValue[0];
    }

    /**
     * ejecuta el metodo load para obtener un objeto del tipo especificado
     *
     * @param type
     * @param srlzbl
     * @return Objeto obtenido
     * @throws Exception
     * @autor Efrain Blanco
     * @Fecha creación: 13/05/2014
     * @Fecha Modificación: 13/05/2014
     */
    public Object load(Class type, Serializable srlzbl) throws Exception {
        Object entidad = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            entidad = session.get(type, srlzbl);
        } catch (HibernateException e) {
            Util.logError(e);
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
        return entidad;
    }

    public Serializable saveEntity(Object entity) throws Exception {
        String pkEntity = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            pkEntity = (String) session.save(entity);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la Creación/Edición del registro");
        } catch (Exception e) {
            transaction.rollback();
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la creación/Edición del registro");
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
        return pkEntity;
    }

    public void updateEntity(Object entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(entity);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la Creación/Edición del registro");
        } catch (Exception e) {
            transaction.rollback();
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la creación/Edición del registro");
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
    }

    public void updateEntity(Object entity, Session session) throws Exception {

        try {
            session.update(entity);
        } catch (HibernateException e) {
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la Creación/Edición del registro");
        } catch (Exception e) {
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la creación/Edición del registro");
        }
    }

    public void deleteEntity(Object entity) throws Exception {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(entity);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la Creación/Edición del registro");
        } catch (Exception e) {
            transaction.rollback();
            Util.logError(e);
            throw new Exception("Ha ocurrido un error "
                    + "en la creación/Edición del registro");
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException e2) {
                    e2.printStackTrace();
                    System.out.println("Error cerrando la session: " + e2);
                }
            }
        }
    }

    public List<LinkedHashMap> getDatosCalendario(String codaxk) throws Exception {
        String sqlCmd = "SMA_ACADEMIC.calendaractivitysearch(?, ?, ?, ?, ?)";

        Object[] inParameters = new Object[4];
        inParameters[0] = getCodCia();
        inParameters[1] = getCodPrs();
        inParameters[2] = "ALL";
        inParameters[3] = codaxk;

        List<LinkedHashMap> lista = listarSP(sqlCmd, inParameters);
        return lista;
    }

    // de la forma callFunctionOrProcedure("nombrepaquete.nombrefuncion('a','b','c')");
    public synchronized String callFunctionOrProcedure(final String function) throws Exception {
        final ArrayList array = new ArrayList();
        CallableStatement cst = null;
        Session sesion = HibernateUtil.getNewSession();
        Transaction tran = HibernateUtil.getNewTransaction(sesion);
        try {
            tran.begin();

            Connection con = getConnection(sesion);
            cst = con.prepareCall("{ ? =  call " + function + " } ");
            cst.registerOutParameter(1, Types.VARCHAR);

            cst.executeUpdate();
            //Obtenemos los valores de salida
            String exitoError = cst.getString(1);
            array.add(exitoError);
            tran.commit();

        } catch (SQLException e) {
            Util.logError(e);
            tran.rollback();
            throw new LogbookException(e.getMessage());
        } finally {
            sesion.close();
            cst.close();

        }
        return array.get(0).toString();

    }

    public static Connection getConnection(Session sesion) {
        final Map conn = new HashMap();
        try {

            (sesion == null ? HibernateUtil.getNewSession() : sesion).doWork(new Work() {

                @Override
                public void execute(Connection cnctn) throws SQLException {
                    conn.put("con", cnctn);
                }
            });

        } catch (Exception e) {
            throw new HibernateException(e);
        }
        return (Connection) conn.get("con");
    }

    public synchronized String callFunctionOrProcedureNotTransaction(final String function, Session session) throws Exception {
        final ArrayList array = new ArrayList();
        CallableStatement cst = null;
        Session sesion = (session == null ? HibernateUtil.getNewSession() : session);
        Transaction tran = HibernateUtil.getNewTransaction(sesion);
        try {
            if (session == null) {
                tran.begin();
            }

            Connection con = getConnection(session);
            cst = con.prepareCall("{ ? =  call " + function + " } ");
            cst.registerOutParameter(1, Types.VARCHAR);

            cst.executeUpdate();
            //Obtenemos los valores de salida
            String exitoError = cst.getString(1);
            array.add(exitoError);
            if (session == null) {
                tran.commit();
            }

        } catch (SQLException e) {
            Util.logError(e);
            if (session == null) {
                tran.rollback();
            }
            throw new LogbookException(e.getMessage());
        } finally {
            if (session == null) {
                session.close();
            }
            cst.close();

        }

        return array.get(0).toString();
    }

    public synchronized void callProcedure(final String procedure, final Session session) {
        final Session sesAux;
        if (session == null) {
            sesAux = HibernateUtil.getNewSession();
        } else {
            sesAux = session;
        }
        final Transaction trans = HibernateUtil.getNewTransaction(sesAux);

        if (session == null) {
            trans.begin();
        }
        sesAux.doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                CallableStatement pstmt = null;
                try {
                    //connection.setAutoCommit(false);
                    pstmt = connection.
                            prepareCall("begin " + procedure + "; end;");

                    pstmt.executeUpdate();
                    if (session == null) {
                        trans.commit();
                    }

                } catch (SQLException e) {
                    if (session == null) {
                        trans.rollback();
                    }
                    Util.logError(e);
                    throw new SQLException(e.getMessage());
                } finally {
                    if (pstmt != null) {
                        pstmt.close();
                        if (session == null) {
                            sesAux.close();
                        }
                    }
                }
            }
        });

    }

    public String getXmllogBook(Map data) throws UnsupportedEncodingException {

        Iterator it = data.entrySet().iterator();
        Object val = null;
        String aux = "<record>";

        while (it.hasNext()) {
            Object entry = it.next();
            String key = ((Map.Entry) entry).getKey().toString().toLowerCase();

            Object valor = ((Map.Entry) entry).getValue();

            String clas = "";
            if (valor == null) {
                clas = "String";
            } else {
                clas = valor.getClass().getSimpleName();
            }

            if (clas.equals("String") || clas.equals("Date")) {

                if (clas.equals("String")) {

//                    val = "\"" + (valor == null ? "" : valor).toString();
//                    val = URLDecoder.decode(val.toString(), "UTF-8").replace("<", "&lt;").replace(">", "&gt;") + "\"";
                    val = (valor == null ? "" : valor).toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
//                    val = URLDecoder.decode(val.toString(), "UTF-8").replace("<", "&lt;").replace(">", "&gt;");
                    val = StringEscapeUtils.unescapeHtml(val.toString()).replaceAll("[']", "&quoti;");
                } else if (clas.equals("Date")) {

//                    val = "\"" + Util.dateFormat((Date) valor, "dd/MM/YY") + "\"";
                    val = Util.dateFormat((Date) valor, "dd/MM/yyyy");

                }
            } else {
//                val = "\"" + valor + "\"";
                val = valor;
            }

//            aux += "<" + key + " value =" + val + " />";
            aux += "<" + key + "><![CDATA[" + val + "]]>" + "</" + key + ">";
        }
        if (!aux.toLowerCase().contains("idesxu")) {
//            aux += "<idesxu value=\"" + getSessionSxu() + "\"/>";
            aux += "<idesxu><![CDATA[" + getSessionSxu() + "]]></idesxu>";
        }

//        aux += "<numiprequest value=\"" + ipAddresRequest + "\"/>";
        String ip = ipAddresRequest == null ? request.getRemoteAddr() : ipAddresRequest;
        String caller = sm.getCallerClassName(3).trim().toLowerCase().contains(".Adm") ? sm.getCallerClassName(3) : sm.getCallerClassName(4);
        aux += "<numiprequest><![CDATA[" + ipAddresRequest + "]]></numiprequest>";
        aux += "<nomcaller><![CDATA[" + caller + "]]></nomcaller>";
        aux += "</record>";

        return aux;
    }

    public String getData(String idebas, String smabas, String string, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class MySecurityManager extends SecurityManager {

        public String getCallerClassName(int callStackDepth) {
            return getClassContext()[callStackDepth].getName();
        }
    }

    static MySecurityManager sm = new MySecurityManager();

    public synchronized String saveLogBook(Map data, String tabla, Session sesion) throws Exception {
        Map stp = callStoredProcedure("sma_system_manager.add_column("
                + "                                        p_table => '" + tabla + "',"
                + "                                        p_field => 'idesxu',"
                + "                                        p_data_type => 'idesxuvarchar2(20)',"
                + "                                        p_return => ?)", 3, sesion);

        if (!stp.get("1").toString().equals("Exito")) {
            throw new LogbookException("Error (LOGBOOK) Alter table" + stp.get("1") + "(LOGBOOK)");
        }

        String sql = getXmllogBook(data);
        String sqlCmd = "sma_system_manager.logbook( p_table =>   '" + tabla + "'  , "
                + "p_values => '" + sql + "' , "
                + "p_idesxu => '" + getSessionSxu() + "')";
        String retorno = "";

        if (sesion == null) {
            retorno = callFunctionOrProcedure(sqlCmd);
        } else {
            retorno = callFunctionOrProcedureNotTransaction(sqlCmd, sesion);
        }

        if (retorno.toUpperCase().contains("ERROR")) {
            throw new LogbookException(retorno);
        }

        return retorno.split("/")[1];

    }

    public synchronized void updateLogBook(Map data, String tabla, String pkey, Session sesion) throws Exception {

        Map stp = callStoredProcedure("sma_system_manager.add_column("
                + "                                        p_table => '" + tabla + "',"
                + "                                        p_field => 'idesxu',"
                + "                                        p_data_type => 'varchar2(20)',"
                + "                                        p_return => ?)", 3, sesion);

        if (!stp.get("1").toString().equals("Exito")) {
            throw new LogbookException("Error (logbook) Alter table" + stp.get("1"));
        }

        String sql = getXmllogBook(data);
        String sqlCmd = "sma_system_manager.logbook( p_table =>   '" + tabla + "'  , \n"
                + "p_values => '" + sql + "' ,"
                + "p_key=> '" + pkey + "' , "
                + "p_idesxu => '" + getSessionSxu() + "',"
                + "p_work =>'U')";

        String retorno = "";
        if (sesion == null) {
            retorno = callFunctionOrProcedure(sqlCmd);
        } else {
            retorno = callFunctionOrProcedureNotTransaction(sqlCmd, sesion);
        }

        if (retorno.toUpperCase().indexOf("ERROR") != -1) {
            throw new LogbookException(retorno);
        }

    }

    public synchronized void deleteLogBook(String tabla, String pkey, Session sesion) throws Exception {

        Map stp = callStoredProcedure("sma_system_manager.add_column("
                + "                                        p_table => '" + tabla + "',"
                + "                                        p_field => 'idesxu',"
                + "                                        p_data_type => 'varchar2(20)',"
                + "                                        p_return => ?)", 3, sesion);

        if (!stp.get("1").toString().equals("Exito")) {
            throw new LogbookException("Error (logbook) Alter table" + stp.get("1"));
        }

        String sql = "<record><numiprequest value=\"" + request.getRemoteAddr() + "\"/>"
                + "<idesxu value=\"" + getSessionSxu() + "\"/></record>";

        String sqlCmd = "sma_system_manager.logbook( p_table =>   '" + tabla + "'  ,"
                + "p_key=>'" + pkey + "', \n"
                + "p_idesxu => '" + getSessionSxu() + "' ,"
                + "p_work=>'D',"
                + "p_values=>'" + sql + "')";

        String retorno = "";
        if (sesion == null) {
            retorno = callFunctionOrProcedure(sqlCmd);
        } else {
            retorno = callFunctionOrProcedureNotTransaction(sqlCmd, sesion);
        }

        if (retorno.toUpperCase().indexOf("ERROR") != -1) {
            throw new LogbookException(retorno);
        }

    }

    public synchronized String SCPNextState(Object... args) throws Exception {
        String tpofje = (String) args[0];
        String codfja = (String) args[1];
        Session sesion = (Session) args[2];
        String pgmfje = args.length > 3 ? ((String) args[3]) : null;
        String sqlCmd = "";
        String nrofje = "";

        if (sesion != null) {

            sqlCmd = "codfje='SCP' and tpofje='" + tpofje + "'";
            if (pgmfje != null) {
                sqlCmd += " and pgmfje = '" + pgmfje + "'";
            }

            nrofje = getDataNotTransaction("NROFJE", "smafje", sqlCmd, sesion);
            sqlCmd = "sma_planning.application_states_current('" + nrofje + "','" + codfja + "')";
            sqlCmd = callFunctionOrProcedureNotTransaction(sqlCmd, sesion);
        } else {

            sqlCmd = "codfje='SCP' and tpofje='" + tpofje + "'";
            if (pgmfje != null) {
                sqlCmd += " and pgmfje = '" + pgmfje + "'";
            }

            nrofje = getData("NROFJE", "smafje", sqlCmd);
            sqlCmd = "sma_planning.application_states_current('" + nrofje + "','" + codfja + "')";
            sqlCmd = callFunctionOrProcedure(sqlCmd);
        }
        if (sqlCmd == null || sqlCmd.trim().equals("NULL")) {
            throw new Exception("No existe estado para el flujo " + nrofje + " del tipo " + tpofje + " ");
        }
        return (sqlCmd);

    }

    public synchronized String CXPNextState(Object... args) throws Exception {
        String sqlCmd = "";
        String nrofje = "";
        String tpofje = (String) args[0];
        String codfja = (String) args[1];
        Session sesion = (Session) args[2];
        String pgmfje = (args.length > 3) ? args[3].toString() : "PGM";

        if (sesion != null) {
            nrofje = getDataNotTransaction("NROFJE", "smafje", "codfje='CXP' and tpofje='" + tpofje + "' and pgmfje = '" + pgmfje + "'", sesion);
            sqlCmd = "sma_planning.application_states_current('" + nrofje + "','" + codfja + "')";
            sqlCmd = callFunctionOrProcedureNotTransaction(sqlCmd, sesion);
        } else {
            nrofje = getData("NROFJE", "smafje", "codfje='CXP' and tpofje='" + tpofje + "' and pgmfje = '" + pgmfje + "'");
            sqlCmd = "sma_planning.application_states_current('" + nrofje + "','" + codfja + "')";
            sqlCmd = callFunctionOrProcedure(sqlCmd);
        }
        if (sqlCmd == null || sqlCmd.trim().equals("NULL")) {
            throw new Exception("No existe estado para el flujo " + nrofje + " del tipo " + tpofje + " ");
        }
        return (sqlCmd);

    }

    public synchronized JSONObject generatorReport(Object... args) {
        final Map datos = (HashMap) args[0];
        final String nombreJasper = (String) args[1];
        final ServletContext init = (ServletContext) args[2];

        String ruta_init = null;
        if (args.length > 3) {
            ruta_init = (String) args[3];
        }

        String rutaImage = "";
        String cia_theme = getCompany().getSTYLE();

        String rut_ = ruta_init == null ? route("IREPORT", null) : ruta_init;
        final String sep = File.separator;

        ruta_init = getCompany().getApplication().getAttribute("localPath").toString() + sep + "reportes" + sep + "Jasper_Reports_UDS" + sep;
        rutaImage = getCompany().getApplication().getAttribute("application").toString() + sep + "utility" + sep + "reportes" + sep + "Jasper_Reports_UDS" + sep + "image" + sep;

        if (!Util.existFileorDir(rutaImage)) {
            new File(rutaImage).mkdir();
        }

        final String img_report = rutaImage;
        final String where = ruta_init;

        final JSONObject jsonResp = new JSONObject();
        Session sesion = HibernateUtil.getSessionFactory().openSession();

        sesion.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try {
                    String subReport = where + "templates" + sep + "Sub_reportes" + sep;
                    String pdfExport = where + "pdf" + sep;

                    datos.put("SUBREPORT_DIR", subReport);
                    datos.put("prutaImagen", img_report);
                    datos.put("pcodcia", getCodCia());
                    datos.put("pnroprs", getNroPrs());
                    datos.put("pnrousr", getNroUsr());
                    datos.put("pcodprs", getCodPrs());
                    datos.put("pphoto", getCompany().getApplication().getAttribute("photoPath"));
                    JasperReport jasperReport;
                    JasperPrint jasperPrint;

                    String consec = getSequence("NROGRT");
                    jsonResp.put("respuesta", "1");
                    jsonResp.put("nomrpt", consec);
                    jasperReport = null;
                    System.out.println(datos.get("ptxtxml") + " -> " + datos);
                    System.setProperty("java.awt.headless", "true");
                    jasperReport = (JasperReport) JRLoader.loadObject(new File(where + "templates" + sep + nombreJasper));
                    jasperPrint = JasperFillManager.fillReport(jasperReport, datos, connection);
                    JasperExportManager.exportReportToPdfFile(jasperPrint, pdfExport + consec + ".pdf");

                } catch (Exception ex) {
                    Util.logError(ex);
                    System.out.println("Error : " + ex.getMessage());

                    try {
                        jsonResp.put("respuesta", "0");
                        jsonResp.put("nomrpt", setError(ex));
                    } catch (Exception ex1) {
                        ex1.printStackTrace();
                    }
                }

            }
        });

        return jsonResp;

    }

    public synchronized JSONObject genReport(Object... args) {

        String nrogrt = (String) args[0];
        final Map datos = (HashMap) args[1];
        final ServletContext init = (ServletContext) args[2];
        String ruta_init = null;
//        if (args.length > 3) {
//            ruta_init = (String) args[3];
//        }
        Session sesion = args.length > 3 ? (Session) args[3] : HibernateUtil.getNewSession();

        String rutaImage = "";
        String cia_theme = getCompany().getSTYLE();

        String rut_ = ruta_init == null ? route("IREPORT", null) : ruta_init;
        final String sep = File.separator;

        ruta_init = getCompany().getApplication().getAttribute("localPath").toString() + sep + "reportes" + sep + "Jasper_Reports_UDS" + sep;
        rutaImage = getCompany().getApplication().getAttribute("application").toString() + sep + "utility" + sep + "reportes" + sep + "Jasper_Reports_UDS" + sep + "image" + sep;

        if (!Util.existFileorDir(rutaImage)) {
            new File(rutaImage).mkdir();
        }

        final JSONObject jsonResp = new JSONObject();

        try {
            String sql = "select smairp.nomirp,smairp.codcia  , smairp.titirp "
                    + "from smairp "
                    + "where ideirp='" + nrogrt + "'";

            listGenericHash(sql);
            String nomRpt = "";
            String titRpt = "";
            if (!getList().isEmpty()) {
                nomRpt = ((Hashtable) getList().get(0)).get("NOMIRP").toString();
                titRpt = ((Hashtable) getList().get(0)).get("TITIRP").toString();
            } else {
                jsonResp.put("respuesta", "0");
                jsonResp.put("nomrpt", "No existe Registro en la tabla de reportes " + nrogrt);
                return jsonResp;
            }

            final String img_report = rutaImage;
            final String nombreJasper = nomRpt;
            final String where = ruta_init;
            final String titirp = titRpt;

            sesion.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    try {
                        String subReport = where + "templates" + sep + "Sub_reportes" + sep;
                        String pdfExport = where + "pdf" + sep;

                        if (!Util.existFileorDir(pdfExport)) {
                            new File(pdfExport).mkdirs();
                        }

                        datos.put("SUBREPORT_DIR", subReport);
                        datos.put("prutaImagen", img_report);
                        datos.put("pcodcia", getCodCia());
                        datos.put("pnroprs", getNroPrs());
                        datos.put("pnrousr", getNroUsr());
                        datos.put("pcodprs", getCodPrs());
                        datos.put("pphoto", getCompany().getApplication().getAttribute("photoPath"));
                        datos.put("ptitrpt", titirp);
                        JasperReport jasperReport;
                        JasperPrint jasperPrint;

                        String consec = getSequence("NROGRT");
                        jsonResp.put("respuesta", "1");
                        jsonResp.put("nomrpt", consec);
                        jasperReport = null;
                        System.out.println(datos.get("ptxtxml"));
                        jasperReport = (JasperReport) JRLoader.loadObject(new File(where + "templates" + sep + nombreJasper));
                        jasperPrint = JasperFillManager.fillReport(jasperReport, datos, connection);
                        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfExport + consec + ".pdf");
                    } catch (JRException ex) {
                        Util.logError(ex);
                        if (ex.getCause() instanceof FileNotFoundException) {
                            jsonResp.put("respuesta", "0");
                            jsonResp.put("nomrpt", "No se encontró plantilla del reporte jasper en la aplicación.");
                            return;
                        }

                        System.out.println("Error : " + ex.getMessage());
                        try {
                            jsonResp.put("respuesta", "0");
                            jsonResp.put("nomrpt", setError(ex));
                        } catch (Exception ex1) {
                            ex1.printStackTrace();
                        }
                    }

                }
            });
        } catch (Exception e) {
            jsonResp.put("respuesta", "0");
            jsonResp.put("nomrpt", MSG_ERROR);
        } finally {
            if (args.length <= 3) {
                sesion.close();
            }

        }

        return jsonResp;

    }

    public Object getData(String sqlCmd, Session sesion) throws SQLException {
        List exit = new ArrayList();

        list(sqlCmd, sesion);

        if (getList().isEmpty()) {
            return "";
        }

        if (getList().size() > 1) {

            for (Object a : getList()) {
                exit.add(((HashMap) a).get(getColumnNames().get(0)).toString());
            }
            return exit;
        } else {
            return ((HashMap) getList().get(0)).get(getColumnNames().get(0)).toString();
        }

    }

    public Map copy(String table, String where, Session sesion) throws ServletException {
        Map datos = new HashMap();

        String sqlCmd = "select c.column_name\n"
                + "        from user_tables t\n"
                + "        join ALL_TAB_COLUMNS c\n"
                + "          on c.table_name = t.table_name\n"
                + "       where t.TABLE_NAME='" + table.toUpperCase() + "'\n"
                + "         and owner = user";

        try {

            List cols = (ArrayList) getData(sqlCmd, sesion);

            sqlCmd = "select * from " + table + " where " + (where.equals("") ? "1=1" : where);

            list(sqlCmd, sesion);
            List data = getList();
            if (data.isEmpty()) {
                return null;
            }

            for (Object h : cols) {
                datos.put(h.toString(), Util.validObj((HashMap) data.get(0), h.toString()));

            }

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException(e.getMessage());
        }

        return datos;
    }

    public String setError(Object... args) {

        StringWriter sw = new StringWriter();
        Exception ex = (Exception) args[0];
        if (ex.getCause() != null) {
            ex.getCause().printStackTrace(new PrintWriter(sw));
        } else {
            ex.printStackTrace(new PrintWriter(sw));
        }

        String tabla = (args.length > 1) ? (String) args[1] : "";
        String err = sw.toString();
        String cause = ex.getCause() != null ? ex.getCause().toString() : ex.getLocalizedMessage();
        cause = cause.length() > 100 ? cause.substring(0, 100) : cause;
        try {

//            err = err.replaceAll("<", "-").replaceAll(">", "-").replaceAll("'", "-").replaceAll("\"", "-");
            Map datos = new HashMap();

            datos.put("sqlerr", err.length() >= 2000 ? err.substring(0, 2000) : err);
            datos.put("tpoprs", getTpoPrs());
            datos.put("coderr", "0000");
            datos.put("codprs", tabla);
            datos.put("codcia", getCodCia());
            datos.put("prcerr", "insert_err_app");
            datos.put("dsperr", cause);
            datos.put("fcherr", new Date());
            datos.put("stderr", "Incluido..");
            String nroerr = saveLogBook(datos, "smaerr", null);

            String aux = "";
            if (!(aux = LogbookException.resultInfo(err, "~Fixed~")).equals("")
                    && !LogbookException.resultFixed(aux, "~Fixed~").equals("")) {

                ModelSma.TYPE_ERROR = "INFO";
                return LogbookException.resultFixed(aux, "~Fixed~");
            }
            return ModelSma.MSG_ERROR + " - " + (ModelSma.CODE_ERROR.replace("##", nroerr));

        } catch (Exception e) {
            Util.logError(e);
            System.out.println("Error: " + err);
            return ModelSma.MSG_ERROR;
        }
    }

    public Hashtable load(String tabla, String where, Session sesion) throws Exception {
        try {

            String sqlCmd = "select * from  " + tabla + "  where " + where;
            if (sesion == null) {
                listGenericHash(sqlCmd);
            } else {
                listGenericHashNotTransaction(sqlCmd, sesion);
            }

            if (!getList().isEmpty()) {
                return (Hashtable) getList().get(0);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return new Hashtable();
    }

    public Map loadSingleRecord(String tabla, String where, Session sesion) throws Exception {
        try {

            String sqlCmd = "select * from "
                    + (tabla.contains("(") ? "table" : "")
                    + "( " + tabla + " )  where " + where;
            if (sesion == null) {
                list(sqlCmd, null);
            } else {
                list(sqlCmd, sesion);
            }

            if (!getList().isEmpty()) {
                return (HashMap) getList().get(0);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return new HashMap();
    }

    public Map getSingleRecord(String query, String[] campos, String where, Session sesion) throws Exception {
        try {

            String sqlCmd ;
            
             if(query.toLowerCase().contains(" from "))
                    sqlCmd = query;
             else{
                    StringBuilder sb = new StringBuilder();
                       for (String n : campos) { 
                           if (sb.length() > 0) 
                               sb.append(',');
                           sb.append(n);
                       }
                       sqlCmd = "select "+ sb.toString() + " from " + query + "  where " + where;
             }
             
            
            if (sesion == null) {
                list(sqlCmd, null);
            } else {
                list(sqlCmd, sesion);
            }

            if (!getList().isEmpty()) {
                return (HashMap) getList().get(0);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
        return new HashMap();
    }
    

    public synchronized Map callStoredProcedure(final String query, final int num, final Session sesion) {

        final Session ses = (sesion == null)
                ? HibernateUtil.getNewSession()
                : sesion;
        final Transaction trans = HibernateUtil.getNewTransaction(ses);
        final Map mapa = new HashMap();

        if (sesion == null) {
            trans.begin();
        }
        ses.doWork(new Work() {

            @Override
            public void execute(Connection cnctn) throws SQLException {

                int times = charCount(query, "?");
                CallableStatement call = null;
                try {

                    call = cnctn.
                            prepareCall("BEGIN " + query + "; END;");

                    for (int i = 1; i <= times; i++) {
                        call.registerOutParameter(i, Types.VARCHAR);
                    }

                    call.executeQuery();

                    for (int i = 1; i <= times; i++) {
                        mapa.put(i + "", call.getObject(i));
                    }
                    if (sesion == null) {
                        trans.commit();
                    }
                    mapa.put("exito", "OK");
                } catch (Exception e) {
                    Util.logError(e);
                    mapa.put("exito", "ERROR");
                    mapa.put("msg", e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
//                    throw new SQLException(e);
                    if (sesion == null) {
                        trans.rollback();
                    }
                } finally {
                    call.close();

                    if (sesion == null) {
                        ses.close();
                    }
                }

            }
        });
        return mapa;
    }

    public synchronized int charCount(String str, String char_) {

        if (str.indexOf(char_) == -1) {
            return 0;
        }

        return (str.length() - (str.replace(char_.toString(), "").length()));

    }

    public synchronized String route(String ruta, Session sesion) {

        try {
            String rout = (String) getData("select vlrrta from smarta where nrorta = '" + ruta + "'", sesion);
            if (rout.equals("SERVER")) {
                return getCompany().getApplication().getAttribute("localPath").toString();
            }

            return rout;
        } catch (Exception e) {
            Util.logError(e);
            return getCompany().getApplication().getAttribute("localPath").toString();
        }
    }

    public synchronized String callFunction(String function, Session sesion) throws Exception {

        return sesion == null
                ? callFunctionOrProcedure(function)
                : callFunctionOrProcedureNotTransaction(function, sesion);

    }

    public String save(Map datos, String table, Session sesion) throws Exception {
        return sqlHandler.saveMap(datos, table, sesion);

    }

    public void update(Map datos, String table, String where, Session sesion) throws Exception {
        sqlHandler.updateMap(datos, table, where, sesion);
    }

    public void deletes(String table, String where, Session sesion) throws Exception {
        sqlHandler.deleteMap(table, where, sesion);
    }

    public byte[] bytesImgDB(String consulta, Session session) throws SQLException {

        Session sesion = session == null ? HibernateUtil.getNewSession() : session;

        Connection con = getConnection(sesion);
        CallableStatement call = con.prepareCall(consulta);
        call.execute();
        ResultSet rs = call.getResultSet();
        byte[] bytes = null;
        if (rs != null) {
            rs.next();
            Blob blob = rs.getBlob(1);
            int len = (int) blob.length();
            bytes = blob.getBytes(1, len);
            blob.free();
            return bytes;
        }
        return null;

    }

    public synchronized String getClob(String query, Session sesion) throws Exception {

        Session ses = sesion == null ? HibernateUtil.getNewSession() : sesion;
        Connection con = getConnection(ses);

        try {

            PreparedStatement pst = con.prepareCall(query);

            ResultSet rs = pst.executeQuery();

            if (rs != null && rs.next()) {
                Clob cl = rs.getClob(1);
                if (cl == null) {
                    return "";
                }
                return cl.getSubString(1, (int) cl.length());
            }

        } catch (Exception e) {
            Util.logError(e);
            throw new LogbookException(e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
        } finally {
            if (sesion == null) {
                ses.close();
            }
        }

        return null;

    }

    public synchronized boolean userHasRole(String tposgu, String nrousr, Session session) {

        try {

            nrousr = nrousr == null ? getNroUsr() : nrousr;
            String tpo = "'" + tposgu + "'";

            if (tposgu.indexOf(",") != -1) {
                tpo = "";
                for (String a : tposgu.split(",")) {
                    tpo += ",'" + a + "'";
                }
                tpo = tpo.substring(1);
            }

            String sqlCmd = "select nrosgu "
                    + "  from table( sma_system_roles.user_roles( p_nrousr => '" + nrousr + "' ) )"
                    + " where tposgu in (" + tpo + ")";

            list(sqlCmd, session);
            return !getList().isEmpty();

        } catch (Exception e) {
            Util.logError(e);
            return false;
        }

    }

} // End Class ModelSma
