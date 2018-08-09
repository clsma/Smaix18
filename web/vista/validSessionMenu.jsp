<%-- 
    Document   : validSessionMenu
    Created on : 13/01/2016, 02:43:57 PM
    Author     : Cl-sma
--%>

<%

    String user = request.getRemoteAddr();
     String sql_ = "sma_system_manager.state_session('" + model.getSessionSxu() + "')";
    sql_ = model.callFunctionOrProcedure(sql_);

    if (application.getAttribute(user) != null && application.getAttribute("sessxu_" + model.getCodPrs()) != null && !sql_.equals("OUT")) {
        response.sendRedirect(request.getContextPath() + "/vista/mainMenu.jsp");
    } else {
        application.removeAttribute(user);
    }

%>
