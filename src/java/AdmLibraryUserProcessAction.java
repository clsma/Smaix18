/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import mvc.model.Util;
import java.io.IOException;
import javax.servlet.ServletException;
import mvc.controller.Action;

/**
 *
 * @author Carlos Pinto Jimenez ( Cl-Sma )
 * @Created 5/09/2016 08:19:32 AM
 * @Archivo AdmLibraryUserProcessAction
 */
public class AdmLibraryUserProcessAction extends Action {

    private JSONObject json;
    private String sqlCmd;

    @Override
    public void run() throws ServletException, IOException {
        String event = Util.getStrRequest("event", request);
        json = new JSONObject();

        try {
            if (event.equals("")) {
             
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
  }
    
    private void write(Object js) throws IOException {
        response.getWriter().write(JSONValue.toJSONString(js));

    }     

}
