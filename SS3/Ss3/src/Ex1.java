import java.util.List;

// Enum Status
enum Status {
    ACTIVE,
    INACTIVE
}

// Record User
record User(String username, String email, Status status) {
}

public class Ex1 {
    public static void main(String[] args) {

        // Tạo 3 User
        List<User> users = List.of(
                new User("alice", "alice@gmail.com", Status.ACTIVE),
                new User("bob", "bob@gmail.com", Status.INACTIVE),
                new User("charlie", "charlie@gmail.com", Status.ACTIVE));

        // In danh sách người dùng
        users.forEach(System.out::println);
    }
}