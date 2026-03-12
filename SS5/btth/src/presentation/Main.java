package presentation;
import exception.InvalidProductException;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    static List<Product> productList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("========= PRODUCT MANAGEMENT SYSTEM =========");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Cập nhật số lượng theo ID");
            System.out.println("4. Xóa sản phẩm đã hết hàng");
            System.out.println("5. Thoát chương trình");
            System.out.print("Chọn: ");

            choice = Integer.parseInt(sc.nextLine());

            try {

                switch (choice) {

                    case 1:
                        addProduct();
                        break;

                    case 2:
                        displayProducts();
                        break;

                    case 3:
                        updateQuantity();
                        break;

                    case 4:
                        deleteOutOfStock();
                        break;

                    case 5:
                        System.out.println("Thoát chương trình...");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ!");

                }

            } catch (InvalidProductException e) {
                System.out.println("Lỗi: " + e.getMessage());
            }

        } while (choice != 5);

    }

    // CREATE
    public static void addProduct() throws InvalidProductException {

        System.out.print("Nhập ID: ");
        int id = Integer.parseInt(sc.nextLine());

        boolean exists = productList.stream()
                .anyMatch(p -> p.getId() == id);

        if (exists) {
            throw new InvalidProductException("ID đã tồn tại!");
        }

        System.out.print("Tên: ");
        String name = sc.nextLine();

        System.out.print("Giá: ");
        double price = Double.parseDouble(sc.nextLine());

        System.out.print("Số lượng: ");
        int quantity = Integer.parseInt(sc.nextLine());

        System.out.print("Category: ");
        String category = sc.nextLine();

        Product p = new Product(id, name, price, quantity, category);
        productList.add(p);

        System.out.println("Thêm sản phẩm thành công!");
    }

    // READ
    public static void displayProducts() {

        if (productList.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-10s %-15s\n",
                "ID", "Name", "Price", "Qty", "Category");

        productList.stream().forEach(p ->
                System.out.printf("%-5d %-15s %-10.2f %-10d %-15s\n",
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getQuantity(),
                        p.getCategory())
        );
    }

    // UPDATE (Optional)
    public static void updateQuantity() throws InvalidProductException {

        System.out.print("Nhập ID cần cập nhật: ");
        int id = Integer.parseInt(sc.nextLine());

        Optional<Product> productOptional = productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (!productOptional.isPresent()) {
            throw new InvalidProductException("Không tìm thấy sản phẩm!");
        }

        System.out.print("Nhập số lượng mới: ");
        int newQuantity = Integer.parseInt(sc.nextLine());

        productOptional.get().setQuantity(newQuantity);

        System.out.println("Cập nhật thành công!");
    }

    // DELETE
    public static void deleteOutOfStock() {

        long before = productList.size();

        productList.removeIf(p -> p.getQuantity() == 0);

        long after = productList.size();

        System.out.println("Đã xóa " + (before - after) + " sản phẩm hết hàng.");
    }

}