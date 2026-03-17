package ex6;

public class PushNotification implements NotificationService {

    @Override
    public void notifyUser() {
        System.out.println("Gửi push notification: Đơn hàng thành công");
    }
}