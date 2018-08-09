package mvc.activities;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.model.ModelSma;
import mvc.model.Util;

public class ValidateBasicData extends Activity {

    @Override
    public boolean isValidated(ModelSma model, HttpServletRequest request, HttpSession session, ServletContext application) throws SQLException, ParseException {
        // TODO Auto-generated method stub

        String sqlCmd
                = "Select *\n"
                + "  from smahlu\n"
                + " where nroprs = '" + model.getNroPrs() + "'\n";

        model.listGenericHash(sqlCmd);
        List<Hashtable> list = model.getList();

        if (list.isEmpty()) {

            try {

                Map datos = new HashMap();

                datos.put("lsthlu", new Date(1990, 01, 01));
                datos.put("pswhlu", new Date(1990, 01, 01));
                datos.put("nroprs", model.getNroPrs());

                model.saveLogBook(datos, "smahlu", null);

                return false;

            } catch (Exception e) {

                Util.logError(e);
                return false;
            }
        } else {

            String strDate = list.get(0).get("LSTHLU").toString();

            Date lsthlu = new Date();
            Date nowDat = new Date();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            lsthlu = formatter.parse(strDate);

            Long dayNow = nowDat.getTime();  // Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object
            Long dayLst = lsthlu.getTime();     // 1000 Segundos  60  min  60 hour  24  dias

            Long numDay = ((dayNow - dayLst) / (1000 * 60 * 60 * 24));

            return numDay < 31;

        }

    }

}
