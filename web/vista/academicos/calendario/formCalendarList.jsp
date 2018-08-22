<%@ page session="true" %>  
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> 
<%@ include file="/vista/formValidSessionUser.jsp" %>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="../../../WEB-INF/InitModel.jsp" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 


<fieldset>
    <input type="text" id="quickfind" class="text" style="display: none">

    <display:table name="requestScope.lstKls" id="lstKls" style="width: 100%" class="displaytable"> 
        <display:setProperty name="basic.empty.showtable" value="true" />	 	

        <display:column title="Código"                          
                        media="HTML"
                        style="text-align: center;">            
            <a href="#" title="Código" onclick="showCalendar('<c:out value='${lstKls.CODKLS}' />')"><c:out value='${lstKls.CODKLS}' /></a>
        </display:column>

        <display:column title="Nombre"  
                        property="NOMKLS"                         
                        group="2"
                        sortable="false">
        </display:column> 

        <display:column title="Tipo"  
                        property="TPOKLS" 
                        sortable="false">
        </display:column> 

        <display:column title="Año"  
                        property="AGNPRS" 
                        sortable="false"
                        style="text-align: center;">
        </display:column> 


        <display:column title="Periodo"  
                        property="PRDPRS" 
                        sortable="false"
                        style="text-align: center;">
        </display:column> 
        
    </display:table>
</fieldset>