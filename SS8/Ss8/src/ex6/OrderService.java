package ex6;


public class OrderService {

    private DiscountStrategy discount;
    private PaymentMethod payment;
    private NotificationService notification;

    public OrderService(SalesChannelFactory factory) {

        discount = factory.createDiscountStrategy();
        payment = factory.createPaymentMethod();
        notification = factory.createNotificationService();
    }

    public void createOrder(double price, int quantity) {

        double total = price * quantity;

        System.out.println("Tổng tiền: " + total);

        double finalAmount = discount.applyDiscount(total);

        payment.pay(finalAmount);

        notification.notifyUser();
    }
}