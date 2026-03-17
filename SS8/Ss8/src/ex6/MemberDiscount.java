package ex6;


public class MemberDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double total) {
        double discount = total * 0.05;
        System.out.println("Áp dụng giảm giá 5% cho thành viên: " + discount);
        return total - discount;
    }
}