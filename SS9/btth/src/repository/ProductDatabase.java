package repository;

import entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> products;

    private ProductDatabase() {
        products = new ArrayList<>();
    }

    public static ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

    public void addProduct(Product product) {
        if (product != null && !isProductIdExists(product.getId())) {
            products.add(product);
            System.out.println("Thêm sản phẩm thành công");
        } else {
            System.out.println("Không thể thêm sản phẩm");
        }
    }

    public boolean updateProduct(String id, Product updatedProduct) {
        if (updatedProduct == null || !id.equals(updatedProduct.getId())) {
            System.out.println("Ko thể nhập dữ liệu");
            return false;
        }

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.set(i, updatedProduct);
                System.out.println("Cập nhật sản phẩm thành công");
                return true;
            }
        }
        System.out.println("Không tìm thấy sản phẩm này ");
        return false;
    }

    public boolean deleteProduct(String id) {
        boolean removed = products.removeIf(product -> product.getId().equals(id));
        if (removed) {
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            System.out.println("khong tim thay ID " + id + " not found.");
        }
        return removed;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(String id) {
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return product.orElse(null);
    }

    private boolean isProductIdExists(String id) {
        return products.stream().anyMatch(product -> product.getId().equals(id));
    }

    public void displayAllProducts() {
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
            return;
        }

        for (Product product : products) {
            product.displayInfo();
            System.out.println("-------------------");
        }
    }
}