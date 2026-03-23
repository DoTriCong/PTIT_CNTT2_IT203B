package ex2;

import java.sql.*;

public class PatientVitalsUpdate {

    public static void updateVitals(Connection conn, int patientId, double temperature, int heartRate) {
        try {

            /*
              VẤN ĐỀ KHI DÙNG STATEMENT (code cũ):
              - Nối chuỗi:
                "UPDATE Vitals SET temperature = " + temp + " WHERE p_id = " + patientId

              - Với Locale khác nhau:
                + US: 37.5  (dùng dấu chấm) → OK
                + Pháp/Việt: 37,5 (dùng dấu phẩy) → SQL ERROR

              - Vì SQL chỉ chấp nhận dấu chấm (.) cho số thập phân
               Dễ lỗi cú pháp nếu phụ thuộc hệ điều hành / vùng miền
             */

            /*
              TẠI SAO setDouble(), setInt() GIẢI QUYẾT ĐƯỢC:

              1. KHÔNG PHỤ THUỘC LOCALE:
                 - PreparedStatement gửi dữ liệu dưới dạng nhị phân (binary) đến DB
                 - KHÔNG convert sang chuỗi như "37,5" hay "37.5"
                 - DB sẽ hiểu đúng kiểu double/int mà không cần quan tâm dấu . hay ,

              2. TYPE HANDLING (xử lý kiểu dữ liệu):
                 - setDouble() đảm bảo giá trị luôn là kiểu số double hợp lệ
                - setInt() đảm bảo giá trị luôn là số nguyên

              3. TRÁNH LỖI CÚ PHÁP:
                 - Không còn việc build SQL string thủ công, không lỗi format

              4. AN TOÀN HƠN:
                 - Đồng thời cũng tránh luôn SQL Injection
             */

            // Câu SQL chuẩn với placeholder
            String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Gán giá trị đúng kiểu dữ liệu
            pstmt.setDouble(1, temperature);
            pstmt.setInt(2, heartRate);
            pstmt.setInt(3, patientId);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println(" Cập nhật chỉ số thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}