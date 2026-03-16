package btvn;

import java.util.ArrayList;
import java.util.List;

public class Ex4 {

    static class Order {
        private String orderId;

        public Order(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderId() {
            return orderId;
        }
    }

    interface OrderRepository {
        void save(Order order);

        List<Order> findAll();
    }

    interface NotificationService {
        void send(String message, String recipient);
    }

    static class FileOrderRepository implements OrderRepository {
        private List<Order> orders = new ArrayList<>();

        public void save(Order order) {
            orders.add(order);
            System.out.println("Luu don hang vao file: " + order.getOrderId());
        }

        public List<Order> findAll() {
            return orders;
        }
    }

    static class DatabaseOrderRepository implements OrderRepository {
        private List<Order> orders = new ArrayList<>();

        public void save(Order order) {
            orders.add(order);
            System.out.println("Luu don hang vao database: " + order.getOrderId());
        }

        public List<Order> findAll() {
            return orders;
        }
    }

    static class EmailNotification implements NotificationService {
        public void send(String message, String recipient) {
            System.out.println("Gui email: " + message);
        }
    }

    static class SMSNotification implements NotificationService {
        public void send(String message, String recipient) {
            System.out.println("Gui SMS: " + message);
        }
    }

    static class OrderService {
        private OrderRepository orderRepository;
        private NotificationService notificationService;

        public OrderService(OrderRepository orderRepository, NotificationService notificationService) {
            this.orderRepository = orderRepository;
            this.notificationService = notificationService;
        }

        public void createOrder(Order order) {
            orderRepository.save(order);
            notificationService.send("Don hang " + order.getOrderId() + " da duoc tao", "customer");
        }
    }

    public static void main(String[] args) {

        OrderRepository fileRepo = new FileOrderRepository();
        NotificationService emailService = new EmailNotification();
        OrderService orderService1 = new OrderService(fileRepo, emailService);

        Order order1 = new Order("ORD001");
        orderService1.createOrder(order1);

        OrderRepository dbRepo = new DatabaseOrderRepository();
        NotificationService smsService = new SMSNotification();
        OrderService orderService2 = new OrderService(dbRepo, smsService);

        Order order2 = new Order("ORD002");
        orderService2.createOrder(order2);
    }
}
