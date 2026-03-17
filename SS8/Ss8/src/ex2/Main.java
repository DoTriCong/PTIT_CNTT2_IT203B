package ex2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        OldThermometer oldThermometer = new OldThermometer();

        TemperatureSensor adapter =
                new ThermometerAdapter(oldThermometer);

        SmartHomeFacade home = new SmartHomeFacade(adapter);

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem nhiệt độ");
            System.out.println("2. Chế độ rời nhà");
            System.out.println("3. Chế độ ngủ");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    home.getCurrentTemperature();
                    break;

                case 2:
                    home.leaveHome();
                    break;

                case 3:
                    home.sleepMode();
                    break;

                case 0:
                    System.exit(0);
            }
        }
    }
}
