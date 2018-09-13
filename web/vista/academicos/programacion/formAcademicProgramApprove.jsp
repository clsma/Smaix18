
<%@page import="mvc.controller.AdmAcademicProgramApproveAction"%>
<%@include file="/vista/formPageIncludes.jsp" %>
<%    String usable = Util.getStrRequest("usable", request);
    //usable = AdmAcademicProgramApproveAction.validRol(model, usable);
    // String cal = AdmAcademicProgramApproveAction.validCalendar(model);   
%>
<html>
    <head>        
        <script src="<%=request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/") + 1)%>js/formAcademicProgramApprove.js?v=ssss" charset="UTF-8"></script>
        <title>Aprobaci&oacute;n Programaci&oacute;n Academica</title>
        <style>

            table.tabli td:nth-child(odd){
                text-align: right;
            }
            table.tabli td:nth-child(even){
                text-align: left;
            }

            fieldset{
                border: 1px solid gray;
                padding: 5px;
            }
            legend{
                color: green;
                font-weight: bolder
            }

            .advise{
                width: 100%;
                margin : 10px auto;
                color: green;
                font-weight: bolder;
                font-size: 14px;
                text-align: center;
            }


        </style>
    </head>
    <body>        
        <div id="panel"  title="Programación Académica">
            <div class="advise">Aprobación Programación Académica </div>
            <div style="width: 100%;margin-bottom: 10px;text-align: center">
                <table style="display: inline-block" class="form-control">
                    <tr>
                        <td>Año:
                            <input type = "text" 
                                   style="border: none;" 
                                   class="textDisabled" 
                                   id = "agnprs" 
                                   size = "10" 
                                   name = "agnprs" 
                                   readonly = "readonly" 
                                   value = ""/>

                            Periodo:
                            <input type = "text" 
                                   style="border: none;" 
                                   class="textDisabled" 
                                   id = "prdprs" 
                                   size = "10" 
                                   name = "prdprs" 
                                   readonly = "readonly" 
                                   value = ""/>  
                            <button id="searchPak">Buscar</button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="advise advisegrp"></div>
            <div id="tabs"></div>
            <div id="container" class="form-control">
                <div id="PGM" title="Programas"></div>                                
                <div id="DTL" title="Solicitud"></div>                                             
                <div id="SMT" title="Semestres" data-parent="DTL"></div>
                <div id="PRF" title="Docentes" data-parent="DTL"></div>                
                <div id="RSM" title="Resumen" data-parent="DTL"></div>                
            </div>
            <div id="msg"></div>
        </div>       
    </body>
</html>
