package ex3;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = new User();

        System.out.print("Nhập tuổi: ");
        int age = sc.nextInt();

        try {

            user.setAge(age);

            System.out.println("Tuổi hợp lệ: " + user.getAge());

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }

        System.out.println("Chương trình vẫn tiếp tục chạy");
    }
}
