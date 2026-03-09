interface Authenticatable {
    String getPassword();

    default boolean isAuthenticated() {
        return getPassword().isBlank();
    };

    static String encrypt(String rawPassword) {
        return rawPassword + 2026;
    }

}

class Student implements Authenticatable {
    private String fullName;
    private String password;

    public Student(String fullName, String password) {
        this.fullName = fullName;
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

public class Ex3 {
    public static void main(String[] args) {
        Student student = new Student("son", "son");
        System.out.println(student.isAuthenticated());
        String encrypted = Authenticatable.encrypt(student.getPassword());
        System.out.println(encrypted);
    }
}