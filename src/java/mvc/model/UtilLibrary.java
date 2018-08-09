/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Cl-sma
 */
public class UtilLibrary {

    private static ModelSma model;

    public static ModelSma getModel() {
        if (model == null) {
            return new ModelSma();
        }
        return model;
    }

    public static void setModel(ModelSma model) {
        UtilLibrary.model = model;
    }

    public static String maxNmaBbl(String codcia) {

        try {
            String sqlCmd = "select nvl (max( to_number( nmabbl ) )  , 0 ) + 1  nmabbl"
                    + "        from smabbl"
                    + "       where codcia = '" + codcia + "'";

            return (String) getModel().getData(sqlCmd, null);

        } catch (Exception e) {
            Util.logError(e);
            return null;
        }
    }

    public static String stadistic(String codbst, String tpobst, String numbst) throws Exception {

        String sqlCmd = "sma_library.statistic(\n"
                + "                             p_codbst => '" + codbst + "',\n"
                + "                             p_tpobst => '" + tpobst + "',\n"
                + "                             p_numbst => '" + numbst + "',\n"
                + "                             p_fchbst =>'" + Util.dateFormat(new Date(), "yyyy-MM-dd") + "',\n"
                + "                             p_hrabst =>null,\n"
                + "                             p_idesxu => '" + model.getSessionSxu() + "'\n"
                + "                    )";

        return model.callFunctionOrProcedure(sqlCmd);
    }

    public static String statePerson(String codcia, String nrotrc, String tpotrc, Session sesion) throws Exception {

        return sesion == null
                ? model.callFunctionOrProcedure("sma_library.states( p_codcia => '" + codcia + "' , p_nrotrc => '" + nrotrc + "' , p_tpotrc => '" + tpotrc + "' )")
                : model.callFunctionOrProcedureNotTransaction("sma_library.states( p_codcia => '" + codcia + "' , p_nrotrc => '" + nrotrc + "' , p_tpotrc => '" + tpotrc + "' )", sesion);
    }

    public static int dailyStatistic(String tpobst, String codbst, String user) {

        String sqlCmd = "select count ( smabst.nrobst) count \n"
                + "                      from smabst\n"
                + "                      join smasxu\n"
                + "                        on smasxu.idesxu = smabst.idesxu\n"
                + "                    where nrousr = '" + user + "'\n"
                + "                      and tpobst = '" + tpobst + "' \n"
                + "                      and trunc( sysdate ) = trunc( fchbst )\n";
        if (!codbst.trim().equals("COUNT")) {
            sqlCmd += " and codbst = '" + codbst + "'";
        }

        try {
            return Integer.valueOf((String) model.getData(sqlCmd, null));
        } catch (Exception e) {
            Util.logError(e);
            return 0;
        }

    }

    public static String validateBlockedLibrary(String codprs, String tpoprs, String tpoblk, String appbld, String nrobtk, int usuario) throws Exception {
        String sqlCmd = "select count(nrobld) as count"
                + "        from smablk "
                + "        join smabld "
                + "          on smabld.nroblk = smablk.nroblk "
                + "         and smabld.appbld = '" + appbld + "' "
                + "         and smabld.stdbld = 'Activo' "
                + "       where smablk.codcia = '" + model.getCodCia() + "' "
                + "         and smablk.tpoblk = '" + tpoblk + "' "
                + "         and smablk.codblk = '" + nrobtk + "' "
                + "         and smablk.nrotrc = '" + codprs + "' "
                + "         and smablk.tpotrc = '" + tpoprs + "' "
                + "         and smablk.stdblk = 'Activo' ";
        model.listGenericHash(sqlCmd);
        String strAux = ((Hashtable) model.getList().get(0)).get("COUNT").toString();
        if (strAux.equals("0")) {
            return "";
        }

        return "<label class='label_'>Error : " + (usuario == 1 ? "Usted" : "El usuario") + " se encuentra " + " bloqueado en la Biblioteca " + nombbl(model.getCodCia(), nrobtk) + "." + "</label>";
    }

    public static String nombbl(String codcia, String nrobtk) throws SQLException {
        return (String) model.getData("select nombbl from smabbl where codcia = '" + model.getCodCia() + "' and nrobtk = '" + nrobtk + "'", null);

    }

    public static List userActives(String nrousr, String tpousr, String nrobtk) throws SQLException {

        nrobtk = nrobtk == null ? "" : nrobtk;
        String sqlCmd = "  select * from table ( sma_library.user_library(\n"
                + "                                                           p_codcia => '" + model.getCodCia() + "',"
                + "                                                           p_nrobtk => '" + nrobtk.trim() + "'\n"
                + "                                                         ) )"
                + "     where 1=1 ";

        if (!tpousr.isEmpty()) {
            sqlCmd += "and tpobub = '" + tpousr + "' ";
        }

        if (!nrousr.isEmpty()) {
            sqlCmd += " and nrousr = '" + nrousr + "'";
        }

        model.listGenericHash(sqlCmd);
        return model.getList();
    }

    public static String validConfiguration(String codprs, String tpoprs, String tpolan, String option, Session sesion) throws Exception {
        String returned;
        option = option == null ? "" : option;

        if (sesion == null) {
            returned = model.callFunctionOrProcedure("sma_library.configuration(p_codcia => '" + model.getCodCia() + "' , "
                    + "                                                         p_tpotrc => '" + tpoprs + "' , "
                    + "                                                         p_nrotrc => '" + codprs + "' ,"
                    + "                                                         p_tpolan => '" + tpolan + "' ,"
                    + "                                                         p_option => '" + option + "' )");

        } else {
            returned = model.callFunctionOrProcedureNotTransaction("sma_library.configuration(p_codcia => '" + model.getCodCia() + "' , "
                    + "                                                                       p_tpotrc => '" + tpoprs + "' , "
                    + "                                                                       p_nrotrc => '" + codprs + "' ,"
                    + "                                                                       p_tpolan => '" + tpolan + "' ,"
                    + "                                                                       p_option => '" + option + "' )", sesion);
        }

        return returned;

    }

    public static String hasLoan(String nrotrc, String tpotrc, String tpolan, Session sesion) throws Exception {
        String returned;

        returned = model.callFunction("sma_library.configuration(p_codcia => '" + model.getCodCia() + "' , "
                + "                                              p_tpotrc => '" + tpotrc + "' , "
                + "                                              p_nrotrc => '" + nrotrc + "' ,"
                + "                                              p_tpolan => '" + tpolan + "' ,"
                + "                                              p_option => '' )", sesion);

        return returned;

    }

    public static HashMap smabtk(String nrobtk) {
        try {

            if (nrobtk.trim().isEmpty()) {
                nrobtk = "sma_library.get('" + model.getCodCia() + "')";
            } else {
                nrobtk = "'" + nrobtk + "'";
            }
            String sqlCmd = "select nrobtk ,  nombtk nombtk from smabtk where nrobtk = " + nrobtk + "";
            return (HashMap) model.copy("smabtk", "nrobtk = " + nrobtk, null);

        } catch (Exception e) {
            Util.logError(e);
            return new HashMap();
        }
    }

    public static int loan_count(String codcia, String tpotrc, String nrotrc, String tpoloan) throws Exception {

        String res = model.callFunctionOrProcedure("sma_library.loan_count( \n"
                + "                                                      p_codcia => '" + codcia + "',\n"
                + "                                                      p_tpotrc => '" + tpotrc + "',\n"
                + "                                                      p_nrotrc => '" + nrotrc + "',\n"
                + "                                                      p_tpolan => '" + tpoloan + "'\n"
                + "                                 )");

        return Integer.valueOf(res);

    }

    public static int libraryConfig(String codcia, String tpotrc, String tpoloan) throws SQLException {
        if (tpoloan.equals("INT")) {
            tpoloan = "canint";
        } else if (tpoloan.equals("PRT")) {
            tpoloan = "canprt";
        } else if (tpoloan.equals("CPY")) {
            tpoloan = "cancpy";
        } else if (tpoloan.equals("RSV") || tpoloan.equals("BRV")) {
            tpoloan = "canbrv";
        }
        String sql = "select nvl( " + tpoloan + " , 0 ) count \n"
                + "   from smabbc \n"
                + "  where tpoprs = '" + tpotrc + "'"
                + "    and  codcia = '" + codcia + "'";
        sql = (String) model.getData(sql, null);
        sql = sql.trim().isEmpty() ? "0" : sql;
        try {
            return Integer.valueOf(sql);
        } catch (Exception e) {
            Util.logError(e);
            return 0;
        }

    }

    public static int maxCondonation(String nrousr, String smtpsm, Session sesion) {

        String sqlCmd = "sma_library.condonation_count( p_codbas => '" + nrousr + "' )";
        try {
            return Integer.parseInt(model.callFunction(sqlCmd, sesion));
        } catch (Exception e) {
            Util.logError(e);
            return 0;
        }

    }

    public static int userCondonations(String nrotrc, String tpotrc, String smtpsm, Session sesion) {

        String sqlCmd = "select count(*)\n"
                + "     from smabkd\n"
                + "    where nrotrc = '" + nrotrc + "'\n"
                + "      and tpotrc = '" + tpotrc + "'\n"
                + "      and smtpsm " + (smtpsm == null || smtpsm.trim().isEmpty() ? " is null " : " = '" + smtpsm + "'");

        try {

            return Integer.parseInt((String) model.getData(sqlCmd, sesion));

        } catch (Exception e) {
            Util.logError(e);
            return 0;
        }
    }
    
    public static int isAvailable(String nrotrc, String tpotrc, String smtpsm, Session sesion) {

        String sqlCmd = "select count(*)\n"
                + "     from smabkd\n"
                + "    where nrotrc = '" + nrotrc + "'\n"
                + "      and tpotrc = '" + tpotrc + "'\n"
                + "      and smtpsm " + (smtpsm == null || smtpsm.trim().isEmpty() ? " is null " : " = '" + smtpsm + "'");

        try {

            return Integer.parseInt((String) model.getData(sqlCmd, sesion));

        } catch (Exception e) {
            Util.logError(e);
            return 0;
        }
    }
}
