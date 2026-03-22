package ex5.presentation;

import java.util.Scanner;
import ex5.business.DoctorService;
import ex5.model.Doctor;
public class MainApp {
    public static void main(String[] args) {
        DoctorService service = new DoctorService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MENU QUẢN LÝ BÁC SĨ =====");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ mới");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    service.showDoctors();
                    break;
                case 2:
                    System.out.print("Nhập mã bác sĩ: ");
                    String id = sc.nextLine();
                    System.out.print("Nhập họ tên: ");
                    String name = sc.nextLine();
                    System.out.print("Nhập chuyên khoa: ");
                    String specialty = sc.nextLine();
                    service.addDoctor(new Doctor(id, name, specialty));
                    break;
                case 3:
                    service.statisticSpecialty();
                    break;
                case 4:
                    System.out.println(" Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
