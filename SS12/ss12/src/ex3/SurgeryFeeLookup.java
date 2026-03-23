package ex3;

import java.sql.*;

public class SurgeryFeeLookup {

    public static void getSurgeryFee(Connection conn, int surgeryId) {
        try {

            /*
              VẤN ĐỀ CODE CŨ:
              - Gọi:
                cstmt.setInt(1, 505);
                cstmt.execute();
                cstmt.getDouble(2);

              - Nhưng KHÔNG đăng ký OUT parameter → lỗi:
                "The column index is out of range"

             JDBC không biết tham số thứ 2 là OUTPUT
             */

            /*
              TẠI SAO PHẢI registerOutParameter():
              1. JDBC cần biết trước kiểu dữ liệu của OUT parameter
                 - Stored Procedure có thể trả về nhiều kiểu khác nhau
                 - Driver cần chuẩn bị bộ nhớ và ánh xạ dữ liệu

              2. Nếu không đăng ký:
                 - JDBC coi tất cả là IN parameter
                 - Khi gọi getDouble(2) → không tồn tại → lỗi index

              3. THỨ TỰ BẮT BUỘC:
                 - set IN parameter
                 - register OUT parameter
                 - execute()
              - get OUT value
             */

            /*
              KIỂU DECIMAL TRONG SQL:
            - Trong Java phải dùng:
               Types.DECIMAL (hoặc Types.NUMERIC)
             */

            CallableStatement cstmt = conn.prepareCall("{call GET_SURGERY_FEE(?, ?)}");

            // 1. Set IN parameter
            cstmt.setInt(1, surgeryId);

            // 2. Đăng ký OUT parameter
            cstmt.registerOutParameter(2, Types.DECIMAL);

            // 3. Thực thi
            cstmt.execute();

            // 4. Lấy giá trị OUT
            double cost = cstmt.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + cost);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
