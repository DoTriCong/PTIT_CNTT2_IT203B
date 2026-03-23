package ex1;

import java.sql.*;

public class DoctorLogin {

    public static boolean login(Connection conn, String doctorCode, String password) {
        try {
            /*
              VẤN ĐỀ SQL INJECTION (code cũ):
             - Khi dùng nối chuỗi:
               "SELECT * FROM Doctors WHERE code = '" + code + "' AND pass = '" + pass + "'"
             - Hacker có thể nhập:
               password = ' OR '1'='1
             - Query trở thành:
               SELECT * FROM Doctors WHERE code = 'abc' AND pass = '' OR '1'='1'
             - Điều kiện '1'='1' luôn đúng → bypass đăng nhập
             */

            /*
              TẠI SAO PreparedStatement LÀ "TẤM KHIÊN":
              1. Cơ chế PRE-COMPILED (biên dịch trước):
                 - Câu SQL được gửi lên DB dưới dạng template:
                   SELECT * FROM Doctors WHERE code = ? AND pass = ?
                 - DB sẽ biên dịch trước cấu trúc SQL (parse + plan execution)
                - Sau đó mới gán giá trị vào dấu ?

              2. PHÂN TÁCH CODE & DATA:
                - Giá trị truyền vào (doctorCode, password) được coi là DATA thuần túy
                - KHÔNG BAO GIỜ được hiểu là một phần của câu lệnh SQL

              3. CHỐNG SQL INJECTION:
                 - Nếu hacker nhập: ' OR '1'='1
               - Nó sẽ được xử lý như một chuỗi bình thường:
                   pass = "' OR '1'='1"
                 - Không thể phá vỡ cấu trúc SQL → an toàn tuyệt đối
             */

            //  Sử dụng PreparedStatement an toàn
            String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            // Gán tham số vào dấu ?
            pstmt.setString(1, doctorCode);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            // Nếu có dữ liệu → đăng nhập thành công
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}