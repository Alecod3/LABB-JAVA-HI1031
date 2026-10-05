<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bo.UserHandler,ui.UserInfo" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Cache-Control", "no-store");
    if (session.getAttribute("user") != null) {
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/products.jsp"));
        return;
    }
    String error = null;
    String username = request.getParameter("username");
    if ("POST".equals(request.getMethod())) {
        try {
            UserInfo user = new UserHandler().login(username, request.getParameter("password"));
            if (user != null) {
                session.setAttribute("user", user);
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/products.jsp"));
                return;
            }
            error = "Fel användarnamn eller lösenord.";
        } catch (IllegalStateException e) {
            application.log("Inloggningen kunde inte genomföras.", e);
            response.setStatus(500);
            error = "Inloggningen fungerar inte just nu. Försök igen senare.";
        }
    }
%>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Logga in Webshop</title>
</head>
<body>
    <h1>Logga in</h1>
    <%@ include file="/WEB-INF/views/navigation.jspf" %>
    <% if (error != null) { %>
        <p role="alert"><%= escapeHtml(error) %></p>
    <% } %>
    <form method="post" action="login.jsp">
        <p><label for="username">Användarnamn</label><br>
            <input id="username" name="username" maxlength="50" autocomplete="username" required value="<%= escapeHtml(username) %>"></p>
        <p><label for="password">Lösenord</label><br>
            <input id="password" name="password" type="password" maxlength="1024" autocomplete="current-password" required></p>
        <button type="submit">Logga in</button>
    </form>
</body>
</html>
