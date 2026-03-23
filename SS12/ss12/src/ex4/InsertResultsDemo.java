package ex4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class InsertResultsDemo {

    // Giả sử TestResult là class có phương thức getData()
    static class TestResult {
        private String data;
        public TestResult(String data) { this.data = data; }
        public String getData() { return data; }
    }

    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "123456Â");
        List<TestResult> list = List.of(
                new TestResult("A"),
                new TestResult("B"),
                new TestResult("C")
        );
        // Phần 1: Mã nguồn lỗi (Statement thường)
        // - Mỗi vòng lặp tạo mới một Statement.
        // - Database phải Parse và lập Execution Plan lại nhiều lần cho cùng một cấu trúc câu lệnh.
        // - Lãng phí CPU, bộ nhớ, tốc độ chậm.
        // - Nối chuỗi trực tiếp tiềm ẩn SQL Injection.
        for (TestResult tr : list) {
            String sql = "INSERT INTO Results(data) VALUES('" + tr.getData() + "')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql); // Tạo mới Statement và biên dịch lại nhiều lần
            stmt.close();
        }
        // Phần 2: Tối ưu với PreparedStatement
        // - Khởi tạo PreparedStatement một lần bên ngoài vòng lặp.
        // - Database chỉ Parse và lập Execution Plan một lần.
        // - Bên trong vòng lặp chỉ thay đổi tham số.
        // - Tốc độ cải thiện rõ rệt, an toàn hơn.
        // ============================
        String sql = "INSERT INTO Results(data) VALUES(?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        for (TestResult tr : list) {
            pstmt.setString(1, tr.getData());
            pstmt.executeUpdate();
        }
        pstmt.close();
        // Tối ưu hơn nữa: Batch Insert
        // - Giảm số lần round-trip giữa ứng dụng và Database.
        // - Hiệu năng tăng mạnh khi nạp dữ liệu khối lượng lớn.
        PreparedStatement pstmtBatch = conn.prepareStatement(sql);
        for (TestResult tr : list) {
            pstmtBatch.setString(1, tr.getData());
            pstmtBatch.addBatch();
        }
        pstmtBatch.executeBatch();
        pstmtBatch.close();

        conn.close();
    }
}
