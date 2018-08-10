

<%-- 
    Document   : formCountries
    Created on : 2/09/2016 10:27:52 AM
    Author     : Ing. Jacobo Llanos
--%>

<%@include file="/vista/formPageIncludes.jsp" %>
<html>
    <head>
        <script src="<%=request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/") + 1)%>js/formCountries.js?v=asd" charset="UTF-8"></script>
        <title>Paises</title>
        <style>
            
            table.tabli td:nth-child(odd){
                text-align: right;
            }
            table.tabli td:nth-child(even){
                text-align: left;
            }
        </style>
    </head>
    <body>
        <div id="panel"  title="Paises">
            <div id="tabs"></div>
            <div id="container" class="form-control">
                <div id="List" title="Listado">
                    <table>
                        <tr>
                            <td class="lstPai">
                                
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="Basic" title="Básicos">
                    <in:input name="idepai" type="hidden" />
                    <table>
                        <tr>
                            <td class="lblReq">
                                C&oacute;digo:
                            </td>
                            <td>
                                <in:input  name="codpai" type="text" size="7" valid="letranumero" maxlength="6"/> 
                            </td>
                        </tr>
                        <tr>
                            <td class="lblReq">
                                I.S.A:
                            </td>
                            <td>
                                <in:input  name="isapai" type="text" size="3" valid="letranumero" maxlength="2"/> 
                            </td>
                        </tr>
                        <tr>
                            <td >
                                I.S.B:
                            </td>
                            <td>
                                <in:input  name="isbpai" type="text" size="4" valid="letranumero" maxlength="3"/> 
                            </td>
                        </tr>
                        <tr>
                            <td class="lblReq">Nombre</td>
                            <td>
                                <in:input name="nompai" type="text" size="80" maxlength="100" valid="letranumero" />
                            </td>
                        </tr>
                        <tr>
                                <td class="lblReq" align="right">Continente: </td>
                                <td align="left">
                                    <se:select name="codcnt" filter="select '000' codcnt, 'Seleccione..' nomcnt from dual union select codcnt, nomcnt from vewcnt" title="Continente"  />
                                </td>
                        </tr>
                    </table>
                    
                </div>
            </div>
            <div id="msg"></div>
        </div>
    </body>
</html>