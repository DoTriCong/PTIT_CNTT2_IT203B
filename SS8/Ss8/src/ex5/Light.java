package ex5;

public class Light {

    private boolean on = true;

    public void off() {
        on = false;
        System.out.println("Đèn: Tắt");
    }

    public String getStatus() {
        return on ? "Bật" : "Tắt";
    }
}