package ex5.dao;

import ex5.model.Doctor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class DoctorDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "root";
    private static final String PASSWORD = "123456Â";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Xem danh sách bác sĩ
    public List<Doctor> getAllDoctors() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT doctor_id, full_name, specialty FROM Doctors";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("doctor_id"),
                        rs.getString("full_name"),
                        rs.getString("specialty")
                ));
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi khi lấy danh sách bác sĩ: " + e.getMessage());
        }
        return list;
    }

    // Thêm bác sĩ mới
    public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO Doctors(doctor_id, full_name, specialty) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getDoctorId());
            pstmt.setString(2, doctor.getFullName());
            pstmt.setString(3, doctor.getSpecialty());
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(" Lỗi khi thêm bác sĩ: " + e.getMessage());
            return false;
        }
    }

    // Thống kê chuyên khoa
    public void statisticSpecialty() {
        String sql = "SELECT specialty, COUNT(*) AS total FROM Doctors GROUP BY specialty";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Chuyên khoa: " + rs.getString("specialty") +
                        " | Số lượng bác sĩ: " + rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thống kê: " + e.getMessage());
        }
    }
}

