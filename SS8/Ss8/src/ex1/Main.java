package ex1;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Device> devices = new ArrayList<>();

        HardwareConnection connection = null;

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Kết nối phần cứng");
            System.out.println("2. Tạo thiết bị mới");
            System.out.println("3. Bật thiết bị");
            System.out.println("4. Tắt thiết bị");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    connection = HardwareConnection.getInstance();
                    connection.connect();
                    break;

                case 2:
                    System.out.println("Chọn loại:");
                    System.out.println("1. Đèn");
                    System.out.println("2. Quạt");
                    System.out.println("3. Điều hòa");

                    int type = sc.nextInt();

                    DeviceFactory factory = null;

                    if (type == 1) factory = new LightFactory();
                    if (type == 2) factory = new FanFactory();
                    if (type == 3) factory = new AirConditionerFactory();

                    Device device = factory.createDevice();
                    devices.add(device);

                    break;

                case 3:
                    System.out.println("Chọn thiết bị:");

                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println((i + 1));
                    }

                    int onIndex = sc.nextInt() - 1;
                    devices.get(onIndex).turnOn();
                    break;

                case 4:
                    System.out.println("Chọn thiết bị:");

                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println((i + 1));
                    }

                    int offIndex = sc.nextInt() - 1;
                    devices.get(offIndex).turnOff();
                    break;

                case 0:
                    System.exit(0);
            }
        }
    }
}
