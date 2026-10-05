package bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.ProductDB;
import ui.ProductInfo;

public class ProductHandler {

    private final ProductDB productDB = new ProductDB();

    public List<ProductInfo> getProducts() {
        try {
            List<ProductInfo> products = new ArrayList<ProductInfo>();
            for (Product product : productDB.findAll()) {
                products.add(toInfo(product));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException("Kunde inte hämta produkterna.", e);
        }
    }


    private ProductInfo toInfo(Product product) {
        return new ProductInfo(product.getId(), product.getName(),
                product.getDescription(), product.getPrice());
    }
}
