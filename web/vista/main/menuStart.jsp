<%-- 
    Document   : menuStart
    Created on : 17/07/2015, 11:58:56 PM
    Author     : 222
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/vista/formPageIncludes.jsp" %>
<html>
    <head>
        <title>SoftWare</title>
        <script type="text/javascript" src="<%=clsma.getBASE()%>/vista/main/js/menuStart.js"></script>
        
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<%=clsma.getBASE()%>/vista/main/css/styles.css">
        <script type="text/javascript" src="<%=clsma.getBASE()%>/vista/main/js/script.js" charset="UTF-8"></script>
        <!--<link rel="shortcut icon"   href="<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/img/favicon.ico" >-->
        <!--<link rel="stylesheet"      href="<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/css/estilos.css">--> 

        <%-- Objetos del menú viejo funciona para IE --%>
        <!--<link rel='stylesheet' type='text/css' href='<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/css/menu/styles.css'   id="menuNew"  />-->
        <link rel='stylesheet' type='text/css' href='<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/css/menu/menu.css'     id="menuNew"  />
        <!--<link rel="stylesheet" type="text/css" href="<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/css/style_menu.css"    id="menuOld"/>-->


        <%-- Objetos del menú nuevo funciona para todos menos IE --%>
        <!--<script language="JavaScript" type="text/JavaScript" src="<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/js/menu_jquery.js"></script>-->       
        <!--<script language="JavaScript" type="text/JavaScript" src="<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/js/mainMenu.js"></script>-->

        <style>
            body{
                padding: 0;
                margin: 0;
            }

            .frame{
                width: 100%;
                height: 100%;
            }

            #display{
                width: 100%;    
                height: 700px;
                border: none;
            }

            *{margin: 0px;
              padding:0px
            }

            #foot{
                position: fixed;
                bottom: 0px;
                margin-right: auto;
                margin-left: auto;


            }/*

*/            .inf{
                color: white;
                text-shadow: 1px 0px 1px white;
                font-size: 12px;
            }

            .dtlInf{

                color: white;
                text-shadow: 1px 0px 1px white;
                font-size: 11px;
            }
            .phot{
                width: 43%;
                height:80px;
                border: 1px solid black;
                box-shadow: 0px 1px 1px black;
            }

            .phot img{

                border-radius: 5px;
            }


        </style>
    </head>
    <body>
        <table  id="tblSmaHeader" >
            <tr>	
                <td  align="left" width="60%">
                    <img alt="" id="imgCl" src="<%= clsma.getBASE()%>/vista/styles/<%=clsma.getSTYLE()%>/img/logo_intro.png"  style="margin-left: 95px ; height: 80px" />
                </td>
                <td align="right"  valign="top" width="30%">
                    <table style="margin-right: 20px;width: 60%;top: 0"  >
                        <tr>

                            <td> 
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
                                        <td class="dtlInf"><%=model.getCodPrs()%></td>
                                    </tr>

                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
                <td align="rigth" width="10%">
                    <div class="phot">
                        <%
                            String onerror = clsma.getBASE() + "/vista/img/img-not-foud.png";
                        %>
                        <a><img src="<%= clsma.getBASE()%>/utility/photo/<%=model.getCodPrs()%>.png" onerror="this.src='<%=onerror%>'" style="width: 100%;height: 80px"></a>
                    </div>
                </td>

            </tr>
        </table>

        <div id='cssmenu'>

        </div>
        <div class="frame" >
            <iframe id="display" name="display" ></iframe>
        </div>
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
</html>
