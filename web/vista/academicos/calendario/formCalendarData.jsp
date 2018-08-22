<%@ page session="true" %>  
<%@ taglib uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl" %>
<%@ page import="mvc.model.Util" %>
<%@ page import="java.util.LinkedList,java.util.List,java.util.Hashtable,java.io.*" %>
<%@ page import="java.util.Hashtable,mvc.model.Util,mvc.model.*" %> 
<%@ include file="/vista/formValidSessionUser.jsp" %>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="../../../WEB-INF/InitModel.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%    Hashtable smakls = new Hashtable();

    String events = request.getParameter("events") != null ? request.getParameter("events") : "SAVE";
    String tpokls = "General";
    if (request.getAttribute("smakls") != null) {
        smakls = (Hashtable) request.getAttribute("smakls");
        tpokls = smakls.get("TPOKLS").toString();
    }
%>
<%=HTML_DOCTYPE_%>
<html> 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UFT-8">
        <%=SCRIPT_NORMAL + SCRIPT_DATEPK + SCRIPT_FILTER %> 
        <link rel="stylesheet" href="<%=BASEURL%>/vista/css/estilos.css">
        <link rel="stylesheet" href="<%= BASEURL%>/vista/css/display_tag.css">
        <link href="<%= BASEURL%>/vista/inscripciones/css/formEnrollmentData.css" rel="stylesheet" type="text/css">
        <script language="JavaScript" type="text/JavaScript" src="<%= BASEURL%>/vista/script/Funciones.js" charset="UTF-8"></script>
        <script language="JavaScript" type="text/JavaScript" src="<%=BASEURL%>/vista/script/jquery.json-2.4.min.js"></script>
        <script language="JavaScript" type="text/JavaScript" src="<%=BASEURL%>/vista/js/search.js" charset="UTF-8"></script>
        <script language="JavaScript" type="text/JavaScript" src="<%=BASEURL%>/vista/js/generic.js" charset="UTF-8"></script>
        <script language="JavaScript" id="thescript" type="text/JavaScript" src="<%=BASEURL%>/vista/academicos/calendario/js/formCalendarData.js" charset="UTF-8" tpokls="<%= tpokls %>"></script>
        <script language="JavaScript" type="text/JavaScript">
            setPath('<%= BASEURL%>'); 
            nullMouse();  
        </script>        
    </head>
    <body>
        <div id="modal_pgm" title="PROGRAMAS">
            <table id="table" style="width: 100%;">
                <tr>
                    <td align="center">
                        <a style="font-size: small;" href="#" onclick="marcartodos()" class="hypgrid">Marcar todo</a>
                        &nbsp;&nbsp;&nbsp;
                        <a style="font-size: small;" href="#" onclick="desmarcartodos()" class="hypgrid">Desmarcar todo</a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="div_prog_modal"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div id="modal_axk" title="CALENDARIOS"></div>
        <div id="panel" title="CALENDARIO">
            <form name="formUpdate" id="formUpdate"  action="<%=CONTROLLER%>/Adm/SelectionPolicies">
                <input type="hidden" name="codkls" id="codkls" value="<%=smakls.get("CODKLS") == null ? "" : smakls.get("CODKLS").toString()%>" />
                <table style="width: 100%">                    
                    <tr>
                        <td>
                            <div id="tabs" >
                                <ul>

                                    <!----------------------- PESTAÑA DE DATOS CALENDARIO ----------------------------> 
                                    <li>
                                        <a href="#KLS" id="hrefKLS">
                                            Información del calendario
                                        </a>
                                    </li>

                                    <!----------------------- PESTAÑA DE ACTIVIDADES DE CALENDARIO ----------------------------> 
                                    <li>
                                        <a href="#AXK" id="hrefAXK">
                                            Actividades de calendario
                                        </a>
                                    </li>                                    

                                    <!----------------------- PESTAÑA DE PROGRAMAS---------------------------> 
                                    <li>
                                        <a href="#PGM" id="hrefPGM">
                                            Programas
                                        </a>
                                    </li>

                                </ul>

                                <div id="KLS"> 
                                    <table style="width: 80%;">
                                        <tr>
                                            <td align="right" class="lblReq" >Nombre:</td>
                                            <td>
                                                <input type="text" name="nomkls" id="nomkls" title="Nombre del calendario" size="65" value="<%=smakls.get("NOMKLS") == null ? "" : smakls.get("NOMKLS").toString()%>" onkeypress="return validSQLI(event)"/>
                                            </td>
                                            <td align="right" class="lblReq">Año:</td>
                                            <td >
                                                <%
                                                    String filtro = "SELECT * FROM ( SELECT ROWNUM x FROM DUAL CONNECT BY LEVEL<=extract(year from sysdate)+1 ) x WHERE x.x>=2006 order by x desc";
                                                %>
                                                <TagControl:TControl model = "<%=model%>" 
                                                                     typctl = "Combo"
                                                                     name = "agnprs"
                                                value = "<%= smakls.get("AGNPRS") != null ? smakls.get("AGNPRS").toString() : ""%>" 
                                                filtro = "<%=filtro%>"
                                                                     title = "Año"/>
                                            </td>
                                            <td align="right" class="lblReq">Periodo:</td>
                                            <td >
                                                <TagControl:TControl model = "<%=model%>" 
                                                                     typctl = "Combo"
                                                                     name = "prdprs"
                                                value = "<%= smakls.get("PRDPRS") != null ? smakls.get("PRDPRS").toString() : ""%>" 
                                                filtro = "<%= "DATA:1#2"%>"
                                                                     title = "Periodo"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="right" class="lblReq" >Tipo:</td>
                                            <td>  
                                                <% String filter = "select nomelm,"
                                                            + " dspelm "
                                                            + " from smaelm "
                                                            + " join smavld "
                                                            + " on ( "
                                                            + " trim(smaelm.nomelm) = trim(smavld.codvld) "
                                                            + " and smavld.tpovld = 'KLS' "
                                                            + " and smavld.codprs = '" + model.getCodPrs() + "'"
                                                            + " )  "
                                                            + " where codelm = 'KLS' "
                                                            + " and tipelm = 'TYPE'";%> 
                                                <TagControl:TControl model = "<%= model%>" 
                                                                     typctl = "Combo" 
                                                                     name = "tpokls" 
                                                                     title = "Tipos"                                                                                  
                                                filtro = "<%= filter%>" 	
                                                value = "<%= smakls.get("TPOKLS") != null ? smakls.get("TPOKLS").toString() : "" %>" />
                                            </td>
                                            <td align="right" class="lblReq" >Estado:</td>
                                            <td>
                                                <TagControl:TControl model = "<%= model%>" 
                                                                     typctl = "Combo" 
                                                                     name = "stdkls" 
                                                                     title = "Estado"                                                                                  
                                                filtro = "<%= "DATA:Activo..#Inactivo"%>" 	
                                                                     value = "<%= smakls.get("STDKLS") != null ? smakls.get("STDKLS").toString() : "" %>" />
                                            </td>
                                        </tr>
                                    </table>
                                </div>

                                <div id="AXK"> 
                                    <table style="width: 100%;">
                                        <tr>                                            
                                            <td align="center">
                                                <div class="demo">								        
                                                        <button type = "button" id = "btnAxk" onclick="showActivities()">Agregar Actividades</button>
                                                 </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <div id="div_actividades" ></div>
                                            </td>
                                        </tr>                                        
                                    </table>                                    
                                </div>
                                                
                                <div id="PGM"> 
                                    <table style="width: 100%;">
                                        <tr>                                            
                                            <td align="center">
                                                <div class="demo">								        
                                                        <button type = "button" id = "btnPgm" onclick="showPrograms()">Agregar Programas</button>
                                                 </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <div id="div_programas" ></div>
                                            </td>
                                        </tr>
                                    </table>                                     
                                </div>                                

                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="10">
                            <div class="ui-state-error"><script>escribirMensajeRequeridos();</script></div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>