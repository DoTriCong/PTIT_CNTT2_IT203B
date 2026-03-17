package ex6;
public class WebsiteDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double total) {
        double discount = total * 0.1;
        System.out.println("Áp dụng giảm giá 10%: " + discount);
        return total - discount;
    }
}
