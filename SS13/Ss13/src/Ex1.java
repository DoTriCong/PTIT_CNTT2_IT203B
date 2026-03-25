/*
Phần 1: Phân tích
 JDBC mặc định bật Auto-Commit = true
 cho nên mỗi câu lệnh executeUpdate() sẽ tự commit ngay sau khi chạy
 Trong code cũ:
 thao tác 1 (UPDATE) chạy xong là commit ngay vào DB
 sau đó xảy ra lỗi RuntimeException
 thao tác 2 (INSERT) không chạy được
 kết quả: thuốc đã bị trừ, nhưng không có bản ghi lịch sử
 vi phạm tính Atomicity của Transaction
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Ex1 {
    private static final Statement DatabaseManager = null;

    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DatabaseManager.getConnection();
            // Tắt auto-commit để gom các câu lệnh thành 1 transaction
            conn.setAutoCommit(false);
            // Thao tác 1: Trừ số lượng thuốc trong kho
            String sqlUpdateInventory = "UPDATE Medicine_Inventory " +
                    "SET quantity = quantity - 1 " +
                    "WHERE medicine_id = ?";
            ps1 = conn.prepareStatement(sqlUpdateInventory);
            ps1.setInt(1, medicineId);
            int row1 = ps1.executeUpdate();
            if (row1 == 0) {
                throw new Exception("Không tìm thấy thuốc trong kho để cập nhật.");
            }
            // Thao tác 2: Ghi vào lịch sử bệnh án
            String sqlInsertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) " +
                    "VALUES (?, ?, GETDATE())";
            ps2 = conn.prepareStatement(sqlInsertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            int row2 = ps2.executeUpdate();
            if (row2 == 0) {
                throw new Exception("Không thể lưu lịch sử cấp phát thuốc.");
            }
            conn.commit();
            System.out.println("Cấp phát thuốc thành công!");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback transaction.");
                } catch (Exception rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }

            System.out.println("Có lỗi xảy ra: " + e.getMessage());

        } finally {
            try {
                if (ps2 != null)
                    ps2.close();
            } catch (Exception e) {
                System.out.println("Lỗi đóng ps2: " + e.getMessage());
            }

            try {
                if (ps1 != null)
                    ps1.close();
            } catch (Exception e) {
                System.out.println("Lỗi đóng ps1: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    // Bật lại auto-commit để tránh ảnh hưởng các chỗ khác
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println("Lỗi đóng connection: " + e.getMessage());
            }
        }
    }
}