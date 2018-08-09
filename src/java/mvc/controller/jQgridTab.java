/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller;

import mvc.model.Util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;
import mvc.model.Model;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Carlos Pinto 08/04/2015
 * @description jQgridTab Clase que se encarga de la construccion de jqGrid
 * desde un JTLS
 *
 */
public class jQgridTab extends TagSupport {

    private String selector;
    private String paginador;
    private String statement;
    private String attributes;
    private List dataList;
    private Model model;
    private String[] columns;
    private String[] titles;
    private String[] frozenColumn;
    private String[] summary;
    private Boolean[] groupColumnShow;
    private String groupFields;
    private String groupOrder;
    private String groupText;
    private String formatMoney;
    private boolean groupCollapse;
    private boolean groupColunmShow;
    private boolean showSummaryOnHide;
    private boolean frozenColumns;
    private boolean isSummary;
    private boolean filterToolbar;
    private boolean sortableRows;
    private boolean generalFilter;
    private int[] widths;
    private int[] hiddens;
    private int[] keys;
    private int[] formatDate;
    private LinkedHashMap formatter;
    private LinkedHashMap columnOptions;
    private LinkedHashMap columnAlign;
    private LinkedHashMap options;
    private LinkedHashMap summaryColumn;
    private LinkedHashMap frozenOptions;
    private LinkedHashMap rowsPanOptions;

    public jQgridTab() {
        selector = null;
        titles = null;
        dataList = null;
        paginador = null;
        statement = null;
        model = null;
        frozenColumn = null;
        columns = null;
        widths = null;
        hiddens = null;
        keys = null;
        formatDate = null;
        formatMoney = null;
        formatter = null;
        options = null;
        attributes = null;
        columnAlign = null;
        groupColumnShow = null;
        groupFields = null;
        groupOrder = null;
        groupCollapse = true;
        groupText = null;
        groupColunmShow = false;
        filterToolbar = false;
        showSummaryOnHide = false;
        generalFilter = false;
        sortableRows = false;
        isSummary = false;
        frozenColumns = false;
        summaryColumn = null;
        summary = null;
        frozenOptions = null;
        columnOptions = null;
        rowsPanOptions = null;

    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getPaginador() {
        return paginador;
    }

    public void setPaginador(String paginador) {
        this.paginador = paginador;
    }

    public List getDataList() {
        return dataList;
    }

    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }

    public int[] getHiddens() {
        return hiddens;
    }

    public void setHiddens(int[] hiddens) {
        this.hiddens = hiddens;
    }

    public int[] getKeys() {
        return keys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public LinkedHashMap getFormatter() {
        return formatter;
    }

    public void setFormatter(LinkedHashMap formatter) {
        this.formatter = formatter;
    }

    public LinkedHashMap getOptions() {
        return options;
    }

    public void setOptions(LinkedHashMap options) {
        this.options = options;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public LinkedHashMap getColumnAlign() {
        return columnAlign;
    }

    public void setColumnAlign(LinkedHashMap columnAlign) {
        this.columnAlign = columnAlign;
    }

    public String getGroupField() {
        return groupFields;
    }

    public void setGroupField(String groupField) {
        this.groupFields = groupField;
    }

    public String getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(String groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getGroupText() {
        return groupText;
    }

    public void setGroupText(String groupText) {
        this.groupText = groupText;
    }

    public boolean isGroupCollapse() {
        return groupCollapse;
    }

    public void setGroupCollapse(boolean groupCollapse) {
        this.groupCollapse = groupCollapse;
    }

    public String getGroupFields() {
        return groupFields;
    }

    public void setGroupFields(String groupFields) {
        this.groupFields = groupFields;
    }

    public boolean isGroupColunmShow() {
        return groupColunmShow;
    }

    public void setGroupColunmShow(boolean groupColunmShow) {
        this.groupColunmShow = groupColunmShow;
    }

    public boolean isShowSummaryOnHide() {
        return showSummaryOnHide;
    }

    public void setShowSummaryOnHide(boolean showSummaryOnHide) {
        this.showSummaryOnHide = showSummaryOnHide;

    }

    public int[] getFormatDate() {
        return formatDate;
    }

    public void setFormatDate(int[] formatDate) {
        this.formatDate = formatDate;
    }

    public String getFormatMoney() {
        return formatMoney;
    }

    public void setFormatMoney(String formatMoney) {
        this.formatMoney = formatMoney;
    }

    public LinkedHashMap getSummaryColumn() {
        return summaryColumn;
    }

    public void setSummaryColumn(LinkedHashMap summaryColumn) {
        this.summaryColumn = summaryColumn;
        this.showSummaryOnHide = true;
    }

    public boolean isFilterToolbar() {
        return filterToolbar;
    }

    public void setFilterToolbar(boolean filterToolbar) {
        this.filterToolbar = filterToolbar;
    }

    public boolean isFrozenColumns() {
        return frozenColumns;
    }

    public void setFrozenColumns(boolean frozenColumns) {
        this.frozenColumns = frozenColumns;
    }

    public LinkedHashMap getFrozenOptions() {
        return frozenOptions;
    }

    public void setFrozenOptions(LinkedHashMap frozenOptions) {
        this.frozenOptions = frozenOptions;
    }

    public boolean isSortableRows() {
        return sortableRows;
    }

    public void setSortableRows(boolean sortableRows) {
        this.sortableRows = sortableRows;
    }

    public boolean isIsSummary() {
        return isSummary;
    }

    public void setIsSummary(boolean isSummary) {
        this.isSummary = isSummary;
    }

    public boolean isGeneralFilter() {
        return generalFilter;
    }

    public void setGeneralFilter(boolean generalFilter) {
        this.generalFilter = generalFilter;
    }

    public Boolean[] getGroupColumnShow() {
        return groupColumnShow;
    }

    public void setGroupColumnShow(Boolean[] groupColumnShow) {
        this.groupColumnShow = groupColumnShow;
    }

    public String[] getFrozenColumn() {
        return frozenColumn;
    }

    public void setFrozenColumn(String[] frozenColumn) {
        this.frozenColumn = frozenColumn;
    }

    public String[] getSummary() {
        return summary;
    }

    public void setSummary(String[] summary) {
        this.summary = summary;
    }

    public LinkedHashMap getColumnOptions() {
        return columnOptions;
    }

    public void setColumnOptions(LinkedHashMap columnOptions) {
        this.columnOptions = columnOptions;
    }

    public LinkedHashMap getrowsPanOptions() {
        return rowsPanOptions;
    }

    public void setrowsPanOptions(LinkedHashMap rowsPanOptions) {
        this.rowsPanOptions = rowsPanOptions;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print(this.getHtml());
        } catch (Exception ex) {
            throw new JspException("Class mvc.controller.jQgridTab Metodo [doEndTag] " + ex.getMessage());
        }
        return SKIP_BODY;
    }

    public String getHtml() throws SQLException {

        StringBuilder html = new StringBuilder();
        JSONObject jsontable = new JSONObject();

        JSONObject tablaChasis
                = (JSONObject) getTable(model,
                        statement, titles, widths,
                        hiddens, keys, dataList, columns, formatter, columnAlign,
                        groupFields, groupOrder, groupText, groupCollapse,
                        groupColunmShow, showSummaryOnHide, formatDate,
                        formatMoney, summaryColumn, filterToolbar, frozenColumns,
                        frozenOptions, sortableRows, generalFilter, groupColumnShow,
                        frozenColumn, summary, columnOptions, rowsPanOptions);

        selector = selector == null ? "GridGen" : selector;
        jsontable.put("tableChasis", tablaChasis);
        jsontable.put("idGrid", selector);
        jsontable.put("pager", paginador);
        jsontable.put("options", options);

        html.append("<div id='theQgrid_").append(selector).append("'");
        html.append(" style='text-align:center;display:inline-table'>");

        html.append("<table id ='").append(selector).append("'");
        if (attributes != null) {
            html.append(" ").append(attributes).append(" ");
        }
        html.append(" data-parent=\"theQgrid_").append(selector).append("\">");
        html.append("</table>");

        if (paginador != null) {
            html.append("<div id ='").append(paginador).append("'></div>");
        }

        String scriptBody = "";
        scriptBody += " \nvar datasZXC" + selector + "='" + JSONValue.toJSONString(jsontable) + "'; \n";
        scriptBody += "configGridTab.call(this,datasZXC" + selector + ");\n";

        html.append("<script id='scr_").append(selector).append("'>")
                .append(scriptBody).append("</script>")
                .append("</div>");

        return html.toString()
                .replaceAll("\\\\t", "  ")
                .replaceAll("(\\\\r|\\\\n|\\\\r\\\\n)+g", "\\\n");
    }

    public static synchronized JSONObject getTable(Object... args)
            throws SQLException {

        Model model = (Model) args[0];
        String sql = (String) args[1];
        String titles[] = (String[]) args[2];
        int wids[] = (int[]) args[3];
        int hiddens[] = (int[]) args[4];
        int keys[] = (int[]) args[5];
        List dataList = (List) args[6];
        String fields[] = (String[]) args[7];
        LinkedHashMap formater = (LinkedHashMap) args[8];
        LinkedHashMap align = (LinkedHashMap) args[9];
        String gFields = (String) args[10];
        String gOrder = (String) args[11];
        String gText = (String) args[12];
        boolean gCollapse = Boolean.valueOf(args[13].toString());
        boolean gColShow = Boolean.valueOf(args[14].toString());
        boolean sSummary = Boolean.valueOf(args[15].toString());
        int formatDate[] = (int[]) args[16];
        String formatMoney = (String) args[17];
        LinkedHashMap summary = (LinkedHashMap) args[18];
        boolean filterToolbar = Boolean.valueOf(args[19].toString());
        boolean frozen = Boolean.valueOf(args[20].toString());
        LinkedHashMap frozenOptions = (LinkedHashMap) args[21];
        boolean sortable = Boolean.valueOf(args[22].toString());
        boolean generalFilter = Boolean.valueOf(args[23].toString());
        Boolean gColumnShow[] = (Boolean[]) args[24];
        String[] fColumn = (String[]) args[25];
        String[] summaryC = (String[]) args[26];
        LinkedHashMap columOpt = (LinkedHashMap) args[27];
        LinkedHashMap rowsPan = (LinkedHashMap) args[28];

        int i = 0;
        LinkedHashMap tableModel = null;
        List<LinkedHashMap> listModel = new LinkedList();
        Iterator it = null;
        JSONObject json = new JSONObject();
        List<String> columns = null;
        Map group = new LinkedHashMap();

        if (sql != null) {
            model.listGenericHash(sql);
            columns = model.getColumnNames();
        } else {
            columns = new ArrayList<String>();
            columns.addAll(Arrays.asList(fields));
        }

        for (String column : columns) {

            tableModel = new LinkedHashMap();

            tableModel.put("index", column);
            tableModel.put("name", column);
            tableModel.put("sortable", true);
            tableModel.put("resizable", false);

            if (wids != null) {
                tableModel.put("width", (wids.length > i ? wids[i] : 50));
            }

            if (hiddens != null) {
                for (int j : hiddens) {
                    if (j == i) {
                        tableModel.put("hidden", true);
                    }
                }
            }
            if (keys != null) {
                for (int j : keys) {
                    if (j == i) {
                        tableModel.put("key", true);
                    }
                }
            }
            if (fColumn != null) {
                for (String j : fColumn) {
                    if (j.equals(column)) {
                        tableModel.put("frozen", true);
                    }
                }
            }

            if (formater != null) {
                it = formater.entrySet().iterator();
                while (it.hasNext()) {
                    Object entry = it.next();
                    int key = Integer.valueOf(((Map.Entry) entry).getKey().toString());
                    Object valor = ((Map.Entry) entry).getValue();
                    if (key == i) {
                        tableModel.put("formatter", valor);
                    }
                }

            }

            if (align != null) {
                it = align.entrySet().iterator();
                while (it.hasNext()) {
                    Object entry = it.next();
                    int key = Integer.valueOf(((Map.Entry) entry).getKey().toString());
                    Object valor = ((Map.Entry) entry).getValue();
                    if (key == i) {
                        tableModel.put("align", valor);
                    }
                }
            }

            if (summary != null) {
                it = summary.entrySet().iterator();
                while (it.hasNext()) {
                    Object entry = it.next();
                    String key = ((Map.Entry) entry).getKey().toString();
                    Object valor = ((Map.Entry) entry).getValue();
                    if (key.equals(column)) {
                        tableModel.put("summaryType", valor);
                    }
                }
            }

            if (columOpt != null) {
                it = columOpt.entrySet().iterator();
                while (it.hasNext()) {
                    Object entry = it.next();
                    String key = ((Map.Entry) entry).getKey().toString();
                    Object valor = ((Map.Entry) entry).getValue();
                    String aux[] = (String[]) valor;
                    String valores[];
                    if (key.equals(column)) {

                        for (String a : aux) {
                            valores = a.split("-");
                            tableModel.put(valores[0], (valores[1].toLowerCase().equals("false") || valores[1].toLowerCase().equals("true"))
                                    ? Boolean.valueOf(valores[1]) : valores[1]);
                        }

                    }
                }
            }

            listModel.add(tableModel);
            i++;
        }

        if (gFields != null) {
            group.put("groupField", Arrays.asList(gFields.split(",")));
            int len = gFields.split(",").length;
            int cont = 0;

            if (gText != null) {
                group.put("groupText", Arrays.asList(gText.split(",")));
            } else {

                String text = "";
                for (cont = 0; cont < len; cont++) {
                    text += ",<b>{0}</b>";
                }
                text = text.substring(1);
                group.put("groupText", Arrays.asList(text.split(",")));
            }

            if (gOrder != null) {
                group.put("groupOrder", Arrays.asList(gOrder.split(",")));
            }

            if (gColumnShow != null) {

                group.put("groupColumnShow", Arrays.asList(gColumnShow));
            } else {
                Boolean cSho[] = new Boolean[Arrays.asList(gFields.split(",")).size()];
                int c = 0;
                for (String a : Arrays.asList(gFields.split(","))) {
                    cSho[c] = false;
                    c++;
                }
                group.put("groupColumnShow", Arrays.asList(cSho));

            }

            group.put("groupCollapse", gCollapse);

            if (sSummary) {
                group.put("groupSummary", true);
            }

            json.put("grouping", true);
            json.put("groupingView", group);
        }

        if (formatDate != null) {
            Integer[] formatDat = new Integer[formatDate.length];
            int k = 0;
            for (int in : formatDate) {
                formatDat[k] = in;
                k++;
            }
            json.put("dateFormat", Arrays.asList(formatDat));

        }

        if (formatMoney != null) {
            String[] arrs = formatMoney.split(",");
            json.put("formatMoney", Arrays.asList(arrs));

        }

        if (frozen) {
            JSONObject jsonF = new JSONObject();
            jsonF.put("useColSpanStyle", true);
            JSONObject frozModel;
            List frozz = new ArrayList();

            it = frozenOptions.entrySet().iterator();
            while (it.hasNext()) {
                frozModel = new JSONObject();
                Object entry = it.next();
                String key = ((Map.Entry) entry).getKey().toString();
                Object valor = ((Map.Entry) entry).getValue();
                String cols[] = key.split("-");
                frozModel.put("startColumnName", cols[0]);
                frozModel.put("numberOfColumns", cols[1]);
                frozModel.put("titleText", valor);
                frozz.add(frozModel);
            }

            jsonF.put("groupHeaders", frozz);
            json.put("frozen", jsonF);

        }

        if (fColumn != null) {
            json.put("frozenColumn", true);
        }

        if (summaryC != null) {
            json.put("summaryCols", Arrays.asList(summaryC));
        }

        if (filterToolbar) {
            json.put("filter", true);
        }

        json.put("sortable", sortable);
        json.put("generalFilter", generalFilter);

        json.put("columnModel", listModel);
        json.put("exito", "OK");
        json.put("titleModel", Arrays.asList(titles));

        if (rowsPan != null) {
            json.put("rowsPanModel", rowsPan);
        }

        if (sql == null) {
            json.put("listData", dataList);
        } else {
            json.put("listData", model.getList());
        }

        return json;
    }

}
