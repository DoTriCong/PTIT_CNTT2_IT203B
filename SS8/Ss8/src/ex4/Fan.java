package ex4;

public class Fan implements Observer {

    @Override
    public void update(int temperature) {

        if (temperature < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        }
        else if (temperature <= 25) {
            System.out.println("Quạt: Chạy tốc độ trung bình");
        }
        else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }
}