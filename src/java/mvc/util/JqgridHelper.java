/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.util;

import java.util.HashMap;
import mvc.controller.jQgridTab;

/**
 *
 * @author Anthony Cajamarca
 * @since 17/03/2016
 * 
 */
public class JqgridHelper {

    private jQgridTab tab;

    public JqgridHelper(HashMap dataMap) {
        tab = new jQgridTab();
        tab.setColumns(dataMap.get("colums").toString().split(","));
        tab.setTitles(dataMap.get("titles").toString().split(","));
        tab.setKeys((int[]) dataMap.get("keys"));
        tab.setHiddens((int[]) dataMap.get("hiddens"));
        tab.setSelector(dataMap.get("selector").toString());
        tab.setPaginador(dataMap.get("paginator").toString());
        tab.setFormatDate((int[]) dataMap.get("formatDate"));
    }

}
