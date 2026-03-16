package btvn;

import java.util.*;

public class Ex5 {

    static class Product {
        String id;
        String name;
        double price;
        String category;

        Product(String id, String name, double price, String category) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.category = category;
        }
    }

    static class Customer {
        String name;
        String email;
        String phone;

        Customer(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }
    }

    static class OrderItem {
        Product product;
        int quantity;

        OrderItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        double getTotal() {
            return product.price * quantity;
        }
    }

    static class Order {
        String id;
        Customer customer;
        List<OrderItem> items = new ArrayList<>();
        double total;
        double discount;
        double finalAmount;

        Order(String id, Customer customer) {
            this.id = id;
            this.customer = customer;
        }
    }

    interface DiscountStrategy {
        double calculate(double total);

        String getName();
    }

    static class PercentageDiscount implements DiscountStrategy {
        double percent;

        PercentageDiscount(double percent) {
            this.percent = percent;
        }

        public double calculate(double total) {
            return total * percent / 100;
        }

        public String getName() {
            return percent + "%";
        }
    }

    static class FixedDiscount implements DiscountStrategy {
        double amount;

        FixedDiscount(double amount) {
            this.amount = amount;
        }

        public double calculate(double total) {
            return amount;
        }

        public String getName() {
            return "Fixed " + amount;
        }
    }

    static class HolidayDiscount implements DiscountStrategy {
        public double calculate(double total) {
            return total * 0.2;
        }

        public String getName() {
            return "Holiday 20%";
        }
    }

    interface PaymentMethod {
        void pay(double amount);

        String getName();
    }

    static class CODPayment implements PaymentMethod {
        public void pay(double amount) {
            System.out.println("Thanh toan COD: " + amount);
        }

        public String getName() {
            return "COD";
        }
    }

    static class CreditCardPayment implements PaymentMethod {
        public void pay(double amount) {
            System.out.println("Thanh toan the tin dung: " + amount);
        }

        public String getName() {
            return "Credit Card";
        }
    }

    static class MomoPayment implements PaymentMethod {
        public void pay(double amount) {
            System.out.println("Thanh toan MoMo: " + amount);
        }

        public String getName() {
            return "MoMo";
        }
    }

    static class VNPayPayment implements PaymentMethod {
        public void pay(double amount) {
            System.out.println("Thanh toan VNPay: " + amount);
        }

        public String getName() {
            return "VNPay";
        }
    }

    interface OrderRepository {
        void save(Order order);

        List<Order> findAll();
    }

    static class FileOrderRepository implements OrderRepository {
        List<Order> orders = new ArrayList<>();

        public void save(Order order) {
            orders.add(order);
            System.out.println("Da luu don hang " + order.id);
        }

        public List<Order> findAll() {
            return orders;
        }
    }

    interface NotificationService {
        void send(String message, String target);
    }

    static class EmailNotification implements NotificationService {
        public void send(String message, String target) {
            System.out.println("Da gui email: " + message);
        }
    }

    static class SMSNotification implements NotificationService {
        public void send(String message, String target) {
            System.out.println("Da gui SMS: " + message);
        }
    }

    static class InvoiceGenerator {
        void print(Order order) {
            System.out.println("=== HOA DON ===");
            System.out.println("Khach: " + order.customer.name);
            for (OrderItem i : order.items) {
                System.out.println(i.product.name + " - SL: " + i.quantity + " - Gia: " + i.product.price);
            }
            System.out.println("Tong: " + order.total);
            System.out.println("Giam gia: " + order.discount);
            System.out.println("Can thanh toan: " + order.finalAmount);
        }
    }

    static class OrderService {
        OrderRepository repository;
        NotificationService notification;

        OrderService(OrderRepository repository, NotificationService notification) {
            this.repository = repository;
            this.notification = notification;
        }

        void placeOrder(Order order) {
            repository.save(order);
            notification.send("Don hang " + order.id + " da duoc tao", order.customer.email);
        }
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            List<Product> products = new ArrayList<>();
            List<Customer> customers = new ArrayList<>();
            List<DiscountStrategy> discounts = new ArrayList<>();
            List<PaymentMethod> payments = new ArrayList<>();

            discounts.add(new PercentageDiscount(10));
            discounts.add(new HolidayDiscount());

            payments.add(new CODPayment());
            payments.add(new CreditCardPayment());
            payments.add(new MomoPayment());
            payments.add(new VNPayPayment());

            OrderRepository repo = new FileOrderRepository();
            NotificationService notify = new EmailNotification();
            OrderService orderService = new OrderService(repo, notify);
            InvoiceGenerator invoice = new InvoiceGenerator();

            int orderCount = 1;

            while (true) {
                System.out.println("1.Them san pham");
                System.out.println("2.Them khach hang");
                System.out.println("3.Tao don hang");
                System.out.println("4.Xem don hang");
                System.out.println("5.Tinh doanh thu");
                System.out.println("6.Them thanh toan moi");
                System.out.println("7.Them giam gia moi");
                System.out.println("0.Thoat");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 0)
                    break;

                if (choice == 1) {
                    products.add(new Product("SP01", "Laptop", 15000000, "Dien tu"));
                    System.out.println("Da them san pham SP01");
                }

                if (choice == 2) {
                    customers.add(new Customer("Nguyen Van A", "a@example.com", "0123456789"));
                    System.out.println("Da them khach hang");
                }

                if (choice == 3) {
                    Order order = new Order("ORD00" + orderCount++, customers.get(0));
                    order.items.add(new OrderItem(products.get(0), 1));
                    order.total = order.items.get(0).getTotal();
                    DiscountStrategy d = discounts.get(0);
                    order.discount = d.calculate(order.total);
                    order.finalAmount = order.total - order.discount;
                    invoice.print(order);
                    payments.get(1).pay(order.finalAmount);
                    orderService.placeOrder(order);
                }

                if (choice == 4) {
                    for (Order o : repo.findAll()) {
                        System.out.println(o.id + " - " + o.customer.name + " - " + o.finalAmount);
                    }
                }

                if (choice == 5) {
                    double sum = 0;
                    for (Order o : repo.findAll())
                        sum += o.finalAmount;
                    System.out.println("Tong doanh thu: " + sum);
                }

                if (choice == 6) {
                    payments.add(new PaymentMethod() {
                        public void pay(double amount) {
                            System.out.println("Thanh toan ZaloPay: " + amount);
                        }

                        public String getName() {
                            return "ZaloPay";
                        }
                    });
                    System.out.println("Da them phuong thuc thanh toan ZaloPay");
                }

                if (choice == 7) {
                    discounts.add(new PercentageDiscount(20));
                    System.out.println("Da them giam gia VIP 20%");
                }
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}