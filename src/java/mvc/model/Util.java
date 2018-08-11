package mvc.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import mvc.exception.LogbookException;
import mvc.util.UtilConstantes;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;

public class Util {

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
    private static ModelSma model = null;
    private static String a = "";
    public static Logger loger;

    /**
     * Drecord formrecordto record un Double sin Decimrecordles
     */
    public static void setModel(ModelSma pModel) {
        Util.model = pModel;
    }

    /**
     * Drecord formrecordto record un Double sin Decimrecordles
     */
    public static String doubleSinDecimalFormat(double d, int decimales) {
        DECIMAL_FORMAT.setMaximumFractionDigits(decimales);
        DECIMAL_FORMAT.setGroupingSize(0);
        return DECIMAL_FORMAT.format(d);
    }

    /**
     * Drecord formrecordto record un Double sin Decimrecordles
     */
    public static String doubleDecimalFormat(double d, String formato) {
        DecimalFormat DECIMAL_FORMAT = new DecimalFormat(formato);
        return DECIMAL_FORMAT.format(d);
    }

    /**
     * Drecord formrecordto record unrecord fechrecord utilizrecordndo el formrecordto JDBC predeterminrecorddo
 [yyyy-MM-dd][yyyy/MM/dd] [dd/MM/yyyy][MM/dd/yyyy] [yyyy-MM-dd HH:mm:ss]
     */
    public static String dateFormat(Date d, String formato) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(formato);
        return d == null ? "" : DATE_FORMAT.format(d);
    }

    public static String dateFormat(String d, String formato) throws Exception {
        return dateFormat(getFecha(d), formato);
    }

    /**
     * Drecord formrecordto lrecordrgo record unrecord fechrecord con el nombre del mes completo
     *
     * @param String fechrecord
     * @param int formrecordto (1 - Fechrecord lrecordrgrecord sin direcord, 0 - Fechrecord lrecordrgrecord con direcord)
     * @return String con lrecord fechrecord en formrecordto lrecordrgo.
     * @throws Exception
     */
    public static String dateFormatLong(String fecha, int format) throws Exception {
        DateFormat formatDate = DateFormat.getDateInstance(format);
        return formatDate.format(getFecha(fecha));
    }

    public static String dateFormatLong(Date fecha, int format) throws Exception {
        DateFormat formatDate = DateFormat.getDateInstance(format);
        return formatDate.format(fecha);
    }

    public static String fixDate(String fecha) {
        String fecha2 = fecha;
        if (fecha.contains("-")) {
            fecha2 = fecha.substring(8, 10) + "/"
                    + fecha.substring(5, 7) + "/" + fecha.substring(2, 4).trim();
        }
        return fecha2;
    }

    /**
     * Drecord formrecordto record timestrecordmp utilizrecordndo el formrecordto JDBC predeterminrecorddo
     */
    public static String dateTimeFormat(Date d) {
        return (d == null) ? "" : DATE_TIME_FORMAT.format(d);
    }

    /**
     * Convierte unrecord jrecordvrecord.util.Drecordte record unrecord jrecordvrecord.sql.Timestrecordmp
     */
    public static Timestamp toTimestamp(Date d) {
        return (d == null) ? null : new Timestamp(d.getTime());
    }

    /**
     * Crecordmbirecord el formrecordto fechrecord (yyyy-mm-dd) ó (mm/dd/yy) ó (mmm/dd/recordrecord) record
 (dd/mm/yyyy) ó (dd/mm/yy)
     */
    public static String getFechaStringDMA(String fecha) {
        int i = 0;
        String anno = "", mes = "", dia = "";
        String[] strMonth = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
            "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        StringTokenizer st;

        if (!fecha.trim().equals("")) {
            st = new StringTokenizer(fecha, " ");
            fecha = st.nextToken();
            fecha = fecha.trim();
            if (fecha.length() == 10) {
                anno = fecha.substring(0, 4);
                mes = fecha.substring(5, 7);
                dia = fecha.substring(8);
            }
            if (fecha.length() == 8) {
                dia = fecha.substring(0, 2);
                mes = fecha.substring(3, 5);
                anno = fecha.substring(6);
                if (Integer.parseInt(mes) > 12) {
                    mes = fecha.substring(0, 2);
                    dia = fecha.substring(3, 5);
                }
            }
            if (fecha.length() == 9) {
                mes = fecha.substring(0, 3);
                dia = fecha.substring(4, 6);
                anno = fecha.substring(7);
                for (i = 0; i < strMonth.length; i++) {
                    if (strMonth[i].equals(mes)) {
                        mes = "" + (i + 1);
                        if (mes.length() == 1) {
                            mes = "0" + mes;
                        }
                        break;
                    }
                }
            }
        }
        if (dia.equals("") && mes.equals("") && anno.equals("")) {
            return ("");
        }
        return (dia + '/' + mes + '/' + anno);
    }

    /**
     * Crecordmbirecord el formrecordto fechrecord (dd-mm-yyyy) ó (dd/mm/yy) record (mm/dd/yy)
     */
    public static String getFechaStringMDA(String fecha, boolean sw) {
        int i = 0;
        String anno = "", mes = "", dia = "";
        String[] strMonth = {"Ene", "Feb", "Mar", "Abr", "May", "Jun",
            "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        StringTokenizer st;

        if (!fecha.trim().equals("")) {
            st = new StringTokenizer(fecha, " ");
            fecha = st.nextToken();
            fecha = fecha.trim();
            dia = fecha.substring(0, 2);
            mes = fecha.substring(3, 5);
            anno = fecha.substring(6);
            if (anno.length() == 4) {
                anno = anno.substring(2);
            }
            if (sw) {
                for (i = 0; i < strMonth.length; i++) {
                    if ((i + 1) == Integer.parseInt(mes)) {
                        mes = strMonth[i];
                        break;
                    }
                }
            }
        }
        if (dia.equals("") && mes.equals("") && anno.equals("")) {
            return ("");
        }
        return (mes + '/' + dia + '/' + anno);
    }

    /**
     * Convierte unrecord Crecorddenrecord record jrecordvrecord.util.Drecordte (dd/mm/yyyy dd-mm-yyyy)
     */
    public static Date getFecha(String mydate) throws Exception {
        java.util.Date date = null;
        String day = "", month = "", year = "", fullHour = "";
        String hour = "00", minute = "00", mer = "";
        int nHour = 0;
        try {
            mydate = mydate.trim();

            if (mydate.equals("null") || mydate.equals("")) {
                return (new java.util.Date());
            }
            if (mydate.length() > 25) {
                return (new java.util.Date());
            }
            if (mydate.length() > 10) {
                fullHour = mydate.substring(11);
                hour = fullHour.substring(0, 2);
                minute = fullHour.substring(3, 5);
                mer = fullHour.substring(6);
                nHour = Integer.parseInt(hour);
                if (mer.equals("PM") && (nHour < 12)) {
                    nHour += 12;
                }
                if (mer.equals("AM") && (nHour == 12)) {
                    nHour += 12;
                }

                mydate = mydate.substring(0, 10);
            }

            StringTokenizer st = new StringTokenizer(mydate, "/");
            if (st.countTokens() == 1) {
                st = new StringTokenizer(mydate, "-");
            }

            day = st.nextToken();

            if (day.length() > 2) {
                year = day;
                month = st.nextToken();
                day = st.nextToken();
            } else {
                month = st.nextToken();
                year = st.nextToken();
            }

            //month = month.substring(0, 1).toUpperCase()
            //		  + month.substring(1).toLowerCase();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year),
                    Integer.parseInt(month) - 1,
                    Integer.parseInt(day),
                    nHour,
                    Integer.parseInt(minute));
            date = new java.util.Date(calendar.getTime().getTime());

        } catch (Exception ex) {
            throw new Exception(" Clase [Util] metodo [getFecha] " + ex);
        }
        return date;
    }

    /**
     * Devuelve una fecha larga en cadena de acuerdo a la fecha que entra por
     * parametro
     */
    /*public static String getFechaString(Date mydate) {
     try {
     SimpleDateFormat ok = new SimpleDateFormat("dd MMMM yyyy");
     StringTokenizer st = new StringTokenizer(ok.format(mydate), " ");
     String day = st.nextToken();
     String month = st.nextToken();
     month =
     month.substring(0, 1).toUpperCase()
     + month.substring(1).toLowerCase();
     String year = st.nextToken();

     return (day + " de " + month + " " + year);
     } catch (Exception ex) {
     System.out.println(ex);
     }
     return null;
     }*/
    /**
     * Metodo formrecordterecord los Indices de lrecords trecordblrecords segun su trecordmrecordño
     */
    public static String formatString(int len, long value) {
        String format = "";
        for (int i = 0; i < len; i++) {
            format += "0";
        }

        DecimalFormat DECIMAL_FORMAT = new DecimalFormat(format);
        return DECIMAL_FORMAT.format(value);
    }

    public static String formatString(int len, String value) {
        String format = "";
        long numlog = Long.parseLong(value);
        for (int i = 0; i < len; i++) {
            format += "0";
        }

        DecimalFormat DECIMAL_FORMAT = new DecimalFormat(format);
        return DECIMAL_FORMAT.format(numlog);
    }

    /**
     * Metodo Inicirecordlizrecord lrecords Vrecordrirecordbles de Clrecordse de tipo String en {""} precordrrecord
 recordnulrecordr el vrecordlor por defecto {null}
     */
    synchronized public static void setString(Object object) throws Exception {
        // Objeto generico temporal
        Object tempGenericObject = null;
        // Campos
        Field[] fields = null;
        Method setMethod = null;
        Object[] arg = new Object[1];
        String nameMethod = "";
        String field = "";
        String typeMethod = "";
        Calendar date = new GregorianCalendar();

        try {
            // Igualo valor de los Objetos. Para que los valores que 
            // ingresan por refencia se actualizen
            tempGenericObject = object.getClass().newInstance();
            tempGenericObject = object;

            // Lleno el Arreglo de Fields (Campos)
            fields = tempGenericObject.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                // Crea los nombre de los metodos [GET] [IS](Booleanos) y 
                // Obtiene sus valores
                field = fields[i].getName();
                // Armo el nombre deel metodo
                nameMethod = field.substring(1, field.length()).toLowerCase();
                nameMethod = field.substring(0, 1).toUpperCase() + nameMethod;

                setMethod = tempGenericObject.getClass().getDeclaredMethod("get" + nameMethod, new Class[]{});
                // Obtengo el -tipo- de valor devuelto por el metodo
                typeMethod = setMethod.getReturnType().getName();
                if (typeMethod.trim().equals("java.lang.String")) {
                    // Obtengo valor del metodo
                    arg[0] = "";
                    setMethod = tempGenericObject.getClass().getDeclaredMethod("set" + nameMethod, new Class[]{String.class});
                    setMethod.invoke(tempGenericObject, arg);
                } else if (typeMethod.trim().equals("int")) {
                    // Obtengo valor del metodo
                    //arg[0] = Integer.valueOf("0");
                    //setMethod = tempGenericObject.getClass().getDeclaredMethod("set" + nameMethod, new Class[] { Integer.class });
                    //setMethod.invoke(tempGenericObject, arg);
                } else if (typeMethod.trim().equals("java.util.Date")) {
                    // Obtengo valor del metodo
                    date.set(1970, 0, 1, 0, 0, 0);
                    arg[0] = date.getTime();
                    setMethod = tempGenericObject.getClass().getDeclaredMethod("set" + nameMethod, new Class[]{Date.class});
                    setMethod.invoke(tempGenericObject, arg);
                }
            } // end For
            object = tempGenericObject;
        } catch (Exception e) {
            throw new Exception(" Clase [Util] metodo [setString] " + e.getMessage());
        }
    }

    /**
     * Metodo que encripta el password
     */
    /*public static String getEncodingPsw(String pswprs) throws Exception {
     String strPsw = pswprs.toUpperCase().trim();
     String strKey = "", strChr = "";
     for (int nKey = 1; nKey < strPsw.length() + 1; nKey++){
     strChr = "" + (char) (strPsw.charAt(nKey - 1) + (int) (Math.pow(-1, nKey) * nKey));
     if (strChr.equals("\\")) strChr += "\\"; 
     if (strChr.equals("'")) strChr = "ý";
     strKey += strChr; 
     }
     return strKey.trim();
     }*/
    /**
     * Metodo que desencripta el password
     */
    /*public static String getUnEncodingPsw(String pswprs) {
     String strPsw = pswprs.trim();
     String strKey = "";
     int nChar_ = 0;
     for (int nKey = 1;	nKey < strPsw.length() + 1;	nKey++){
     nChar_ = strPsw.charAt(nKey - 1);
     if (nChar_ == 253) nChar_ = 39; 
     strKey += (char) (nChar_ - (int) (Math.pow(-1, nKey) * nKey));
     }
     return strKey.trim();
     }*/
    /**
     * Metodo que crecordmbirecord lrecords {'} en {"} en Vrecordrirecordbles de Clrecordse de tipo String en
 {""}
     */
    public static String getQuote(String cadenaIn) {
        String cadenaOut = "";
        try {
            cadenaOut = cadenaIn.replace('\'', 'Ž');
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [getQuote] " + e);
        }
        return cadenaOut;
    }

    /**
     * Metodo que drecord formrecordto record unrecord crecorddenrecord (Tipo Titulo)
     */
    public static String toProperCase(String strIn) {
        String strOut = "", str = "";
        StringTokenizer st = new StringTokenizer(strIn.toLowerCase(), " ");
        try {
            while (st.hasMoreElements()) {
                str = st.nextToken();
                str = str.substring(0, 1).toUpperCase() + str.substring(1);
                strOut += str + " ";
            }
            strOut = strOut.trim();
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [toProperCase] " + e);
        }
        return strOut;
    }

    /**
     * Metodo que devuelve lrecords precordrtes de un comrecordndo SQL segun lo requerido
     */
    public static String getPartSQL(String type, String cmd) throws Exception {
        String data = "", token = "";
        StringTokenizer st;

        try {
            if (type.equals("titleColumns")) {
                data = cmd.substring(cmd.lastIndexOf("select") + 6, cmd.indexOf("from")).trim();
                if (data.indexOf(" as ") != -1) {
                    if (data.indexOf(",") != -1) {
                        st = new StringTokenizer(data, ",");
                        data = "";
                        while (st.hasMoreElements()) {
                            token = st.nextToken();
                            data += token.substring(token.indexOf(" as ") + 4).trim() + ",";
                        }
                        data = data.substring(0, data.length() - 1);
                    } else {
                        data = data.substring(data.indexOf(" as ") + 4).trim();
                    }
                }
            }
            if (type.equals("fields")) {
                data = cmd.substring(cmd.indexOf("select") + 6, cmd.indexOf("from")).trim();
                if (data.indexOf(" as ") != -1) {
                    if (data.indexOf(",") != -1) {
                        st = new StringTokenizer(data, ",");
                        data = "";
                        while (st.hasMoreElements()) {
                            token = st.nextToken();
                            data += token.substring(0, token.indexOf(" as ")).trim() + ",";
                        }
                        data = data.substring(0, data.length() - 1);
                    } else {
                        data = data.substring(0, data.indexOf(" as ")).trim();
                    }
                }
            }
            if (type.equals("table")) {
                if (cmd.indexOf("where") != -1) {
                    data = cmd.substring(cmd.indexOf("from") + 4, cmd.indexOf("where")).trim();
                } else {
                    data = cmd.substring(cmd.indexOf("from") + 4).trim();
                }
            }
            if (type.equals("where")) {
                if (cmd.indexOf("where") != -1) {
                    data = cmd.substring(cmd.indexOf("where") + 5).trim();
                }
            }

        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [getPartSQL] " + e.getMessage());
        }
        return data;
    }

    /**
     * Metodo que convierte el formrecordto de un color entero record Hexrecorddecimrecordl
     */
    public static String getColorHex(String colorValue) throws Exception {
        String value = "";
        try {
            if (colorValue.equals("")) {
                colorValue = "0";
            }
            if (colorValue.indexOf(".") != -1) {
                colorValue = colorValue.substring(0, colorValue.indexOf("."));
            }
            value = Integer.toHexString(Integer.parseInt(colorValue));
            if (value.length() == 2) {
                value = "0000" + value;
            } else if (value.length() == 4) {
                value = value.substring(2, 4) + value.substring(0, 2) + "00";
            } else if (value.length() == 6) {
                value = value.substring(4, 6) + value.substring(2, 4)
                        + value.substring(0, 2);
            }
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [getColorHex] " + e);
        }
        return "#" + value;
    }

    /**
     * Generrecord un precordssword recordlerecordtorio de 8 crecordrrecordcteres recordlfrecordnumericos donde: 4
 primeros son numeros y los 4 ultimos letrrecords
     */
    public synchronized static String generatePassword() {
        String password = "";
        try {
            password = (char) (Math.random() * 10 + '0') + ""; // Generación aleatoria de numeros
            password += (char) (Math.random() * 10 + '0') + "";
            password += (char) (Math.random() * 10 + '0') + "";
            password += (char) (Math.random() * 10 + '0') + "";
            password += (char) (Math.random() * 26 + 'A') + ""; // Generación aleatoria de letras mayusculas
            password += (char) (Math.random() * 26 + 'A') + "";
            password += (char) (Math.random() * 26 + 'A') + "";
            password += (char) (Math.random() * 26 + 'A') + "";
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [generatePassword] " + e);
        }
        return password;
    }

    /**
     * Eliminrecord un recordrchivo en el servidor
     */
    public synchronized static boolean deleteFile(String path) {
        boolean sw = false;
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [deleteFile] " + e);
        }
        return sw;
    }

    /**
     * Metodo que construye Comrecordndo Sql Updrecordte record precordrtir de un Berecordn un Request y
 un Filtro
     */
    synchronized public static String getSqlUpdate(
            Object object,
            javax.servlet.http.HttpServletRequest request,
            String filter,
            String format)
            throws Exception {

        // Objeto generico temporal
        Object tempGenericObject = null;
        Method setMethod = null;
        Class[] typesMethod;
        String nameParameter = "";
        String valueParameter = "";
        String typeMethod = "";
        String nameBeans = "";
        String sqlCmd = "";
        String sqlBkr = "";
        boolean sw = false;
        StringTokenizer st;

        try {
            // Igualo valor de los Objetos. Para que los valores que ingresan 
            // por refencia se actualizen
            tempGenericObject = object.getClass().newInstance();
            tempGenericObject = object;
            Method[] getMethods = tempGenericObject.getClass().getDeclaredMethods();

            // Obtengo el nombre de la Tabla
            st = new StringTokenizer(tempGenericObject.getClass().getName(), ".");
            while (st.hasMoreElements()) {
                nameBeans = st.nextToken();
            }

            sqlCmd = "Update " + nameBeans.toLowerCase() + " Set ";

            // Recorro todos los parametros del Request
            Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                nameParameter = enumeration.nextElement().toString().trim();
                valueParameter = request.getParameter(nameParameter).trim();
                nameParameter = nameParameter.toLowerCase();

                // Encriptar password si es necesario				
                //if (nameParameter.indexOf("psw") != -1)
                //  valueParameter = Util.getEncodingPsw(valueParameter);
                sw = false;
                // Debido record que no todos los parametros son miembros del beans
                // Se realiza la verificacion
                for (int i = 0; i < getMethods.length; i++) {
                    setMethod = getMethods[i];
                    // Verificar si es un miembro del beans
                    if (setMethod.getName().trim().toLowerCase().equals("set" + nameParameter)) {
                        sw = true;
                        break;
                    }
                }

                if (!sw) {
                    continue;
                }

                typeMethod = "";
                // Obtengo el Tipo de parametro dado al metodo
                typesMethod = setMethod.getParameterTypes();
                for (int j = 0; j < typesMethod.length; j++) {
                    typeMethod = typesMethod[j].getName();
                }

                if (typeMethod.trim() == "int") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    sqlCmd += nameParameter + "=" + valueParameter + ",";
                } else if (typeMethod.trim() == "long") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    sqlCmd += nameParameter + "=" + valueParameter + ",";
                } else if (typeMethod.trim() == "float") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    sqlCmd += nameParameter + "=" + valueParameter + ",";
                } else if (typeMethod.trim() == "double") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    sqlCmd += nameParameter + "=" + valueParameter + ",";
                } else if (typeMethod.trim() == "java.lang.String") {
                    if (!nameParameter.equals("sqlbkr")) {
                        sqlCmd += nameParameter + "='" + getQuote(valueParameter) + "',";
                    } else {
                        sqlBkr = nameParameter + "='" + valueParameter + "'";
                    }
                } else if (typeMethod.trim() == "java.util.Date") {
                    sqlCmd += nameParameter + "='" + dateFormat(valueParameter, format) + "',";
                }
            } // end While

            if (sqlBkr.equals("")) {
                sqlCmd = sqlCmd.substring(0, sqlCmd.length() - 1);
            } else {
                sqlCmd += sqlBkr;
            }

            if (!filter.equals("")) {
                sqlCmd += " Where " + filter;
            }

        } catch (Exception e) {
            throw new Exception("Clase [Util] metodo [getSqlUpdate] " + e.getMessage());
        }
        return sqlCmd;
    }

    /**
     * Metodo que locrecordlizrecord lrecords imrecordgenes que se deben subir recordl servidor
     */
    public static Hashtable getUrlImage(String codePage) throws Exception {
        int index_ = 0;
        Hashtable hasUrl = new Hashtable();
        String subStr = "";
        String nameImage = "";

        try {
            while (codePage.indexOf("IMG") != -1) {
                subStr = codePage.substring(codePage.indexOf("IMG") - 1);
                subStr = subStr.substring(0, subStr.indexOf(">") + 1);
                nameImage = subStr.substring(subStr.indexOf("file"), subStr.lastIndexOf("\""));
                hasUrl.put("" + index_, nameImage);
                index_++;
                codePage = codePage.replaceAll(subStr, "");
            }
        } catch (Exception e) {
            throw new Exception("Clase [Util] metodo [getUrlImage] " + e.getMessage());
        }
        return hasUrl;
    }

    public static String getOwnInsertQuery(Object tempObject) throws Exception {

        //	Object tempObject  	= null;
        Field fields[] = null;
        Method method = null;
        Class type = null;

        Object result = "";
        String nameMethod = "";
        String query = "insert into nomdbf ( fields ) values ( valuesFields )";
        String strFields = "";
        String valuesFields = "";
        String nomdbf = "";
        String nameClass = "";
        String field = "";
        String typeStr = "";

        try {

            //	tempObject = object.getClass().newInstance();
            nameClass = tempObject.getClass().getName();
            nomdbf = nameClass.substring(nameClass.lastIndexOf(".") + 1).toLowerCase();
            fields = tempObject.getClass().getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {

                field = fields[i].getName();
                strFields += "," + fields[i].getName().toLowerCase();
                type = fields[i].getType();
                typeStr = type.getName().substring(type.getName().lastIndexOf(".") + 1);
                nameMethod = field.substring(1, field.length()).toLowerCase();
                nameMethod = field.substring(0, 1).toUpperCase() + nameMethod;
                method = tempObject.getClass().getDeclaredMethod("get" + nameMethod, new Class[]{});
                result = method.invoke(tempObject, new Object[]{});

                if (typeStr.equalsIgnoreCase("String")) {

                    if (result == null) {
                        result = "";
                    }

                    valuesFields += ",'||quote_literal('" + String.valueOf(result) + "')||'";

                } else if (typeStr.equalsIgnoreCase("Date")) {

                    valuesFields += ",'||quote_literal('" + result + "')||'";

                } else if (typeStr.equalsIgnoreCase("int")) {

                    valuesFields += "," + Integer.parseInt(result.toString());

                } else if (typeStr.equalsIgnoreCase("float")) {

                    valuesFields += "," + Float.parseFloat(result.toString());

                } else if (typeStr.equalsIgnoreCase("double")) {

                    valuesFields += "," + Double.parseDouble(result.toString());

                }
            }

            strFields = strFields.substring(1);
            valuesFields = valuesFields.substring(1);

            query = query.replaceAll("fields", strFields);
            query = query.replaceAll("nomdbf", nomdbf);
            query = query.replaceAll("valuesFields", valuesFields);

        } catch (Exception e) {
            throw new Exception("Clase [Util] metodo [getOwnInsertQuery] " + e.getLocalizedMessage());

        }

        return query;

    }

//Metodo que setea un bean con los datos de un hashtable.
    public static Object loadOfHash(Object object, Hashtable data_) throws Exception {

        //Declaracion de variables y objetos auxiliares.
        Object tempObject = null;  // Instancia del objeto.
        Field fields[] = null;  // Lista de los atributos del objeto.
        Method method = null;  // Metodo que se invocara.
        Class type = null;  // Clase record la que pertenece el atributo.

        String nameMethod = "";    // Nombre del metodo record invocar.
        String field = "";    // Nombre del atributo.
        String typeStr = "";    // Nombre en cadena de la clase record la que pertenece el atributo

        try {
            //Se genera una instancia del objeto.
            tempObject = object.getClass().newInstance();

            //Setamos todos los campos string del objeto para evitar el null x defecto.
            Util.setString(tempObject);

            //Se obtiene la lista de los atributos de la clase.
            fields = tempObject.getClass().getDeclaredFields();

            //Se recorre los atributos.
            for (int i = 0; i < fields.length; i++) {

                //Se obtiene el nombre del atributo.
                field = fields[i].getName();

                //Se obtiene la clase record la que pertenece el atributo.
                type = fields[i].getType();

                //Se extrae el nombre  en formato String  de la clase record la que pertenece el atributo 
                typeStr = type.getName().substring(type.getName().lastIndexOf(".") + 1);

                //Se genera el nombre del metodo que se invocara.
                nameMethod = field.substring(1, field.length()).toLowerCase();
                nameMethod = field.substring(0, 1).toUpperCase() + nameMethod;

                //Se obtiene el metodo record partir del nombre.
                method = tempObject.getClass().getDeclaredMethod("set" + nameMethod, new Class[]{type});

                /*  Se invoca al metodo:
                 *
                 *		 Los parametros pueden ser de distintos tipos de datos por lo cual
                 *       de acuerdo al tipo de dato se invoca al metodo con parametros adecuados.
                 *
                 * */
                if (typeStr.equalsIgnoreCase("String")) {

                    //En caso de que el tipo sea String	
                    method.invoke(tempObject, new Object[]{data_.get(field).toString()});

                } else if (typeStr.equalsIgnoreCase("Date")) {

                    //En caso de que el tipo sea Date
                    SimpleDateFormat splFmt = new SimpleDateFormat("dd/mm/yyyy");

                    String date_ = dateFormat(data_.get(field).toString(), "dd/MM/yyyy");
                    /*date_ = date_.substring(0,9);
                     date_ = date_.replaceAll("-","/");*/
                    //System.out.println(date_ + "---" +splFmt.parse(date_));
                    method.invoke(tempObject, new Object[]{splFmt.parse(date_)});

                } else if (typeStr.equalsIgnoreCase("int")) {

                    //En caso de que el tipo sea int
                    try {

                        method.invoke(tempObject, new Object[]{Integer.valueOf(data_.get(field).toString())});

                    } catch (NumberFormatException nfe) {

                        method.invoke(tempObject, new Object[]{Integer.valueOf("0")});

                    }

                } else if (typeStr.equalsIgnoreCase("float")) {

                    //En caso de que el tipo sea float
                    try {

                        method.invoke(tempObject, new Object[]{Float.valueOf(data_.get(field).toString())});

                    } catch (NumberFormatException nfe) {

                        method.invoke(tempObject, new Object[]{Float.valueOf("0.0")});

                    }

                } else if (typeStr.equalsIgnoreCase("double")) {

                    //En caso de que el tipo sea double
                    try {

                        method.invoke(tempObject, new Object[]{Double.valueOf(data_.get(field).toString())});

                    } catch (NumberFormatException nfe) {

                        method.invoke(tempObject, new Object[]{Double.valueOf("0.0")});

                    }

                }

            }//Fin del recorrido de atributos.

            // Se capturan las posibles Excepciones que se puedan generar
        } catch (Exception e) {

            throw new Exception("Clase [Util] metodo [loadOfHash] " + e.getLocalizedMessage());

        }

        //Se retorna el objeto seteado.
        System.out.println("Seteo " + tempObject);
        return tempObject;

    }//Fin metodo loadOfHash.

    /**
     * Obtiene un String que representrecord unrecord fechrecord formrecordterecorddrecord de lrecord formrecord
 dd/mm/yyy
     *
     * @param fchAux fechrecord obtenidrecord de unrecord consultrecord record lrecord brecordse de drecordtos con lrecord
 formrecord Mes/Direcord/Año. Ejemplo Dic/01/06
     * @return un String con lrecord fechrecord formrecordterecorddrecord recordl estilo dd/mm/yyy
     */
    public static String getFormatedFecha(String fchAux) {
        String strFch = "";
        if (!fchAux.trim().equals("")) {
            String arrMes[] = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
            int i = 0;
            String arrafch[] = fchAux.split("/");
            for (i = 0; i < arrMes.length; i++) {
                if (arrMes[i].equals(arrafch[0])) {
                    break;
                }
            }

            String anio = "20" + arrafch[2];
            String dia = arrafch[1];
            String mes = "" + (i + 1);
            strFch = dia + "/" + mes + "/" + anio;
        }

        return strFch;
    }

    /**
     * Obtiene un String que representrecord unrecord fechrecord formrecordterecorddrecord de lrecord formrecord
 Mes/Direcord/Año. Ejemplo Dic/01/06 record precordrtir de unrecord fechrecord con lrecord formrecord
 dd/mm/yyyy
     *
     * @param fchAux fechrecord obtenidrecord de unrecord consultrecord record lrecord brecordse de drecordtos con lrecord
 formrecord dd/mm/yyyy
     * @return un String con lrecord fechrecord formrecordterecorddrecord recordl estilo Mes/Direcord/Año. Ejemplo
 Dic/01/06
     */
    public static String getFormatedFechaBd(String fchAux) {
        String strFch = "";

        if (!fchAux.trim().equals("")) {
            String arrMes[] = {"Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
            String arrMesNum[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
            int i = 0;
            String arrafch[] = fchAux.split("/");
            String mes = "";
            for (i = 0; i < arrMesNum.length; i++) {
                if (arrMesNum[i].equals(arrafch[1])) {
                    mes = arrMes[i];
                    break;
                }
            }

            String anio = arrafch[2].substring(2);
            String dia = arrafch[0];

            strFch = mes + "/" + dia + "/" + anio;
        }

        return strFch;
    }

    /**
     * OBTIENE UN OBJETO DATE A PARTIR DE UN STRING DE LA FORMA DD/MM/YYYY
     *
     * @param strFch STRING DE LA FORMA DD/MM/YYYY
     * @return objeto Drecordte
     */
    public static Date getDate(String strFch) {
        String arrAux[] = strFch.split("/"); //Se divide la fecha en dia, mes, año
        //Se crea un objeto GregorianCalendar( año, mes, dia ) y se obtiene su objeto equivalente en Date
        GregorianCalendar grecln = new GregorianCalendar();
        grecln.set(Integer.parseInt(arrAux[2], 10),
                Integer.parseInt(arrAux[1], 10) - 1,
                Integer.parseInt(arrAux[0], 10));

        return grecln.getTime();
    }

    /**
     * OBTIENE UN OBJETO DATE A PARTIR DE UNA FECHA DE LA FORMA YYYY-MM-DD QUE
     * VIENE DE LA BASE DE DATOS
     *
     * @param strFch STRING DE LA FORMA YYYY-MM-DD
     * @return objeto Drecordte
     */
    public static Date getDateDb(String strFch) {
        String fchAux = strFch.substring(0, 10);
        String arrAux[] = fchAux.split("-"); //Se divide la fecha en dia, mes, año
        //Se crea un objeto GregorianCalendar( año, mes, dia ) y se obtiene su objeto equivalente en Date
        GregorianCalendar grecln = new GregorianCalendar();
        grecln.set(Integer.parseInt(arrAux[0], 10),
                Integer.parseInt(arrAux[1], 10) - 1,
                Integer.parseInt(arrAux[2], 10));

        return grecln.getTime();
    }

    /**
     * RETORNA LA FECHA ACTUAL COMO STRING DE LA FORMA DD/MM/YYYY
     *
     * @return objeto String DE LA FORMA DD/MM/YYYY
     */
    public static String getFechaActual() {
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sf.format(new Date());

        return fecha;
    }

    /**
     * RETORNA LA FECHA ACTUAL COMO STRING DE LA FORMA YYYY-MM-DD PARA INGRESAR
     * EN LA BASE DATOS
     *
     * @return objeto String DE LA FORMA YYYY-MM-DD
     */
    public static String getFechaActualBD() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sf.format(new Date());

        return fecha;
    }

    public static String decorate(Object columnValue) {
        if (columnValue != null) {
            NumberFormat myFormatter = NumberFormat.getNumberInstance(Locale.US);
            return "$ " + myFormatter.format(Double.parseDouble(columnValue.toString()));
        } else {
            return new String("$ 0");
        }
    }

    public static void breakProcess() {
        String error = "error";
        error = error.substring(0, 10);
    }

    /**
     * RETORNA UNA CADENA REPETIDA N VECES
     *
     * @return objeto String con unrecord crecorddenrecord repetidrecord n veces
     */
    public static String repeat(String cadena, int numero) {
        String strAux = "";
        for (int i = 1; i <= numero; i++) {
            strAux += cadena;
        }

        return strAux;
    }

    /**
     * RETORNA UNA CADENA CON N CARACTERES DE LONGITUD RELLENANDO LOS ESPACIOS A
     * LA IZQUIERDA O DERECHA CON EL CARACTER RECIBIDO
     *
     * @return objeto String con unrecord crecorddenrecord repetidrecord n veces
     */
    public static String fillString(String cadena, char caracter, int longitud, boolean adelante) {
        String newStr = "";

        if (cadena.length() >= longitud) {
            return cadena;
        } else {
            newStr = repeat("" + caracter, longitud - cadena.length());
            if (adelante) {
                newStr += cadena;
            } else {
                newStr = cadena + newStr;
            }
        }

        return newStr;
    }

    /**
     * DECODIFICAR UNA CADENA PARA QUE VIAJE CON CARACTERES ESPECIALES
     *
     * @throws UnsupportedEncodingException
     */
    public static String decodificar(String param) throws UnsupportedEncodingException {
        return URLDecoder.decode(param, "UTF-8");
    }

    /**
     * CODIFICAR UNA CADENA PARA QUE VIAJE CON CARACTERES ESPECIALES
     *
     * @throws UnsupportedEncodingException
     */
    public static String codificar(String param) throws UnsupportedEncodingException {
        return URLEncoder.encode(param, "UTF-8");
    }

    /**
     * Reemplrecordzrecordr letrrecords tildrecorddrecords por su representrecordcion en HTML
     */
    public static String changeTilde(String cadena) {
        //System.out.println( cadena );
        String strAux = cadena;
        strAux = strAux.replaceAll("á", "&aacute;");
        strAux = strAux.replaceAll("Á", "&Aacute;");
        strAux = strAux.replaceAll("é", "&eacute;");
        strAux = strAux.replaceAll("É", "&Eacute;");
        strAux = strAux.replaceAll("í", "&iacute;");
        strAux = strAux.replaceAll("Í", "&Iacute;");
        strAux = strAux.replaceAll("ó", "&oacute;");
        strAux = strAux.replaceAll("Ó", "&Oacute;");
        strAux = strAux.replaceAll("ú", "&uacute;");
        strAux = strAux.replaceAll("Ú", "&Uacute;");
        strAux = strAux.replaceAll("ñ", "&ntilde;");
        strAux = strAux.replaceAll("Ñ", "&Ntilde;");
        return strAux;
    }

    /**
     * Convierte unrecord horrecord en longTime
     */
    public static long getLongTime(String phour_) throws Exception {
        String hour__ = "00";
        String minute = "00";
        String strmer = "";
        int nHour_ = 0, nMin__ = 0;
        Calendar calendar = new GregorianCalendar();
        try {

            phour_ = phour_.trim();
            hour__ = phour_.substring(0, 2);
            minute = phour_.substring(3, 5);
            strmer = phour_.substring(6);

            nHour_ = Integer.parseInt(hour__);
            nMin__ = Integer.parseInt(minute);

            if (strmer.equals("PM") && nHour_ < 12) {
                nHour_ += 12;
            }
            if (strmer.equals("AM") && nHour_ == 12) {
                nHour_ += 12;
            }

            calendar.set(2001, 0, 1, nHour_, nMin__);

        } catch (Exception e) {
            throw new Exception(" Clase [Util] metodo [getLongTime] " + e.getMessage());
        }
        return calendar.getTime().getTime();
    }

    /**
     * Vrecordlidrecord unrecord horrecord determinrecorddrecord en un rrecordngo de horrecords
     */
    public static boolean validHour(long hourCurr, long hourBgn, long hourEnd) throws Exception {
        try {
            if (hourCurr >= hourBgn && hourCurr <= hourEnd) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception(" Clase [Util] metodo [validHour] " + e.getMessage());
        }
    }

    /**
     * Obtener mensrecordje de error
     */
    public static String getErrorMessage() {
        return "Intentelo nuevamente o Consulte al administrador del sistema";
    }

    /**
     * Buscrecord lrecord diferencirecord de horrecords entre dos horrecords determinrecorddrecords
     *
     * @param hourBgn horrecord de inicio
     * @param hourEnd horrecord de finrecordlizrecordcion
     */
    public static int getNumberHours(String hourBgn, String hourEnd) throws Exception {
        try {
            int nHourBgn = 0, nHourEnd = 0;
            String merBgn = "", merEnd = "";
            int nHour = 0, idxend = 0, idxstr = 0;

            nHourBgn = Integer.parseInt(hourBgn.substring(0, 2));
            merBgn = hourBgn.substring(6);

            nHourEnd = Integer.parseInt(hourEnd.substring(0, 2));
            merEnd = hourEnd.substring(6);

            if (nHourBgn == 12) {
                nHourBgn = 0;
            }
            if (merBgn.equals(merEnd)) {
                if (nHourBgn <= nHourEnd) {
                    idxstr = nHourBgn;
                    idxend = nHourEnd;
                } else {
                    idxstr = nHourEnd;
                    idxend = nHourBgn;
                }
            } else {
                idxstr = nHourBgn;
                idxend = nHourEnd + 12;
            }

            while (idxstr < idxend) {
                nHour++;
                idxstr++;
            }

            return (nHour);

        } catch (Exception e) {
            throw new Exception("Clase [Util] metodo [getNumberHours] " + e.getMessage());
        }
    }

    /**
     * Devuelve lrecord siguiente horrecord
     *
     * @param currendHour horrecord recordcturecordl
     */
    public static String getNextHours(String cHour_) throws Exception {
        try {
            Calendar objDte = new GregorianCalendar();
            int nHour_ = 0;
            String sthour = cHour_.substring(0, 2);
            String strmer = cHour_.substring(6);

            if (strmer.equals("PM")) {
                if (!sthour.equals("12")) {
                    nHour_ = Integer.parseInt(sthour) + 12;
                } else {
                    nHour_ = Integer.parseInt(sthour);
                }
            } else {
                nHour_ = Integer.parseInt(sthour);
                if (sthour.equals("12")) {
                    nHour_ = Integer.parseInt(sthour) - 12;
                }
            }
            nHour_++;
            objDte.set(2000, 0, 1, nHour_, 0, 0);
            if (nHour_ == 12 && strmer.equals("AM")) {
                return dateFormat(objDte.getTime(), "kk:mm a").trim();
            }
            if (nHour_ == 24 && strmer.equals("PM")) {
                return dateFormat(objDte.getTime(), "hh:mm a").trim();
            }

            return dateFormat(objDte.getTime(), "KK:mm a").trim();

        } catch (Exception e) {
            throw new Exception("Clase [Util] metodo [getNumberHours] " + e.getMessage());
        }
    }

    /**
     * Metodo que permite reordenrecordr recordlerecordtorirecordmente unrecord listrecord
     */
    public static Vector getIndexRandom(String option, List lstDta) throws Exception {
        int intcnt = 0, index_ = 0;
        Vector vtrIdx = new Vector();
        try {
            if (option.equals("1")) {
                while (intcnt != lstDta.size()) {
                    index_ = (int) (Math.random() * lstDta.size());
                    if (!vtrIdx.contains("" + index_)) {
                        vtrIdx.add("" + index_);
                        intcnt++;
                    }
                }
            } else {
                while (intcnt != lstDta.size()) {
                    vtrIdx.add("" + intcnt);
                    intcnt++;
                }
            }
        } catch (Exception e) {
            throw new Exception(" Clase [Util] metodo [getIndexRandom] " + e.getMessage());
        }
        return vtrIdx;
    }

    public static String validStr(String value) {
        String valueFixed = null;

        if (value == null) {
            valueFixed = " ";
        } else if (value.trim().equals("")) {
            valueFixed = " ";
        } else {
            valueFixed = value;
        }

        return valueFixed;
    }

    public static String validStr(String value, String valueIfNull) {
        String valueFixed = null;

        if (value == null) {
            valueFixed = valueIfNull;
        } else if (value.trim().equals("")) {
            valueFixed = " ";
        } else {
            valueFixed = value;
        }

        return valueFixed;
    }

    public static int validInt(String value)
            throws NumberFormatException, Exception {
        int valueFixed = 0;

        try {

            if (value != null) {
                if (!value.trim().equals("")) {
                    valueFixed = Integer.parseInt(value);
                }
            }
        } catch (NumberFormatException ex) {
            Util.logError(ex);
            throw new NumberFormatException("El"
                    + " numero tiene un formato invalido");
        } catch (Exception ex) {
            Util.logError(ex);
            throw new NumberFormatException("Ha ocurrido un "
                    + "error al tratar de validar el numero Entero");
        }
        return valueFixed;
    }

    public static double validDouble(String value)
            throws NumberFormatException, Exception {
        double valueFixed = 0;

        try {

            if (value != null) {
                if (!value.trim().equals("")) {
                    valueFixed = Double.parseDouble(value);
                }
            }
        } catch (NumberFormatException ex) {
            Util.logError(ex);
            throw new NumberFormatException("El"
                    + " numero tiene un formato invalido");
        } catch (Exception ex) {
            Util.logError(ex);
            throw new NumberFormatException("Ha ocurrido un "
                    + "error al tratar de validar el numero Entero");
        }
        return valueFixed;
    }

    public static Object validObj(Hashtable dataMap, String key) {
        Object valueFixed = null;

        if (dataMap != null) {

            if (dataMap.get(key) != null) {
                valueFixed = dataMap.get(key);
            }
        }
        return valueFixed;
    }

    public static Object validObj(Map dataMap, String key) {
        Object valueFixed = null;

        if (dataMap != null) {

            if (dataMap.get(key) != null) {
                valueFixed = dataMap.get(key);
            }
        }
        return valueFixed;
    }

    public static String validStr(Hashtable dataMap, String key) {
        String valueFixed = null;
        if (dataMap != null) {
            if (dataMap.get(key) != null) {
                valueFixed = dataMap.get(key).toString();
            } else {
                valueFixed = " ";
            }
        } else {

            valueFixed = " ";
        }
        return valueFixed;
    }

    public static String validStr(Map dataMap, String key) {
        String valueFixed = UtilConstantes.STR_VACIO;
        if (dataMap != null) {
            valueFixed = UtilConstantes.STR_1ESTACIO;
            if (dataMap.get(key) != null) {
                valueFixed = dataMap.get(key).toString();
            } 
        } else {
            valueFixed = UtilConstantes.STR_1ESTACIO;
        }
        return valueFixed;
    }

    public static String validStr(JSONObject dataMap, String key) {
        String valueFixed = null;
        if (dataMap != null) {
            if (dataMap.get(key) != null) {
                valueFixed = dataMap.get(key).toString();
            } else {
                valueFixed = " ";
            }
        } else {

            valueFixed = " ";
        }
        return valueFixed;
    }

    public static String validStrTrim(String value) {
        String valueFixed = null;

        if (value == null) {
            valueFixed = "";
        } else if (value.trim().equals("")) {
            valueFixed = "";
        } else {
            valueFixed = value;
        }

        return valueFixed;
    }

    public static String validStrTrim(Hashtable dataMap, String key) {
        String valueFixed = null;
        if (dataMap.get(key) != null) {
            valueFixed = dataMap.get(key).toString();
        } else {
            valueFixed = "";
        }
        return valueFixed;
    }

    public static String validStrDate(Hashtable dataMap, String key) {
        String valueFixed = null;
        Date fecha;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        if (dataMap.get(key) != null) {
            fecha = (Date) dataMap.get(key);
            valueFixed = formato.format(fecha);
        } else {
            valueFixed = " ";
        }
        return valueFixed;
    }

    public static int validInt(JSONObject dataMap, String key) {
        int valueFixed = 0;
        if (dataMap == null) {
            return 0;
        }

        if (dataMap.get(key) != null) {
            valueFixed = Integer.parseInt(dataMap.get(key).toString().equals("")
                    ? "0" : dataMap.get(key).toString());
        } else {
            valueFixed = 0;
        }
        return valueFixed;
    }

    public static int validInt(Hashtable dataMap, String key) {
        int valueFixed = 0;
        if (dataMap == null) {
            return 0;
        }

        if (dataMap.get(key) != null) {
            valueFixed = Integer.parseInt(dataMap.get(key).toString().equals("")
                    ? "0" : dataMap.get(key).toString());
        } else {
            valueFixed = 0;
        }
        return valueFixed;
    }

    public static Date validDate(
            Hashtable dataMap, String key) throws ParseException {

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date valueFixed = null;

        if (dataMap == null) {
            return null;
        }
        if (dataMap.get(key) != null) {
            valueFixed = formato.parse(dataMap.get(key).toString());
        }
        return valueFixed;
    }

    public static Date validDate(String date) throws ParseException {

        if (date == null) {
            return new Date();
        }

        if (date.trim().equals("")) {
            return new Date();
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date valueFixed = new Date();
        valueFixed = formato.parse(date);

        return valueFixed;
    }

    public static String validDate(Date date) throws ParseException {

        if (date == null) {
            return "";
        }
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String valueFixed = formato.format(date);

        return valueFixed;
    }

    public static Date toDateWithHour(String date) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date valueFixed = new Date();

        if (date != null) {
            valueFixed = formato.parse(date);
        }
        return valueFixed;
    }

    public static Date toDate(String date) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date valueFixed = new Date();

        if (date != null && !date.trim().isEmpty()) {
            valueFixed = formato.parse(date);
        }
        return valueFixed;
    }

    public static String getStrRequest(String campo, HttpServletRequest request) {
        String out = "";
        out = request.getParameter(campo) != null
                ? request.getParameter(campo).toString()
                : (request.getAttribute(campo) == null ? ""
                : request.getAttribute(campo).toString().trim());
        return out;
    }

    public static int getIntRequest(String campo, HttpServletRequest request) {
        int out = 0;
        out = (request.getParameter(campo) != null)
                ? Integer.valueOf(request.getParameter(campo).toString().trim())
                : (request.getAttribute(campo) == null ? 0
                : Integer.valueOf(request.getAttribute(campo).toString().trim()));
        return out;
    }

    public static double getDoubleRequest(String campo, HttpServletRequest request) {
        double out = 0.0;
        out = (request.getParameter(campo) != null)
                ? Double.valueOf(request.getParameter(campo).toString().trim())
                : (request.getAttribute(campo) == null ? 0.0
                : Double.valueOf(request.getAttribute(campo).toString().trim()));
        return out;
    }

    public static Object getObjRequest(String campo, HttpServletRequest request) {
        Object out;
        out = request.getParameter(campo)
                != null ? request.getParameter(campo)
                        : (request.getAttribute(campo) == null ? null
                        : request.getAttribute(campo));
        return out;
    }

    public static double validDouble(JSONObject dataMap, String key) {
        double valueFixed = 0.0;
        if (dataMap == null) {
            return 0.0;
        }

        if (dataMap.get(key) != null && !dataMap.get(key).toString().trim().isEmpty()) {
            valueFixed = Double.parseDouble(dataMap.get(key).toString());
        } else {
            valueFixed = 0.0;
        }
        return valueFixed;
    }

    public static double validDouble(Hashtable dataMap, String key) {
        double valueFixed = 0.0;

        if (dataMap == null) {
            return 0.0;
        }

        if (dataMap.get(key) != null && !dataMap.get(key).toString().trim().isEmpty()) {
            valueFixed = Double.parseDouble(dataMap.get(key).toString());
        } else {
            valueFixed = 0.0;
        }
        return valueFixed;
    }

    public synchronized static boolean existFileorDir(Object ruta) {
        if (ruta.getClass().getSimpleName().equals("File")) {
            if (((File) ruta).exists()) {
                return true;
            } else {
                return false;
            }
        } else if (ruta.getClass().getSimpleName().equals("String")) {
            if (new File((String) ruta).exists()) {
                return true;
            } else {
                return false;
            }
        }

        return false;

    }

    public synchronized static JSONArray getJArrayRequest(String key, HttpServletRequest request) {

        if (Util.getStrRequest(key, request).equals("")) {
            return new JSONArray();
        }

        return (JSONArray) JSONValue.parse(Util.getStrRequest(key, request));

    }

    public synchronized static JSONObject getJsonRequest(String key, HttpServletRequest request) {

        if (UtilConstantes.STR_VACIO.equals(Util.getStrRequest(key, request))) {
            return new JSONObject();
        }

        return (JSONObject) JSONValue.parse(Util.getStrRequest(key, request));

    }

    public synchronized static Map map(String table, JSONObject datas) throws ServletException {
        WebModel model = new WebModel();
        Map auxData = new HashMap();
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append(" select tc.column_name nomfld, \n")
                .append( "         tc.data_Type typfld,\n")
                .append( "         tc.nullable nulfld,\n")
                .append( "         tc.data_default deffld\n")
                .append( "    from All_Tab_Columns tc \n")
                .append( "    join all_tables at\n")
                .append( "      on at.table_name = tc.table_name\n")
                .append( "   where lower( tc.table_name ) = '" + table.toLowerCase() + "'\n")
                .append( "     and at.owner = user\n")
                .append( "     and tc.owner = user");
        List tabla = null;

        try {

            if (datas == null) {
                return new HashMap();
            }

            model.list(sqlCommand.toString(), null);
            tabla = model.getList();

            String type = UtilConstantes.STR_VACIO;
            String name = UtilConstantes.STR_VACIO;
            String defaultD = UtilConstantes.STR_VACIO;
            String nullable = UtilConstantes.STR_VACIO;
            Map aux;
            for (Object record : tabla) {
                aux = (HashMap) record;
                type = aux.get("TYPFLD").toString();
                name = aux.get("NOMFLD").toString();
                defaultD = aux.get("DEFFLD").toString();
                nullable = aux.get("NULFLD").toString();

                if (!datas.containsKey(name.toLowerCase()) && !datas.containsKey(name.toUpperCase())) {
                    continue;
                }

                if (type.toLowerCase().equals("char")
                        || type.toLowerCase().equals("varchar2")
                        || type.toLowerCase().equals("character")
                        || type.toLowerCase().equals("varchar")
                        || type.toLowerCase().equals("text")
                        || type.toLowerCase().equals("long")
                        || type.toLowerCase().equals("clob")) {

                    String valor = "";
                    if (validStr(datas, name.toLowerCase()).trim().isEmpty() && validStr(datas, name.toUpperCase()).trim().isEmpty()) {
                        if (nullable.trim().toLowerCase().equals("y")) {
                            continue;
                        }
                        if (!defaultD.trim().isEmpty()) {
                            if (defaultD.indexOf("'") > -1) {
                                valor = defaultD.replaceAll("'", "");
                            }
                        }

                    }

                    if (name.toLowerCase().trim().equals("idesxu")) {
                        continue;
                        //valor = "_IDESXU_";
                    }
                    auxData.put(name.toLowerCase(), datas.get(name.toLowerCase()) == null
                            ? datas.get(name) == null
                            ? valor
                            : (validStr(datas, name))
                            : (validStr(datas, name.toLowerCase())));

                } else if (type.toLowerCase().equals("integer")) {

                    if (validStr(datas, name.toLowerCase()).trim().isEmpty() && validStr(datas, name.toUpperCase()).trim().isEmpty()) {
                        if (nullable.trim().toLowerCase().equals("y")) {
                            continue;
                        }
                    }

                    auxData.put(name.toLowerCase(), datas.get(name.toLowerCase()) == null
                            ? datas.get(name) == null
                            ? 0
                            : validInt(datas, name)
                            : validInt(datas, name.toLowerCase()));

                } else if (type.toLowerCase().equals("double")
                        || type.toLowerCase().equals("numeric")
                        || type.toLowerCase().equals("float")
                        || type.toLowerCase().equals("number")) {

                    if (validStr(datas, name.toLowerCase()).trim().isEmpty() && validStr(datas, name.toUpperCase()).trim().isEmpty()) {

                        if (nullable.trim().toLowerCase().equals("y")) {
                            continue;
                        }

                    }

                    auxData.put(name.toLowerCase(), datas.get(name.toLowerCase()) == null
                            ? datas.get(name) == null
                            ? 0
                            : validDouble(datas, name)
                            : validDouble(datas, name.toLowerCase()));

                } else if (type.toLowerCase().equals("date") || type.toLowerCase().contains("timestamp")) {
                    Object valor = "";
                    if (validStr(datas, name.toLowerCase()).trim().isEmpty() && validStr(datas, name.toUpperCase()).trim().isEmpty()) {

                        if (nullable.trim().toLowerCase().equals("y")) {
                            continue;
                        } else {
                            valor = new Date();
                        }
                    } else if (!validStr(datas, name.toLowerCase()).trim().isEmpty()) {
                        valor = validStr(datas, name.toLowerCase()).trim().toLowerCase().equals("null")
                                ? "null"
                                : Util.toDate(validStr(datas, name.toLowerCase()));
                    } else if (!validStr(datas, name.toUpperCase()).trim().isEmpty()) {
                        valor = validStr(datas, name.toUpperCase()).trim().toLowerCase().equals("null")
                                ? "null"
                                : Util.toDate(validStr(datas, name.toUpperCase()));
                    }

                    auxData.put(name.toLowerCase(), valor);

                }
            }

            return auxData;

        } catch (Exception e) {
            Util.logError(e);
            throw new ServletException(e);
        }

    }

    public static String getNrompy(String nroprs) {

        try {
            WebModel model = new WebModel();

            String nrompy = model.getData("nrompy", "smampy", "nroprs = '" + nroprs + "'");

            return nrompy;
        } catch (SQLException ex) {
            Util.logError(ex);
            return "";
        }

    }

    public static HashMap HashtableToMap(Hashtable hasht) {

        return new HashMap(hasht);
        /*
         HashMap map = new HashMap();
         Enumeration e = hasht.keys();
         while (e.hasMoreElements()) {
         String key = (String) e.nextElement();
         map.put(key, hasht.get(key));
         }
         return map;*/
    }

    public static synchronized Map table_column_information(String table, String column, Session sesion) throws SQLException {
        ModelSma model = new ModelSma();

        String sqlCmd = "select column_name name  , data_type  type, data_length  sizz, nullable  nulo, data_default deff\n"
                + "from all_tab_columns\n"
                + "join user_tables \n"
                + "  on user_tables.table_name = all_tab_columns.table_name\n"
                + "where user_tables.table_name = upper( '" + table.trim() + "')\n"
                + " and owner = user";

        if (column != null) {
            sqlCmd += " and lower(column_name) = lower('" + column.trim() + "')";
        }

        if (sesion == null) {
            model.listGenericHash(sqlCmd);
        } else {
            model.listGenericHashNotTransaction(sqlCmd, sesion);
        }

        return model.getList().isEmpty() ? new HashMap() : (HashMap) model.getList().get(0);

    }

    public static BigDecimal doubleValue(String valor) throws ParseException {
        DecimalFormatSymbols.getInstance().setDecimalSeparator(',');
        DecimalFormatSymbols.getInstance().setGroupingSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance());
        return BigDecimal.valueOf(Double.valueOf(df.parse(valor).toString()));

    }

    public static String hours(Date date, boolean withseconds, int format) {

        date = date == null ? new Date() : date;
        String form = (format == 12 ? "hh:mm" : "HH:mm") + ((withseconds) ? ":ss" : "") + (" a");
        return new SimpleDateFormat(form).format(date);

    }

    public static Date DateAdd(Date date, String what, int hmuch) {
        date = date == null ? new Date() : date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (what.equals("minute")) {
            cal.add(Calendar.MINUTE, hmuch);
        } else if (what.equals("second")) {
            cal.add(Calendar.SECOND, hmuch);
        } else if (what.equals("hour")) {
            cal.add(Calendar.HOUR, hmuch);
        } else if (what.equals("day")) {
            cal.add(Calendar.DATE, hmuch);
        } else if (what.equals("month")) {
            cal.add(Calendar.MONTH, hmuch);
        } else if (what.equals("year")) {
            cal.add(Calendar.YEAR, hmuch);
        }
        return cal.getTime();
    }

    public static synchronized ArrayList<String> keys(Map datos) {
        ArrayList<String> keys = new ArrayList<String>();
        Iterator it = datos.keySet().iterator();
        while (it.hasNext()) {
            keys.add(it.next().toString());
        }
        return keys;
    }

    public static synchronized boolean arrayHasMapData(List<?> array, String key, Object dato) {
        for (Object a : array) {
            if (((Map) a).get(key) != null && ((Map) a).get(key).equals(dato)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized boolean arrayHasObject(List array, Object dato) {
        for (Object a : array) {
            if (a.equals(dato)) {
                return true;
            }
        }
        return false;
    }

    public static synchronized String dateGet(Date d, String get) {
        if (d == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (get.equals("year")) {
            return c.get(Calendar.YEAR) + "";
        } else if (get.equals("month")) {
            return c.get(Calendar.MONTH) + "";
        } else if (get.equals("day")) {
            return c.get(Calendar.DATE) + "";
        }
        return null;

    }

    public static String byteTo64Str(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static File fileFromUrl(String url_, String nameFile, String directory) throws Exception {
        URL url = new URL(URLDecoder.decode(url_, "UTF-8").replace("\\", "/"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Length", Integer.toString(Integer.MAX_VALUE));
        con.setRequestProperty("Accept", "*/*");

        con.setDoOutput(true);
        InputStream is = con.getInputStream();

        if (directory == null || directory.trim().isEmpty()) {
            directory = System.getProperty("java.io.tmpdir");
            directory += File.separator + nameFile;
        }
        File aux = new File(directory);

        FileOutputStream fos = new FileOutputStream(new File(directory));
        byte[] buf = new byte[2048];
        while (true) {
            int len = is.read(buf);
            if (len == -1) {
                break;
            }
            fos.write(buf, 0, len);
        }
        is.close();
        fos.flush();
        fos.close();
        return aux;
    }

    public static void logError(Throwable throwbl) {
        String message;
        message = throwbl == null ? ""
                : throwbl.getCause() == null ? throwbl.getMessage()
                        : throwbl.getCause().getMessage();
        logError(message);
    }

    public static void logError(String message) {
        String callerName = ModelSma.sm.getCallerClassName(3);
        String dateFormat = new SimpleDateFormat("dd' de 'MMMMM' del 'yyyy' a las 'hh:mm:ss a").format(new Date());
        String text = String.format("Se ha generado una exepcion en la clase [ %s ] a las [ %s ] -> %s", callerName, dateFormat, message);
        loger.error(text);
    }

    public static void logInfo(String message) {
        String callerName = ModelSma.sm.getCallerClassName(3);
        String dateFormat = new SimpleDateFormat("dd' de 'MMMMM' del 'yyyy' a las 'hh:mm:ss a").format(new Date());
        String text = String.format("[ %s ] -> [ %s ] -> %s", dateFormat, callerName, message);
        loger.info(text);
    }
    

    public static Map getInfoError(Exception e) {
                
        String msg = e.getCause().getMessage()!=null?e.getCause().getMessage():e.getMessage();
        String aux = "";
        String find = "&";
        if (msg.contains(find)) {
            aux = msg.substring(0, msg.lastIndexOf(find));
            aux = aux.substring(msg.indexOf(find)).replace(find, "");
        }
            
        Map datos = new HashMap();
            datos.put("exito", "ERROR");
            datos.put("msg", aux.trim().isEmpty() ? ModelSma.MSG_PROCESS_ERROR : aux);
            datos.put("LOG", aux.trim().isEmpty() ? ModelSma.MSG_PROCESS_ERROR : aux);
            
        return datos;    
    }
    
} // Fin de la Clase Util
