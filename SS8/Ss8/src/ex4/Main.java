package ex4;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        TemperatureSensor sensor = new TemperatureSensor();

        Fan fan = new Fan();
        Humidifier humidifier = new Humidifier();

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng ký quạt");
            System.out.println("2. Đăng ký máy tạo ẩm");
            System.out.println("3. Set nhiệt độ");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sensor.attach(fan);
                    System.out.println("Quạt: Đã đăng ký nhận thông báo");
                    break;

                case 2:
                    sensor.attach(humidifier);
                    System.out.println("Máy tạo ẩm: Đã đăng ký");
                    break;

                case 3:
                    System.out.print("Nhập nhiệt độ: ");
                    int temp = sc.nextInt();

                    sensor.setTemperature(temp);
                    break;

                case 0:
                    System.exit(0);
            }
        }
    }
}