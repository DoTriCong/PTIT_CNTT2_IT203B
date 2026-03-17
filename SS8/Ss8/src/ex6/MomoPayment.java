package ex6;

public class MomoPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + amount);
    }
}