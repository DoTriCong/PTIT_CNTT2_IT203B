package ex5;
/*
Phân tích ngắn gọn:
- Input: maBenhNhan (int), tienTamUng (double), mã giường (int).
- Output: thông báo thành công hoặc lỗi, rollback nếu thất bại.
Các lỗi thường gặp:
 Nhập sai kiểu dữ liệu (InputMismatchException, NumberFormatException).
 Giường không tồn tại hoặc đã có người.
 Lỗi khi ghi nhận tài chính (mất mạng, sai SQL).
 Tiền tạm ứng <= 0.
5 Hai nhân viên cùng chọn một giường.
Giải pháp:
 Dùng JDBC Transaction: setAutoCommit(false).
 Validate dữ liệu trước khi ghi DB.
 Sau mỗi UPDATE kiểm tra rowsAffected, nếu = 0 thì throw Exception.
 Nếu tất cả thành công thì commit, nếu có lỗi thì rollback.
Kiến trúc:
 ReceptionView: nhập dữ liệu.
 PatientController: điều phối.
 AdmissionService: xử lý transaction (insert bệnh nhân, update giường, insert tài chính).
 DatabaseHelper: quản lý kết nối.
 Model: Patient, Bed.
Flow:
1. Nhập dữ liệu, validate.
2. Mở kết nối, tắt AutoCommit.
3. Kiểm tra giường.
4. INSERT bệnh nhân.
5. UPDATE giường.
6. INSERT tài chính.
7. Commit nếu thành công, rollback nếu lỗi.
8. Đóng tài nguyên.
Database:
- Beds(bed_id, bed_name, status).
- Patients(patient_id, patient_name, age, bed_id, advance_amount, status).
- Finance_Records(record_id, patient_id, amount, record_type, created_at).
*/

public class Ex5 {
    public static void main(String[] args) {
        ReceptionView view = new ReceptionView();
        view.start();
    }
}