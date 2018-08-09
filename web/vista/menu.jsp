<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List,java.util.Hashtable,java.util.LinkedList,mvc.model.Util,java.io.File,java.util.GregorianCalendar,java.util.Calendar,mvc.model.Smabas"%> 
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="../WEB-INF/InitModel.jsp"%>
<%@ include file="/vista/formValidSessionUser.jsp" %>
<%@ page import="java.util.StringTokenizer"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
	<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1' />
	<link rel='stylesheet' type='text/css' href='styles.css' />
	<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
	<script language="JavaScript" type="text/JavaScript" src="<%= BASEURL%>/vista/script/menu_jquery.js"></script>
</head>
<body>
<div id='cssmenu'>
<ul>
   <li class='active'><a href='index.html'><span>Home</span></a></li>
   <li class='has-sub'><a href='#'><span>Products</span></a>
      <ul>
         <li class='has-sub'><a href='#'><span>Product 1</span></a>
            <ul>
               <li><a href='#'><span>Sub Item</span></a></li>
               <li class='last'><a href='#'><span>Sub Item</span></a></li>
            </ul>
         </li>
         <li class='has-sub'><a href='#'><span>Product 2</span></a>
            <ul>
               <li><a href='#'><span>Sub Item</span></a></li>
               <li class='last'><a href='#'><span>Sub Item</span></a></li>
            </ul>
         </li>
      </ul>
   </li>
   <li><a href='#'><span>About</span></a></li>
   <li class='last'><a href='#'><span>Contact</span></a></li>
</ul>
</div>
</body>
</html>