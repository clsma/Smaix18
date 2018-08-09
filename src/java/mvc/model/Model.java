package mvc.model;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import mvc.exception.LogbookException;
import org.apache.poi.util.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;

public class Model implements Serializable {

    // Campos de configuracion de conexion
    private String jdbcDriver;
    private String databaseURL;
    private String databasePassword;
    private String databaseUser;
    private String formatDate = "MM/dd/yyyy";
    private String codcia;
    private String codprs;
    private String tpoprs;
    private String nroprs;
    private String nrousr;
    private String nomprs;
    private String apeprs;
    private String sessionId;
    private String sessionSxu;
    private String lemCia;
    private String thmCia;
    private String nomCia;
    private ClCompanyInformation company;
    String tit = "", tposd = "", codpr = "";
    int cont = 0;
    String a = "";
    // Campos de generacion de listas y objetos
    private List genericList;
    private int numColumns;
    private List columnNames;
    private String sequence;

    public void setColumnNames(List lista) {
        this.columnNames = lista;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * Configura el jdbcDriver
     *
     * @param jdbcDriver el jdbcDriver
     */
    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    /**
     * Configura el jdbcDriver
     *
     * @param jdbcDriver el jdbcDriver
     */
    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    /**
     * Configura el nombre del Usuario
     *
     * @param databaseUser el nombre del usrario
     */
    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    /**
     * Configura el Password del usuario
     *
     * @param Password el Password del usuario
     */
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    /**
     * Configura el formato de fechas para la Base de Datos
     *
     * @param formatDate el formato de fechas a manejar en la Base de Datos
     */
    public void setFormatDate(String formatDate) {
        this.formatDate = formatDate;
    }

    /**
     * Devuelve el jdbcDriver
     */
    public String getJdbcDriver() {
        return jdbcDriver;
    }

    /**
     * Devuelve el URL de la Base de datos
     */
    public String getDatabaseURL() {
        return databaseURL;
    }

    /**
     * Devuelve el Usuario Habilitado para la conexion a la Base de datos
     */
    public String getDatabaseUser() {
        return databaseUser;
    }

    /**
     * Devuelve el Codigo de Usuario actual en session
     */
    public String getCodPrs() {
        return codprs;
    }

    /**
     * Devuelve el Numero de persona del Usuario actual en session
     */
    public String getNroPrs() {
        return nroprs;
    }

    /**
     * Devuelve el Numero de usuario del Usuario actual en session
     */
    public String getNroUsr() {
        return nrousr;
    }

    /**
     * Devuelve el Codigo de Usuario actual en session
     */
    public String getTpoPrs() {
        return tpoprs;
    }

    /**
     * Devuelve el Codigo de la compa|ia actual en session
     */
    public String getCodCia() {
        return codcia;
    }

    /**
     * Devuelve el Password del usuario habilitado para conexion a la Base de
     * datos
     */
    public String getDatabasePassword() {
        return databasePassword;
    }

    /**
     * Devuelve el formato de fechas para la Base de Datos
     */
    public String getFormatDate() {
        return formatDate;
    }

    public int getNumColumns() {
        return numColumns;

    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    // Variable de inicio sesion 
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLemCia() {
        return lemCia;
    }

    public void setLemCia(String lemCia) {
        this.lemCia = lemCia;
    }

    public String getThmCia() {
        return thmCia;
    }

    public void setThmCia(String thmCia) {
        this.thmCia = thmCia;
    }

    public String getNomCia() {
        return nomCia;
    }

    public void setNomCia(String nomCia) {
        this.nomCia = nomCia;
    }

    public String getNomprs() {
        return nomprs;
    }

    public void setNomprs(String nomprs) {
        this.nomprs = nomprs;
    }

    public String getApeprs() {
        return apeprs;
    }

    public void setApeprs(String apeprs) {
        this.apeprs = apeprs;
    }

    public void setCompany(ClCompanyInformation company) {
        this.company = company;
    }

    public ClCompanyInformation getCompany() {
        return company;
    }

    public String getSessionSxu() {
        return sessionSxu;
    }

    public void setSessionSxu(String sessionSxu) {
        this.sessionSxu = sessionSxu;
    }

    /**
     * Devuelve el objeto Standar correspondiente al Obejto actual requerido
     *
     * @exception SQLException si aparece un error de base de datos
     */
    //=======================================================================
    //	Metodos de Listado en la Base de Datos
    //========================================================================
    /**
     * Devuelve los resultados (lista de objetos) de la busqueda de una clase
     * requerida
     */
    public List getList() {
        return genericList;
    }

    public synchronized void list(String query, Session sesion)
            throws SQLException {
        Map mapaux = null;
        List auxColumnNames = new ArrayList<String>();
        genericList = new LinkedList();
        columnNames = new ArrayList<String>();
        numColumns = 0;
        String dato = "";
        Session session = sesion == null ? HibernateUtil.getNewSession() : sesion;

        try {
            query = query.replaceAll("&#39;", "\'");
            query = query.replaceAll("&#34;", "\"");

            if (query.trim().endsWith(";")) {
                query = query.trim().substring(0, query.trim().length() - 1);
            }

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
            List<Map<String, Object>> listMapRs = sqlQuery.list();

            if (listMapRs != null && listMapRs.size() > 0) {

                boolean isFill = false;

                for (Map<String, Object> registro : listMapRs) {
                    numColumns = registro.values().size();
                    mapaux = new HashMap();

                    for (Map.Entry<String, Object> campo : registro.entrySet()) {
                        String key = campo.getKey().trim();
                        Object value = campo.getValue() != null
                                ? campo.getValue().toString().trim() : "";
                        dato = value.toString();
                        dato = dato.replace("'", "&#39;")
                                .replace("\"", "&#34;");
//                        dato = dato.replaceAll("\n", "\\\\n");
//                        dato = dato.replaceAll("\r", "\\\\r");
                        mapaux.put(key, dato);

                        if (!isFill) {
                            auxColumnNames.add(key);
                        }
                    }
                    isFill = true;
                    genericList.add(mapaux);
                }
                columnNames = auxColumnNames;
            }

        } catch (Exception e) {
            Util.logError(e);
            System.out.println("Consulta :=> " + query);
            if (e.getCause() != null) {
                throw new SQLException(e.getCause());
            } else {
                throw new SQLException(e.getCause());
            }
        } finally {
            if (sesion == null) {
                session.close();
            }
        }
    }

    @Deprecated
    public synchronized void listGenericHash(String query)
            throws SQLException {
        Hashtable auxHashtable = null;
        List auxColumnNames = new ArrayList<String>();
        PreparedStatement pstmt = null;
        genericList = new LinkedList();
        columnNames = new ArrayList<String>();
        numColumns = 0;
        String dato = "";
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            if (query.trim().endsWith(";")) {
                query = query.trim().substring(0, query.trim().length() - 1);
            }
            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
            List<Map<String, Object>> listMapRs = sqlQuery.list();

            if (listMapRs != null && listMapRs.size() > 0) {

                Object[] arrayRs;
                boolean isFill = false;

                for (Map<String, Object> registro : listMapRs) {
                    numColumns = registro.values().size();
                    auxHashtable = new Hashtable();

                    for (Map.Entry<String, Object> campo : registro.entrySet()) {
                        String key = campo.getKey().trim();
                        Object value = campo.getValue() != null
                                ? campo.getValue().toString().trim() : "";
                        dato = value.toString();

                        auxHashtable.put(key, dato.trim());

                        if (!isFill) {
                            auxColumnNames.add(key);
                        }
                    }
                    isFill = true;
                    genericList.add(auxHashtable);
                }
                columnNames = auxColumnNames;
            }

        } catch (HibernateException e) {
            Util.logError(e);
            System.out.println("Consulta :=> " + query);
            if (e.getCause() != null) {
                throw new SQLException(e.getCause());
            } else {
                throw new SQLException(e.getCause());
            }
        } catch (Exception e) {
            Util.logError(e);
            System.out.println("Consulta :=> " + query);
            throw new SQLException(e.getMessage());
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

    /**
     * Llama al metodo listGenericHash parametrizado
     *
     * @exception SQLException si aparece un error de base de datos
     */
    public synchronized void listGenericHashCLOB(String query)
            throws SQLException {
        Hashtable auxHashtable = null;
        List auxColumnNames = new ArrayList<String>();
        PreparedStatement pstmt = null;
        genericList = new LinkedList();
        columnNames = new ArrayList<String>();
        numColumns = 0;
        String dato = "";
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setResultTransformer(MyResultTransformer.INSTANCE);
            List<Map<String, Object>> listMapRs = sqlQuery.list();

            if (listMapRs != null && listMapRs.size() > 0) {

                Object[] arrayRs;
                boolean isFill = false;

                for (Map<String, Object> registro : listMapRs) {
                    numColumns = registro.values().size();
                    auxHashtable = new Hashtable();

                    for (Map.Entry<String, Object> campo : registro.entrySet()) {
                        String key = campo.getKey().trim();
                        Object value = campo.getValue() != null
                                ? campo.getValue().toString().trim() : "";
                        auxHashtable.put(key, value.toString().trim());

                        if (!isFill) {
                            auxColumnNames.add(key);
                        }
                    }
                    isFill = true;
                    genericList.add(auxHashtable);
                }
                columnNames = auxColumnNames;
            }

        } catch (HibernateException e) {
            Util.logError(e);
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            Util.logError(e);
            throw new SQLException(e.getMessage());
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

    /**
     * Llama al metodo ListGeneric parametrizado
     *
     * @exception SQLException si aparece un error de base de datos
     */
    public synchronized void listGeneric(String query) throws SQLException {
        List auxList = null;
        genericList = new LinkedList();

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {

            query = query.replaceAll("&#39;", "\'");
            query = query.replaceAll("&#34;", "\"");

            if (query.trim().endsWith(";")) {
                query = query.trim().substring(0, query.trim().length() - 1);
            }

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setResultTransformer(AliasToEntityOrderedMapResultTransformer.INSTANCE);
            List<Map<String, Object>> listMapRs = sqlQuery.list();

            if (listMapRs != null && listMapRs.size() > 0) {
                for (Map<String, Object> registro : listMapRs) {
                    auxList = new LinkedList();
                    for (Map.Entry<String, Object> campo : registro.entrySet()) {
                        String key = campo.getKey().trim();
                        Object value = campo.getValue() != null
                                ? campo.getValue().toString().trim() : "";
                        auxList.add(value);
                    }
                    genericList.add(auxList);
                }
            }

        } catch (HibernateException e) {
            Util.logError(e);
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            Util.logError(e);
            throw new SQLException(e.getMessage());
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

    /**
     * Configura el Codigo del usuario actual en session
     *
     * @param codprs codigo del objeto
     */
    public void setCodPrs(String codprs) {
        this.codprs = codprs;
    }

    /**
     * Configura el Numero de persona del usuario actual en session
     *
     * @param nroprs codigo del objeto
     */
    public void setNroPrs(String nroprs) {
        this.nroprs = nroprs;
    }

    /**
     * Configura el Numero de usuario del usuario actual en session
     *
     * @param nrousr codigo del objeto
     */
    public void setNroUsr(String nrousr) {
        this.nrousr = nrousr;
    }

    /**
     * Configura el tipo de usuario actual en session
     *
     * @param codprs codigo del objeto
     */
    public void setTpoPrs(String tpoprs) {
        this.tpoprs = tpoprs;
    }

    /**
     * Configura el codigo de la compa|ia actual en session
     *
     * @param codprs codigo del objeto
     */
    public void setCodCia(String codcia) {
        this.codcia = codcia;
    }

    @Deprecated
    public synchronized void save(String sqlCmd, Object[] values,
            String table, String operacion) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);

            int index = 0;
            for (Object value : values) {
                String type = value.getClass().getSimpleName();

                if ("String".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Integer".equals(type)) {
                    query.setParameter(index, (Integer) value);
                } else if ("Date".equals(type)) {
                    query.setParameter(index, new java.sql.Date(((Date) value).getTime()));
                } else if ("Double".equals(type)) {
                    query.setParameter(index, (Double) value);
                } else if ("FileInputStream".equals(type)) {
                    InputStream in = (InputStream) value;
                    byte[] bytes = IOUtils.toByteArray(in);
                    query.setParameter(index, bytes);
                } else if ("NULL".equals(type)) {
                    query.setParameter(index, null);
                } else {
                    throw new Exception("No se ha definido el tipo "
                            + "de dato para el valor con indice: " + index);
                }
                index++;
            }
            query.executeUpdate();
            // saveBitacora(sqlCmd, values, table, operacion, session);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        } catch (Exception e) {
            transaction.rollback();
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
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

    @Deprecated
    private String getSqlFinal(Object[] valores, String sql) {
        int i = 0;
        String aux = "";
        for (char a : sql.toCharArray()) {
            String car = String.valueOf(a);
            if (car.equals("?")) {
                String tipo = valores[i].getClass().getSimpleName();
                String replac = !(tipo.equals("Integer") || tipo.equals("Double")) ? ("'" + valores[i] + "'") : valores[i].toString();
                i++;
                aux += car.replace("?", replac);
            } else {
                aux += car;
            }
        }
//        System.out.println("Car: " + aux);
        return aux;

    }

    @Deprecated
    private void saveBitacora(
            String sqlCmd, Object[] values,
            final String table, final String operacion,
            Session session) throws Exception, SQLException {

        final String cadenaSql = getSqlFinal(values, sqlCmd);

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                CallableStatement callStmt = null;
                try {

                    callStmt = connection.prepareCall("{ ? = call "
                            + "SMA_SYSTEM_MANAGER."
                            + "INSERT_BITACORA( ?, ?, ?, ?, ?, ?, ? ) }");

                    callStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                    callStmt.setString(2, getCodCia());
                    callStmt.setString(3, getCodPrs());
                    callStmt.setString(4, getTpoPrs());
                    callStmt.setString(5, table);
                    callStmt.setString(6, cadenaSql.trim());
                    callStmt.setString(7, operacion);
                    callStmt.setString(8, "0");

                    //Ejecutamos procedimiento
                    callStmt.executeUpdate();
                    //Obtenemos los valores de salida
                    String exitoError = callStmt.getString(1);

                    if (!exitoError.trim().equals("1")) {
                        throw new SQLException("No "
                                + "fue posible registrar "
                                + "la bitacora, Detalle: " + exitoError);
                    }
                } finally {
                    if (callStmt != null) {
                        callStmt.close();
                    }
                }
            }
        });
    }

    @Deprecated
    public synchronized void update(String sqlCmd,
            Object[] values, String table) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);

            int index = 0;
            for (Object value : values) {
                String type = value.getClass().getSimpleName();

                if ("String".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Integer".equals(type)) {
                    query.setParameter(index, (Integer) value);
                } else if ("Date".equals(type)) {
                    query.setParameter(index, new java.sql.Date(((Date) value).getTime()));
                } else if ("Double".equals(type)) {
                    query.setParameter(index, (Double) value);
                } else if ("FileInputStream".equals(type)) {
                    InputStream in = (InputStream) value;
                    byte[] bytes = IOUtils.toByteArray(in);
                    query.setParameter(index, bytes);
                } else {
                    throw new Exception("No se ha definido el tipo "
                            + "de dato para el valor con indice: " + index);
                }
                index++;
            }
            int result = query.executeUpdate();
            //saveBitacora(sqlCmd, values, table, "U", session);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        } catch (Exception e) {
            transaction.rollback();
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
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

    @Deprecated
    public synchronized void delete(String sqlCmd,
            Object[] values, String table) throws Exception {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);

            int index = 0;
            for (Object value : values) {
                String type = value.getClass().getSimpleName();

                if ("String".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Integer".equals(type)) {
                    query.setParameter(index, (Integer) value);
                } else if ("Date".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Double".equals(type)) {
                    query.setParameter(index, (Double) value);
                } else {
                    throw new Exception("No se ha definido el tipo "
                            + "de dato para el valor con indice: " + index);
                }
                index++;
            }
            int result = query.executeUpdate();
            //  saveBitacora(sqlCmd, values, table, "D", session);
            transaction.commit();

        } catch (HibernateException e) {
            transaction.rollback();
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        } catch (Exception e) {
            transaction.rollback();
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
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

    public synchronized String getSequence(
            final String keyName) throws SQLException {

        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {

                    String ideKey = keyName.substring(3);
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {
                        //generamos el consecutivo
                        String sqlConsecutivo = "SELECT "
                                + "SMA_SYSTEM_MANAGER.GET_KEY"
                                + "( '" + ideKey + "', '" + keyName
                                + "') AS SEQUENCE FROM DUAL";

                        stmt = connection.prepareStatement(sqlConsecutivo);
                        rs = stmt.executeQuery();
                        rs.next();
                        setSequence(rs.getString("SEQUENCE"));

                    } catch (Exception e) {
                        Util.logError(e);
                        throw new SQLException("Error "
                                + "consultado la sequencia seq" + ideKey);
                    } finally {
                        if (rs != null) {
                            rs.close();
                        }
                        if (stmt != null) {
                            stmt.close();
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
        return sequence;
    }

    private String getStringValues(Object[] values) {

        String cadenaValues = " { ";

        for (Object value : values) {
            cadenaValues += "'" + value.toString() + "' | ";
        }
        cadenaValues = cadenaValues.
                substring(0, cadenaValues.length() - 2) + " }";

        return cadenaValues;
    }

    private String getSqlFixed(String sqlCmd) {
        int index = sqlCmd.indexOf("VALUES") + 6;

        String sqlFixed = sqlCmd.substring(0, index);

        return sqlFixed;
    }

    @Deprecated
    public synchronized void saveNotTransaction(String sqlCmd,
            Object[] values, String table, Session session) throws Exception {

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);

            int index = 0;
            for (Object value : values) {

                String type;
                if (value == null) {
                    type = "NULL";
                } else {
                    type = value.getClass().getSimpleName();
                }

                if ("String".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Integer".equals(type)) {
                    query.setParameter(index, (Integer) value);
                } else if ("Date".equals(type)) {
                    query.setParameter(index, new java.sql.Date(((Date) value).getTime()));
                } else if ("Double".equals(type)) {
                    query.setParameter(index, (Double) value);
                } else if ("FileInputStream".equals(type)) {
                    InputStream in = (InputStream) value;
                    byte[] bytes = IOUtils.toByteArray(in);
                    query.setParameter(index, bytes);
                } else if ("NULL".equals(type)) {
                    query.setParameter(index, null);
                } else {
                    throw new Exception("No se ha definido el tipo "
                            + "de dato para el valor con indice: " + index);
                }
                index++;
            }
            query.executeUpdate();
            //   saveBitacora(sqlCmd, values, table, "I", session);

        } catch (HibernateException e) {
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        } catch (Exception e) {
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        }
    }

    @Deprecated
    public synchronized void updateNotTransaction(String sqlCmd,
            Object[] values, String table, Session session) throws Exception {

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);

            int index = 0;
            for (Object value : values) {
                String type = value.getClass().getSimpleName();

                if ("String".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Integer".equals(type)) {
                    query.setParameter(index, (Integer) value);
                } else if ("Date".equals(type)) {
                    query.setParameter(index, new java.sql.Date(((Date) value).getTime()));
                } else if ("Double".equals(type)) {
                    query.setParameter(index, (Double) value);
                } else if ("FileInputStream".equals(type)) {
                    InputStream in = (InputStream) value;
                    byte[] bytes = IOUtils.toByteArray(in);
                    query.setParameter(index, bytes);
                } else {
                    throw new Exception("No se ha definido el tipo "
                            + "de dato para el valor con indice: " + index);
                }
                index++;
            }
            query.executeUpdate();
            //  saveBitacora(sqlCmd, values, table, "U", session);

        } catch (HibernateException e) {
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        } catch (Exception e) {
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        }
    }

    @Deprecated
    public synchronized void deleteNotTransaction(String sqlCmd,
            Object[] values, String table, Session session) throws Exception {

        try {
            SQLQuery query = session.createSQLQuery(sqlCmd);

            int index = 0;
            for (Object value : values) {
                String type = value.getClass().getSimpleName();

                if ("String".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Integer".equals(type)) {
                    query.setParameter(index, (Integer) value);
                } else if ("Date".equals(type)) {
                    query.setParameter(index, value.toString());
                } else if ("Double".equals(type)) {
                    query.setParameter(index, (Double) value);
                } else {
                    throw new Exception("No se ha definido el tipo "
                            + "de dato para el valor con indice: " + index);
                }
                index++;
            }
            query.executeUpdate();
            //  saveBitacora(sqlCmd, values, table, "D", session);

        } catch (HibernateException e) {
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        } catch (Exception e) {
            Util.logError(e);
            throw new LogbookException(e.getCause().toString());
        }
    }

    public synchronized
            String getSequenceNotTransaction(final String keyName,
                    Session session) throws SQLException {

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {

                String ideKey = keyName.substring(3);
                PreparedStatement stmt = null;
                ResultSet rs = null;

                try {
                    //generamos el consecutivo
                    String sqlConsecutivo = "SELECT "
                            + "SMA_SYSTEM_MANAGER.GET_KEY"
                            + "( '" + ideKey + "', '" + keyName
                            + "') AS SEQUENCE FROM DUAL";

                    stmt = connection.prepareStatement(sqlConsecutivo);
                    rs = stmt.executeQuery();
                    rs.next();
                    setSequence(rs.getString("SEQUENCE"));

                } catch (Exception e) {
                    Util.logError(e);
                    throw new SQLException("Error "
                            + "consultado la sequencia seq" + ideKey);
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            }
        });
        return sequence;
    }

    @Deprecated
    public void callSqlInsert(String dataInsert, String[] values,
            final String table) throws Exception, SQLException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        final String sqlFixed = getXmlSql(dataInsert, values);

        try {

            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {

                    CallableStatement callStmt = null;
                    try {
                        callStmt = connection.prepareCall("{ ? = call "
                                + "SMA_SYSTEM_MANAGER."
                                + "SQL_INSERT( ?, ?, ?, ?, ?, ? ) }");

                        callStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
                        callStmt.setString(2, getCodCia());
                        callStmt.setString(3, getCodPrs());
                        callStmt.setString(4, getTpoPrs());
                        callStmt.setString(5, table);
                        callStmt.setString(6, sqlFixed);
                        callStmt.setInt(7, 1);

                        //Ejecutamos procedimiento
                        callStmt.executeUpdate();
                        //Obtenemos los valores de salida
                        String exitoError = callStmt.getString(1);
                        if (!exitoError.trim().equals("1")) {
                            throw new SQLException("No fue "
                                    + "posible registrar la "
                                    + "bitacora, Detalle: " + exitoError);
                        }
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
    }

    @Deprecated
    private String getXmlSql(String dataInsert, String[] values) {
        String xmlSql = dataInsert.
                replaceAll("\\[", "<").replaceAll("]", ">");

        int i = 0;
        for (String value : values) {
            xmlSql = xmlSql.replaceFirst("\\?", values[i]);
            i++;
        }

        return xmlSql;
    }

    @Deprecated
    public synchronized void listGenericHashNotTransaction(
            String query, Session session) throws SQLException {

        Hashtable auxHashtable = null;
        List auxColumnNames = new ArrayList<String>();
        genericList = new LinkedList();

        try {

            SQLQuery sqlQuery = session.createSQLQuery(query);
            sqlQuery.setResultTransformer(
                    AliasToEntityOrderedMapResultTransformer.INSTANCE);
            List<Map<String, Object>> listMapRs = sqlQuery.list();

            if (listMapRs != null && listMapRs.size() > 0) {

                columnNames = new ArrayList<String>();
                Object[] arrayRs;
                boolean isFill = false;

                for (Map<String, Object> registro : listMapRs) {
                    numColumns = registro.values().size();
                    auxHashtable = new Hashtable();
                    for (Map.Entry<String, Object> campo : registro.entrySet()) {
                        String key = campo.getKey();
                        Object value = campo.getValue()
                                != null ? campo.getValue() : "";
                        auxHashtable.put(key, value.toString().trim());

                        if (!isFill) {
                            auxColumnNames.add(key);
                        }
                    }
                    isFill = true;
                    genericList.add(auxHashtable);
                }
                columnNames = auxColumnNames;
            }

        } catch (HibernateException e) {
            Util.logError(e);
            throw new HibernateException(e.getMessage());
        } catch (Exception e) {
            Util.logError(e);
            throw new SQLException(e.getMessage());
        }
    }

    public void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (HibernateException e2) {
                e2.printStackTrace();
                System.out.println("Error cerrando la session: " + e2);
            }
        }
    }

} // Fin Clase Model
