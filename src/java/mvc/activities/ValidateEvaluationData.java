/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.activities;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mvc.model.ModelSma;

/**
 *
 * @author Miguel
 */
public class ValidateEvaluationData extends Activity {

    @Override
    public boolean isValidated(ModelSma model, HttpServletRequest request, HttpSession session, ServletContext application) throws SQLException, ParseException {
        try {
            String sqlCmd = "sma_virtual_campus.evaluation_valid(?,?,?,?,?)";

            List list = model.listarSP(sqlCmd, new Object[]{
                model.getCodCia(),
                model.getNroUsr(),
                model.getNroPrs(),
                ""
            });

            return list.isEmpty();

        } catch (Exception ex) {
            return true;
        }
    }

}
