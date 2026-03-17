package ex6;

public class FirstTimeDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double total) {
        double discount = total * 0.15;
        System.out.println("Áp dụng giảm giá 15% (lần đầu): " + discount);
        return total - discount;
    }
}