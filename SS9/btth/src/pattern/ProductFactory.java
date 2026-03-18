package pattern;


import entity.Product;
import entity.PhysicalProduct;
import entity.DigitalProduct;

public class ProductFactory {

    public static Product createProduct(int productType, String id, String name, double price, double attribute) {
        switch (productType) {
            case 1:
                return new PhysicalProduct(id, name, price, attribute);
            case 2:
                return new DigitalProduct(id, name, price, attribute);
            default:
                throw new IllegalArgumentException("Loại sản phẩm không hợp lệ. Vui lòng chọn 1 hoặc 2.");
        }
    }

    public static Product createPhysicalProduct(String id, String name, double price, double weight) {
        return new PhysicalProduct(id, name, price, weight);
    }

    public static Product createDigitalProduct(String id, String name, double price, double size) {
        return new DigitalProduct(id, name, price, size);
    }
}
