package ex5;

import java.sql.*;
import java.util.Scanner;

public class Ex5 {

    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/database",
                    "root",
                    "123456Â"
            );

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n=====  MENU =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Tiếp nhận bệnh nhân mới");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & Tính phí");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        listPatients();
                        break;
                    case 2:
                        addPatient(sc);
                        break;
                    case 3:
                        updateMedicalRecord(sc);
                        break;
                    case 4:
                        dischargeAndCalculate(sc);
                        break;
                    case 5:
                        System.out.println("Thoát chương trình.");
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1. Danh sách bệnh nhân
    private static void listPatients() {
        String sql = "SELECT id, name, age, department FROM patients";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- DANH SÁCH BỆNH NHÂN ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("department")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Tiếp nhận bệnh nhân mới (PreparedStatement chống SQL Injection)
    private static void addPatient(Scanner sc) {
        /*
         * Dùng PreparedStatement:
         * - Không nối chuỗi → tránh SQL Injection
         * - Xử lý được tên có dấu nháy: L'Oréal, D'Arcy
         */

        String sql = "INSERT INTO patients(name, age, department) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Tên: ");
            String name = sc.nextLine();

            System.out.print("Tuổi: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Khoa: ");
            String department = sc.nextLine();

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, department);

            ps.executeUpdate();

            System.out.println("Thêm bệnh nhân thành công!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Cập nhật bệnh án
    private static void updateMedicalRecord(Scanner sc) {
        String sql = "UPDATE patients SET disease = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            System.out.print("Nhập ID bệnh nhân: ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập bệnh lý mới: ");
            String disease = sc.nextLine();

            ps.setString(1, disease);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Xuất viện & Tính phí (CallableStatement + OUT parameter)
    private static void dischargeAndCalculate(Scanner sc) {
        /*
         * Stored Procedure:
         * CALCULATE_DISCHARGE_FEE(IN patient_id INT, OUT total_fee DECIMAL)
         *
         * Quy trình:
         * 1. set IN
         * 2. register OUT
         * 3. execute
         * 4. get OUT
         */

        String sql = "{call CALCULATE_DISCHARGE_FEE(?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {

            System.out.print("Nhập ID bệnh nhân: ");
            int id = Integer.parseInt(sc.nextLine());

            cs.setInt(1, id);

            // Đăng ký OUT parameter (DECIMAL)
            cs.registerOutParameter(2, Types.DECIMAL);

            cs.execute();

            double totalFee = cs.getDouble(2);

            System.out.println("Tổng viện phí: " + totalFee);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
