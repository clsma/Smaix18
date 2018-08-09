<%

//    response.sendRedirect("http://190.14.253.28:8080" + request.getRequestURI() + "?" + request.getQueryString());
%>


<%-- Define e inicializa el modelo --%> 
<%@page import="java.util.Date"%>
<%@page import="mvc.model.ClCompanyInformation"%>
<%@page import="mvc.model.WebModel"%>
<%@page import="mvc.model.Util"%>
<%@page import="java.util.Hashtable"%>


<jsp:useBean
    id="model"
    scope="session"
    class="mvc.model.WebModel">
    <% model.init(application);
        model.ipAddresRequest = request.getRemoteAddr();
    %>
</jsp:useBean> 


<jsp:useBean
    id="clsma"
    scope="session" 
    class="mvc.model.ClCompanyInformation">
    <%
        if (clsma.hasCia == null) {
            clsma.ClCompanyInformation();
            clsma.setApplication(application);
            clsma.initContext();
            model.setCompany(clsma);            
        }
    %>
</jsp:useBean>

<%
    String idemnx = Util.getStrRequest("seek", request);
//    String isSeek = Util.getStrRequest("seekExecute", request);
    model.ipAddresRequest = request.getRemoteAddr();
    String path = request.getServletPath();
    String ip = request.getLocalAddr();
    String params = request.getQueryString();

    if (!clsma.verifyAccess(idemnx, path, params, model).equals("TRUE")) {
        throw new ServletException("No tiene permisos para esta actividad");
    }
    /*
     String ipAddress = null;
     String getWay = request.getHeader("VIA");   // Gateway
     ipAddress = request.getHeader("X-FORWARDED-FOR");   // proxy
     if (ipAddress == null) {
     ipAddress = request.getRemoteAddr();
     }
     System.out.println("IP Address: " + ipAddress + " " + getWay);*/

    String BASEURL = request.getContextPath();
    String CONTROLLER = BASEURL + "/servlet";

    clsma.setBASE(BASEURL);
    clsma.setCONTROL(CONTROLLER);

    String STYLE_ = clsma.getSTYLE();

    if (model.getCodCia() == null || model.getCodCia().trim().equals("")) {
        String codcia = Util.validStr(clsma.getHasCia(), "CODCIA");
        String nombre = clsma.getNOMBRE_CIA();
        model.setCodCia(codcia);
        model.setNomCia(nombre);
        model.setNroPrs("XXXXXXXXXX");
        model.setNroUsr("XXXXXXXXXX");
        model.setCodPrs("XXXXXXXXXX");
        model.setTpoPrs("GST");
    }

    String formatDate = (application.getInitParameter("formatDate") != null) ? application.getInitParameter("formatDate") : "";
    String formatDateShow = (application.getInitParameter("formatDateShow") != null) ? application.getInitParameter("formatDateShow") : "";

    model.setFormatDate(formatDate);
    model.setFormatDateShow(formatDateShow);

    String SCRIPT_NORMAL;
    /*
     SCRIPT_NORMAL =  "<link type=\"text/css\" href=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/themes/uic-theme/jquery.ui.all.css\" rel=\"stylesheet\" />";
     SCRIPT_NORMAL += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/demos/demos.css\" rel=\"stylesheet\" />";
     SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/js/jquery-1.10.2.js\"></script>\n"
     + "<script language='JavaScript' type='text/JavaScript' src='"+ BASEURL+"/vista/script/Funciones.js'  charset='UTF-8'></script>\n"
     +"<script language='JavaScript' type='text/JavaScript' src='"+BASEURL+"/vista/script/jquery.json-2.4.min.js'  charset='UTF-8'></script>\n"
     +"<script language='JavaScript' type='text/JavaScript' src='"+BASEURL+"/vista/js/search.js'  charset='UTF-8'></script>\n"
     +"<script type='text/JavaScript' src='"+BASEURL+"/vista/js/generic.js' charset='UTF-8'></script>\n"
     +"<link rel='stylesheet' href='"+BASEURL+"/vista/css/estilos.css'>";
     */
    SCRIPT_NORMAL = "<link type=\"text/css\" href=\"" + BASEURL + "/vista/styles/" + STYLE_ + "/ui-theme/jquery.ui.all.css\" rel=\"stylesheet\" />"
            + "<link rel='stylesheet'  type=\"text/css\" href='" + BASEURL + "/vista/styles/" + STYLE_ + "/css/estilos.css'>"
            + "<link rel='stylesheet'  type=\"text/css\" href='" + BASEURL + "/vista/styles/general.css?v=asda'>";

    SCRIPT_NORMAL
            += "<link rel='stylesheet'  type=\"text/css\" href='" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/demos/demos.css' />"
            + "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/js/jquery-1.10.2.js\"></script>\n"
            + "<script language='JavaScript' type='text/JavaScript' src='" + BASEURL + "/vista/script/Funciones.js?v=sd'  charset='UTF-8'></script>\n"
            + "<script language='JavaScript' type='text/JavaScript' src='" + BASEURL + "/vista/script/jquery.json-2.4.min.js'  charset='UTF-8'></script>\n"
            + "<script language='JavaScript' type='text/JavaScript' src='" + BASEURL + "/vista/js/search.js?v=?v=assd'  charset='UTF-8'></script>\n"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/clsma.accent.js?v=ass\" charset=\"UTF-8\"></script>"
            + "<script language='JavaScript' type='text/JavaScript' src='" + BASEURL + "/vista/js/ecmascript.js?v=assd'  charset='UTF-8'></script>\n"
            + "<script type='text/JavaScript' src='" + BASEURL + "/vista/js/generic.js?v=aerd3' charset='UTF-8'></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/script/jquery.loader.js?v=asd\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/jquery.search.js?v=sass\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/jqGridTag.js?v=aas\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/clsma.js?v=awss\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/clsma.prototypes.js?v=sas\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/script/md5.js?v=ass\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/jquery.findPrs.js?v=sss\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/attachUpload.js?v=asss\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/tabValidator.js?v=asss\" charset=\"UTF-8\"></script>"
            + "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + BASEURL + "/vista/js/components/Components.js?v=asss\" charset=\"UTF-8\"></script>";

    SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery-ui.js\"></script>";
    SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.core.js\"></script>";
    SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.widget.js\"></script>";
    SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.button.js\"></script>";
    SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.mouse.js\"></script>";
    SCRIPT_NORMAL += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.position.js\"></script>";

    String SCRIPT_DIALOG = "";
//    SCRIPT_DIALOG  = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery-ui-1.8.7.custom/development-bundle/external/jquery.bgiframe-2.1.1.js\"></script>";
//    SCRIPT_DIALOG += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery-ui-1.8.7.custom/development-bundle/ui/jquery.ui.draggable.js\"></script>";
//    SCRIPT_DIALOG += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery-ui-1.8.7.custom/development-bundle/ui/jquery.ui.resizable.js\"></script>";
//    SCRIPT_DIALOG += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery-ui-1.8.7.custom/development-bundle/ui/jquery.ui.dialog.js\"></script>";
//    SCRIPT_DIALOG += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery-ui-1.8.7.custom/development-bundle/ui/jquery.ui.dialog.js\"></script>";

    String SCRIPT_DATEPK = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.datepicker.js\"></script>";

    String SCRIPT_TIMEPKII = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-timepicker-addon.js\" charset='UTF-8'></script>";
    SCRIPT_TIMEPKII += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/js/jquery-timepicker_new/jquery-ui-timepicker-addon.css\" rel=\"stylesheet\" id=\"\" />";
    String SCRIPT_TIMEPK = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-timepicker/jquery.timepicker.js\"></script>";
    SCRIPT_TIMEPK += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/js/jquery-timepicker/jquery.timepicker.css\" rel=\"stylesheet\" id=\"\" media=\"print, projection, screen\"/>";
    SCRIPT_TIMEPK += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-timepicker/lib/site.js\"></script>";
    SCRIPT_TIMEPK += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/js/jquery-timepicker/lib/site.css\" rel=\"stylesheet\" id=\"\" media=\"print, projection, screen\"/>";

//    String SCRIPT_AUTOCP = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery-ui-1.8.7.custom/development-bundle/ui/jquery.ui.autocomplete.js\"></script>";
    String SCRIPT_TABBED = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.sortable.js\"></script>";
    SCRIPT_TABBED += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.tabs.js\"></script>";

    String SCRIPT_FILTER = "";
    SCRIPT_FILTER += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/picnetjquerytablefilter/picnet.jquery.tablefilter.js\"></script>";

    String SCRIPT_SORTER = "";
    SCRIPT_SORTER += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/script/jquery.tablesorter/tablesorter/themes/blue/style.css\" rel=\"stylesheet\" id=\"\" media=\"print, projection, screen\"/>";
    SCRIPT_SORTER += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tablesorter/tablesorter/jquery.tablesorter.js\"></script>";
    SCRIPT_SORTER += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tablesorter/tablesorter/chili-1.8b.js\"></script>";
    SCRIPT_SORTER += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tablesorter/tablesorter/jquery.tablesorter.pager.js\"></script>";
    SCRIPT_SORTER += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tablesorter/tablesorter/jquery.metadata.js\"></script>";

    String SCRIPT_TOOLTP = "<link type=\"text/css\" href=\"" + BASEURL + "/vista/css/jquery.cluetip.css\"  rel=\"stylesheet\"/>";
    SCRIPT_TOOLTP += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.cluetip.js\"></script>";
    SCRIPT_TOOLTP += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/demo.js\"></script>";

    String HTML_DOCTYPE_ = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";

    String SCRIPT_JQTREE = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.metadata.js\"></script>";
    SCRIPT_JQTREE += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tree.js\"></script>";
    SCRIPT_JQTREE += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tree.metadata.js\"></script>";

    String SCRIPT_ACCORD = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.accordion.js\"></script>";

    String SCRIPT_DRGDRP = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.draggable.js\"></script>";
    SCRIPT_DRGDRP += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/development-bundle/ui/jquery.ui.droppable.js\"></script>";

    String SCRIPT_TBLSCR = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.tablescroll/jquery.tablescroll.js\"></script>";

    String SCRIPT_TREETB = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/treeTable/javascripts/jquery.ui.js\"></script>";
    SCRIPT_TREETB += "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/treeTable/javascripts/jquery.treeTable.js\"></script>";
    SCRIPT_TREETB += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/script/treeTable/stylesheets/master.css\"  rel=\"stylesheet\"/>";
    SCRIPT_TREETB += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/script/treeTable/stylesheets/jquery.treeTable.css\"  rel=\"stylesheet\"/>";

    String SCRIPT_JQGRID = "<script id=\"jqScript\" type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery.jqGrid-4.5.4/js/i18n/grid.locale-es.js\" charset='UTF-8'></script>";
    SCRIPT_JQGRID += "<script id=\"jqScript\" src=\"" + BASEURL + "/vista/js/jquery.jqGrid-4.5.4/js/jquery.jqGrid.min.js\" type=\"text/javascript\" charset='UTF-8'></script>";
    SCRIPT_JQGRID += "<link id=\"jqScript\" href=\"" + BASEURL + "/vista/js/jquery.jqGrid-4.5.4/css/ui.jqgrid.css\" rel=\"stylesheet\" type=\"text/css\" />";

    String SCRIPT_FUNCIONES = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/Funciones.js\"></script>";
    String SCRIPT_JSON = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/jquery.json-2.4.min.js\"></script>";
    String SCRIPT_GENERIC = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/generic.js\"></script>";
    String SCRIPT_DISPLAY_TAB = "<link rel='stylesheet'  type=\"text/css\" href='" + BASEURL + "/vista/styles/" + STYLE_ + "/css/display_tag.css'>";

    String SCRIPT_MONEY = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/plugin-sma/accounting.min.js\" charset='UTF-8'></script>";
    SCRIPT_MONEY = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/js/jquery-ui-1.10.4/plugin-sma/accounting.js\" charset='UTF-8'></script>";
    String SCRIPT_EDITOR = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/tinymce_spanish/tinymce/jscripts/tiny_mce/tiny_mce.js\" charset='UTF-8'></script>";

    String SCRIP_MENU_VERTICAL = "<script language=\"JavaScript\" type=\"text/JavaScript\" src=" + BASEURL + "/vista/js/jquery.menu-vertical.js?v=adsi\" charset=\"UTF-8\"></script>";
    SCRIP_MENU_VERTICAL += "<link href=" + BASEURL + "/vista/styles/vertical-menu.css?v=sss\" rel=\"stylesheet\"> ";

    String SCRIPT_AUTOCOMPLETE = "<script type=\"text/javascript\" src=\"" + BASEURL + "/vista/script/EasyAutocomplete-1.3.3/jquery.easy-autocomplete.js\"></script>";
    SCRIPT_AUTOCOMPLETE += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/script/EasyAutocomplete-1.3.3/easy-autocomplete.css\"  rel=\"stylesheet\"/></script>";
    SCRIPT_AUTOCOMPLETE += "<link type=\"text/css\" href=\"" + BASEURL + "/vista/script/EasyAutocomplete-1.3.3/easy-autocomplete.themes.css\"  rel=\"stylesheet\"/></script>";

%>

