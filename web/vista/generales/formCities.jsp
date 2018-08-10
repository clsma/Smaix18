

<%-- 
    Document   : formCities
    Created on : 2/09/2016 10:27:52 AM
    Author     : Ing. Jacobo Llanos
--%>

<%@include file="/vista/formPageIncludes.jsp" %>
<html>
    <head>
        <script src="<%=request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/") + 1)%>js/formCities.js?v=asd" charset="UTF-8"></script>
        <title>Ciudades</title>
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
        <div id="panel"  title="Ciudades">
            <div id="tabs"></div>
            <div id="container" class="form-control">
                <div id="List" title="Listado">
                    <table>
                        <tr>
                            <td class="lstCiu">
                                
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="Basic" title="Básicos">
                    <in:input name="ideciu" type="hidden" />
                    <table>
                        <tr>
                                <td align="right" class="lblReq">Código:</td>
                                <td><input type="text" name="codciu" id="codciu" title="Código" data-valid="letranumero" maxlength="5" size="15" onkeypress="return validSQLILettersAndNumber(event)" /></td>
                        </tr>
                        <tr>
                                <td align="right" class="lblReq">Nombre:</td>
                                <td><input type="text" name="nomciu" id="nomciu" title="Nombre" size="80" data-valid="letras" maxlength="100" onkeypress="return validSQLILetters(event)" /></td>
                        </tr>
                        <tr>
                                <td align="right" class="lblReq">Identificador:</td>
                                <td><input type="text" name="npqciu" id="npqciu" title="Identificador" size="40" maxlength="30" data-valid="letras" onkeypress="return validSQLILetters(event)" /></td>
                        </tr>	
                        <tr>
                                <td class="lblReq" align="right">Departamento: </td>
                                <td align="left">
                                    <div id="dpto"></div>
<!--                                <input readonly="readonly" name="codciuShw" type="text" id="codciuShw"  title="Ciudad" class="text" value="" onfocus="javascript:return;" > -->
                                </td>
                        </tr>
                    </table>
                    
                </div>
            </div>
            <div id="msg"></div>
        </div>
    </body>
</html>