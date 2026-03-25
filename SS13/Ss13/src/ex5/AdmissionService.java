package ex5;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdmissionService {

    public void admitPatient(String patientName, int age, int bedId, double advanceAmount) {
        Connection conn = null;
        PreparedStatement psCheckBed = null;
        PreparedStatement psInsertPatient = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psInsertFinance = null;
        ResultSet rs = null;

        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            // Bước 1: Kiểm tra giường có tồn tại và đang trống không
            String sqlCheckBed = "SELECT status FROM Beds WHERE bed_id = ?";
            psCheckBed = conn.prepareStatement(sqlCheckBed);
            psCheckBed.setInt(1, bedId);
            rs = psCheckBed.executeQuery();

            if (!rs.next()) {
                throw new Exception("Ma giuong khong ton tai.");
            }

            String bedStatus = rs.getString("status");
            if (!"TRONG".equalsIgnoreCase(bedStatus)) {
                throw new Exception("Giuong da co nguoi, vui long chon giuong khac.");
            }

            // Bước 2: Thêm mới bệnh nhân
            String sqlInsertPatient = "INSERT INTO Patients (patient_name, age, bed_id, advance_amount, status) "
                    + "VALUES (?, ?, ?, ?, ?)";
            psInsertPatient = conn.prepareStatement(sqlInsertPatient, Statement.RETURN_GENERATED_KEYS);
            psInsertPatient.setString(1, patientName);
            psInsertPatient.setInt(2, age);
            psInsertPatient.setInt(3, bedId);
            psInsertPatient.setDouble(4, advanceAmount);
            psInsertPatient.setString(5, "NOI_TRU");

            int patientRow = psInsertPatient.executeUpdate();
            if (patientRow == 0) {
                throw new Exception("Khong the tao ho so benh nhan.");
            }

            int patientId = -1;
            try (ResultSet generatedKeys = psInsertPatient.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patientId = generatedKeys.getInt(1);
                } else {
                    throw new Exception("Khong lay duoc ma benh nhan vua tao.");
                }
            }

            // Bước 3: Cập nhật trạng thái giường thành đã có người
            String sqlUpdateBed = "UPDATE Beds SET status = 'DA_CO_NGUOI' WHERE bed_id = ? AND status = 'TRONG'";
            psUpdateBed = conn.prepareStatement(sqlUpdateBed);
            psUpdateBed.setInt(1, bedId);

            int bedRow = psUpdateBed.executeUpdate();
            if (bedRow == 0) {
                throw new Exception("Khong the cap nhat trang thai giuong.");
            }

            // Bước 4: Ghi nhận tiền tạm ứng vào bảng tài chính
            String sqlInsertFinance = "INSERT INTO Finance_Records (patient_id, amount, record_type, created_at) "
                    + "VALUES (?, ?, ?, NOW())";
            psInsertFinance = conn.prepareStatement(sqlInsertFinance);
            psInsertFinance.setInt(1, patientId);
            psInsertFinance.setDouble(2, advanceAmount);
            psInsertFinance.setString(3, "TAM_UNG");

            int financeRow = psInsertFinance.executeUpdate();
            if (financeRow == 0) {
                throw new Exception("Khong the ghi nhan tai chinh.");
            }

            conn.commit();
            System.out.println("Tiep nhan benh nhan thanh cong!");

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Da rollback giao dich.");
                } catch (SQLException rollbackEx) {
                    System.out.println("Loi khi rollback: " + rollbackEx.getMessage());
                }
            }

            System.out.println("Tiep nhan that bai: " + e.getMessage());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong ResultSet: " + e.getMessage());
            }

            try {
                if (psCheckBed != null) {
                    psCheckBed.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong psCheckBed: " + e.getMessage());
            }

            try {
                if (psInsertPatient != null) {
                    psInsertPatient.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong psInsertPatient: " + e.getMessage());
            }

            try {
                if (psUpdateBed != null) {
                    psUpdateBed.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong psUpdateBed: " + e.getMessage());
            }

            try {
                if (psInsertFinance != null) {
                    psInsertFinance.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong psInsertFinance: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong Connection: " + e.getMessage());
            }
        }
    }
}