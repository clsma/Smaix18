<%@ page session="true"%>
<%@ page import="java.util.List,java.util.LinkedList,java.util.Hashtable"%>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="/WEB-INF/InitModel.jsp"%>
<%@ include file="/vista/formValidSessionUser.jsp" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<html>
    <head>
        <%=SCRIPT_NORMAL%>
        <%=SCRIPT_JQGRID%>
        <%=SCRIPT_DATEPK%>

        <script language="JavaScript" type="text/JavaScript" src="<%=BASEURL%>/vista/academicos/calendario/js/formCalendar.js?param=asd" charset="UTF-8"></script>
        <script language="JavaScript" type="text/JavaScript">
            setPath('<%= BASEURL%>');
            nullMouse();	
        </script>

    </head>
    <body>
        <div id="panel" align="center" title="Calendario academico" class="form-control">

            <div id="tabs">

                <div id="LST" title="Calendarios">

                    <form name="formUpdate" id="formUpdate" method="post">
                        <table width="98%">		
                            <tr>
                            <td align="right" >Tipo:</td>
                            <td>  
                                <% String filter = "";%> 
                                <TagControl:TControl model = "<%= model%>" 
                                                     typctl = "Combo" 
                                                     name = "tpokls_" 
                                                     title = "Tipos"                                                                                  
                                filtro = "DATA:Template,General" 	
                                                     value = ""
                                                     javascriptnew = "showList()"/>
                            </td>
                            <td align="right" >Estado:</td>
                            <td>
                                <%
                                filter = "DATA:Activo..#Inactivo";
                                %>
                                <TagControl:TControl model = "<%= model%>" 
                                                     typctl = "Combo" 
                                                     name = "stdkls" 
                                                     title = 'Estado'                                                                                 
                                                     filtro = '<%=filter%>' 	
                                                     value = ""
                                                     javascriptnew = "showList()"/>
                            </td>
                            </tr>
                            <tr>
                            <td colspan="4" style="text-align: center">
                                <div id="div_calendarios"></div>
                            </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div id="KLS" title="Calendario">
                    <form id="formKls" name="formKls">
                        <table style="width: 80%;">
                            <tr>
                            <td align="right" class="lblReq" >
                                Nombre
                            </td>
                            <td>
                                <input type="text" name="nomkls" id="nomkls" title="Nombre del calendario" size="65" onkeypress="return validSQLI(event)"/>
                            </td>
                            <td align="right" class="lblReq">
                                Año</td>
                            <td >
                                <%
                                    String filtro = "SELECT * FROM ( SELECT ROWNUM x FROM DUAL CONNECT BY LEVEL<=extract(year from sysdate)+1 ) x WHERE x.x>=2006 order by x desc";
                                %>
                                <TagControl:TControl model = "<%=model%>" 
                                                     typctl = "Combo"
                                                     name = "agnprs" 
                                                     title = "Año"
                                filtro = "<%=filtro%>"
                                                     />
                            </td>
                            <td align="right" class="lblReq">
                                Periodo
                            </td>
                            <td >
                                <TagControl:TControl model = "<%=model%>" 
                                                     typctl = "Combo"
                                                     name = "prdprs"                                            
                                                     title = "Periodo"
                                filtro = "DATA:1#2"
                                                     />
                            </td>
                            </tr>
                            <tr>
                            <td align="right" class="lblReq">
                                Tipo
                            </td>
                            <td>  
                                <%  filter = "select nomelm,"
                                            + " dspelm "
                                            + " from smaelm "
                                            + " join smavld "
                                            + " on ( "
                                            + " trim(smaelm.nomelm) = trim(smavld.codvld) "
                                            + " and smavld.tpovld = 'KLS' "
                                            + " and smavld.codprs = '" + model.getCodPrs() + "'"
                                            + " )  "
                                            + " where codelm = 'KLS' "
                                            + " and tipelm = 'TYPE' order by 2 ";%> 
                                <TagControl:TControl model = "<%= model%>" 
                                                     typctl = "Combo" 
                                                     name = "tpokls" 
                                                     title = "Tipos"      
                                filtro = "<%= filter%>" 	
                                                     />
                            </td>
                            <td align="right" class="lblReq" >
                                Estado
                            </td>
                            <td>
                                <TagControl:TControl model = "<%= model%>" 
                                                     typctl = "Combo" 
                                                     name = "stdkls" 
                                                     title = "Estado"                                                                                  
                                filtro = 'DATA:Activo..#Inactivo' />
                            </td>
                            </tr>
                        </table>
                    </form>
                </div>   
                <div id="AXK" title="Actividades"> 
                    <table style="width: 100%;">
                        <tr>                                            
                        <td align="center">
                            <div class="demo">								        
                                <!--<button type = "button" id = "btnAxk" onclick="showActivities()">Agregar Actividades</button>-->
                            </div>
                        </td>
                        </tr>
                        <tr>
                        <td align="center" >
                            <div id="div_actividades" ></div>
                        </td>
                        </tr>                                        
                    </table>                                    
                </div>

                <div id="PGM" title="Programas"> 
                    <table style="width: 100%;" border="0">
                        <tr>                                            
                        <td style="text-align: right">
                            Tipo:
                        </td>
                        <td style="text-align: left"> 
                            <% filtro = "select distinct tpopgm from smapgm";%>
                            <TagControl:TControl model="<%=model%>"
                            filtro="<%=filtro%>"
                                                 name="tpopgm"
                                                 typctl="Combo"
                                                 javascriptnew="showPgm()">

                            </TagControl:TControl>
                        </td>
                        <td style="text-align: right">
                            Modalidad:
                        </td>
                        <td style="text-align: left"> 
                            <% filtro = "select distinct stgpgm from smapgm order by 1 desc";%>
                            <TagControl:TControl model="<%=model%>"
                            filtro="<%=filtro%>"
                                                 name="stgpgm"
                                                 typctl="Combo"
                                                 javascriptnew="showPgm()">

                            </TagControl:TControl>
                        </td>
                        </tr>
                        <tr>
                        <td align="center" colspan="4">
                            <div id="div_programas" ></div>
                        </td>
                        </tr>
                    </table>                                     
                </div>
                <div id="DKS" title="Departamentos"> 
                    <table style="width: 100%;">
                        <tr>                                            
                        <td align="center">
                            <div class="demo">								        
                                <!--<button type = "button" id = "btnPgm" onclick="showPrograms()">Agregar Programas</button>-->
                            </div>
                        </td>
                        </tr>
                        <tr>
                        <td align="center">
                            <div id="div_departamentos" ></div>
                        </td>
                        </tr>
                    </table>                                     
                </div>
            </div>
        </div>

        <div style="display: none">
            <TagControl:TControl model="<%=model%>"
                                 typctl="Combo"
                                 filtro="DATA:Particular#General"
                                 name ="tipaxk" >
            </TagControl:TControl>

        </div>
    </body>
</html>