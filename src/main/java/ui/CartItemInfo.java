package ui;

import java.math.BigDecimal;

public final class CartItemInfo {

    private final int productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;

    public CartItemInfo(int productId, String productName, BigDecimal unitPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
