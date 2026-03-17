package ex6;

public interface SalesChannelFactory {

    DiscountStrategy createDiscountStrategy();

    PaymentMethod createPaymentMethod();

    NotificationService createNotificationService();
}
