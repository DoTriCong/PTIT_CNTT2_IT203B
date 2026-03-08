package ex6;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User();

        try {
            System.out.print("Nhập tên: ");
            String name = sc.nextLine();
            user.setName(name);
            System.out.print("Nhập tuổi: ");
            int age = Integer.parseInt(sc.nextLine());
            user.setAge(age);
            user.display();
        }
        catch (InvalidAgeException e) {
            Logger.logError(e.getMessage());
        }
        catch (NumberFormatException e) {
            Logger.logError("Tuổi phải là số hợp lệ");
        }
        finally {
            sc.close();
            System.out.println("Dọn dẹp tài nguyên");
        }
    }
}
