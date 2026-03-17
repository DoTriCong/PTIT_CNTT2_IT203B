package ex6;

public class EmailNotification implements NotificationService {

    @Override
    public void notifyUser() {
        System.out.println("Gửi email: Đơn hàng thành công");
    }
}
