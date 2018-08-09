/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taglib.form;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import mvc.model.ModelSma;
import mvc.model.Util;
import mvc.model.WebModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 *
 * @author Carlos Pinto
 */
public class inputForm extends BodyTagSupport {

    private String name;
    private String type;
    private String style;
    private String clase;
    private String title;
    private String filter;
    private String attribute;
    private String valid;
    private String size;
    private String maxlength;
    private String table;
    private String cols;
    private String rows;
    private String required;
    private String disabled;
    private String readonly;
    private Object value;
    private String change;
    private String onkeypress;
    private String onblur;
    private String onkeydown;
    private String onkeyup;
    private String onfocus;
    private String onclick;
    private String columns;
    private String money;
    private String date;
    private String dateopt;
    private String time;
    private String timeopt;
    private String formats;
    private String onenterpress;
    private String onvalidation;
    private String label;
    private String labelClass;
    private String orientation;
    private String editor;
    private String editoropt;

    private ModelSma model;
    private myEnum.Input types;

    public inputForm() {

        disabled = "false";
        required = "false";
        name = null;
        type = null;
        style = null;
        clase = null;
        attribute = null;
        filter = null;
        title = null;
        valid = null;
        model = null;
        size = null;
        maxlength = "100";
        table = null;
        cols = "60";
        rows = "6";
        model = new WebModel();
        readonly = "false";
        change = null;
        value = null;
        onkeypress = null;
        onblur = null;
        onkeydown = null;
        onkeyup = null;
        onfocus = null;
        onclick = null;
        money = null;
        columns = null;
        date = null;
        dateopt = null;
        time = null;
        timeopt = null;
        formats = null;
        onenterpress = null;
        onvalidation = "true";
        label = null;
        labelClass = null;
        orientation = "vertical";
        editor = "false";
        editoropt = null;

    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
        this.type = "select";
    }

    public String getCols() {
        return cols;
    }

    public void setCols(String cols) {
        this.cols = cols;
        this.type = "textarea";
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getReadonly() {
        return readonly;
    }

    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    public ModelSma getModel() {
        return model;
    }

    public void setModel(ModelSma model) {
        this.model = model;
    }

    public myEnum.Input getTypes() {
        return types;
    }

    public void setTypes(myEnum.Input types) {
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String isRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String isDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getOnkeypress() {
        return onkeypress;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public String getOnblur() {
        return onblur;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public String getOnkeydown() {
        return onkeydown;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public String getOnkeyup() {
        return onkeyup;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public String getOnfocus() {
        return onfocus;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
        this.type = "select";
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateopt() {
        return dateopt;
    }

    public void setDateopt(String dateopt) {
        this.dateopt = dateopt;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeopt() {
        return timeopt;
    }

    public void setTimeopt(String timeopt) {
        this.timeopt = timeopt;
    }

    public String getFormats() {
        return formats;
    }

    public void setFormats(String formats) {
        this.formats = formats;
    }

    public String getOnenterpress() {
        return onenterpress;
    }

    public void setOnenterpress(String onenterpressed) {
        this.onenterpress = onenterpressed;
    }

    public String getOnvalidation() {
        return onvalidation;
    }

    public void setOnvalidation(String onvalidation) {
        this.onvalidation = onvalidation;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelClass() {
        return labelClass;
    }

    public void setLabelClass(String labelClass) {
        this.labelClass = labelClass;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getEditoropt() {
        return editoropt;
    }

    public void setEditoropt(String editoropt) {
        this.editoropt = editoropt;
    }

    @Override
    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            out.print(this.getHtml());
        } catch (Exception ex) {
            Util.logError(ex);
        }
//        return super.doEndTag(); //To change body of generated methods, choose Tools | Templates.
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        BodyContent body = getBodyContent();
        try {
            value = body.getString().trim().isEmpty() ? "" : body.getString();
            type = "textarea";
        } catch (Exception e) {
            value = "";
        }

        return SKIP_BODY;

    }

    public String getHtml() throws Exception {

        String strExecute = "",
                html = "";

        if (type != null && type.equals("check")) {
            type = "checkbox";
        }

        if (type != null && type.toLowerCase().equals(types.TEXT.valor())) {
            html = getHtmlInputText("text", valid);
        } else if (type != null && type.toLowerCase().equals(types.PASSWORD.valor())) {
            html = getHtmlInputText("password", "password");
        } else if (type != null && type.toLowerCase().equals(types.HIDDEN.valor())) {
            html = getHtmlInputText("hidden", null);
        } else if (type != null && type.toLowerCase().equals(types.TEXTAREA.valor())) {
            html = getHtmlInputTextarea(valid);
        } else if (type != null && (type.toLowerCase().equals(types.RADIO.valor()) || type.toLowerCase().equals(types.CHECKBOX.valor()))) {
            html = getHtmlInputCheckRadio(type);
        } else if (type == null || table != null && columns == null || type.equals("select")) {
            html = getHtmlSelect();
        } else if (type != null && type.toLowerCase().equals("file")) {
            html = getHtmlInputFile();
        }

        if (type != null && (!type.toLowerCase().equals("radio") || !type.toLowerCase().equals("checkbox"))) {
            strExecute = "<script id=\"scrp_" + name + "\">validAllAtributtes('scrp_" + name + "' , '" + name + "');</script>";
        }

        return html + strExecute;
    }

    private String getHtmlInputText(String type, String validate) {
        String strStyles = "";
        String strAttributes = "";
        String strclases = "";
        String strValid = "";
        String strExecute = "";
        StringBuilder HTML = new StringBuilder();

        if (type.toLowerCase().equals("password")) {
            valid = "password";
        }

        if (valid != null && validate != null) {

            strValid = new myEnum().optionValid(validate.toLowerCase());
        }

        HTML.append("<input type=\"")
                .append(type)
                .append("\" id=\"")
                .append(name)
                .append("\" name=\"")
                .append(name)
                .append("\" ");

        if (style != null) {
            HTML.append(" style=\"")
                    .append(style)
                    .append("\" ");
        }

        if (attribute != null) {
            HTML.append(attribute);
        }

        if (clase != null) {
            HTML.append(" class=\"")
                    .append(clase)
                    .append("\"");
        }

        if (maxlength != null) {
            HTML.append(" maxlength=\"")
                    .append(maxlength)
                    .append("\"");
        }
        if (title != null) {
            HTML.append(" title=\"")
                    .append(title)
                    .append("\"");
        }
        if (size != null) {
            HTML.append(" size=\"").
                    append(size)
                    .append("\"");
        }
        if (disabled != null && !disabled.equals("false")) {
            HTML.append(" disabled ");
        }
        if (readonly != null && !readonly.equals("false")) {
            HTML.append(" readonly ");
        }
        if (required != null && !required.equals("false")) {
            HTML.append(" required ");
        }
        if (value != null) {
            HTML.append(" value=\"")
                    .append(value)
                    .append("\"");
        }
        if (money != null) {
            HTML.append(" data-money=\"")
                    .append(money)
                    .append("\"");
        }
        if (date != null) {
            HTML.append(" data-date=\"")
                    .append(date)
                    .append("\"");
        }
        if (dateopt != null) {
            HTML.append(" data-opt=\"")
                    .append(dateopt)
                    .append("\"");
        }
        if (time != null) {
            HTML.append(" data-time=\"")
                    .append(time)
                    .append("\"");
        }
        if (timeopt != null) {
            HTML.append(" data-opt=\"")
                    .append(timeopt)
                    .append("\"");
        }

        if (onkeydown != null) {
            HTML.append("onkeydown=\"")
                    .append(onkeydown)
                    .append("\"");
        }
        if (onkeypress != null) {
            if (valid == null) {
                HTML.append("onkeypress=\"")
                        .append(onkeypress)
                        .append("\"");
            } else {
                strValid = strValid.substring(0, strValid.length() - 1) + "; " + onkeypress + "\"";
            }
        }
        if (onkeyup != null) {
            HTML.append("onkeyup=\"")
                    .append(onkeyup)
                    .append("\"");
        }
        if (onclick != null) {
            HTML.append("onclick=\"")
                    .append(onclick)
                    .append("\"");
        }
        if (onblur != null) {
            HTML.append("onblur=\"")
                    .append(onblur)
                    .append("\"");
        }
        if (onfocus != null) {
            HTML.append("onfocus=\"")
                    .append(onfocus)
                    .append("\"");
        }
        if (onenterpress != null) {
            HTML.append("data-enter-pressed=\"")
                    .append(onenterpress)
                    .append("\"");
        }

        HTML.append(strValid);

        HTML.append("data-tovalidate=\"")
                .append(onvalidation)
                .append("\"");

        HTML.append(" />");

        return HTML.toString();

    }

    private String getHtmlInputTextarea(String validate) {

        String strStyles = "";
        String strAttributes = "";
        String strclases = "";
        String strValid = "";
        String strExecute = "";
        StringBuilder HTML = new StringBuilder();

        if (valid != null && validate != null) {

            strValid = new myEnum().optionValid(validate.toLowerCase());
        }

        HTML.append("<textarea ")
                .append(" id=\"")
                .append(name)
                .append("\" name=\"")
                .append(name)
                .append("\" ");

        if (style != null) {
            HTML.append(" style=\"")
                    .append(style)
                    .append("\" ");
        }

        if (attribute != null) {
            HTML.append(attribute);
        }

        if (clase != null) {
            HTML.append(" class=\"")
                    .append(clase)
                    .append("\"");
        }

        if (maxlength != null) {
            HTML.append(" maxlength=\"")
                    .append(maxlength)
                    .append("\"");
        }
        if (title != null) {
            HTML.append(" title=\"")
                    .append(title)
                    .append("\"");
        }
        if (size != null) {
            HTML.append(" size=\"")
                    .append(size)
                    .append("\"");
        }
        if (disabled != null && !disabled.equals("false")) {
            HTML.append(" disabled ");
        }
        if (readonly != null && !readonly.equals("false")) {
            HTML.append(" readonly ");
        }
        if (required != null && !required.equals("false")) {
            HTML.append(" required ");
        }
        if (value != null) {
            HTML.append(" value=\"")
                    .append(value)
                    .append("\"");
        }
        if (rows != null) {
            HTML.append(" rows=\"")
                    .append(rows)
                    .append("\"");
        }
        if (cols != null) {
            HTML.append(" cols=\"")
                    .append(cols)
                    .append("\"");
        }
        if (value != null) {
            HTML.append(" value=\"")
                    .append(value)
                    .append("\"");
        }
        if (onkeydown != null) {
            HTML.append("onkeydown=\"")
                    .append(onkeydown)
                    .append("\"");
        }
        if (onkeypress != null) {
            HTML.append("onkeypress=\"")
                    .append(onkeypress)
                    .append("\"");
        }
        if (onkeyup != null) {
            HTML.append("onkeyup=\"")
                    .append(onkeyup)
                    .append("\"");
        }
        if (onclick != null) {
            HTML.append("onclick=\"")
                    .append(onclick)
                    .append("\"");
        }
        if (onblur != null) {
            HTML.append("onblur=\"")
                    .append(onblur)
                    .append("\"");
        }
        if (onfocus != null) {
            HTML.append("onfocus=\"")
                    .append(onfocus)
                    .append("\"");
        }
        if (editor != null) {
            HTML.append(" data-editor=\"")
                    .append(editor)
                    .append("\"");
        }
        if (editoropt != null) {
            HTML.append(" data-opt=\"")
                    .append(editoropt)
                    .append("\"");
        }
        HTML.append("data-tovalidate=\"")
                .append(onvalidation)
                .append("\"");

        HTML.append(strValid);

        HTML.append(" >").append(value == null ? "" : value).append("</textarea>");

        return HTML.toString();
    }

    public String getHtmlInputCheckRadio(String type) {

        StringBuilder HTML = new StringBuilder();

        if (filter != null && !filter.toLowerCase().startsWith("select")) {
            JSONArray json;
            filter = filter.replaceAll("#", "\"");
            try {
                json = (JSONArray) JSONValue.parse(filter);
            } catch (Exception e) {
                json = new JSONArray();
            }

            String aux[];
            int i = 0;
            for (Object a : json) {
                aux = a.toString().split("-");
                if (aux.length == 0) {
                    continue;
                }

                if (aux.length == 1) {
                    aux = new String[]{aux[0], aux[0]};
                }

                HTML.append(makeRadioCheck(type, aux, name, (name + i)));
                i++;
            }
        } else if (filter != null && filter.toLowerCase().startsWith("select")) {
            List data;
            try {
                model.list(filter, null);
                data = model.getList();
            } catch (Exception e) {
                data = new LinkedList();
            }
            String aux[];
            String first, second;
            int i = 0;
            for (Object a : data) {
                if (model.getColumnNames().size() == 1) {
                    first = Util.validStr((HashMap) a, (String) model.getColumnNames().get(0));
                    aux = new String[]{first, first};
                } else {
                    first = Util.validStr((HashMap) a, (String) model.getColumnNames().get(0));
                    second = Util.validStr((HashMap) a, (String) model.getColumnNames().get(1));
                    aux = new String[]{first, second};
                }

                HTML.append(makeRadioCheck(type, aux, name, (name + i)));
                i++;
            }

        } else {
            HTML.append(makeRadioCheck(type, new String[]{""}, name, name));
        }

        return HTML.toString();
    }

    private String makeRadioCheck(String type, String[] values, String name, String id) {
        StringBuilder HTML = new StringBuilder();
        HTML.append("<label style=\"margin:2px 5px;vertical-align:middle;padding-left:20px;cursor:pointer\" class=\"").append(Util.validStr(labelClass, "")).append("\" >")
                .append("<input ")
                .append(" type=\"")
                .append((type.equals("check") ? "checkbox" : type))
                .append("\" ")
                .append(" id=\"")
                .append(id)
                .append("\" name=\"")
                .append(name)
                .append("\" style=\"margin-left:-20px\"");

        if (style != null) {
            HTML.append(" style=\"").append(style).append("\" ");
        }

        if (attribute != null) {
            HTML.append(attribute);
        }

        if (clase != null) {
            HTML.append(" class=\"").append(clase).append("\"");
        }

        if (title != null) {
            HTML.append(" title=\"").append(title).append("\"");
        }

        if (disabled != null && !disabled.equals("false")) {
            HTML.append(" disabled ");
        }

        if (change != null) {
            HTML.append(" onchange=\"").append(change).append("\"");
        }

        if (values != null) {
            HTML.append(" data-val=\"")
                    .append(values[0])
                    .append("\"")
                    .append(" value=\"")
                    .append(values[0])
                    .append("\"");
            if (values[0].equals(value)) {
                HTML.append(" checked ");
            }

        }
        if (onkeydown != null) {
            HTML.append("onkeydown=\"")
                    .append(onkeydown)
                    .append("\"");
        }
        if (onkeypress != null) {
            HTML.append("onkeypress=\"")
                    .append(onkeypress)
                    .append("\"");
        }
        if (onkeyup != null) {
            HTML.append("onkeyup=\"")
                    .append(onkeyup)
                    .append("\"");
        }
        if (onclick != null) {
            HTML.append("onclick=\"")
                    .append(onclick)
                    .append("\"");
        }
        if (onblur != null) {
            HTML.append("onblur=\"")
                    .append(onblur)
                    .append("\"");
        }
        if (onfocus != null) {
            HTML.append("onfocus=\"")
                    .append(onfocus)
                    .append("\"");
        }

        HTML.append("data-tovalidate=\"")
                .append(onvalidation)
                .append("\"");

        HTML.append("/>");

        if (values != null) {

            HTML.append(values.length > 1 ? values[1] : values[0])
                    .append(" ");

        }
        if ((values == null || values[0] == null || values[0].trim().isEmpty()) && label != null) {
            HTML.append(label);
        }

        HTML.append("</label>");
        if (orientation.equals("vertical")) {
            HTML.append("<br/>");
        }
        return HTML.toString();

    }

    private String getHtmlSelect() {

        String strStyles = "";
        String strAttributes = "";
        String strclases = "";
        String strValid = "";
        String strExecute = "";
        StringBuilder HTML = new StringBuilder();

        HTML.append("<select ")
                .append(" id=\"")
                .append(name)
                .append("\" name=\"")
                .append(name)
                .append("\" ");

        if (style != null) {
            HTML.append(" style=\"").append(style).append("\" ");
        }

        if (attribute != null) {
            HTML.append(attribute);
        }

        if (clase != null) {
            HTML.append(" class=\"").append(clase).append("\"");
        }

        if (title != null) {
            HTML.append(" title=\"").append(title).append("\"");
        }

        if (!disabled.equals("false")) {
            HTML.append(" disabled ");
        }

        if (change != null) {
            HTML.append(" onchange=\"").append(change).append("\"");
        }
        if (value != null) {
            HTML.append(" value=\"")
                    .append(value)
                    .append("\"");
        }

        if (onclick != null) {
            HTML.append("onclick=\"")
                    .append(onclick)
                    .append("\"");
        }
        if (onblur != null) {
            HTML.append("onblur=\"")
                    .append(onblur)
                    .append("\"");
        }
        if (onfocus != null) {
            HTML.append("onfocus=\"")
                    .append(onfocus)
                    .append("\"");
        }

        HTML.append(" data-tovalidate=\"")
                .append(onvalidation)
                .append("\"");

        HTML.append(" >");
        filter = filter.trim();
        if (filter != null && !filter.toLowerCase().startsWith("select") && table == null) {
            JSONArray json;
            filter = filter.replaceAll("#", "\"");
            try {
                json = (JSONArray) JSONValue.parse(filter);
            } catch (Exception e) {
                json = new JSONArray();
            }

            String aux[];
            for (Object a : json) {
                aux = a.toString().split("-");
                if (aux.length == 0) {
                    continue;
                }

                if (aux.length == 1) {
                    aux = new String[]{aux[0], aux[0]};
                }

                HTML.append("<option value=\"")
                        .append(aux[0])
                        .append("\"");
                if (aux[0].equals(value)) {
                    HTML.append(" selected ");
                }

                HTML.append("> ")
                        .append(aux[1])
                        .append("</option>");

            }

        } else if (filter != null && filter.toLowerCase().startsWith("select")) {

            HTML.append(makeSelectOptions(filter, (value == null ? "" : value).toString()));

        } else if (columns != null && table != null) {
            String sqlCmd = "select " + columns + " from " + table;
            if (filter != null && !filter.trim().isEmpty()) {
                sqlCmd += " where " + filter;
            }

            HTML.append(makeSelectOptions(sqlCmd, (value == null ? "" : value).toString()));

        }

        HTML.append("</select>");

        return HTML.toString();

    }

    private String makeSelectOptions(String select, String value) {

        StringBuilder HTML = new StringBuilder();
        List data;
        try {
            model.listGenericHash(select);
            data = model.getList();
        } catch (Exception e) {
            Util.logError(e);
            data = new LinkedList();
        }

        for (Object a : data) {
            HTML.append("<option ");
            Hashtable aux = (Hashtable) a;
            String key = model.getColumnNames().get(0);
            HTML.append(" value=\"")
                    .append(aux.get(key))
                    .append("\" ");
            if (aux.get(key).equals(value)) {
                HTML.append(" selected ");
            }
            HTML.append(" >");
            HTML.append(aux.get(model.getColumnNames().size() > 1 ? model.getColumnNames().get(1) : model.getColumnNames().get(0)));
            HTML.append("</option>");
        }

        return HTML.toString();
    }

    private String getHtmlInputFile() {

        String strStyles = "";
        String strAttributes = "";
        String strclases = "";
        String strValid = "";
        String strExecute = "";
        StringBuilder HTML = new StringBuilder();

        HTML.append("<input type=\"")
                .append(type)
                .append("\" id=\"")
                .append(name)
                .append("\" name=\"")
                .append(name)
                .append("\" ");

        if (style != null) {
            HTML.append(" style=\"")
                    .append(style)
                    .append("\" ");
        }

        if (attribute != null) {
            HTML.append(attribute);
        }

        if (clase != null) {
            HTML.append(" class=\"")
                    .append(clase)
                    .append("\"");
        }

        if (title != null) {
            HTML.append(" title=\"")
                    .append(title)
                    .append("\"");
        }
        if (size != null) {
            HTML.append(" size=\"").
                    append(size)
                    .append("\"");
        }
        if (disabled != null && !disabled.equals("false")) {
            HTML.append(" disabled ");
        }
        if (required != null && !required.equals("false")) {
            HTML.append(" required ");
        }
        if (onkeydown != null) {
            HTML.append("onkeydown=\"")
                    .append(onkeydown)
                    .append("\"");
        }
        if (onkeypress != null) {
            if (valid == null) {
                HTML.append("onkeypress=\"")
                        .append(onkeypress)
                        .append("\"");
            } else {
                strValid = strValid.substring(0, strValid.length() - 1) + "; " + onkeypress + "\"";
            }
        }
        if (onkeyup != null) {
            HTML.append("onkeyup=\"")
                    .append(onkeyup)
                    .append("\"");
        }
        if (onclick != null) {
            HTML.append("onclick=\"")
                    .append(onclick)
                    .append("\"");
        }
        if (onblur != null) {
            HTML.append("onblur=\"")
                    .append(onblur)
                    .append("\"");
        }
        if (onfocus != null) {
            HTML.append("onfocus=\"")
                    .append(onfocus)
                    .append("\"");
        }
        formats = (formats == null ? "['pdf']" : formats);

        if (formats != null) {
            HTML.append("data-formats=\"")
                    .append(formats)
                    .append("\"");
        }
        HTML.append("data-tovalidate=\"")
                .append(onvalidation)
                .append("\"");

        HTML.append("data-file=\"true\"");
        HTML.append(" />");

        return HTML.toString();

    }

}

class myEnum {

    public enum Input {

        TEXT {
            @Override
            public String valor() {
                return "text";
            }
        },
        PASSWORD {
            @Override
            public String valor() {
                return "password";
            }
        },
        HIDDEN {
            @Override
            public String valor() {
                return "hidden";
            }
        },
        TEXTAREA {
            @Override
            public String valor() {
                return "textarea";
            }
        },
        SUBMIT {
            @Override
            public String valor() {
                return "submit";
            }
        },
        RESET {
            @Override
            public String valor() {
                return "reset";
            }
        },
        RADIO {
            @Override
            public String valor() {
                return "radio";
            }
        },
        CHECKBOX {
            @Override
            public String valor() {
                return "checkbox";
            }
        },
        SELECT {
            @Override
            public String valor() {
                return "select";
            }
        },
        FILE {
            @Override
            public String valor() {
                return "file";
            }
        };

        public abstract String valor();
    }

    public String optionValid(String tipo) {

        Map option = new HashMap();
        option.put("letras", " data-valid=\"letras\" onkeypress=\"return validSQLILetters(event)\"");
        option.put("caracteres", " data-valid=\"caracteres\" onkeypress=\"return validSQLICharacters(event)\"");
        option.put("numeros", " data-valid=\"numeros\" onkeypress=\"return validSQLINumber(event)\"");
        option.put("letranumero", " data-valid=\"letranumero\" onkeypress=\"return validSQLILettersAndNumber(event)\"");
        option.put("mail", " data-valid=\"mail\" onkeypress=\"return validSQLIEmail(event)\"");
        option.put("direccion", " data-valid=\"direccion\" onkeypress=\"return validSQLIAddress(event)\"");
        option.put("web", " data-valid=\"web\" onkeypress=\"return validSQLIWeb(event)\"");
        option.put("password", " data-valid=\"password\" onkeypress=\"return validSQLIPassword(event)\"");
        option.put("decimal", " data-valid=\"numeros\" data-decimal=\"true\" onkeypress=\"return validSQLIDecimal(event)\"");
        option.put("dni", " data-valid=\"dni\" onkeypress=\"return validSQLIDni(event)\"");

        if (!option.containsKey(tipo)) {
            return "";
        }

        return option.get(tipo).toString();
    }

}
