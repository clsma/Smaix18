<%-- 
    Document   : formBillingSchemes
    Created on : 1/09/2015, 11:42:03 AM
    Author     : clsma
--%>
<%@include file="/vista/formPageIncludes.jsp" %>
<%
      String text = "La diferencia entre \"Proyectar\" y \"Copiar\": La proyección se hace con base en la demanda de los cursos anteriores y reformas de pensum, la copia se duplicar lo que se tenía el período anterior al nuevo";
      String pgms = "select smapxp.idepgm, smapxp.nompgm \n" +
                   " from table(sma_academic.programs_assign( p_codcia => '" + model.getCodCia() + "'\n" +
                   "                                        , p_codprs => '" + model.getCodPrs() + "' )) smapxp\n" +
               " order by smapxp.nompgm";
%>
<html>
    <head>
        <title>Proyectar programaci&oacute;n Acad&eacute;mica</title>
        <script src="<%=clsma.getBASE()%>/vista/academicos/programacion/js/formAcademicProgramProject.js?v=<%=Math.random() * 93.33%>"></script>
    </head>
    <body>
        <div id="panel" title="Proyectar programaci&oacute;n Acad&eacute;mica">
               
                <div id="tabs"></div>
                <div id="container">
                    <div id="PROJECTS" title="Programas">
<!--                        <table id="listProject"></table>-->
<!--<div style="width: 100%;margin-bottom: 10px;text-align: center">-->
                                            <table style="width: 100%;">
                                                <tr>
                                                    <td>
                                                        <table style="width: 100%;">
                                                            <tr>
                                                                <td align="right">
                                                                    <label class="label">
                                                                        Año :
                                                                    </label>
                                                                </td>
                                                                <td>
                                                                    <input type = "text" style="border: none;" class="textDisabled" id = "agnprs" size = "10" name = "agnprs" readonly = "readonly" value = ""/>
                                                                </td>
                                                                <td align="right">							        		
                                                                    <label class="label">
                                                                        Periodo :
                                                                    </label>
                                                                </td>
                                                                <td>
                                                                    <input type = "text" style="border: none;" class="textDisabled" id = "prdprs" size = "10" name = "prdprs" readonly = "readonly" value = ""/>															        								        						        
                                                                </td>	
                                                            </tr>
                                                        </table>

<!--  <table style="display: inline-block" class="form-control">
                    <tr>
                        <td>Año:
                            < se:select name="agnprs" title="Año" filter="select '999','Seleccione'
                                       from dual
                                       union
                                       select distinct agnprs , agnprs
                                       from smaaxk
                                       where trunc( sysdate )
                                       between trunc( bgnaxk ) and trunc( endaxk )
                                       and codaxk = 'PAK'
                                       order by 1 desc" value="999"/>
                            Periodo:
                            < se:select name="prdprs" title="Periodo" filter="select '999','Seleccione'
                                       from dual
                                       union
                                       select distinct prdprs , prdprs
                                       from smaaxk
                                       where trunc( sysdate )
                                       between trunc( bgnaxk ) and trunc( endaxk )
                                       and codaxk = 'PAK'
                                       order by 1 desc" value="999" />
                            <button id="searchPak">Buscar</button>
                        </td>
                    </tr>
                </table>-->

                                                    </td>
                                                </tr>					 						  	 					  	 
                                                <tr>
                                                    <td colspan="4" align="center">
                                                        <div id="divProject"></div>
                                                        <input type="hidden" name="idepgm_" id="idepgm_" value="">
                                                    </td>
                                                </tr>
                                            </table>					  	 

                    </div>
<!--</div>-->                    
                    <div id="DETAILS" title="Detalle" > </div>

                    <div id="SEMESTER" title="Semestre" data-parent="DETAILS">
<div style="width: 100%;margin-bottom: 10px;text-align: center">
<table style="display: inline-block" class="form-control">
    <tr>
     <td style="width: 50%;margin-bottom: 10px">
         <table>
                    <tr>
                        <td>Programa:
                            <se:select name="idepgm" title="Programa" disabled="disabled" filter="<%= pgms%>"  change="changePgm()"/>
                        </td>
                    </tr>
                    <tr>
                       <td align="left"><input   type="checkbox" id="chkprf"  checked name="chkprf" size="60" value="1" maxlength="60" title="Copiar docentes" >En la copia incluir docentes</td>
                    </tr>
                    <tr>
                       <td align="left"><input   type="checkbox" id="chkhro"  checked name="chkhro" size="60" value="1" maxlength="60" title="Copiar horarios" >En la copia incluir horarios</td>
                    </tr>
           </table>
        </td>
      <td style="width: 50%;margin-bottom: 10px">
         <table>
            <tr>
              <td>
                  <textarea rows="5" cols="70" id="explica" name="explica" title="De interes"  maxlength="131"  disabled="disabled" ><%= text %></textarea>
              </td>
             </tr>
         </table>
       </td>
     </tr>
                    <tr>
                        <td colspan="2">
                          <div id="divSemester"></div>
                        </td>
                    </tr>
 </table>
                      <input type="hidden" name="smtpsm" id="smtpsm" value="">
                    </div>    
</div>    

                    <div id="COURSES" title="Asignaturas" data-parent="DETAILS">
                        <table style="width: 100%;">
<!--                          <tr>
                           <td>
                             <table style="width: 100%;">
                               <tr>
                                 <td align="right">
                                    <label class="label">
                                        Cursos :
                                    </label>
                                 </td>
                                 <td>
                                    <input type = "text" class="text" id = "nrocrs" size = "10" name = "nrocrs" value = ""/>
                                 </td>
                               </tr>
                            </table>
                           </td>
                          </tr>					 						  	 					  	 -->
                          <tr>
                           <td colspan="2" align="center">
                               <div id="divCourses"></div>
                           </td>
                          </tr>
                          </table>
                    </div>                        

                    <div id="PROGRAM" title="Programadas" data-parent="DETAILS">
                         <div id="divProgrammer"></div>
                    </div>                        
                    <div id="DELETE" title="Proyecciones" data-parent="DETAILS">
                         <div id="divDelete"></div>
                    </div>                        
                </div>
               <input type="hidden" name="idesmt" id="idesmt" value="">
        </div>
    </body>
</html>
