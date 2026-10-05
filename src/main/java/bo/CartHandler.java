package bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.ProductDB;
import ui.CartInfo;
import ui.CartItemInfo;

public class CartHandler {

    private final Cart cart = new Cart();
    private final ProductDB productDB = new ProductDB();

    public synchronized boolean addProduct(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Ogiltigt produkt-id.");
        }
        try {
            Product product = productDB.findById(productId);
            if (product == null) {
                return false;
            }
            cart.addProduct(product);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException("Kunde inte lägga produkten i korgen.", e);
        }
    }

    public synchronized void removeProduct(int productId) {
        if (productId <= 0) {
            throw new IllegalArgumentException("Ogiltigt produkt-id.");
        }
        cart.removeProduct(productId);
    }
    public synchronized CartInfo getCartInfo() {
        List<CartItemInfo> items = new ArrayList<CartItemInfo>();
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            items.add(new CartItemInfo(product.getId(), product.getName(),
                    product.getPrice(), item.getQuantity()));
        }
        return new CartInfo(items, cart.getTotalPrice());
    }
}
