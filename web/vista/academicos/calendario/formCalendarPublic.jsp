  
<%    request.setAttribute("validLogin", "false");
%>
<%@include file="/vista/formPageIncludes.jsp" %>
<html>
    <head>
        <script language="JavaScript" type="text/JavaScript" src="<%=clsma.getBASE()%>/vista/academicos/calendario/js/formCalendarPublic.js?param=<%= new Date().getTime()%>" charset="UTF-8"></script>
        <script language="JavaScript" type="text/JavaScript">
            nullMouse();	
        </script>
        <style>
            body {
                margin: 0px;
                padding: 0px;
                background: #214243 url("../img/imginit/bg1_01.gif") repeat;
                font-family: Arial, Helvetica, sans-serif;
                font-size: 13px;
                color: #5e6e6e;
            }
            #div_lstAxk,#div_lstKxp{
                display:none;
            }
        </style>   

    </head>
    <body>
        <div id="panel" align="center" title="Calendario academico" class="form-control">

            <div id="tabs"></div>
            <div id="container">    

                <div id="LST" title="Calendarios">
                    <table >		
                        <tr>
                            <td align="right" style="display:none;" >Tipo:</td>
                            <td style="display:none;">   
                                <% String filter = "select nomelm,"
                                            + " dspelm "
                                            + " from smaelm "
                                            + " join smavld "
                                            + " on ( "
                                            + " trim(smaelm.nomelm) = trim(smavld.codvld) "
                                            + " and smavld.tpovld = 'KLS' "
                                            + " "
                                            + " )  "
                                            + " where codelm = 'KLS' "
                                            + " and tipelm = 'TYPE' order by 2 ";%> 
                                <TagControl:TControl model = "<%= model%>" 
                                                     typctl = "Combo" 
                                                     name = "tpokls_" 
                                                     title = "Tipos"                                                                                  
                                filtro = "<%= filter%>" 	
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
                </div>
                <div id="DETL" title="Detalle" ></div>
                <div id="KLS" title="Informaci&oacute;n" data-parent="DETL" >
                    <form id="formKls" name="formKls">
                        <table style="width: 80%;">
                            <tr>
                                <td align="right" class="lblReq" >
                                    Nombre
                                </td>
                                <td>
                                    <input type="text" name="nomkls" id="nomkls" 
                                           title="Nombre del calendario" 
                                           size="65"
                                           readonly="true"
                                           onkeypress="return validSQLI(event)"/>
                                </td>
                            </tr>
                            <tr>
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
                            </tr>
                            <tr>
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
                            </tr>
                            <tr>
                                <td align="right" class="lblReq" >
                                    Estado
                                </td>
                                <td>
                                    <TagControl:TControl model = "<%= model%>" 
                                                         typctl = "Combo" 
                                                         name = "stdkls_" 
                                                         title = "Estado"                                                                                  
                                                         filtro = 'DATA:Activo..#Inactivo' />
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>   
                <div id="AXK" title="Actividades" data-parent="DETL"> 
                    <table style="width: 100%;">
                        <tr>
                            <td align="center" >
                                <div id="div_actividades" ></div>
                            </td>
                        </tr>                                        
                    </table>                                    
                </div>

                <div id="PGM" title="Programas" data-parent="DETL"> 
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