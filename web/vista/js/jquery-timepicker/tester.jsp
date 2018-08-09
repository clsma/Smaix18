<%-- 
    Document   : tester
    Created on : 2/09/2014, 03:39:56 PM
    Author     : efrain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../../../WEB-INF/InitModel.jsp" %>
<!DOCTYPE html>
<html>
    <head>
  <meta charset='utf-8'>

  <title>Timepicker for jQuery &ndash; Demos and Documentation</title>
  <meta name="description" content="A lightweight, customizable jQuery timepicker plugin inspired by Google Calendar. Add a user-friendly javascript timepicker dropdown to your app in minutes." />

   <%=SCRIPT_NORMAL%>
  
  <script type="text/javascript" src="jquery.timepicker.js"></script>
  <link rel="stylesheet" type="text/css" href="jquery.timepicker.css" />

  <script type="text/javascript" src="lib/bootstrap-datepicker.js"></script>
  <link rel="stylesheet" type="text/css" href="lib/bootstrap-datepicker.css" />

  <script type="text/javascript" src="lib/site.js"></script>
  <link rel="stylesheet" type="text/css" href="lib/site.css" />

</head>
    <body>
         <section id="examples">
        <article>
            <div class="demo">
                <h2>Basic Example</h2>
                <p><input id="basicExample" type="text" class="time" /></p>
            </div>

            <script>
                $(function() {
                    $('#basicExample').timepicker({ 'step': 15 });
                });
            </script>

            <pre class="code" data-language="javascript">$('#basicExample').timepicker();</pre>
        </article>

    </section>
    </body>
</html>
