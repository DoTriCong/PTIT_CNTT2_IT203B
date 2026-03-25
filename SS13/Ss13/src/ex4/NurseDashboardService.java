package ex4;
/*
 Đầu vào: Không có tham số (lấy hết danh sách bệnh nhân cấp cứu). Đầu ra: List BenhNhanDTO gồm tên + danh sách dịch vụ.
 Có 2 hướng xử lý:
 Hướng 1 (N+1 Query): SELECT 500 bệnh nhân, rồi lặp 500 lần gọi thêm SELECT dịch vụ cho từng người.
 Hướng 2 (LEFT JOIN): Gộp tất cả trong 1 câu SELECT duy nhất với LEFT JOIN giữa BenhNhan và DichVuSuDung.
 Sau đó đưa kết quả lên bộ nhớ và dùng LinkedHashMap để nhóm dữ liệu theo từng bệnh nhân.
 Đánh giá:
 Về Network: Hướng 1 gửi tới 501 queries (rất chậm). Hướng 2 chỉ cần 1 query (nhanh hơn nhiều).
 Về xử lý Java: Hướng 1 code đơn giản hơn. Hướng 2 cần thêm logic gộp bằng Map nhưng không phức tạp.
 Kết luận: Chọn Hướng 2 (LEFT JOIN) để loại bỏ tình trạng chờ 15 giây.
 LEFT JOIN cũng giúp hiển thị cả bệnh nhân chưa sử dụng dịch vụ nào (Bẫy 2).
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NurseDashboardService {

    private static final Statement DatabaseManager = null;

    public List<BenhNhanDTO> layDanhSachDashboard() {
        List<BenhNhanDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DatabaseManager.getConnection();

            String sql = "SELECT b.maBenhNhan, b.tenBenhNhan, b.tuoi, b.soGiuong, "
                    + "d.maDichVu, d.tenDichVu, d.loaiDichVu, d.soLuong "
                    + "FROM BenhNhan b "
                    + "LEFT JOIN DichVuSuDung d ON b.maBenhNhan = d.maBenhNhan "
                    + "WHERE b.trangThai = 'DANG_DIEU_TRI' "
                    + "ORDER BY b.maBenhNhan";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            Map<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();

            while (rs.next()) {
                int maBenhNhan = rs.getInt("maBenhNhan");

                BenhNhanDTO benhNhan = mapBenhNhan.get(maBenhNhan);
                if (benhNhan == null) {
                    benhNhan = new BenhNhanDTO();
                    benhNhan.setMaBenhNhan(maBenhNhan);
                    benhNhan.setTenBenhNhan(rs.getString("tenBenhNhan"));
                    benhNhan.setTuoi(rs.getInt("tuoi"));
                    benhNhan.setSoGiuong(rs.getString("soGiuong"));
                    benhNhan.setDsDichVu(new ArrayList<>());

                    mapBenhNhan.put(maBenhNhan, benhNhan);
                }

                // XỬ LÝ BẪY 2:
                // Nếu bệnh nhân chưa có dịch vụ thì các cột bên bảng DichVuSuDung sẽ là NULL.
                // Khi đó không tạo đối tượng DichVu, chỉ giữ dsDichVu là danh sách rỗng.
                int maDichVu = rs.getInt("maDichVu");
                if (!rs.wasNull()) {
                    DichVu dichVu = new DichVu();
                    dichVu.setMaDichVu(maDichVu);
                    dichVu.setTenDichVu(rs.getString("tenDichVu"));
                    dichVu.setLoaiDichVu(rs.getString("loaiDichVu"));
                    dichVu.setSoLuong(rs.getInt("soLuong"));

                    benhNhan.getDsDichVu().add(dichVu);
                }
            }

            result = new ArrayList<>(mapBenhNhan.values());

        } catch (SQLException e) {
            System.out.println("Loi khi lay du lieu dashboard: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong ResultSet: " + e.getMessage());
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong PreparedStatement: " + e.getMessage());
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Loi dong Connection: " + e.getMessage());
            }
        }

        return result;
    }
}
