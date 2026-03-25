/*
Đầu vào:
- maBenhNhan (int): mã bệnh nhân cần xuất viện
- tienVienPhi (double): số tiền viện phí cần thanh toán

Đầu ra:
- In thông báo kết quả ra Console:
  + Nếu thành công: "Xuất viện và thanh toán thành công"
  + Nếu thất bại: mô tả lỗi chi tiết và rollback toàn bộ dữ liệu

Cách làm:
 Tắt Auto-Commit ngay từ đầu (conn.setAutoCommit(false))
 Trước khi trừ tiền phải SELECT số dư ví để phòng Bẫy 1 (thiếu tiền)
 Sau mỗi lệnh UPDATE phải kiểm tra số dòng bị ảnh hưởng (rows)
   Nếu rows == 0 thì throw Exception để phòng Bẫy 2 (Row Affected = 0)
 Nếu tất cả các bước đều thành công thì commit()
 Nếu có bất kỳ lỗi nào thì rollback()
 Luồng xử lý: (1) Tạo kết nối -> (2) Tắt Auto-Commit -> (3) Kiểm tra số dư ví ->
 (4) Trừ tiền viện phí -> (5) Giải phóng giường & kiểm tra kết quả -> (6)
 Cập nhật trạng thái bệnh nhân & kiểm tra kết quả -> (7) Xác nhận commit -> (8) Đóng kết nối trong finally.
Kết luận:
Giải pháp sử dụng Transaction đảm bảo nguyên tắc "All or Nothing":
- Không xảy ra tình trạng trừ tiền nhưng chưa xuất viện
- Không bị treo giường bệnh
- Dữ liệu luôn nhất quán và an toàn
*/



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex3 {
    public static final Statement DatabaseManager = null;
    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psSelect = null;
        PreparedStatement psUpdateBalance = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psUpdatePatient = null;
        ResultSet rs = null;
        try {
            conn = DatabaseManager.getConnection();
            // Tắt AutoCommit để gom 3 thao tác vào cùng 1 transaction
            conn.setAutoCommit(false);
            // Lấy số dư tạm ứng và mã giường của bệnh nhân
            String sqlSelect = "SELECT advance_balance, bed_id FROM Patients WHERE patient_id = ?";
            psSelect = conn.prepareStatement(sqlSelect);
            psSelect.setInt(1, maBenhNhan);
            rs = psSelect.executeQuery();
            // BẪY 2:
            // Nếu không tìm thấy bệnh nhân thì chủ động ném lỗi để rollback
            if (!rs.next()) {
                throw new Exception("Không tìm thấy bệnh nhân.");
            }
            double soDuTamUng = rs.getDouble("advance_balance");
            int maGiuong = rs.getInt("bed_id");
            // BẪY 1:
            // Không cho phép số dư bị âm
            // Nếu số dư < tiền viện phí thì ném lỗi và rollback
            if (soDuTamUng < tienVienPhi) {
                throw new Exception("Số dư tạm ứng không đủ để thanh toán viện phí.");
            }
            // Bước 1: Trừ tiền viện phí vào số dư tạm ứng
            String sqlUpdateBalance = "UPDATE Patients SET advance_balance = advance_balance - ? WHERE patient_id = ?";
            psUpdateBalance = conn.prepareStatement(sqlUpdateBalance);
            psUpdateBalance.setDouble(1, tienVienPhi);
            psUpdateBalance.setInt(2, maBenhNhan);
            int rowBalance = psUpdateBalance.executeUpdate();
            // BẪY 2:
            // Nếu executeUpdate() trả về 0 thì không có dòng nào được cập nhật
            // Phải tự ném lỗi để rollback
            if (rowBalance == 0) {
                throw new Exception("Không thể cập nhật số dư tạm ứng.");
            }
            // Bước 2: Giải phóng giường bệnh
            String sqlUpdateBed = "UPDATE Beds SET status = 'TRONG' WHERE bed_id = ?";
            psUpdateBed = conn.prepareStatement(sqlUpdateBed);
            psUpdateBed.setInt(1, maGiuong);
            int rowBed = psUpdateBed.executeUpdate();
            // BẪY 2:
            // Nếu không cập nhật được giường thì rollback
            if (rowBed == 0) {
                throw new Exception("Không thể cập nhật trạng thái giường bệnh.");
            }
            // Bước 3: Cập nhật trạng thái bệnh nhân thành đã xuất viện
            String sqlUpdatePatient = "UPDATE Patients SET status = 'DA_XUAT_VIEN' WHERE patient_id = ?";
            psUpdatePatient = conn.prepareStatement(sqlUpdatePatient);
            psUpdatePatient.setInt(1, maBenhNhan);
            int rowPatient = psUpdatePatient.executeUpdate();
            // BẪY 2:
            // Nếu không cập nhật được trạng thái bệnh nhân thì rollback
            if (rowPatient == 0) {
                throw new Exception("Không thể cập nhật trạng thái xuất viện của bệnh nhân.");
            }
            // Nếu cả 3 bước đều thành công thì commit
            conn.commit();
            System.out.println("Xuất viện và thanh toán thành công!");

        } catch (Exception e) {
            // Nếu có lỗi thì rollback toàn bộ transaction
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback transaction.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Lỗi khi rollback: " + rollbackEx.getMessage());
                }
            }
            System.out.println("Xuất viện thất bại: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng ResultSet: " + e.getMessage());
            }
            try {
                if (psSelect != null) {
                    psSelect.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psSelect: " + e.getMessage());
            }

            try {
                if (psUpdateBalance != null) {
                    psUpdateBalance.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psUpdateBalance: " + e.getMessage());
            }
            try {
                if (psUpdateBed != null) {
                    psUpdateBed.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psUpdateBed: " + e.getMessage());
            }
            try {
                if (psUpdatePatient != null) {
                    psUpdatePatient.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng psUpdatePatient: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng Connection: " + e.getMessage());
            }
        }
    }
}