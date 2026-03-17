package ex1;

public class Fan implements Device {

    @Override
    public void turnOn() {
        System.out.println("Quạt: Đang chạy.");
    }

    @Override
    public void turnOff() {
        System.out.println("Quạt: Tắt.");
    }
}