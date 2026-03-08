package ex6;

public class User {

    private String name;
    private int age;

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Tên không hợp lệ!");
            return;
        }

        this.name = name;
    }

    public void setAge(int age) throws InvalidAgeException {

        if (age < 0) {
            throw new InvalidAgeException("Tuổi không thể âm!");
        }
        this.age = age;
    }

    public void display() {

        if (name != null) {
            System.out.println("Tên: " + name);
        }
        System.out.println("Tuổi: " + age);
    }
}
