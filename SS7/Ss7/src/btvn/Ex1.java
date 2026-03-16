package btvn;

import java.util.ArrayList;
import java.util.List;

public class Ex1 {

    static class Product {
        private String id;
        private String name;
        private double price;

        public Product(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    static class Customer {
        private String name;
        private String email;
        public Customer(String name, String email, String address) {
            this.name = name;
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }
    }

    static class OrderItem {
        private Product product;
        private int quantity;

        public OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public double getTotalPrice() {
            return product.getPrice() * quantity;
        }
    }

    static class Order {
        private String orderId;
        private Customer customer;
        private List<OrderItem> items = new ArrayList<>();
        private double totalAmount;

        public Order(String orderId, Customer customer) {
            this.orderId = orderId;
            this.customer = customer;
        }

        public void addItem(OrderItem item) {
            items.add(item);
        }

        public List<OrderItem> getItems() {
            return items;
        }

        public String getOrderId() {
            return orderId;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }

    static class OrderCalculator {
        public double calculateTotal(Order order) {
            double total = 0;
            for (OrderItem item : order.getItems()) {
                total += item.getTotalPrice();
            }
            return total;
        }
    }

    static class OrderRepository {
        public void save(Order order) {
            System.out.println("Da luu don hang " + order.getOrderId());
        }
    }

    static class EmailService {
        public void sendOrderConfirmation(String email, String orderId) {
            System.out.println("Da gui email den " + email +
                    ": Don hang " + orderId + " da duoc tao");
        }
    }

    public static void main(String[] args) {

        Product p1 = new Product("SP01", "Laptop", 15000000);
        Product p2 = new Product("SP02", "Chuot", 300000);
        System.out.println("Tao san pham: SP01 - Laptop - 15000000, SP02 - Chuot - 300000");

        Customer customer = new Customer("Nguyen Van A", "a@example.com", "Ha Noi");
        System.out.println("Tao khach hang: Nguyen Van A - a@example.com");

        Order order = new Order("ORD001", customer);
        order.addItem(new OrderItem(p1, 1));
        order.addItem(new OrderItem(p2, 2));
        System.out.println("Don hang ORD001 duoc tao");

        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);
        order.setTotalAmount(total);
        System.out.println("Tong tien: " + total);

        OrderRepository repository = new OrderRepository();
        repository.save(order);

        EmailService emailService = new EmailService();
        emailService.sendOrderConfirmation(customer.getEmail(), order.getOrderId());
    }
}