<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    response.setHeader("Cache-Control", "no-store");
    session.invalidate();
    response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/index.jsp"));
%>
