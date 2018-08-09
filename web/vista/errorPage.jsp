<%-- 
    Document   : Error
    Created on : 13/01/2015, 04:36:35 PM
    Author     : clsma
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>

<html style="height: 100%">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error page</title>
        <script type="text/javascript" src="<%=request.getContextPath()%>/vista/js/jquery-ui-1.10.4/js/jquery-1.10.2.js"></script>
        <script src="<%=request.getContextPath()%>/vista/styles/bootstrap-3.3.6/dist/js/bootstrap.js" charset="UTF-8"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/vista/styles/bootstrap-3.3.6/dist/css/bootstrap.css">

    </head>

    <body>
        <div class="container" style="margin-top: 100px">

            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12 ">
                <div class="row" >
                    <h1 style="font-size: 75px;font-weight: bolder">Oops!</h1>
                    <h3>
                        La aplicación ha obtenido un resultado inesperado. X
                    </h3>
                   
                </div>
                <div class="row">
                    <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12" style="padding-left: 0">
                        <a href="#" onclick="$('#msgs').toggle();">Ver detalle</a>
                    </div>
                    <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12" style="padding-left: 0;display: none" id="msgs" >
                        <div class="alert alert-danger" role="alert">
                            <%
                                String msg = exception.getMessage(); 
                                out.print(msg);
                                //out.print(msg.substring(0, msg.length() > 300 ? 300 : msg.length()));
                            %>
                            ...</div>
                    </div>
                </div>
            </div>

            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12" style="margin-left: 10px;display: inline-block;vertical-align: middle;float: none;" >
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="text-align: center">
                        <img src="<%=request.getContextPath()%>/vista/img/error_icon.png"  class="img-responsive" style="margin: 0 auto"/>
                    </div>
                </div>
            </div>
            <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12 ">

            </div>
            <div  class="col-lg-12 col-sm-12 col-md-12 col-xs-12" style="margin-top: 50px;">
                <a  onclick="window.top.location.href = '<%=request.getContextPath()%>'" class="btn btn-default col-xs-12 col-lg-2 col-md-2 col-lg-offset-5 col-md-offset-5"  >
                    <span class="glyphicon glyphicon-home" aria-hidden="true"></span> Inicio
                </a>
            </div>
        </div>
    </body>
</html>
