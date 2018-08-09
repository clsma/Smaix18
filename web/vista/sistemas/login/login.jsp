<%-- 
    Document   : login
    Created on : 3/08/2015, 11:25:56 AM
    Author     : ( Cl-sma ) Carlos Pinto Jimenez
--%> 

<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Hashtable,java.util.List,java.util.LinkedList,mvc.model.Util" %>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl"%>
<%@ taglib uri="/WEB-INF/tldsNew/select.tld" prefix="s"%>
<%@ taglib uri="/WEB-INF/tldsNew/input.tld" prefix="i"%>
<%@ include file="/WEB-INF/InitModel.jsp"%>
<%@ include file="/vista/validSessionMenu.jsp"%>


<%    String tempKey = "" + new Date().getTime() + session.getId() + session.getCreationTime() + "";
    String serverKey = session.getId() + tempKey + session.getCreationTime();
    clsma.setAccesRuntimeKey(serverKey);
    String reason = Util.getStrRequest("msgReason", request).trim();
    if (reason.trim().isEmpty()) {
        reason = Util.validStr((String) session.getAttribute("msgReason"), "");
    }



%>


<%=SCRIPT_NORMAL + SCRIPT_JQGRID%>

<html>
    <head>
        <title>Bienvenidos a <%=clsma.getNOMBRE_CIA()%></title>
        <link href="<%=clsma.getBASE()%>/vista/sistemas/login/css/login.css?v=ssd" rel="stylesheet">
        <link href="<%=clsma.getBASE()%>/vista/sistemas/login/css/menu-login.css?v=assd" rel="stylesheet">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <script src="<%=clsma.getBASE()%>/vista/sistemas/login/js/login.js?v=asd" charset="UTF-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script>
            setPath('<%= BASEURL%>');
        </script>
    </head>
    <body>
        <div id="reason" ><%=reason%></div>
        <div id="cont-video">
            <div id="shadow"></div>
                        <video autoplay="" muted="" loop="">
                            <source src="<%=clsma.getBASE()%>/vista/img/login/video.webm" type="video/webm">
                            <source src="<%=clsma.getBASE()%>/vista/img/login/video.mp4" type="video/mp4">
                            <source src="<%=clsma.getBASE()%>/vista/img/login/video.ogv" type="video/ogg">	
                        </video>
            <!--    <img alt="Background Image" src="<%=clsma.getBASE()%>/vista/img/login/Online-Banking.jpg" >-->
        </div>
        <div id="cont-small-window">
            <img alt="Background Image" src="<%=clsma.getBASE()%>/vista/img/login/small-img.png">
        </div>

        <div id="head">


            <nav id="cssmenu">
                <ul >
                    <li class="current-menu-item">
                        <a href="#">Admisiones</a>
                        <ul>
                            <li>
                                <a href="<%=BASEURL%>/vista/academicos/convocatorias/initOffers.jsp">Inscripciones</a>
                            </li>
                            <!--    <li>
                                    <a href="<%=BASEURL%>/vista/academicos/admisiones/formSeeCitations.jsp">Credenciales</a>
                                </li>-->
                            <li> 
                                <a href="<%=BASEURL%>/servlet/Adm/ApplicationReentryAndExtTransfer?events=INIT&tposol=TRS">Traslado</a>
                            </li>
                            <li>
                                <a href="<%=BASEURL%>/vista/academicos/convocatorias/initOffers.jsp">Transferencias</a>
                            </li>
                            <li>
                                <a href="<%=BASEURL%>/servlet/Adm/ApplicationReentryAndExtTransfer?events=INIT&tposol=RIN">Reintegros</a>
                            </li>

                            <li>
                                <a href="<%=BASEURL%>/vista/academicos/calendario/formCalendarPublic.jsp">Calendario P&uacute;blico</a>
                            </li>


                        </ul>
                    </li>
                    <li>
                        <a href="#">Egresados</a>
                        <ul> 
                            <li>
                                <a href="<%=BASEURL%>/vista/academicos/egresados/formGraduates.jsp">Solicitud de Egresado</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">Biblioteca</a>
                        <ul> 
                            <li>
                                <a href="<%=BASEURL%>/vista/biblioteca/formLibraryAdvancedSearch.jsp">Búsqueda Sencilla</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">Actividades</a>
                        <ul>
                            <li>
                                <a href="<%=BASEURL%>/vista/administrativos/compra/formSupplierRegister.jsp">Registro de proveedores</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="<%=BASEURL%>/vista/academicos/admisiones/formVerificationCertificate.jsp">Verificaci&oacute;n de certificados</a>

                    </li>
                    <li>
                        <a href="<%=BASEURL%>/vista/administrativos/tesoreria/formPrintPublicKmp.jsp">Comprobantes</a>                           
                    </li>
                    <!--                    <li>
                                            <a href="#">Usuarios</a>
                                            <ul>
                                                <li>
                                                    <a href="<%=BASEURL%>/vista/academicos/estudiantes/formAcademusoftGetPassword.jsp">Mi usuario estudiantil</a>
                                                </li>
                                            </ul>
                                        </li>-->
                    <li>
                        <a href="<%=BASEURL%>/vista/pqrs/initTickets.jsp">PQRS</a>
                    </li>
                    <li>
                        <a href="<%=BASEURL%>/vista/academicos/campusvirtual/formViewCartelera.jsp?p=PUB">Cartelera</a>
                    </li>
                    <li>
                        <a href="<%=BASEURL%>/vista/administrativos/contrato/formLaborlaCPSInscription.jsp?p=PUB">Convocatorias Laborales</a>
                    </li>
                    <li>
                        <a href="#">Acerca de.</a>
                        <ul>
                            <li><a href="#">Campus</a></li>
                            <li><a href="#">Contacto</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
<!--       <div id="alerts" class="alerts">
            <div class="alertBar" style="text-align: right">
                <a href="#" onclick="$('.alerts').toggleClass('slideUpAlert');">
                    <img src="<%=clsma.getBASE()%>/vista/img/cerrar.gif" style="width: 25px;height: 25px" />
                </a>
            </div>
            <div class="alertcontainer">
                <ul class="ulmenu">
                    <li>
                        <a href="<%=clsma.getBASE()%>/vista/academicos/prom_institucional/formAspirantValidPin.jsp?url=V/vista/academicos/prom_institucional/formAspirantSearchData.jsp" target="_blank">> Cambio de datos inscripción para Aspirantes.</a>
                    </li>
                </ul>
            </div>
        </div>-->

       
        <div id="body" class="">
            <div class="container">

                <div class="welcome-text contents">
                    <div class="imgLogo">
                        <div class="logoContainer"> 
                            <img alt="" id="imgCl" src="<%=clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/img/logo_login.png" >
                        </div>
                    </div>
                    <h1 class="info_unv">
                        <br>
                        Bienvenidos a Smaix18
                        <br>
                        <div>
                            <span style="font-weight: 800">
                                Software de Manejo Avanzado 
                            </span>
                        </div>

                    </h1>
                </div>


                <div class="form-login contents">
                    <div class="login-body">
                        <div class="messages">
                            <span></span>
                        </div>
                        <div class="form-img">
                            <img src="<%=clsma.getBASE()%>/vista/img/login/lock.png" >

                            <span>Acceso Usuarios</span>
                        </div>


                        <form id="formLogin" onsubmit="return validThisLogin(this)" autocomplete="off" action="<%=clsma.getCONTROL()%>/InitLogin/Application" method="post">
                            <s:select
                                name="codcia"
                                filter="select codcia,nomcia from smacia"
                                clase="selectLogin"
                                title="Compañia"
                                />
                            <input class="inputs inputLogin" type="text" id="userTxt" name="userTxt" data-valid="caracteres" placeholder="Usuario - Identificación "  autofocus autocomplete="off" value="">
                            <input class="inputs inputPassword" type="password" id="passTxt" name="passTxt" data-valid="caracteres" placeholder="Contraseña"  autocomplete="off"> 
                            <p>
                                <button class="btn btn-lg btn-primary" type="submit">Entrar</button>
                            </p>
                            <p>
                                <i:input type="checkbox" name="keep" label="Mantener en sesión" clase="keep" labelClass="keep"/>
                            </p>
                            <input type="hidden" id="tpoTxt">
                            <input type="hidden" id="usrTxt">
                            <input type="hidden" id="coreid" value="<%=tempKey%>">
                        </form>
                        <div class="frgt">
                            <p><a href="#FRGPSW" class="a-for">¿ Olvidó su contraseña ?</a></p>
                        </div>
                    </div>


                </div>

            </div>
        </div>
        <section id="forgrtContent">
            <div id="FRGPSW" class="frgtPsw " style="max-width: 100%" >
                <div style="max-width: 100%;height: 100%;padding: 5px 3px;">

                    <div id="FORMCHN" style="width: 50%;margin: 0 auto;position: relative;top: 50%;transform: translateY(-50%)">
                        <div style="width: 100%;" class="messages2">
                            <span style="font-weight: bolder">

                            </span>
                        </div>

                        <s:select filter="[#AAA-Seleccione Tipo de usuario#,#BAS-Estudiante#,#EGR-Egresado#,#PRS-Funcionarios y Otros#]" name="tpoprs" title="Tipo de usuario" clase="selectLogin" change="validUser()"/>
                        <s:select filter="select '000' idepgm , 'Seleccione su programa' nombre from dual union select idepgm , nropgm || ' - ' || nompgm || ' - ' ||  nomscn nombre
                                  from smapgm 
                                  join smascn
                                  on smapgm.idescn = smascn.idescn
                                  where stdpgm = 'Activo ..'" name="idepgm" title="Programa" clase="selectLogin " />
                        <i:input attribute="placeholder='Identificación'" clase="inputs inputLogin" type="text" name="nriprs" valid="dni" maxlength="60" title="Identificación" />
                        <button id="bRestore" class="btn btn-lg btn-primary" style="margin-top: 8px" onclick="validChgn()">Recuperar</button>
                    </div>

                </div>
            </div>
        </section>            
        <div id="foot" class="">
            <div class="footerBar">
                <h2>Copyright © 2015 All rights reserved. Powered by CL SMA Ltda</h2>
            </div>
            <div id="footer-content">

            </div>
        </div>
        <div id="showTable"></div>
    </body>
</html>
