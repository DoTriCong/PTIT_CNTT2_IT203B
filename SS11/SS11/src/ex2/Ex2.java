package ex2;

// - Đoạn mã ban đầu dùng if (rs.next()) => chỉ kiểm tra được 1 lần duy nhất.
// - Sau khi gọi rs.next(), con trỏ của ResultSet di chuyển từ vị trí "trước dòng đầu tiên"
//   sang dòng đầu tiên. Nếu dùng if, ta chỉ xử lý đúng 1 dòng rồi kết thúc.
// - Nếu bảng trống, rs.next() trả về false => không in gì, dễ gây lỗi logic.
//
//  Giải pháp:
// - Dùng vòng lặp while(rs.next()) để duyệt qua toàn bộ các dòng trong ResultSet.
// - Mỗi lần gọi rs.next(), con trỏ sẽ di chuyển sang dòng kế tiếp (nếu có).
// - Nhờ đó, ta có thể in ra toàn bộ danh sách thuốc trong kho.

// PHẦN 2 - THỰC THI
ResultSet rs = stmt.executeQuery("SELECT medicine_name, stock FROM Pharmacy");

// Dùng vòng lặp để duyệt toàn bộ kết quả
while (rs.next()) {
String name = rs.getString("medicine_name");
int stock = rs.getInt("stock");
    System.out.println("Thuốc: " + name + " | Số lượng tồn: " + stock);
}
