<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctx = request.getContextPath();

    String pageTitle = request.getParameter("pageTitle");
    if (pageTitle == null || pageTitle.isEmpty()) {
        pageTitle = "LicenseOps";
    }

    // e.g. "/css/app.css" or "/css/license.css"
    String pageCss = request.getParameter("pageCss");
%>
<head>
    <meta charset="UTF-8">
    <title><%= pageTitle %></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- ✅ CSRF token for JS-driven POST forms (required by Spring Security) -->
    <meta name="_csrf"            content="${_csrf.token}"/>
    <meta name="_csrf_param_name" content="${_csrf.parameterName}"/>

    <!-- ✅ Context path for building absolute action URLs in JS -->
    <meta name="ctx" content="<%= ctx %>"/>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">

    <!-- Icon font -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">

    <!-- Global layout CSS -->
    <link rel="stylesheet" href="<%= ctx %>/css/layout.css">

    <!-- Page-specific CSS (per JSP) -->
    <% if (pageCss != null && !pageCss.isEmpty()) { %>
        <link rel="stylesheet" href="<%= ctx + pageCss %>">
    <% } %>
</head>