import java.io.IOException;

public class Ex4 {

    public static void saveToFile() throws IOException {
        System.out.println("Đang lưu dữ liệu vào file");
        throw new IOException("Lỗi khi ghi file");
    }

    public static void processUserData() throws IOException {

        System.out.println("Đang xử lý dữ liệu người dùng");
        saveToFile();
    }
    public static void main(String[] args) {
        try {
            processUserData();
        } catch (IOException e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
        System.out.println("Chương trình vẫn tiếp tục chạy");
    }
}