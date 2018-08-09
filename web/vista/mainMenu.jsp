<%@page import="mvc.controller.AdmInitPageAction"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Hashtable,java.util.LinkedList,mvc.model.Util"%> 
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="/WEB-INF/InitModel.jsp"%>
<%@ include file="/vista/formvalidSessionLogin.jsp" %>
<%@ page import="java.util.StringTokenizer"%>

<!DOCTYPE HTML>
<% 
String idemenu = "001";
    AdmInitPageAction admInitPageAction = new AdmInitPageAction();
    AdmInitPageAction.sendRedirectMenu(response, session, idemenu);
%>
<html>
    <head>
        <%            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setDateHeader("Expires", -1); //evita o caching no servidor proxy
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
        %>

        <%=SCRIPT_NORMAL%>
        <%=SCRIPT_FUNCIONES%>

        <TITLE>Sma ver ix14 (Software de Manejo Académico y Administrativo)</TITLE>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />

        <link rel="shortcut icon"   href="<%= BASEURL%>/vista/styles/<%=STYLE_%>/img/favicon.ico" >
        <!--<link rel="stylesheet"      href="<%= BASEURL%>/vista/styles/<%=STYLE_%>/css/estilos.css">--> 

        <%-- Objetos del menú viejo funciona para IE --%>
        <link rel='stylesheet' type='text/css' href='<%= BASEURL%>/vista/styles/<%=STYLE_%>/css/menu/styles.css'   id="menuNew"  />
        <link rel='stylesheet' type='text/css' href='<%= BASEURL%>/vista/styles/<%=STYLE_%>/css/menu/menu.css'     id="menuNew"  />
        <link rel="stylesheet" type="text/css" href="<%= BASEURL%>/vista/styles/<%=STYLE_%>/css/style_menu.css"    id="menuOld"/>
        <link rel="stylesheet" type="text/css" href="<%= BASEURL%>/vista/styles/shakes.css"    id="menuOld"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <%-- Objetos del menú nuevo funciona para todos menos IE --%>
        <!--<script language="JavaScript" type="text/JavaScript" src="<%= BASEURL%>/vista/styles/<%=STYLE_%>/js/menu_jquery.js"></script>-->       

        <script language="JavaScript" type="text/JavaScript" src="<%= BASEURL%>/vista/styles/<%=STYLE_%>/js/mainMenu.js?v=asds" charset="UTF-8"></script>


        <script language="JavaScript" type="text/javascript" id="panleInit" >
            setPath('<%= BASEURL%>');
 
            $(function() {
                var menuTop = $('#display').offset().top + 20;
                menuTop = $(window).height() - menuTop;
                $('#display').height(menuTop);
                 $('#activityFrm').height($(window).height() - 50);
                $(window).resize(function() {
                    var menuTop = $('#display').offset().top + 20;
                    menuTop = $(window).height() - menuTop;
                    $('#display').height(menuTop);
                });
                $('#activityFrm').height($(window).height() - 50);
            });

        </script>

        <script language="JavaScript" type="text/JavaScript" src="<%= BASEURL%>/vista/main/js/menuStart.js?v=sfyd" charset="UTF-8"></script>
        <script type="text/javascript" src="<%=clsma.getBASE()%>/vista/main/js/script.js" charset="UTF-8"></script>

        <style>
            *{margin: 0px;
              padding:0px
            }

            #foot{
                position: fixed;
                bottom: 0px;
                margin-right: auto;
                margin-left: auto;


            }

            .inf{
                color: white;
                text-shadow: 1px 0px 1px white;
                font-size: 12px;
            }

            .dtlInf{
                color: white;
                text-shadow: 1px 0px 1px white;
                font-size: 11px;
                margin-left: 10px;
            }
            .phot{
                height:80px;
                /*border: 1px solid black;*/


            }

            .phot img{
                box-shadow: 0px 1px 1px black;
                border-radius: 5px;
            }

            a.close_:before{
                content: 'X';
            }
            a.close_{
                text-decoration: none;
                background-color: white;
                box-shadow: 2px 2px gray;
                font-weight: bolder;
                font-size: 13px;
                color: black;
            }
            a.close_:hover{
                background-color: gray;
                box-shadow: 2px 2px 2px white;
                color: white;
            }

        </style> 
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />

    </head>

    <body style=""> 

        <div id="tblSmaHeader" style="height: 80px">

            <div class="icon" style="display: inline-table;vertical-align: top;width: 50%;">
                <img alt="" id="imgCl" src="<%= BASEURL%>/vista/styles/<%=STYLE_%>/img/logo_intro.png"  style="margin-left: 95px ; height: 80px" />
            </div>

            <div class="dataUser" style="display: inline-table;vertical-align: top;width: 20%;height: 80px;margin-left: 100px">
                <div style="padding-top: 20px;padding-left: 20px">

                    <table style="width: 100%;">
                        <tr>
                            <td align="left" class="inf">Usuario En sesión</td>
                            <td align="left" class="dtlInf">
                                <%=model.getApeprs() + " " + model.getNomprs()%>
                            </td>
                        </tr>


                        <tr>

                            <td class="inf">Fecha y hora</td>
                            <td align="left" class="dtlInf">

                                <table>
                                    <tr>
                                        <td><%=new SimpleDateFormat("dd-MM-yyyy").format(new Date())%></td>
                                        <td> - </td>
                                        <td id="liveclock"></td>
                                    </tr>
                                </table>

                            </td>
                        </tr>
                        <tr>
                            <td class="inf">Identificación</td>
                            <td class="dtlInf" id='codprstext'><%=model.getCodPrs()%></td>
                        </tr>

                    </table>
                </div>
            </div>

            <div class="phot"  style="display: inline-block;vertical-align: top;text-align: right;right: 0;margin-left: 20px;top: 0">
                <%
                    String onerror = BASEURL + "/vista/img/img-not-foud.png";
                %>
                <a><img src="<%=BASEURL%>/utility/photos/<%=model.getNroPrs()%>.png" onerror="this.src='<%=onerror%>'" style="width: 80px;height: 80px" id="foto_perfil"></a>
            </div>

        </div>

        <div id="cssmenu" style="min-height: 50px;  border: 5px; border-color: red;z-index: 1000"> </div>

        <div id="balck">
            <%--
                /*
                 * ******** NOTIFICACION TAREAS PENDIENTES *******
                 */
                //String urlSrc = BASEURL + "/vista/formIntro.jsp?codhlp=PTN" + tpoprs;
                  String urlSrc = CONTROLLER +"/Adm/Introduction";
                if (!Mensaje.equals("")) {
                    urlSrc = BASEURL + Send;
                }
            --%>
        </div>

        <div  id="frameContent" > 
            <iframe name="display" id="display" style="min-height: 500px; height: 100%;width: 100%"
                    src="<%-- urlSrc--%>"
                    frameborder="0" onload="">
            </iframe>   
        </div>  
        <div id="activity" style="position: absolute;float: left;left: 0;top:0;display: none;width: 100%;height: 100%;background-color: #F1EEEE;z-index: 99999">
            <a class="close_" style="border-radius: 40%;float: right;top: 10px;right: 10px;position: relative;border: 1px dotted gray;padding: 10px" href="#"></a>
            <div class="name" style="text-align: center">
                <h3 style="font-size: 20px;color: black;text-shadow: 1px 2px white;margin-top: 10px" class="h3"></h3> 
            </div>
            <div id="bodiactivity">
                <iframe name="activityFrm" id="activityFrm" style="min-height: 200px; height: 100%;width: 100%"
                        src="<%-- urlSrc--%>"
                        frameborder="0" onload="">
                </iframe>   
            </div>
        </div>

        <!-- **********************************************************   -->
        <!-- **************** 		Footer 	   *************************   -->          			
        <!-- **********************************************************   -->  
        <div  style="position: fixed;bottom: 0;width: 100%" id="foot" >
            <table    id="tblSmaHeader"  >

                <tr   >
                    <td height="20" align="center" valign="middle"  >

                        <span style="font-family: sans-serif;font-style: normal;color: white;text-shadow: 1px 1px   black;font-size: 13px">
                            Copyright &copy; 2015 All rights reserved. Powered by 
                        </span>

                        <a href="http://www.clsma.net/" style="font-family: sans-serif;font-style: normal;color: white;text-shadow: 1px 1px black;;font-size: 14px" title="Compa&ntilde;ia L&iacute;der en Software de Manejo Avanzado" >
                            CL SMA Ltda
                        </a>	

                    </td>
                </tr>	
            </table>
        </div>
    </body>
</HTML>
