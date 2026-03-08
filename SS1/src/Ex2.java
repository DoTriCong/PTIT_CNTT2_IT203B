import java.util.Scanner;

public class Ex2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập tổng số người: ");
        int totalUsers = sc.nextInt();

        System.out.print("Nhập số nhóm: ");
        int groups = sc.nextInt();
        try {
            int result = totalUsers / groups;
            System.out.println("Mỗi nhóm có: " + result + " người");
        } catch (ArithmeticException e) {
            System.out.println("Không thể chia cho 0");
        }
        System.out.println("Chương trình vẫn tiếp tục chạy");
    }
}