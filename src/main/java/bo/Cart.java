package bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartItem> items = new ArrayList<CartItem>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Produkten får inte vara null.");
        }

        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                item.increaseQuantity();
                return;
            }
        }

        items.add(new CartItem(product));
    }

    public void removeProduct(int productId) {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().getId() == productId) {
                if (item.getQuantity() > 1) {
                    item.decreaseQuantity();
                } else {
                    items.remove(i);
                }
                return;
            }
        }
    }
    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            BigDecimal itemTotal = item.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        return total;
    }
}
