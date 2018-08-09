package mvc.activities;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mvc.model.ModelSma;
import mvc.model.Util;

public class ValidateActiveSurvey extends Activity {

    @Override
    public boolean isValidated(ModelSma model, HttpServletRequest request, HttpSession session, ServletContext application) throws SQLException, ParseException {
        // TODO Auto-generated method stub
        boolean isValidated = true;

        String sqlCmd = "select nroqtr, nomqtr "
                + "from smaqtr "
                + "where  codcia = '" + model.getCodCia() + "'"
                + " and tpoqtr = 'ENC' "
                + " and current_date between fchbgn and fchend"
                + " and ( tpoprs  in ( Select tposgu from sma_systemuser_roles('"
                + model.getCodCia() + "','" + model.getCodPrs() + "') ) or  tpoprs = 'PRS' )";
//                        "Select * "
//                                    + "	from smahlu "
//                                    + "	where codcia = '"+model.getCodCia()+"'"
//                                    + "	 and  codprs = '"+model.getCodPrs()+"'";
        // " and codprs = '" + model.getCodPrs() + "'" +

        model.listGenericHash(sqlCmd);
        List<Hashtable> listQtr = model.getList();

        if (listQtr.isEmpty()) {
            return true; // String query = "Select sma_sqlinsert_smahlu('"+ model.getCodCia() +"', '" +model.getCodPrs()+ "' , false , false , 'I')"; // String retorno = model.callFunctionOrProcedure(query);			  	  
        } else {
            try {
                sqlCmd = "select smtpsm from smabas where codprs = '"+model.getCodPrs()+"'";
                
                String smtpsm = model.callFunctionOrProcedure(sqlCmd);
                
                if(smtpsm.equals("00")||smtpsm.trim().equals("")||smtpsm.equals("01")){
                    return true;
                }
                for (Hashtable encuesta : listQtr) {
                    
                    sqlCmd = "select agnprs, prdprs from sma_calendaractivitysearch('" + model.getCodCia() + "', '" + model.getCodPrs() + "', 'BAS', 'PCS')";
                    model.listGenericHash(sqlCmd);
                    if (model.getList().isEmpty()) {
                        sqlCmd = "select agnprs, prdprs from sma_calendaractivitysearch('" + model.getCodCia() + "', '" + model.getCodPrs() + "', 'ALL', 'PCS')";
                        model.listGenericHash(sqlCmd);
                    }
                    
                    
                    
                    List listKls = model.getList();
                    Hashtable smaTmp;
                    String agnprs;
                    String prdprs;
                    String nroqtr = ((Hashtable) listQtr.get(0)).get("nroqtr").toString();
                    
                    
                    if (!listKls.isEmpty()) {
                        smaTmp = (Hashtable) listKls.get(0);
                        agnprs = smaTmp.get("agnprs").toString();
                        prdprs = smaTmp.get("prdprs").toString();
                        
                        sqlCmd = "select codprs "
                                + "from smaqsa "
                                + "where codcia = '" + model.getCodCia() + "' "
                                + "and codprs = '" + model.getCodPrs() + "' "
                                + "and agnprs = '" + agnprs + "' "
                                + "and prdprs = '" + prdprs + "' "
                                + "and nroqtr = '" + nroqtr + "'";
                        model.listGenericHash(sqlCmd);
                        List listRsp = model.getList();
                        
                        
                        if (listRsp.isEmpty()) {
                            return false;
                        }
                        
                        
                        
                    }
                    
                }

                return isValidated;
            } catch (Exception ex) {
                Util.logError(ex);
                return false;
            }

        }
    }
}
