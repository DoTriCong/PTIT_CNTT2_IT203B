package ex6;

public class PrintReceipt implements NotificationService {

    @Override
    public void notifyUser() {
        System.out.println("In hóa đơn tại cửa hàng");
    }
}
