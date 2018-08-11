<%@ page session="true"%>
<%@ page import = "java.util.Hashtable,java.util.List,java.util.LinkedList,mvc.model.Util" %>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl"%>
<%@ include file="/WEB-INF/InitModel.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", -1); //evita o caching no servidor proxy
    response.sendRedirect(clsma.getBASE()+"/vista/sistemas/login/login.jsp");
%>

<html >
    <head>

        <%=SCRIPT_NORMAL%>
        <%=SCRIPT_DIALOG%>
        <%=SCRIPT_ACCORD%>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title> Software de manejo avanzado V - ix18  </title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />
        <script type="text/javascript"  src="<%= BASEURL%>/vista/js/init/formForgetPassword.js" ></script>
        <script language="JavaScript" type="text/JavaScript" src="<%= BASEURL%>/vista/js/init/init.js" urlbase="<%=BASEURL%>" stylebase="<%=clsma.getSTYLE()%>" id="scriptinit"></script>

        <link href="<%=BASEURL%>/vista/styles/<%=STYLE_%>/css/styleinit.css" rel="stylesheet" type="text/css" media="all" />
        <style>

            .borderRadius{
                -webkit-border-radius: 5px;
                -moz-border-radius: 5px;
                border-radius: 5px;
                background: yellow;

            }

        </style>
        <script>
            setPath('<%=BASEURL%>');
        </script>
    </head>



    <body class="homepage" onload="changeImg()">
        <div id="model_menu" title="RESULTADOS EXAMEN DE ADMISION" style="display: none;" >	
        </div>
        <div id="model_individual" title="RESULTADOS EXAMEN DE ADMISION" style="display: none;" >	
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="header" class="container">
            <tr valign="top">
                <td style="display:block" colspan="20">
                    <div id="menu" style="width:100%;" >
                        <ul>
                            <li><a id="hrefConv" onclick="location.href = '<%=BASEURL%>/vista/academicos/convocatorias/initOffers.jsp';" href="#" title=""><span>Inscripciones</span></a></li>
                            <li><a href="<%=BASEURL%>/vista/academicos/convocatorias/initOffers.jsp" title=""   class="itemOculto" ><span>Convocatorias</span></a></li>
                            <li><a href="<%=BASEURL%>/vista/academicos/admisiones/formSeeCitations.jsp" title=""  class="itemOculto" ><span>Consulta de Citaciones</span></a></li>
                            <li><a href="<%=BASEURL%>/servlet/Adm/ApplicationReentryAndExtTransfer?State_=INIT&tposol=RIN" title=""  ><span>Reingresos</span></a></li>
                            <li><a href="<%=BASEURL%>/servlet/Adm/ApplicationReentryAndExtTransfer?State_=INIT&tposol=TRS" title=""  ><span>Transferencias Internas</span></a></li>
                            <li><a href="<%=BASEURL%>/vista/academicos/estudiantes/formAcademusoftGetPassword.jsp" title="Consigue tu usuario como estudiante de la universidad"   ><span>Mi usuario estudiantil</span></a></li>
                            <li><a href="#" accesskey="3" style="cursor: pointer;" title=""  class="itemOculto" ><span>Egresados</span></a></li>
                            <li><a href="#" accesskey="3" style="cursor: pointer;" title=""  class="itemOculto" ><span>Resultados Examen de Admision</span></a></li>
                            <li><a href="<%=BASEURL%>/vista/pqrs/initTickets.jsp" style="cursor: pointer;" ><span>PQRS</span></a></li>
                            <li><a href="<%=BASEURL%>/vista/academicos/admisiones/formVerificationCertificate.jsp" style="cursor: pointer;" ><span>Verificación de certificado</span></a></li>
                        </ul>
                    </div></td>
            </tr>

        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="page" class="container">
            <tr>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0" id="page-bgtop">
                        <tr>
                            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td id="page-bgbtm"><div id="content">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td><div id="banner">
                                                                <div class="image"><img src="<%=BASEURL%>/vista/styles/<%=STYLE_%>/img/imginit/banner1.jpg" width="671" height="225" alt="" id="imgRandom" name="imgRandom" /></div>
                                                                <div class="caption1">
                                                                    <h2><%=clsma.getNOMBRE_CIA()%></h2>
                                                                    <p>SMA versión ix18</p>
                                                                </div>
                                                                <div class="border"></div>
                                                            </div></td>
                                                    </tr>
                                                </table>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td><div class="box-style2" id="box3">
                                                                <br>
                                                                <h2 > <%=clsma.getNOMBRE_CIA()%> </h2>
                                                                <p class="byline"><%=clsma.getLEMA_CIA()%></p>
                                                                <div class="content">
                                                                    <div  class="image-style3 image-style3a"><img id="imgJef" src="<%=BASEURL%>/vista/styles/<%=STYLE_%>/img/imginit/banner4.jpg" width="168" height="146" alt="" /><span></span></div>
                                                                    <p>Presentamos la nueva plataforma SMA IX18, Sistema de informacion integrado para apoyo a los estudiantes, docentes y funcionarios de la Universidad<a href="#">
                                                                            <strong style="color:maroon">mas...</strong></a></p>
                                                                    <p><a  href="#" class="button-style1" style="position: relative;top: -40px"><span>Ultimas convocatorias</span></a><a href="#" target="_blank" ><img src="<%=BASEURL%>/vista/img/logo_clsma.png" width="180" height="65" style="position: relative;top: -20px;left: 45px" /></a></p>

                                                                </div>
                                                            </div></td>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div id="sidebar">
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td><div id="box1" class="box-style1">
                                                                <h2 class="title">Ingresar plataforma SMA</h2>

                                                                <div class="content">
                                                                    <div class="image-style1 image-style1a">
                                                                        <form method="post" name="formLogin" class="form-control"  id="formLogin" action="<%= CONTROLLER%>/Valid/Login" >
                                                                            <ul class="style2">
                                                                                <li>
                                                                                    <div style="position: absolute;top: 4px;left: 4px;vertical-align: bottom;">
                                                                                        Instituciones:
                                                                                    </div>
                                                                                    <div style="position: absolute;top: 4px;left: 80px;">
                                                                                        <TagControl:TControl model="<%= model%>" typctl="Combo" name="codcia" title="Usuario" value="UIC" filtro="select codcia,npqcia from smacia,smaciu where smaciu.codciu = smacia.codciu order by nomcia"/> 																		  
                                                                                    </div>
                                                                                </li>
                                                                                <li>
                                                                                    <div style="position: absolute;top: 30px;left: 4px;vertical-align: bottom;" align="right">
                                                                                        Codigo:
                                                                                    </div>
                                                                                    <div style="position: absolute;top: 30px;left: 80px;">
                                                                                        <input type="text" name="codprs" id="codprs" class="text input-control" value="" data-valid="letranumero" style="height: 18px; width: 94px" onkeypress="return validSQLINumber(event)">
                                                                                    </div>
                                                                                </li>
                                                                                <li>
                                                                                    <div style="position: absolute;top: 56px;left: 4px;vertical-align: bottom;" align="right">
                                                                                        Password:
                                                                                    </div>
                                                                                    <div style="position: absolute;top: 56px;left: 80px;">
                                                                                        <!--                                                                                        <input type="password" name="pswprs" id="pswprs" class="text" value="" style="width: 95px; height: 18px" maxlength="15" onkeypress="if (event.keyCode == 13)
                                                                                                                                                                                            capturarDatos()"><input type="submit"   name="btnLogin" class="bnt" value="Login" onClick="validLogin()"/>-->
                                                                                        <input type="password" name="pswprs" id="pswprs" class="text input-control"   value="" style="width: 95px; height: 18px" maxlength="15" >
                                                                                        <input type="button" id="btnLogin"  name="btnLogin" class="bnt" value="Login" onClick/>
                                                                                    </div>
                                                                                </li>
                                                                            </ul>
                                                                            <input name="logout" type="hidden" id="logout" value="In">

                                                                        </form>
                                                                    </div>
                                                                    <a href="#" onclick="forgetPassword()">Olvido&nbsp;su&nbsp;password&nbsp;&#8230;</a>															
                                                                </div>

                                                                <div class="bgbtm"></div>
                                                            </div></td>
                                                    </tr>
                                                </table>

                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tbody>
                                                        <tr>
                                                            <td>
                                                                <div id="box2" class="box-style1">
                                                                    <h2 class="title">PROVEEDORES</h2>
                                                                    <div class="content">
                                                                        <ul class="style2">
                                                                            <li>
                                                                                <div class="image-style2 image-style2a"><img src="<%= BASEURL%>/vista/img/formSpr.png" width="65" height="48" alt=""><span></span></div>
                                                                                <h3><a href="<%=BASEURL%>/vista/administrativos/compra/formSupplierRegister.jsp" style="color: #214243" target="blank">Registro de proveedores</a></h3>

                                                                            </li>

                                                                        </ul>
                                                                    </div>
                                                                    <div class="bgbtm"></div>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>                                   


                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td><div id="box2" class="box-style1">
                                                                <h2 class="title">CAMPUS</h2>
                                                                <div class="content" id="sedes">

                                                                </div>
                                                                <div class="bgbtm"></div>
                                                            </div></td>
                                                    </tr>
                                                </table>
                                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td><div id="box2" class="box-style1">
                                                                <h2 class="title">CONTACTENOS</h2>
                                                                <div class="content">
                                                                    <ul class="style2">
                                                                        <li>
                                                                            <div class="image-style2 image-style2a" style="clear: both;float: none"><img src="<%=BASEURL%>/vista/img/init/no_image_icon.gif" width="65" height="48" alt="" /><span></span></div>
                                                                            <br>
                                                                            <h3><a href="#" style="color: #214243" target="blank">Email : <%=clsma.getEMAIL_CIA()%></a></h3>
                                                                            <p style="font-weight: bold">Telefono : <%=clsma.getTELEFONO_CIA()%> </p> 
                                                                            <p style="font-weight: bold">Atención al cliente:</p>
                                                                            <p><%=clsma.getATENCION_CIA()%></p> 
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <div class="bgbtm"></div>
                                                            </div></td>
                                                    </tr>

                                                </table>
                                            </div></td>
                                    </tr>
                                </table></td>
                        </tr>
                    </table></td>
            </tr>
        </table>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" id="footer">
            <tr>
                <td valign="top"><p>(c) 2015 CL SMA LTDA. All rights reserved. </p></td>
            </tr>
        </table>
    </body>
</html>
