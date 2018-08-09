/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.activities;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.model.ModelSma;
import mvc.model.Util;

/**
 *
 * @author juancamilowong
 */
public class ValidateEvalDoc extends Activity {

    @Override
    public boolean isValidated(ModelSma model, HttpServletRequest request, HttpSession session, ServletContext application) throws SQLException, ParseException {
        try {
            boolean isValidated = true;
            
            String sqlCmd = "select count(smapvb.nropvb) "
                    + "from smapvb "
                    + "join smapvd "
                    + "on smapvb.nropvd = smapvd.nropvd "
                    + "where smapvb.codcia = '" + model.getCodCia() + "' "
                    + "and smapvb.codbas = '" + model.getCodPrs() + "' "
                    + "and trim(stdpvb) = 'Pendiente'  "
                    + "and now()::date between bgnpvd and endpvd";
            
            Integer result = Integer.parseInt(model.callFunctionOrProcedure(sqlCmd));
            if (result > 0) {
                isValidated = false;
            }
            
            return isValidated;
        } catch (Exception ex) {
            Util.logError(ex);
            return false;
        }

        }

    }
