
<%@ page session = "true" %>
<%@ page errorPage = "/vista/errorPage.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl"%>
<%@ include file="../../../WEB-INF/InitModel.jsp" %>
<%@ include file="/vista/formValidSessionUser.jsp" %>

<% 
  String urlnxt = (request.getAttribute("urlnxt") != null) ? request.getAttribute("urlnxt").toString() : "";
    
  if (urlnxt.equals("")) urlnxt = (request.getParameter("urlnxt") != null) ? request.getParameter("urlnxt") : "";
  
  if (urlnxt.startsWith("C")){
    urlnxt = "/servlet" + urlnxt.substring(1);
  }else if( urlnxt.startsWith("V") ){
    urlnxt = urlnxt.substring(1);}

String msg=(request.getAttribute("msg") != null) ? request.getAttribute("msg").toString() : "";
%>
<%=HTML_DOCTYPE_%>
<html>
    <head>
        <%=SCRIPT_NORMAL+SCRIPT_DIALOG %>
        <title>USUARIO EN SESION</title>
        <script language="JavaScript" type="text/JavaScript" src="<%= BASEURL %>/vista/js/formValidSession.js"></script>

        <script language="JavaScript" type="text/JavaScript">
            setPath('<%= BASEURL %>');
            nullMouse();

            setUrl('<%=urlnxt %>');
        </script>

    </head>
    <body  onload="init_page()">

        <div id="panel" title="USUARIO EN SESIÓN" style="display: none;">

            <form name="formUpdate" id="formUpdate" method="post" onsubmit="return validarsesion(this);" action="<%= CONTROLLER %>/Adm/ValidSession?state=VALID" >
            <!--<form id="formUpdate" onsubmit="return validSubmittion(this);" action="<%=CONTROLLER%>"></form>-->
                <table cellspacing="0" cellpadding="0" border="0" width="350" align="center" id="formulario">
                    <tr><td>
                            <table border="0" cellspacing="2" cellpadding="2" width="350">

                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td align="center" valign="middle" colspan="2">
                                        <img id="codpho" width="98" height="120" border="0" src="<%=clsma.getBASE() %>/utility/photos/<%=model.getNroPrs()%>.png" onerror="this.src='<%=clsma.getBASE() %>/vista/img/notFoundImg.jpg'">
                                    </td>
                                </tr>
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr><td colspan="2"><hr></td></tr>
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td align="right" class="caption">Usuario</td>
                                    <td align="left" class="message"><%=(model.getApeprs()+" " +model.getNomprs()).toUpperCase() %></td>
                                </tr>
                                <tr>
                                    <td align="right" class="caption">Clave</td>
                                    <td align="left"><input name="pswprs" id="pswprs" size="15" maxlength="15" type="password" title="Clave" class="text" onkeypress=" validateKeyPress(event)"></td>
                                </tr>
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td colspan="5"  style="font-family:Verdana, Tahoma , Arial, Helvetica, sans-serif;font-size: 12px;">
                                        <div align="center">
                                            El m&oacute;dulo al cual desea ingresar el usuario es de acceso seguro, esto es, que debe validarse nuevamente para poder ingresar a &eacute;l.
                                        </div>
                                    </td>			
                                </tr>
                                <tr><td colspan="2">&nbsp;</td></tr>
                                <tr>
                                    <td  colspan="2">
                                        <div align="center" class="demo">  
                                            <!--<input name="Boton" type="submit" value="Aceptar" class="boton">-->
                                            <!--<a id="botonA" name="botonA" onclick='validarsesion()'>Aceptar</a>-->
                                        </div>
                                    </td>
                                </tr>

                            </table>
                        </td></tr>
                </table>
                <input name="urlnxt" id="urlnxt" type="hidden" value="<%= urlnxt %>">
            </form>
            <input name="msg" id="msg" type="hidden" value="<%= msg %>">

            <!--</form>-->
        </div>
    </body>
</html>