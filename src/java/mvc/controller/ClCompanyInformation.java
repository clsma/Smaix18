/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;import mvc.model.Util;

import java.util.Hashtable;
import mvc.model.Util;
import mvc.model.WebModel;

/**
 *
 * @author clsma
 */ 
public abstract class ClCompanyInformation {

    public static Hashtable hasCia;
    public static String STYLE_;
    public static String NOMBRE_CIA;
    public static String LEMA_CIA;
    public static String ATENCION_CIA;
    public static String TELEFONO_CIA;
    public static String EMAIL_CIA;

    public static void ClCompanyInformation() {
        WebModel model = new WebModel();
        try {
            model.listGenericHash("select * from smacia");
            hasCia = (Hashtable) model.getList().get(0);
            STYLE_ = Util.validStr(hasCia, "THMCIA");
            NOMBRE_CIA = Util.validStr(hasCia, "NOMCIA");
            LEMA_CIA = Util.validStr(hasCia, "LEMCIA");
            ATENCION_CIA = Util.validStr(hasCia, "CTGCIA");
            TELEFONO_CIA = Util.validStr(hasCia, "TELCIA");
            EMAIL_CIA = Util.validStr(hasCia, "EMLCIA");
        } catch (Exception e) {
            Util.logError(e);
        }
    }

}
