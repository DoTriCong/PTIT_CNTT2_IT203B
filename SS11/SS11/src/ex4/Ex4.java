package ex4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ex4 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // Ví dụ input giả lập từ người dùng (có thể chứa chuỗi tấn công)
        String patientName = "' OR '1'='1";

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/database", "rôot", "123456Â");
            //  Vấn đề:
            // - Khi nối chuỗi trực tiếp:
            //   SELECT * FROM Patients WHERE full_name = '' OR '1'='1'
            // - Mệnh đề WHERE luôn đúng vì '1'='1' là điều kiện TRUE.
            // - Kết quả: Trả về toàn bộ bảng Patients => lộ dữ liệu bệnh án.
            //
            //  Giải pháp:
            // - Không nối chuỗi trực tiếp.
            // - Dùng PreparedStatement để truyền tham số an toàn.
            // - Có thể thêm bước kiểm tra và loại bỏ ký tự đặc biệt (' ; --) trước khi gán.

            String sql = "SELECT * FROM Patients WHERE full_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sanitizeInput(patientName)); // xử lý input trước khi bind

            rs = pstmt.executeQuery();

            // ===========================
            // PHẦN 2 - THỰC THI
            // ===========================
            while (rs.next()) {
                System.out.println("Bệnh nhân: " + rs.getString("full_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Hàm đơn giản loại bỏ ký tự đặc biệt nguy hiểm
    private static String sanitizeInput(String input) {
        if (input == null) return null;
        return input.replaceAll("[';--]", "");
        // loại bỏ dấu nháy đơn, chấm phẩy, và chuỗi --
    }
}

