<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bo.CartHandler,ui.CartInfo,ui.CartItemInfo" %>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Cache-Control", "no-store");
    CartHandler cartHandler;
    synchronized (session) {
        cartHandler = (CartHandler) session.getAttribute("cart");
        if (cartHandler == null) {
            cartHandler = new CartHandler();
            session.setAttribute("cart", cartHandler);
        }
    }
    String error = null;
    if ("POST".equals(request.getMethod())) {
        try {
            String removeProductId = request.getParameter("removeProductId");
            String productIdText = removeProductId != null ? removeProductId : request.getParameter("productId");
            int productId = Integer.parseInt(productIdText);
            if (productId <= 0) {
                throw new NumberFormatException();
            }
            if (removeProductId != null) {
                cartHandler.removeProduct(productId);
            } else if (!cartHandler.addProduct(productId)) {
                response.setStatus(404);
                error = "Produkten finns inte.";
            }
            if (error == null) {
                response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/cart.jsp"));
                return;
            }
        } catch (NumberFormatException e) {
            response.setStatus(400);
            error = "Ogiltigt produktnummer.";
        } catch (IllegalStateException e) {
            application.log("Kunde inte uppdatera shoppingkorgen.", e);
            response.setStatus(500);
            error = "Shoppingkorgen kunde inte uppdateras. Försök igen senare.";
        }
    }
    CartInfo cartInfo = cartHandler.getCartInfo();
%>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Shoppingkorg Webshop</title>
</head>
<body>
    <h1>Shoppingkorg</h1>
    <%@ include file="/WEB-INF/views/navigation.jspf" %>
    <% if (error != null) { %>
        <p role="alert"><%= escapeHtml(error) %></p>
    <% } %>
    <% if (cartInfo.getItems().isEmpty()) { %>
        <p>Din shoppingkorg är tom.</p>
    <% } else { %>
        <table>
            <thead>
                <tr><th scope="col">Produkt</th><th scope="col">Styckpris</th><th scope="col">Antal</th><th scope="col">Ändra</th></tr>
            </thead>
            <tbody>
                <% for (CartItemInfo item : cartInfo.getItems()) { %>
                    <tr>
                        <td><%= escapeHtml(item.getProductName()) %></td>
                        <td><%= formatPrice(item.getUnitPrice()) %></td>
                        <td><%= item.getQuantity() %></td>
                        <td>
                            <form method="post" action="cart.jsp">
                                <input type="hidden" name="removeProductId" value="<%= item.getProductId() %>">
                                <button type="submit">Ta bort</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <p>Totalt: <strong><%= formatPrice(cartInfo.getTotalPrice()) %></strong></p>
    <% } %>
</body>
</html>
