<%-- 
    Document   : formPageIncludes
    Created on : 15/07/2015, 01:13:13 PM
    Author     : clsma
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Hashtable"%>
<%@page import="java.util.LinkedList"%>
<%@page session="true"%>
<%@page import="java.util.Date,mvc.model.Util,java.util.*"%>
<%@include file="/WEB-INF/InitModel.jsp"%>
<%@taglib  uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl" %>
<%@taglib  uri="/WEB-INF/tldsNew/input.tld" prefix="in" %>
<%@taglib  uri="/WEB-INF/tldsNew/textarea.tld" prefix="ta" %>
<%@taglib  uri="/WEB-INF/tldsNew/select.tld" prefix="se" %>
<%@taglib  uri="http://java.sun.com/jstl/core" prefix="c" %>
<%--
<%    if (Util.getStrRequest("validLogin", request).trim().isEmpty()) {
%>
<%@ include file="/vista/formvalidSessionLogin.jsp" %>
<%    }--%>

<%    String base = BASEURL;
    String control = CONTROLLER;
    Map imports = new HashMap();
    imports.put("normal", SCRIPT_NORMAL);
    imports.put("html", HTML_DOCTYPE_);
    imports.put("jqgrid", SCRIPT_JQGRID);
    imports.put("money", SCRIPT_MONEY);
    imports.put("menuvertical", SCRIP_MENU_VERTICAL);
    imports.put("time", SCRIPT_TIMEPKII);
    imports.put("editor", SCRIPT_EDITOR);
    imports.put("autocomplete", SCRIPT_AUTOCOMPLETE);

    String neww = "<script src=\"" + clsma.getBASE() + "/vista/academicos/pensum/js/jquery.contextmenu.r2.js\"  charset=\"UTF-8\"></script> ";
    imports.put("contextmenu", neww);
    neww = "<script src=\"" + clsma.getBASE() + "/vista/js/jquery.tooltip.js\" charset=\"UTF-8\" ></script>  ";
    imports.put("tooltip", neww);
    neww = "<script src=\"" + clsma.getBASE() + "/vista/js/formHeadPrs.js?v=ads\" charset=\"UTF-8\" ></script>  ";
    imports.put("head", neww);
    neww = " <link rel=\"stylesheet\" href=\"" + clsma.getBASE() + "/vista/styles/bootstrap-3.3.6/dist/css/bootstrap.css\">"
            + "<script src=\"" + clsma.getBASE() + "/vista/styles/bootstrap-3.3.6/dist/js/bootstrap.js\" charset=\"UTF-8\"></script>"
            + "<script src=\"" + clsma.getBASE() + "/vista/js/components/btpanel.js?v=asd\" charset=\"UTF-8\"></script>"
            + "<script src=\"" + clsma.getBASE() + "/vista/js/components/btalert.js?v=as\" charset=\"UTF-8\"></script>"
            + "<script src=\"" + clsma.getBASE() + "/vista/js/components/btcollapse.js?v=asd\" charset=\"UTF-8\"></script>"
            + "<script src=\"" + clsma.getBASE() + "/vista/js/components/generics.js?v=asd\" charset=\"UTF-8\"></script>";
    imports.put("bootstrap", neww);
    neww = "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + clsma.getBASE().trim() + "/vista/js/formUploadPhoto.js?v=asd\" charset=\"UTF-8\"></script>";
    imports.put("photo", neww);
    neww = "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + clsma.getBASE().trim() + "/vista/js/scheduleDraw.js?v=sqd\" charset=\"UTF-8\"></script>";
    imports.put("schedule", neww);
    neww = "<script language=\"JavaScript\" type=\"text/JavaScript\" src=\"" + clsma.getBASE().trim() + "/vista/biblioteca/js/libraryComponents.js?v=asd\" charset=\"UTF-8\"></script>";
    imports.put("libraryComponents", neww);
%>


<%=HTML_DOCTYPE_ + SCRIPT_NORMAL + SCRIPT_JQGRID + SCRIPT_MONEY + SCRIP_MENU_VERTICAL + SCRIPT_TIMEPKII%>

<%
//    request.setCharacterEncoding("UTF-8");
//    response.setCharacterEncoding("UTF-8");
%>
<html>    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <script type="text/javascript">
            setPath('<%=BASEURL%>');
            clsma.dataPrs = {nomprs: '<%=model.getApeprs() + " " + model.getNomprs()%>',
                codprs: '<%=model.getCodPrs()%>',
                nroprs: '<%=model.getNroPrs()%>',
                nrousr: '<%=model.getNroUsr()%>',
                codcia: '<%=model.getCodCia()%>',
                reportProvider: '<%=model.getCompany().getREPORT_PROVIDER()%>'
            };
            <%
                if (!Util.getStrRequest("usable", request).trim().isEmpty()) {
            %>
            clsma.userHasControl = <%=model.userHasRole(Util.getStrRequest("usable", request), model.getNroUsr(), null)%>
            $(function () {
                if (clsma.userHasControl) {
                    $('body').attr('data-access', '<%=Util.getStrRequest("usable", request)%>');
                } else {
                    $('body').empty()
                    clsma.$msg('Usuario no posee los roles necesarios para el uso de esta actividad.');
                    clsma.$error('Usuario no posee los roles necesarios para el uso de esta actividad.');
                }
            });

            <%
                }
            %>
        </script>

    </head>
</html>
