package mvc.model;

import java.text.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.lang.reflect.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class Util_M {

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
    private static ModelSma model = null;
    private static String a = "";

    /* VARIABLES DE CONEXION Y MANIPULACION DE LA BASE DE DATOS DE INFORMIX */
    private static Connection conIfx = null;
    private static PreparedStatement pstIfx = null;
    private static ResultSet rstIfx = null;
    private static String sqlIfx = "";
    /* FIN VARIABLES DE CONEXION Y MANIPULACION DE LA BASE DE DATOS DE INFORMIX */

    /**
     * Da formato a un Double sin Decimales
     */
    public static void setModel(ModelSma pModel) {
        model = pModel;
    }

    /**
     * Da formato a un Double sin Decimales
     */
    public static String doubleSinDecimalFormat(double d, int decimales) {
        DECIMAL_FORMAT.setMaximumFractionDigits(decimales);
        DECIMAL_FORMAT.setGroupingSize(0);
        return DECIMAL_FORMAT.format(d);
    }

    /**
     * Da formato a un Double sin Decimales
     */
    public static String doubleDecimalFormat(double d, String formato) {
        DecimalFormat DECIMAL_FORMAT = new DecimalFormat(formato);
        return DECIMAL_FORMAT.format(d);
    }

    /**
     * Da formato a una fecha utilizando el formato JDBC predeterminado
     * [yyyy-MM-dd][yyyy/MM/dd] [dd/MM/yyyy][MM/dd/yyyy] [yyyy-MM-dd HH:mm:ss]
     */
    public static String dateFormat(Date d, String formato) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(formato);
        return d == null ? "" : DATE_FORMAT.format(d);
    }

    public static String dateFormat(String d, String formato) throws Exception {
        return dateFormat(getFecha(d), formato);
    }

    /**
     * Da formato largo a una fecha con el nombre del mes completo
     *
     * @param String fecha
     * @param int formato (1 - Fecha larga sin dia, 0 - Fecha larga con dia)
     * @return String con la fecha en formato largo.
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

    /**
     * Da formato a timestamp utilizando el formato JDBC predeterminado
     */
    public static String dateTimeFormat(Date d) {
        return (d == null) ? "" : DATE_TIME_FORMAT.format(d);
    }

    /**
     * Convierte una java.util.Date a una java.sql.Timestamp
     */
    public static Timestamp toTimestamp(Date d) {
        return (d == null) ? null : new Timestamp(d.getTime());
    }

    /**
     * Cambia el formato fecha (yyyy-mm-dd) � (mm/dd/yy) � (mmm/dd/aa) a
     * (dd/mm/yyyy) � (dd/mm/yy)
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
     * Cambia el formato fecha (dd-mm-yyyy) � (dd/mm/yy) a (mm/dd/yy)
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
     * Convierte una Cadena a java.util.Date (dd/mm/yyyy dd-mm-yyyy)
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
     * Metodo formatea los Indices de las tablas segun su tama�o
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
     * Metodo Inicializa las Variables de Clase de tipo String en {""} para
     * anular el valor por defecto {null}
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
     * Metodo setea o Modifica los valores de los Objetos con el Request
     */
    synchronized public static Object setRequest(
            Object object,
            javax.servlet.http.HttpServletRequest request)
            throws Exception {

        // Objeto generico temporal
        Object tempGenericObject = null;
        Method setMethod = null;
        Class[] typesMethod;
        Object[] arg = new Object[1];
        String nameParameter = "";
        String valueParameter = "";
        String typeMethod = "";
        boolean sw = false;

        try {
			// Igualo valor de los Objetos. Para que los valores que ingresan 
            // por refencia se actualizen
            tempGenericObject = object.getClass().newInstance();
            tempGenericObject = object;
            Method[] getMethods = tempGenericObject.getClass().getDeclaredMethods();

            Enumeration enumeration = request.getParameterNames();
            while (enumeration.hasMoreElements()) {
                nameParameter = ((String) enumeration.nextElement()).toLowerCase();
                valueParameter = (request.getParameter(nameParameter) != null)
                        ? request.getParameter(nameParameter).trim() : "";

                a = a + " {" + nameParameter + "} " + "<" + valueParameter + ">";
                // Encriptar password si es necesario				
                if (nameParameter.indexOf("psw") != -1) {
                    if (model != null) {
//                        valueParameter = model.getEncodingPsw(valueParameter);
                    }
                }

                sw = false;
				// debido a que no todos los parametros son miembros del beans
                // armo el nombre del metodo
                nameParameter = "set" + nameParameter.trim();
                for (int i = 0; i < getMethods.length; i++) {
                    setMethod = getMethods[i];

                    if (setMethod.getName().trim().toLowerCase().equals(nameParameter)) {
                        sw = true; // es un miembro del beans
                        break;
                    }
                }

                if (!sw) {
                    continue;
                }

                typeMethod = "";
                // Obtengo el -tipo- de parametro dado al metodo
                typesMethod = setMethod.getParameterTypes();
                for (int j = 0; j < typesMethod.length; j++) {
                    typeMethod = typesMethod[j].getName();
                }

                if (typeMethod.trim() == "boolean") {
                    arg[0] = Boolean.valueOf(valueParameter);
                } else if (typeMethod.trim() == "char") {
                    arg[0] = Integer.valueOf(valueParameter);
                } else if (typeMethod.trim() == "byte") {
                    arg[0] = Byte.valueOf(valueParameter);
                } else if (typeMethod.trim() == "short") {
                    arg[0] = Short.valueOf(valueParameter);
                } else if (typeMethod.trim() == "int") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    arg[0] = Integer.valueOf(valueParameter);
                } else if (typeMethod.trim() == "long") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    arg[0] = Long.valueOf(valueParameter);
                } else if (typeMethod.trim() == "float") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    arg[0] = Float.valueOf(valueParameter);
                } else if (typeMethod.trim() == "double") {
                    if (valueParameter.equals("")) {
                        valueParameter = "0";
                    }
                    arg[0] = Double.valueOf(valueParameter);
                } else if (typeMethod.trim() == "java.lang.String") {
                    arg[0] = valueParameter.toString().trim();
                } else if (typeMethod.trim() == "java.util.Date") {
                    arg[0] = Util.getFecha(valueParameter);
                }

                // Setear el metodo con el valor del parametro
                setMethod.invoke(tempGenericObject, arg);
                /**/
                a = a + "(" + (setMethod.getName().trim().toLowerCase()) + ")" + "[" + arg[0] + "]";
            } // end While

            /**
             * **ROMPER EL PROGRAMA***
             */
            /*
             String b[]=new String [2];
             b[5]="";*/
            /**
             * **********************
             */
            object = tempGenericObject;
        } catch (Exception e) {
            throw new Exception("Clase [Util] metodo [setRequest] " + e.getMessage() + "---" + a);
        }
        return object;
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
     if (strChr.equals("'")) strChr = "�";
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
     * Metodo que cambia las {'} en {"} en Variables de Clase de tipo String en
     * {""}
     */
    public static String getQuote(String cadenaIn) {
        String cadenaOut = "";
        try {
            cadenaOut = cadenaIn.replace('\'', '�');
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [getQuote] " + e);
        }
        return cadenaOut;
    }

    /**
     * Metodo que da formato a una cadena (Tipo Titulo)
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
     * Metodo que devuelve un arreglo con los valores de las llaves primarias de
     * un formBean
     */
    public static String[] getPkTableValues(
            javax.servlet.http.HttpServletRequest request,
            Model model,
            String name) throws Exception {
        String auxCamposPk[] = {};
        String value = "";
        try {
            String[] arrPkTable = {"",""};//model.getPkTable(name);
            auxCamposPk = new String[arrPkTable.length];
            for (int i = 0; i < arrPkTable.length; i++) {
                value = request.getParameter(arrPkTable[i].trim() + "Old");
                if (value == null) {
                    value = request.getParameter(arrPkTable[i].trim());
                }
                auxCamposPk[i] = value;
            }
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [getPkTableValues] " + e);
        }
        return auxCamposPk;
    }

    /**
     * Metodo que devuelve las partes de un comando SQL segun lo requerido
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
     * Metodo que convierte el formato de un color entero a Hexadecimal
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
     * Genera un password aleatorio de 8 caracteres alfanumericos donde: 4
     * primeros son numeros y los 4 ultimos letras
     */
    public synchronized static String generatePassword() {
        String password = "";
        try {
            password = (char) (Math.random() * 10 + '0') + ""; // Generaci�n aleatoria de numeros
            password += (char) (Math.random() * 10 + '0') + "";
            password += (char) (Math.random() * 10 + '0') + "";
            password += (char) (Math.random() * 10 + '0') + "";
            password += (char) (Math.random() * 26 + 'A') + ""; // Generaci�n aleatoria de letras mayusculas
            password += (char) (Math.random() * 26 + 'A') + "";
            password += (char) (Math.random() * 26 + 'A') + "";
            password += (char) (Math.random() * 26 + 'A') + "";
        } catch (Exception e) {
            System.out.println("Error Clase [Util] Metodo [generatePassword] " + e);
        }
        return password;
    }

    /**
     * Elimina un archivo en el servidor
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
     * Metodo que construye Comando Sql Update a partir de un Bean un Request y
     * un Filtro
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
				// Debido a que no todos los parametros son miembros del beans
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
     * Metodo que localiza las imagenes que se deben subir al servidor
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
        Class type = null;  // Clase a la que pertenece el atributo.

        String nameMethod = "";    // Nombre del metodo a invocar.
        String field = "";    // Nombre del atributo.
        String typeStr = "";    // Nombre en cadena de la clase a la que pertenece el atributo

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

                //Se obtiene la clase a la que pertenece el atributo.
                type = fields[i].getType();

                //Se extrae el nombre  en formato String  de la clase a la que pertenece el atributo 
                typeStr = type.getName().substring(type.getName().lastIndexOf(".") + 1);

                //Se genera el nombre del metodo que se invocara.
                nameMethod = field.substring(1, field.length()).toLowerCase();
                nameMethod = field.substring(0, 1).toUpperCase() + nameMethod;

                //Se obtiene el metodo a partir del nombre.
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
        //System.out.println((Smaavo)tempObject+"------");
        return tempObject;

    }//Fin metodo loadOfHash.

    /**
     * Obtiene un String que representa una fecha formateada de la forma
     * dd/mm/yyy
     *
     * @param fchAux fecha obtenida de una consulta a la base de datos con la
     * forma Mes/Dia/A�o. Ejemplo Dic/01/06
     * @return un String con la fecha formateada al estilo dd/mm/yyy
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
     * Obtiene un String que representa una fecha formateada de la forma
     * Mes/Dia/A�o. Ejemplo Dic/01/06 a partir de una fecha con la forma
     * dd/mm/yyyy
     *
     * @param fchAux fecha obtenida de una consulta a la base de datos con la
     * forma dd/mm/yyyy
     * @return un String con la fecha formateada al estilo Mes/Dia/A�o. Ejemplo
     * Dic/01/06
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
     * @return objeto Date
     */
    public static Date getDate(String strFch) {
        String arrAux[] = strFch.split("/"); //Se divide la fecha en dia, mes, a�o
        //Se crea un objeto GregorianCalendar( a�o, mes, dia ) y se obtiene su objeto equivalente en Date
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
     * @return objeto Date
     */
    public static Date getDateDb(String strFch) {
        String fchAux = strFch.substring(0, 10);
        String arrAux[] = fchAux.split("-"); //Se divide la fecha en dia, mes, a�o
        //Se crea un objeto GregorianCalendar( a�o, mes, dia ) y se obtiene su objeto equivalente en Date
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
     * @return objeto String con una cadena repetida n veces
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
     * @return objeto String con una cadena repetida n veces
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
     * Reemplazar letras tildadas por su representacion en HTML
     */
    public static String changeTilde(String cadena) {
        //System.out.println( cadena );
        String strAux = cadena;
        strAux = strAux.replaceAll("�", "&aacute;");
        strAux = strAux.replaceAll("�", "&Aacute;");
        strAux = strAux.replaceAll("�", "&eacute;");
        strAux = strAux.replaceAll("�", "&Eacute;");
        strAux = strAux.replaceAll("�", "&iacute;");
        strAux = strAux.replaceAll("�", "&Iacute;");
        strAux = strAux.replaceAll("�", "&oacute;");
        strAux = strAux.replaceAll("�", "&Oacute;");
        strAux = strAux.replaceAll("�", "&uacute;");
        strAux = strAux.replaceAll("�", "&Uacute;");
        strAux = strAux.replaceAll("�", "&ntilde;");
        strAux = strAux.replaceAll("�", "&Ntilde;");
        return strAux;
    }

    /**
     * Obtener mensaje de error
     */
    public static String getErrorMessage() {
        return "Intentelo nuevamente o Consulte al administrador del sistema";
    }

    /**
     * Metodo para insertar solicitudes de envio de correos
     */
    public static String sendEmail(String tpoems, String frmems, String to_ems,
            String cc_ems, String bccems, String msgems,
            String txtems, String attems, String codprs,
            String tpoprs) throws Exception {
        String strRst = "1";
      //  model.setSwdisconnect(false);

        // Insetar datos del correo a enviar
        String sqlCmd = "select sma_insertemailtosend('" + "" + "',"
                + "'" + model.getCodCia() + "',"
                + "'" + "" + "',"
                + "'" + tpoems + "',"
                + "'" + Util.getFechaActualBD() + " 00:00:00" + "',"
                + "'" + Util.getFechaActualBD() + " 00:00:00" + "',"
                + "'" + Util.getFechaActualBD() + " 00:00:00" + "',"
                + "'" + frmems + "',"
                + "'" + to_ems + "',"
                + "'" + cc_ems + "',"
                + "'" + bccems + "',"
                + "'" + msgems + "',"
                + "'" + txtems + "',"
                + "'" + attems + "',"
                + "'" + codprs + "',"
                + "'" + tpoprs + "',"
                + "'" + "Solicitado" + "',"
                + "'" + "POSTGRES" + "',"
                + "'" + "PTG" + "',"
                + "'" + "I" + "'"
                + ") ";

        System.out.println("sqlCmd = " + sqlCmd);
        String nroems = model.callFunctionOrProcedure(sqlCmd);
        if (nroems.startsWith("Error")) {
            strRst = nroems.split("/")[1];
        }
        /*else
         {		
         nroems = nroems.split("/")[1];
		
         // Insertar solicitud de correo
         String nrosrv = Util.dateFormat(new java.util.Date(), "hhmmssSSSS");
		
         sqlCmd =  "select sma_insertserverprinterrequest('"+ model.getCodCia() +"',"+
         "'"+ nrosrv            +"',"+
         "'"+ "EML"             +"',"+
         "'"+ "1"               +"',"+
         "'"+ codprs            +"',"+
         "'"+ tpoprs            +"',"+
         "'"+ ""                +"',"+
         "'"+ ""                +"',"+
         "'"+ ""                +"',"+
         "'"+ Util.getFechaActualBD() + " 00:00:00" +"',"+
         "'"+ nroems            +"',"+
         "'"+ "Solicitado"      +"',"+
         "'"+ ""                +"',"+
         "'"+ ""                +"',"+
         "'"+ ""                +"',"+
         "'"+ "POSTGRES"        +"',"+
         "'"+ "PTG"             +"',"+
         "'"+ "I"               +"'"+
         ") ";
         String strAux = model.callFunctionOrProcedure(sqlCmd);
         if( strAux.startsWith("Error") )
         strRst = strAux.split("/")[1];
         }*/

        System.out.println("Respuesta SendEmail Util_M : " + strRst);
//        model.setSwdisconnect(true);
        return strRst;
    }

} // Fin de la Clase Util
