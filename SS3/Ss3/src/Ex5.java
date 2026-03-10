import java.util.Comparator;
import java.util.List;

enum Status {
    ACTIVE,
    INACTIVE
}

record User(String username, String email, Status status) {
}

public class Ex5 {
    public static void main(String[] args) {

        // Sinh viên tự định nghĩa danh sách users
        List<User> users = List.of(
                new User("alexander", "alex@gmail.com", Status.ACTIVE),
                new User("charlotte", "charlotte@gmail.com", Status.ACTIVE),
                new User("benjamin", "ben@gmail.com", Status.INACTIVE),
                new User("bob", "bob@gmail.com", Status.INACTIVE),
                new User("alice", "alice@gmail.com", Status.ACTIVE));

        // In ra 3 người dùng có username dài nhất
        users.stream()
                .sorted(Comparator.comparingInt(
                        user -> ((User) user).username().length()).reversed())
                .limit(3)
                .map(User::username)
                .forEach(System.out::println);
    }
}