package ex5;


import java.util.Scanner;

public class ReceptionView {

    private final Scanner scanner;
    private final PatientController controller;

    public ReceptionView() {
        scanner = new Scanner(System.in);
        controller = new PatientController();
    }

    public void start() {
        while (true) {
            showMenu();
            int choice = inputMenuChoice();

            switch (choice) {
                case 1:
                    controller.showAvailableBeds();
                    break;
                case 2:
                    handleAdmitPatient();
                    break;
                case 3:
                    System.out.println("Thoat chuong trinh. Tam biet!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long chon lai.");
            }
        }
    }

    private void showMenu() {
        System.out.println(" HE THONG TIEP NHAN NOI TRU ");
        System.out.println("1. Xem tinh trang giuong benh");
        System.out.println("2. Tiep nhan benh nhan");
        System.out.println("3. Thoat");
        System.out.print("Nhap lua chon: ");
    }

    private int inputMenuChoice() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Vui long nhap so hop le: ");
            }
        }
    }

    private void handleAdmitPatient() {
        String patientName = inputPatientName();
        int age = inputAge();
        int bedId = inputBedId();
        double advanceAmount = inputAdvanceAmount();

        controller.admitPatient(patientName, age, bedId, advanceAmount);
    }

    private String inputPatientName() {
        while (true) {
            System.out.print("Nhap ten benh nhan: ");
            String name = scanner.nextLine().trim();

            if (!name.isEmpty()) {
                return name;
            }

            System.out.println("Ten benh nhan khong duoc de trong.");
        }
    }

    private int inputAge() {
        while (true) {
            try {
                System.out.print("Nhap tuoi: ");
                int age = Integer.parseInt(scanner.nextLine().trim());

                if (age > 0) {
                    return age;
                }

                System.out.println("Tuoi phai lon hon 0.");
            } catch (NumberFormatException e) {
                System.out.println("Tuoi phai la so nguyen hop le.");
            }
        }
    }

    private int inputBedId() {
        while (true) {
            try {
                System.out.print("Nhap ma giuong muon chon: ");
                int bedId = Integer.parseInt(scanner.nextLine().trim());

                if (bedId > 0) {
                    return bedId;
                }

                System.out.println("Ma giuong phai lon hon 0.");
            } catch (NumberFormatException e) {
                System.out.println("Ma giuong phai la so nguyen hop le.");
            }
        }
    }

    private double inputAdvanceAmount() {
        while (true) {
            try {
                System.out.print("Nhap so tien tam ung: ");
                double amount = Double.parseDouble(scanner.nextLine().trim());

                if (amount > 0) {
                    return amount;
                }

                System.out.println("So tien tam ung phai lon hon 0.");
            } catch (NumberFormatException e) {
                System.out.println("So tien phai la so hop le.");
            }
        }
    }
}