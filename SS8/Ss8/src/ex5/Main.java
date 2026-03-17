package ex5;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        TemperatureSensor sensor = new TemperatureSensor();

        sensor.attach(fan);
        sensor.attach(ac);

        Command sleepCommand = new SleepModeCommand(light, fan, ac);

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Kích hoạt chế độ ngủ");
            System.out.println("2. Thay đổi nhiệt độ");
            System.out.println("3. Xem trạng thái thiết bị");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sleepCommand.execute();
                    break;

                case 2:
                    System.out.print("Nhập nhiệt độ: ");
                    int temp = sc.nextInt();
                    sensor.setTemperature(temp);
                    break;

                case 3:
                    System.out.println("Đèn: " + light.getStatus());
                    System.out.println("Quạt: " + fan.getStatus());
                    System.out.println("Điều hòa: " + ac.getTemperature());
                    break;

                case 0:
                    System.exit(0);
            }
        }
    }
}