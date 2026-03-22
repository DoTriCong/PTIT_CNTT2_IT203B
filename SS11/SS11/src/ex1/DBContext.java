package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBContext {
    // - Nếu liên tục khởi tạo Connection mà không đóng (close),
    //   hệ thống sẽ bị rò rỉ tài nguyên, dẫn đến quá tải kết nối.
    // - Trong môi trường bệnh viện (24/7), việc treo hệ thống có thể ảnh hưởng trực tiếp
    //   đến việc truy xuất hồ sơ bệnh nhân, gây hậu quả nghiêm trọng.
    //  Giải pháp:
    // - Dùng hằng số cho cấu hình (URL, USER, PASSWORD).
    // - Đảm bảo mọi phương thức truy vấn đều đóng Connection, Statement, ResultSet trong khối finally.
    // - Nâng cao: sử dụng Connection Pooling (HikariCP, C3P0) để quản lý tập trung.

    // PHẦN 2 - THỰC THI
    // Hằng số cấu hình
    private static final String URL = "jdbc:mysql://localhost:3306/database";
    private static final String USER = "root";
    private static final String PASSWORD = "123456Â";

    // Phương thức lấy kết nối
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Ví dụ phương thức truy vấn
    public void executeQuery(String sql) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(); // mở kết nối
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("patient_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
