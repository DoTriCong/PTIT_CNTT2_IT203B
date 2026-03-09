import java.util.function.*;

class User {
    String username;
    String role;

    public User() {
        this.username = "defaultUser";
        this.role = "user";
    }

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }
}

public class Ex1 {
    public static void main(String[] args) {

        User u = new User("cong", "admin");

        //1. Predicate: kiểm tra có phải admin không
        Predicate<User> isAdmin = user -> user.role.equalsIgnoreCase("admin");
        System.out.println("Is admin: " + isAdmin.test(u));

        //2. Function: chuyển User -> String
        Function<User, String> getUsername = user -> user.username;
        System.out.println("Username: " + getUsername.apply(u));

        //3. Consumer: in thông tin User
        Consumer<User> printUser = user ->
                System.out.println("User: " + user.username + " - Role: " + user.role);
        printUser.accept(u);

        //4. Supplier: tạo User mặc định
        Supplier<User> createUser = () -> new User();
        User newUser = createUser.get();
        System.out.println("New User: " + newUser.username + " - " + newUser.role);
    }
}