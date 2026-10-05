package ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CartInfo {

    private final List<CartItemInfo> items;
    private final BigDecimal totalPrice;

    public CartInfo(List<CartItemInfo> items, BigDecimal totalPrice) {
        this.items = Collections.unmodifiableList(new ArrayList<CartItemInfo>(items));
        this.totalPrice = totalPrice;
    }

    public List<CartItemInfo> getItems() {
        return items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
}
