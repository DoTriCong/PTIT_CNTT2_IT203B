package ex3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex3 {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        int inputId = 999;

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/database", "root", "123456Â");
            stmt = conn.createStatement();
            //  Vấn đề:
            // - executeUpdate(sql) trả về số dòng bị tác động (int).
            // - Nếu mã giường tồn tại, giá trị trả về >= 1 (số dòng cập nhật).
            // - Nếu mã giường không tồn tại, giá trị trả về = 0.
            // - Code ban đầu không kiểm tra giá trị này => luôn in "thành công",
            //   gây hiểu lầm cho y tá.
            //  Giải pháp:
            // - Kiểm tra giá trị trả về của executeUpdate().
            // - Nếu = 0 => thông báo "Mã giường không tồn tại".
            // - Nếu > 0 => thông báo "Cập nhật thành công".

            String sql = "UPDATE Beds SET bed_status = 'Đang sử dụng' WHERE bed_id = " + inputId;
            int rowsAffected = stmt.executeUpdate(sql);
            // PHẦN 2 - THỰC THI
            if (rowsAffected > 0) {
                System.out.println(" Đã cập nhật giường bệnh thành công!");
            } else {
                System.out.println("Mã giường " + inputId + " không tồn tại trong hệ thống!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
