package taglib.form;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import mvc.model.ModelSma;
import mvc.model.Util;

public class ControlForm extends TagSupport  {

    // ==========================================================================
    // Atributos de la Etiqueta
    // ==========================================================================
    private ModelSma model = null;
    private String typctl = "";
    private String name = "";
    private String title = "";
    private String value = "";
    private String filtro = "";
    private String activo = "";
    private String fields = "";
    private String table = "";
    private String online = "";
    private String titlecolumns = "";
    private String titlesearch = "Datos";
    private String state = "";
    private String javascriptnew = "";
    private String showbuttonbar = "11111";
    private String url = "";
    private String length = "40";
    private String format = "";
    private String inputmask = "";
    private String fldAux = "";

    public ControlForm() {
        javascriptnew = "";
    }

    @Override
    protected void finalize() throws Throwable {
        javascriptnew = "";
        model = null;
        typctl = "";
        name = "";
        title = "";
        value = "";
        filtro = "";
        activo = "";
        fields = "";
        table = "";
        online = "";
        titlecolumns = "";
        titlesearch = "Datos";
        state = "";
        javascriptnew = "";
        showbuttonbar = "11111";
        url = "";
        length = "40";
        format = "";
        inputmask = "";
        fldAux = "";
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param model
     */
    public void setModel(ModelSma model) {
        this.model = model;
    }

    /**
     * @param typctl
     */
    public void setTypctl(String typctl) {
        this.typctl = typctl.trim();
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value.trim();
    }

    /**
     * @param filtro
     */
    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    /**
     * @param activo
     */
    public void setActivo(String activo) {
        this.activo = activo;
    }

    /**
     * @param fields
     */
    public void setFields(String fields) {
        this.fields = fields;
    }

    /**
     * @param table
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * @param titlecolumns
     */
    public void setTitlecolumns(String titlecolumns) {
        this.titlecolumns = titlecolumns;
    }

    /**
     * @param titlesearch
     */
    public void setTitlesearch(String titlesearch) {
        this.titlesearch = titlesearch;
    }

    /**
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @param javascriptnew
     */
    public void setJavascriptnew(String javascriptnew) {
        this.javascriptnew = javascriptnew;
    }

    /**
     * @param showbuttonbar
     */
    public void setShowbuttonbar(String showbuttonbar) {
        this.showbuttonbar = showbuttonbar;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @param length
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @param inputmask
     */
    public void setInputmask(String inputmask) {
        this.inputmask = inputmask;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    // ==================================================
    // Metodos de Ciclo de Vida
    // ==================================================
    public int doEndTag() throws JspException {
        try {

            JspWriter out = pageContext.getOut();
            out.print(this.getSourceCode());

        } catch (Exception ex) {
            throw new JspException("Class ControlForm Metodo [doEndTag] " + ex.getMessage());
        }
        try {
            finalize();
        } catch (Throwable ex) {
            Util.logError(ex);
        }
        return SKIP_BODY;
    }

    public String getSourceCode() throws JspException {
        int i = 0;
        List listDat = new LinkedList();
        List sisDat = new LinkedList();
        String option = "", valueFound = "", sqlCmd = "", nameTable = "", nameShw = "";
        String filtroAux = "", fieldAux = "";
        String fltAux = "", fldAux = "", class_ = "";
        StringBuffer strctls = new StringBuffer();

        String[] arrTrg = {};

        try {

            // Generar contenidos de lista combos
            if (!filtro.equals("") && !typctl.equals("Search")) {

                if (filtro.toLowerCase().trim().startsWith("select")) {

                } else {
                    if (!filtro.substring(0, 6).toLowerCase().equals("select") && !filtro.startsWith("DATA:")) {
                        if (fields.trim().equals("")) {
                            filtro = "select nomelm,dspelm from smaelm where " + filtro
                                    + ((filtro.toLowerCase().indexOf("order by") != -1) ? " " : " order by nroelm");
                        } else {
                            filtro = "select " + fields + " from smaelm where " + filtro + ((filtro.toLowerCase().indexOf("order by") != -1) ? " " : " order by nroelm");
                        }
                    }
                }
                // Se ejecuta la consulta y se obtiene las lista con los resultados
                if (!filtro.startsWith("DATA:")) {

                    model.listGeneric(filtro);
                    listDat = model.getList();
                    // Se carga la lista con datos fijos
                } else {
                    filtro = filtro.substring(5);
                    arrTrg = filtro.split("#");
                    for (int j = 0; j < arrTrg.length; j++) {
                        sisDat = new LinkedList();
                        if (arrTrg[j].indexOf(",") != -1) {
                            sisDat.add(arrTrg[j].substring(0, arrTrg[j].indexOf(",")));
                            sisDat.add(arrTrg[j].substring(arrTrg[j].indexOf(",") + 1));
                        } else {
                            sisDat.add(arrTrg[j]);
                            sisDat.add(arrTrg[j]);
                        }
                        listDat.add(sisDat);
                    }
                }
            }

            if (typctl.equals("Check")) {
                typctl = "checkbox";
            }
            if (typctl.equals("Radio")) {
                typctl = "radio";
            }

            if (typctl.equals("Combo") || typctl.equals("Lista")) {

                if (typctl.equals("Lista")) {
                    option = "size='5'";
                }

                if (!javascriptnew.equals("")) {
                    javascriptnew = "onChange=\"javascript:" + javascriptnew + "\"";
                }

                strctls.append("<select name='" + name + "' " + option + " class='combo' id='" + name + "' title='" + title + "' " + activo + " " + this.javascriptnew + ">\n");
                String bg = " style = 'background-color: #FFFFFF' ";
                for (int j = 0; j < listDat.size(); j++) {
                    option = "";
                    bg = " style = 'background-color: #FFFFFF' ";
                    if (j % 2 == 0) {
                        bg = " style = 'background-color: #FFFFFF' ";
                    }
                    sisDat = (List) listDat.get(j);
                    if (sisDat.size() == 1) {
                        sisDat.add(sisDat.get(0));
                    }
                    if (sisDat.get(0).equals(value) || sisDat.get(1).equals(value)) {
                        option = "selected";
                    }
                    strctls.append("  <option value='" + sisDat.get(0) + "' " + bg + option + ">" + sisDat.get(1) + "</option>\n");
                }
                strctls.append("</select>");

            } else if (typctl.equals("radio") || typctl.equals("checkbox")) {

                if (!this.javascriptnew.equals("")) {
                    this.javascriptnew = "onClick=\"javascript:" + this.javascriptnew + "\"";
                } else {
                    this.javascriptnew = "onClick=\"javascript:updateValueCheckBox(this)\"";
                }

                for (int j = 0; j < listDat.size(); j++) {
                    option = "";
                    sisDat = (List) listDat.get(j);
                    if (sisDat.size() == 1) {
                        sisDat.add(sisDat.get(0));
                    }

                    if (sisDat.get(0).equals(value) || sisDat.get(1).equals(value)) {
                        option = "checked";
                    }

                    strctls.append("<input class='check' name='" + name + "' id='" + (name + j) + "' type='" + typctl + "' title='" + title + " : " + sisDat.get(1) + "' " + option + " " + activo + " value='"
                            + sisDat.get(0) + "' " + this.javascriptnew + ">" + sisDat.get(1) + "<br>\n");
                }

                if (listDat.isEmpty()) {
                    if (value.equals("1") || value.equals("1.0")) {
                        option = "checked";
                    }
                    strctls.append("<input class='check' name='" + this.name + "' id='" + this.name + "' type='" + this.typctl + "' title='" + this.title + "' " + option + " " + this.activo + " value='"
                            + this.value + "' " + this.javascriptnew + " alt='" + this.length + "'>" + title + "");
                }

            } else if (typctl.equals("Search")) {
                if (!filtro.equals("")) {
                    if (fields.equals("") && table.equals("")) {
                        fields = "nomelm,dspelm";
                        table = "smaelm";
                    }

                    if (titlecolumns.equals("")) {
                        titlecolumns = fields;
                    }

                    if (!value.equals("")) {
                        sqlCmd = "select " + fields + " from " + table;
                        if (!filtro.equals("")) {
                            filtroAux = filtro;
                            if (filtroAux.indexOf("&") != -1 && value.indexOf("&") != -1) {
                                arrTrg = value.split("&");
                                i = 0;
                                if (arrTrg.length > 0) {
                                    while (filtroAux.indexOf("&") != -1) {
                                        fieldAux = filtroAux.substring(filtroAux.indexOf("&") + 1);
                                        fieldAux = fieldAux.substring(0, fieldAux.indexOf("&"));
                                        filtroAux = filtroAux.replaceAll("&" + fieldAux + "&", arrTrg[i]);
                                        i++;
                                    }
                                }
                                value = value.substring(value.lastIndexOf("&") + 1);
                            }
                            sqlCmd += " where " + filtroAux;
                            // sqlCmd += " where " + filtro;*/
                        }

                        if (sqlCmd.lastIndexOf("order by") != -1) {
                            sqlCmd = sqlCmd.substring(0, sqlCmd.lastIndexOf("order by"));
                        }

                        if (sqlCmd.indexOf("where") != -1) {
                            sqlCmd += " and ";
                        } else {
                            sqlCmd += " where ";
                        }

                        if (fields.indexOf(",") != -1) {
                            filtroAux = fields.substring(0, fields.indexOf(","));
                            if (filtroAux.indexOf(" as ") != -1) {
                                filtroAux = fields.substring(0, fields.indexOf(" as "));
                            }
                            sqlCmd += filtroAux + " = '" + value + "'";
                            model.listGeneric(sqlCmd);
                            listDat = model.getList();
                            if (!listDat.isEmpty()) {
                                sisDat = (List) listDat.get(0);
                                valueFound = sisDat.get(1).toString();
                            }
                        } else {
                            valueFound = value.trim();
                        }
                    }

                    table = table.replace('\'', '~');
                    filtro = filtro.replace('=', '|');
                    filtro = filtro.replace('\'', '~');
                    filtro = filtro.replace('%', '*');
                    option = fields.replace(',', '#');
                    option = option.replace('(', ' ');
                    option = option.replace(')', ' ');
                    option = option.replace("distinct", " ");

                    arrTrg = option.split("#");
                    if (arrTrg.length == 1) {
                        nameShw = name + "Shw";
                        option = name + "#" + nameShw;

                    } else {
                        nameShw = name + "Shw";
                        option = name + "#" + nameShw;
                        for (int j = 0; j < arrTrg.length; j++) {
                            if (j > 1) {
                                option += "#" + arrTrg[j];
                            }
                        }
                    }

                    if (this.javascriptnew.trim().equals("")) {
                        this.javascriptnew = "return;";
                    }

                    strctls.append("<input readonly='true' name='" + nameShw + "' type='text' id='" + nameShw + "' class='text' title='" + title + "' size='" + this.length + "' value='" + valueFound
                            + "' onFocus=\"javascript:" + this.javascriptnew + "\">\n");
                    strctls.append("<input type='button' id = 'btn" + name + "' name='bton" + name
                            + "Show' value=' ' class='botonsmall' onmouseover = 'changeCursor(this)' onClick=\"javascript:showWindowFind('" + fields + "','" + table + "','" + filtro + "','" + titlecolumns + "','"
                            + option + "','" + titlesearch + "')\" " + activo + ">\n");

                    strctls.append("<input name='" + name + "' type='hidden' id='" + name + "' value='" + value + "'>");

                } else {
                    strctls.append("<label class='message'>No existe criterio de busqueda</label>");
                }

            } else if (typctl.equals("Label")) {

                valueFound = "&nbsp;";
                if (!filtro.equals("")) {
                    sqlCmd = filtro;
                    model.listGeneric(sqlCmd);
                    listDat = model.getList();
                    if (!listDat.isEmpty()) {
                        sisDat = (List) listDat.get(0);
                        valueFound = sisDat.get(0).toString();
                    }
                    strctls.append(valueFound);

                } else {
                    strctls.append(valueFound);
                }

            } else if (typctl.equals("Date")) {

                if (activo.equals("disabled")) {
                    option = "readonly";
                }
                value = Util.dateFormat(Util.getFecha(value), model.getFormatDateShow());

                strctls.append("<input name='" + name + "' type='text' id='" + name + "' class='text' title='" + title + "' size='10' value='" + value
                        + "' alt='Date' onKeyPress=\"return validFormat(this,event,'Date')\" " + option + ">\n");
                strctls.append("<input type='button' name='btonDate" + name + "' value=' ' class='botonsmall_' onmouseover = 'changeCursor(this)' onClick=\"javascript:showCal('Cal_" + name + "')\" " + activo
                        + "> ");

            } else if (typctl.equals("DateNew")) {

                if (activo.equals("disabled")) {
                    option = "readonly";
                }
                value = Util.dateFormat(Util.getFecha(value), model.getFormatDateShow());

                strctls.append("<input name='" + name + "' type='text' id='" + name + "' class='text' title='" + title + "' size='10' value='" + value
                        + "' alt='Date' onKeyPress=\"return validFormat(this,event,'Date')\" " + option + ">\n");
                option = model.getFormatDateShow();
                option = option.replace("DD", "dd");
                option = option.replace("MM", "mm");
                option = option.replace("YYYY", "yyyy");
                strctls.append("<a href='#' ><img src='" + this.url + "/vista/img/calendar.gif' border='0' width='16' height='15' alt='" + title + "' onClick=\"displayCalendar(formUpdate." + name + ",'"
                        + option + "',this)\"></a>");

            } else if (typctl.equals("Hour")) {

                if (activo.equals("disabled")) {
                    option = "readonly";
                }
                strctls.append("<input readonly='true' name='" + name + "' type='text' id='" + name + "' class='text' title='" + title + "' size='10' value='" + value + "' " + option + ">\n");
                strctls.append("<input type='button' name='btonHour" + name + "' value=' ' class='botonsmall' onClick=\"javascript:updateHour(0,'" + name + "')\" " + activo + ">");

            } else if (typctl.equals("Color")) {

                if (activo.equals("disabled")) {
                    option = "readonly";
                }
                strctls.append("<input readonly='true' name='" + name + "' type='text' id='" + name + "' style='background-color:" + Util.getColorHex(value) + "; color:" + Util.getColorHex(value)
                        + "' class='text' title='" + title + "' size='10' value='" + value + "' " + option + ">\n");
                strctls.append("<input type='button' name='btonColor" + name + "' value=' ' class='botonsmall' onClick=\"javascript:updateColor(0,'" + name + "')\" " + activo + ">\n");

            } else if (typctl.equals("Text")) {
                fltAux = "";
                option = "";
                class_ = "text";

                if (!filtro.equals("")) {
                    sqlCmd = filtro;
                    model.listGeneric(sqlCmd);
                    listDat = model.getList();
                    if (!listDat.isEmpty()) {
                        sisDat = (List) listDat.get(0);
                        value = sisDat.get(0).toString();
                    }
                }

                // "$ ###,###,###.#"
                if (!inputmask.equals("") && (format.equals("Int") || format.equals("Decimal"))) {
                    value = Util.doubleDecimalFormat(Double.parseDouble(value), inputmask);
                    if (inputmask.indexOf("$") != -1) {
                        fltAux = "onKeyUp=\"javascript:formatNumber( unformatNumber( this.value ),this,'$' )\"";
                    } else {
                        fltAux = "onKeyUp=\"javascript:formatNumber( unformatNumber( this.value ),this,'' )\"";
                    }
                }
                // yyyy-MM-dd hh:mm:ss
                if (!inputmask.equals("") && format.equals("Date")) {
                    value = Util.dateFormat(Util.getFecha(value), inputmask);
                }

                if (activo.equals("disabled")) {
                    option = "readonly";
                    class_ = "textdisabled";
                }
                if (!format.equals("")) {
                    format = "onKeyPress=\"return validFormat(this,event,'" + format + "')\"";
                }
                if (!javascriptnew.equals("")) {
                    javascriptnew = "onKeyUp=\"javascript:" + javascriptnew + "\"";
                }

                strctls.append("<input name='" + name + "' type='text' id='" + name + "' class='" + class_ + "' title='" + title + "' size='" + length + "' maxlength='" + length + "' value='" + value + "' alt='" + inputmask + "' " + option + " " + fltAux + " " + format + " " + javascriptnew + ">\n");

            } else if (typctl.equals("ButtonBar")) {

                String scracc = "disabled", insacc = "disabled", delacc = "disabled";
                String strAccess = "";

                nameTable = this.table.toLowerCase();

                // Se esta accediendo desde internet sin iniciar sesion
                if (!this.online.equals("")) {
                    sisDat = new LinkedList();
                    sisDat.add("0");
                    sisDat.add("1");
                    sisDat.add("0");
                    sisDat.add("0");
                    listDat.add(sisDat);
                } else {
                    if (this.activo.equals("")) {
                        sqlCmd = "select scracc,insacc,edtacc,delacc " + "from smaacc " + "where codcia = '" + this.model.getCodCia() + "' " + "and codprs = '" + this.model.getCodPrs() + "' " + "and nomdbf = '"
                                + nameTable + "'";
                        model.listGeneric(sqlCmd);
                        listDat = model.getList();
                    } else if (this.activo.trim().equals("NO_BUTTON_BAR")) {
                        showbuttonbar = "00000";
                    } else {
                        sisDat = new LinkedList();
                        sisDat.add("1");
                        sisDat.add("1");
                        sisDat.add("1");
                        sisDat.add("1");
                        listDat.add(sisDat);
                    }
                }

                if (!listDat.isEmpty()) {
                    sisDat = (List) listDat.get(0);
                    strAccess = sisDat.get(0).toString().trim() + sisDat.get(1).toString().trim() + sisDat.get(2).toString().trim() + sisDat.get(3).toString().trim();

                    // Estado Consultar [Activo]
                    if (sisDat.get(0).equals("1")) {
                        scracc = "";
                    }

                    if (this.state.equals("Insert")) {
                        // Estado Insertar [Activo]
                        if (sisDat.get(1).equals("1")) {
                            insacc = "";
                        }
                        // Estado Editar [Activo]
                        if (sisDat.get(2).equals("1")) {
                            scracc = "";
                        }
                    } else if (this.state.equals("Edit")) {
                        // Estado Editar [Activo]
                        if (sisDat.get(2).equals("1")) {
                            insacc = "";
                            scracc = "";
                        }
                        // Estado Eliminar [Activo]
                        if (sisDat.get(3).equals("1")) {
                            delacc = "";
                        }
                    }

                }

                strctls.append("<div class='demo'>");

                if (this.showbuttonbar.charAt(0) == '1') {
                    if (this.javascriptnew.equals("")) {
                        javascriptnew = "clearFields('formUpdate',true)";
                    }
                    strctls.append("<input type='button' name='btnNew' id='btnNew' value='Nuevo' class='boton' onClick=\"javascript:" + this.javascriptnew + "\">\n");
                }
                if (this.showbuttonbar.charAt(1) == '1') {
                    strctls.append("<input type='submit' name='btnSave' value='Guardar' " + insacc + " class='boton'>\n");
                }
                if (this.showbuttonbar.charAt(2) == '1') {
                    strctls.append("<input type='button' name='btnDelete' value='Eliminar' " + delacc + " class='boton' onClick=\"javascript:deleteRecord('C/Adm/" + name + "','formUpdate')\">\n");
                }
                if (this.showbuttonbar.charAt(3) == '1') {
                    strctls.append("<input type='button' name='btnSearch' value='Buscar' class='boton' " + scracc + " onClick=\"javascript:reDirect('C/Build/List?nommod=" + nameTable + "&url=C/Adm/" + name
                            + "')\">\n");
                }
                if (this.showbuttonbar.charAt(3) == '2') {
                    strctls.append("<input type='button' name='btnSearch' value='Buscar' class='boton' " + scracc + " onClick=\"javascript:sendForm('formUpdate','C/Build/List?nommod=" + nameTable
                            + "&url=C/Adm/" + name + "')\">\n");
                }
                /*
                 * strctls += "<input type='button' name='Imprimir' value='Imprimir'
                 * class='boton'
                 * onClick=\"javascript:showWindowReport('?nomrpt=rpt"+nameB+"')\">\n";
                 */
                if (this.showbuttonbar.charAt(4) == '1') {
                    strctls.append("<input type='button' name='btnRestore' value='Deshacer' class='boton' onClick=\"javascript:formUpdate.reset()\">\n");
                }
                strctls.append("<input type='hidden' name='varAccess' id='varAccess' value='" + strAccess + "'>");
                strctls.append("</div>");

            } else if (typctl.equals("Button") || typctl.equals("Submit") || typctl.equals("Reset")) {

                String stracc = "disabled";
                nameTable = this.table.toLowerCase();
                if (this.activo.equals("")) {
                    sqlCmd = "select scracc,insacc,edtacc,delacc " + "from smaacc " + "where codcia = '" + this.model.getCodCia() + "' " + "and nroprs = '" + this.model.getNroPrs() + "' " + "and nomdbf = '"
                            + nameTable + "'";
                    model.listGeneric(sqlCmd);
                    listDat = model.getList();
                } else {
                    sisDat = new LinkedList();
                    sisDat.add("1");
                    sisDat.add("1");
                    sisDat.add("1");
                    sisDat.add("1");
                    listDat.add(sisDat);
                }

                if (!listDat.isEmpty()) {
                    sisDat = (List) listDat.get(0);

                    // Estado Consultar [Activo]
                    if (this.state.equals("Insert")) {
                        // Estado Insertar [Activo]

                        if (sisDat.get(1).equals("1")) {
                            stracc = "";
                        }
                    } else if (this.state.equals("Edit")) {
                        // Estado Editar [Activo]
                        if (sisDat.get(2).equals("1")) {
                            stracc = "";
                        }
                    } else if (this.state.equals("Delete")) {
                        // Estado Eliminar [Activo]
                        if (sisDat.get(3).equals("1")) {
                            stracc = "";
                        }
                    }
                }

                if (!this.javascriptnew.equals("")) {
                    this.javascriptnew = "onClick=\"javascript:" + this.javascriptnew + "\"";
                }

                strctls.append("<input type='" + this.typctl + "' name='" + this.name + "' value='" + this.value + "' " + stracc + "  " + this.javascriptnew + " title='" + this.title + "'>");

                /*}else if (typctl.equals("Photo")){

                 if (activo.equals("1")){
                 option = "javascript:showWindow('winPhoto',-1,-1,500,280,'no','" + this.url + "/vista/formUploadFile.jsp?codpho=" + value + "&name_i=" + name + "')";

                 strctls.append("<a href=\"" + option + "\" onMouseOver='window.status=\"Click aqui!, Actualizar Foto\";return true' title='Click aqui!, Actualizar Foto'><img id='" + name
                 + "' width='98' height='120' border='0' src='" + this.url + "/utility/photos/" + value + ".jpg'></a>");
                 }else{
                 strctls.append("<img id='" + name + "' width='98' height='120' border='0' src='" + this.url + "/utility/photos/" + value + ".jpg'>");
                 }*/
            } else if (typctl.equals("Photo")) {
                if (activo.equals("1")) {
                    option = "javascript:showWindow('winPhoto',-1,-1,500,280,'no','" + this.url + "/vista/formUploadFile.jsp?codpho=" + value + "&name_i=" + name + "')";
                } else {
                    option = "#";
                }
                strctls.append("<a id= 'href_" + name + "'     href=\"" + option + "\" onMouseOver='window.status=\"Click aqui!, Actualizar Foto\";return true' title='Click aqui!, Actualizar Foto'><img id='" + name
                        + "' width='98' height='120' border='0' src='" + this.url + "/utility/photos/" + value + ".jpg'   onerror=\"this.src='" + this.url + "/vista/img/notFoundImg.jpg'\"></a>");
            }

        } catch (Exception ex) {
            throw new JspException("Class ControlForm Metodo [getSourceCode] Objeto [" + this.name + "] Descrip. [" + ex.getMessage() + "]");
        }
        return strctls.toString();
    }

}// Fin Class
