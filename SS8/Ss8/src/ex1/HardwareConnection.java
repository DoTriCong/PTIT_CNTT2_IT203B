package ex1;

public class HardwareConnection {

    private static HardwareConnection instance;

    private HardwareConnection() {
    }

    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
        }
        return instance;
    }

    public void connect() {
        System.out.println("HardwareConnection: Đã kết nối phần cứng.");
    }

    public void disconnect() {
        System.out.println("HardwareConnection: Ngắt kết nối phần cứng.");
    }
}