<%-- 
    Document   : formAcademicPlanningManager
    Created on : 13/07/2016 06:14:36 PM
    Author     : Carlos Pinto Jimenez ( Cl-Sma )
--%>

<%@page import="mvc.controller.AdmAcademicProgramRequestAction"%>
<%@include file="/vista/formPageIncludes.jsp" %>
<%    String usable = Util.getStrRequest("usable", request);
    usable = AdmAcademicProgramRequestAction.validRol(model, usable);
    String cal = AdmAcademicProgramRequestAction.validCalendar(model);
    String text = "La diferencia entre \"Proyectar\" y \"Copiar\": La proyección se hace con base en la demanda de los cursos anteriores y reformas de pensum, la copia se duplicar lo que se tenía el período anterior al nuevo";
    String codcia = model.getCodCia();
%>
<html>
    <head>
        <script src="<%=clsma.getBASE()%>/vista/js/scheduleDraw.js?v=d" charset="UTF-8"></script>
        <script src="<%=request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/") + 1)%>js/formAcademicProgramRequest.js?v=ssss" charset="UTF-8"></script>
        <title>Programación Academica</title>
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
        <script>
            clsma.hasAccess = <%=usable%>;
            clsma.hasCalendar = <%=cal%>;
        </script>
    </head>
    <body>        
        <div id="panel"  title="Programación Académica">
            <div class="advise">Programación Académica del año y periodo</div>
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
                <!--                <div id="DKS" title="Departamentos"></div>-->
                <div id="PGMS" title="Programas"></div>
                <div id="SMTCRS" title="Semestres y Cursos"></div>
                <div id="LBR" title="Cursos Libres"></div>
                <div id="ELC" title="Electivas y Énfasis"></div>
                <div id="INT" title="Configuración Integradas">
                    <div id="tabInt">
                        <div id="LSTINT" title="Listado">
                            <div class="LSTINTE"></div>
                            <div class="BTTINTE" style="width: 100%;text-align: center">
                                <button id="newInteger">Nueva</button>
                            </div>

                        </div>
                        <div id="NEWINT" title="Detalle">
                            <table class="tabli" style="width: 100%">
                                <tr>
                                    <td>Asignatura Integrada</td>
                                    <td >
                                        <div id="integrada"></div>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td class="lstModule" colspan="6"></td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                                <tr style="display: none;" class="rowButtonInt">
                                    <td colspan="6" style="text-align: center">
                                        <button id="saveInt">Guardar Integradas</button>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="msg"></div>
                </div>
                <div id="DTL" title="Programación"></div>
                <div id="GRP" title="Grupo">
                    <in:input type="hidden" name="nropak" />
                    <in:input type="hidden" name="codpsm" />
                    <in:input type="hidden" name="nropss" />
                    <in:input type="hidden" name="mdlmat" />
                    <fieldset>
                        <legend>Datos de la asignatura</legend>
                        <table class="tabli">
                            <tr class="datapgm">
                                <td class="lblReq">
                                    Curso
                                </td>
                                <td colspan="8">
                                    <div id="curso"></div>
                                </td>

                            </tr>
                            <tr>
                                <td>
                                    Código: 
                                </td>
                                <td>
                                    <in:input name="codmat" type="text" readonly="true" title="Código Asignatura" size="13" disabled="true" />
                                </td>
                                <td class="lblReq">
                                    Asignatura
                                </td>
                                <td colspan="8">
                                    <div id="asignatura"></div>
                                </td>


                            </tr>
                            <tr>
                                <td>Grupo</td>
                                <td>
                                    <in:input type="text" name="codpak" size="4" valid="letranumero" readonly="true" disabled="true" title="Grupo" onvalidation="false"/> 
                                </td>
                                <td class="lblReq" >Seccional</td>
                                <td colspan="8">
                                    <div id="seccional"></div>
                                </td>
                            </tr>
                            <tr>

                                <td class="lblReq">Créditos</td>
                                <td>
                                    <in:input type="text" name="crdakd" size="4" valid="numeros" disabled="true"  title="Creditos" readonly="true" /> 
                                </td>
                                <td class="lblReq">Capacidad</td>
                                <td>
                                    <in:input type="text" name="kpcpak" size="4" valid="numeros" title="Capacidad" /> 
                                </td>
                            </tr>
                            <tr>
                                <td class="lblReq">
                                    Tipo de grupo
                                </td>
                                <td>
                                    <se:select name="tpopak" title="Tipo de grupo" filter="[#<Regular>-Regular#,#<Iregular>-Iregular#,#<Electiva>-Electiva#]" />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td >Fechas</td>
                                <td colspan="8"><hr></td>
                            </tr>
                            <tr>
                                <td class="lblReq">Inicio</td>
                                <td>
                                    <in:input type="text" date="true" name="bgnpak" title="Inicio grupo" dateopt="{'select':'setMin'}" />
                                </td>
                                <td class="lblReq">Fin</td>
                                <td>
                                    <in:input type="text" date="true" name="endpak" title="Fin grupo" dateopt="{'select':'setMax'}"/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td >Parciales</td>
                                <td colspan="8"><hr></td>
                            </tr>
                            <tr>
                                <td class="lblReq">
                                    1er.
                                </td>
                                <td>
                                    <in:input type="text" size="5" valid="numeros"  name="pjapak" title="Primer parcial" readonly="true" />
                                </td>
                                <td class="lblReq">
                                    2do.
                                </td>
                                <td>
                                    <in:input type="text" size="5" valid="numeros" name="pjbpak" title="Primer parcial" readonly="true"  />
                                </td>
                                <td class="lblReq">
                                    3er.
                                </td>
                                <td>
                                    <in:input type="text" size="5" valid="numeros" name="pjcpak" title="Primer parcial" readonly="true" />
                                </td>
                                <td class="lblReq">
                                    4to.
                                </td>
                                <td>
                                    <in:input type="text" size="5"  valid="numeros" name="pjdpak" title="Primer parcial" readonly="true" />
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Intensidad Horaria</td>
                                <td colspan="8"><hr></td>
                            </tr>
                            <tr>
                                <td class="lblReq">Teóricas</td>
                                <td>
                                    <in:input type="text" size="5"  valid="numeros" name="teomat" title="Horas teóricas" readonly="true"/>
                                </td>
                                <td class="lblReq">Prácticas</td>
                                <td>
                                    <in:input type="text" size="5"  valid="numeros" name="pramat" title="Horas Prácticas" readonly="true"/>
                                </td>
                                <td class="lblReq">Independientes</td>
                                <td>
                                    <in:input type="text" size="5"  valid="numeros" name="invmat" title="Horas Investigativas" readonly="true"/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                            <tr>
                                <td>Calificación</td>
                                <td colspan="8"><hr></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <in:input type="radio" name="knppak" filter="[#0-Cuantitativa#,#1-Cualitativa#]" title="Modo de calificación" onvalidation="false" value="0" />
                                    <in:input type="check" name="habmat" value="0" title="Habilitable" label="Habilitable"/>
                                    <in:input type="check" name="lblmat" value="0" title="Nivelable" label="Nivelable"/>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <br/>
                    <fieldset>
                        <legend>Datos del docente</legend>
                        <table class="tabli" style="width: 100%">
                            <tr>
                                <td>Docente del grupo</td>
                                <td style="width: 300px">
                                    <div id="docente"></div>
                                </td>
                                <td style="text-align: left">
                                    <button id="desprf">Limpiar Docente</button>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Tipo de horas :
                                </td>
                                <td colspan="3">
                                    <se:select name="tpohrs" filter="[#0-Seleccione#,#Tiempo Completo#,#Catedra-Cátedra#]" title="Tipo de horas Docente" />
                                    Tipo docente para la facultad :
                                    <se:select name="tpoprf" filter="[#0-Seleccione#,#ADS-Adscrito#,#SUM-Suministrado#]" title="Tipo de Docente para la facultad" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Horas semanales del docente :
                                </td>
                                <td colspan="3">
                                    <in:input type="text" name="hrsprf" valid="numeros" title="Horas semanales del docente" size="4" />
                                </td>
                            </tr>

                            <tr class="hraPrf" style="display: none">
                                <td></td>
                            </tr>
                            <tr class="hraPrf" style="display: none"> 
                                <td colspan="10" style="text-align: center">
                                    <fieldset>
                                        <legend>Horas del docente Principal</legend>
                                        <div class="tblPrfHra" style="display: inline-table;vertical-align: top;width: 30%"></div>
                                        <div class="ButtonSveHrs" style="text-align: left;display: inline-table;vertical-align: top;width: 30%">
                                            <button id="HrasPrf">Guardar Horas</button>
                                        </div>

                                    </fieldset>
                                </td>
                            </tr>
                            <tr class="hraPrf" style="display: none">
                                <td></td>
                            </tr>

                            <tr class="trPrfAdd" style="display: none">
                                <td></td>
                            </tr>
                            <tr class="trPrfAdd" style="display: none;vertical-align: top" >
                                <td colspan="8">
                                    <div id="tabsAd" style="width: 100%">
                                        <div id="LSTADD" title="Listado"></div>
                                        <div id="DTLADD" title="Detalle">
                                            <in:input type="hidden" name="nropxx" />
                                            <table>
                                                <tr>
                                                    <td>
                                                        Docente:
                                                    </td>
                                                    <td colspan="3">
                                                        <div id="docadicional"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="lblReq">
                                                        Inicio
                                                    </td>
                                                    <td>
                                                        <in:input type="text" name="bgnpxg" date="true" dateopt="{'mindate':'today'}" title="Inicio" />
                                                    </td>
                                                    <td class="lblReq">
                                                        Final
                                                    </td>
                                                    <td>
                                                        <in:input type="text" name="endpxg" date="true" dateopt="{'mindate':'today'}" title="Inicio" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="lblReq">
                                                        Horas Semanales docente
                                                    </td>
                                                    <td>
                                                        <in:input type="text" name="hrsprfAdd" title="Horas Semanales Docente" valid="numeros" size="5" />
                                                    </td>
                                                    <td class="lblReq">
                                                        Tipo de horas
                                                    </td>
                                                    <td>
                                                        <se:select name="tpohrsAdd" title="Tipo de horas" filter="[#Tiempo Completo#,#Cátedra#]" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="lblReq">
                                                        Estado
                                                    </td>
                                                    <td>
                                                        <se:select name="stdpxg" filter="[#Activo#,#Inactivo#]" title="Estado"/>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </td>
                                <td style="vertical-align: top;text-align: center">
                                    <button id="addPrf" >Docente Adicional</button>
                                    <button id="savePrfAdd" >Guardar Adicional</button>
                                </td>
                            </tr>

                        </table>
                    </fieldset>
                </div>
                <div id="HOR" title="Horarios">

                    <fieldset>
                        <legend>Establecer Horarios</legend>
                        <table style="width: 100%;" class="tabli">
                            <tr>
                                <td class="lblReq">Horario por</td>
                                <td>
                                    <se:select name="horby" filter="[#SEL-Seleccione#,#CRS-Curso#,#PRF-Docentes#,#MAT-Asignatura#,#ELC-Electivas#]" title="Horario por:" change="validHorario()"/>
                                </td>
                            </tr>
                            <tr class="hide hidecrs">
                                <td class="lblReq">
                                    Curso
                                </td>
                                <td>
                                    <div id="curseohor"></div>
                                </td>
                            </tr>
                            <tr class="hide hideprf">
                                <td class="lblReq">
                                    Docente
                                </td>
                                <td>
                                    <div id="docentehor"></div>
                                </td>
                            </tr>
                            <tr class="hide hidemat">
                                <td class="lblReq">
                                    Asignatura
                                </td>
                                <td>
                                    <div id="asignaturahor"></div>
                                </td>
                            </tr>
                            <tr class="hide hideelec">
                                <td class="lblReq">
                                    Electivas
                                </td>
                                <td>
                                    <div id="electivahor"></div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="text-align: center">
                                    <span style="color: red;font-weight: bolder">* A la hora de crear los horarios las fechas de los grupos viene de la fecha establecida en el curso.</span>
                                    <br/><span style="color: red;font-weight: bolder">* Los grupos ya creados vendran con las fechas establecidas por usted.</span>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                            </tr>
                        </table>
                        <div class="schedulecontainer"></div>
                    </fieldset>
                </div>
                <div id="RQS" title="Requerimientos"></div>
                <div id="MAT" title="Por asignaturas" data-parent="DTL"></div>
                <div id="PRF" title="Por docentes" data-parent="DTL"></div>
                <div id="SMT" title="Semestres" data-parent="SMTCRS">
                    <div id="tabsSmt">
                        <div id="LSTSMT" title="Listado de Semestres"></div>
                        <div id="DTLSMT" title="Detalle">
                            <fieldset>
                                <legend>Datos del Semestre</legend>
                                <table class="tabli">
                                    <tr>
                                        <td class="lblReq">Programa - pensum</td>
                                        <td>
                                            <div id="programa"></div>
                                            <in:input type="hidden" name="idepgm" />
                                        </td>
                                        <td class="lblReq">Semestre</td>
                                        <td>
                                            <div id="semestre"></div>
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </div>
                    </div>
                </div>
                <div id="CRS" title="Cursos" data-parent="SMTCRS">
                    <div id="tabsCrs">
                        <div id="LSTCRS" title="Listado de cursos"></div>
                        <div id="DTLCRS" title="Detalle">
                            <in:input type="hidden" name="idecrs" />
                            <fieldset>
                                <legend>Datos del curso</legend>
                                <table class="tabli">
                                    <tr>
                                        <td class="lblReq">Nombre</td>
                                        <td colspan="8">
                                            <in:input type="text" name="nomcrs" valid="letranumero" size="50" maxlength="50" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td >Código</td>
                                        <td colspan="3">
                                            <in:input type="text" name="codcrs" valid="letranumero" size="30" maxlength="50" disabled="true" title="Código urso"/>
                                        </td>
                                        <td >Número</td>
                                        <td colspan="8">
                                            <in:input type="text" name="nrocrs" valid="letranumero" size="4" maxlength="50" disabled="true" title="Número curso"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="lblReq">Semestre</td>
                                        <td class="htmlCombo">

                                        </td>
                                        <td class="lblReq"> 
                                            Capacidad
                                        </td>
                                        <td>
                                            <in:input  type="text" name="kpccrs" size="4" maxlength="3" valid="numeros" title="Capacidad curso"/>
                                        </td>
                                        <td class="lblReq">
                                            Tipo
                                        </td>
                                        <td>
                                            <se:select name="tpocrs" title="Tipo de curso" filter="[#Regular ..#,#Iregular#]" />
                                        </td>

                                    </tr>
                                    <tr>
                                        <td class="lblReq">Inicio</td>
                                        <td>
                                            <in:input name="bgncrs" title="Inicio curso"  type="text" date="true" />
                                        </td>
                                        <td class="lblReq">Fin</td>
                                        <td>
                                            <in:input name="endcrs" title="Fin curso" type="text" date="true"  />
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                            <fieldset>
                                <legend>Esquema de horarios</legend>
                                <table>
                                    <tr>
                                        <td>Esquema de horarios</td>
                                        <td>
                                            <se:select name="codhrf" filter="select distinct tipelm , memelm || ' - ' || tipelm memelm from smaelm where codelm = 'SCH' and tipelm != 'Sel.' order by memelm asc" title="Esquema de horarios" />
                                        </td>
                                    </tr>
                                </table>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
            <div id="msg"></div>
        </div>

        <div id="SEMESTERPROJECT" title="Semestre" data-parent="DETAILS" style="display:none">
            <div style="width: 100%;margin-bottom: 10px;text-align: center">
                <table style="display: inline-block" class="form-control">
                    <tr>
                        <td style="width: 50%;margin-bottom: 10px">
                            <table>                                
                                <tr>
                                    <td align="left"><input   type="checkbox" id="chkprf"  checked name="chkprf" size="60" value="1" maxlength="60" title="Copiar docentes" >
                                        <label for="chkprf">Incluir docentes</label>
                                        </td>
                                </tr>
                                <tr>
                                    <td align="left"><input   type="checkbox" id="chkhro"  checked name="chkhro" size="60" value="1" maxlength="60" title="Copiar horarios" >
                                    <label for="chkhro">Incluir horarios</label>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td style="width: 50%;margin-bottom: 10px">
                            <table>
                                <tr>
                                    <td>
                                        <textarea rows="5" cols="70" id="explica" name="explica" title="De interes"  maxlength="131"  disabled="disabled" ><%= text%></textarea>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div id="divSemesterProj"></div>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="smtpsmProj" id="smtpsmProj" value="">
            </div>    
        </div>   
    </body>
</html>
