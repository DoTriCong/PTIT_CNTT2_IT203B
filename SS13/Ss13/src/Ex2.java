/*
Phần 1: Phân tích
   JDBC mặc định Auto-Commit = true, nhưng ở đây đã setAutoCommit(false)
   Các thao tác chỉ được lưu khi gọi commit()
   Vấn đề: Khi lỗi xảy ra ở bước 2, dev chỉ in ra lỗi bằng System.out.println()
   mà không rollback.
     kết quả là:
     transaction vẫn đang "treo" chưa commit, chưa rollback
     kết nối giữ trạng thái không an toàn, gây lãng phí tài nguyên
     cho nên vi phạm nguyên tắc transaction: phải có hành động khôi phục (Rollback)
     Hành động bị bỏ quên: conn.rollback()
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex2 {
    private static final Statement DatabaseManager = null;
    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        try (Connection conn = DatabaseManager.getConnection()) {
            // Tắt Auto-Commit
            conn.setAutoCommit(false);
            try {
                // Thao tác 1: Trừ tiền trong ví
                String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sqlDeductWallet);
                ps1.setDouble(1, amount);
                ps1.setInt(2, patientId);
                ps1.executeUpdate();
                // Thao tác 2: Cập nhật trạng thái hóa đơn (cố tình sai để test rollback)
                String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sqlUpdateInvoice);
                ps2.setInt(1, invoiceId);
                ps2.executeUpdate();
                // Commit nếu tất cả thành công
                conn.commit();
                System.out.println("Thanh toán hoàn tất!");
            } catch (SQLException e) {
                // Bắt buộc rollback khi có lỗi
                try {
                    conn.rollback();
                    System.out.println("Đã rollback giao dịch.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }

                System.out.println("Lỗi hệ thống: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Không thể kết nối DB: " + e.getMessage());
        }
    }
}