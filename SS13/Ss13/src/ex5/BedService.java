package ex5;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BedService {

    public void showAvailableBeds() {
        String sql = "SELECT bed_id, bed_name, status FROM Beds WHERE status = 'TRONG' ORDER BY bed_id";

        try (
                Connection conn = DatabaseHelper.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            System.out.println("\n DANH SACH GIUONG TRONG");

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.println("Ma giuong: " + rs.getInt("bed_id")
                        + " | Ten giuong: " + rs.getString("bed_name")
                        + " | Trang thai: " + rs.getString("status"));
            }

            if (!hasData) {
                System.out.println("Hien tai khong co giuong trong.");
            }

        } catch (SQLException e) {
            System.out.println("Khong the lay danh sach giuong: " + e.getMessage());
        }
    }
}
