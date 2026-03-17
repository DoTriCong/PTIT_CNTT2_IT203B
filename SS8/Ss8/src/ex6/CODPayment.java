package ex6;

public class CODPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Thanh toán khi nhận hàng: " + amount);
    }
}