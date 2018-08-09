package taglib.form;

import java.util.Hashtable;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import taglib.form.ControlForm;
import javax.servlet.jsp.*;
import java.util.StringTokenizer;
import javax.servlet.jsp.tagext.*;

import mvc.model.ModelSma;
import mvc.model.Util;

public class GridControl extends TagSupport {

  //==========================================================================
    //                Atributos de la Etiqueta
    //==========================================================================
    private ModelSma model = null;
    private Hashtable configGrid = new Hashtable();
    private String color = "#FFFFFF";
    private String height = "200";
    private String align = "center";
    private boolean filter = false;
    private String hg = "30px";
    private String cols = "20";

    /**
     * @param model
     */
    public void setModel(ModelSma model) {
        this.model = model;
    }

    /**
     * @param configGrid
     */
    public void setConfigGrid(Hashtable configGrid) {
        this.configGrid = configGrid;
    }

    /**
     * @param color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @param height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    /**
     * @param align
     */
    public void setAlign(String align) {
        this.align = align;
    }

  //==================================================
    //             Metodos de Ciclo de Vida
    //==================================================
    public String getSourceCode() throws JspException {

        int w = 0, wg = 0, i = 0, j = 0, y = 0, length = 0;
        String rowTitle = "", title = "", value = "", format = "", field = "";
        String strControl = "", option = "", strScriptCalendar = "", tooltips = "";
        String checked = "", javascript = "";
        String arrColumn[][];
        String[] arrTrg = {};
        String arrBgColor[] = new String[2];
        Iterator it;
        List listData = new LinkedList();
        List listCmb_ = new LinkedList();
        List sisDat = new LinkedList();
        Map dataRow = new Hashtable();
        StringBuffer str = new StringBuffer();
        String qryCmb = "";
        String disabled = "";

        try {
            arrBgColor[0] = this.color;
            arrBgColor[1] = "#FFFFFF";
            // Obtener configuración de Columnas
            arrColumn = (String[][]) configGrid.get("ConfigCol");

            rowTitle += "<tr style='display:none' id='trFilter' height='22px'><th colspan = '" + arrColumn.length + "' bgcolor = '#CCCCCC' align = 'left'>"
                    + "<font style='color:#333333' face='Verdana' size = '2'>Filtrar&nbsp;</font><input type ='text' name = 'quickfind' id = 'quickfind' class='text'>"
                    + "</th></tr>";

            rowTitle += "<tr class='botonhover' height='22px'>\n";

            // Limpiar matriz
            for (i = 0; i < arrColumn.length; i++) {
                for (j = 0; j < 8; j++) {
                    if (arrColumn[i][j] == null) {
                        arrColumn[i][j] = "";
                    }
                }
            }

            for (i = 0; i < arrColumn.length; i++) {
                // Titulo de la Columna
                title = arrColumn[i][1];
                if (title.equals("")) {
                    title = "&nbsp;";
                }
                // Ancho de la Columna
                if (!arrColumn[i][2].equals("")) {
                    length = Integer.parseInt(arrColumn[i][2]);
                    length = (length / 10) + 3;
                    if (title.length() > length) {
                        rowTitle += "          <th width='" + arrColumn[i][2] + "' align='center' class = 'rowtitle'><label title='" + title + "' class = 'label'>" + title.substring(0, length - 3) + "..." + "</label></th>\n";
                    } else {
                        rowTitle += "          <th width='" + arrColumn[i][2] + "' align='center' class = 'rowtitle'>" + title + "</th>\n";
                    }
                    w += Integer.parseInt(arrColumn[i][2]);
                }
            }

            rowTitle += "        </tr>\n";

            wg = w + 20;

            if (filter) {
                hg = "50px";
            }

            str.append("<table id='tableData' width='" + wg + "' align='" + this.align + "' cellpadding='0' cellspacing='0' class='simple'>\n  <tr>\n    <td>\n");
            // str.append("      <div id='layTitle' style='position:relative; left:0px; top:0px; width:"+wg+"px; height:"+hg+"; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; '>\n");
            str.append("      <div id='layTitle' style='left:0px; top:0px; width:" + wg + "px; height:" + hg + "; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; '>\n");
            str.append("      <table id='tableColumns' width='" + w + "' border='0' cellspacing='1' cellpadding='0' align='left'>\n");
            str.append(rowTitle + "      </table>\n");
            str.append("      </div>\n");

            listData = (List) configGrid.get("Data");
            //System.out.println(listData);
            it = listData.iterator();

            //str.append("      <div id='layPublic' style='position:relative; left:0px; top:0px; width:"+wg+"px; height:"+this.height+"px; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; overflow:auto;'>\n");
            str.append("      <div id='layPublic' style=' left:0px; top:0px; width:" + wg + "px; height:" + this.height + "px; z-index:1; background-color:#ffffff; layer-background-color:#ffffff; border:1px none #000000; visibility:visible; overflow:auto;'>\n");
            if (!listData.isEmpty()) {
                str.append("      <table id = 'GridTable' width='" + w + "' border='1' cellspacing='1' cellpadding='0' align='left' class='simple'>\n");

                /*ARMO EL THEAD*/
                if (this.filter) {
                    str.append("       <thead>");
                    str.append("       <tr style='display:none' id='trIcons'>");
                    for (i = 0; i < arrColumn.length; i++) {
                        if (!arrColumn[i][1].equals("")) {
                            title = arrColumn[i][3].equals("") ? "<label style ='display:none'>|</label><img name='imgFilter' border = '0'/><label style ='display:none'>|</label>" : "";
                            str.append("          <th  align='center' class = 'label'>" + title + "</th>\n");
                        }
                    }
                    str.append("       </tr>");
                    str.append("       </thead>");
                }
                /*FIN THEAD*/

                str.append("       <tbody>");
                i = -1;
                Object mapRegistro;
                while (it.hasNext()) {
                    i++;

                    mapRegistro = it.next();

                    if (mapRegistro instanceof Hashtable) {
                        dataRow = (Hashtable) mapRegistro;
                    } else {
                        dataRow = (LinkedHashMap) mapRegistro;
                    }

//		      dataRow = (Hashtable) it.next();		      
                    str.append("        <tr bgcolor='" + arrBgColor[(i % 2)] + "' style='height: 25px;' onmouseover='cargarImagen(this,\"#FFFFAA\")' onmouseout='quitarImagen(this,\"" + arrBgColor[(i % 2)] + "\")'>\n");
                    for (j = 0; j < arrColumn.length; j++) {
                        // Obtener Valor
                        value = "";
                        if (dataRow.containsKey(arrColumn[j][0])) {
                            value = dataRow.get(arrColumn[j][0]).toString();
                        }
                        if (arrColumn[j][0].equals("#") || arrColumn[j][0].equals("seq")) {
                            value = "" + (i + 1);
                            dataRow.put("seq", value);
                        }
                        // Ancho de Columnas
                        if (!arrColumn[j][2].equals("")) {
                            // Verificar tipo de Control
                            if (arrColumn[j][3].equals("")) {
                                if (value.equals("")) {
                                    value = "&nbsp;";
                                }
                                length = Integer.parseInt(arrColumn[j][2]);
                                length = (length / 10) + 3;
                                if (value.length() > length) {
                                    strControl = "<label title='" + value + "' name='label_" + arrColumn[j][0] + "' id='label_" + (arrColumn[j][0] + i) + "' class = 'label'>" + value.substring(0, length - 3) + "..." + "</label>";
                                } else {
                                    strControl = "<label title='" + value + "' name='label_" + arrColumn[j][0] + "' id='label_" + (arrColumn[j][0] + i) + "' class = 'label'>" + value + "</label>";
                                }
                            } else if (arrColumn[j][3].equals("Check")) {
                                checked = "";
                                disabled = "";
                                if (value.equals("1")) {
                                    checked = "checked";
                                    disabled = "";
                                } else if (value.equals("2")) {
                                    checked = "";
                                    disabled = "disabled";
                                } else if (value.equals("3")) {
                                    checked = "checked";
                                    disabled = "disabled";
                                }
                                javascript = "onClick=\"updateValueCheckBox(this)\"";
                                if (arrColumn[j][6].startsWith("javascript")) {
                                    javascript = arrColumn[j][6].substring(arrColumn[j][6].indexOf("#") + 1);
                                }
                                strControl = "<input class='check' name='" + (arrColumn[j][0] + i) + "' id='" + (arrColumn[j][0] + i) + "' type='checkbox' " + checked + " value='" + value + "' " + javascript + " " + disabled + ">";
                            } else if (arrColumn[j][3].equals("Edit")) {
                                try {
                                    cols = "cols = '" + arrColumn[j][8] + "'";
                                } catch (Exception e) {
                                    cols = "style = 'width:100%'";
                                }
                                strControl = "<textarea class='textarea' " + cols + " rows = '2' name='" + (arrColumn[j][0] + i) + "' id='" + (arrColumn[j][0] + i) + "' >" + value + "</textarea>";
                            } else if (arrColumn[j][3].equals("Combo")) {

                                javascript = "";
                                if (!arrColumn[j][4].equals("") && !arrColumn[j][4].equals("1")) {
                                    javascript = "onChange=\"javascript:" + arrColumn[j][4] + "\"";
                                }

                                title = arrColumn[j][1];

                                // Generar contenidos del combos
                                qryCmb = arrColumn[j][6];
                                listCmb_.clear();

                                if (!qryCmb.equals("")) {

                                    if (!qryCmb.substring(0, 6).toLowerCase().equals("select") && !qryCmb.startsWith("DATA:")) {
                                        qryCmb = "select nomelm,dspelm from smaelm where " + qryCmb + "order by nroelm";
                                    }

                                    // Se ejecuta la consulta y se obtiene las lista con los resultados
                                    if (!qryCmb.startsWith("DATA:")) {
                                        model.listGeneric(qryCmb);
                                        listCmb_ = model.getList();
                                    } else {
                                        // Se carga la lista con datos fijos
                                        qryCmb = qryCmb.substring(5);
                                        arrTrg = qryCmb.split("#");
                                        for (int k = 0; k < arrTrg.length; k++) {
                                            sisDat = new LinkedList();
                                            if (arrTrg[k].indexOf(",") != -1) {
                                                sisDat.add(arrTrg[k].substring(0, arrTrg[k].indexOf(",")));
                                                sisDat.add(arrTrg[k].substring(arrTrg[k].indexOf(",") + 1));
                                            } else {
                                                sisDat.add(arrTrg[k]);
                                                sisDat.add(arrTrg[k]);
                                            }
                                            listCmb_.add(sisDat);
                                        }
                                    }
                                }

                                strControl = "<select name='" + (arrColumn[j][0] + i) + "' " + option + " class='combo' id='" + (arrColumn[j][0] + i) + "' title='" + title + "' " + javascript + ">\n";
                                for (int k = 0; k < listCmb_.size(); k++) {
                                    option = "";
                                    sisDat = (List) listCmb_.get(k);
                                    if (sisDat.size() == 1) {
                                        sisDat.add(sisDat.get(0));
                                    }
                                    if (sisDat.get(0).equals(value) || sisDat.get(1).equals(value)) {
                                        option = "selected";
                                    }
                                    strControl += "  <option value='" + sisDat.get(0) + "' " + option + ">" + sisDat.get(1) + "</option>\n";
                                }
                                strControl += "</select>";

                            } else if (arrColumn[j][3].equals("Radio")) {
                                checked = "";
                                if (value.equals("1")) {
                                    checked = "checked";
                                }
                                strControl = "<input class='radio' name='" + (arrColumn[j][0]) + "' id='" + (arrColumn[j][0] + i) + "' type='radio' " + checked + " value='" + value + "'>";
                            } else if (arrColumn[j][3].equals("Date")) {
                                strControl = "<input readonly='true' name='" + (arrColumn[j][0] + i) + "' type='text' id='" + (arrColumn[j][0] + i) + "' class='text' title='" + arrColumn[j][1] + "' size='10' value='" + value + "'>";
                                strControl += "<input type='button' name='btonDate" + j + "' value='...' class='botonsmall' onClick=\"javascript:showCal('Cal_" + (arrColumn[j][0] + i) + "')\">";
                                strScriptCalendar += "  addCalendar('Cal_" + (arrColumn[j][0] + i) + "', 'Elegir Fecha', '" + (arrColumn[j][0] + i) + "');\n";
                            } else if (arrColumn[j][3].startsWith("Text")) {
                                format = arrColumn[j][3].substring(arrColumn[j][3].indexOf("Text") + 4);
                                if (!format.equals("")) {
                                    format = "onKeyPress=\"return validFormat(this,event,'" + format + "')\"";
                                }

                                StringTokenizer tokens = new StringTokenizer(arrColumn[j][3], ":");
                                String actions = "";
                                String strAux = "";
                                if (tokens.countTokens() > 0) {
                                    strAux = tokens.nextToken();
                                    format = strAux.substring(strAux.indexOf("Text") + 4);
                                    format = "onKeyPress=\"return validFormat(this,event,'" + format + "')\"";
                                    while (tokens.hasMoreTokens()) {
                                        StringTokenizer tknAct = new StringTokenizer(tokens.nextToken(), "=");
                                        actions += tknAct.nextToken() + "='" + tknAct.nextToken() + "' ";
                                    }
                                }
                                strControl = "<input class='text' name='" + (arrColumn[j][0] + i) + "' id='" + (arrColumn[j][0] + i) + "' type='text' size='8' maxlength='12' title='" + arrColumn[j][1] + "' " + format + " value='" + value + "'>";
                            } else if (arrColumn[j][3].equals("Link")) {
                                // Url's
                                option = arrColumn[j][6];
                                if (!option.equals("")) {
                                    while (option.indexOf("#") != -1) {
                                        field = option.substring(option.indexOf("#") + 1);
                                        field = field.substring(0, field.indexOf("#"));

                                        if (dataRow.containsKey(field)) {
                                            option = option.replaceAll("#" + field + "#", dataRow.get(field).toString());
                                        } else {
                                            option = option.replaceAll("#" + field + "#", "");
                                        }

                                        if (option.indexOf("\"") != -1) {
                                            option = option.replaceAll("\"", " Pgdas");
                                        }
                                    }
                                }
                                // Target
                                format = arrColumn[j][7];
                                if (!format.equals("")) {
                                    format = "target='" + format + "'";
                                }
                                // Tool Tips - Status Bar
                                tooltips = arrColumn[j][1];
                                // Texto
                                if (value.equals("")) {
                                    value = "[...]";
                                } else {
                                    tooltips += " [" + value + "]";
                                    length = Integer.parseInt(arrColumn[j][2]);
                                    length = (length / 10) + 3;
                                    if (value.length() > length) {
                                        value = value.substring(0, length - 3) + "...";
                                    }
                                }

                                option = option.replaceAll("javascript:", "");

                                strControl = "<a class='hypgrid' onclick=\"" + option + "\" href=\"" + "#" + "\" " + format + " onMouseOver='window.status=\"" + tooltips + "\";return true' title='" + tooltips + "'>" + value + "</a>";
                            } else if (arrColumn[j][3].equals("LinkTable")) {

                                option = arrColumn[j][6];
                                if (!option.equals("")) {
                                    while (option.indexOf("#") != -1) {
                                        field = option.substring(option.indexOf("#") + 1);

                                        field = field.substring(0, field.indexOf("#"));

                                        if (dataRow.containsKey(field)) {
                                            option = option.replaceAll("#" + field + "#", dataRow.get(field).toString());
                                        } else {
                                            option = option.replaceAll("#" + field + "#", value);
                                        }
                                        //System.out.println(option+" --");
                                    }
                                }
//							 Target
                                format = arrColumn[j][7];
                                if (!format.equals("")) {
                                    format = "target='" + format + "'";
                                }
                                // Tool Tips - Status Bar
                                tooltips = arrColumn[j][1];
                                // Texto
                                if (value.equals("")) {
                                    value = "[...]";
                                } else {
                                    tooltips += " [" + value + "]";
                                    length = Integer.parseInt(arrColumn[j][2]);
                                    length = (length / 10) + 3;
                                    if (value.length() > length) {
                                        value = value.substring(0, length - 3) + "...";
                                    }
                                }

                                strControl = "<a class='hypgrid' href=\"" + option + "\" " + format + " onMouseOver='window.status=\"" + tooltips + "\";return true' title='" + tooltips + "'>" + value + "</a>";
                                strControl += "<br/><div id = 'div" + arrColumn[j][0] + (y + 1) + "' style = 'display: none'>";
                                strControl += arrColumn[j][8];
                                strControl += "</div>";
                                y++;
                            } else if (arrColumn[j][3].equals("Search")) {
                                String filter = arrColumn[j][8].split("~")[0];//filtro

                                ControlForm tagControl = new ControlForm();
                                String[] fields = arrColumn[j][8].split("~")[1].split("#");//fields		    	    
                                String[] columns = arrColumn[j][8].split("~")[3].split("#");//columns		    	
                                String namesFld = "", namesCol = "";

                                tagControl.setId(arrColumn[j][0] + i);
                                tagControl.setName(arrColumn[j][0] + i);
                                tagControl.setFiltro(filter);
                                tagControl.setModel(this.model);
                                tagControl.setTitle(arrColumn[j][8].split("~")[2]);//title				    	      
                                tagControl.setTypctl("Search");
                                tagControl.setTable(arrColumn[j][8].split("~")[4]);//table		    	  
                                tagControl.setLength(arrColumn[j][8].split("~")[5]);//length

                                for (int p = 0; p < fields.length; p++) {
                                    namesFld += fields[p] + ",";
                                }

                                namesFld = namesFld.substring(0, namesFld.length() - 1);//quito la ultima coma
                                tagControl.setFields(namesFld);

                                for (int p = 0; p < columns.length; p++) {
                                    namesCol += columns[p] + ",";
                                }

                                namesCol = namesCol.substring(0, namesCol.length() - 1);//quito la ultima coma
                                tagControl.setTitlecolumns(namesCol);
                                tagControl.setTitlecolumns(namesCol);
                                strControl = tagControl.getSourceCode();
                            }

                            // Verificar si lleva campo oculto ó no
                            if (arrColumn[j][4].equals("1")) {
                                strControl += "<input name='" + (arrColumn[j][0] + "H") + "' id='" + (arrColumn[j][0] + "H" + i) + "' type='hidden' value='" + value + "'>";
                            }
                            // Verificar alineamiento
                            if (arrColumn[j][5].equals("")) {
                                arrColumn[j][5] = "left";
                            }

                            str.append("          <td width='" + arrColumn[j][2] + "' align='" + arrColumn[j][5] + "'>" + strControl + "</td>\n");
                        } else {
                            if (!arrColumn[j][0].equals("")) {
                                strControl = "          <input name='" + (arrColumn[j][0]) + "' id='" + (arrColumn[j][0] + i) + "' type='hidden' value='" + value + "'>\n";
                                str.append(strControl);
                            }
                        }
                    }
                    str.append("        </tr>\n");
                }
                str.append("       </tbody>");
                str.append("      </table>\n");
            }
            str.append("      </div>\n");
            str.append("    </td>\n  </tr>\n</table>");

            if (!strScriptCalendar.equals("")) {
                strScriptCalendar = "<script language='JavaScript' type='text/JavaScript'>\n" + strScriptCalendar + "</script>\n";
            }

        } catch (Exception ex) {
            Util.logError(ex);
            throw new JspException("Class GridControl Metodo [getSourceCode] " + ex.getMessage());
        }

    //System.out.println(strScriptCalendar+str.toString());
        return strScriptCalendar + str.toString();
    }

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(this.getSourceCode());
        } catch (Exception ex) {
            throw new JspException("Class ControlForm Metodo [doEndTag] " + ex.getMessage());
        }
        return SKIP_BODY;
    }

}// Fina Class
