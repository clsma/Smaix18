<%@ page session="true"%>
<%@ page errorPage="/vista/errorPage.jsp"%>
<%@ include file="/WEB-INF/InitModel.jsp" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%

	//String BASEURL = request.getContextPath();
	String rows = "21%,20%,59%", pageMnu = "";
	String tpomnu = application.getInitParameter("typeMenu");
	if (tpomnu.equals("0")) {
		rows = "25%,75%";
		pageMnu = BASEURL + "/vista/Menus01.jsp";
	} else {
		rows = "23%,4%,59%";
		pageMnu = BASEURL + "/vista/Menus02.jsp";
	}
	
	pageMnu = BASEURL + "/vista/mainMenu.jsp";
%>
<html>
<head>
<title>SMA Plataforma VIRTUAL</title>
<link REL="SHORTCUT ICON" HREF="<%= BASEURL %>/vista/img/icono.ico">
<script language="JavaScript" type="text/javascript">  
  function justSize(){
   /* var sizeR = '21%,79%';
	
    if (screen.Width == 800)
      sizeR = '28%,72%';
    else if (screen.Width >= 1000)
      sizeR = '21%,79%';
    document.getElementById('frameRow').rows = sizeR;*/
  }
</script>
</head>

<c:redirect url="/vista/mainMenu.jsp"  ></c:redirect> 

</html>
