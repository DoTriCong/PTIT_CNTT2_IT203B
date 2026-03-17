package ex5;

public class Fan implements Observer {

    private String speed = "Tắt";

    public void low() {
        speed = "Tốc độ thấp";
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    public void high() {
        speed = "Tốc độ mạnh";
        System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
    }

    public String getStatus() {
        return speed;
    }

    @Override
    public void update(int temperature) {

        if (temperature > 30) {
            high();
        }
    }
}