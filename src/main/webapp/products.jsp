<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bo.ProductHandler,ui.ProductInfo,java.util.List,java.util.Collections" %>
<%
    response.setHeader("Cache-Control", "no-store");
    List<ProductInfo> products = Collections.emptyList();
    String error = null;
    try {
        products = new ProductHandler().getProducts();
    } catch (IllegalStateException e) {
        application.log("Kunde inte hämta produktlistan.", e);
        response.setStatus(500);
        error = "Produkterna kunde inte hämtas. Försök igen senare.";
    }
%>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Produkter Webshop</title>
</head>
<body>
    <h1>Produkter</h1>
    <%@ include file="/WEB-INF/views/navigation.jspf" %>
    <% if (error != null) { %>
        <p role="alert"><%= escapeHtml(error) %></p>
    <% } else if (products.isEmpty()) { %>
        <p>Det finns inga produkter att visa.</p>
    <% } else { %>
        <table>
            <thead>
                <tr><th scope="col">Namn</th><th scope="col">Beskrivning</th><th scope="col">Pris</th><th scope="col">Köp</th></tr>
            </thead>
            <tbody>
                <% for (ProductInfo product : products) { %>
                    <tr>
                        <td><%= escapeHtml(product.getName()) %></td>
                        <td><%= escapeHtml(product.getDescription()) %></td>
                        <td><%= formatPrice(product.getPrice()) %></td>
                        <td>
                            <form method="post" action="cart.jsp">
                                <input type="hidden" name="productId" value="<%= product.getId() %>">
                                <button type="submit">Lägg i korgen</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>
</body>
</html>
