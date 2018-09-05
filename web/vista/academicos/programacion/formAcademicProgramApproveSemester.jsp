<%@ page session="true"%>
<%@ page import="java.util.List,java.util.LinkedList,java.util.Hashtable"%>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="../WEB-INF/InitModel.jsp"%>
<%@ include file="/vista/formValidSessionUser.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/tagForm.tld" prefix="TagControl" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>

<display:table name="sessionScope.lstSem_" id="lstSEM"    style="width: 95%" > 
    <display:setProperty name="basic.empty.showtable" value="true" />	 		 

    <display:column title="Semestre" 
                    style="width:50; text-align:center"
                    media="html"
                    property="smtpsm" 
                    group="1"
                    sortable="false">				

    </display:column> 

    <display:column title="Codmat"  
                    property="codmat" 
                    style="text-align:center"
                    sortable="false">
    </display:column> 

    <display:column title="Materia"  
                    property="nommat" 
                    style="text-align:left"
                    sortable="false">
    </display:column> 

    <display:column title="Grupo" 
                    style="text-align:left"
                    property="nrogrp"
                    sortable="false">				
    </display:column>
    <display:column title="Docente" 
                    style="text-align:left"
                    property="nomprf"
                    sortable="false">				
    </display:column>
    <display:column title="Horas" 
                    style="text-align:right"
                    property="numhrs"
                    sortable="false">				
    </display:column>
</display:table>

</html>