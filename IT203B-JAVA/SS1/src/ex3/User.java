package ex3;

public class User {

    private int age;

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Tuổi không thể âm");
        }
        this.age = age;
    }
}
