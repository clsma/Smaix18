<%-- 
    Document   : mainMenu
    Created on : 15/12/2015, 04:45:03 PM
    Author     : Cl-sma(Carlos Pinto)
--%>

<%@page import="mvc.controller.AdmInitPageAction"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/vista/formPageIncludes.jsp" %>
<%    String idemenu = "002";
    AdmInitPageAction.sendRedirectMenu(response, session, idemenu);
%>
<html>
    <head>
        <meta charset="utf-8" />
        <meta name="author" content="clsma" />
        <meta name="viewport" content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes" />

        <title>Smaix18</title>
        <%=imports.get("bootstrap")%>
        <link type="text/css" rel="stylesheet" href="<%=clsma.getBASE()%>/vista/sistemas/mmenu/menu/css/demo.css?v=asd" />
        <link type="text/css" rel="stylesheet" href="<%=clsma.getBASE()%>/vista/sistemas/mmenu/dist/core/css/jquery.mmenu.all.css?v=ass" />
        <link type="text/css" rel="stylesheet" href="<%=clsma.getBASE()%>/vista/styles/shakes.css" />
        <script type="text/javascript" src="<%=clsma.getBASE()%>/vista/sistemas/mmenu/dist/core/js/jquery.mmenu.min.all.js"></script>
        <script type="text/javascript" src="<%=clsma.getBASE()%>/vista/sistemas/mmenu/menu/mmenu.js?v=ashsasd"></script>
        <style>
            #activity a.close_:before{
                content: 'X';
            }
            #activity a.close_{
                text-decoration: none;
                background-color: white;
                box-shadow: 2px 2px gray;
                font-weight: bolder;
                font-size: 13px;
                color: black;
            }
            #activity a.close_:hover{
                background-color: gray;
                box-shadow: 2px 2px 2px white;
                color: white;

            }



        </style>
        <script>
            $(function() {
                $('#display').height($(window).height() - 50);
                var div = $('<div id="imgInit"/>')
                        .css({width: '100%', height: '100%', 'text-align': 'center', display: 'block'});
                var img = $('<img />')
                        .attr({src: Rutav + '/vista/styles/<%=clsma.getSTYLE()%>/img/logo.png'})
                        .css({top: '50%', 'transform': 'translateY(-50%)', position: 'relative',width:'330px',height:'220px'})
                        .appendTo(div);
                $('#display').contents().find('body').append(div);
                $(window).resize(function() {
                    var display = $('#display').css({height: $(window).height() - 50, width: '100%'});
                    var div = display.contents().find('div#imgInit');
                    var img = div.find('img');
                    if (!isEmpty(div)) {
                        div.css({height: '100%', width: '100%'});
                        if (div.width() <= 500) {
//                            img.css({height: 120, width: 130});
                        } else {
//                            img.css({height: 'auto', width: 'auto'});
                        }

                    }
                });
            });


        </script>
    </head>
    <body>
        <div id="page">
            <div class="header ui-state-default">
                <a href="#menu"></a>
                <div class="row textprs" style="width: 100%">
                    <div class="col-xs-8 col-md-7 textprs" style="font-size: 80%">
                        <div><%=clsma.getNOMBRE_CIA() %></div>
                    </div>
                    <div class="col-xs-3 col-md-4" id="date" style="font-size: 80%">
                        <div >
                            <%=(new SimpleDateFormat(" MMMM dd 'de' yyyy ").format(new Date())).toUpperCase()%>
                            <br/>
                            <span id="liveclock"></span>
                        </div>
                    </div>

                </div>
            </div>
            <div class="content">
                <iframe style="width: 100%;position: absolute;top: 48px;left: 0;border: none;" id="display" name="display"></iframe>
            </div>
            <div id="activity" style="position: fixed;left: 0;top:0;display: none;width: 100%;height: 100%;background-color: #F1EEEE;z-index: 99999">
                <a class="close_" style="border-radius: 40%;float: right;top: 10px;right: 10px;position: relative;border: 1px dotted gray;padding: 10px" href="#"></a>
                <div class="name" style="text-align: center">
                    <h3 style="color: black;text-shadow: 1px 2px white" class="h3"></h3> 
                </div>
                <div id="bodiactivity">
                    <iframe name="activityFrm" id="activityFrm" style="min-height: 500px; height: 95%;width: 100%;position: fixed"
                            src="<%-- urlSrc--%>"
                            frameborder="0" >
                    </iframe>   
                </div>
            </div>
        </div>
        <nav id="menu">

        </nav>

    </body>
</html>
