package mvc.util;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

public class Util {

    private static final SimpleDateFormat 
            DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat();
    private static Calendar calendar;

    /**
     * Da formato a una fecha utilizando el formato JDBC predeterminado
     * [yyyy-MM-dd][yyyy/MM/dd] [dd/MM/yyyy][MM/dd/yyyy] [yyyy-MM-dd HH:mm:ss]
     * @param d
     * @param formato
     * @return 
     */
    public static String dateFormat(Date d, String formato) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(formato);
        return d == null ? "" : DATE_FORMAT.format(d);
    }

    public static String dateFormat(String d, String formato) throws Exception {
        return dateFormat(getFecha(d), formato);
    }

    /**
     * Convierte una Cadena a java.util.Date (dd/mm/yyyy dd-mm-yyyy)
     * @param mydate
     * @return 
     * @throws java.lang.Exception
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
                if (fullHour.length() > 6) {
                    mer = fullHour.substring(6);
                }
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

            month = month.substring(0, 1).toUpperCase()
                    + month.substring(1).toLowerCase();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1,
                    Integer.parseInt(day), nHour, Integer.parseInt(minute));
            date = new java.util.Date(calendar.getTime().getTime());

        } catch (Exception ex) {
            throw new Exception(" Clase [Util] metodo [getFecha] " + ex);
        }
        return date;
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

    
} // Fin de la Clase Util
